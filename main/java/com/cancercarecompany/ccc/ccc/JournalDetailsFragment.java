package com.cancercarecompany.ccc.ccc;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalDetailsFragment extends Fragment {

    private static final String SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_1 = "breakfast";
    private static final String SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_2 = "lunch";
    private static final String SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_3 = "dinner";
    private static final String SIDEEFFECT_MEMORY_SEEKBAR_HEADLINE_1 = "memory";
    private static final String SIDEEFFECT_MEMORY_SEEKBAR_HEADLINE_2 = "concentration";
    private static final String SIDEEFFECT_DERMAL_SEEKBAR_HEADLINE_1 = "dry_skin";
    private static final String SIDEEFFECT_DERMAL_SEEKBAR_HEADLINE_2 = "itch";
    private static final String SIDEEFFECT_NOSE_SEEKBAR_HEADLINE_1 = "dry_nose";
    private static final String SIDEEFFECT_NOSE_SEEKBAR_HEADLINE_2 = "blocked_nose";

    private static final String SIDEEFFECT_MOUTH_CHECKBOX_VALUE1 = "sore";
    private static final String SIDEEFFECT_MOUTH_CHECKBOX_VALUE2 = "dry";
    private static final String SIDEEFFECT_MOUTH_CHECKBOX_VALUE3 = "taste";
    private static final String SIDEEFFECT_MOUTH_CHECKBOX_VALUE4 = "pain";

    private static final String SIDEEFFECT_TINGLING_CHECKBOX_VALUE1 = "hands";
    private static final String SIDEEFFECT_TINGLING_CHECKBOX_VALUE2 = "feet";

    private static final String SIDEEFFECT_PAIN_RIGHT_HAND_VALUE         = "RHA";
    private static final String SIDEEFFECT_PAIN_RIGHT_SHOULDER_VALUE     = "RSH";
    private static final String SIDEEFFECT_PAIN_RIGHT_CHEST_VALUE        = "RCH";
    private static final String SIDEEFFECT_PAIN_RIGHT_ARM_VALUE          = "RAR";
    private static final String SIDEEFFECT_PAIN_RIGHT_HIP_VALUE          = "RHI";
    private static final String SIDEEFFECT_PAIN_RIGHT_UPPER_LEG_VALUE    = "RUL";
    private static final String SIDEEFFECT_PAIN_RIGHT_KNEE_VALUE         = "RKN";
    private static final String SIDEEFFECT_PAIN_RIGHT_LOWER_LEG_VALUE    = "RLL";
    private static final String SIDEEFFECT_PAIN_RIGHT_FOOT_VALUE         = "RFO";
    private static final String SIDEEFFECT_PAIN_LEFT_HAND_VALUE          = "LHA";
    private static final String SIDEEFFECT_PAIN_LEFT_SHOULDER_VALUE      = "LSH";
    private static final String SIDEEFFECT_PAIN_LEFT_CHEST_VALUE         = "LCH";
    private static final String SIDEEFFECT_PAIN_LEFT_ARM_VALUE           = "LAR";
    private static final String SIDEEFFECT_PAIN_LEFT_HIP_VALUE           = "LHI";
    private static final String SIDEEFFECT_PAIN_LEFT_UPPER_LEG_VALUE     = "LUL";
    private static final String SIDEEFFECT_PAIN_LEFT_KNEE_VALUE          = "LKN";
    private static final String SIDEEFFECT_PAIN_LEFT_LOWER_LEG_VALUE     = "LLL";
    private static final String SIDEEFFECT_PAIN_LEFT_FOOT_VALUE          = "LFO";
    private static final String SIDEEFFECT_PAIN_HEAD_VALUE               = "HEA";
    private static final String SIDEEFFECT_PAIN_NECK_VALUE               = "NEC";
    private static final String SIDEEFFECT_PAIN_UPPER_BACK_VALUE         = "UBA";
    private static final String SIDEEFFECT_PAIN_MID_BACK_VALUE           = "MBA";
    private static final String SIDEEFFECT_PAIN_LOWER_BACK_VALUE         = "LBA";
    private static final String SIDEEFFECT_PAIN_RIGHT_ABDOMEN_VALUE      = "RAB";
    private static final String SIDEEFFECT_PAIN_LEFT_ABDOMEN_VALUE       = "LAB";
    private static final String SIDEEFFECT_PAIN_TAILBONE_VALUE           = "TAI";

    private LinearLayout seekBarsLayout;
    private RelativeLayout seekBarLayout1;
    private RelativeLayout seekBarLayout2;
    private RelativeLayout seekBarLayout3;
    private LinearLayout checkBoxGridLayout;
    private LinearLayout radioGroupLayout;
    private LinearLayout checkBoxLayout;
    private ImageButton saveButton;
    private ImageButton deleteButton;
    private EditText sideeffectNotes;

    private TextView sideeffectQuestion;
    private TextView sideeffectQuestion2;
    private TextView seekBarHeadline1;
    private TextView seekBarHeadline2;
    private TextView seekBarHeadline3;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private TextView seekBarMin1;
    private TextView seekBarMax1;
    private TextView seekBarMin2;
    private TextView seekBarMax2;
    private TextView seekBarMin3;
    private TextView seekBarMax3;
    private TextView seekBarResult1;
    private TextView seekBarResult2;
    private TextView seekBarResult3;

    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;

    private CheckBox rightHandCheckbox;
    private CheckBox rightShoulderCheckbox;
    private CheckBox rightChestCheckbox;
    private CheckBox rightArmCheckbox;
    private CheckBox rightHipCheckbox;
    private CheckBox rightUpperLegCheckbox;
    private CheckBox rightKneeCheckbox;
    private CheckBox rightLowerLegCheckbox;
    private CheckBox rightFootCheckbox;
    private CheckBox leftHandCheckbox;
    private CheckBox leftShoulderCheckbox;
    private CheckBox leftChestCheckbox;
    private CheckBox leftArmCheckbox;
    private CheckBox leftHipCheckbox;
    private CheckBox leftUpperLegCheckbox;
    private CheckBox leftKneeCheckbox;
    private CheckBox leftLowerLegCheckbox;
    private CheckBox leftFootCheckbox;
    private CheckBox headCheckbox;
    private CheckBox neckCheckbox;
    private CheckBox upperBackCheckbox;
    private CheckBox midBackCheckbox;
    private CheckBox lowerBackCheckbox;
    private CheckBox rightAbdomenCheckbox;
    private CheckBox leftAbdomenCheckbox;
    private CheckBox tailboneCheckbox;
    private Button showHistoryButton;
    private EditText healthDataValue;

    private JournalExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_journal_details, container, false);

        connectHandler = ConnectionHandler.getInstance();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setHasOptionsMenu(true);
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle(listItem.header);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lay_journal_detail);
            layout.setVisibility(View.GONE);
        }

        ((MainActivity) getActivity()).setTitle(connectHandler.patient.patient_name.concat(getString(R.string.patient_journey)));

        // Declare Seekbar views
        seekBarsLayout = (LinearLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbars);
        seekBarLayout1 = (RelativeLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbar1);
        seekBarLayout2 = (RelativeLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbar2);
        seekBarLayout3 = (RelativeLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbar3);
        checkBoxGridLayout = (LinearLayout) view.findViewById(R.id.lay_journal_sideeffect_checkbox_grid);
        radioGroupLayout = (LinearLayout) view.findViewById(R.id.lay_journal_sideeffect_radiogroup);
        checkBoxLayout = (LinearLayout) view.findViewById(R.id.lay_journal_sideeffect_checkbox);

        saveButton = (ImageButton) view.findViewById(R.id.btn_save);
        deleteButton = (ImageButton) view.findViewById(R.id.btn_delete);
        sideeffectNotes = (EditText) view.findViewById(R.id.txt_journal_notes);

        sideeffectQuestion = (TextView) view.findViewById(R.id.txt_journal_sideeffect_question);
        sideeffectQuestion2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_question2);
        seekBarHeadline1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_headline1);
        seekBarHeadline2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_headline2);
        seekBarHeadline3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_headline3);
        seekBar1 = (SeekBar) view.findViewById(R.id.sb_journal_sideeffect_seekbar1);
        seekBar2 = (SeekBar) view.findViewById(R.id.sb_journal_sideeffect_seekbar2);
        seekBar3 = (SeekBar) view.findViewById(R.id.sb_journal_sideeffect_seekbar3);
        seekBarMin1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_min1);
        seekBarMax1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_max1);
        seekBarMin2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_min2);
        seekBarMax2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_max2);
        seekBarMin3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_min3);
        seekBarMax3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_max3);
        seekBarResult1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_result1);
        seekBarResult2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_result2);
        seekBarResult3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_result3);

        radioGroup = (RadioGroup) view.findViewById(R.id.rg_journal_sideeffect_radiogroup);
        radioButton1 = (RadioButton) view.findViewById(R.id.rb_journal_sideeffect_radiobutton1);
        radioButton2 = (RadioButton) view.findViewById(R.id.rb_journal_sideeffect_radiobutton2);
        radioButton3 = (RadioButton) view.findViewById(R.id.rb_journal_sideeffect_radiobutton3);
        radioButton4 = (RadioButton) view.findViewById(R.id.rb_journal_sideeffect_radiobutton4);
        radioButton5 = (RadioButton) view.findViewById(R.id.rb_journal_sideeffect_radiobutton5);

        checkBox1 = (CheckBox) view.findViewById(R.id.cb_journal_sideeffect_checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.cb_journal_sideeffect_checkbox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.cb_journal_sideeffect_checkbox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.cb_journal_sideeffect_checkbox4);

        rightHandCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_hand);
        rightShoulderCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_shoulder);
        rightChestCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_chest);
        rightArmCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_arm);
        rightHipCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_hip);
        rightUpperLegCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_upper_leg);
        rightKneeCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_knee);
        rightLowerLegCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_lower_leg);
        rightFootCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_foot);
        leftHandCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_hand);
        leftShoulderCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_shoulder);
        leftChestCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_chest);
        leftArmCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_arm);
        leftHipCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_hip);
        leftUpperLegCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_upper_leg);
        leftKneeCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_knee);
        leftLowerLegCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_lower_leg);
        leftFootCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_foot);
        headCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_head);
        neckCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_neck);
        upperBackCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_upper_back);
        midBackCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_mid_back);
        lowerBackCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_lower_back);
        rightAbdomenCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_right_abdomen);
        leftAbdomenCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_left_abdomen);
        tailboneCheckbox = (CheckBox) view.findViewById(R.id.checkBox_journal_sideeffect_tailbone);
        healthDataValue = (EditText) view.findViewById(R.id.text_journal_healthData_value);
        showHistoryButton = (Button) view.findViewById(R.id.btn_journal_detail_show_history);
        seekBarsLayout.setVisibility(View.VISIBLE);
        seekBarHeadline1.setVisibility(View.INVISIBLE);
        seekBarLayout2.setVisibility(View.GONE);
        seekBarLayout3.setVisibility(View.GONE);
        seekBarResult1.setVisibility(View.GONE);
        seekBarResult2.setVisibility(View.GONE);
        seekBarResult3.setVisibility(View.GONE);
        healthDataValue.setVisibility(View.GONE);

        InputFilter filter = new InputFilter() {
            final int maxDigitsBeforeDecimalPoint=3;
            final int maxDigitsAfterDecimalPoint=1;

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source
                        .subSequence(start, end).toString());
                if (!builder.toString().matches(
                        "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

                )) {
                    if(source.length()==0)
                        return dest.subSequence(dstart, dend);
                    return "";
                }

                return null;

            }
        };

        showHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItem.sideeffect!=null){
                    FragmentManager fm = getFragmentManager();
                    SideeffectDialogFragment dialogFragment = new SideeffectDialogFragment();
                    Bundle args = new Bundle();
                    args.putString(SideeffectDialogFragment.SIDEEFFECT_TYPE, listItem.sideeffect.type);
                    dialogFragment.setArguments(args);
                    dialogFragment.show(fm, "Josef");
                }else if (listItem.healthData!=null){
                    FragmentManager fm = getFragmentManager();
                    HealthDataDialogFragment dialogFragment = new HealthDataDialogFragment();
                    Bundle args = new Bundle();
                    args.putString(HealthDataDialogFragment.HEALTH_DATA_TYPE, listItem.healthData.type);
                    dialogFragment.setArguments(args);
                    dialogFragment.show(fm, "Josef");
                }
            }
        });

        if (listItem.sideeffect != null){
            sideeffectQuestion.setText(
                    getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ listItem.sideeffect.type +"_question", "string", getActivity().getPackageName())));
        }

        if (listItem.healthData != null){
            sideeffectQuestion.setText(
                    getString(getActivity().getResources().getIdentifier("journal_health_data_"+ listItem.healthData.type +"_question", "string", getActivity().getPackageName())));
            sideeffectNotes.setVisibility(View.GONE);
            healthDataValue.setVisibility(View.VISIBLE);
            seekBarLayout1.setVisibility(View.GONE);
            healthDataValue.setText(listItem.healthData.value);
            healthDataValue.setFilters(new InputFilter[] {filter});

        }

        if (listItem.sideeffect != null){
            if (!(listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_URINATION) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_PAIN) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_NOSE) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_DIZZINESS) ||
                    listItem.sideeffect.type.matches(JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE))){
/*            seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ listItem.sideeffect.type +"_seekbar_headline", "string", getActivity().getPackageName())));
                seekBarMin1.setText(
                        getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ listItem.sideeffect.type +"_seekbar_min", "string", getActivity().getPackageName())));
                seekBarMax1.setText(
                        getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ listItem.sideeffect.type +"_seekbar_max", "string", getActivity().getPackageName())));
*/
                initalizeSeekbars(String.valueOf(listItem.sideeffect.severity));
                seekBarMin1.setText(getString(R.string.journal_sideeffect_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffect_seekbar_max));
            }

            switch(listItem.sideeffect.type){

                case JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION:
//                    seekBarsLayout.setVisibility(View.GONE);
//                    seekBarHeadline1.setVisibility(View.GONE);
                    sideeffectQuestion2.setVisibility(View.VISIBLE);
                    sideeffectQuestion2.setText(getString(R.string.journal_sideeffect_physical_diarrhea_question2));
                    radioGroupLayout.setVisibility(View.VISIBLE);
                    radioButton1.setText(R.string.journal_sideeffect_physical_constipation_radioButton1);
                    radioButton2.setText(R.string.journal_sideeffect_physical_constipation_radioButton2);
                    radioButton3.setText(R.string.journal_sideeffect_physical_constipation_radioButton3);
                    radioButton4.setText(R.string.journal_sideeffect_physical_constipation_radioButton4);
                    radioButton5.setText(R.string.journal_sideeffect_physical_constipation_radioButton5);
                    initailizeRadioButton(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA:
//                    seekBarsLayout.setVisibility(View.GONE);
//                    seekBarHeadline1.setVisibility(View.GONE);
                    sideeffectQuestion2.setVisibility(View.VISIBLE);
                    sideeffectQuestion2.setText(getString(R.string.journal_sideeffect_physical_diarrhea_question2));
                    radioGroupLayout.setVisibility(View.VISIBLE);
                    radioButton1.setText(R.string.journal_sideeffect_physical_diarrhea_radioButton1);
                    radioButton2.setText(R.string.journal_sideeffect_physical_diarrhea_radioButton2);
                    radioButton3.setText(R.string.journal_sideeffect_physical_diarrhea_radioButton3);
                    radioButton4.setText(R.string.journal_sideeffect_physical_diarrhea_radioButton4);
                    radioButton5.setText(R.string.journal_sideeffect_physical_diarrhea_radioButton5);
                    initailizeRadioButton(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    sideeffectQuestion.setText(getString(R.string.journal_sideeffect_physical_appetite_question));
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_appetite_seekbar_"
                            + SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_1, "string", getActivity().getPackageName())));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_appetite_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_appetite_seekbar_max));
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_appetite_seekbar_"
                            + SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_2, "string", getActivity().getPackageName())));
                    seekBarMin2.setText(getString(R.string.journal_sideeffect_physical_appetite_seekbar_min));
                    seekBarMax2.setText(getString(R.string.journal_sideeffect_physical_appetite_seekbar_max));
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_appetite_seekbar_"
                            + SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_3, "string", getActivity().getPackageName())));
                    seekBarMin3.setText(getString(R.string.journal_sideeffect_physical_appetite_seekbar_min));
                    seekBarMax3.setText(getString(R.string.journal_sideeffect_physical_appetite_seekbar_max));
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE:
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED:
/*                    seekBarHeadline1.setText(getString(R.string.journal_sideeffect_physical_bloated_seekbar_headline));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_bloated_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_bloated_seekbar_max));
                    initalizeSeekbars(listItem.sideeffect.value);
*/                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY:
/*                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    seekBarHeadline1.setText(getString(R.string.journal_sideeffect_physical_mobility_seekbar_headline));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_mobility_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_mobility_seekbar_max));
*/                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY:
                    seekBarsLayout.setVisibility(View.VISIBLE);
