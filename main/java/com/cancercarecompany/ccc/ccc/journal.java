package com.cancercarecompany.ccc.ccc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

//import android.widget.Spinner;

public class journal extends AppCompatActivity {

    ArrayList<Events> eventList;
    ArrayList<Patient> patientList = new ArrayList<>();
    String lbl_datum;
    TextView header;
    TextView fr_medicin;
    TextView lu_medicin;
    TextView mi_medicin;
    TextView txt_rat_status;
    SeekBar seek_fat;
    RelativeLayout relativeLayout;
    String choice;
    Boolean fr_mark = Boolean.FALSE;
    Boolean lu_mark = Boolean.FALSE;
    Boolean mi_mark = Boolean.FALSE;
    Boolean kv_mark = Boolean.FALSE;
    ImageButton journeyButton;
    ImageButton careTeamButton;

    Lcl_work_area lcl;
    String lclString;
    io.socket.client.Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        eventList = new ArrayList<Events>();

        //loadEventList

        final Button btn_fatigue      = (Button)    findViewById(R.id.btn_fatigue);
        final Button btn_smarta       = (Button)    findViewById(R.id.btn_smarta);
        final Button btn_munforandr   = (Button)    findViewById(R.id.btn_munforandr);
        final Button btn_stickn       = (Button)    findViewById(R.id.btn_stickn);
        final Button btn_forstdiar    = (Button)    findViewById(R.id.btn_forst_diar);
        final Button btn_aptit        = (Button)    findViewById(R.id.btn_aptit);
        final Button btn_yrsel        = (Button)    findViewById(R.id.btn_yrsel);
        final Button btn_krakn        = (Button)    findViewById(R.id.btn_krakn);
        final Button btn_frukost      = (Button)    findViewById(R.id.btn_frukost);
        final Button btn_lunch        = (Button)    findViewById(R.id.btn_lunch);
        final Button btn_middag       = (Button)    findViewById(R.id.btn_middag);
        final Button btn_kvall        = (Button)    findViewById(R.id.btn_kvall);
        final RatingBar rat_status    = (RatingBar) findViewById(R.id.rat_status);
        final TextView txt_rat_status = (TextView)  findViewById(R.id.txt_rat_status);
        final TextView txt_med_txt    = (TextView)  findViewById(R.id.txt_med_int);
        final TextView txt_diary_head = (TextView)  findViewById(R.id.diary_header);
        final CalendarView view_calendar = (CalendarView)  findViewById(R.id.evt_cal);
        final ImageButton btn_journey    = (ImageButton) findViewById(R.id.journeyButton);
        final ImageButton careTeamButton = (ImageButton) findViewById(R.id.contactsButton);

// Hämta data

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        lclString = (String) preferences.getString("Person", "");

        System.out.println(lclString);
        Gson gson = new Gson();

        lcl = gson.fromJson(lclString, Lcl_work_area.class);

        for (int i = 0; i < lcl.patient.size(); i++) {
            patientList.add(lcl.patient.get(i));
        }





//        txt_diary_head.setText(R.string.txt_journal_headline);
//                txt_diary_head.setText((txt_diary_head.getText()) + " " + year + "-" + month + "-" + date);


        //txt_rat_status.setText(patientList.get(0).patient_ID);

        txt_rat_status.setText(R.string.txt_stat_start);

        btn_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journeyActivity();
            }
        });

        btn_fatigue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Fat");
            }
        });

        btn_smarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Pain");
            }
        });

        btn_aptit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = "Apetite";
                add_fatigue(choice);
            }
        });

        btn_yrsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Yrsel");
            }
        });

        btn_forstdiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Forstopp");
            }
        });

        btn_krakn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_fatigue("Krakn");
            }
        });

        rat_status.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txt_rat_status.setText("Idag är det " + String.valueOf(rating) + ", bra");
            }
        });

        btn_frukost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fr_mark.equals(Boolean.FALSE)) {
                    fr_mark = Boolean.TRUE;
                    btn_frukost.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    fr_mark = Boolean.FALSE;
                    btn_frukost.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }

        });


        btn_lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( lu_mark.equals(Boolean.FALSE)) {
                    lu_mark = Boolean.TRUE;
                    btn_lunch.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    lu_mark = Boolean.FALSE;
                    btn_lunch.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }
        });

        btn_middag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mi_mark.equals(Boolean.FALSE)) {
                    mi_mark = Boolean.TRUE;
                    btn_middag.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    mi_mark = Boolean.FALSE;
                    btn_middag.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }
        });
