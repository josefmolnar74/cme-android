package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class CreateCareTeamActivity extends AppCompatActivity {

    EditText patientName;
    EditText yearOfBirth;
    EditText diagnose;
    EditText relationship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_care_team);
        patientName = (EditText) findViewById(R.id.txt_patient_name);
        yearOfBirth = (EditText) findViewById(R.id.txt_year_of_birth);
        diagnose = (EditText) findViewById(R.id.txt_diagnose);
        relationship = (EditText) findViewById(R.id.txt_relationship);
//        patientName.setInputType((InputType.TYPE_NULL);
    }

    public void onClickCreateCareTeamNext(View view){
        // Go to login window only when all input has been filled in
        Intent intent = new Intent(this, LoginActivity.class);
        String patientNameString = (String) patientName.getText().toString();
        String yearOfBirthString = (String)yearOfBirth.getText().toString();
        String diagnoseString = (String) diagnose.getText().toString();
        String relationshipString = (String) relationship.getText().toString();

        if (!patientNameString.isEmpty() && !yearOfBirthString.isEmpty() && !diagnoseString.isEmpty()){
            intent.putExtra(LoginActivity.PATIENT_NAME, patientNameString);
            intent.putExtra(LoginActivity.YEAR_OF_BIRTH, yearOfBirthString);
            intent.putExtra(LoginActivity.DIAGNOSE, diagnoseString);
            intent.putExtra(LoginActivity.RELATIONSHIP, relationshipString);
            startActivity(intent);
        }
        // Add message to explain that all strings needs to be provided
    }
}
