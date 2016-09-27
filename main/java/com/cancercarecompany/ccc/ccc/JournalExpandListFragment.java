package com.cancercarecompany.ccc.ccc;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class JournalExpandListFragment extends Fragment {


    TextView journalHeaderText;
    ExpandableListAdapter expandListAdapter;
    ExpandableListView expandListView;

    List<SideeffectExpandListItem> emotionalExpandList;
    List<SideeffectExpandListItem> physicalExpandList;
    List<String> familyExpandList;
    List<String> practicalExpandList;

    List<String> listDataHeader;
    HashMap<String, List<SideeffectExpandListItem>> listDataChild;
    ConnectionHandler connectHandler;

    private int calendarDays;
    private String journalDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal_exp_list, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(true);
        }

        connectHandler = ConnectionHandler.getInstance();
        final ImageButton dateBackButton = (ImageButton) view.findViewById(R.id.img_journal_navigate_back);
        final ImageButton dateForwardButton = (ImageButton) view.findViewById(R.id.img_journal_navigate_forward);
        final ImageButton calendarButton = (ImageButton) view.findViewById(R.id.img_calendar);
        final RelativeLayout calendarLayout = (RelativeLayout) view.findViewById(R.id.layout_journal_calendar);
        journalHeaderText = (TextView) view.findViewById(R.id.txt_journal_date);
        expandListView = (ExpandableListView) view.findViewById(R.id.explv_journal);
        emotionalExpandList = new ArrayList<SideeffectExpandListItem>();
        physicalExpandList = new ArrayList<SideeffectExpandListItem>();
        journalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        journalHeaderText.setText(journalDate);
        final CalendarView calendar = (CalendarView) view.findViewById(R.id.cal_journal_calendar);

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<SideeffectExpandListItem>>();

        listDataHeader.add(getResources().getString(R.string.sideeffect_physical));
        listDataHeader.add(getResources().getString(R.string.sideeffect_emotional));

        prepareExpList();

        listDataChild.put(listDataHeader.get(0), physicalExpandList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), emotionalExpandList);

        expandListAdapter = new JournalExpandListAdapter(this.getContext(), listDataHeader, listDataChild);
        expandListView.setAdapter(expandListAdapter);

        expandListView.expandGroup(0);
        expandListView.expandGroup(1);

        // Listview on child click listener
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupi, int childi, long id) {

                // Begin the transaction
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                JournalDetailsFragment myJournalDetails = new JournalDetailsFragment();

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    ft.replace(R.id.journal_placeholder2, myJournalDetails);
                }
                else{
                    ft.replace(R.id.journal_placeholder1, myJournalDetails);
                }
                ft.addToBackStack(null);
                // send family member data to fragment
                switch (groupi){
                    case 0:
                        myJournalDetails.setItem(physicalExpandList.get(childi));
                        break;
                    case 1:
                        myJournalDetails.setItem(emotionalExpandList.get(childi));
                        break;
                }

                ft.commit();
                return false;
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarLayout.getVisibility() == View.VISIBLE){
                    calendarLayout.setVisibility(View.GONE);
                }else{
                    calendarLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int date) {
                journalHeaderText.setText(R.string.txt_journal_headline);
                month += 1;
                journalDate = year + "-" +String.format("%02d",month) +"-" +String.format("%02d",date);
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    journalHeaderText.setText(journalDate);
                } else{
                    journalHeaderText.setText(journalHeaderText.getText().toString().concat(" ".concat(journalDate)));
                }
            }
        });


        dateBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDays -= 1;
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, calendarDays);
                calendar.setDate (cal.getTimeInMillis(), true, true);
                journalDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    journalHeaderText.setText(journalDate);
                } else{
                    journalHeaderText.setText(journalHeaderText.getText().toString().concat(" ".concat(journalDate)));
                }
            }
        });

        dateForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDays += 1;
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, calendarDays);
                calendar.setDate (cal.getTimeInMillis(), true, true);
                journalDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    journalHeaderText.setText(journalDate);
                } else{
                    journalHeaderText.setText(journalHeaderText.getText().toString().concat(" ".concat(journalDate)));
                }
            }
        });

        return view;
    }

    private void prepareExpList(){

        List<String> physicalSideEffects = Arrays.asList(
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_BREATHING, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_URINATION, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_FEVER, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_NAUSEA, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_NOSE, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_PAIN, "string", getActivity().getPackageName())),
//                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_SEX, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_ITCH, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP, "string", getActivity().getPackageName())),
//                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_ABUSE, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING, "string", getActivity().getPackageName()))
        );

        List<String> emotionalSideeffects = Arrays.asList(
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_EMOTIONAL_DEPRESSION, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_EMOTIONAL_FEAR, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_EMOTIONAL_NERVOUS, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_EMOTIONAL_DEJECTION, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_EMOTIONAL_WORRY, "string", getActivity().getPackageName())),
                getString(getActivity().getResources().getIdentifier("sideeffect_"+JournalFragment.SIDEEFFECT_EMOTIONAL_LOSS, "string", getActivity().getPackageName()))
        );


        // find todays sideeffects
        connectHandler.getSideeffectForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

        ArrayList<Sideeffect> todaysSideeffects = new ArrayList<Sideeffect>();

        // Hopefully I can remove this when I have a method that gets only this day sideeffects
        if (connectHandler.sideeffects.sideeffect_data != null) {
            for (int i = 0; i < connectHandler.sideeffects.sideeffect_data.size(); i++) {
                boolean dateIsToday = false;
                try {
                    dateIsToday = matchDate(journalHeaderText.getText().toString(), connectHandler.sideeffects.sideeffect_data.get(i).date);
                } catch (ParseException e) {
                }
                if (dateIsToday) {
                    todaysSideeffects.add(connectHandler.sideeffects.sideeffect_data.get(i));
                }
            }
        }

        Collections.sort(physicalSideEffects, Collator.getInstance(new Locale("sv")));
        Collections.sort(physicalSideEffects, Collator.getInstance(new Locale("sv")));

        int todaysSideffectPosition;

        SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem();
        for (int i=0; i < physicalSideEffects.size() ; i++){
            todaysSideffectPosition = -1;
/*            for (int j=0; j < todaysSideeffects.size(); j++){
                if
                String mString = getString(getActivity().getResources().getIdentifier("sideeffect_"+todaysSideeffects.get(j).type, "string", getActivity().getPackageName()));
                if (mString.matches(physicalSideEffects.get(i))){
                    // Sideeffect has already saved value from today
                    todaysSideffectPosition = j;
                    break;
                }
            }
*/            sideeffectItem.headline = physicalSideEffects.get(i);
            Sideeffect sideeffect = null;
            if (todaysSideffectPosition >=0) {
                sideeffect = new Sideeffect(todaysSideeffects.get(todaysSideffectPosition).sideeffect_ID,
                                            connectHandler.patient.patient_ID,
                                            connectHandler.person.person_ID,
                                            todaysSideeffects.get(todaysSideffectPosition).date,
                                            todaysSideeffects.get(todaysSideffectPosition).time,
                                            todaysSideeffects.get(todaysSideffectPosition).type,
                                            todaysSideeffects.get(todaysSideffectPosition).value);
            }else{
                // create empty Sideeffect object
                sideeffect = new Sideeffect(0,
                                            connectHandler.patient.patient_ID,
                                            connectHandler.person.person_ID,
                                            "",
                                            "",
                                            physicalSideEffects.get(i),
                                            "");
            }
            sideeffectItem.headline = emotionalSideeffects.get(i);
            sideeffectItem.sideeffect = sideeffect;
            physicalExpandList.add(sideeffectItem);
        }

        for (int i=0; i < emotionalSideeffects.size() ; i++){
            todaysSideffectPosition = -1;
            for (int j=0; j < todaysSideeffects.size(); j++){
                if (todaysSideeffects.get(j).type.matches(emotionalSideeffects.get(i))){
                    // Sideeffect has already saved value from today
                    todaysSideffectPosition = j;
                    break;
                }
            }
            Sideeffect sideeffect = null;
            if (todaysSideffectPosition >=0) {
                sideeffect = new Sideeffect(todaysSideeffects.get(todaysSideffectPosition).sideeffect_ID,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        todaysSideeffects.get(todaysSideffectPosition).date,
                        todaysSideeffects.get(todaysSideffectPosition).time,
                        todaysSideeffects.get(todaysSideffectPosition).type,
                        todaysSideeffects.get(todaysSideffectPosition).value);
            }else{
                // create empty Sideeffect object
                sideeffect = new Sideeffect(0,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        "",
                        "",
                        emotionalSideeffects.get(i),
                        "");
            }
            sideeffectItem.headline = emotionalSideeffects.get(i);
            emotionalExpandList.add(sideeffectItem);
        }

    }

    private boolean matchDate(String targetDateString , String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = null;
        Date date = null;
        long milliseconds = 0;
        dateString = dateString.split("T")[0];

        try {
            targetDate = format.parse(targetDateString);
        } catch (ParseException e) {
            System.out.println("Failure when parsing the targetDateString");
        }
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Failure when parsing the dateString");
        }
        if ((date != null) && (targetDate != null)) {
            return (targetDate.getTime() == date.getTime());
        } else {
            return false;
        }
    }

}