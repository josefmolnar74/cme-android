package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

     PopupWindow pw;
    ImageButton journeyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        journeyButton = (ImageButton) findViewById(R.id.btn_journey_button);
        journeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(MainActivity.this, JourneyActivity.class);
                startActivity(k);
            }
        });

        ImageButton infoButton = (ImageButton) findViewById(R.id.logoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        ImageButton contactButton = (ImageButton) findViewById(R.id.btn_careteam_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CareTeamActivity.class));
            }
        });
    }


}
