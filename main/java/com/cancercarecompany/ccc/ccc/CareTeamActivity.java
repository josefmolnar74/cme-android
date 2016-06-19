package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class CareTeamActivity extends AppCompatActivity {

    // Hej Robingit
    ArrayList<Events> eventList;
    ArrayList<Patient> patientList = new ArrayList<>();
    ArrayList<CareTeamMember> familyList = new ArrayList<>();
    ArrayList<CareTeamMember> healthCareList = new ArrayList<>();

    GridView familyGridView;
    GridView healthCareGridView;
    CareTeamFamilyAdapter familyAdapter;
    CareTeamHealthCareAdapter healthCareAdapter;
    CareTeamHealthCareAdapter listAdapter = healthCareAdapter;
    LinearLayout linearLayout;
    ImageButton journeyButton;
    ImageButton journalButton;
    ConnectionHandler connectHandler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careteam);
        connectHandler = ConnectionHandler.getInstance();

        TextView loggedIn = (TextView) findViewById(R.id.loggedIn);
        loggedIn.setText(connectHandler.person.first_name);

        familyGridView = (GridView) findViewById(R.id.careTeamFamilyGridView);
        healthCareGridView = (GridView) findViewById(R.id.careTeamHealthCareGridView);

        final Button buttonAddHealthCareMember = (Button) findViewById(R.id.btn_add_CTmember);
        final Button buttonAddFamilyMember   = (Button) findViewById(R.id.btn_add_CT);

        journeyButton = (ImageButton) findViewById(R.id.journeyButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);

        journeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journeyActivity();
            }
        });

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journalActivity();
            }
        });

        // Generate family and friends care team members
        for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
            familyList.add(connectHandler.patient.care_team.get(i));
        }

        connectHandler.getInvitedCareTeamMembers(connectHandler.patient.patient_ID);

        while (connectHandler.socketBusy){}

        for (int i = 0; i < connectHandler.invites.invite_data.size();i++){
            CareTeamMember invitedCareTeamMember = new CareTeamMember(
                    connectHandler.invites.invite_data.get(i).person_ID,
                    connectHandler.invites.invite_data.get(i).invited_first_name,
                    connectHandler.invites.invite_data.get(i).invited_last_name,
                    connectHandler.invites.invite_data.get(i).invited_email,
                    connectHandler.invites.invite_data.get(i).invited_relationship,
                    connectHandler.invites.invite_data.get(i).invited_admin);
            familyList.add(invitedCareTeamMember);
        }
        // Add invited friends that have yet not accepted invitation
//        for (int i = connectHandler.patient.care_team.size(); i < ; i++){

//        }

        familyAdapter = new CareTeamFamilyAdapter(this, familyList);
        familyGridView.setAdapter(familyAdapter);

        // Generate health care members
        // TBD solution to differentiate between family and health care
        for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
            healthCareList.add(connectHandler.patient.care_team.get(i));
        }

        healthCareAdapter = new CareTeamHealthCareAdapter(this, healthCareList);
        healthCareGridView.setAdapter(healthCareAdapter);

        //Open popup window to show user detail information and edit/delete
        familyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int familyListPosition, long id) {
                showCareTeamMember(familyListPosition);
            }
        });

        //Open popup window to show user detail information and edit/delete
        healthCareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int healthCareGridPosition, long id) {
                showCareTeamMember(healthCareGridPosition);
            }
        });

        buttonAddHealthCareMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteCareTeamMember();
            }
        });

        buttonAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHealthCareMember();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void createHealthCareMember(){
        inviteCareTeamMember();
    }

    public void inviteCareTeamMember() {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.care_team_member_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 1000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText editFirstName        = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final EditText editLastName         = (EditText) popupView.findViewById(R.id.txt_lastName_careteam);
        final EditText editPhoneNumber      = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final EditText editEmail            = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final EditText editRelation         = (EditText) popupView.findViewById(R.id.txt_careteam_relation);
        final Spinner  editAdmin            = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] spinnerAdminValues   = {"Yes", "No"};
        final Button   buttonSave           = (Button) popupView.findViewById(R.id.btn_save_careteam);
        final Button   buttonCancel         = (Button) popupView.findViewById(R.id.btn_cancel_careteam);
        final Button   buttonEdit           = (Button) popupView.findViewById(R.id.btn_edit_careteam);
        final TextView alertText            = (TextView) popupView.findViewById(R.id.text_careTeamInvite_alertText);

        // no need to save LastName and PhoneNumber
