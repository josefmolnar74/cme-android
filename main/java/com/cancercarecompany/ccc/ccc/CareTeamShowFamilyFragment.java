package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
        final EditText editFirstName = (EditText) view.findViewById(R.id.etxt_careteam_firstName);
        final EditText editLastName = (EditText) view.findViewById(R.id.etxt_careteam_lastname);
        final EditText editPhoneNumber = (EditText) view.findViewById(R.id.etxt_careteam_phone);
        final EditText editEmail = (EditText) view.findViewById(R.id.etx_careteamt_email);
        final EditText editRelation = (EditText) view.findViewById(R.id.etxt_careteam_relation);
        final Spinner spinnerAdmin = (Spinner) view.findViewById(R.id.spinner_admin_careteam);
        final String[] spinnerAdminValues = {"Yes", "No"};
        final ImageButton familyAvatar = (ImageButton) view.findViewById(R.id.img_careteam_family_avatar);

        int familyAvatarId = 0;
        switch(listItem.type) {

            case "family":
                for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
                    if (connectHandler.patient.care_team.get(i).person_ID == listItem.id) {
                        position = i;
                        familyAvatarId = connectHandler.patient.care_team.get(position).avatar;
                        editFirstName.setText(connectHandler.patient.care_team.get(position).first_name);
                        editLastName.setText(connectHandler.patient.care_team.get(position).last_name);
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
                        editFirstName.setText(connectHandler.invites.invite_data.get(position).invited_first_name);
                        editLastName.setText(connectHandler.invites.invite_data.get(position).invited_last_name);
                        editEmail.setText(connectHandler.invites.invite_data.get(position).invited_email);
                        editRelation.setText(connectHandler.invites.invite_data.get(position).invited_relationship);
                        break;
                    }
                }
                break;
            case "healthcare":
                break;
        }

        if ((listItem.type == "family") || (listItem.type == "invite")){
            switch (familyAvatarId) {
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


        editFirstName.setFocusable(false);
        editLastName.setFocusable(false);
        editEmail.setFocusable(false);
        editPhoneNumber.setFocusable(false);
        editRelation.setFocusable(false);
/*
            ArrayAdapter<String> adapterAdmin = new ArrayAdapter<String>(CareTeamActivity.this, android.R.layout.simple_spinner_item, spinnerAdminValues);
            adapterAdmin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAdmin.setAdapter(adapterAdmin);

            spinnerAdmin.setEnabled(false);
*/
        return view;

    }

    public void setItem(CareTeamExpandListItem selectedListItem){
        listItem = selectedListItem;
    }
}