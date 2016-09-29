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

    // CONSTANTS
    final static public String SIDEEFFECT_PHYSICAL_APPERANCE = "physical_appearance";
    final static public String SIDEEFFECT_PHYSICAL_HYGIENE = "physical_hygiene";
    final static public String SIDEEFFECT_PHYSICAL_BREATHING = "physical_breathing";
    final static public String SIDEEFFECT_PHYSICAL_URINATION = "physical_urination";
    final static public String SIDEEFFECT_PHYSICAL_CONSTIPATION = "physical_constipation";
    final static public String SIDEEFFECT_PHYSICAL_DIARRHEA = "physical_diarrhea";
    final static public String SIDEEFFECT_PHYSICAL_APPETITE = "physical_appetite";
    final static public String SIDEEFFECT_PHYSICAL_FATIGUE = "physical_fatigue";
    final static public String SIDEEFFECT_PHYSICAL_BLOATED  = "physical_bloated";
    final static public String SIDEEFFECT_PHYSICAL_FEVER  = "physical_fever";
    final static public String SIDEEFFECT_PHYSICAL_MOBILITY  = "physical_mobility";
    final static public String SIDEEFFECT_PHYSICAL_DIGESTION  ="physical_digestion";
    final static public String SIDEEFFECT_PHYSICAL_MEMORY  = "physical_memory";
    final static public String SIDEEFFECT_PHYSICAL_MOUTH  = "physical_mouth";
//    final static public String SIDEEFFECT_PHYSICAL_NAUSEA  = "physical_nausea";
    final static public String SIDEEFFECT_PHYSICAL_VOMIT = "physical_vomit";
    final static public String SIDEEFFECT_PHYSICAL_DIZZINESS = "physical_dizziness";
    final static public String SIDEEFFECT_PHYSICAL_NOSE = "physical_nose";
    final static public String SIDEEFFECT_PHYSICAL_PAIN = "physical_pain";
    final static public String SIDEEFFECT_PHYSICAL_SEX = "physical_sex";
    final static public String SIDEEFFECT_PHYSICAL_DERMAL = "physical_dermal";
    final static public String SIDEEFFECT_PHYSICAL_SLEEP = "physical_sleep";
    final static public String SIDEEFFECT_PHYSICAL_ABUSE = "physical_abuse";
    final static public String SIDEEFFECT_PHYSICAL_TINGLING = "physical_tingling";

    final static public String SIDEEFFECT_EMOTIONAL_DEPRESSION ="emotional_depression";
    final static public String SIDEEFFECT_EMOTIONAL_FEAR = "emotional_fear";
    final static public String SIDEEFFECT_EMOTIONAL_NERVOUS = "emotional_nervous";
    final static public String SIDEEFFECT_EMOTIONAL_DEJECTION = "emotional_dejection";
    final static public String SIDEEFFECT_EMOTIONAL_WORRY = "emotional_worry";
    final static public String SIDEEFFECT_EMOTIONAL_ACTIVITIES = "emotional_activities";


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
