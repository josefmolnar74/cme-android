package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JoinCareTeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_care_team);
    }

    public void onClickJoinCareTeamNext(View view){
        Intent intent = new Intent(this, LoginActivity.class);
//        intent.putExtra("patient_name", patientName.getText().toString());
//        intent.putExtra("year_of_birth", yearOfBirth.getText().toString());
//        intent.putExtra("patient_name", diagnose.getText().toString());
        startActivity(intent);
    }

}
