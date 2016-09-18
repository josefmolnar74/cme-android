package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class CareTeamShowFamilyFragment extends Fragment {

    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_care_team_show_family, container, false);
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
                        break;
                    }
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
                        break;
                    }
                }
                break;
            case "healthcare":
                break;
        }

        switch (familyAvatarId) {
            case 255:
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
            buttonEdit.setVisibility(View.INVISIBLE);
            txtName.setVisibility(View.INVISIBLE);
            txtEmail.setVisibility(View.INVISIBLE);
            txtPhoneNumber.setVisibility(View.INVISIBLE);
            txtRelation.setVisibility(View.INVISIBLE);
        }else{
            txtSave.setVisibility(View.INVISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);
            editName.setFocusable(false);
            editEmail.setFocusable(false);
            editPhoneNumber.setFocusable(false);
            editRelation.setFocusable(false);
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtName.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.VISIBLE);
                txtPhoneNumber.setVisibility(View.VISIBLE);
                txtRelation.setVisibility(View.VISIBLE);
                txtSave.setVisibility(View.VISIBLE);
                buttonEdit.setVisibility(View.INVISIBLE);
                editName.setFocusable(true);
                editName.setFocusableInTouchMode(true);
                editName.setEnabled(true);
                editEmail.setFocusable(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setEnabled(true);
                editPhoneNumber.setFocusable(true);
                editPhoneNumber.setFocusableInTouchMode(true);
                editPhoneNumber.setEnabled(true);
                editRelation.setFocusable(true);
                editRelation.setFocusableInTouchMode(true);
                editRelation.setEnabled(true);
            }

        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                txtSave.setVisibility(View.INVISIBLE);
//                updateHealthCare(gridPosition);
            }

            private void updateHealthCare(int gridPosition) {

/*                HealthCare newHealthCare = new HealthCare(
                        connectHandler.patient.get(gridPosition).healthcare_ID,
                        connectHandler.patient.patient_ID,
                         editTitle.getText().toString(),
                        txtName.getText().toString(),
                        editDepartment.getText().toString(),
                        editPhoneNumber1.getText().toString(),
                        editPhoneNumber2.getText().toString(),
                        editPhoneNumber3.getText().toString(),
                        editEmail.getText().toString(),
                        selectedHealthcareAvatar);

                connectHandler.updateHealthcare(newHealthCare);

                while (connectHandler.socketBusy){}

                //update healthcareList as well
/*
                healthcareList.get(gridPosition).name = txtName.getText().toString();
                healthcareList.get(gridPosition).title = editTitle.getText().toString();
                healthcareList.get(gridPosition).department = editDepartment.getText().toString();
                healthcareList.get(gridPosition).phone_number1 = editPhoneNumber1.getText().toString();
                healthcareList.get(gridPosition).phone_number2 = editPhoneNumber2.getText().toString();
                healthcareList.get(gridPosition).phone_number3 = editPhoneNumber3.getText().toString();
                healthcareList.get(gridPosition).email = editEmail.getText().toString();
                healthcareList.get(gridPosition).avatar = selectedHealthcareAvatar;

                healthCareAdapter.notifyDataSetChanged();
*/
            }

        });

        return view;

    }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}