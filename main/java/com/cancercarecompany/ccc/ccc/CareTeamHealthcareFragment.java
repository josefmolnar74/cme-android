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

public class CareTeamHealthcareFragment extends Fragment {

    private CareTeamExpandListItem listItem;
    private ConnectionHandler connectHandler;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        connectHandler = ConnectionHandler.getInstance();

        View view = inflater.inflate(R.layout.fragment_care_team_healthcare, container, false);
        final TextView txtTitle = (TextView) view.findViewById(R.id.txt_careteam_healthcare_title);
        final TextView txtDepartment = (TextView) view.findViewById(R.id.txt_careteam_healthcare_department);
        final TextView txtName = (TextView) view.findViewById(R.id.txt_careteam_healthcare_name);
        final TextView txtEmail = (TextView) view.findViewById(R.id.txt_careteam_healthcare_email);
        final TextView txtPhone1 = (TextView) view.findViewById(R.id.txt_careteam_healthcare_phone1);
        final TextView txtPhone2 = (TextView) view.findViewById(R.id.txt_careteam_healthcare_phone2);
        final TextView txtPhone3 = (TextView) view.findViewById(R.id.txt_careteam_healthcare_phone3);

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
                editEmail.setText(connectHandler.healthcare.healthcare_data.get(position).email);
                editPhone1.setText(connectHandler.healthcare.healthcare_data.get(position).phone_number1);
                editPhone2.setText(connectHandler.healthcare.healthcare_data.get(position).phone_number2);
                editPhone3.setText(connectHandler.healthcare.healthcare_data.get(position).phone_number3);
                if(editTitle.getText().toString().isEmpty()){
                    editTitle.setVisibility(View.INVISIBLE);
                }
                if(editDepartment.getText().toString().isEmpty()){
                    editDepartment.setVisibility(View.INVISIBLE);
                }
                if(editName.getText().toString().isEmpty()){
                    editName.setVisibility(View.INVISIBLE);
                }
                if(editEmail.getText().toString().isEmpty()){
                    editEmail.setVisibility(View.INVISIBLE);
                }
                if(editPhone1.getText().toString().isEmpty()){
                    editPhone1.setVisibility(View.INVISIBLE);
                }
                if(editPhone2.getText().toString().isEmpty()){
                    editPhone2.setVisibility(View.INVISIBLE);
                }
                if(editPhone3.getText().toString().isEmpty()){
                    editPhone3.setVisibility(View.INVISIBLE);
                }
                break;
            }
        }

        switch (healthcareAvatarId) {
            case 0:
                healthcareAvatar.setImageResource(R.drawable.addcontact);
                break;
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

        if (listItem.type == "new") {
            txtSave.setVisibility(View.VISIBLE);
            buttonEdit.setVisibility(View.INVISIBLE);
            editTitle.requestFocus();
            txtTitle.setVisibility(View.INVISIBLE);

        }else{
            txtSave.setVisibility(View.INVISIBLE);
            buttonEdit.setVisibility(View.VISIBLE);

        }

        editTitle.setFocusable(false);
        editDepartment.setFocusable(false);
        editName.setFocusable(false);
        editEmail.setFocusable(false);
        editPhone1.setFocusable(false);
        editPhone2.setFocusable(false);
        editPhone3.setFocusable(false);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTitle.setVisibility(View.VISIBLE);
                editDepartment.setVisibility(View.VISIBLE);
                editName.setVisibility(View.VISIBLE);
                editEmail.setVisibility(View.VISIBLE);
                editPhone1.setVisibility(View.VISIBLE);
                editPhone2.setVisibility(View.VISIBLE);
                editPhone3.setVisibility(View.VISIBLE);
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

                if (listItem.type == "new"){
                    HealthCare newHealthCare = new HealthCare(
                            0,
                            connectHandler.patient.patient_ID,
                            editTitle.getText().toString(),
                            editName.getText().toString(),
                            editDepartment.getText().toString(),
                            editPhone1.getText().toString(),
                            editPhone2.getText().toString(),
                            editPhone3.getText().toString(),
                            editEmail.getText().toString(),
                            0); // must fix avatar

                    connectHandler.createHealthcare(newHealthCare);
                }else{
                    //update existing healthcare member
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
                }

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