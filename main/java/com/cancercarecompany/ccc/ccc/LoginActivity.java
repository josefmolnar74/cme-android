package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    EditText firstnameRegister;
    EditText lastnameRegister;
    EditText emailLogin;
    EditText passwordRegister;
    EditText passwordLogin;
    EditText emailRegister;
    TextView registerLogin;
    TextView registerRegister;
    TextView loginLogin;
    RelativeLayout registerLayout;
    RelativeLayout loginLayout;
    Button cancelButton;
    TextView registerButton;
    Lcl_work_area lcl;

    private String patientName;
    private String yearOfBirth;
    private String diagnose;
    private String relationship;
    private String invitedEmail;

    public static final String PATIENT_NAME = "patient name"; //From create care team
    public static final String YEAR_OF_BIRTH = "year of birth"; //From create care team
    public static final String DIAGNOSE = "diagnose"; //From create care team
    public static final String RELATIONSHIP = "relationship"; //From create care team
    public static final String INVITED_EMAIL   = "join email"; //From join care team

    private ConnectionHandler connectHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectHandler = ConnectionHandler.getInstance();

        firstnameRegister = (EditText) findViewById(R.id.txt_firstname_register_login);
        lastnameRegister = (EditText) findViewById(R.id.txt_lastname_register_login);
        emailLogin = (EditText) findViewById(R.id.txt_email_login_login);
        passwordRegister = (EditText) findViewById(R.id.txt_password_register_login);
        passwordLogin = (EditText) findViewById(R.id.txt_password_login_login);
        emailRegister = (EditText) findViewById(R.id.txt_email_register_login);
        registerRegister = (TextView) findViewById(R.id.btn_register_register_login);
        registerLogin = (TextView) findViewById(R.id.btn_register_login);
        loginLogin = (TextView) findViewById(R.id.btn_login_login);
        loginLayout = (RelativeLayout) findViewById(R.id.loginlayout);
        registerLayout = (RelativeLayout) findViewById(R.id.registerlayout);
        cancelButton = (Button) findViewById(R.id.btn_cancel_login);
        registerLayout.setVisibility(View.INVISIBLE);

        // Get values sent from Create team and join activity
        Intent intent = getIntent();
        patientName = intent.getStringExtra(PATIENT_NAME);
        yearOfBirth = intent.getStringExtra(YEAR_OF_BIRTH);
        diagnose = intent.getStringExtra(DIAGNOSE);
        relationship = intent.getStringExtra(RELATIONSHIP);
        invitedEmail = intent.getStringExtra(INVITED_EMAIL);
        emailLogin.setText(invitedEmail);

        if ((patientName == null) && (invitedEmail == null)){
            registerButton = (TextView) findViewById(R.id.btn_register_login);
            registerButton.setVisibility(View.INVISIBLE);
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.INVISIBLE);
            }
        });

        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.INVISIBLE);
                registerLayout.setVisibility(View.VISIBLE);
            }
        });

        registerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        loginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void register(){
        Person newUser = new Person(0 , firstnameRegister.getText().toString(), lastnameRegister.getText().toString(), emailRegister.getText().toString(), passwordRegister.getText().toString(), null);
        connectHandler.createUser(newUser);
        while (connectHandler.socketBusy){}
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), "User has been created!", duration);
        toast.show();
        //TBD solution to choose several careTeams possible solution to save last used patient

        // During register user need to either create a new care team or join and existing
        if (patientName != null){
            // User tries to create new patient
            Patient newPatient = new Patient(0,patientName,yearOfBirth,diagnose,null,null);
            connectHandler.createPatient(newPatient, relationship);
        }
        while ((connectHandler.patient != null) && connectHandler.socketBusy) {}
        Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
        startActivity(myIntent);
    }

    private void login(){
        Person newUser = new Person(0, null, null, emailLogin.getText().toString(), passwordLogin.getText().toString(), null);
        connectHandler.login(newUser);

        while (connectHandler.socketBusy){}

        if (connectHandler.person == null){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            String alertText = String.format("Login failed");
            alertDialogBuilder.setMessage(alertText);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    emailLogin.setText("");
                    passwordLogin.setText("");
                }
            });
            /*
            alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    emailLogin.setText("");
                    passwordLogin.setText("");
                }
            });
            */
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (connectHandler.person.patient == null){
            // Login success but no care team connected
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            String alertText = String.format("Do you want to join %s care team?", patientName);
            alertDialogBuilder.setMessage(alertText);

            alertDialogBuilder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    createCareTeam();
                }
            });

            alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    emailLogin.setText("");
                    passwordLogin.setText("");
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            //Login success

            while (connectHandler.socketBusy){}

            lcl = connectHandler.lcl;
            Gson gson = new Gson();
            String jsonPerson = gson.toJson(lcl);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Person", jsonPerson);
            editor.apply();

            Intent myIntent = new Intent(this, CareTeamActivity.class);
            startActivity(myIntent);
        }
    }

    private void createCareTeam(){
        Intent myIntent = new Intent(this, CreateCareTeamActivity.class);
        startActivity(myIntent);
    }
}
