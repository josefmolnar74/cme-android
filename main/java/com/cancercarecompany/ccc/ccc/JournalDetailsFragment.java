package com.cancercarecompany.ccc.ccc;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class JournalDetailsFragment extends Fragment {

    private SideeffectExpandListItem listItem;
    private ConnectionHandler connectHandler;



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

        // Declare Seekbar views
        LinearLayout seekbarsLayout = (LinearLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbars);
        RelativeLayout seekbarLayout1 = (RelativeLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbar1);
        RelativeLayout seekbarLayout2= (RelativeLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbar2);
        RelativeLayout seekbarLayout3 = (RelativeLayout) view.findViewById(R.id.lay_journal_sideeffect_seekbar3);

        final TextView sideeffectQuestion = (TextView) view.findViewById(R.id.txt_journal_sideeffect_question);
        final TextView seekBarHeadline1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_headline1);
        final TextView seekBarHeadline2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_headline2);
        final TextView seekBarHeadline3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_headline3);
        final SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.sb_journal_sideeffect_seekbar1);
        final SeekBar seekBar2 = (SeekBar) view.findViewById(R.id.sb_journal_sideeffect_seekbar2);
        final SeekBar seekBar3 = (SeekBar) view.findViewById(R.id.sb_journal_sideeffect_seekbar3);
        final TextView seekBarMin1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_min1);
        final TextView seekBarMax1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_max1);
        final TextView seekBarMin2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_min2);
        final TextView seekBarMax2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_max2);
        final TextView seekBarMin3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_min3);
        final TextView seekBarMax3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_max3);
        final TextView seekBarResult1 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_result1);
        final TextView seekBarResult2 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_result2);
        final TextView seekBarResult3 = (TextView) view.findViewById(R.id.txt_journal_sideeffect_seekbar_result3);

        seekBarResult1.setVisibility(View.GONE);
        seekBarResult2.setVisibility(View.GONE);
        seekBarResult3.setVisibility(View.GONE);

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
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE:
                seekbarsLayout.setVisibility(View.VISIBLE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_appetite_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_appetite_seekbar_headline1));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_appetite_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_appetite_seekbar_max));
                seekBarHeadline2.setText(getString(R.string.journal_sideeffects_appetite_seekbar_headline2));
                seekBarMin2.setText(getString(R.string.journal_sideeffects_appetite_seekbar_min));
                seekBarMax2.setText(getString(R.string.journal_sideeffects_appetite_seekbar_max));
                seekBarHeadline3.setText(getString(R.string.journal_sideeffects_appetite_seekbar_headline3));
                seekBarMin3.setText(getString(R.string.journal_sideeffects_appetite_seekbar_min));
                seekBarMax3.setText(getString(R.string.journal_sideeffects_appetite_seekbar_max));

                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.GONE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_fatigue_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_fatigue_seekbar_headline));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_fatigue_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_fatigue_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.GONE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_bloated_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_bloated_seekbar_headline));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_bloated_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_bloated_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_FEVER:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.GONE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_mobility_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_mobility_seekbar_headline));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_mobility_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_mobility_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.VISIBLE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_memory_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_memory_seekbar_headline1));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_memory_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_memory_seekbar_max));
                seekBarHeadline2.setText(getString(R.string.journal_sideeffects_memory_seekbar_headline2));
                seekBarMin2.setText(getString(R.string.journal_sideeffects_memory_seekbar_min));
                seekBarMax2.setText(getString(R.string.journal_sideeffects_memory_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_NAUSEA:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_NOSE:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.VISIBLE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_nose_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_nose_seekbar_headline1));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_nose_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_nose_seekbar_max));
                seekBarHeadline2.setText(getString(R.string.journal_sideeffects_nose_seekbar_headline2));
                seekBarMin2.setText(getString(R.string.journal_sideeffects_nose_seekbar_min));
                seekBarMax2.setText(getString(R.string.journal_sideeffects_nose_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_PAIN:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_SEX:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.VISIBLE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_dermal_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_dermal_seekbar_headline1));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_dermal_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_dermal_seekbar_max));
                seekBarHeadline2.setText(getString(R.string.journal_sideeffects_dermal_seekbar_headline2));
                seekBarMin2.setText(getString(R.string.journal_sideeffects_dermal_seekbar_min));
                seekBarMax2.setText(getString(R.string.journal_sideeffects_dermal_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP:
                seekbarsLayout.setVisibility(View.VISIBLE);
                seekbarLayout2.setVisibility(View.GONE);
                seekbarLayout3.setVisibility(View.GONE);
                sideeffectQuestion.setText(getString(R.string.journal_sideeffects_sleep_question));
                seekBarHeadline1.setText(getString(R.string.journal_sideeffects_sleep_seekbar_headline));
                seekBarMin1.setText(getString(R.string.journal_sideeffects_sleep_seekbar_min));
                seekBarMax1.setText(getString(R.string.journal_sideeffects_sleep_seekbar_max));
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_ABUSE:
                break;

            case JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING:
                break;

        }

        // Replace x with patient name in Question text
        sideeffectQuestion.setText(sideeffectQuestion.getText().toString().replace("*", connectHandler.patient.patient_name));

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

    public void setItem(SideeffectExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}