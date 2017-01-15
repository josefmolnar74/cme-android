package com.cancercarecompany.ccc.ccc;


import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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

    private UserLoginTask mAuthTask = null;

    ExpandableListAdapter expListAdapter;
    ExpandableListView expListView;

    List<CareTeamExpandListItem> patientExpList;
    List<CareTeamExpandListItem> familyExpList;
    List<CareTeamExpandListItem> healthCareExpList;
    List<CareTeamExpandListItem> cancerFriendsExpList;
    List<String> listDataHeader;
    HashMap<String, List<CareTeamExpandListItem>> listDataChild;
    private ConnectionHandler connectHandler;
    private boolean admin;
    private ArrayList<CancerFriend> cancerFriends = new ArrayList<CancerFriend>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_care_team_exp_list, container, false);

        connectHandler = ConnectionHandler.getInstance();
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.VISIBLE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(true);
        }

        ((MainActivity) getActivity()).setTitle(connectHandler.patient.patient_name.concat(getString(R.string.patient_journey)));

        expListView = (ExpandableListView) view.findViewById(R.id.explv_careteam);
        patientExpList = new ArrayList<CareTeamExpandListItem>();
        familyExpList = new ArrayList<CareTeamExpandListItem>();
        healthCareExpList = new ArrayList<CareTeamExpandListItem>();
        cancerFriendsExpList = new ArrayList<CareTeamExpandListItem>();

        //build list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<CareTeamExpandListItem>>();

        listDataHeader.add(getResources().getString(R.string.careteam_patient));
        listDataHeader.add(getResources().getString(R.string.careteam_family));
        listDataHeader.add(getResources().getString(R.string.careteam_healthcare));
        listDataHeader.add(getResources().getString(R.string.careteam_cancer_friends));

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                // Begin the transaction
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                switch (groupPosition){
                    case 0:
                        // Patient
                        CareTeamPatientFragment mycareTeamShowPatient = new CareTeamPatientFragment();
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                            ft.replace(R.id.careteam_placeholder2, mycareTeamShowPatient);
                        }
                        else{
                            ft.replace(R.id.careteam_placeholder1, mycareTeamShowPatient);
                        }
                        ft.addToBackStack(null);
                        // send family member data to fragment
                        mycareTeamShowPatient.setItem(familyExpList.get(childPosition));

                        break;

                    case 1:
                        // Family or invited user
                        CareTeamFamilyFragment mycareTeamShowFamily = new CareTeamFamilyFragment();
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                            ft.replace(R.id.careteam_placeholder2, mycareTeamShowFamily);
                        }
                        else{
                            ft.replace(R.id.careteam_placeholder1, mycareTeamShowFamily);
                        }
                        ft.addToBackStack(null);
                        // send family member data to fragment
                        mycareTeamShowFamily.setItem(familyExpList.get(childPosition));

                        break;

                    case 2:
                        CareTeamHealthcareFragment mycareTeamShowHealthcare = new CareTeamHealthcareFragment();
                        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                            ft.replace(R.id.careteam_placeholder2, mycareTeamShowHealthcare);
                        }
                        else{
                            ft.replace(R.id.careteam_placeholder1, mycareTeamShowHealthcare);
                        }
                        ft.addToBackStack(null);
                        // send healthcare item to fragment
                        mycareTeamShowHealthcare.setItem(healthCareExpList.get(childPosition));
                        break;
                }
                ft.commit();
                return false;
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if ((connectHandler.patient != null) && (connectHandler.invites != null) && (connectHandler.healthcare != null)){
            prepareCareTeamExpandList();
        }
    }

    private void prepareCareTeamExpandList(){

        if (!patientExpList.isEmpty()){
            patientExpList.clear();
        }

        if (!familyExpList.isEmpty()){
            familyExpList.clear();
        }

        if (!healthCareExpList.isEmpty()){
            healthCareExpList.clear();
        }

        if (!cancerFriendsExpList.isEmpty()){
            cancerFriendsExpList.clear();
        }

        // check admin
        if (connectHandler.patient.care_team != null){
            for (int i=0; i < connectHandler.patient.care_team.size(); i++){
                if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                        (connectHandler.patient.care_team.get(i).admin == 1)){
                    admin = true;
                }
            }
        }
        else{
            admin = true;
        }

        if (connectHandler.patient != null) {
            if (admin){
                // add position to invite new member
                CareTeamExpandListItem dummyListItem = new CareTeamExpandListItem();
                dummyListItem.name = getResources().getString(R.string.careteam_invite_new_member);
                dummyListItem.type = "new";
                dummyListItem.avatar = 0;
                familyExpList.add(dummyListItem);
            }
            if (connectHandler.patient.care_team != null) {
                for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
                    CareTeamExpandListItem listItem = new CareTeamExpandListItem();
                    listItem.id = connectHandler.patient.care_team.get(i).person_ID;
                    listItem.type = "family";
                    listItem.name = connectHandler.patient.care_team.get(i).name;
                    listItem.avatar = connectHandler.patient.care_team.get(i).avatar;
                    listItem.relationship = connectHandler.patient.care_team.get(i).relationship;
                    familyExpList.add(listItem);
                }
            }

            if (connectHandler.invites != null) {
                for (int i = 0; i < connectHandler.invites.invite_data.size(); i++) {
                    CareTeamExpandListItem listItem = new CareTeamExpandListItem();
                    listItem.id = connectHandler.invites.invite_data.get(i).invite_ID;
                    listItem.type = "invite";
                    listItem.name = connectHandler.invites.invite_data.get(i).invited_name;
                    listItem.avatar = connectHandler.invites.invite_data.get(i).invited_avatar;
                    listItem.relationship = connectHandler.invites.invite_data.get(i).invited_relationship;
                    familyExpList.add(listItem);
                }
            }

            if (connectHandler.healthcare != null) {
                if (admin) {
                    // add position to create new healthcare member
                    CareTeamExpandListItem dummyListItem = new CareTeamExpandListItem();
                    dummyListItem.name = getResources().getString(R.string.careteam_create_new_healthcare);
                    dummyListItem.type = "new";
                    dummyListItem.avatar = 0;
                    healthCareExpList.add(dummyListItem);
                }
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

            if (cancerFriends.isEmpty()){
                // Prepare cancer friends
                prepareCancerFriends();
            }

            for (int i = 0; i < cancerFriends.size(); i++) {
                CareTeamExpandListItem listItem = new CareTeamExpandListItem();
                listItem.id = i;
                listItem.type = "cancer_friend";
                listItem.name = cancerFriends.get(i).name;
                listItem.avatar = 0;
                listItem.relationship = cancerFriends.get(i).link + " " +cancerFriends.get(i).phone;
                cancerFriendsExpList.add(listItem);
            }

            // temp solution, add one empty element to emotional list so that whole list is shown
/*            CareTeamExpandListItem emptyItem = new CareTeamExpandListItem();
            emptyItem.id = 0;
            emptyItem.type = "empty_line";
            emptyItem.name = "";
            emptyItem.avatar = 0;
            emptyItem.relationship = "";
            cancerFriendsExpList.add(emptyItem);
*/
            CareTeamExpandListItem listItem = new CareTeamExpandListItem();
            listItem.id = connectHandler.patient.patient_ID;
            listItem.type = "patient";
            listItem.name = connectHandler.patient.patient_name;
            listItem.avatar = 0;
            listItem.relationship = connectHandler.patient.diagnose;
            patientExpList.add(listItem);

            listDataChild.put(listDataHeader.get(0), patientExpList); // Header, Child data
            listDataChild.put(listDataHeader.get(1), familyExpList);
            listDataChild.put(listDataHeader.get(2), healthCareExpList);
            listDataChild.put(listDataHeader.get(3), cancerFriendsExpList);
        }

        expListAdapter = new CareTeamExpandListAdapter(this.getContext(), listDataHeader, listDataChild);
        expListView.setAdapter(expListAdapter);

