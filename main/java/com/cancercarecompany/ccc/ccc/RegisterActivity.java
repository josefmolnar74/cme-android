package com.cancercarecompany.ccc.ccc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText registerFirstName;
    EditText registerLastName;
    EditText emailLogin;
    EditText registerPassword;
    EditText passwordLogin;
    EditText registerEmail;
    RelativeLayout registerLayout;
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
    public static final String INVITED_EMAIL   = "invited email"; //From join care team

    private ConnectionHandler connectHandler;
    private Invite invite; //support only 1 patient

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        connectHandler = ConnectionHandler.getInstance();

        registerFirstName = (EditText) findViewById(R.id.text_register_firstname);
        registerLastName = (EditText) findViewById(R.id.text_register_lastname);
        registerPassword = (EditText) findViewById(R.id.text_register_password);
        registerEmail = (EditText) findViewById(R.id.text_register_email);

        // Get values sent from Create team and join activity
        Intent intent = getIntent();
        patientName = intent.getStringExtra(PATIENT_NAME);
        yearOfBirth = intent.getStringExtra(YEAR_OF_BIRTH);
        diagnose = intent.getStringExtra(DIAGNOSE);
        relationship = intent.getStringExtra(RELATIONSHIP);
        invitedEmail = intent.getStringExtra(INVITED_EMAIL);
        registerEmail.setText(invitedEmail);

        registerButton = (TextView) findViewById(R.id.button_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        Person newUser = new Person(0 , registerFirstName.getText().toString(), registerLastName.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString(), null);
        connectHandler.createUser(newUser);
        while (connectHandler.socketBusy){}

        if (connectHandler.person == null){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            String alertText = String.format("Login failed, user already exists");
            alertDialogBuilder.setMessage(alertText);

            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    login();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(), "User has been created!", duration);
            toast.show();

            // During register user need to either create a new care team or join and existing
            if (patientName != null){
                // User tries to create new patient
                Patient newPatient = new Patient(0,patientName,yearOfBirth,diagnose,null,null);
                connectHandler.createPatient(newPatient, relationship);
            }
            else if (invitedEmail != null){//replace with invite object
                connectHandler.acceptCareTeamInvite();
                while (connectHandler.socketBusy){}
                //Ugly solution to solve that created careteammember is part of patient
                connectHandler.getPatient(invite.patient_ID);
            }

            Intent myIntent = new Intent(this, CareTeamActivity.class);
            startActivity(myIntent);
        }
    }

    private void login(){
        //User email already exist please use another email
        Intent myIntent = new Intent(this, WelcomeActivity.class);
        startActivity(myIntent);
    }

}
