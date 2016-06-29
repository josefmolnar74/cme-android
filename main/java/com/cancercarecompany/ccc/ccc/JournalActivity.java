package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//import android.widget.Spinner;

public class JournalActivity extends AppCompatActivity {

    ConnectionHandler connectHandler;
    ArrayList<Events> eventList;
    ArrayList<Patient> patientList = new ArrayList<>();

    ArrayList<Status> statusList = new ArrayList<>();
    GridView statusGridView;
    JournalStatusAdapter statusAdapter;

    ArrayList<String> beverageList = new ArrayList<>();
    GridView beverageGridView;
    JournalBeverageAdapter beverageAdapter;

    String lbl_datum;
    TextView header;
    TextView fr_medicin;
    TextView lu_medicin;
    TextView mi_medicin;
    TextView txt_rat_status;
    SeekBar seek_fat;
    RelativeLayout relativeLayout;
    String choice;
    Boolean fr_mark = Boolean.FALSE;
    Boolean lu_mark = Boolean.FALSE;
    Boolean mi_mark = Boolean.FALSE;
    Boolean kv_mark = Boolean.FALSE;
    ImageButton journeyButton;
    ImageButton careTeamButton;
    ImageButton emotionsButton;
    TextView emotionText;

    Button fatigueButton;
    Button painButton;
    Button mouthButton;
    Button tinglingButton;
    Button diarrheaButton;
    Button appetiteButton;
    Button dizzinessButton;
    Button vomitButton;
    Button otherButton;

    int fatigueIdForToday;
    int painIdForToday;
    int mouthIdForToday;
    int tinglingIdForToday;
    int diarrheaIdForToday;
    int appetiteIdForToday;
    int dizzinessIdForToday;
    int vomitIdForToday;
    int otherIdForToday;
    int beverageIdForToday;

    public static final String TIME_SIMPLE_FORMAT   = "yyyy-MM-dd";
    public static final String DATE_SIMPLE_FORMAT   = "kk:mm:ss";

    public static final String EMOTION_HAPPY        = "Happy";
    public static final String EMOTION_SAD          = "Sad";
    public static final String EMOTION_FRUSTRATED   = "Frustrated";
    public static final String EMOTION_WORRIED      = "Worried";
    public static final String EMOTION_SATISFIED    = "Satisfied";
    public static final String EMOTION_TIRED        = "Tired";
    public static final String EMOTION_SICK         = "Sick";
    public static final String EMOTION_NAUSEOUS     = "Nauseous";
    public static final String EMOTION_SCARED       = "Scared";

    public static final String SIDEEFFECT_TYPE_FATIGUE      = "Fatigue";
    public static final String SIDEEFFECT_TYPE_PAIN         = "Pain";
    public static final String SIDEEFFECT_TYPE_MOUTH        = "Mouth change";
    public static final String SIDEEFFECT_TYPE_TINGLING     = "Tingling/numbness";
    public static final String SIDEEFFECT_TYPE_DIARRHEA     = "Diarrhea";
    public static final String SIDEEFFECT_TYPE_APPETITE     = "Appetite";
    public static final String SIDEEFFECT_TYPE_DIZZINESS     = "Dizziness";
    public static final String SIDEEFFECT_TYPE_VOMIT        = "Vomit";
    public static final String SIDEEFFECT_TYPE_OTHER        = "Other";

    public static final String SIDEEFFECT_PAIN_RIGHT_HAND_VALUE         = "RHA";
    public static final String SIDEEFFECT_PAIN_RIGHT_SHOULDER_VALUE     = "RSH";
    public static final String SIDEEFFECT_PAIN_RIGHT_CHEST_VALUE        = "RCH";
    public static final String SIDEEFFECT_PAIN_RIGHT_ARM_VALUE          = "RAR";
    public static final String SIDEEFFECT_PAIN_RIGHT_HIP_VALUE          = "RHI";
    public static final String SIDEEFFECT_PAIN_RIGHT_UPPER_LEG_VALUE    = "RUL";
    public static final String SIDEEFFECT_PAIN_RIGHT_KNEE_VALUE         = "RKN";
    public static final String SIDEEFFECT_PAIN_RIGHT_LOWER_LEG_VALUE    = "RLL";
    public static final String SIDEEFFECT_PAIN_RIGHT_FOOT_VALUE         = "RFO";
    public static final String SIDEEFFECT_PAIN_LEFT_HAND_VALUE          = "LHA";
    public static final String SIDEEFFECT_PAIN_LEFT_SHOULDER_VALUE      = "LSH";
    public static final String SIDEEFFECT_PAIN_LEFT_CHEST_VALUE         = "LCH";
    public static final String SIDEEFFECT_PAIN_LEFT_ARM_VALUE           = "LAR";
    public static final String SIDEEFFECT_PAIN_LEFT_HIP_VALUE           = "LHI";
    public static final String SIDEEFFECT_PAIN_LEFT_UPPER_LEG_VALUE     = "LUL";
    public static final String SIDEEFFECT_PAIN_LEFT_KNEE_VALUE          = "LKN";
    public static final String SIDEEFFECT_PAIN_LEFT_LOWER_LEG_VALUE     = "LLL";
    public static final String SIDEEFFECT_PAIN_LEFT_FOOT_VALUE          = "LFO";
    public static final String SIDEEFFECT_PAIN_HEAD_VALUE               = "HEA";
    public static final String SIDEEFFECT_PAIN_NECK_VALUE               = "NEC";
    public static final String SIDEEFFECT_PAIN_UPPER_BACK_VALUE         = "UBA";
    public static final String SIDEEFFECT_PAIN_MID_BACK_VALUE           = "MBA";
    public static final String SIDEEFFECT_PAIN_LOWER_BACK_VALUE         = "LBA";
    public static final String SIDEEFFECT_PAIN_RIGHT_ABDOMEN_VALUE      = "RAB";
    public static final String SIDEEFFECT_PAIN_LEFT_ABDOMEN_VALUE       = "LAB";
    public static final String SIDEEFFECT_PAIN_TAILBONE_VALUE           = "TAI";

