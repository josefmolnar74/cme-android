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
public class JournalFragment extends Fragment {

    public JournalFragment() {
        // Required empty public constructor
    }

    public static JournalFragment newInstance() {
        JournalFragment fragment = new JournalFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        // Check language settings
        SharedPreferences prefs = this.getActivity().getSharedPreferences(
                "language_settings", Context.MODE_PRIVATE);

        String languageString = prefs.getString("language_settings", "");
        System.out.println("LANGUAGE SETTINGS: " + languageString);
        //////////////////////////

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        JournalExpandListFragment myJournalExpList = new JournalExpandListFragment();
        ft.replace(R.id.journal_placeholder1, myJournalExpList);
        ft.commit();
        return view;
    }

}
