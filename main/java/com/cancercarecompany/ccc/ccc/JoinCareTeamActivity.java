package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class JoinCareTeamActivity extends AppCompatActivity {

    EditText emailJoin;

    private Socket socket = new Socket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_care_team);
        emailJoin = (EditText) findViewById(R.id.txt_email_join_careteam);
    }

    public void onClickJoinCareTeamNext(View view){
        socket.findUser(emailJoin.getText().toString());

        while (socket.person == null){

        }
        String patientName = (String) socket.person.patient.get(0).patient_name;
        Intent intent = new Intent(this, LoginActivity.class);
//        intent.putExtra("socket", patientName.getText().toString());
        startActivity(intent);
    }
}
