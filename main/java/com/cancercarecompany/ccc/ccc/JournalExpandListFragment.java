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
        List<String> emotionalExpandList = new ArrayList<String>();
        List<String> physicalExpandList = new ArrayList<String>();
        List<String> familyExpandList = new ArrayList<String>();
        List<String> practicalExpandList = new ArrayList<String>();

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add(getResources().getString(R.string.journal_problem_emotional));
        listDataHeader.add(getResources().getString(R.string.journal_problem_physical));
        listDataHeader.add(getResources().getString(R.string.journal_problem_family));
        listDataHeader.add(getResources().getString(R.string.journal_problem_family));

        listDataChild.put(listDataHeader.get(0), emotionalExpandList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), physicalExpandList);
        listDataChild.put(listDataHeader.get(2), familyExpandList);
        listDataChild.put(listDataHeader.get(3), practicalExpandList);

        expandListAdapter = new JournalExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expandListView.setAdapter(expandListAdapter);

        expandListView.expandGroup(0);
        expandListView.expandGroup(1);

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

}