    public static final String SIDEEFFECT_DIARRHEA_VALUE_1 = "1";
    public static final String SIDEEFFECT_DIARRHEA_VALUE_2 = "2";
    public static final String SIDEEFFECT_DIARRHEA_VALUE_3 = "3";
    public static final String SIDEEFFECT_DIARRHEA_VALUE_4 = "4";
    public static final String SIDEEFFECT_DIARRHEA_VALUE_5 = "MORE THAN 4";

    private String[] sideeffectDiarrheaValues = {SIDEEFFECT_DIARRHEA_VALUE_1,
                        SIDEEFFECT_DIARRHEA_VALUE_2,SIDEEFFECT_DIARRHEA_VALUE_3,
                        SIDEEFFECT_DIARRHEA_VALUE_4,SIDEEFFECT_DIARRHEA_VALUE_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        fatigueButton = (Button) findViewById(R.id.btn_journal_sideeffect_fatigue);
        painButton = (Button) findViewById(R.id.btn_journal_sideeffect_pain);
        mouthButton = (Button) findViewById(R.id.btn_journal_sideeffect_mouth);
        tinglingButton = (Button) findViewById(R.id.btn_journal_sideeffect_tingling);
        diarrheaButton = (Button) findViewById(R.id.btn_journal_sideeffect_diarrhea);
        appetiteButton = (Button) findViewById(R.id.btn_journal_sideeffect_appetite);
        dizzinessButton = (Button) findViewById(R.id.btn_journal_sideeffect_dizziness);
        vomitButton = (Button) findViewById(R.id.btn_journal_sideeffect_vomit);
        otherButton = (Button) findViewById(R.id.btn_journal_sideeffect_other);
        emotionsButton = (ImageButton) findViewById(R.id.btn_journal_emotions);

        final EditText statusTextEditText       = (EditText) findViewById(R.id.edtxt_journal_status);
//        final TextView txt_med_txt            = (TextView) findViewById(R.id.txt_med_int);
        final TextView journalHeaderText = (TextView) findViewById(R.id.txt_journal_header);
        final Button saveStatusButton           = (Button) findViewById(R.id.btn_journal_status_save);
        final Button medicationBreakfastButton  = (Button) findViewById(R.id.btn_journal_medication_breakfast);
        final Button medicationLunchButton      = (Button) findViewById(R.id.btn_journal_medication_lunch);
        final Button medicationDinnerButton     = (Button) findViewById(R.id.btn_journal_medication_dinner);
        final ImageButton journeyButton         = (ImageButton) findViewById(R.id.btn_journal_journey_button);
        final ImageButton careTeamButton        = (ImageButton) findViewById(R.id.btn_journal_careteam_button);
        final CalendarView calendar             = (CalendarView) findViewById(R.id.cal_journal_calendar);
        emotionText =  (TextView) findViewById(R.id.txt_journal_emotions);
        // Statusbar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));


        //Get journal data
        connectHandler = ConnectionHandler.getInstance();
        connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}
        connectHandler.getStatusForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}
        connectHandler.getSideeffectForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}
        connectHandler.getBeveragesForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

        TextView loggedIn = (TextView) findViewById(R.id.loggedIn);
        if (connectHandler.person != null){
            loggedIn.setText(connectHandler.person.first_name);
        }

        if (connectHandler.status != null){
            for (int i = 0; i < connectHandler.status.status_data.size(); i++) {
                //TODO Check if there is status for today
                boolean dateIsToday = false;
                try {
                    dateIsToday = checkIfDateIsToday(connectHandler.status.status_data.get(i).date);
                } catch (ParseException e){}
                if (dateIsToday){
                    statusList.add(connectHandler.status.status_data.get(i));
                }
            }
        }

        // Find if there are any sideeffects for today
        findSideeffectsForToday();

        statusGridView = (GridView) findViewById(R.id.gridview_journal_status);
        statusAdapter = new JournalStatusAdapter(this, statusList);
        statusGridView.setAdapter(statusAdapter);

        int savedBeverageAmount = 0;
        beverageIdForToday = -1; //init
        if (connectHandler.beverages.beverage_data.size() > 0){
            // Check if beverage has already been saved today
            // Get last saved beverage and check for date
            int lastIndex = connectHandler.beverages.beverage_data.size()-1;
            boolean dateIsToday = false;
            try {
                dateIsToday = checkIfDateIsToday(connectHandler.beverages.beverage_data.get(lastIndex).date);
            } catch (ParseException e){}

            if (dateIsToday){
                // yep, beverage has been saved today
                beverageIdForToday = lastIndex;
                savedBeverageAmount = connectHandler.beverages.beverage_data.get(beverageIdForToday).amount;
            }
        }

        for (int i = 0; i < 8 ; i++) {
            if (i < savedBeverageAmount){
                beverageList.add("full");
            } else{
                beverageList.add("empty");
            }
        }

        beverageGridView = (GridView) findViewById(R.id.gridview_journal_beverage);
        beverageAdapter = new JournalBeverageAdapter(this, beverageList);
        beverageGridView.setAdapter(beverageAdapter);

        //Open popup window to show user detail information and edit/delete
        beverageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((beverageList.get(position) == "empty") && ((position == 0) || ((position > 0) && (beverageList.get(position-1) == "full")))){
                    beverageList.set(position, "full");
                } else if ((beverageList.get(position) == "full") && ((position == 7) || ((position < 7) && (beverageList.get(position+1) == "empty")))){
                    beverageList.set(position, "empty");
                }
                beverageAdapter.notifyDataSetChanged();
            }
        });

        journeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journeyActivity();
            }
        });

        careTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journeyActivity();
            }
        });

        emotionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmotions();
            }
        });

        saveStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (!statusTextEditText.getText().toString().isEmpty()){
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String time = new SimpleDateFormat("kk:mm:ss").format(new Date());
                    // Create new status
                    Status newStatus = new Status(
                            0,
                            connectHandler.patient.patient_ID,
                            connectHandler.person.person_ID,
                            date,
                            time,
                            statusTextEditText.getText().toString(),
                            emotionText.getText().toString(),
                            1);
                    connectHandler.createStatus(newStatus);

                    statusList.add(newStatus);
                    statusAdapter.notifyDataSetChanged();
                    statusTextEditText.setText("");
                }
            }
        });

        //Open popup window to show user detail information and edit/delete
        statusGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showStatus(position);
            }
        });

        fatigueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use same popup as for appetite
                createSideeffectAppetite(SIDEEFFECT_TYPE_FATIGUE);
            }
        });

        painButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectPain(SIDEEFFECT_TYPE_PAIN);
            }
        });

        tinglingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectPain(SIDEEFFECT_TYPE_TINGLING);
            }
        });

        appetiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectAppetite(SIDEEFFECT_TYPE_APPETITE);            }
        });

        mouthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectDiarrhea(SIDEEFFECT_TYPE_MOUTH);            }
        });

        dizzinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectDiarrhea(SIDEEFFECT_TYPE_DIZZINESS);
            }
        });

        diarrheaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectDiarrhea(SIDEEFFECT_TYPE_DIARRHEA);
            }
        });

        vomitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectDiarrhea(SIDEEFFECT_TYPE_VOMIT);
            }
        });

        medicationBreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fr_mark.equals(Boolean.FALSE)) {
                    fr_mark = Boolean.TRUE;
                    medicationBreakfastButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    fr_mark = Boolean.FALSE;
                    medicationBreakfastButton.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
//                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
//                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }

        });


        medicationLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( lu_mark.equals(Boolean.FALSE)) {
                    lu_mark = Boolean.TRUE;
                    medicationLunchButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    lu_mark = Boolean.FALSE;
                    medicationLunchButton.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
//                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
//                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }
        });

        medicationDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mi_mark.equals(Boolean.FALSE)) {
                    mi_mark = Boolean.TRUE;
                    medicationDinnerButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    mi_mark = Boolean.FALSE;
                    medicationDinnerButton.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
//                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
//                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }
        });

        careTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                careTeam();
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int date) {
                Toast.makeText(getApplicationContext(),date+ "/"+month+"/"+year,4000).show();
                //     getString(R.string.txt_journal_headline, year,month, date);
                journalHeaderText.setText(R.string.txt_journal_headline);
                journalHeaderText.setText((journalHeaderText.getText()) + " " + year + "-" + month + "-" + date);
            }
        });
    }
    @Override
    protected void onPause() {

        // Save beverage amount
        int amount = 0;
        for (int i = 0; i < beverageList.size();i++){
            if (beverageList.get(i) == "full"){
                amount += 1;
            }
        }
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("kk:mm:ss").format(new Date());
        Beverage beverage = new Beverage(   0,
                                            connectHandler.patient.patient_ID,
                                            connectHandler.person.person_ID,
                                            date,
                                            time,
                                            amount);
        if (beverageIdForToday >= 0){
            // update already existing beverage for today
            beverage.beverage_ID = connectHandler.beverages.beverage_data.get(beverageIdForToday).beverage_ID;
            connectHandler.updateBeverage(beverage);
        } else{
            // Beverage exist for today, create a new one
            connectHandler.createBeverage(beverage);
        }
        super.onPause();
    }

    public void showStatus(final int position) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.journal_status_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText statusEditText        = (EditText) popupView.findViewById(R.id.txt_journal_status_popup_status_text);
        final Button   buttonSave           = (Button) popupView.findViewById(R.id.btn_journal_status_save);
        final Button   buttonCancel         = (Button) popupView.findViewById(R.id.btn_journal_status_cancel);
        final Button   buttonEdit           = (Button) popupView.findViewById(R.id.btn_journal_status_edit);
        final Button   buttonDelete         = (Button) popupView.findViewById(R.id.btn_journal_status_delete);

        statusEditText.setFocusable(false);
        statusEditText.setText(statusList.get(position).status);
        buttonEdit.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonSave.setVisibility(View.INVISIBLE);

        RelativeLayout relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_journal_status_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                buttonDelete.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                saveStatus(position);
                popupWindow.dismiss();
            }

            private void saveStatus(int position) {
                //update in gridPosition
                statusList.get(position).status = statusEditText.getText().toString();
//                statusList.get(listPosition).emotion = emotionEditText.getText().toString();
                Status updateStatus = new Status(
                        statusList.get(position).status_ID,
                        statusList.get(position).patient_ID,
                        statusList.get(position).person_ID,
                        statusList.get(position).date,
                        statusList.get(position).time,
                        statusEditText.getText().toString(),
                        "",
                        0); // must implement share

                connectHandler.updateStatus(updateStatus);

                while (connectHandler.socketBusy){}
            }

        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                statusEditText.setEnabled(true);
                statusEditText.setFocusable(true);
                statusEditText.setFocusableInTouchMode(true);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonEdit.getVisibility() == View.VISIBLE) {
                    popupWindow.dismiss();
                }
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                buttonDelete.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.INVISIBLE);
                deleteCareTeamMembers(position);
            }
            private void deleteCareTeamMembers(int index) {
                statusList.remove(position);
                statusAdapter.notifyDataSetChanged();
                connectHandler.deleteStatus(statusList.get(position).status_ID);
                popupWindow.dismiss();
            }
        });
    }

    public void showEmotions() {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.emotions_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final ImageButton buttonEmotionHappy = (ImageButton) popupView.findViewById(R.id.btn_emotion_happy);
        final ImageButton buttonEmotionSad = (ImageButton) popupView.findViewById(R.id.btn_emotion_sad);
        final ImageButton buttonEmotionFrustrated = (ImageButton) popupView.findViewById(R.id.btn_emotion_frustrated);
        final ImageButton buttonEmotionSatisfied = (ImageButton) popupView.findViewById(R.id.btn_emotion_satisfied);
        final ImageButton buttonEmotionSick = (ImageButton) popupView.findViewById(R.id.btn_emotion_sick);
        final ImageButton buttonEmotionNauseous = (ImageButton) popupView.findViewById(R.id.btn_emotion_nauseous);
        final ImageButton buttonEmotionTired = (ImageButton) popupView.findViewById(R.id.btn_emotion_tired);
        final ImageButton buttonEmotionScared = (ImageButton) popupView.findViewById(R.id.btn_emotion_scared);
        final ImageButton buttonEmotionWorried = (ImageButton) popupView.findViewById(R.id.btn_emotion_worried);
        final TextView emotionHappyText = (TextView) popupView.findViewById(R.id.txt_emotion_happy);
        final TextView emotionSadText = (TextView) popupView.findViewById(R.id.txt_emotion_sad);
        final TextView emotionFrustratedText = (TextView) popupView.findViewById(R.id.txt_emotion_frustrated);
        final TextView emotionSatisfiedText = (TextView) popupView.findViewById(R.id.txt_emotion_satisfied);
        final TextView emotionSickText = (TextView) popupView.findViewById(R.id.txt_emotion_sick);
        final TextView emotionNauseousText = (TextView) popupView.findViewById(R.id.txt_emotion_nauseous);
        final TextView emotionTiredText = (TextView) popupView.findViewById(R.id.txt_emotion_tired);
        final TextView emotionScaredText = (TextView) popupView.findViewById(R.id.txt_emotion_scared);
        final TextView emotionWorriedText = (TextView) popupView.findViewById(R.id.txt_emotion_worried);

        RelativeLayout relativeLayout = (RelativeLayout) popupView.findViewById(R.id.emotions_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.LEFT, 0, 0);

        buttonEmotionHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_happy);
                emotionText.setText(emotionHappyText.getText());
                popupWindow.dismiss();

            }
        });

        buttonEmotionSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_sad);
                emotionText.setText(emotionSadText.getText());
                popupWindow.dismiss();
            }
        });

        buttonEmotionFrustrated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_frustrated);
                emotionText.setText(emotionFrustratedText.getText());
                popupWindow.dismiss();
            }
        });

        buttonEmotionSatisfied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_satisfied);
                emotionText.setText(emotionSatisfiedText.getText());
                popupWindow.dismiss();
            }
        });
        buttonEmotionSick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_sick);
                emotionText.setText(emotionSickText.getText());
                popupWindow.dismiss();
            }
        });
        buttonEmotionNauseous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_nauseous);
                emotionText.setText(emotionNauseousText.getText());
                popupWindow.dismiss();
            }
        });
        buttonEmotionWorried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_worried);
                emotionText.setText(emotionWorriedText.getText());
                popupWindow.dismiss();
            }
        });
        buttonEmotionScared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_scared);
                emotionText.setText(emotionScaredText.getText());
                popupWindow.dismiss();
            }
        });
        buttonEmotionTired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emotionsButton.setImageResource(R.drawable.emotion_tired);
                emotionText.setText(emotionTiredText.getText());
                popupWindow.dismiss();
            }
        });
    }

    public void createSideeffectPain(final String sideeffectType) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.journal_sideeffect_pain_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final CheckBox rightHandCheckbox            = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_hand);
        final CheckBox rightShoulderCheckbox        = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_shoulder);
        final CheckBox rightChestCheckbox           = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_chest);
        final CheckBox rightArmCheckbox             = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_arm);
        final CheckBox rightHipCheckbox             = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_hip);
        final CheckBox rightUpperLegCheckbox        = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_upper_leg);
        final CheckBox rightKneeCheckbox            = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_knee);
        final CheckBox rightLowerLegCheckbox        = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_lower_leg);
        final CheckBox rightFootCheckbox            = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_foot);
        final CheckBox leftHandCheckbox             = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_hand);
        final CheckBox leftShoulderCheckbox         = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_shoulder);
        final CheckBox leftChestCheckbox            = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_chest);
        final CheckBox leftArmCheckbox              = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_arm);
        final CheckBox leftHipCheckbox              = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_hip);
        final CheckBox leftUpperLegCheckbox         = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_upper_leg);
        final CheckBox leftKneeCheckbox             = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_knee);
        final CheckBox leftLowerLegCheckbox         = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_lower_leg);
        final CheckBox leftFootCheckbox             = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_foot);
        final CheckBox headCheckbox                 = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_head);
        final CheckBox neckCheckbox                 = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_neck);
        final CheckBox upperBackCheckbox           = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_upper_back);
        final CheckBox midBackCheckbox              = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_mid_back);
        final CheckBox lowerBackCheckbox            = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_lower_back);
        final CheckBox rightAbdomenCheckbox         = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_abdomen);
        final CheckBox leftAbdomenCheckbox          = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_abdomen);
        final CheckBox tailboneCheckbox             = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_tailbone);

        final TextView sideeffectsHeaderTextView    = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_popup_pain_headline);
        final TextView textSideeffectQuestion       = (TextView) popupView.findViewById(R.id.txt_journal_sideeffects_popup_pain_question);
        final Button    buttonSave                  = (Button) popupView.findViewById(R.id.btn_journal_status_save);
        final Button    buttonCancel                = (Button) popupView.findViewById(R.id.btn_journal_status_cancel);

        switch(sideeffectType){
            case SIDEEFFECT_TYPE_PAIN:
                sideeffectsHeaderTextView.setText(R.string.journal_sideeffects_pain);
                break;
            case SIDEEFFECT_TYPE_TINGLING:
                sideeffectsHeaderTextView.setText(R.string.journal_sideeffects_tingling);
                break;
        }
        // Replace X with patient name
        textSideeffectQuestion.setText(textSideeffectQuestion.getText().toString().replace("*", connectHandler.patient.patient_name));

        // if sideeffect exist, initalise the saved checkbox values
        String sideeffectValueString = null;
        if ((painIdForToday >= 0) && (sideeffectType == SIDEEFFECT_TYPE_PAIN)){
            sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(painIdForToday).value;
        } else if ((tinglingIdForToday >= 0) && (sideeffectType == SIDEEFFECT_TYPE_TINGLING)){
            sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(tinglingIdForToday).value;
        }

        if (sideeffectValueString != null){
            // Sideeffect on this day exist, populate the checkboxes
            String[] bodyAreasArray = sideeffectValueString.split(",");
            for (String s : bodyAreasArray){
                switch(s){
                    case SIDEEFFECT_PAIN_RIGHT_HAND_VALUE:
                        rightHandCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_SHOULDER_VALUE:
                        rightShoulderCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_CHEST_VALUE:
                        rightChestCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_ARM_VALUE:
                        rightArmCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_HIP_VALUE:
                        rightHipCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_UPPER_LEG_VALUE:
                        rightUpperLegCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_KNEE_VALUE:
                        rightKneeCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_LOWER_LEG_VALUE:
                        rightLowerLegCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_FOOT_VALUE:
                        rightFootCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_HAND_VALUE:
                        leftHandCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_SHOULDER_VALUE:
                        leftShoulderCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_CHEST_VALUE:
                        leftChestCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_ARM_VALUE:
                        leftArmCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_HIP_VALUE:
                        leftHipCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_UPPER_LEG_VALUE:
                        leftUpperLegCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_KNEE_VALUE:
                        leftKneeCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_LOWER_LEG_VALUE:
                        leftLowerLegCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_FOOT_VALUE:
                        leftFootCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_HEAD_VALUE:
                        headCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_NECK_VALUE:
                        neckCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_UPPER_BACK_VALUE:
                        upperBackCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_MID_BACK_VALUE:
                        midBackCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LOWER_BACK_VALUE:
                        lowerBackCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_RIGHT_ABDOMEN_VALUE:
                        rightAbdomenCheckbox.setChecked(true);
                        break;
                    case SIDEEFFECT_PAIN_LEFT_ABDOMEN_VALUE:
                        leftAbdomenCheckbox.setChecked(true);break;
                    case SIDEEFFECT_PAIN_TAILBONE_VALUE:
                        tailboneCheckbox.setChecked(true);
                        break;

                }
            }
        }

        buttonCancel.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);

        RelativeLayout relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_journal_sideeffects_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancel.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                saveSideeffectPain(sideeffectType);
                popupWindow.dismiss();
            }

            private void saveSideeffectPain(String sideeffectType) {

                String sideeffectValue = "";

                if (rightHandCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_HAND_VALUE;
                    sideeffectValue += ",";
                }
                if (rightShoulderCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_SHOULDER_VALUE;
                    sideeffectValue += ",";
                }
                if (rightChestCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_CHEST_VALUE;
                    sideeffectValue += ",";
                }
                if (rightArmCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_ARM_VALUE;
                    sideeffectValue += ",";
                }
                if (rightHipCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_HIP_VALUE;
                    sideeffectValue += ",";
                }
                if (rightUpperLegCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_UPPER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (rightKneeCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_KNEE_VALUE;
                    sideeffectValue += ",";
                }
                if (rightLowerLegCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_LOWER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (rightFootCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_FOOT_VALUE;
                    sideeffectValue += ",";
                }
                if (leftHandCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_HAND_VALUE;
                    sideeffectValue += ",";
                }
                if (leftShoulderCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_SHOULDER_VALUE;
                    sideeffectValue += ",";
                }
                if (leftChestCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_CHEST_VALUE;
                    sideeffectValue += ",";
                }
                if (leftArmCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_ARM_VALUE;
                    sideeffectValue += ",";
                }
                if (leftHipCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_HIP_VALUE;
                    sideeffectValue += ",";
                }
                if (leftUpperLegCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_UPPER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (leftKneeCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_KNEE_VALUE;
                    sideeffectValue += ",";
                }
                if (leftLowerLegCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_LOWER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (leftFootCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_FOOT_VALUE;
                    sideeffectValue += ",";
                }
                if (headCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_HEAD_VALUE;
                    sideeffectValue += ",";
                }
                if (neckCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_NECK_VALUE;
                    sideeffectValue += ",";
                }
                if (upperBackCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_UPPER_BACK_VALUE;
                    sideeffectValue += ",";
                }
                if (midBackCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_MID_BACK_VALUE;
                    sideeffectValue += ",";
                }
                if (lowerBackCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LOWER_BACK_VALUE;
                    sideeffectValue += ",";
                }
                if (rightAbdomenCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_ABDOMEN_VALUE;
                    sideeffectValue += ",";
                }
                if (leftAbdomenCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_ABDOMEN_VALUE;
                    sideeffectValue += ",";
                }
                if (tailboneCheckbox.isChecked()){
                    sideeffectValue += SIDEEFFECT_PAIN_TAILBONE_VALUE;
                    sideeffectValue += ",";
                }
                saveSideeffect(sideeffectType, sideeffectValue);
            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                popupWindow.dismiss();
            }
        });

    }

    public void createSideeffectAppetite(final String sideeffectType) {


        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.journal_sideeffect_appetite_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        final Button    buttonSave              = (Button) popupView.findViewById(R.id.btn_journal_sideeffect_appetite_save);
        final Button    buttonCancel            = (Button) popupView.findViewById(R.id.btn_journal_sideeffect_appetite_cancel);
        final TextView  textSideeffectsHeader   = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_popup_appetite_headline);
        final TextView  textSideeffectQuestion  = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_popup_appetite_question);

        final SeekBar  seekBar1                 = (SeekBar)  popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar1);
        final SeekBar  seekBar2                 = (SeekBar)  popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar2);
        final SeekBar  seekBar3                 = (SeekBar)  popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar3);
        final TextView textSideeffectsHeader1   = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_headline1);
        final TextView textSideeffectsHeader2   = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_headline2);
        final TextView textSideeffectsHeader3   = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_headline3);
        final TextView textSeekBarMin1          = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_min1);
        final TextView textSeekBarMin2          = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_min2);
        final TextView textSeekBarMin3          = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_min3);
        final TextView textSeekBarMax1          = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_max1);
        final TextView textSeekBarMax2          = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_max2);
        final TextView textSeekBarMax3          = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_max3);
        final TextView textSeekBarResult1       = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_result1);
        final TextView textSeekBarResult2       = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_result2);
        final TextView textSeekBarResult3       = (TextView) popupView.findViewById(R.id.txt_journal_sideeffect_appetite_seekbar_result3);

        final RelativeLayout seekbarLayout2     = (RelativeLayout) popupView.findViewById(R.id.lay_journal_sideeffect_seekbar2);
        final RelativeLayout seekbarLayout3     = (RelativeLayout) popupView.findViewById(R.id.lay_journal_sideeffect_seekbar3);

        // Initialize seekbar popup
        switch(sideeffectType){
            case SIDEEFFECT_TYPE_APPETITE:
                textSideeffectsHeader.setText(R.string.journal_sideeffects_appetite);
                textSideeffectQuestion.setText(R.string.journal_sideeffects_appetite_question);
                textSideeffectsHeader1.setText(R.string.journal_sideeffects_appetite_seekbar_headline1);
                textSeekBarMin1.setText(R.string.journal_sideeffects_appetite_seekbar_min1);
                textSeekBarMax1.setText(R.string.journal_sideeffects_appetite_seekbar_min1);
                textSideeffectsHeader2.setText(R.string.journal_sideeffects_appetite_seekbar_headline2);
                textSeekBarMin2.setText(R.string.journal_sideeffects_appetite_seekbar_min2);
                textSeekBarMax2.setText(R.string.journal_sideeffects_appetite_seekbar_min2);
                textSideeffectsHeader3.setText(R.string.journal_sideeffects_appetite_seekbar_headline3);
                textSeekBarMin3.setText(R.string.journal_sideeffects_appetite_seekbar_min3);
                textSeekBarMax3.setText(R.string.journal_sideeffects_appetite_seekbar_min3);
                break;
            case SIDEEFFECT_TYPE_FATIGUE:
                textSideeffectsHeader.setText(R.string.journal_sideeffects_fatigue);
                seekbarLayout2.removeAllViews();
                seekbarLayout3.removeAllViews();
                textSideeffectQuestion.setText(R.string.journal_sideeffects_fatigue_question);
                textSideeffectsHeader1.setVisibility(View.INVISIBLE);
                textSeekBarMin1.setText(R.string.journal_sideeffects_fatigue_seekbar_min1);
                textSeekBarMax1.setText(R.string.journal_sideeffects_fatigue_seekbar_max1);
                break;
        }

        // Replace X with patient name
        textSideeffectQuestion.setText(textSideeffectQuestion.getText().toString().replace("*", connectHandler.patient.patient_name));

        // Get position for todays sideeffect
        String sideeffectValueString = null;
        switch(sideeffectType){
            case SIDEEFFECT_TYPE_APPETITE:
                if (appetiteIdForToday >= 0){
                    sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(appetiteIdForToday).value;
                    // Populate the seekbar values for the existing side effect
                    //Separate Breakfast, lunch, dinner and convert string values to integer and set progress default
                    String[] parts = sideeffectValueString.split(",");
                    Integer value1 = Integer.parseInt(parts[0].substring(parts[0].indexOf(":") + 1));
                    seekBar1.setProgress(value1);
                    textSeekBarResult1.setText(value1.toString());
                    Integer value2 = Integer.parseInt(parts[1].substring(parts[1].indexOf(":") + 1));
                    seekBar2.setProgress(value2);
                    textSeekBarResult2.setText(value2.toString());
                    Integer value3 = Integer.parseInt(parts[2].substring(parts[2].indexOf(":") + 1));
                    seekBar3.setProgress(value3);
                    textSeekBarResult3.setText(value3.toString());
                }
                break;
            case SIDEEFFECT_TYPE_FATIGUE:
                if (fatigueIdForToday >= 0){
                    sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(fatigueIdForToday).value;
                    seekBar1.setProgress(Integer.parseInt(sideeffectValueString));
                    textSeekBarResult1.setText(sideeffectValueString);
                }
                break;
        }

        popupWindow.setFocusable(true);
        popupWindow.update();
        RelativeLayout relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_journal_sideeffect_appetite_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                Integer progress = progresValue;
                textSeekBarResult1.setText(progress.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                Integer progress = progresValue;
                textSeekBarResult2.setText(progress.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                Integer progress = progresValue;
                textSeekBarResult3.setText(progress.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancel.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                saveSideeffectAppetite(sideeffectType);
                popupWindow.dismiss();
            }

            private void saveSideeffectAppetite(String sideeffectType) {
                String sideeffectValue = "";
                switch(sideeffectType){
                    case SIDEEFFECT_TYPE_APPETITE:
                        sideeffectValue = "Breakfast:"+seekBar1.getProgress()+",Lunch:"+seekBar2.getProgress()+",Dinner:"+seekBar3.getProgress();
                        break;
                    case SIDEEFFECT_TYPE_FATIGUE:
                        sideeffectValue += seekBar1.getProgress();
                        break;
                }
                saveSideeffect(sideeffectType, sideeffectValue);
            }
        });

    }

    public void createSideeffectDiarrhea(final String sideeffectType) {


        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.journal_sideeffect_diarrhea_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        final Button        buttonSave              = (Button) popupView.findViewById(R.id.btn_journal_sideeffects_diarrhea_save);
        final Button        buttonCancel            = (Button) popupView.findViewById(R.id.btn_journal_sideeffects_diarrhea_cancel);
        final TextView      textSideeffectsHeader   = (TextView) popupView.findViewById(R.id.txt_journal_sideeffects_diarrhea_popup_headline);
        final TextView      textSideeffectQuestion  = (TextView) popupView.findViewById(R.id.txt_journal_sideeffects_diarrhea_popup_question);
        final RadioGroup    radioGroup              = (RadioGroup) popupView.findViewById(R.id.rg_journal_sideeffects_dirrhea_popup);
        final RadioButton   radioButton1            = (RadioButton) popupView.findViewById(R.id.rb_journal_sideeffects_diarrhea_popup_selection1);
        final RadioButton   radioButton2            = (RadioButton) popupView.findViewById(R.id.rb_journal_sideeffects_diarrhea_popup_selection2);
        final RadioButton   radioButton3            = (RadioButton) popupView.findViewById(R.id.rb_journal_sideeffects_diarrhea_popup_selection3);
        final RadioButton   radioButton4            = (RadioButton) popupView.findViewById(R.id.rb_journal_sideeffects_diarrhea_popup_selection4);
        final RadioButton   radioButton5            = (RadioButton) popupView.findViewById(R.id.rb_journal_sideeffects_diarrhea_popup_selection5);

        // Initialize seekbar popup
        switch(sideeffectType){
            case SIDEEFFECT_TYPE_DIARRHEA:
                textSideeffectsHeader.setText(R.string.journal_sideeffects_diarrhea);
                textSideeffectQuestion.setText(R.string.journal_sideeffects_diarrhea_question);
                radioButton1.setText(R.string.journal_sideeffects_diarrhea_radioButton1);
                radioButton2.setText(R.string.journal_sideeffects_diarrhea_radioButton2);
                radioButton3.setText(R.string.journal_sideeffects_diarrhea_radioButton3);
                radioButton4.setText(R.string.journal_sideeffects_diarrhea_radioButton4);
                radioButton5.setText(R.string.journal_sideeffects_diarrhea_radioButton5);
                break;
            case SIDEEFFECT_TYPE_MOUTH:
                textSideeffectsHeader.setText(R.string.journal_sideeffects_mouth_change_header);
                textSideeffectQuestion.setText(R.string.journal_sideeffects_mouth_change_question);
                radioButton1.setText(R.string.journal_sideeffects_mouth_change_radioButton1);
                radioButton2.setText(R.string.journal_sideeffects_mouth_change_radioButton2);
                radioButton3.setText(R.string.journal_sideeffects_mouth_change_radioButton3);
                radioButton4.setText(R.string.journal_sideeffects_mouth_change_radioButton4);
                radioButton5.setVisibility(View.INVISIBLE);
                break;
            case SIDEEFFECT_TYPE_VOMIT:
                textSideeffectsHeader.setText(R.string.journal_sideeffects_vomit_header);
                textSideeffectQuestion.setText(R.string.journal_sideeffects_vomit_question);
                radioButton1.setText(R.string.journal_sideeffects_vomit_radioButton1);
                radioButton2.setText(R.string.journal_sideeffects_vomit_radioButton2);
                radioButton3.setText(R.string.journal_sideeffects_vomit_radioButton3);
                radioButton4.setVisibility(View.INVISIBLE);
                radioButton5.setVisibility(View.INVISIBLE);
                break;
            case SIDEEFFECT_TYPE_DIZZINESS:
                textSideeffectsHeader.setText(R.string.journal_sideeffects_dizziness_header);
                textSideeffectQuestion.setText(R.string.journal_sideeffects_dizziness_question);
                radioButton1.setText(R.string.journal_sideeffects_dizziness_radioButton1);
                radioButton2.setText(R.string.journal_sideeffects_dizziness_radioButton2);
                radioButton3.setVisibility(View.INVISIBLE);
                radioButton4.setVisibility(View.INVISIBLE);
                radioButton5.setVisibility(View.INVISIBLE);
                break;
        }

        // Replace X with patient name
        textSideeffectQuestion.setText(textSideeffectQuestion.getText().toString().replace("*", connectHandler.patient.patient_name));

        // if sideeffect exist, initalise the saved checkbox values
        String sideeffectValueString = null;

        // Get position for todays sideeffect
        switch(sideeffectType){
            case SIDEEFFECT_TYPE_DIARRHEA:
                if (diarrheaIdForToday >= 0){
                    sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(diarrheaIdForToday).value;
                }
                break;
            case SIDEEFFECT_TYPE_MOUTH:
                if (mouthIdForToday >= 0){
                    sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(mouthIdForToday).value;
                }
                break;
            case SIDEEFFECT_TYPE_VOMIT:
                if (vomitIdForToday >= 0){
                    sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(vomitIdForToday).value;
                }
                break;
            case SIDEEFFECT_TYPE_DIZZINESS:
                if (dizzinessIdForToday >= 0){
                    sideeffectValueString = connectHandler.sideeffects.sideeffect_data.get(dizzinessIdForToday).value;
                }
                break;
        }

        if (sideeffectValueString != null){
            switch(Integer.parseInt(sideeffectValueString)){
                case 1:
                    radioButton1.setChecked(true);
                    break;
                case 2:
                    radioButton2.setChecked(true);
                    break;
                case 3:
                    radioButton3.setChecked(true);
                    break;
                case 4:
                    radioButton4.setChecked(true);
                    break;
                case 5:
                    radioButton5.setChecked(true);
                    break;
            }
        }
        popupWindow.setFocusable(true);
        popupWindow.update();
        RelativeLayout relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_journal_sideeffect_diarrhea_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sideeffectValue = "";
                Integer radioButtonPosition = 0;
                int selectedId=radioGroup.getCheckedRadioButtonId();
                //Get which option was selected
                if (selectedId == radioButton1.getId()){
//                    radioButtonPosition = 1;
                    sideeffectValue += "1";
                } else if (selectedId == radioButton2.getId()){
                    sideeffectValue += "2";
                } else if (selectedId == radioButton3.getId()){
                    sideeffectValue += "3";
                } else if (selectedId == radioButton4.getId()){
                    sideeffectValue += "4";
                } else if (selectedId == radioButton5.getId()){
                    sideeffectValue += "5";
                }

                buttonCancel.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                saveSideeffect(sideeffectType, sideeffectValue);
                popupWindow.dismiss();
            }

        });

    }

    private boolean checkIfDateIsToday(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'");
        Date date = null;
        long milliseconds = 0;
        try {
            date  = format.parse(dateString);
        } catch (ParseException e){
            System.out.println("Failure when parsing the dateString");
        }
        if (date != null){
            milliseconds = date.getTime();
        }
        return DateUtils.isToday(milliseconds);
    }

    private void findSideeffectsForToday(){

        // Initalise values if changes has happened to the list
        fatigueIdForToday = -1;
        painIdForToday = -1;
        mouthIdForToday = -1;
        tinglingIdForToday = -1;
        diarrheaIdForToday = -1;
        appetiteIdForToday = -1;
        dizzinessIdForToday = -1;
        vomitIdForToday = -1;
        otherIdForToday = -1;
        fatigueButton.getBackground().setTint(getColor(R.color.button_material_light));
        painButton.getBackground().setTint(getColor(R.color.button_material_light));
        mouthButton.getBackground().setTint(getColor(R.color.button_material_light));
        tinglingButton.getBackground().setTint(getColor(R.color.button_material_light));
        diarrheaButton.getBackground().setTint(getColor(R.color.button_material_light));
        appetiteButton.getBackground().setTint(getColor(R.color.button_material_light));
        vomitButton.getBackground().setTint(getColor(R.color.button_material_light));
        otherButton.getBackground().setTint(getColor(R.color.button_material_light));

        if (connectHandler.sideeffects.sideeffect_data != null){
            for (int position=0; position < connectHandler.sideeffects.sideeffect_data.size(); position++){
                boolean dateIsToday = false;
                try {
                    dateIsToday = checkIfDateIsToday(connectHandler.sideeffects.sideeffect_data.get(position).date);
                } catch (ParseException e){}
                if (dateIsToday){
                    switch (connectHandler.sideeffects.sideeffect_data.get(position).type){
                        case SIDEEFFECT_TYPE_FATIGUE:
//                        fatigueIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            fatigueIdForToday = position;
                            fatigueButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_PAIN:
//                        painIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            painIdForToday = position;
                            painButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_MOUTH:
//                        mouthIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            mouthIdForToday = position;
                            mouthButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_TINGLING:
//                        tinglingIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            tinglingIdForToday = position;
                            tinglingButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_DIARRHEA:
//                        diarrheaIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            diarrheaIdForToday = position;
                            diarrheaButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_APPETITE:
//                        appetiteIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            appetiteIdForToday = position;
                            appetiteButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_DIZZINESS:
//                        dizzinessIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            dizzinessIdForToday = position;
                            dizzinessButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_VOMIT:
//                        vomitIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            vomitIdForToday = position;
                            vomitButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                        case SIDEEFFECT_TYPE_OTHER:
//                        otherIdForToday = connectHandler.sideeffects.sideeffect_data.get(i).sideeffect_ID;
                            otherIdForToday = position;
                            otherButton.getBackground().setTint(getColor(R.color.cme_light));
                            break;
                    }

                }
            }
        }
    }

    private void saveSideeffect(String sideeffectType, String sideeffectValue){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("kk:mm:ss").format(new Date());

        Sideeffect newSideeffect = new Sideeffect(
                0,
                connectHandler.patient.patient_ID,
                connectHandler.person.person_ID,
                date,
                time,
                sideeffectType,
                sideeffectValue);

        connectHandler.createSideeffect(newSideeffect);
        while (connectHandler.socketBusy){}

        // update existing sideeffects, better solution, where only todays sideeffects are read TBD
        connectHandler.getSideeffectForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}
        findSideeffectsForToday();
        // and voila :)

    }
    private void careTeam(){
        Intent myIntent = new Intent(this, CareTeamActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void journeyActivity(){
        Intent myIntent = new Intent(this, JourneyActivity.class);
        startActivity(myIntent);
        finish();
    }

}
