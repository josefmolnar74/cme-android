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

public class CareTeamShowHealthcareFragment extends Fragment {

    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_care_team_show_healthcare, container, false);
        final EditText editTitle = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_title);
        final EditText editDepartment = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_department);
        final EditText editName = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_name);
        final EditText editEmail = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_email);
        final EditText editPhone1 = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_phone1);
        final EditText editPhone2 = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_phone2);
        final EditText editPhone3 = (EditText) view.findViewById(R.id.etxt_careteam_healthcare_phone3);
        final ImageButton healthcareAvatar = (ImageButton) view.findViewById(R.id.img_careteam_healthcare_avatar);
        final ImageButton buttonEdit = (ImageButton) view.findViewById(R.id.btn_edit);
        final TextView txtSave = (TextView) view.findViewById(R.id.txt_save);
        int healthcareAvatarId = 0;

        for (int i = 0; i < connectHandler.patient.care_team.size(); i++) {
            if (connectHandler.healthcare.healthcare_data.get(i).healthcare_ID == listItem.id) {
                position = i;
                healthcareAvatarId = connectHandler.healthcare.healthcare_data.get(position).avatar;
                editTitle.setText(connectHandler.healthcare.healthcare_data.get(position).title);
                editDepartment.setText(connectHandler.healthcare.healthcare_data.get(position).department);
                editName.setText(connectHandler.healthcare.healthcare_data.get(position).name);
                editPhone1.setText(connectHandler.healthcare.healthcare_data.get(position).phone_number1);
                editPhone2.setText(connectHandler.healthcare.healthcare_data.get(position).phone_number2);
                editPhone3.setText(connectHandler.healthcare.healthcare_data.get(position).phone_number3);
                break;
            }
        }

        txtSave.setVisibility(View.INVISIBLE);
        buttonEdit.setVisibility(View.VISIBLE);

        switch (healthcareAvatarId) {
            case 1:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_anestetist);
                break;
            case 2:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_male);
                break;
            case 3:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_surgeon);
                break;
            case 4:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_doctor_female);
                break;
            case 5:
                healthcareAvatar.setImageResource(R.drawable.avatar_healthcare_nurse);
                break;
        }


//        healthcareAvatarId.setFocusable(false);
        editTitle.setFocusable(false);
        editDepartment.setFocusable(false);
        editName.setFocusable(false);
        editPhone1.setFocusable(false);
        editPhone2.setFocusable(false);
        editPhone3.setFocusable(false);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSave.setVisibility(View.VISIBLE);
                buttonEdit.setVisibility(View.INVISIBLE);
                editTitle.setFocusable(true);
                editTitle.setFocusableInTouchMode(true);
                editTitle.setEnabled(true);
                editDepartment.setFocusable(true);
                editDepartment.setFocusableInTouchMode(true);
                editDepartment.setEnabled(true);
                editName.setFocusable(true);
                editName.setFocusableInTouchMode(true);
                editName.setEnabled(true);
                editEmail.setFocusable(true);
                editEmail.setFocusableInTouchMode(true);
                editEmail.setEnabled(true);
                editPhone1.setFocusable(true);
                editPhone1.setFocusableInTouchMode(true);
                editPhone1.setEnabled(true);
                editPhone2.setFocusable(true);
                editPhone2.setFocusableInTouchMode(true);
                editPhone2.setEnabled(true);
                editPhone3.setFocusable(true);
                editPhone3.setFocusableInTouchMode(true);
                editPhone3.setEnabled(true);
            }

        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonEdit.setVisibility(View.VISIBLE);
                txtSave.setVisibility(View.INVISIBLE);

                HealthCare updateHealthCare = new HealthCare(
                        connectHandler.healthcare.healthcare_data.get(position).healthcare_ID,
                        connectHandler.patient.patient_ID,
                        editTitle.getText().toString(),
                        editName.getText().toString(),
                        editDepartment.getText().toString(),
                        editPhone1.getText().toString(),
                        editPhone2.getText().toString(),
                        editPhone3.getText().toString(),
                        editEmail.getText().toString(),
                        connectHandler.healthcare.healthcare_data.get(position).avatar);

                connectHandler.updateHealthcare(updateHealthCare);

                while (connectHandler.socketBusy){}

                //update healthcareList as well
/*
                healthcareList.get(gridPosition).name = editName.getText().toString();
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