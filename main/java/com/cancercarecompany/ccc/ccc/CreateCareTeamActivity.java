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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_care_team);
        patientName = (EditText) findViewById(R.id.txt_patient_name);
        patientName = (EditText) findViewById(R.id.txt_year_of_birth);
        patientName = (EditText) findViewById(R.id.txt_diagnose);
//        patientName.setInputType((InputType.TYPE_NULL);
    }

    public void onClickCreateCareTeam(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("patient_name", patientName.getText().toString());
        intent.putExtra("year_of_birth", yearOfBirth.getText().toString());
        intent.putExtra("patient_name", diagnose.getText().toString());
        startActivity(intent);
    }
}
