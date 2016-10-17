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
public class JournalExpandListFragment extends Fragment {


    TextView journalHeaderText;
    ExpandableListAdapter expandListAdapter;
    ExpandableListView expandListView;

    List<SideeffectExpandListItem> emotionalExpandList;
    List<SideeffectExpandListItem> physicalExpandList;
    List<SideeffectExpandListItem> distressExpandList;

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
        connectHandler = ConnectionHandler.getInstance();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(true);
        }

        // find todays sideeffects
        connectHandler.getSideeffectForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

        final ImageButton dateBackButton = (ImageButton) view.findViewById(R.id.img_journal_navigate_back);
        final ImageButton dateForwardButton = (ImageButton) view.findViewById(R.id.img_journal_navigate_forward);
        final ImageButton calendarButton = (ImageButton) view.findViewById(R.id.img_calendar);
        final RelativeLayout calendarLayout = (RelativeLayout) view.findViewById(R.id.layout_journal_calendar);
        journalHeaderText = (TextView) view.findViewById(R.id.txt_journal_date);
        expandListView = (ExpandableListView) view.findViewById(R.id.explv_journal);
        emotionalExpandList = new ArrayList<SideeffectExpandListItem>();
        physicalExpandList = new ArrayList<SideeffectExpandListItem>();
        distressExpandList = new ArrayList<SideeffectExpandListItem>();
        calendarDays = 0;
        journalDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        journalHeaderText.setText(journalDate);
        final CalendarView calendar = (CalendarView) view.findViewById(R.id.cal_journal_calendar);

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<SideeffectExpandListItem>>();

        listDataHeader.add(getResources().getString(R.string.sideeffect_physical));
        listDataHeader.add(getResources().getString(R.string.sideeffect_emotional));
        listDataHeader.add(getResources().getString(R.string.sideeffect_distress));

        prepareExpList();

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
                        myJournalDetails.setItem(distressExpandList.get(childi));
                        break;
                    case 1:
                        myJournalDetails.setItem(physicalExpandList.get(childi));
                        break;
                    case 2:
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
                prepareExpList();
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
                prepareExpList();
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
                prepareExpList();
            }
        });

        return view;
    }

    private void prepareExpList(){

        List<String> physicalSideeffectList = Arrays.asList(
                JournalFragment.SIDEEFFECT_PHYSICAL_APPERANCE,
                JournalFragment.SIDEEFFECT_PHYSICAL_BREATHING,
                JournalFragment.SIDEEFFECT_PHYSICAL_URINATION,
                JournalFragment.SIDEEFFECT_PHYSICAL_CONSTIPATION,
                JournalFragment.SIDEEFFECT_PHYSICAL_DIARRHEA,
                JournalFragment.SIDEEFFECT_PHYSICAL_APPETITE,
                JournalFragment.SIDEEFFECT_PHYSICAL_FATIGUE,
                JournalFragment.SIDEEFFECT_PHYSICAL_BLOATED,
                JournalFragment.SIDEEFFECT_PHYSICAL_FEVER,
                JournalFragment.SIDEEFFECT_PHYSICAL_MOBILITY,
                JournalFragment.SIDEEFFECT_PHYSICAL_DIGESTION,
                JournalFragment.SIDEEFFECT_PHYSICAL_MEMORY,
                JournalFragment.SIDEEFFECT_PHYSICAL_MOUTH,
//                JournalFragment.SIDEEFFECT_PHYSICAL_NAUSEA,
                JournalFragment.SIDEEFFECT_PHYSICAL_VOMIT,
                JournalFragment.SIDEEFFECT_PHYSICAL_DIZZINESS,
                JournalFragment.SIDEEFFECT_PHYSICAL_NOSE,
                JournalFragment.SIDEEFFECT_PHYSICAL_PAIN,
//              JournalFragment.SIDEEFFECT_PHYSICAL_SEX,
                JournalFragment.SIDEEFFECT_PHYSICAL_DERMAL,
                JournalFragment.SIDEEFFECT_PHYSICAL_SLEEP,
//              JournalFragment.SIDEEFFECT_PHYSICAL_ABUSE,
                JournalFragment.SIDEEFFECT_PHYSICAL_TINGLING
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

        if (!physicalExpandList.isEmpty()){
            physicalExpandList.clear();
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

        int todaysSideffectPosition;

        for (int i=0; i < physicalSideeffectList.size() ; i++){
            todaysSideffectPosition = -1;
            for (int j=0; j < todaysSideeffects.size(); j++){
                if (todaysSideeffects.get(j).type.matches(physicalSideeffectList.get(i))){
                    // Sideeffect has already saved value from today
                    todaysSideffectPosition = j;
                    break;
                }
            }

            if (todaysSideffectPosition >=0) {
                String header =
                        getString(getActivity().getResources().getIdentifier("sideeffect_"+ physicalSideeffectList.get(i), "string", getActivity().getPackageName()));

                Sideeffect sideeffect = new Sideeffect(todaysSideeffects.get(todaysSideffectPosition).sideeffect_ID,
                                            connectHandler.patient.patient_ID,
                                            connectHandler.person.person_ID,
                                            todaysSideeffects.get(todaysSideffectPosition).date,
                                            todaysSideeffects.get(todaysSideffectPosition).time,
                                            todaysSideeffects.get(todaysSideffectPosition).type,
                                            todaysSideeffects.get(todaysSideffectPosition).value,
                                            "");
                SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem(header, sideeffect);
                physicalExpandList.add(sideeffectItem);

            }else{
                // create empty Sideeffect object
                String header =
                        getString(getActivity().getResources().getIdentifier("sideeffect_"+ physicalSideeffectList.get(i), "string", getActivity().getPackageName()));
                Sideeffect sideeffect = new Sideeffect(-1,
                                            connectHandler.patient.patient_ID,
                                            connectHandler.person.person_ID,
                                            "",
                                            "",
                                            physicalSideeffectList.get(i),
                                            "",
                                            "");
                SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem(header, sideeffect);
                physicalExpandList.add(sideeffectItem);
            }

        }

        Collections.sort(physicalExpandList, new Comparator<SideeffectExpandListItem>() {
            @Override
            public int compare(SideeffectExpandListItem o1, SideeffectExpandListItem o2) {
                return o1.header.compareTo(o2.header);
            }
        });

        for (int i=0; i < emotionalSideeffectList.size() ; i++){
            todaysSideffectPosition = -1;
            for (int j=0; j < todaysSideeffects.size(); j++){
                if (todaysSideeffects.get(j).type.matches(emotionalSideeffectList.get(i))){
                    // Sideeffect has already saved value from today
                    todaysSideffectPosition = j;
                    break;
                }
            }
            if (todaysSideffectPosition >=0) {
                String header =
                        getString(getActivity().getResources().getIdentifier("sideeffect_"+ emotionalSideeffectList.get(i), "string", getActivity().getPackageName()));

                Sideeffect sideeffect = new Sideeffect(todaysSideeffects.get(todaysSideffectPosition).sideeffect_ID,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        todaysSideeffects.get(todaysSideffectPosition).date,
                        todaysSideeffects.get(todaysSideffectPosition).time,
                        todaysSideeffects.get(todaysSideffectPosition).type,
                        todaysSideeffects.get(todaysSideffectPosition).value,
                        "");
                SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem(header, sideeffect);
                emotionalExpandList.add(sideeffectItem);

            }else{
                // create empty Sideeffect object
                String header =
                        getString(getActivity().getResources().getIdentifier("sideeffect_"+ emotionalSideeffectList.get(i), "string", getActivity().getPackageName()));
                Sideeffect sideeffect = new Sideeffect(-1,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        "",
                        "",
                        emotionalSideeffectList.get(i),
                        "",
                        "");
                SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem(header, sideeffect);
                emotionalExpandList.add(sideeffectItem);
            }
        }

        Collections.sort(emotionalExpandList, new Comparator<SideeffectExpandListItem>() {
            @Override
            public int compare(SideeffectExpandListItem o1, SideeffectExpandListItem o2) {
                return o1.header.compareTo(o2.header);
            }
        });

        for (int i=0; i < distressSideeffectList.size() ; i++){
            todaysSideffectPosition = -1;
            for (int j=0; j < todaysSideeffects.size(); j++){
                if (todaysSideeffects.get(j).type.matches(distressSideeffectList.get(i))){
                    // Sideeffect has already saved value from today
                    todaysSideffectPosition = j;
                    break;
                }
            }

            if (todaysSideffectPosition >=0) {
                String header =
                        getString(getActivity().getResources().getIdentifier("sideeffect_"+ distressSideeffectList.get(i), "string", getActivity().getPackageName()));

                Sideeffect sideeffect = new Sideeffect(todaysSideeffects.get(todaysSideffectPosition).sideeffect_ID,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        todaysSideeffects.get(todaysSideffectPosition).date,
                        todaysSideeffects.get(todaysSideffectPosition).time,
                        todaysSideeffects.get(todaysSideffectPosition).type,
                        todaysSideeffects.get(todaysSideffectPosition).value,
                        "");
                SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem(header, sideeffect);
                distressExpandList.add(sideeffectItem);

            }else{
                // create empty Sideeffect object
                String header =
                        getString(getActivity().getResources().getIdentifier("sideeffect_"+ distressSideeffectList.get(i), "string", getActivity().getPackageName()));
                Sideeffect sideeffect = new Sideeffect(-1,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        journalDate,
                        "",
                        distressSideeffectList.get(i),
                        "",
                        "");
                SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem(header, sideeffect);
                distressExpandList.add(sideeffectItem);
            }

        }

        // temp solution, add one empty element to emotional list so that whole list is shown
        Sideeffect sideeffect = new Sideeffect(-1,
                0,
                0,
                "",
                "",
                "",
                "",
                "");
        SideeffectExpandListItem sideeffectItem = new SideeffectExpandListItem("", sideeffect);
        emotionalExpandList.add(sideeffectItem);


        listDataChild.put(listDataHeader.get(0), physicalExpandList);
        listDataChild.put(listDataHeader.get(1), emotionalExpandList);
        listDataChild.put(listDataHeader.get(2), distressExpandList);

        expandListAdapter = new JournalExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expandListView.setAdapter(expandListAdapter);
        expandListView.collapseGroup(0);
        expandListView.collapseGroup(1);
        expandListView.collapseGroup(2);
        expandListView.expandGroup(0);
        expandListView.expandGroup(1);
        expandListView.expandGroup(2);

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

    private String getSideeffectType(String translatedSideeffectType){
        // The sideeffect was translated and sorted, thus we do not now the sideeffect type
        // Now we need to identify the translated string and find
        // the associated type. Needed for setup the corresponding detail fragment
        String sideeffectType = "";

        return sideeffectType;
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle((connectHandler.patient.patient_name.concat(getString(R.string.patient_journey))));
    }
}