///* TEST
        btn_stickn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_Events();
            }
        });
//TEST*/
        btn_kvall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( kv_mark.equals(Boolean.FALSE)) {
                    kv_mark = Boolean.TRUE;
                    btn_kvall.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                } else {
                    kv_mark = Boolean.FALSE;
                    btn_kvall.setBackgroundColor(getResources().getColor(R.color.addcontact));
                }
                if ((fr_mark.equals(Boolean.TRUE) && lu_mark.equals(Boolean.TRUE) &&
                        mi_mark.equals(Boolean.TRUE) && kv_mark.equals(Boolean.TRUE))) {
                    txt_med_txt.setText(R.string.txt_med_done);
                } else {
                    txt_med_txt.setText(R.string.txt_med_info);
                }
            }
        });

        careTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                careTeam();
            }
        });

        view_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int date) {
                Toast.makeText(getApplicationContext(),date+ "/"+month+"/"+year,4000).show();
                //     getString(R.string.txt_journal_headline, year,month, date);
                txt_diary_head.setText(R.string.txt_journal_headline);
                txt_diary_head.setText((txt_diary_head.getText()) + " " + year + "-" + month + "-" + date);
            }
        });
        /*
        v.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
       this.calendar = new GregorianCalendar( year, month, dayOfMonth );
    }//met

         */
    }

    private void careTeam(){
        Intent myIntent = new Intent(this, CareTeamActivity.class);
        startActivity(myIntent);
    }


    public void add_fatigue(String choice) {
        popup_add_fatigue(choice);
    }

    public void popup_add_fatigue(final String choice) {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.biv_slider_0_10_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 2000);


        final Button   btn_cancel    = (Button) popupView.findViewById(R.id.btn_cancel_popup);
        final TextView txt_header    = (TextView) popupView.findViewById(R.id.txt_biv_slider_head);
        final TextView txt_subheader = (TextView) popupView.findViewById(R.id.txt_biv_slider_subhead);

        final SeekBar  seek_fat   = (SeekBar)  popupView.findViewById(R.id.seek_fat);
        final SeekBar  seek_fat2  = (SeekBar)  popupView.findViewById(R.id.seek_fat2);
        final SeekBar  seek_fat3  = (SeekBar)  popupView.findViewById(R.id.seek_fat3);
        final TextView text_min   = (TextView) popupView.findViewById(R.id.txt_seek_min);
        final TextView text_min2  = (TextView) popupView.findViewById(R.id.txt_seek_min2);
        final TextView text_min3  = (TextView) popupView.findViewById(R.id.txt_seek_min3);
        final TextView text_res   = (TextView) popupView.findViewById(R.id.txt_seek_res);
        final TextView text_res2  = (TextView) popupView.findViewById(R.id.txt_seek_res2);
        final TextView text_res3  = (TextView) popupView.findViewById(R.id.txt_seek_res3);
        final TextView text_max   = (TextView) popupView.findViewById(R.id.txt_seek_max);
        final TextView text_max2  = (TextView) popupView.findViewById(R.id.txt_seek_max2);
        final TextView text_max3  = (TextView) popupView.findViewById(R.id.txt_seek_max3);


        if (choice == "Fat") {
            txt_header.setText(R.string.txt_biv_fat_head);
            txt_subheader.setText(R.string.txt_biv_fat_desc);
            text_min.setText(R.string.txt_biv_seek_min);
            text_max.setText(R.string.txt_biv_seek_max);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);

        } else if (choice == "Pain") {
            txt_header.setText(R.string.txt_biv_pain_head);
            txt_subheader.setText(R.string.txt_biv_pain_desc);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);

        } else if (choice == "Apetite") {
            txt_header.setText(R.string.txt_biv_apetite_head);
            txt_subheader.setText(R.string.txt_biv_apetite_desc);

            text_max.setText(R.string.txt_biv_seek_apetite_max);
            text_min.setText(R.string.txt_biv_seek_apetite_min);
            text_max2.setText(R.string.txt_biv_seek_apetite_max);
            text_min2.setText(R.string.txt_biv_seek_apetite_min);
            text_max3.setText(R.string.txt_biv_seek_apetite_max);
            text_min3.setText(R.string.txt_biv_seek_apetite_min);

            seek_fat2.setVisibility(View.VISIBLE);
            text_max2.setVisibility(View.VISIBLE);
            text_res2.setVisibility(View.VISIBLE);
            text_min2.setVisibility(View.VISIBLE);
            seek_fat3.setVisibility(View.VISIBLE);
            text_max3.setVisibility(View.VISIBLE);
            text_res3.setVisibility(View.VISIBLE);
            text_min3.setVisibility(View.VISIBLE);
            text_res.setText(R.string.txt_frukost);
            text_res2.setText(R.string.txt_lunch);
            text_res3.setText(R.string.txt_middag);
        } else if ( choice == "Yrsel") {
            txt_header.setText(R.string.txt_biv_yrsel_head);
            txt_subheader.setText(R.string.txt_biv_yrsel_desc);
            text_min.setText(R.string.txt_biv_seek_y_min);
            text_max.setText(R.string.txt_biv_seek_y_max);
            text_res.setText(R.string.txt_biv_seek_y_res);
            seek_fat.setMax(2);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);
        } else if ( choice == "Forstopp") {
            txt_header.setText(R.string.txt_biv_forstopp_head);
            txt_subheader.setText(R.string.txt_biv_forstopp_desc);
            text_min.setText(R.string.txt_biv_seek_fst_min);
            text_res.setText(R.string.txt_biv_seek_fst_res);
            text_max.setText(R.string.txt_biv_seek_fst_max);
            seek_fat.setMax(2);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);
        } else if ( choice == "Krakn") {
            txt_header.setText(R.string.txt_biv_krakn_head);
            txt_subheader.setText(R.string.txt_biv_krakn_desc);
            text_min.setText(R.string.txt_biv_seek_krk_min);
            text_max.setText(R.string.txt_biv_seek_krk_max);
            text_res.setText(R.string.txt_biv_seek_krk_med);

            seek_fat.setMax(2);
            seek_fat2.setVisibility(View.INVISIBLE);
            text_max2.setVisibility(View.INVISIBLE);
            text_res2.setVisibility(View.INVISIBLE);
            text_min2.setVisibility(View.INVISIBLE);
            seek_fat3.setVisibility(View.INVISIBLE);
            text_max3.setVisibility(View.INVISIBLE);
            text_res3.setVisibility(View.INVISIBLE);
            text_min3.setVisibility(View.INVISIBLE);
        }

        popupWindow.setFocusable(true);
        popupWindow.update();

        seek_fat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if ( choice == "Apetite") {
                    text_res.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else if ( choice == "Yrsel") {
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else if ( choice == "Krakn") {
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else if ( choice == "Forstopp") {
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else {
                    text_res.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seek_fat2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choice == "Apetite") {
                    text_res2.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else {
                    text_res2.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        seek_fat3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (choice == "Apetite") {
                    text_res3.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                } else {
                    text_res3.setText(progress + "0 %");
                    Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_lay_scr);
        popupWindow.showAsDropDown(relativeLayout, 500, -1100);
    }

    /*   public void add_yrsel(String choice) {
            popup_add_yrsel(choice);
            //popup_add_fatigue("Pain");
        }
    */
    public void popup_add_yrsel(String choice) {
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.biv_slider_0_10_popup, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1000, 2000);

        popupWindow.setFocusable(true);
        popupWindow.update();

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_lay_scr);
        popupWindow.showAsDropDown(relativeLayout, 500, -1100);
    }

    private void journeyActivity(){
        Intent myIntent = new Intent(this, JourneyActivity.class);
        startActivity(myIntent);
    }

    private void get_Events(){
/*
        Socket socketClass = new Socket();
       // Person newUser = new Person(0, null, null, emailLogin.getText().toString(), passwordLogin.getText().toString());
        Event events = new Event(0, lcl.patients.get(0).patient_ID, null, null, null, null, null, null, null);
//        Event events = new Event(0, 2, null, null, null, null, null, null, null, null);

        mSocket = socketClass.events(events);
        while (socketClass.lcl == null){
            System.out.println("tom");
        }
        lcl = socketClass.lcl;
        System.out.println(lcl);

        Gson gson = new Gson();
        String jsonPerson = gson.toJson(lcl);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("event",jsonPerson);
        editor.apply();

        Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
        startActivity(myIntent);
*/
    }

}
