package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    Lcl_work_area lcl;


    io.socket.client.Socket mSocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
        Socket socketClass = new Socket();
        Person newUser = new Person(0 , firstnameRegister.getText().toString(), lastnameRegister.getText().toString(), emailRegister.getText().toString(), passwordRegister.getText().toString());

        mSocket = socketClass.createUser(newUser);

        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), "User has been created!", duration);
        toast.show();

    }

    private void login(){
        Socket socketClass = new Socket();
        Person newUser = new Person(0, null, null, emailLogin.getText().toString(), passwordLogin.getText().toString());

        mSocket = socketClass.login(newUser);
        while (socketClass.lcl == null){
                System.out.println("tom");
        }
        lcl = socketClass.lcl;
        System.out.println(lcl);

        Gson gson = new Gson();
        String jsonPerson = gson.toJson(lcl);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Person",jsonPerson);
        editor.apply();

            Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
            startActivity(myIntent);
    }






}
