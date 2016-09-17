package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class CareTeamActivity extends AppCompatActivity {

    public int selectedFamilyAvatar;
    public int selectedHealthcareAvatar;
    CareTeamFamilyAdapter familyAdapter;
    CareTeamHealthCareAdapter healthCareAdapter;
    RelativeLayout relativeLayout;
    LinearLayout wholeScreen;
    ImageButton familyAvatar;
    ImageButton healthcareAvatar;
    ConnectionHandler connectHandler;

    List<CareTeamMember> familyList;
    List<HealthCare> healthcareList;

    String languageString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careteam);
        connectHandler = ConnectionHandler.getInstance();

        Toolbar cmeToolbar = (Toolbar) findViewById(R.id.cme_toolbar);
        setSupportActionBar(cmeToolbar);
        cmeToolbar.setTitleTextColor(0xFFFFFFFF);

        // Display patient name on topbar
        if (connectHandler.patient != null) {
            getSupportActionBar().setTitle(connectHandler.patient.patient_name.concat(getString(R.string.patient_careteam)));
        }

        wholeScreen = (LinearLayout) findViewById(R.id.careTeamScreen);

        // Check language settings
        SharedPreferences prefs = this.getSharedPreferences(
                "language_settings", Context.MODE_PRIVATE);

        languageString = prefs.getString("language_settings", "");
        System.out.println("LANGUAGE SETTINGS: " + languageString);
        //////////////////////////

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CareTeamExpListFragment myCareTeamExpList = new CareTeamExpListFragment();
        ft.replace(R.id.your_placeholder1, myCareTeamExpList);
        ft.commit();

        final Button buttonAddMember = (Button) findViewById(R.id.btn_add);

        buttonAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteCareTeamMember();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_careteam:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_journey:
                journeyActivity();
                return true;

            case R.id.action_journal:
                journalActivity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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

        final EditText editFirstName        = (EditText) popupView.findViewById(R.id.etxt_careteam_firstName);
        final EditText editLastName         = (EditText) popupView.findViewById(R.id.etxt_careteam_lastname);
        final EditText editPhoneNumber      = (EditText) popupView.findViewById(R.id.etxt_careteam_phone);
        final EditText editEmail            = (EditText) popupView.findViewById(R.id.etx_careteamt_email);
        final EditText editRelation         = (EditText) popupView.findViewById(R.id.etxt_careteam_relation);
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

        buttonEdit.setVisibility(View.VISIBLE);
        buttonCancel.setVisibility(View.VISIBLE);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonSave.setVisibility(View.INVISIBLE);

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
                updateHealthCare(gridPosition);
                popupWindow.dismiss();
            }

            private void updateHealthCare(int gridPosition) {

                HealthCare newHealthCare = new HealthCare(
                        healthcareList.get(gridPosition).healthcare_ID,
                        connectHandler.patient.patient_ID,
                        editTitle.getText().toString(),
                        editName.getText().toString(),
                        editDepartment.getText().toString(),
                        editPhoneNumber1.getText().toString(),
                        editPhoneNumber2.getText().toString(),
                        editPhoneNumber3.getText().toString(),
                        editEmail.getText().toString(),
                        selectedHealthcareAvatar);

                connectHandler.updateHealthcare(newHealthCare);

                while (connectHandler.socketBusy){}

                //update healthcareList as well

                healthcareList.get(gridPosition).name = editName.getText().toString();
                healthcareList.get(gridPosition).title = editTitle.getText().toString();
                healthcareList.get(gridPosition).department = editDepartment.getText().toString();
                healthcareList.get(gridPosition).phone_number1 = editPhoneNumber1.getText().toString();
                healthcareList.get(gridPosition).phone_number2 = editPhoneNumber2.getText().toString();
                healthcareList.get(gridPosition).phone_number3 = editPhoneNumber3.getText().toString();
                healthcareList.get(gridPosition).email = editEmail.getText().toString();
                healthcareList.get(gridPosition).avatar = selectedHealthcareAvatar;

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

                editTitle.setFocusable(true);
                editTitle.setFocusableInTouchMode(true);
                editTitle.setEnabled(true);
                editName.setFocusable(true);
                editName.setFocusableInTouchMode(true);
                editName.setEnabled(true);
                editDepartment.setFocusable(true);
                editDepartment.setFocusableInTouchMode(true);
                editDepartment.setEnabled(true);
                editPhoneNumber1.setFocusable(true);
                editPhoneNumber1.setFocusableInTouchMode(true);
                editPhoneNumber1.setEnabled(true);
                editPhoneNumber2.setFocusable(true);
                editPhoneNumber2.setFocusableInTouchMode(true);
                editPhoneNumber2.setEnabled(true);
                editPhoneNumber3.setFocusable(true);
                editPhoneNumber3.setFocusableInTouchMode(true);
                editPhoneNumber3.setEnabled(true);
                editEmail.setFocusable(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setEnabled(true);

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
                healthcareList.remove(gridPosition);
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