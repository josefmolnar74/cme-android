package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onClickCreateCareTeam(View view){
        Intent intent = new Intent(this, CreateCareTeamActivity.class);
        startActivity(intent);
    }

    public void onJoinCreateCareTeam(View view){

    }

    public void onClickLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}