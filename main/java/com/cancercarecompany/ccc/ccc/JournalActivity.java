package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

    public static final String TIME_SIMPLE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_SIMPLE_FORMAT = "kk:mm:ss";

    public static final String SIDEEFFECT_PAIN_RIGHT_HAND_VALUE = "RHA";
    public static final String SIDEEFFECT_PAIN_RIGHT_SHOULDER_VALUE = "RSH";
    public static final String SIDEEFFECT_PAIN_RIGHT_CHEST_VALUE = "RCH";
    public static final String SIDEEFFECT_PAIN_RIGHT_ARM_VALUE = "RAR";
    public static final String SIDEEFFECT_PAIN_RIGHT_HIP_VALUE = "RHI";
    public static final String SIDEEFFECT_PAIN_RIGHT_UPPER_LEG_VALUE = "RUL";
    public static final String SIDEEFFECT_PAIN_RIGHT_KNEE_VALUE = "RKN";
    public static final String SIDEEFFECT_PAIN_RIGHT_LOWER_LEG_VALUE = "RLL";
    public static final String SIDEEFFECT_PAIN_RIGHT_FOOT_VALUE = "RFO";
    public static final String SIDEEFFECT_PAIN_LEFT_HAND_VALUE = "LHA";
    public static final String SIDEEFFECT_PAIN_LEFT_SHOULDER_VALUE = "LSH";
    public static final String SIDEEFFECT_PAIN_LEFT_CHEST_VALUE = "LCH";
    public static final String SIDEEFFECT_PAIN_LEFT_ARM_VALUE = "LAR";
    public static final String SIDEEFFECT_PAIN_LEFT_HIP_VALUE = "LHI";
    public static final String SIDEEFFECT_PAIN_LEFT_UPPER_LEG_VALUE = "LUL";
    public static final String SIDEEFFECT_PAIN_LEFT_KNEE_VALUE = "LKN";
    public static final String SIDEEFFECT_PAIN_LEFT_LOWER_LEG_VALUE = "LLL";
    public static final String SIDEEFFECT_PAIN_LEFT_FOOT_VALUE = "LFO";
    public static final String SIDEEFFECT_PAIN_HEAD_VALUE = "HEA";
    public static final String SIDEEFFECT_PAIN_NECK_VALUE = "NEC";
    public static final String SIDEEFFECT_PAIN_UPPER_BACK_VALUE = "UBA";
    public static final String SIDEEFFECT_PAIN_MID_BACK_VALUE = "MBA";
    public static final String SIDEEFFECT_PAIN_LOWER_BACK_VALUE = "LBA";
    public static final String SIDEEFFECT_PAIN_RIGHT_ABDOMEN_VALUE = "RAB";
    public static final String SIDEEFFECT_PAIN_LEFT_ABDOMEN_VALUE = "LAB";
    public static final String SIDEEFFECT_PAIN_TAILBONE_VALUE = "TAI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        final EditText statusTextEditText = (EditText) findViewById(R.id.edtxt_journal_status);
        final Button saveStatusButton = (Button)    findViewById(R.id.btn_journal_status_save);
        final Button fatigueButton = (Button)    findViewById(R.id.btn_journal_sideeffect_fatigue);
        final Button painButton = (Button)    findViewById(R.id.btn_journal_sideeffect_pain);
        final Button mouthButton = (Button)    findViewById(R.id.btn_journal_sideeffect_mouth);
        final Button numbnessButton = (Button)    findViewById(R.id.btn_tingling);
        final Button diarrheaButton = (Button)    findViewById(R.id.btn_journal_sideeffect_diarrhea);
        final Button appetitButton = (Button)    findViewById(R.id.btn_journal_sideeffect_appetite);
        final Button dizzinessButton = (Button)    findViewById(R.id.btn_journal_sideeffect_dizziness);
        final Button vomitButton = (Button)    findViewById(R.id.btn_journal_sideeffect_vomit);
        final Button medBreakfastButton = (Button)    findViewById(R.id.btn_journal_medication_breakfast);
        final Button medLunchButton = (Button)    findViewById(R.id.btn_journal_medication_lunch);
        final Button medDinnerButton = (Button)    findViewById(R.id.btn_journal_medication_dinner);
        final TextView txt_med_txt    = (TextView)  findViewById(R.id.txt_med_int);
        final TextView txt_diary_head = (TextView)  findViewById(R.id.txt_journal_header);
        final CalendarView calendar = (CalendarView)  findViewById(R.id.cal_journal_calendar);
        final ImageButton journeyButton    = (ImageButton) findViewById(R.id.btn_journal_journey_button);
        final ImageButton careTeamButton = (ImageButton) findViewById(R.id.btn_journal_careteam_button);
        //Get journal data
        connectHandler = ConnectionHandler.getInstance();
        connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}
        connectHandler.getStatusForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

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

        statusGridView = (GridView) findViewById(R.id.gridview_journal_status);
        statusAdapter = new JournalStatusAdapter(this, statusList);
        statusGridView.setAdapter(statusAdapter);

        for (int i = 0; i < 8 ; i++) {
            beverageList.add("empty");
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
                            "happy",
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
                add_fatigue("Fat");
            }
        });

        painButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSideeffectPain();
            }
        });

        numbnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_Events();
            }
        });

        appetitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = "Apetite";
                add_fatigue(choice);
            }
        });

        dizzinessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Yrsel");
            }
        });

        diarrheaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Forstopp");
            }
        });

        vomitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Krakn");
            }
        });

        medBreakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fr_mark.equals(Boolean.FALSE)) {
                    fr_mark = Boolean.TRUE;
                    medBreakfastButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    fr_mark = Boolean.FALSE;
                    medBreakfastButton.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }

        });


        medLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( lu_mark.equals(Boolean.FALSE)) {
                    lu_mark = Boolean.TRUE;
                    medLunchButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    lu_mark = Boolean.FALSE;
                    medLunchButton.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }
        });

        medDinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mi_mark.equals(Boolean.FALSE)) {
                    mi_mark = Boolean.TRUE;
                    medDinnerButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    mi_mark = Boolean.FALSE;
                    medDinnerButton.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
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
                txt_diary_head.setText(R.string.txt_journal_headline);
                txt_diary_head.setText((txt_diary_head.getText()) + " " + year + "-" + month + "-" + date);
            }
        });
    }

    private void careTeam(){
        Intent myIntent = new Intent(this, CareTeamActivity.class);
        startActivity(myIntent);
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

    public void createSideeffectPain() {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.journal_sideeffect_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final CheckBox rightHandCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_hand);
        final CheckBox rightShoulderCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_shoulder);
        final CheckBox rightChestCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_chest);
        final CheckBox rightArmCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_arm);
        final CheckBox rightHipCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_hip);
        final CheckBox rightUpperLegCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_upper_leg);
        final CheckBox rightKneeCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_knee);
        final CheckBox rightLowerLegCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_lower_leg);
        final CheckBox rightFootCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_foot);
        final CheckBox leftHandCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_hand);
        final CheckBox leftShoulderCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_shoulder);
        final CheckBox leftChestCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_chest);
        final CheckBox leftArmCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_arm);
        final CheckBox leftHipCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_hip);
        final CheckBox leftUpperLegCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_upper_leg);
        final CheckBox leftKneeCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_knee);
        final CheckBox leftLowerLegCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_lower_leg);
        final CheckBox leftFootCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_foot);
        final CheckBox headCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_head);
        final CheckBox neckCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_neck);
        final CheckBox uppderBackCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_upper_back);
        final CheckBox midBackCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_mid_back);
        final CheckBox lowerBackCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_lower_back);
        final CheckBox rightAbdomenCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_right_abdomen);
        final CheckBox leftAbdomenCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_left_abdomen);
        final CheckBox tailboneCheckbox = (CheckBox) popupView.findViewById(R.id.checkBox_journal_sideeffect_tailbone);

        final Button    buttonSave           = (Button) popupView.findViewById(R.id.btn_journal_status_save);
        final Button    buttonCancel         = (Button) popupView.findViewById(R.id.btn_journal_status_cancel);

        buttonCancel.setVisibility(View.VISIBLE);
        buttonSave.setVisibility(View.VISIBLE);

        RelativeLayout relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_journal_sideeffects_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancel.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                saveSideeffectPain();
                popupWindow.dismiss();
            }

            private void saveSideeffectPain() {

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
                if (uppderBackCheckbox.isChecked()){
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

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String time = new SimpleDateFormat("kk:mm:ss").format(new Date());

                Sideeffect newSideeffect = new Sideeffect(
                        0,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        date,
                        time,
                        "pain",
                        sideeffectValue);

                connectHandler.createSideeffect(newSideeffect);

                while (connectHandler.socketBusy){}
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

    public void add_fatigue(String choice) {
        popup_add_fatigue(choice);
    }

    public void popup_add_fatigue(final String choice) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.biv_slider_0_10_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 2000);


        final Button   btn_cancel    = (Button) popupView.findViewById(R.id.btn_cancel_popup);
        final TextView txt_header    = (TextView) popupView.findViewById(R.id.txt_biv_slider_head);
        final TextView txt_subheader = (TextView) popupView.findViewById(R.id.txt_biv_slider_subhead);

        final SeekBar  seek_fat   = (SeekBar)  popupView.findViewById(R.id.seek_fat);
        final SeekBar  seek_fat2  = (SeekBar)  popupView.findViewById(R.id.seek_fat2);
        final SeekBar  seek_fat3  = (SeekBar)  popupView.findViewById(R.id.seek_fat3);
        final TextView text_min   = (TextView) popupView.findViewById(R.id.txt_seek_min);
        final TextView text_min2  = (TextView) popupView.findViewById(R.id.txt_seek_min2);
        final TextView text_min3  = (TextView) popupView.findViewById(R.id.txt_seek_min3);
        final TextView text_res   = (TextView) popupView.findViewById(R.id.txt_seek_res);
        final TextView text_res2  = (TextView) popupView.findViewById(R.id.txt_seek_res2);
        final TextView text_res3  = (TextView) popupView.findViewById(R.id.txt_seek_res3);
        final TextView text_max   = (TextView) popupView.findViewById(R.id.txt_seek_max);
        final TextView text_max2  = (TextView) popupView.findViewById(R.id.txt_seek_max2);
        final TextView text_max3  = (TextView) popupView.findViewById(R.id.txt_seek_max3);


        if (choice == "Fat") {
            txt_header.setText(R.string.txt_biv_fat_head);
            txt_subheader.setText(R.string.txt_biv_fat_desc);
            text_min.setText(R.string.txt_biv_seek_min);
            text_max.setText(R.string.txt_biv_seek_max);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);

        } else if (choice == "Pain") {
            txt_header.setText(R.string.txt_biv_pain_head);
            txt_subheader.setText(R.string.txt_biv_pain_desc);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);

        } else if (choice == "Apetite") {
            txt_header.setText(R.string.txt_biv_apetite_head);
            txt_subheader.setText(R.string.txt_biv_apetite_desc);

            text_max.setText(R.string.txt_biv_seek_apetite_max);
            text_min.setText(R.string.txt_biv_seek_apetite_min);
            text_max2.setText(R.string.txt_biv_seek_apetite_max);
            text_min2.setText(R.string.txt_biv_seek_apetite_min);
            text_max3.setText(R.string.txt_biv_seek_apetite_max);
            text_min3.setText(R.string.txt_biv_seek_apetite_min);

            seek_fat2.setVisibility(View.VISIBLE);
            text_max2.setVisibility(View.VISIBLE);
            text_res2.setVisibility(View.VISIBLE);
            text_min2.setVisibility(View.VISIBLE);
            seek_fat3.setVisibility(View.VISIBLE);
            text_max3.setVisibility(View.VISIBLE);
            text_res3.setVisibility(View.VISIBLE);
            text_min3.setVisibility(View.VISIBLE);
            text_res.setText(R.string.journal_medication_breakfast);
            text_res2.setText(R.string.journal_medication_lunch);
            text_res3.setText(R.string.journal_medication_dinner);
        } else if ( choice == "Yrsel") {
            txt_header.setText(R.string.txt_biv_yrsel_head);
            txt_subheader.setText(R.string.txt_biv_yrsel_desc);
            text_min.setText(R.string.txt_biv_seek_y_min);
            text_max.setText(R.string.txt_biv_seek_y_max);
            text_res.setText(R.string.txt_biv_seek_y_res);
            seek_fat.setMax(2);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);
        } else if ( choice == "Forstopp") {
            txt_header.setText(R.string.txt_biv_forstopp_head);
            txt_subheader.setText(R.string.txt_biv_forstopp_desc);
            text_min.setText(R.string.txt_biv_seek_fst_min);
            text_res.setText(R.string.txt_biv_seek_fst_res);
            text_max.setText(R.string.txt_biv_seek_fst_max);
            seek_fat.setMax(2);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);
        } else if ( choice == "Krakn") {
            txt_header.setText(R.string.txt_biv_krakn_head);
            txt_subheader.setText(R.string.txt_biv_krakn_desc);
            text_min.setText(R.string.txt_biv_seek_krk_min);
            text_max.setText(R.string.txt_biv_seek_krk_max);
            text_res.setText(R.string.txt_biv_seek_krk_med);

            seek_fat.setMax(2);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);
        }

        popupWindow.setFocusable(true);
        popupWindow.update();

        seek_fat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if ( choice == "Apetite") {
                    text_res.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else if ( choice == "Yrsel") {
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else if ( choice == "Krakn") {
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else if ( choice == "Forstopp") {
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else {
                    text_res.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seek_fat2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choice == "Apetite") {
                    text_res2.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else {
                    text_res2.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seek_fat3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choice == "Apetite") {
                    text_res3.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else {
                    text_res3.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_lay_scr);
        popupWindow.showAsDropDown(relativeLayout, 500, -1100);
    }

    /*   public void add_yrsel(String choice) {
            popup_add_yrsel(choice);
            //popup_add_fatigue("Pain");
        }
    */
    public void popup_add_yrsel(String choice) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.biv_slider_0_10_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 2000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_lay_scr);
        popupWindow.showAsDropDown(relativeLayout, 500, -1100);
    }

    private void journeyActivity(){
        Intent myIntent = new Intent(this, JourneyActivity.class);
        startActivity(myIntent);
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

    private void get_Events(){
/*
        Socket socketClass = new Socket();
       // Person newUser = new Person(0, null, null, emailLogin.getText().toString(), passwordLogin.getText().toString());
        Event events = new Event(0, lcl.patients.get(0).patient_ID, null, null, null, null, null, null, null);
//        Event events = new Event(0, 2, null, null, null, null, null, null, null, null);

        mSocket = socketClass.events(events);
        while (socketClass.lcl == null){
            System.out.println("tom");
        }
        lcl = socketClass.lcl;
        System.out.println(lcl);

        Gson gson = new Gson();
        String jsonPerson = gson.toJson(lcl);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("event",jsonPerson);
        editor.apply();

        Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
        startActivity(myIntent);
*/
    }

}
