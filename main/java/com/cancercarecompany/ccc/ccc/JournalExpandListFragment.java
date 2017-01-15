package com.cancercarecompany.ccc.ccc;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalExpandListFragment extends Fragment{

    private UserLoginTask mAuthTask = null;
    private ConnectionHandler connectHandler;

    public ExpandableListAdapter expandListAdapter;
    public ExpandableListView expandListView;

    private List<JournalExpandListItem> healthDataExpandList;
    private List<JournalExpandListItem> emotionalExpandList;
    private List<JournalExpandListItem> physicalExpandList;
    private List<JournalExpandListItem> otherExpandList;
    private List<JournalExpandListItem> distressExpandList;

    private List<String> listDataHeader;
    private HashMap<String, List<JournalExpandListItem>> listDataChild;

    private int calendarDays;
    private String journalDate;
    private TextView journalHeaderText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal_exp_list, container, false);

        connectHandler = ConnectionHandler.getInstance();
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(true);
        }

        final ImageButton dateBackButton = (ImageButton) view.findViewById(R.id.img_journal_navigate_back);
        final ImageButton dateForwardButton = (ImageButton) view.findViewById(R.id.img_journal_navigate_forward);
        final ImageButton calendarButton = (ImageButton) view.findViewById(R.id.img_calendar);
        final Button historyButton = (Button) view.findViewById(R.id.btn_journal_show_history);
        final CalendarView calendar = (CalendarView) view.findViewById(R.id.cal_journal_calendar);
        calendar.setVisibility(View.GONE);
        journalHeaderText = (TextView) view.findViewById(R.id.txt_journal_date);
        expandListView = (ExpandableListView) view.findViewById(R.id.explv_journal);
        healthDataExpandList = new ArrayList<JournalExpandListItem>();
        emotionalExpandList = new ArrayList<JournalExpandListItem>();
        physicalExpandList = new ArrayList<JournalExpandListItem>();
        otherExpandList = new ArrayList<JournalExpandListItem>();
        distressExpandList = new ArrayList<JournalExpandListItem>();
        calendarDays = 0;
        journalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        journalHeaderText.setText(journalDate);

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<JournalExpandListItem>>();

        listDataHeader.add(getResources().getString(R.string.journal_health_data));
        listDataHeader.add(getResources().getString(R.string.journal_sideeffect_physical));
        listDataHeader.add(getResources().getString(R.string.journal_sideeffect_emotional));
//        listDataHeader.add(getResources().getString(R.string.journal_sideeffect_other));
//        listDataHeader.add(getResources().getString(R.string.sideeffect_distress));

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
                myJournalDetails.setDate(journalDate);

                switch (groupi){
                    case 0:
                        myJournalDetails.setItem(healthDataExpandList.get(childi));
                        break;
                    case 1:
                        myJournalDetails.setItem(physicalExpandList.get(childi));
                        break;
                    case 2:
                        myJournalDetails.setItem(emotionalExpandList.get(childi));
                        break;
                    case 3:
                        myJournalDetails.setItem(otherExpandList.get(childi));
                        break;
                }

                ft.commit();
                return false;
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendar.getVisibility() == View.VISIBLE){
                    calendar.setVisibility(View.GONE);
                }else{
                    calendar.setVisibility(View.VISIBLE);
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
                prepareEventsExpandList();
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
                prepareEventsExpandList();
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
                prepareEventsExpandList();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();

                JournalHistoryFragment myJournalHistory = new JournalHistoryFragment();

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    ft.replace(R.id.journal_placeholder2, myJournalHistory);
                }
                else{
                    ft.replace(R.id.journal_placeholder1, myJournalHistory);
                }
                ft.addToBackStack(null);
                // send family member data to fragment
