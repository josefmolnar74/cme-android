package com.cancercarecompany.ccc.ccc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by christianbarlof on 16-05-14.
 */
public class Care_team_member_popup extends AppCompatActivity {

    String first_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_team_member_popup);

        System.out.println("Care_team_member_popup");
        System.out.println(first_name);
        System.out.println("first_name");

    }


}
