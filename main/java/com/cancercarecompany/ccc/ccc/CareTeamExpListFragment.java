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
public class CareTeamExpListFragment extends Fragment {


    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;

    List<CareTeamExpandListItem> familyExpList;
    List<CareTeamExpandListItem> healthCareExpList;

    List<String> listDataHeader;
    HashMap<String, List<CareTeamExpandListItem>> listDataChild;
    ConnectionHandler connectHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_care_team_exp_list, container, false);
        expListView = (ExpandableListView) view.findViewById(R.id.explv_careteam);
        familyExpList = new ArrayList<CareTeamExpandListItem>();
        healthCareExpList = new ArrayList<CareTeamExpandListItem>();

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<CareTeamExpandListItem>>();

        listDataHeader.add("Family and friends");
        listDataHeader.add("Health care");

        if (connectHandler.patient != null) {
            if (connectHandler.patient.care_team != null) {
                for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
                    CareTeamExpandListItem listItem = new CareTeamExpandListItem();
                    listItem.id = connectHandler.patient.care_team.get(i).person_ID;
                    listItem.type = "family";
                    listItem.name = connectHandler.patient.care_team.get(i).first_name;
                    listItem.avatar = connectHandler.patient.care_team.get(i).avatar;
                    listItem.relationship = connectHandler.patient.care_team.get(i).relationship;
                    familyExpList.add(listItem);
                }
            }

            connectHandler.getInvitedCareTeamMembers(connectHandler.patient.patient_ID);
            while (connectHandler.socketBusy) {
            }

            if (connectHandler.invites != null) {
                for (int i = 0; i < connectHandler.invites.invite_data.size(); i++) {
                    CareTeamExpandListItem listItem = new CareTeamExpandListItem();
                    listItem.id = connectHandler.invites.invite_data.get(i).invite_ID;
                    listItem.type = "invite";
                    listItem.name = connectHandler.invites.invite_data.get(i).invited_first_name;
                    listItem.avatar = connectHandler.invites.invite_data.get(i).invited_avatar;
                    listItem.relationship = connectHandler.invites.invite_data.get(i).invited_relationship;
                    familyExpList.add(listItem);
                }
            }

            connectHandler.getHealthcareForPatient(connectHandler.patient.patient_ID);
            while (connectHandler.socketBusy) {
            }

            if (connectHandler.healthcare != null) {
                for (int i = 0; i < connectHandler.healthcare.healthcare_data.size(); i++) {
                    CareTeamExpandListItem listItem = new CareTeamExpandListItem();
                    listItem.id = connectHandler.healthcare.healthcare_data.get(i).healthcare_ID;
                    listItem.type = "healthcare";
                    listItem.name = connectHandler.healthcare.healthcare_data.get(i).name;
                    listItem.avatar = connectHandler.healthcare.healthcare_data.get(i).avatar;
                    listItem.relationship = connectHandler.healthcare.healthcare_data.get(i).title;
                    healthCareExpList.add(listItem);
                }
            }

            listDataChild.put(listDataHeader.get(0), familyExpList); // Header, Child data
            listDataChild.put(listDataHeader.get(1), healthCareExpList);
        }

        expListAdapter = new CareTeamExpandListAdapter(this.getContext(), listDataHeader, listDataChild);

        expListView.setAdapter(expListAdapter);

        expListView.expandGroup(0);
        expListView.expandGroup(1);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Begin the transaction
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                CareTeamShowFamilyFragment mycareTeamShowFamily = new CareTeamShowFamilyFragment();
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    ft.replace(R.id.your_placeholder2, mycareTeamShowFamily);
                }
                else{
                    ft.replace(R.id.your_placeholder1, mycareTeamShowFamily);
                }
                ft.addToBackStack(null);

                switch (groupPosition){

                    case 0:
                        // Family or invited user
                        mycareTeamShowFamily.setItem(familyExpList.get(childPosition));
                        break;
                    case 1:
                        // HealthCare
                        mycareTeamShowFamily.setItem(healthCareExpList.get(childPosition));
                        break;
                }
//                mycareTeamShowFamily.
                ft.commit();
                return false;
            }
        });

        return view;
    }

}

