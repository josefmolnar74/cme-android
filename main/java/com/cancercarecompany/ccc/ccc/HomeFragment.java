package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private OnTabSwitchToEventListener mListener;

    public static final String article_type_CME = "cme";
    public static final String article_type_INSPIRATION = "inspiration";
    public static final String article_type_TIPS = "tips";

    private ConnectionHandler connectHandler;
    private ArrayList<Event> eventList;
    private Integer infoIndex = 0;

    private ImageButton startJourneyButton;
    private TextView journeyHeaderText;
    private Button sendQuestionButton;
    private ImageView eventImage1;
    private ImageView eventImage2;
    private ImageView eventImage3;
    private TextView eventCategoryText1;
    private TextView eventCategoryText2;
    private TextView eventCategoryText3;
    private TextView eventDateText1;
    private TextView eventDateText2;
    private TextView eventDateText3;
    private TextView eventTimeText1;
    private TextView eventTimeText2;
    private TextView eventTimeText3;
    private RelativeLayout eventLayout1;
    private RelativeLayout eventLayout2;
    private RelativeLayout eventLayout3;
    private TextView schoolText;
    private TextView inspirationText;
    private TextView tipsText;
    private EditText questionText;
    private Button showAllQuestionsButton;

    ArrayList<Article> cmeArticleList;
    ArrayList<Article> inspirationArticleList;
    ArrayList<Article> tipsArticleList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        connectHandler = ConnectionHandler.getInstance();
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);

        if(getResources().getBoolean(R.bool.portrait_only)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else{
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        startJourneyButton = (ImageButton) view.findViewById(R.id.button_start_journey);
        journeyHeaderText = (TextView) view.findViewById(R.id.txt_header_journey);
        sendQuestionButton = (Button) view.findViewById(R.id.button_send_question);
        eventImage1 = (ImageView) view.findViewById(R.id.img_home_events_event1);
        eventImage2 = (ImageView) view.findViewById(R.id.img_home_events_event2);
        eventImage3 = (ImageView) view.findViewById(R.id.img_home_events_event3);
        eventCategoryText1 = (TextView) view.findViewById(R.id.txt_home_events_sub_category1);
        eventCategoryText2 = (TextView) view.findViewById(R.id.txt_home_events_sub_category2);
        eventCategoryText3 = (TextView) view.findViewById(R.id.txt_home_events_sub_category3);
        eventDateText1 = (TextView) view.findViewById(R.id.txt_home_events_date1);
        eventDateText2 = (TextView) view.findViewById(R.id.txt_home_events_date2);
        eventDateText3 = (TextView) view.findViewById(R.id.txt_home_events_date3);
        eventTimeText1 = (TextView) view.findViewById(R.id.txt_home_events_time1);
        eventTimeText2 = (TextView) view.findViewById(R.id.txt_home_events_time2);
        eventTimeText3 = (TextView) view.findViewById(R.id.txt_home_events_time3);
        eventLayout1 = (RelativeLayout) view.findViewById(R.id.layout_home_event1);
        eventLayout2 = (RelativeLayout) view.findViewById(R.id.layout_home_event2);
        eventLayout3 = (RelativeLayout) view.findViewById(R.id.layout_home_event3);
        schoolText = (TextView) view.findViewById(R.id.txt_home_school);
        inspirationText = (TextView) view.findViewById(R.id.txt_home_inspiration);
        tipsText = (TextView) view.findViewById(R.id.txt_home_tips);
        showAllQuestionsButton = (Button) view.findViewById(R.id.button_all_questions);
        questionText = (EditText) view.findViewById(R.id.edit_question);
        eventLayout1.setVisibility(View.GONE);
        eventLayout2.setVisibility(View.GONE);
        eventLayout3.setVisibility(View.GONE);

        startJourneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Intent myIntent = new Intent(MyApplication.getContext(), JourneyActivity.class);
                    getActivity().startActivity(myIntent);
                    getActivity().finish();
                }
            }
        });

        eventLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnTabSwitchToEvent(1);
            }
        });

        eventLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnTabSwitchToEvent(2);
            }
        });

        eventLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mListener.OnTabSwitchToEvent(3);
            }
        });

        showAllQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                QuestionsDialogFragment dialogFragment = new QuestionsDialogFragment();
                dialogFragment.show(fm, "Josef");
            }
        });

        schoolText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog( getActivity().getString(getActivity().getResources().getIdentifier("info_school", "string", getActivity().getPackageName())),
                        cmeArticleList.get(infoIndex).title,
                        cmeArticleList.get(infoIndex).text,
                        cmeArticleList.get(infoIndex).source);
            }
        });

        inspirationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog( getActivity().getString(getActivity().getResources().getIdentifier("info_inspiration", "string", getActivity().getPackageName())),
                        inspirationArticleList.get(infoIndex).title,
                        inspirationArticleList.get(infoIndex).text,
                        inspirationArticleList.get(infoIndex).source);
            }
        });

        tipsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog( getActivity().getString(getActivity().getResources().getIdentifier("info_tips", "string", getActivity().getPackageName())),
                        tipsArticleList.get(infoIndex).title,
                        tipsArticleList.get(infoIndex).text,
                        tipsArticleList.get(infoIndex).source);
            }
        });

        sendQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add date and time later
                if (!connectHandler.pendingMessage){
                    Question newQuestion = new Question(0,
                            connectHandler.patient.patient_ID,
                            connectHandler.person.person_ID,
                            connectHandler.person.email,
                            questionText.getText().toString(),
                            "",
                            "",
                            "",
                            "");
                    // needs implementation in backend
                    connectHandler.createQuestion(newQuestion);
                    questionText.setText("");
                    final Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!connectHandler.pendingMessage) {
                                questionCreatedDialog();
                            }
                            else {
                                handler.postDelayed(this,1000);
                            }
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if ((connectHandler.patient != null) && (connectHandler.events != null) && (connectHandler.questions != null) && (connectHandler.articles != null)){
            cmeArticleList = new ArrayList<Article>();
            inspirationArticleList = new ArrayList<Article>();
            tipsArticleList = new ArrayList<Article>();
            eventList = new ArrayList<>();

            if (connectHandler.patient != null) {
                if (connectHandler.patient.care_team != null) {
                    if (connectHandler.patient != null) {
                        Date todaysDate = new Date();
                        // Step one day back so that todays events are not displayed in passed events list
                        Calendar c = Calendar.getInstance();
                        c.setTime(todaysDate);
                        c.add(Calendar.DATE, -1);
                        todaysDate = c.getTime();
                        for (int i = 0; i < connectHandler.events.event_data.size(); i++) {
                            Date date = null;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                            try {
                                date = format.parse(connectHandler.events.event_data.get(i).date);
                            } catch (Exception e) {
                                System.out.println("Date conversion unsuccesfull");
                            }
                            Date compareDate = null;
                            // Sort the events in date order
                            if ((date != null) && (todaysDate.before(date))) {
                                // put event in future list
                                if (eventList.size() == 0) {
                                    eventList.add(connectHandler.events.event_data.get(i));
                                } else {
                                    for (int j = 0; j < eventList.size(); j++) {
                                        try {
                                            compareDate = format.parse(eventList.get(j).date);
                                        } catch (Exception e) {
                                            System.out.println("Date conversion unsuccesfull");
                                        }

                                        if (compareDate.after(date)) {
                                            eventList.add(j, connectHandler.events.event_data.get(i));
                                            break;
                                        }
                                        if (j == eventList.size() - 1) {
                                            // new event is later than all others in list
                                            eventList.add(connectHandler.events.event_data.get(i));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (eventList.size() > 0){
                eventLayout1.setVisibility(View.VISIBLE);
                eventImage1.setImageResource(getActivity().getResources().getIdentifier("event_"+eventList.get(0).sub_category+"_bubble", "drawable", getActivity().getPackageName()));
                eventCategoryText1.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+eventList.get(0).sub_category, "string", getActivity().getPackageName())));
                eventDateText1.setText(eventList.get(0).date.substring(0,10));
                eventTimeText1.setText(eventList.get(0).time.substring(0,5));
            }

            if (eventList.size() > 1){
                eventLayout2.setVisibility(View.VISIBLE);
                eventImage2.setImageResource(getActivity().getResources().getIdentifier("event_"+eventList.get(1).sub_category+"_bubble", "drawable", getActivity().getPackageName()));
                eventCategoryText2.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+eventList.get(1).sub_category, "string", getActivity().getPackageName())));
                eventDateText2.setText(eventList.get(1).date.substring(0,10));
                eventTimeText2.setText(eventList.get(1).time.substring(0,5));
            }

            if (eventList.size() > 2){
                eventLayout3.setVisibility(View.VISIBLE);
                eventImage3.setImageResource(getActivity().getResources().getIdentifier("event_"+eventList.get(2).sub_category+"_bubble", "drawable", getActivity().getPackageName()));
                eventCategoryText3.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+eventList.get(2).sub_category, "string", getActivity().getPackageName())));
                eventDateText3.setText(eventList.get(2).date.substring(0,10));
                eventTimeText3.setText(eventList.get(2).time.substring(0,5));
            }

            if (connectHandler.patient != null) {
                journeyHeaderText.setText(journeyHeaderText.getText().toString().replace("*", connectHandler.patient.patient_name));

                for (int i=0; i < connectHandler.articles.article_data.size(); i++){
                    switch(connectHandler.articles.article_data.get(i).article_type){
                        case article_type_CME:
                            cmeArticleList.add(connectHandler.articles.article_data.get(i));
                            break;
                        case article_type_INSPIRATION:
                            inspirationArticleList.add(connectHandler.articles.article_data.get(i));
                            break;
                        case article_type_TIPS:
                            tipsArticleList.add(connectHandler.articles.article_data.get(i));
                            break;
                    }
                }
            }

            Random r = new Random();
            infoIndex = r.nextInt(cmeArticleList.size());
            schoolText.setText(cmeArticleList.get(infoIndex).title);
            infoIndex = r.nextInt(inspirationArticleList.size());
            inspirationText.setText(inspirationArticleList.get(infoIndex).title);
            infoIndex = r.nextInt(tipsArticleList.size());
            tipsText.setText(tipsArticleList.get(infoIndex).title);
        }
    }

    private void questionCreatedDialog(){
        // Generate dialog to make sure user wants to delete
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String alertText = String.format(getString(R.string.created_question));
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setNegativeButton(getString(R.string.ok),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                alertDialog.dismiss();
            }
        }.start();
    }

    private void startEventFragment(){
        // start Event fragment
    }

    void showInfoDialog(String type, String title, String text, String source){
        FragmentManager fm = getFragmentManager();
        InfoDialogFragment dialogFragment = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(InfoDialogFragment.INFO_TYPE, type);
        args.putString(InfoDialogFragment.INFO_TITLE, title);
        args.putString(InfoDialogFragment.INFO_TEXT, text);
        args.putString(InfoDialogFragment.INFO_SOURCE, source);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Josef");
    }

    public interface OnTabSwitchToEventListener {
        void OnTabSwitchToEvent(int eventIndex);
    }

    // make sure the Activity implemented it
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            try {
                this.mListener = (OnTabSwitchToEventListener)context;
            }
            catch (final ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
            }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            connectHandler.getPatient(connectHandler.person.patient.get(0).patient_ID);
            connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
            connectHandler.getQuestionsForPatient(connectHandler.patient.patient_ID);
            connectHandler.getArticles(connectHandler.patient.patient_ID);

            while(connectHandler.pendingMessage){}

            if ((connectHandler.person != null) && (connectHandler.events != null) && (connectHandler.questions != null) && (connectHandler.articles != null)){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                startFragment();
            } else {

            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

        private void startFragment(){
            onStart();
        }
    }
}

