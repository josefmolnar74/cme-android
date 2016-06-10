package com.cancercarecompany.ccc.ccc;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JoinCareTeamActivity extends AppCompatActivity {

    ConnectionHandler connectHandler;

    TextView textAddEmail;
    EditText inputEmail;
    Button buttonFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_care_team);
        textAddEmail = (TextView) findViewById(R.id.text_add_email_join_careteam);
        inputEmail = (EditText) findViewById(R.id.text_email_join_careteam);
        buttonFind = (Button) findViewById(R.id.button_find_join_careteam);
        connectHandler = ConnectionHandler.getInstance();
    }

    public void onClickJoinCareTeamNext(View view){
        connectHandler.findUser(inputEmail.getText().toString());
   //     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        while (connectHandler.person == null){}

        String patientName = connectHandler.person.patient.get(0).patient_name;
        if (patientName != null){
           joinCareTeam(patientName);
        }
    }

//        buttonFind.setVisibility(View.INVISIBLE);
//        textAddEmail.setVisibility(View.INVISIBLE);
//        inputEmail.setVisibility(View.INVISIBLE);

    public void joinCareTeam(String patientName){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String alertText = String.format("Do you want to join %s care team?", patientName);
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                login();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputEmail.setText("");
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(LoginActivity.INVITED_EMAIL, connectHandler.person.email);
        startActivity(intent);
    }
}
