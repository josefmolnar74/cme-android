package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import com.google.gson.Gson;

public class ManageCareTeamActivity extends AppCompatActivity {


    ArrayList<Patients> patientList = new ArrayList<>();

    ListView customListView;
    ManageCareTeamAdapter listAdapter;
    Lcl_work_area lcl;
    String lclString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_care_team);

        customListView = (ListView) findViewById(R.id.careTeamListView);

        lclString = (String) getIntent().getSerializableExtra("Person");
        Gson gson = new Gson();

        lcl = gson.fromJson(lclString, Lcl_work_area.class);
        System.out.println(lcl.first_name);


        for (int i = 0; i < lcl.patients.size(); i++) {
            patientList.add(lcl.patients.get(i));
        }

        listAdapter = new ManageCareTeamAdapter(this, patientList);
        customListView.setAdapter(listAdapter);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToCareTeam(position);

            }
        });
    }


    private int goToCareTeam(int position) {

        Intent myIntent = new Intent(this, CareTeamActivity.class);
        myIntent.putExtra("Person", lclString);
        myIntent.putExtra("Position", position);
        setResult(RESULT_OK, myIntent);


        startActivity(myIntent);

        return position;
    }



}
