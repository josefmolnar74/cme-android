package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class CreateCareTeamActivity extends AppCompatActivity {

    EditText patientName;
    EditText yearOfBirth;
    EditText diagnose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_care_team);
        patientName = (EditText) findViewById(R.id.text_create_careteam_patientname);
        yearOfBirth = (EditText) findViewById(R.id.text_create_careteam_year_of_birth);
        diagnose = (EditText) findViewById(R.id.text_create_careteam_diagnose);

        // Statusbar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    public void onClickCreateCareTeamNext(View view){
        // Go to login window only when all input has been filled in
        Intent intent = new Intent(this, RegisterActivity.class);
        String patientNameString = (String) patientName.getText().toString();
        String yearOfBirthString = (String)yearOfBirth.getText().toString();
        String diagnoseString = (String) diagnose.getText().toString();

        if (!patientNameString.isEmpty() && !yearOfBirthString.isEmpty() && !diagnoseString.isEmpty()){
            intent.putExtra(RegisterActivity.PATIENT_NAME, patientNameString);
            intent.putExtra(RegisterActivity.YEAR_OF_BIRTH, yearOfBirthString);
            intent.putExtra(RegisterActivity.DIAGNOSE, diagnoseString);
            startActivity(intent);
            finish();
        }
        // Add message to explain that all strings needs to be provided
    }
}
