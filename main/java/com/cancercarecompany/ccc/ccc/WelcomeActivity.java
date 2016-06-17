package com.cancercarecompany.ccc.ccc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

public class WelcomeActivity extends AppCompatActivity {

    ConnectionHandler connectHandler;
    EditText loginEmail;
    EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        connectHandler = ConnectionHandler.getInstance(); //initialize socket and server connection
        loginEmail = (EditText) findViewById(R.id.text_login_email);
        loginPassword = (EditText) findViewById(R.id.text_login_password);
    }

    public void onClickCreateCareTeam(View view){
        Intent intent = new Intent(this, CreateCareTeamActivity.class);
        startActivity(intent);
    }

    public void onClickJoinCareTeam(View view){
        Intent intent = new Intent(this, JoinCareTeamActivity.class);
        startActivity(intent);
    }

    public void onClickLogin(View view){
        login();
    }

    private void login(){
        Person newUser = new Person(0, null, null, loginEmail.getText().toString(), loginPassword.getText().toString(), null);
        connectHandler.login(newUser);

        while (connectHandler.socketBusy){}

        if (connectHandler.person == null){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            String alertText = String.format("Login failed");
            alertDialogBuilder.setMessage(alertText);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    loginEmail.setText("");
                    loginPassword.setText("");
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            //Login success

            while (connectHandler.socketBusy){}
/*
            Gson gson = new Gson();
            String jsonPerson = gson.toJson(connecHandler);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Person", jsonPerson);
            editor.apply();
*/
            Intent myIntent = new Intent(this, CareTeamActivity.class);
            startActivity(myIntent);
        }
    }


}