//        editLastName.setFocusable(false);
//        editPhoneNumber.setFocusable(false);

        buttonSave.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);

        Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
        adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_admin.setAdapter(adapter_admin);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertText.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }
        });

        buttonSave.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String firstNameString = editFirstName.getText().toString();
                String emailString = editEmail.getText().toString();

                //FirstName and email must be specified, the others will get emptystring if not specified
                if ((!firstNameString.isEmpty()) && (!emailString.isEmpty())){
                    alertText.setVisibility(View.INVISIBLE);
                    // invite new care team member
                    int admin;
                    if(editAdmin.getSelectedItem() == "Yes") {
                        admin = 1;
                    }
                    else {
                        admin = 0;
                    }
                    Invite newInvite = new Invite(  0,
                                                    connectHandler.person.first_name,
                                                    connectHandler.patient.patient_ID,
                                                    connectHandler.patient.patient_name,
                                                    firstNameString,
                                                    editLastName.getText().toString(),
                                                    emailString,
                                                    editRelation.getText().toString(),
                                                    admin,
                                                    0,
                                                    0);

                    connectHandler.inviteCareTeamMember(newInvite);

                    CareTeamMember invitedCareTeamMember = new CareTeamMember(
                            newInvite.person_ID,
                            newInvite.invited_first_name,
                            newInvite.invited_last_name,
                            newInvite.invited_email,
                            newInvite.invited_relationship,
                            newInvite.invited_admin);

                    familyList.add(invitedCareTeamMember);

                    familyAdapter.notifyDataSetChanged();
//                listAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();

                }else{
                    // notify user they need to add
                    alertText.setVisibility(View.VISIBLE);
                }
            }
        });

        linearLayout = (LinearLayout) popupView.findViewById(R.id.care_team_member_popup);
        //  LinearLayout layout = (LinearLayout) findViewById(R.id.careTeamScreen);
        popupWindow.showAsDropDown(linearLayout, 500, 20);
        popupWindow.isFocusable();

    }

    public void showCareTeamMember(final int listPosition) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.care_team_member_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 1000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final TextView firstName            = (TextView) popupView.findViewById(R.id.lbl_firstName_careteam);
        final EditText editFirstName        = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final TextView lastName             = (TextView) popupView.findViewById(R.id.lbl_lastName_careteam);
        final EditText editLastName         = (EditText) popupView.findViewById(R.id.txt_lastName_careteam);
        final TextView phoneNumber          = (TextView) popupView.findViewById(R.id.lbl_phone_careteam);
        final EditText editPhoneNumber      = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final TextView email                = (TextView) popupView.findViewById(R.id.lbl_email_careteam);
        final EditText editEmail            = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final TextView relation             = (TextView) popupView.findViewById(R.id.lbl_relation_careteam);
        final EditText editRelation         = (EditText) popupView.findViewById(R.id.txt_careteam_relation);
        final TextView admin                = (TextView) popupView.findViewById(R.id.lbl_admin_careteam);
        final Spinner spinnerAdmin             = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] spinnerAdminValues   = {"Ja", "Nej"};

        final Button buttonEdit      = (Button) popupView.findViewById(R.id.btn_edit_careteam);
        final Button buttonSave      = (Button) popupView.findViewById(R.id.btn_save_careteam);
        final Button buttonCancel    = (Button) popupView.findViewById(R.id.btn_cancel_careteam);
        final Button buttonDelete    = (Button) popupView.findViewById(R.id.btn_del_ct_memb);

        editFirstName.setText(connectHandler.patient.care_team.get(listPosition).first_name);
        editLastName.setText(connectHandler.patient.care_team.get(listPosition).last_name);
        editEmail.setText(connectHandler.patient.care_team.get(listPosition).email);
        editFirstName.setFocusable(false);
        editLastName.setFocusable(false);
        editEmail.setFocusable(false);
        editPhoneNumber.setFocusable(false);

        ArrayAdapter<String> adapterAdmin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
        adapterAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdmin.setAdapter(adapterAdmin);

        spinnerAdmin.setEnabled(false);

        buttonEdit.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonSave.setVisibility(View.INVISIBLE);

        linearLayout = (LinearLayout) popupView.findViewById(R.id.care_team_member_popup);
        popupWindow.showAsDropDown(linearLayout, 500, 20);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                saveContact(listPosition);
                popupWindow.dismiss();
            }

            private void saveContact(int listPosition) {
                firstName.setText(editFirstName.getText().toString());
                lastName.setText(editLastName.getText().toString());
                email.setText(editEmail.getText().toString());
                admin.setText(spinnerAdmin.getSelectedItem().toString());
/*
                CareTeamMember newCareTeamMember = new CareTeamMember(  firstName.getText().toString(),
                                                                        lastName.getText().toString(),
                                                                        email.getText().toString(),
                                                                        relation.getText().toString(),1);
//                                                                        Integer.parseInt(admin.getText().toString()));

                connectHandler.inviteCareTeamMember(newCareTeamMember);
                //Enable when invite has been implemented in the backend
//                while (connectHandler.socketBusy){}
*/
                healthCareAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
            }

        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                prepareForEdit(listPosition);
            }

            private void prepareForEdit(int MLp) {

                editFirstName.setFocusable(true);
                editFirstName.setFocusableInTouchMode(true);
                editFirstName.setEnabled(true);
                editLastName.setFocusable(true);
                editLastName.setFocusableInTouchMode(true);
                editLastName.setEnabled(true);
                editEmail.setFocusable(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setEnabled(true);
                editPhoneNumber.setFocusable(true);
                editPhoneNumber.setFocusableInTouchMode(true);
                editPhoneNumber.setEnabled(true);

                ArrayAdapter<String> adapterAdmin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
                adapterAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAdmin.setAdapter(adapterAdmin);

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonEdit.getVisibility() == View.VISIBLE) {
                    popupWindow.dismiss();
                }
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
//                cancelEdit(listPosition);
            }

            private void cancelEdit(int MLp) {


/*              Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
                ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
                adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_admin.setAdapter(adapter_admin);
*/
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.INVISIBLE);
                deleteCareTeamMembers(listPosition);
            }
            private void deleteCareTeamMembers(int index) {

//                healthCareAdapter.notifyDataSetChanged();
//                listAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }

    private void journeyActivity(){
        Intent myIntent = new Intent(this, JourneyActivity.class);
        startActivity(myIntent);

    }

    private void journalActivity(){
        Intent myIntent = new Intent(this, JournalActivity.class);
        startActivity(myIntent);

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CareTeamActivity", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cancercarecompany.ccc.ccc/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CareTeamActivity", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cancercarecompany.ccc.ccc/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}