package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

public class JoinCareTeamActivity extends AppCompatActivity {

    ConnectionHandler connectHandler;
    EditText emailJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_care_team);
        emailJoin = (EditText) findViewById(R.id.txt_email_join_careteam);
        connectHandler = ConnectionHandler.getInstance();
    }

    public void onClickJoinCareTeamNext(View view){
        connectHandler.findUser(emailJoin.getText().toString());

        while (connectHandler.person == null){}

        String patientName = connectHandler.person.patient.get(0).patient_name;
        if (patientName != null){
            joinCareTeam(patientName);
        }
    }

    public void joinCareTeam(String patientName){
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.join_care_team_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, 600, 600);
        popupWindow.setFocusable(true);
        popupWindow.update();
        popupWindow.showAsDropDown(relativeLayout, 560, -200);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
