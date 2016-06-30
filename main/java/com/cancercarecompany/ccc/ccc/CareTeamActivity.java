package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class CareTeamActivity extends AppCompatActivity {

    ArrayList<CareTeamMember> familyList = new ArrayList<>();
    ArrayList<HealthCare> healthcareList = new ArrayList<>();

    public int selectedFamilyAvatar;
    public int selectedHealthcareAvatar;
    GridView familyGridView;
    GridView healthCareGridView;
    CareTeamFamilyAdapter familyAdapter;
    CareTeamHealthCareAdapter healthCareAdapter;
    RelativeLayout relativeLayout;
    ImageButton journeyButton;
    ImageButton journalButton;
    ImageButton familyAvatar;
    ImageButton healthcareAvatar;
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

        // Statusbar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        connectHandler = ConnectionHandler.getInstance();

        // Display patient name on topbar
        TextView patientNameText = (TextView) findViewById(R.id.txt_careteam_patient_name);
        if (connectHandler.patient != null){
            patientNameText.setText(patientNameText.getText().toString().concat(" ".concat(connectHandler.patient.patient_name)));
        }

        TextView loggedIn = (TextView) findViewById(R.id.txt_journal_loggedIn);
        if (connectHandler.person != null){
            loggedIn.setText(connectHandler.person.first_name);
        }

        familyGridView = (GridView) findViewById(R.id.gridview_careteam_family);
        healthCareGridView = (GridView) findViewById(R.id.gridview_careteam_healthcare);

        final Button buttonAddHealthCareMember = (Button) findViewById(R.id.btn_careteam_add_healthcare);
        final Button buttonAddFamilyMember   = (Button) findViewById(R.id.btn_careteam_invite_careteam);

        journeyButton = (ImageButton) findViewById(R.id.btn_journal_journey_button);
        journalButton = (ImageButton) findViewById(R.id.btn_journal_journal_button);

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
        if (connectHandler.patient != null){
            if (connectHandler.patient.care_team != null){
                for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
                    familyList.add(connectHandler.patient.care_team.get(i));
                }
            }
        }

        connectHandler.getInvitedCareTeamMembers(connectHandler.patient.patient_ID);

        while (connectHandler.socketBusy){}

        if (connectHandler.invites != null)
        {
            for (int i = 0; i < connectHandler.invites.invite_data.size();i++){
                CareTeamMember invitedCareTeamMember = new CareTeamMember(
                        connectHandler.invites.invite_data.get(i).person_ID,
                        connectHandler.invites.invite_data.get(i).invited_first_name,
                        connectHandler.invites.invite_data.get(i).invited_last_name,
                        connectHandler.invites.invite_data.get(i).invited_email,
                        connectHandler.invites.invite_data.get(i).invited_relationship,
                        connectHandler.invites.invite_data.get(i).invited_avatar,
                        connectHandler.invites.invite_data.get(i).invited_admin);
                familyList.add(invitedCareTeamMember);
            }
        }

        familyAdapter = new CareTeamFamilyAdapter(this, familyList);
        familyGridView.setAdapter(familyAdapter);

        // Generate health care members
        connectHandler.getHealthcareForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

        if (connectHandler.healthcare != null){
            for (int i = 0; i < connectHandler.healthcare.healthcare_data.size(); i++) {
                healthcareList.add(connectHandler.healthcare.healthcare_data.get(i));
            }
        }

        healthCareAdapter = new CareTeamHealthCareAdapter(this, healthcareList);
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
                showHealthcare(healthCareGridPosition);
            }
        });

        buttonAddHealthCareMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHealthCareMember();
            }
        });

        buttonAddFamilyMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteCareTeamMember();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void createHealthCareMember(){
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.careteam_healthcare_popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText editTitle        = (EditText) popupView.findViewById(R.id.edittext_healthcare_title);
        final EditText editName         = (EditText) popupView.findViewById(R.id.edittext_healthcare_name);
        final EditText editDepartment   = (EditText) popupView.findViewById(R.id.edittext_healthcare_department);
        final EditText editPhoneNumber1 = (EditText) popupView.findViewById(R.id.edittext_healthcare_phonenumber1);
        final EditText editPhoneNumber2 = (EditText) popupView.findViewById(R.id.edittext_healthcare_phonenumber2);
        final EditText editPhoneNumber3 = (EditText) popupView.findViewById(R.id.edittext_healthcare_phonenumber3);
        final EditText editEmail        = (EditText) popupView.findViewById(R.id.edittext_healthcare_email);
        final Button   buttonSave       = (Button) popupView.findViewById(R.id.btn_healthcare_save);
        final Button   buttonCancel     = (Button) popupView.findViewById(R.id.btn_healthcare_cancel);
        final Button   buttonEdit       = (Button) popupView.findViewById(R.id.btn_healthcare_edit);
        final Button   buttonDelete     = (Button) popupView.findViewById(R.id.btn_healthcare_delete);
        final TextView alertText        = (TextView) popupView.findViewById(R.id.txt_careteaminvite_alerttext);
        healthcareAvatar = (ImageButton) popupView.findViewById(R.id.img_careteam_health_care_avatar);

        buttonSave.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_careteam_healthcare_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupWindow.isFocusable();

        healthcareAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertText.setVisibility(View.INVISIBLE);
                popupWindow.dismiss();
            }
        });

        healthcareAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHealthcareAvatars();
            }
        });

        buttonCancel.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertText.setVisibility(View.INVISIBLE);
                 popupWindow.dismiss();
            }
        });

        buttonSave.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String titleString = editTitle.getText().toString();
                String phoneNumber1String = editPhoneNumber1.getText().toString();

                //FirstName and email must be specified, the others will get emptystring if not specified
                if ((!titleString.isEmpty()) && (!phoneNumber1String.isEmpty())){
                    alertText.setVisibility(View.INVISIBLE);
                    HealthCare newHealthcare = new HealthCare(
                            0,
                            connectHandler.patient.patient_ID,
                            titleString,
                            editName.getText().toString(),
                            editDepartment.getText().toString(),
                            editPhoneNumber1.getText().toString(),
                            editPhoneNumber2.getText().toString(),
                            editPhoneNumber3.getText().toString(),
                            editEmail.getText().toString(),
                            selectedHealthcareAvatar);


                    connectHandler.createHealthcare(newHealthcare);

                    healthcareList.add(newHealthcare);

                    healthCareAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();

                }else{
                    // notify user they need to add
                    alertText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void inviteCareTeamMember() {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.careteam_member_popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText editFirstName        = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final EditText editLastName         = (EditText) popupView.findViewById(R.id.txt_lastName_careteam);
        final EditText editPhoneNumber      = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final EditText editEmail            = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final EditText editRelation         = (EditText) popupView.findViewById(R.id.txt_careteam_relation);
        final Spinner  editAdmin            = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] spinnerAdminValues   = {"Yes", "No"};
        final Button   buttonSave           = (Button) popupView.findViewById(R.id.btn_careteam_save);
        final Button   buttonCancel         = (Button) popupView.findViewById(R.id.btn_careteam_cancel);
        final Button   buttonEdit           = (Button) popupView.findViewById(R.id.btn_careteam_edit);
        final TextView alertText            = (TextView) popupView.findViewById(R.id.txt_careteam_invite_alert);

        buttonSave.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);

        Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
        adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_admin.setAdapter(adapter_admin);

        familyAvatar = (ImageButton) popupView.findViewById(R.id.img_careteam_family_avatar);
        familyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyAvatars();
            }
        });


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
                                                    selectedFamilyAvatar,
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
                            newInvite.invited_avatar,
                            newInvite.invited_admin);

                    familyList.add(invitedCareTeamMember);

                    familyAdapter.notifyDataSetChanged();
                    popupWindow.dismiss();

                }else{
                    // notify user they need to add
                    alertText.setVisibility(View.VISIBLE);
                }
            }
        });

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_careteam_member_popup);
        //  LinearLayout layout = (LinearLayout) findViewById(R.id.careTeamScreen);
