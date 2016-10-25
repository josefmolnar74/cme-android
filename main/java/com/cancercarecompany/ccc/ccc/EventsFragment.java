package com.cancercarecompany.ccc.ccc;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        // Check language settings
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "language_settings", Context.MODE_PRIVATE);

        String languageString = prefs.getString("language_settings", "");
        System.out.println("LANGUAGE SETTINGS: " + languageString);
        //////////////////////////

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        EventsExpandListFragment myEventsExpList = new EventsExpandListFragment();
        ft.replace(R.id.events_placeholder1, myEventsExpList);
        ft.commit();
        return view;
    }

}
