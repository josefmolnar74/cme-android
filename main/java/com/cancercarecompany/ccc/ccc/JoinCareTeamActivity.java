package com.cancercarecompany.ccc.ccc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        connectHandler.findCareTeamInvite(inputEmail.getText().toString());
   //     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        while (connectHandler.socketBusy){}

        if (connectHandler.invite == null)
        {
            alertNoCareTeam();
        }else
        {
            joinCareTeam(connectHandler.invite.patient_name);
        }
    }

    public void alertNoCareTeam(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String alertText = String.format("No care team found to join");
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                inputEmail.setText("");
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void joinCareTeam(String patientName){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        String alertText = String.format("Do you want to join %s care team?", patientName);
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                register();
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

    public void register(){
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(RegisterActivity.INVITED_EMAIL, inputEmail.getText().toString());
        startActivity(intent);
    }
}