//        popupWindow.showAsDropDown(gridLayout, 500, 20);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupWindow.isFocusable();

    }

    public void showCareTeamMember(final int listPosition) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.careteam_member_popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText editFirstName        = (EditText) popupView.findViewById(R.id.txt_firstName_careteam);
        final EditText editLastName         = (EditText) popupView.findViewById(R.id.txt_lastName_careteam);
        final EditText editPhoneNumber      = (EditText) popupView.findViewById(R.id.txt_phone_careteam);
        final EditText editEmail            = (EditText) popupView.findViewById(R.id.txt_email_careteam);
        final EditText editRelation         = (EditText) popupView.findViewById(R.id.txt_careteam_relation);
        final Spinner  spinnerAdmin         = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
        final String[] spinnerAdminValues   = {"Yes", "No"};
        final TextView alertText            = (TextView) popupView.findViewById(R.id.txt_careteam_invite_alert);
        final Button   buttonSave           = (Button) popupView.findViewById(R.id.btn_careteam_save);
        final Button   buttonCancel         = (Button) popupView.findViewById(R.id.btn_careteam_cancel);
        final Button   buttonEdit           = (Button) popupView.findViewById(R.id.btn_careteam_edit);
        final Button   buttonDelete         = (Button) popupView.findViewById(R.id.btn_careteam_delete);
        familyAvatar = (ImageButton) popupView.findViewById(R.id.img_careteam_family_avatar);
        switch(familyList.get(listPosition).avatar){
            case 1:
                familyAvatar.setImageResource(R.drawable.family_avatar_1);
                break;
            case 2:
                familyAvatar.setImageResource(R.drawable.family_avatar_2);
                break;
            case 3:
                familyAvatar.setImageResource(R.drawable.family_avatar_3);
                break;
            case 4:
                familyAvatar.setImageResource(R.drawable.family_avatar_4);
                break;
            case 5:
                familyAvatar.setImageResource(R.drawable.family_avatar_5);
                break;
            case 6:
                familyAvatar.setImageResource(R.drawable.family_avatar_6);
                break;
            case 7:
                familyAvatar.setImageResource(R.drawable.family_avatar_7);
                break;
            case 8:
                familyAvatar.setImageResource(R.drawable.family_avatar_8);
                break;
            case 9:
                familyAvatar.setImageResource(R.drawable.family_avatar_9);
                break;
            case 10:
                familyAvatar.setImageResource(R.drawable.family_avatar_10);
                break;
            case 11:
                familyAvatar.setImageResource(R.drawable.family_avatar_11);
                break;
            case 12:
                familyAvatar.setImageResource(R.drawable.family_avatar_12);
                break;
            case 13:
                familyAvatar.setImageResource(R.drawable.family_avatar_13);
                break;
            case 14:
                familyAvatar.setImageResource(R.drawable.family_avatar_14);
                break;
            case 15:
                familyAvatar.setImageResource(R.drawable.family_avatar_15);
                break;
            case 16:
                familyAvatar.setImageResource(R.drawable.family_avatar_16);
                break;
            case 17:
                familyAvatar.setImageResource(R.drawable.family_avatar_17);
                break;
            case 18:
                familyAvatar.setImageResource(R.drawable.family_avatar_18);
                break;
        }

        editFirstName.setText(familyList.get(listPosition).first_name);
        editLastName.setText(familyList.get(listPosition).last_name);
        editEmail.setText(familyList.get(listPosition).email);
        editRelation.setText(familyList.get(listPosition).relationship);

        editFirstName.setFocusable(false);
        editLastName.setFocusable(false);
        editEmail.setFocusable(false);
        editPhoneNumber.setFocusable(false);
        editRelation.setFocusable(false);

        ArrayAdapter<String> adapterAdmin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
        adapterAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdmin.setAdapter(adapterAdmin);

        spinnerAdmin.setEnabled(false);

        buttonEdit.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonSave.setVisibility(View.INVISIBLE);

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_careteam_member_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

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

                Person updatePerson = new Person(familyList.get(listPosition).person_ID,
                                                    editFirstName.getText().toString(),
                                                    editLastName.getText().toString(),
                                                    editEmail.getText().toString(),
                                                    editPhoneNumber.getText().toString(),
                                                    selectedFamilyAvatar,
                                                    null);

                connectHandler.updateUser(updatePerson);
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
                cancelEdit(listPosition);
            }

            private void cancelEdit(int MLp) {
                Spinner spinner_admin = (Spinner) popupView.findViewById(R.id.spinner_admin_careteam);
                ArrayAdapter<String> adapter_admin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
                adapter_admin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_admin.setAdapter(adapter_admin);
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

                healthCareAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        familyAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFamilyAvatars();
            }
        });
    }

    public void showHealthcare(final int gridPosition) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.careteam_healthcare_popup, null);

        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.update();

        final EditText editTitle        = (EditText) popupView.findViewById(R.id.edittext_healthcare_title);
        final EditText editName         = (EditText) popupView.findViewById(R.id.edittext_healthcare_name);
        final EditText editDepartment   = (EditText) popupView.findViewById(R.id.edittext_healthcare_department);
        final EditText editPhoneNumber1 = (EditText) popupView.findViewById(R.id.edittext_healthcare_phonenumber1);
        final EditText editPhoneNumber2 = (EditText) popupView.findViewById(R.id.edittext_healthcare_phonenumber2);
        final EditText editPhoneNumber3 = (EditText) popupView.findViewById(R.id.edittext_healthcare_phonenumber3);
        final EditText editEmail        = (EditText) popupView.findViewById(R.id.edittext_healthcare_email);
        final Button   buttonSave       = (Button) popupView.findViewById(R.id.btn_healthcare_save);
        final Button   buttonCancel     = (Button) popupView.findViewById(R.id.btn_healthcare_cancel);
        final Button   buttonEdit       = (Button) popupView.findViewById(R.id.btn_healthcare_edit);
        final Button   buttonDelete     = (Button) popupView.findViewById(R.id.btn_healthcare_delete);
        final TextView alertText        = (TextView) popupView.findViewById(R.id.txt_careteaminvite_alerttext);
        healthcareAvatar = (ImageButton) popupView.findViewById(R.id.img_careteam_health_care_avatar);
        switch(healthcareList.get(gridPosition).avatar) {
            case 1:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_female);
                break;
            case 2:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_nurse);
                break;
            case 3:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_anestetist);
                break;
            case 4:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_male);
                break;
            case 5:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_surgeon);
                break;
        }
        buttonSave.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);
        buttonDelete.setVisibility(View.VISIBLE);

        editTitle.setText(healthcareList.get(gridPosition).title);
        editName.setText(healthcareList.get(gridPosition).name);
        editDepartment.setText(healthcareList.get(gridPosition).department);
        editPhoneNumber1.setText(healthcareList.get(gridPosition).phone_number1);
        editPhoneNumber2.setText(healthcareList.get(gridPosition).phone_number2);
        editPhoneNumber3.setText(healthcareList.get(gridPosition).phone_number3);
        editEmail.setText(healthcareList.get(gridPosition).email);

        editTitle.setFocusable(false);
        editName.setFocusable(false);
        editDepartment.setFocusable(false);
        editPhoneNumber1.setFocusable(false);
        editPhoneNumber2.setFocusable(false);
        editPhoneNumber3.setFocusable(false);
        editEmail.setFocusable(false);

        buttonEdit.setVisibility(View.INVISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonSave.setVisibility(View.VISIBLE);

        relativeLayout = (RelativeLayout) popupView.findViewById(R.id.layout_careteam_healthcare_popup);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        healthcareAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHealthcareAvatars();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                saveHealthCare(gridPosition);
                popupWindow.dismiss();
            }

            private void saveHealthCare(int gridPosition) {

                HealthCare newHealthCare = new HealthCare(
                        0, //patient_ID will be set when creating
                        connectHandler.patient.patient_ID,
                        editTitle.getText().toString(),
                        editName.getText().toString(),
                        editDepartment.getText().toString(),
                        editPhoneNumber1.getText().toString(),
                        editPhoneNumber2.getText().toString(),
                        editPhoneNumber3.getText().toString(),
                        editEmail.getText().toString(),
                        selectedHealthcareAvatar);

                while (connectHandler.socketBusy){}

                healthcareList.add(newHealthCare);
                healthCareAdapter.notifyDataSetChanged();
            }

        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.VISIBLE);
                buttonCancel.setVisibility(View.VISIBLE);
                prepareForEdit(gridPosition);
            }

            private void prepareForEdit(int gridPosition) {
/*
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
*/
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
            }

        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                buttonSave.setVisibility(View.INVISIBLE);
                buttonCancel.setVisibility(View.INVISIBLE);
                buttonDelete.setVisibility(View.INVISIBLE);
                deleteHealthcare(gridPosition);
            }
            private void deleteHealthcare(int gridPosition) {

                connectHandler.deleteHealthcare(healthcareList.get(gridPosition).healthcare_ID);
                healthCareAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });
    }

    private void journeyActivity(){
        Intent myIntent = new Intent(this, JourneyActivity.class);
        startActivity(myIntent);
        finish();

    }

    private void journalActivity(){
        Intent myIntent = new Intent(this, JournalActivity.class);
        startActivity(myIntent);
        finish();
    }

    void showFamilyAvatars(){
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View avatarView = layoutInflater.inflate(R.layout.careteam_popup_family_avatars, null);
        final PopupWindow avatarWindow = new PopupWindow(avatarView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final ImageButton avatar1 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_1);
        final ImageButton avatar2 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_2);
        final ImageButton avatar3 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_3);
        final ImageButton avatar4 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_4);
        final ImageButton avatar5 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_5);
        final ImageButton avatar6 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_6);
        final ImageButton avatar7 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_7);
        final ImageButton avatar8 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_8);
        final ImageButton avatar9 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_9);
        final ImageButton avatar10 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_10);
        final ImageButton avatar11 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_11);
        final ImageButton avatar12 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_12);
        final ImageButton avatar13 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_13);
        final ImageButton avatar14 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_14);
        final ImageButton avatar15 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_15);
        final ImageButton avatar16 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_16);
        final ImageButton avatar17 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_17);
        final ImageButton avatar18 = (ImageButton) avatarView.findViewById(R.id.btn_family_avatar_18);

        avatarWindow.setFocusable(true);
        avatarWindow.update();

