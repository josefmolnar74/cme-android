package com.cancercarecompany.ccc.ccc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements AvatarsDialogFragment.OnCompleteListener {

    public static final int AVATARSDIALOG_FRAGMENT = 1; // class variable

    private ConnectionHandler connectHandler;
    private boolean passwordUpdate = false;
    private int familyAvatarId;
    private EditText editName;
    private EditText editEmail;
    private EditText editRelation;
    private ImageButton familyAvatar;
    private LinearLayout passwordLayout;

    public void onComplete(int avatarId) {
        familyAvatarId = avatarId;
        setAvatarResource(familyAvatarId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        connectHandler = ConnectionHandler.getInstance();

        editName = (EditText) findViewById(R.id.etxt_settings_name);
        editEmail = (EditText) findViewById(R.id.etx_settings_email);
        editRelation = (EditText) findViewById(R.id.etxt_settings_relation);
        familyAvatar = (ImageButton) findViewById(R.id.img_settings_avatar);
        passwordLayout = (LinearLayout) findViewById(R.id.layout_settings_update_password);
        editName.setText(connectHandler.person.name);
        editEmail.setText(connectHandler.person.email);
        familyAvatarId = connectHandler.person.avatar;
        setAvatarResource(connectHandler.person.avatar);

        // Get Relationship
        for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
            if (connectHandler.patient.care_team.get(i).person_ID == connectHandler.person.person_ID) {
                editRelation.setText(connectHandler.patient.care_team.get(i).relationship);
            }
        }
    }

    public void onClickAvatar(View view){
        // Open avatar dialog fragment and use bundle to send family avatar type
        AvatarsDialogFragment avatarFragment = new AvatarsDialogFragment();
        Bundle args = new Bundle();
        args.putString(AvatarsDialogFragment.AVATAR_TYPE, AvatarsDialogFragment.AVATAR_FAMILY);
        avatarFragment.setArguments(args);
//        avatarFragment.setTargetFragment(get, AVATARSDIALOG_FRAGMENT);
        avatarFragment.show(getSupportFragmentManager(), "Josef");
    }

    public void onClickSettingsUpdate(View view){

    }

    public void onClickSettingsDelete(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String alertText = String.format(getString(R.string.remove_question));

        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton(getText(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
//                connectHandler.deleteUser(connectHandler.person.person_ID);
            }
        });

        alertDialogBuilder.setNegativeButton(getText(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onClickSettingsShowPassword(View view){
        if (!passwordUpdate){
            passwordLayout.setVisibility(View.VISIBLE);
            passwordUpdate = true;
        }else{
            passwordLayout.setVisibility(View.GONE);
            passwordUpdate = false;
        }
    }

    public void onClickSettingsUpdatePassword(View view){

    }

    private void saveUser() {

        String nameString = editName.getText().toString();
        String emailString = editEmail.getText().toString();

        //FirstName and email must be specified, the others will get emptystring if not specified
        boolean wrongInput = false;

        if ((nameString.isEmpty()) || (emailString.isEmpty())){
            wrongInput = true;
        }

        if (!wrongInput){
            // update my information
            Person updatedPerson = new Person(
                    connectHandler.person.person_ID,
                    nameString,
                    emailString,
                    null,
                    familyAvatarId,
                    null);
            connectHandler.updateUser(updatedPerson);
            this.onBackPressed();
        }
    }

    private void setAvatarResource(Integer familyAvatarId){
        switch (familyAvatarId) {
            case 0:
                familyAvatar.setImageResource(R.drawable.addcontact);
                break;
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
    }
}
