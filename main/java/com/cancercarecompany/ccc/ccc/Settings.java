package com.cancercarecompany.ccc.ccc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by robinlarsson on 15/07/16.
 */
public class Settings extends AppCompatActivity {

    int width;
    int height;


    String languageRadioButtonString;

    public void settingsPopup(LinearLayout linearLayout, final Activity activity){

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        LayoutInflater layoutInflater
                = (LayoutInflater) activity
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.settings_popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, (int) (width * 0.30), (int) (height * 0.35));

        popupWindow.showAtLocation(linearLayout, Gravity.RIGHT | Gravity.TOP, 0, 0);
        popupView.bringToFront();
        popupWindow.setFocusable(true);
        popupWindow.update();

        final RadioButton buttonEN = (RadioButton) popupView.findViewById(R.id.btn_settings_EN);
        final RadioButton buttonSV = (RadioButton) popupView.findViewById(R.id.btn_settings_SV);
        ImageButton cancelButton = (ImageButton) popupView.findViewById(R.id.btn_settings_cancel);
        ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.btn_settings_save);

        buttonEN.setChecked(true);
        languageRadioButtonString = "en";

        buttonEN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true){
                    buttonSV.setChecked(false);
                    languageRadioButtonString = "en";
                }

            }
        });

        buttonSV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true){
                    buttonEN.setChecked(false);
                    languageRadioButtonString = "sv";
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = activity.getSharedPreferences(
                        "language_settings", Context.MODE_PRIVATE);

                prefs.edit().putString("language_settings", languageRadioButtonString).apply();

                popupWindow.dismiss();
                activity.recreate();

                Toast.makeText(activity, "Settings saved!",
                        Toast.LENGTH_LONG).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }


}
