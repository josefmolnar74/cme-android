package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CareTeamFamilyFragment extends Fragment {

    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position;
    private boolean admin = false;
    private boolean myUser = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_care_team_family, container, false);
        final TextView txtName = (TextView) view.findViewById(R.id.txt_careteam_name);
        final TextView txtPhoneNumber = (TextView) view.findViewById(R.id.txt_careteam_family_phone);
        final TextView txtEmail = (TextView) view.findViewById(R.id.txt_careteam_family_email);
        final TextView txtRelation = (TextView) view.findViewById(R.id.txt_careteam_family_relation);
        final EditText editName = (EditText) view.findViewById(R.id.etxt_careteam_name);
        final EditText editPhoneNumber = (EditText) view.findViewById(R.id.etxt_careteam_phone);
        final EditText editEmail = (EditText) view.findViewById(R.id.etx_careteamt_email);
        final EditText editRelation = (EditText) view.findViewById(R.id.etxt_careteam_relation);
        final ImageButton familyAvatar = (ImageButton) view.findViewById(R.id.img_careteam_family_avatar);
        final ImageButton buttonEdit = (ImageButton) view.findViewById(R.id.btn_edit);
        final TextView txtSave = (TextView) view.findViewById(R.id.txt_save);
        final TextView alertText1 = (TextView) view.findViewById(R.id.txt_careteam_invite_alert);
        final TextView alertText2 = (TextView) view.findViewById(R.id.txt_careteam_edit_alert);
        final CheckBox chkAdmin = (CheckBox) view.findViewById(R.id.chkbx_careteam);
        alertText1.setVisibility(View.INVISIBLE);
        alertText2.setVisibility(View.INVISIBLE);
        txtSave.setVisibility(View.INVISIBLE);
        buttonEdit.setVisibility(View.INVISIBLE);
        int familyAvatarId = 0;

        switch(listItem.type) {

            case "family":
                for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
                    if (connectHandler.patient.care_team.get(i).person_ID == listItem.id) {
                        position = i;
                        familyAvatarId = connectHandler.patient.care_team.get(position).avatar;
                        editName.setText(connectHandler.patient.care_team.get(position).name);
                        editEmail.setText(connectHandler.patient.care_team.get(position).email);
                        editRelation.setText(connectHandler.patient.care_team.get(position).relationship);
                        if(editName.getText().toString().isEmpty()){
                            editName.setVisibility(View.INVISIBLE);
                        }
                        if(editEmail.getText().toString().isEmpty()){
                            editEmail.setVisibility(View.INVISIBLE);
                        }
                        if(editPhoneNumber.getText().toString().isEmpty()){
                            editPhoneNumber.setVisibility(View.INVISIBLE);
                        }
                        if(editRelation.getText().toString().isEmpty()){
                            editRelation.setVisibility(View.INVISIBLE);
                        }
                        if (connectHandler.patient.care_team.get(position).admin == 1){
                            chkAdmin.setChecked(true);
                        }
                        break;
                    }
                }
                if (connectHandler.person.person_ID == connectHandler.patient.care_team.get(position).person_ID){
                    myUser = true;
                }
                break;
            case "invite":
                for (int i = 0; i < connectHandler.invites.invite_data.size(); i++) {
                    if (connectHandler.invites.invite_data.get(i).invite_ID == listItem.id) {
                        position = i;
                        familyAvatarId = connectHandler.invites.invite_data.get(position).invited_avatar;
                        editName.setText(connectHandler.invites.invite_data.get(position).invited_name);
                        editEmail.setText(connectHandler.invites.invite_data.get(position).invited_email);
                        editRelation.setText(connectHandler.invites.invite_data.get(position).invited_relationship);
                        if (connectHandler.invites.invite_data.get(position).invited_admin == 1){
                            chkAdmin.setChecked(true);
                        }
                        break;
                    }
                }
                break;
            case "healthcare":
                break;
        }

            // check admin
        for (int i=0; i < connectHandler.patient.care_team.size(); i++){
            if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                (connectHandler.patient.care_team.get(i).admin == 1)){
                    admin = true;
            }
        }

        if (myUser || admin){
            buttonEdit.setVisibility(View.VISIBLE);
        }

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
        if (listItem.type == "new"){
            txtSave.setVisibility(View.VISIBLE);
            editName.requestFocus();
            txtName.setVisibility(View.INVISIBLE);
            txtEmail.setVisibility(View.INVISIBLE);
            txtPhoneNumber.setVisibility(View.INVISIBLE);
            txtRelation.setVisibility(View.INVISIBLE);
        }else{
            editName.setFocusable(false);
            editEmail.setFocusable(false);
            editPhoneNumber.setFocusable(false);
            editRelation.setFocusable(false);
            chkAdmin.setFocusable(false);
            chkAdmin.setEnabled(false);
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.INVISIBLE);
                txtSave.setVisibility(View.VISIBLE);
                if (myUser){
                    // Only possible to edit your own content if you are not
                    editName.setVisibility(View.VISIBLE);
                    editEmail.setVisibility(View.VISIBLE);
                    editPhoneNumber.setVisibility(View.VISIBLE);
                    editRelation.setVisibility(View.VISIBLE);
                    txtName.setVisibility(View.VISIBLE);
                    txtEmail.setVisibility(View.VISIBLE);
                    txtPhoneNumber.setVisibility(View.VISIBLE);
                    txtRelation.setVisibility(View.VISIBLE);
                    editName.setFocusable(true);
                    editName.setFocusableInTouchMode(true);
                    editName.setEnabled(true);
                    editName.requestFocus();
                    editEmail.setFocusable(true);
                    editEmail.setFocusableInTouchMode(true);
                    editEmail.setEnabled(true);
                    editPhoneNumber.setFocusable(true);
                    editPhoneNumber.setFocusableInTouchMode(true);
                    editPhoneNumber.setEnabled(true);
                    editRelation.setFocusable(true);
                    editRelation.setFocusableInTouchMode(true);
                    editRelation.setEnabled(true);
                    chkAdmin.setFocusable(true);
                    chkAdmin.setFocusableInTouchMode(true);
                    chkAdmin.setEnabled(true);
                }
                else if (admin){
                    chkAdmin.setFocusable(true);
                    chkAdmin.setFocusableInTouchMode(true);
                    chkAdmin.setEnabled(true);
                    alertText2.setVisibility(View.VISIBLE);
                }
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                txtSave.setVisibility(View.INVISIBLE);
                String invitedNameString = editName.getText().toString();
                String invitedEmailString = editEmail.getText().toString();

                //FirstName and email must be specified, the others will get emptystring if not specified
                if ((!invitedNameString.isEmpty()) && (!invitedEmailString.isEmpty())){
                    alertText1.setVisibility(View.INVISIBLE);
                    // invite new care team member
                    int admin;
                    if(chkAdmin.isChecked()) {
                        admin = 1;
                    }
                    else {
                        admin = 0;
                    }
                    Invite newInvite = new Invite(  0,
                            connectHandler.person.name,
                            connectHandler.patient.patient_ID,
                            connectHandler.patient.patient_name,
                            invitedNameString,
                            invitedEmailString,
                            editRelation.getText().toString(),
                            0,
                            admin,
                            0,
                            0);

                    connectHandler.inviteCareTeamMember(newInvite);

                    getActivity().onBackPressed();

                }
                else{
                    alertText1.setVisibility(View.INVISIBLE);

                }
/*
                    CareTeamMember invitedCareTeamMember = new CareTeamMember(
                            newInvite.person_ID,
                            newInvite.invited_name,
                            newInvite.invited_email,
                            newInvite.invited_relationship,
                            newInvite.invited_avatar,
                            newInvite.invited_admin);

                    familyList.add(invitedCareTeamMember);
                    familyAdapter.notifyDataSetChanged();
*/
           }

        });

        return view;

    }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}