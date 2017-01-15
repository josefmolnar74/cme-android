package com.cancercarecompany.ccc.ccc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText registerName;
    EditText registerPassword;
    EditText registerEmail;
    EditText registerRelationship;
    TextView registerButton;

    private String patientName;
    private String yearOfBirth;
    private String diagnose;
    private String invitedEmail;

    public static final String PATIENT_NAME = "patient name"; //From create care team
    public static final String YEAR_OF_BIRTH = "year of birth"; //From create care team
    public static final String DIAGNOSE = "diagnose"; //From create care team
    public static final String INVITED_EMAIL   = "invited email"; //From join care team

    private ConnectionHandler connectHandler;
    private Invite invite; //support only 1 patient

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectHandler = ConnectionHandler.getInstance();

        registerName = (EditText) findViewById(R.id.text_register_name);
        registerPassword = (EditText) findViewById(R.id.text_register_password);
        registerEmail = (EditText) findViewById(R.id.text_register_email);
        registerRelationship = (EditText) findViewById(R.id.text_register_relationship);

        // Get values sent from Create team and join activity
        Intent intent = getIntent();
        patientName = intent.getStringExtra(PATIENT_NAME);
        yearOfBirth = intent.getStringExtra(YEAR_OF_BIRTH);
        diagnose = intent.getStringExtra(DIAGNOSE);
        invitedEmail = intent.getStringExtra(INVITED_EMAIL);
        if (invitedEmail != null){
            registerEmail.setText(invitedEmail);
        }

        // Statusbar color
/*        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
*/
        registerButton = (TextView) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        final Person newUser = new Person(0 , registerName.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString(), 0, null);
        connectHandler.createUser(newUser);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!connectHandler.pendingMessage) {
                    connectUser();
                } else {
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void connectUser(){
        if (connectHandler.person == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            String alertText = String.format("Login failed, user already exists");
            alertDialogBuilder.setMessage(alertText);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    backToWelcome();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.toast_message_register_user_created), duration);
            toast.show();

            // During register user need to either create a new care team or join and existing
            if (patientName != null) {
                // User tries to create new patient
                Patient newPatient = new Patient(0, patientName, yearOfBirth, diagnose, null);
                connectHandler.createPatient(newPatient, registerRelationship.getText().toString());
            } else if (invitedEmail != null) {//replace with invite object
                connectHandler.acceptCareTeamInvite();
            }
            login();
        }
    }

    private void backToWelcome(){
        //User email already exist please use another email
        Intent myIntent = new Intent(this, WelcomeActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void login(){
        final Person newUser = new Person(0 , registerName.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString(), 0, null);
        connectHandler.login(newUser);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!connectHandler.pendingMessage) {
                    if (connectHandler.patient != null){
                        getallPatientData(connectHandler.patient.patient_ID);
                    }
                }
                else {
                    handler.postDelayed(this,1000);
                }
            }
        });
    }

    private void getallPatientData(int patientID){
        // Get all patient data after login
        connectHandler.getAllPatientData(patientID);
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
