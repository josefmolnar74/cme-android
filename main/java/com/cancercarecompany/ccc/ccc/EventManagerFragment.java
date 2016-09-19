package com.cancercarecompany.ccc.ccc;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventManagerFragment extends Fragment {

    public int selectedFamilyAvatar;
    public int selectedHealthcareAvatar;
    RelativeLayout relativeLayout;
    LinearLayout wholeScreen;
    ImageButton familyAvatar;
    ImageButton healthcareAvatar;
    ConnectionHandler connectHandler;

    List<CareTeamMember> familyList;
    List<HealthCare> healthcareList;

    String languageString;

    public EventManagerFragment() {
        // Required empty public constructor
    }

    public static EventManagerFragment newInstance() {
        EventManagerFragment fragment = new EventManagerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_event_manager, container, false);

        // Check language settings
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "language_settings", Context.MODE_PRIVATE);

        languageString = prefs.getString("language_settings", "");
        System.out.println("LANGUAGE SETTINGS: " + languageString);
        //////////////////////////

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        CareTeamExpListFragment myCareTeamExpList = new CareTeamExpListFragment();
        ft.replace(R.id.journal_placeholder1, myCareTeamExpList);
        ft.commit();
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
