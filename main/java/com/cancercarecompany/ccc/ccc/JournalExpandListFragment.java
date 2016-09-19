package com.cancercarecompany.ccc.ccc;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class JournalExpandListFragment extends Fragment {


    ExpandableListAdapter expandListAdapter;
    ExpandableListView expandListView;

    List<String> emotionalExpandList;
    List<String> physicalExpandList;
    List<String> familyExpandList;
    List<String> practicalExpandList;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ConnectionHandler connectHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_journal_exp_list, container, false);
        expandListView = (ExpandableListView) view.findViewById(R.id.explv_journal);
        emotionalExpandList = new ArrayList<String>();
        physicalExpandList = new ArrayList<String>();
        familyExpandList = new ArrayList<String>();
        practicalExpandList = new ArrayList<String>();

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(getResources().getString(R.string.journal_problem_emotional));
        listDataHeader.add(getResources().getString(R.string.journal_problem_physical));
        listDataHeader.add(getResources().getString(R.string.journal_problem_family));
        listDataHeader.add(getResources().getString(R.string.journal_problem_practical));

        prepareExpList();

        listDataChild.put(listDataHeader.get(0), emotionalExpandList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), physicalExpandList);
        listDataChild.put(listDataHeader.get(2), familyExpandList);
        listDataChild.put(listDataHeader.get(3), practicalExpandList);

        expandListAdapter = new JournalExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expandListView.setAdapter(expandListAdapter);

//        expandListView.expandGroup(0);
//        expandListView.expandGroup(1);

        // Listview on child click listener
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

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
//                mycareTeamShowFamily.setItem(expandList.get(childPosition));

                ft.commit();
                return false;
            }
        });

        return view;
    }

    private void prepareExpList(){
        emotionalExpandList.add(getString(R.string.journal_problem_emotional_depression));
        emotionalExpandList.add(getString(R.string.journal_problem_emotional_fear));
        emotionalExpandList.add(getString(R.string.journal_problem_emotional_nervous));
        emotionalExpandList.add(getString(R.string.journal_problem_emotional_dejection));
        emotionalExpandList.add(getString(R.string.journal_problem_emotional_worry));
        emotionalExpandList.add(getString(R.string.journal_problem_emotional_loss));

        physicalExpandList.add(getString(R.string.journal_problem_physical_appearance));
        physicalExpandList.add(getString(R.string.journal_problem_physical_hygiene));
        physicalExpandList.add(getString(R.string.journal_problem_physical_breathing));
        physicalExpandList.add(getString(R.string.journal_problem_physical_urination));
        physicalExpandList.add(getString(R.string.journal_problem_physical_constipation));
        physicalExpandList.add(getString(R.string.journal_problem_physical_diarrhea));
        physicalExpandList.add(getString(R.string.journal_problem_physical_eating));
        physicalExpandList.add(getString(R.string.journal_problem_physical_fatigue));
        physicalExpandList.add(getString(R.string.journal_problem_physical_swollen));
        physicalExpandList.add(getString(R.string.journal_problem_physical_fever));
        physicalExpandList.add(getString(R.string.journal_problem_physical_mobility));
        physicalExpandList.add(getString(R.string.journal_problem_physical_digestion));
        physicalExpandList.add(getString(R.string.journal_problem_physical_memory));
        physicalExpandList.add(getString(R.string.journal_problem_physical_mouth));
        physicalExpandList.add(getString(R.string.journal_problem_physical_nausea));
        physicalExpandList.add(getString(R.string.journal_problem_physical_nose));
        physicalExpandList.add(getString(R.string.journal_problem_physical_pain));
        physicalExpandList.add(getString(R.string.journal_problem_physical_sex));
        physicalExpandList.add(getString(R.string.journal_problem_physical_dermal));
        physicalExpandList.add(getString(R.string.journal_problem_physical_sleep));
        physicalExpandList.add(getString(R.string.journal_problem_physical_abuse));
        physicalExpandList.add(getString(R.string.journal_problem_physical_tingling));

        familyExpandList.add(getString(R.string.journal_problem_family_relation_child));
        familyExpandList.add(getString(R.string.journal_problem_family_relation_partner));
        familyExpandList.add(getString(R.string.journal_problem_family_children));
        familyExpandList.add(getString(R.string.journal_problem_family_health));

        practicalExpandList.add(getString(R.string.journal_problem_practical_childcare));
        practicalExpandList.add(getString(R.string.journal_problem_practical_home));
        practicalExpandList.add(getString(R.string.journal_problem_practical_economy));
        practicalExpandList.add(getString(R.string.journal_problem_practical_transportation));
        practicalExpandList.add(getString(R.string.journal_problem_practical_work));
        practicalExpandList.add(getString(R.string.journal_problem_practical_decision));

    }
}

