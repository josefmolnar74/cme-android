package com.cancercarecompany.ccc.ccc;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsExpandListFragment extends Fragment {


    ExpandableListAdapter eventsExpandListAdapter;
    ExpandableListView expListView;

    List<Event> eventExpList;
    List<Event> passedEventExpList;

//    List<Event> EventThisWeekList;
//    List<Event> EventThisMonthExpList;
//    List<Event> cancerFriendsExpList;

    List<String> listDataHeader;
    HashMap<String, List<Event>> listDataChild;
    ConnectionHandler connectHandler;
    private boolean admin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_care_team_exp_list, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(true);
        }

        connectHandler = ConnectionHandler.getInstance();

        expListView = (ExpandableListView) view.findViewById(R.id.explv_careteam);
        eventExpList = new ArrayList<Event>();
        passedEventExpList = new ArrayList<Event>();

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Event>>();

        listDataHeader.add(getResources().getString(R.string.events_all));
        listDataHeader.add(getResources().getString(R.string.events_passed));
//        listDataHeader.add(getResources().getString(R.string.events_all));
//        listDataHeader.add(getResources().getString(R.string.events_passed));

        if (connectHandler.patient != null) {
            connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
            while (connectHandler.socketBusy){}
            if (connectHandler.patient.care_team != null) {
                // check admin
                for (int i=0; i < connectHandler.patient.care_team.size(); i++){
                    if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                            (connectHandler.patient.care_team.get(i).admin == 1)){
                        admin = true;
                    }
                }
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
                        try{
                            date = format.parse(connectHandler.events.event_data.get(i).date);
                        } catch (Exception e){
                            System.out.println("Date conversion unsuccesfull");
                        }
                        Date compareDate = null;
                        // Sort the events in date order
                        if (todaysDate.after(date)){
                            // put event in passed list
                            if (passedEventExpList.size()==0){
                                passedEventExpList.add(connectHandler.events.event_data.get(i));
                            }else{
                                for (int j = 0; j < passedEventExpList.size(); j++){
                                    try{
                                        compareDate = format.parse(passedEventExpList.get(j).date);
                                    } catch (Exception e){
                                        System.out.println("Date conversion unsuccesfull");
                                    }

                                    if (compareDate.after(date)){
                                        passedEventExpList.add(j, connectHandler.events.event_data.get(i));
                                        break;
                                    }
                                    if (j==passedEventExpList.size()-1){
                                        // new event is later than all others in list
                                        passedEventExpList.add(connectHandler.events.event_data.get(i));
                                        break;
                                    }
                                }
                            }
                        }else{
                            // put event in future list
                            if (eventExpList.size()==0){
                                eventExpList.add(connectHandler.events.event_data.get(i));
                            }
                            else{
                                for (int j = 0; j < eventExpList.size(); j++){
                                    try{
                                        compareDate = format.parse(eventExpList.get(j).date);
                                    } catch (Exception e){
                                        System.out.println("Date conversion unsuccesfull");
                                    }

                                    if (compareDate.after(date)){
                                        eventExpList.add(j, connectHandler.events.event_data.get(i));
                                        break;
                                    }
                                    if (j==eventExpList.size()-1){
                                        // new event is later than all others in list
                                        eventExpList.add(connectHandler.events.event_data.get(i));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if ((admin) && (!eventExpList.isEmpty()) && (eventExpList.get(0).event_ID != 0)){
                //add position to invite new member
                Event newListItem = new Event(0, 0, 0, 0, null, "",
                        "create_new",
                        "", "", null, null, null);
                eventExpList.add(0, newListItem);
            }

            // Sort events in the different lists
            listDataChild.put(listDataHeader.get(0), eventExpList); // Header, Child data
            listDataChild.put(listDataHeader.get(1), passedEventExpList);
        }

        eventsExpandListAdapter = new EventsExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expListView.setAdapter(eventsExpandListAdapter);

        expListView.expandGroup(0);
        expListView.expandGroup(1);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Begin the transaction
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                EventsDetailsFragment myEventsDetails = new EventsDetailsFragment();
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    ft.replace(R.id.events_placeholder2, myEventsDetails);
                }
                else{
                    ft.replace(R.id.events_placeholder1, myEventsDetails);
                }
                ft.addToBackStack(null);
                // send healthcare item to fragment
                switch (groupPosition){

                    case 0:
                        myEventsDetails.setItem(eventExpList.get(childPosition));
                        break;

                    case 1:
                        myEventsDetails.setItem(passedEventExpList.get(childPosition));
                        break;
                }
                ft.commit();
                return false;
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getView() != null) {
                // your code goes here
            }
        }
    }
}