//        expListView.collapseGroup(0);
        expListView.collapseGroup(1);
        expListView.collapseGroup(2);
//        expListView.collapseGroup(3);
        expListView.expandGroup(0);
        expListView.expandGroup(1);
        expListView.expandGroup(2);
        expListView.expandGroup(3);
    }

    private class CancerFriend{
        String name;
        String link;
        String phone;

        public CancerFriend(String name, String link, String phone) {

            this.name = name; //default value, does not matter because updated during read from database
            this.link = link;
            this.phone = phone;
        }

    }

    public void updateExpList(){
        // prepare the careteam list and update the exp list
        prepareCareTeamExpandList();
    }

    private void prepareCancerFriends(){
        CancerFriend myCancerFriend1 = new CancerFriend("Barncancerfonden", "www.barncancerfonden.se", "08-584 209 00");
        CancerFriend myCancerFriend2 = new CancerFriend("Nätverket mot cancer", "www.natverketmotcancer.se", "");
        CancerFriend myCancerFriend3 = new CancerFriend("Cancer kompisar", "www.cancerkompisar.se", "");
        cancerFriends.add(myCancerFriend1);
        cancerFriends.add(myCancerFriend2);
        cancerFriends.add(myCancerFriend3);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle((connectHandler.patient.patient_name.concat(getString(R.string.patient_journey))));
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            connectHandler.getPatient(connectHandler.person.patient.get(0).patient_ID);
            connectHandler.getInvitedCareTeamMembers(connectHandler.patient.patient_ID);
            connectHandler.getHealthcareForPatient(connectHandler.patient.patient_ID);

            while(connectHandler.pendingMessage){}

            if ((connectHandler.patient != null) && (connectHandler.invites != null) && (connectHandler.healthcare != null)){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                startFragment();
            } else {

            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

        private void startFragment(){
            onStart();
        }
    }

}

