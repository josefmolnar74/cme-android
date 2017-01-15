package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

    private ConnectionHandler connectHandler;
    private EditText loginEmail;
    private EditText loginPassword;
    private CheckBox loginSave;
    private boolean autoLogin;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectHandler = ConnectionHandler.getInstance(); //initialize socket and server connection
        setContentView(R.layout.welcome_splash_screen);
        //Check if username and password has been saved in share preferences
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = this.getSharedPreferences("login_settings", Context.MODE_PRIVATE);
        autoLogin = sharedPref.getBoolean(getString(R.string.login_auto_login), false);
        if (autoLogin){
            Person newUser = new Person(0, null,
                    sharedPref.getString(getString(R.string.login_saved_email), ""),
                    sharedPref.getString(getString(R.string.login_saved_password), ""),
                    0, null);
            connectHandler.login(newUser);

            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!connectHandler.pendingMessage) {
                        login();
                    }
                    else {
                        handler.postDelayed(this,1000);
                    }
                }
            });
        }
        else{
            setContentView(R.layout.activity_welcome);
            loginEmail = (EditText) findViewById(R.id.text_login_email);
            loginPassword = (EditText) findViewById(R.id.text_login_password);
            loginSave = (CheckBox) findViewById(R.id.checkBox_save_login);

            // Statusbar color
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
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
//        finish();
    }

    public void onClickLogin(View view){
        Person newUser = new Person(0, null, loginEmail.getText().toString(), loginPassword.getText().toString(), 0, null);
        connectHandler.login(newUser);
        mAuthTask = new UserLoginTask();
        mAuthTask.execute((Void) null);
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
            SharedPreferences sharedPref = this.getSharedPreferences("login_settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            if (!sharedPref.getBoolean(getString(R.string.login_auto_login), false)){
                // if not auto login
                editor.putString(getString(R.string.login_saved_email),loginEmail.getText().toString());
                editor.putString(getString(R.string.login_saved_password),loginPassword.getText().toString());
                if (loginSave.isChecked()) {
                    editor.putBoolean(getString(R.string.login_auto_login), true);
                } else{
                    editor.putBoolean(getString(R.string.login_auto_login), false);
                }
                editor.commit();
                onRestart();
            }

            // Get all patient data after login
            connectHandler.getAllPatientData(connectHandler.patient.patient_ID);
            final Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!connectHandler.pendingMessage) {
                        Intent myIntent = new Intent(MyApplication.getContext(), MainActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                    else {
                        handler.postDelayed(this,1000);
                    }
                }
            });
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            while(connectHandler.pendingMessage){}
            if (connectHandler.person != null){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                login();
            } else {
                // Login failed
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyApplication.getContext());
                String alertText = String.format("Server not responding");
                alertDialogBuilder.setMessage(alertText);

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences sharedPref = MyApplication.getContext().getSharedPreferences("login_settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean(getString(R.string.login_auto_login), false);
                        editor.commit();
                        onRestart();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
