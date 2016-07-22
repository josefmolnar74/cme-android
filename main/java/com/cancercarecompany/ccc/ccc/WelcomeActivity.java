package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {

    ConnectionHandler connectHandler;
    EditText loginEmail;
    EditText loginPassword;
    CheckBox loginSave;
    boolean autoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        connectHandler = ConnectionHandler.getInstance(); //initialize socket and server connection
        loginEmail = (EditText) findViewById(R.id.text_login_email);
        loginPassword = (EditText) findViewById(R.id.text_login_password);
        loginSave = (CheckBox) findViewById(R.id.checkBox_save_login);

        // Statusbar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));


        //Check if username and password has been saved in share preferences
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        autoLogin = sharedPref.getBoolean(getString(R.string.login_auto_login), false);
        if (autoLogin){
            loginEmail.setText(sharedPref.getString(getString(R.string.login_saved_email), ""));
            loginPassword.setText(sharedPref.getString(getString(R.string.login_saved_password), ""));
            loginSave.setChecked(true);
        }
    }

    public void onClickCreateCareTeam(View view){
        Intent intent = new Intent(this, CreateCareTeamActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickJoinCareTeam(View view){
        Intent intent = new Intent(this, JoinCareTeamActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickLogin(View view){
        Person newUser = new Person(0, null, null, loginEmail.getText().toString(), loginPassword.getText().toString(), 0, null);
        connectHandler.login(newUser);

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!connectHandler.socketBusy) {
                    login();
                }
                else {
                    handler.postDelayed(this,1000);
                }
            }
        });
    }

    private void login(){
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
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.login_saved_email),loginEmail.getText().toString());
            editor.putString(getString(R.string.login_saved_password),loginPassword.getText().toString());
            if (loginSave.isChecked()) {
                editor.putBoolean(getString(R.string.login_auto_login), true);
            } else{
                editor.putBoolean(getString(R.string.login_auto_login), false);
            }

            editor.commit();

            Intent myIntent = new Intent(this, CareTeamActivity.class);
            startActivity(myIntent);
            finish();
        }
    }


}