//        final ImageButton buttonEmotionHappy = (ImageButton) avatarView.findViewById(R.id.btn_emotion_happy);

        RelativeLayout relativeLayout = (RelativeLayout) avatarView.findViewById(R.id.family_avatars_popup);
        avatarWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 1;
                familyAvatar.setImageResource(R.drawable.family_avatar_1);
                avatarWindow.dismiss();

            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 2;
                familyAvatar.setImageResource(R.drawable.family_avatar_2);
                avatarWindow.dismiss();

            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 3;
                familyAvatar.setImageResource(R.drawable.family_avatar_3);
                avatarWindow.dismiss();

            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 4;
                familyAvatar.setImageResource(R.drawable.family_avatar_4);
                avatarWindow.dismiss();

            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 5;
                familyAvatar.setImageResource(R.drawable.family_avatar_5);
                avatarWindow.dismiss();

            }
        });

        avatar6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 6;
                familyAvatar.setImageResource(R.drawable.family_avatar_6);
                avatarWindow.dismiss();

            }
        });

        avatar7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 7;
                familyAvatar.setImageResource(R.drawable.family_avatar_7);
                avatarWindow.dismiss();

            }
        });

        avatar8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 8;
                familyAvatar.setImageResource(R.drawable.family_avatar_8);
                avatarWindow.dismiss();

            }
        });

        avatar9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 9;
                familyAvatar.setImageResource(R.drawable.family_avatar_9);
                avatarWindow.dismiss();

            }
        });

        avatar10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 10;
                familyAvatar.setImageResource(R.drawable.family_avatar_10);
                avatarWindow.dismiss();

            }
        });

        avatar11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 11;
                familyAvatar.setImageResource(R.drawable.family_avatar_11);
                avatarWindow.dismiss();

            }
        });
        avatar12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 12;
                familyAvatar.setImageResource(R.drawable.family_avatar_12);
                avatarWindow.dismiss();

            }
        });
        avatar13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 13;
                familyAvatar.setImageResource(R.drawable.family_avatar_13);
                avatarWindow.dismiss();

            }
        });
        avatar14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 14;
                familyAvatar.setImageResource(R.drawable.family_avatar_14);
                avatarWindow.dismiss();

            }
        });
        avatar15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 15;
                familyAvatar.setImageResource(R.drawable.family_avatar_15);
                avatarWindow.dismiss();

            }
        });
        avatar16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 16;
                familyAvatar.setImageResource(R.drawable.family_avatar_16);
                avatarWindow.dismiss();

            }
        });
        avatar17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 17;
                familyAvatar.setImageResource(R.drawable.family_avatar_17);
                avatarWindow.dismiss();

            }
        });
        avatar18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFamilyAvatar = 18;
                familyAvatar.setImageResource(R.drawable.family_avatar_18);
                avatarWindow.dismiss();

            }
        });
    }

    void showHealthcareAvatars(){
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View avatarView = layoutInflater.inflate(R.layout.careteam_popup_healthcare_avatars, null);
        final PopupWindow avatarWindow = new PopupWindow(avatarView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final ImageButton avatar1 = (ImageButton) avatarView.findViewById(R.id.btn_healthcare_avatar_1);
        final ImageButton avatar2 = (ImageButton) avatarView.findViewById(R.id.btn_healthcare_avatar_2);
        final ImageButton avatar3 = (ImageButton) avatarView.findViewById(R.id.btn_healthcare_avatar_3);
        final ImageButton avatar4 = (ImageButton) avatarView.findViewById(R.id.btn_healthcare_avatar_4);
        final ImageButton avatar5 = (ImageButton) avatarView.findViewById(R.id.btn_healthcare_avatar_5);

        avatarWindow.setFocusable(true);
        avatarWindow.update();

        RelativeLayout relativeLayout = (RelativeLayout) avatarView.findViewById(R.id.healthcare_avatars_popup);
        avatarWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        avatar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHealthcareAvatar = 1;
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_female);
                avatarWindow.dismiss();

            }
        });

        avatar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHealthcareAvatar = 2;
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_nurse);
                avatarWindow.dismiss();

            }
        });

        avatar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHealthcareAvatar = 3;
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_anestetist);
                avatarWindow.dismiss();

            }
        });

        avatar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHealthcareAvatar = 4;
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_male);
                avatarWindow.dismiss();

            }
        });

        avatar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedHealthcareAvatar = 5;
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_surgeon);
                avatarWindow.dismiss();

            }
        });

    }
}
