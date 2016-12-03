package com.cancercarecompany.ccc.ccc;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.DialogFragment;
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

    public static final String article_type_CME = "cme";
    public static final String article_type_INSPIRATION = "inspiration";
    public static final String article_type_TIPS = "tips";

    private ConnectionHandler connectHandler;
    private EditText questionText;
    private ArrayList<Event> eventList;
    private Integer infoIndex = 0;

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

        if(getResources().getBoolean(R.bool.portrait_only)){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else{
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        connectHandler = ConnectionHandler.getInstance();

        connectHandler.getPatient(connectHandler.person.patient.get(0).patient_ID);
        while(connectHandler.socketBusy){}

        final ImageButton startJourneyButton = (ImageButton) view.findViewById(R.id.button_start_journey);
        final TextView journeyHeaderText = (TextView) view.findViewById(R.id.txt_header_journey);
        final Button sendQuestionButton = (Button) view.findViewById(R.id.button_send_question);
        final ImageView eventImage1 = (ImageView) view.findViewById(R.id.img_home_events_event1);
        final ImageView eventImage2 = (ImageView) view.findViewById(R.id.img_home_events_event2);
        final ImageView eventImage3 = (ImageView) view.findViewById(R.id.img_home_events_event3);
        final TextView eventCategoryText1 = (TextView) view.findViewById(R.id.txt_home_events_sub_category1);
        final TextView eventCategoryText2 = (TextView) view.findViewById(R.id.txt_home_events_sub_category2);
        final TextView eventCategoryText3 = (TextView) view.findViewById(R.id.txt_home_events_sub_category3);
        final TextView eventDateText1 = (TextView) view.findViewById(R.id.txt_home_events_date1);
        final TextView eventDateText2 = (TextView) view.findViewById(R.id.txt_home_events_date2);
        final TextView eventDateText3 = (TextView) view.findViewById(R.id.txt_home_events_date3);
        final TextView eventTimeText1 = (TextView) view.findViewById(R.id.txt_home_events_time1);
        final TextView eventTimeText2 = (TextView) view.findViewById(R.id.txt_home_events_time2);
        final TextView eventTimeText3 = (TextView) view.findViewById(R.id.txt_home_events_time3);
        final RelativeLayout eventLayout1 = (RelativeLayout) view.findViewById(R.id.layout_home_event1);
        final RelativeLayout eventLayout2 = (RelativeLayout) view.findViewById(R.id.layout_home_event2);
        final RelativeLayout eventLayout3 = (RelativeLayout) view.findViewById(R.id.layout_home_event3);
        final TextView schoolText = (TextView) view.findViewById(R.id.txt_home_school);
        final TextView inspirationText = (TextView) view.findViewById(R.id.txt_home_inspiration);
        final TextView tipsText = (TextView) view.findViewById(R.id.txt_home_tips);
        final Button showAllQuestionsButton = (Button) view.findViewById(R.id.button_all_questions);
        eventLayout1.setVisibility(View.GONE);
        eventLayout2.setVisibility(View.GONE);
        eventLayout3.setVisibility(View.GONE);

        cmeArticleList = new ArrayList<Article>();
        inspirationArticleList = new ArrayList<Article>();
        tipsArticleList = new ArrayList<Article>();
        eventList = new ArrayList<>();
        questionText = (EditText) view.findViewById(R.id.edit_question);

        if (connectHandler.patient != null) {
            connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
            while (connectHandler.socketBusy) {}
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

            connectHandler.getQuestionsForPatient(connectHandler.patient.patient_ID);
            while(connectHandler.socketBusy){}

            connectHandler.getArticles(connectHandler.patient.patient_ID);
            while(connectHandler.socketBusy){}

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
                        cmeArticleList.get(infoIndex).text);
            }
        });

        inspirationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog( getActivity().getString(getActivity().getResources().getIdentifier("info_inspiration", "string", getActivity().getPackageName())),
                        inspirationArticleList.get(infoIndex).title,
                        inspirationArticleList.get(infoIndex).text);
            }
        });

        tipsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog( getActivity().getString(getActivity().getResources().getIdentifier("info_tips", "string", getActivity().getPackageName())),
                        tipsArticleList.get(infoIndex).title,
                        tipsArticleList.get(infoIndex).text);
            }
        });

        sendQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add date and time later
                if (!connectHandler.socketBusy){
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
                    while (connectHandler.socketBusy){}
                    questionCreatedDialog();
                }
            }
        });

        return view;
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

    void showInfoDialog(String type, String title, String text){
        FragmentManager fm = getFragmentManager();
        InfoDialogFragment dialogFragment = new InfoDialogFragment();
        Bundle args = new Bundle();
        args.putString(InfoDialogFragment.INFO_TYPE, type);
        args.putString(InfoDialogFragment.INFO_TITLE, title);
        args.putString(InfoDialogFragment.INFO_TEXT, text);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Josef");
    }
        @Override
    public void onResume(){
        super.onResume();
//        ((MainActivity) getActivity()).setTitle("Hem");
    }

}