/*                    seekBarLayout2.setVisibility(View.VISIBLE);
                    seekBarLayout3.setVisibility(View.GONE);
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_physical_memory_seekbar_"
                            + SIDEEFFECT_MEMORY_SEEKBAR_HEADLINE_1, "string", getActivity().getPackageName())));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_memory_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_memory_seekbar_max));
                    seekBarHeadline2.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_physical_memory_seekbar_"
                            + SIDEEFFECT_MEMORY_SEEKBAR_HEADLINE_2, "string", getActivity().getPackageName())));
                    seekBarMin2.setText(getString(R.string.journal_sideeffect_physical_memory_seekbar_min));
                    seekBarMax2.setText(getString(R.string.journal_sideeffect_physical_memory_seekbar_max));
                    initalizeSeekbars(listItem.sideeffect.value);
*/                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH:
                    checkBoxLayout.setVisibility(View.VISIBLE);
                    sideeffectQuestion2.setVisibility(View.VISIBLE);
                    sideeffectQuestion2.setText(getString(R.string.journal_sideeffect_physical_mouth_question2));
                    checkBox1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_mouth_checkbox_"
                            + SIDEEFFECT_MOUTH_CHECKBOX_VALUE1, "string", getActivity().getPackageName())));
                    checkBox2.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_mouth_checkbox_"
                            + SIDEEFFECT_MOUTH_CHECKBOX_VALUE2, "string", getActivity().getPackageName())));
                    checkBox3.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_mouth_checkbox_"
                            + SIDEEFFECT_MOUTH_CHECKBOX_VALUE3, "string", getActivity().getPackageName())));
                    checkBox4.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_mouth_checkbox_"
                            + SIDEEFFECT_MOUTH_CHECKBOX_VALUE4, "string", getActivity().getPackageName())));
                    initCheckBoxes();
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_VOMIT:
//                    seekBarsLayout.setVisibility(View.GONE);
                    sideeffectQuestion2.setVisibility(View.VISIBLE);
                    sideeffectQuestion2.setText(getString(R.string.journal_sideeffect_physical_vomit_question2));
                    radioGroupLayout.setVisibility(View.VISIBLE);
                    radioButton1.setText(R.string.journal_sideeffect_physical_vomit_radioButton1);
                    radioButton2.setText(R.string.journal_sideeffect_physical_vomit_radioButton2);
                    radioButton3.setText(R.string.journal_sideeffect_physical_vomit_radioButton3);
                    radioButton4.setVisibility(View.GONE);
                    radioButton5.setVisibility(View.GONE);
                    initailizeRadioButton(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_DIZZINESS:
                    radioGroupLayout.setVisibility(View.VISIBLE);
                    radioButton1.setText(R.string.journal_sideeffect_physical_dizziness_radioButton1);
                    radioButton2.setText(R.string.journal_sideeffect_physical_dizziness_radioButton2);
                    radioButton3.setVisibility(View.GONE);
                    radioButton4.setVisibility(View.GONE);
                    radioButton5.setVisibility(View.GONE);
                    initailizeRadioButton(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_NOSE:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.VISIBLE);
                    seekBarLayout3.setVisibility(View.GONE);
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_nose_seekbar_"
                            + SIDEEFFECT_NOSE_SEEKBAR_HEADLINE_1, "string", getActivity().getPackageName())));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_nose_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_nose_seekbar_max));
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_nose_seekbar_"
                            + SIDEEFFECT_NOSE_SEEKBAR_HEADLINE_1, "string", getActivity().getPackageName())));
                    seekBarMin2.setText(getString(R.string.journal_sideeffect_physical_nose_seekbar_min));
                    seekBarMax2.setText(getString(R.string.journal_sideeffect_physical_nose_seekbar_max));
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_PAIN:
                    seekBarsLayout.setVisibility(View.GONE);
                    checkBoxGridLayout.setVisibility(View.VISIBLE);
                    initCheckBoxes();
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.VISIBLE);
                    seekBarLayout3.setVisibility(View.GONE);
                    sideeffectQuestion.setText(getString(R.string.journal_sideeffect_physical_dermal_question));
                    seekBarHeadline1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_dermal_seekbar_"
                            + SIDEEFFECT_DERMAL_SEEKBAR_HEADLINE_1, "string", getActivity().getPackageName())));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_dermal_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_dermal_seekbar_max));
                    seekBarHeadline2.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_dermal_seekbar_"
                            + SIDEEFFECT_DERMAL_SEEKBAR_HEADLINE_1, "string", getActivity().getPackageName())));
                    seekBarMin2.setText(getString(R.string.journal_sideeffect_physical_dermal_seekbar_min));
                    seekBarMax2.setText(getString(R.string.journal_sideeffect_physical_dermal_seekbar_max));
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    seekBarHeadline1.setText(getString(R.string.journal_sideeffect_physical_sleep_seekbar_headline));
                    seekBarMin1.setText(getString(R.string.journal_sideeffect_physical_sleep_seekbar_min));
                    seekBarMax1.setText(getString(R.string.journal_sideeffect_physical_sleep_seekbar_max));
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING:
//                    seekBarsLayout.setVisibility(View.GONE);
//                    seekBarHeadline1.setVisibility(View.GONE);
                    sideeffectQuestion2.setVisibility(View.VISIBLE);
                    sideeffectQuestion2.setText(getString(R.string.journal_sideeffect_physical_tingling_question2));
                    checkBoxLayout.setVisibility(View.VISIBLE);
                    checkBox1.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_physical_tingling_checkbox_"
                            + SIDEEFFECT_TINGLING_CHECKBOX_VALUE1, "string", getActivity().getPackageName())));
                    checkBox2.setText(getString(getActivity().getResources().getIdentifier("journal_sideeffect_physical_tingling_checkbox_"
                            + SIDEEFFECT_TINGLING_CHECKBOX_VALUE2, "string", getActivity().getPackageName())));
                    checkBox3.setVisibility(View.GONE);
                    checkBox4.setVisibility(View.GONE);
                    initCheckBoxes();
                    break;

                case JournalFragment.SIDEEFFECT_EMOTIONAL_DEJECTION:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    sideeffectQuestion.setText(getString(R.string.journal_sideeffect_emotional_dejection_question));
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_EMOTIONAL_DEPRESSION:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    sideeffectQuestion.setText(getString(R.string.journal_sideeffect_emotional_depression_question));
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_EMOTIONAL_FEAR:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_EMOTIONAL_ACTIVITIES:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_EMOTIONAL_NERVOUS:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_EMOTIONAL_WORRY:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;

                case JournalFragment.SIDEEFFECT_DISTRESS:
                    seekBarsLayout.setVisibility(View.VISIBLE);
                    seekBarLayout2.setVisibility(View.GONE);
                    seekBarLayout3.setVisibility(View.GONE);
                    initalizeSeekbars(listItem.sideeffect.value);
                    break;
            }

            sideeffectNotes.setText(listItem.sideeffect.notes);

        }

        // Replace x with patient name in Question text
        sideeffectQuestion.setText(sideeffectQuestion.getText().toString().replace("*", connectHandler.patient.patient_name));

        // Replace x with patient name in Question text
        sideeffectQuestion2.setText(sideeffectQuestion2.getText().toString().replace("*", connectHandler.patient.patient_name));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listItem.sideeffect!=null){
                    saveSideeffect();
                }
                else if (listItem.healthData!=null){
                    saveHealthData();
                }
            }

        });


        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                Integer progress = progresValue;
                seekBarResult1.setText(progress.toString());
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
                seekBarResult2.setText(progress.toString());
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
                seekBarResult3.setText(progress.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {        // Inflate the menu; this adds items to the action bar if it is present.
        //only admin can save or delete
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
                deleteSideeffect();
            sideeffectNotes.setText("");
            return true;
        }

        if (id == R.id.action_save) {
            if (listItem.sideeffect!=null){
                saveSideeffect();
            }
            else if (listItem.healthData!=null){
                saveHealthData();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setItem(JournalExpandListItem selectedListItem){
        listItem = selectedListItem;
    }

    public void setDate(String date){
        selectedDate = date;
    }

    private void deleteSideeffect(){
        // Generate dialog to make sure user wants to delete
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String alertText = String.format(getString(R.string.remove_question));
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (listItem.sideeffect!=null){
                    connectHandler.deleteSideeffect(listItem.sideeffect.sideeffect_ID);
                }else if (listItem.healthData!=null){
                    connectHandler.deleteHealthData(listItem.healthData.healthdata_ID);
                }

                while(connectHandler.socketBusy){}
                getActivity().onBackPressed();
                // remove item from list and from database
                // removeItemFromList(listItem.event_ID);
            }
        });
        alertDialogBuilder.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void saveSideeffect(){

        String sideeffectValue = "";

        switch(listItem.sideeffect.type){

            case JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_HYGIENE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_BREATHING:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_URINATION:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION:
                sideeffectValue = getRadioButtonData();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA:
                sideeffectValue = getRadioButtonData();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE:
                sideeffectValue = SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_1 +":" + seekBar1.getProgress()
                        +"," +SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_2 +":" + seekBar2.getProgress()
                        + "," + SIDEEFFECT_APPETITE_SEEKBAR_HEADLINE_3 +":" + seekBar3.getProgress();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_FEVER:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY:
                sideeffectValue = SIDEEFFECT_MEMORY_SEEKBAR_HEADLINE_1 +":" + seekBar1.getProgress()
                        +"," +SIDEEFFECT_MEMORY_SEEKBAR_HEADLINE_2 +":" + seekBar2.getProgress();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH:
                if (checkBox1.isChecked()) {
                    sideeffectValue += SIDEEFFECT_MOUTH_CHECKBOX_VALUE1;
                    sideeffectValue += ",";
                }
                if (checkBox2.isChecked()) {
                    sideeffectValue += SIDEEFFECT_MOUTH_CHECKBOX_VALUE2;
                    sideeffectValue += ",";
                }
                if (checkBox3.isChecked()) {
                    sideeffectValue += SIDEEFFECT_MOUTH_CHECKBOX_VALUE3;
                    sideeffectValue += ",";
                }
                if (checkBox4.isChecked()) {
                    sideeffectValue += SIDEEFFECT_MOUTH_CHECKBOX_VALUE4;
                    sideeffectValue += ",";
                }
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_VOMIT:
                sideeffectValue = getRadioButtonData();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIZZINESS:
                sideeffectValue = getRadioButtonData();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_NOSE:
                sideeffectValue = SIDEEFFECT_NOSE_SEEKBAR_HEADLINE_1 +":" + seekBar1.getProgress()
                        +"," +SIDEEFFECT_NOSE_SEEKBAR_HEADLINE_2 +":" + seekBar2.getProgress();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_PAIN:
                if (rightHandCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_HAND_VALUE;
                    sideeffectValue += ",";
                }
                if (rightShoulderCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_SHOULDER_VALUE;
                    sideeffectValue += ",";
                }
                if (rightChestCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_CHEST_VALUE;
                    sideeffectValue += ",";
                }
                if (rightArmCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_ARM_VALUE;
                    sideeffectValue += ",";
                }
                if (rightHipCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_HIP_VALUE;
                    sideeffectValue += ",";
                }
                if (rightUpperLegCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_UPPER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (rightKneeCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_KNEE_VALUE;
                    sideeffectValue += ",";
                }
                if (rightLowerLegCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_LOWER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (rightFootCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_FOOT_VALUE;
                    sideeffectValue += ",";
                }
                if (leftHandCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_HAND_VALUE;
                    sideeffectValue += ",";
                }
                if (leftShoulderCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_SHOULDER_VALUE;
                    sideeffectValue += ",";
                }
                if (leftChestCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_CHEST_VALUE;
                    sideeffectValue += ",";
                }
                if (leftArmCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_ARM_VALUE;
                    sideeffectValue += ",";
                }
                if (leftHipCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_HIP_VALUE;
                    sideeffectValue += ",";
                }
                if (leftUpperLegCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_UPPER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (leftKneeCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_KNEE_VALUE;
                    sideeffectValue += ",";
                }
                if (leftLowerLegCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_LOWER_LEG_VALUE;
                    sideeffectValue += ",";
                }
                if (leftFootCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_FOOT_VALUE;
                    sideeffectValue += ",";
                }
                if (headCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_HEAD_VALUE;
                    sideeffectValue += ",";
                }
                if (neckCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_NECK_VALUE;
                    sideeffectValue += ",";
                }
                if (upperBackCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_UPPER_BACK_VALUE;
                    sideeffectValue += ",";
                }
                if (midBackCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_MID_BACK_VALUE;
                    sideeffectValue += ",";
                }
                if (lowerBackCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LOWER_BACK_VALUE;
                    sideeffectValue += ",";
                }
                if (rightAbdomenCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_RIGHT_ABDOMEN_VALUE;
                    sideeffectValue += ",";
                }
                if (leftAbdomenCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_LEFT_ABDOMEN_VALUE;
                    sideeffectValue += ",";
                }
                if (tailboneCheckbox.isChecked()) {
                    sideeffectValue += SIDEEFFECT_PAIN_TAILBONE_VALUE;
                    sideeffectValue += ",";
                }
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_SEX:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL:
                sideeffectValue = SIDEEFFECT_DERMAL_SEEKBAR_HEADLINE_1 +":" + seekBar1.getProgress()
                        +"," +SIDEEFFECT_DERMAL_SEEKBAR_HEADLINE_2 +":" + seekBar2.getProgress();
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_ABUSE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING:
                if (checkBox1.isChecked()) {
                    sideeffectValue += SIDEEFFECT_TINGLING_CHECKBOX_VALUE1;
                    sideeffectValue += ",";
                }
                if (checkBox2.isChecked()) {
                    sideeffectValue += SIDEEFFECT_TINGLING_CHECKBOX_VALUE2;
                    sideeffectValue += ",";
                }
                break;

            case JournalFragment.SIDEEFFECT_EMOTIONAL_DEJECTION:
                break;

            case JournalFragment.SIDEEFFECT_EMOTIONAL_DEPRESSION:
                break;

            case JournalFragment.SIDEEFFECT_EMOTIONAL_FEAR:
                break;

            case JournalFragment.SIDEEFFECT_EMOTIONAL_ACTIVITIES:
                break;

            case JournalFragment.SIDEEFFECT_EMOTIONAL_NERVOUS:
                break;

            case JournalFragment.SIDEEFFECT_EMOTIONAL_WORRY:
                break;

            case JournalFragment.SIDEEFFECT_DISTRESS:
                break;

        }
        listItem.sideeffect.severity = seekBar1.getProgress();
        listItem.sideeffect.notes = sideeffectNotes.getText().toString();

        // Save or update sideffect on database
        listItem.sideeffect.value = sideeffectValue;
        listItem.sideeffect.date = selectedDate;
        listItem.sideeffect.time = new SimpleDateFormat("kk:mm:ss").format(new Date());
        if (listItem.sideeffect.sideeffect_ID >= 0){
            // sideeffect already exist, just update
            connectHandler.updateSideeffect(listItem.sideeffect);
        } else {
            // new sideeffect
            connectHandler.createSideeffect(listItem.sideeffect);
        }

        while (connectHandler.socketBusy) {}
        getActivity().onBackPressed();

    }

    private void saveHealthData(){

        String sideeffectValue = "";

        listItem.healthData.value = healthDataValue.getText().toString();

        // Save or update healthData on database
        listItem.healthData.date = selectedDate;
        listItem.healthData.time = new SimpleDateFormat("kk:mm:ss").format(new Date());
        if (listItem.healthData.healthdata_ID >= 0){
            // healthData already exist, just update
            connectHandler.updateHealthData(listItem.healthData);
        } else {
            // new healthData
            connectHandler.createHealthData(listItem.healthData);
        }

        while (connectHandler.socketBusy) {}
        getActivity().onBackPressed();
    }

    private String getRadioButtonData(){
        String sideeffectValue = "";
        Integer radioButtonPosition = 0;
        int selectedId = radioGroup.getCheckedRadioButtonId();
        //Get which option was selected
        if (selectedId == radioButton1.getId()) {
            sideeffectValue += "1";
        } else if (selectedId == radioButton2.getId()) {
            sideeffectValue += "2";
        } else if (selectedId == radioButton3.getId()) {
            sideeffectValue += "3";
        } else if (selectedId == radioButton4.getId()) {
            sideeffectValue += "4";
        } else if (selectedId == radioButton5.getId()) {
            sideeffectValue += "5";
        }
        return sideeffectValue;
    }

    private void inputAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String alertText = String.format(getResources().getString(R.string.all_event_data_not_provided));
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void initailizeRadioButton(String sideeffectValue){
        if (!sideeffectValue.isEmpty()){
            switch (Integer.parseInt(sideeffectValue)) {
                case 0:
                    break;
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
    }

    private void initalizeSeekbars(String sideeffectValue){
        String[] parts = sideeffectValue.split(",");
        if ((parts.length > 0) && (!parts[0].isEmpty())){
            Integer value1 = Integer.parseInt(parts[0].substring(parts[0].indexOf(":") + 1));
            seekBar1.setProgress(value1);
            seekBarResult1.setText(value1.toString());
        }
        if ((parts.length > 1) && (!parts[1].isEmpty())){
            Integer value2 = Integer.parseInt(parts[1].substring(parts[1].indexOf(":") + 1));
            seekBar2.setProgress(value2);
            seekBarResult2.setText(value2.toString());
        }
        if ((parts.length > 2) && (!parts[2].isEmpty())){
            Integer value3 = Integer.parseInt(parts[2].substring(parts[2].indexOf(":") + 1));
            seekBar3.setProgress(value3);
            seekBarResult3.setText(value3.toString());
        }
    }

    private void initCheckBoxes(){
        String[] bodyAreasArray = listItem.sideeffect.value.split(",");
        switch(listItem.sideeffect.type){
            case JournalFragment.SIDEEFFECT_PHYSICAL_PAIN:
                for (String s : bodyAreasArray) {
                    switch (s) {
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
                            leftAbdomenCheckbox.setChecked(true);
                            break;
                        case SIDEEFFECT_PAIN_TAILBONE_VALUE:
                            tailboneCheckbox.setChecked(true);
                            break;
                    }
                }
                break;
            case JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH:
                for (String s : bodyAreasArray) {
                    switch (s) {
                        case SIDEEFFECT_MOUTH_CHECKBOX_VALUE1:
                            checkBox1.setChecked(true);
                            break;
                        case SIDEEFFECT_MOUTH_CHECKBOX_VALUE2:
                            checkBox2.setChecked(true);
                            break;
                        case SIDEEFFECT_MOUTH_CHECKBOX_VALUE3:
                            checkBox3.setChecked(true);
                            break;
                        case SIDEEFFECT_MOUTH_CHECKBOX_VALUE4:
                            checkBox4.setChecked(true);
                            break;
                    }
                }
                break;
            case JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING:
                for (String s : bodyAreasArray) {
                    switch (s) {
                        case SIDEEFFECT_TINGLING_CHECKBOX_VALUE1:
                            checkBox1.setChecked(true);
                            break;
                        case SIDEEFFECT_TINGLING_CHECKBOX_VALUE2:
                            checkBox2.setChecked(true);
                            break;
                    }
                }
                break;
        }
    }


}