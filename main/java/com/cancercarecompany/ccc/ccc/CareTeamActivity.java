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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

public class CareTeamActivity extends AppCompatActivity {

    public int selectedFamilyAvatar;
    public int selectedHealthcareAvatar;
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
        ft.replace(R.id.careteam_placeholder1, myCareTeamExpList);
        ft.commit();

        final Button buttonAddMember = (Button) findViewById(R.id.btn_add);

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

/*
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

*/

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