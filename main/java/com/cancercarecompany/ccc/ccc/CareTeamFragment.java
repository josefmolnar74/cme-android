package com.cancercarecompany.ccc.ccc;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class CareTeamFragment extends Fragment {

    ConnectionHandler connectHandler;
    String languageString;

    public CareTeamFragment() {
        // Required empty public constructor
    }

    public static CareTeamFragment newInstance() {
        CareTeamFragment fragment = new CareTeamFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        View view = inflater.inflate(R.layout.fragment_care_team, container, false);

        // Check language settings
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "language_settings", Context.MODE_PRIVATE);

        languageString = prefs.getString("language_settings", "");
        System.out.println("LANGUAGE SETTINGS: " + languageString);
        //////////////////////////

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        CareTeamExpListFragment myCareTeamExpList = new CareTeamExpListFragment();
        ft.add(myCareTeamExpList, "CareTeamExpListFragment");
        ft.replace(R.id.careteam_placeholder1, myCareTeamExpList);
        ft.commit();
        return view;
    }
}