//                myJournalDetails.setDate(journalDate);

                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if ((connectHandler.patient != null) && (connectHandler.sideeffects != null) && (connectHandler.healthData != null)){
            prepareEventsExpandList();
        }
    }

    private void prepareEventsExpandList(){

        List<String> healthDataList = Arrays.asList(
                JournalFragment.HEALTH_DATA_WEIGHT,
                JournalFragment.HEALTH_DATA_TEMPERATURE,
                JournalFragment.HEALTH_DATA_BLOODPRESSURE,
                JournalFragment.HEALTH_DATA_PULSE
        );

        List<String> physicalSideeffectList = Arrays.asList(
                JournalFragment.SIDEEFFECT_PHYSICAL_BREATHING,
                JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION,
                JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA,
                JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE,
                JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY,
                JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY,
                JournalFragment.SIDEEFFECT_PHYSICAL_CONCENTRATION,
                JournalFragment.SIDEEFFECT_PHYSICAL_VOMIT,
                JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP,
                JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING
        );

        List<String> otherSideeffectList = Arrays.asList(
                JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE,
                JournalFragment.SIDEEFFECT_PHYSICAL_URINATION,
                JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE,
                JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED,
                JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION,
                JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH,
                JournalFragment.SIDEEFFECT_PHYSICAL_DIZZINESS,
                JournalFragment.SIDEEFFECT_PHYSICAL_NOSE,
                JournalFragment.SIDEEFFECT_PHYSICAL_PAIN,
                JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL
        );

        List<String> emotionalSideeffectList = Arrays.asList(
                JournalFragment.SIDEEFFECT_EMOTIONAL_DEPRESSION,
                JournalFragment.SIDEEFFECT_EMOTIONAL_FEAR,
                JournalFragment.SIDEEFFECT_EMOTIONAL_NERVOUS,
                JournalFragment.SIDEEFFECT_EMOTIONAL_DEJECTION,
                JournalFragment.SIDEEFFECT_EMOTIONAL_WORRY,
                JournalFragment.SIDEEFFECT_EMOTIONAL_ACTIVITIES
        );

        List<String> distressSideeffectList = Arrays.asList(
                JournalFragment.SIDEEFFECT_DISTRESS
        );

        if (!healthDataExpandList.isEmpty()){
            healthDataExpandList.clear();
        }

        if (!physicalExpandList.isEmpty()){
            physicalExpandList.clear();
        }

        if (!otherExpandList.isEmpty()){
            otherExpandList.clear();
        }

        if (!emotionalExpandList.isEmpty()){
            emotionalExpandList.clear();
        }

        if (!distressExpandList.isEmpty()){
            distressExpandList.clear();
        }

        if (!listDataChild.isEmpty()){
            listDataChild.clear();
        }

        ArrayList<Sideeffect> todaysSideeffects = new ArrayList<Sideeffect>();
        ArrayList<HealthData> todaysHealthData = new ArrayList<HealthData>();

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

        // Hopefully I can remove this when I have a method that gets only this day sideeffects
        if ((connectHandler.healthData != null) && (connectHandler.healthData.healthdata_data != null)) {
            for (int i = 0; i < connectHandler.healthData.healthdata_data.size(); i++) {
                boolean dateIsToday = false;
                try {
                    dateIsToday = matchDate(journalHeaderText.getText().toString(), connectHandler.healthData.healthdata_data.get(i).date);
                } catch (ParseException e) {
                }
                if (dateIsToday) {
                    todaysHealthData.add(connectHandler.healthData.healthdata_data.get(i));
                }
            }
        }

        buildSideffectExpandList(todaysSideeffects, physicalSideeffectList, physicalExpandList);
        buildSideffectExpandList(todaysSideeffects, emotionalSideeffectList, emotionalExpandList);
        buildSideffectExpandList(todaysSideeffects, otherSideeffectList, otherExpandList);
        buildHealthDataExpandList(todaysHealthData, healthDataList, healthDataExpandList);

        listDataChild.put(listDataHeader.get(0), healthDataExpandList);
        listDataChild.put(listDataHeader.get(1), physicalExpandList);
        listDataChild.put(listDataHeader.get(2), emotionalExpandList);
//        listDataChild.put(listDataHeader.get(3), otherExpandList);
//        listDataChild.put(listDataHeader.get(3), distressExpandList);

        expandListAdapter = new JournalExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expandListView.setAdapter(expandListAdapter);
        expandListView.collapseGroup(0);
        expandListView.collapseGroup(1);
        expandListView.collapseGroup(2);
        expandListView.collapseGroup(3);
        expandListView.expandGroup(0);
        expandListView.expandGroup(1);
        expandListView.expandGroup(2);
//        expandListView.expandGroup(3);
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

    void buildSideffectExpandList(ArrayList<Sideeffect> todaysSideeffects, List<String> sideeffectList, List<JournalExpandListItem> expandList ){

        int todaysSideffectPosition;

        for (int i=0; i < sideeffectList.size() ; i++){
            todaysSideffectPosition = -1;
            for (int j=0; j < todaysSideeffects.size(); j++){
                if (todaysSideeffects.get(j).type.matches(sideeffectList.get(i))){
                    // Sideeffect has already saved value from today
                    todaysSideffectPosition = j;
                    break;
                }
            }

            if (todaysSideffectPosition >=0) {
                String header =
                        getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ sideeffectList.get(i), "string", getActivity().getPackageName()));

                Sideeffect sideeffect = new Sideeffect(todaysSideeffects.get(todaysSideffectPosition).sideeffect_ID,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        todaysSideeffects.get(todaysSideffectPosition).date,
                        todaysSideeffects.get(todaysSideffectPosition).time,
                        todaysSideeffects.get(todaysSideffectPosition).type,
                        todaysSideeffects.get(todaysSideffectPosition).severity,
                        todaysSideeffects.get(todaysSideffectPosition).value,
                        todaysSideeffects.get(todaysSideffectPosition).notes);
                JournalExpandListItem sideeffectItem = new JournalExpandListItem(header, sideeffect, null);
                expandList.add(sideeffectItem);

            }else{
                // create empty Sideeffect object
                String header =
                        getString(getActivity().getResources().getIdentifier("journal_sideeffect_"+ sideeffectList.get(i), "string", getActivity().getPackageName()));
                Sideeffect sideeffect = new Sideeffect(-1,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        "",
                        "",
                        sideeffectList.get(i),
                        0,
                        "",
                        "");
                JournalExpandListItem sideeffectItem = new JournalExpandListItem(header, sideeffect, null);
                expandList.add(sideeffectItem);
            }

        }

        Collections.sort(expandList, new Comparator<JournalExpandListItem>() {
            @Override
            public int compare(JournalExpandListItem o1, JournalExpandListItem o2) {
                return o1.header.compareTo(o2.header);
            }
        });
    }

    void buildHealthDataExpandList(ArrayList<HealthData> todaysHealthData, List<String> healthDataList, List<JournalExpandListItem> expandList ){

        int todaysHealthDataPosition;

        for (int i=0; i < healthDataList.size() ; i++){
            todaysHealthDataPosition = -1;
            for (int j=0; j < todaysHealthData.size(); j++){
                if (todaysHealthData.get(j).type.matches(healthDataList.get(i))){
                    // Sideeffect has already saved value from today
                    todaysHealthDataPosition = j;
                    break;
                }
            }

            if (todaysHealthDataPosition >=0) {
                String header =
                        getString(getActivity().getResources().getIdentifier("journal_health_data_"+ healthDataList.get(i), "string", getActivity().getPackageName()));

                HealthData healthData = new HealthData(todaysHealthData.get(todaysHealthDataPosition).healthdata_ID,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        todaysHealthData.get(todaysHealthDataPosition).date,
                        todaysHealthData.get(todaysHealthDataPosition).time,
                        todaysHealthData.get(todaysHealthDataPosition).type,
                        todaysHealthData.get(todaysHealthDataPosition).value);
                JournalExpandListItem healthDataItem = new JournalExpandListItem(header, null, healthData);
                expandList.add(healthDataItem);

            }else{
                // create empty Sideeffect object
                String header =
                        getString(getActivity().getResources().getIdentifier("journal_health_data_"+ healthDataList.get(i), "string", getActivity().getPackageName()));
                HealthData healthData = new HealthData(-1,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        "",
                        "",
                        healthDataList.get(i),
                        "");
                JournalExpandListItem healthDataItem = new JournalExpandListItem(header, null, healthData);
                expandList.add(healthDataItem);
            }

        }

        Collections.sort(expandList, new Comparator<JournalExpandListItem>() {
            @Override
            public int compare(JournalExpandListItem o1, JournalExpandListItem o2) {
                return o1.header.compareTo(o2.header);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle((connectHandler.patient.patient_name.concat(getString(R.string.patient_journey))));
    }

    public void updateExpList() {
        if (expandListAdapter != null){
            prepareEventsExpandList();
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            connectHandler.getSideeffectForPatient(connectHandler.patient.patient_ID);
            connectHandler.getHealthDataForPatient(connectHandler.patient.patient_ID);

            while(connectHandler.pendingMessage){}

            if ((connectHandler.patient != null) && (connectHandler.sideeffects != null) && (connectHandler.healthData != null)){
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