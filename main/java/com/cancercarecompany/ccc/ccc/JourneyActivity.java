package com.cancercarecompany.ccc.ccc;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.print.PrintAttributes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import android.view.DragEvent;
import android.view.MotionEvent;

import pl.polidea.view.ZoomView;

public class JourneyActivity extends AppCompatActivity  {

    ArrayList<Events> eventList;
    ImageButton addDialys;
    ImageButton addSurgery;
    ImageButton addXray;
    ImageButton addHeart;
    ImageButton addBlood;
    RelativeLayout relativeLayout;
    RelativeLayout cloudLayout;
    int topMargin = 0;
    Calendar currentDate;
    int eventsPassed;
    ImageView car;
    HorizontalScrollView eventScroll;
    HorizontalScrollView heavenScroll;
    HorizontalScrollView bottomScroll;
    RelativeLayout layoutButtons;
    RelativeLayout eventLayout;

    ImageButton cloud1;
    ImageButton cloud2;
    ImageButton cloud3;
    ImageButton cloud4;
    ImageButton cloud5;

    View vID;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        eventList = new ArrayList<Events>();

        car = (ImageView) findViewById(R.id.img_car_journey);
        addDialys = (ImageButton) findViewById(R.id.btn_dialys_journey);
        addSurgery = (ImageButton) findViewById(R.id.btn_surgery_journey);
        addXray = (ImageButton) findViewById(R.id.btn_xray_journey);
        addHeart = (ImageButton) findViewById(R.id.btn_heart_journey);
        addBlood = (ImageButton) findViewById(R.id.btn_blood_journey);
        eventScroll = (HorizontalScrollView) findViewById(R.id.Scroll_event_journey);
        heavenScroll = (HorizontalScrollView) findViewById(R.id.scroll_heaven_journey);
        bottomScroll = (HorizontalScrollView) findViewById(R.id.scroll_bottom_journey);
        cloudLayout = (RelativeLayout) findViewById(R.id.heavenRelativeLayout);
        cloud1 = (ImageButton) findViewById(R.id.btn_cloud1_journey);
        cloud2 = (ImageButton) findViewById(R.id.btn_cloud2_journey);
        cloud3 = (ImageButton) findViewById(R.id.btn_cloud3_journey);
        cloud4 = (ImageButton) findViewById(R.id.btn_cloud4_journey);
        cloud5 = (ImageButton) findViewById(R.id.btn_cloud5_journey);
        layoutButtons = (RelativeLayout) findViewById(R.id.relativeLayout3);
        eventLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);

        addDialys.setOnTouchListener(new MyTouchListener());
        eventLayout.setOnDragListener(new MyDragListener());
        addBlood.setOnTouchListener(new MyTouchListener());
        addHeart.setOnTouchListener(new MyTouchListener());
        addSurgery.setOnTouchListener(new MyTouchListener());
        addXray.setOnTouchListener(new MyTouchListener());

///yguygyigygygy

        eventScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollX = eventScroll.getScrollX();

                heavenScroll.setScrollX(scrollX/4);
                bottomScroll.setScrollX(scrollX*2);


            }
        });



    }

    public void addTreatment(View v){
        System.out.println(v.getId());
        popup(v);

    }

    private void refreshEvents() {

        for (int i = 0; i < eventList.size(); i++) {

            ViewGroup layout = (ViewGroup) findViewById(R.id.eventLayoutJourney);
            View imageButton = layout.findViewById(i);
            layout.removeView(imageButton);
        }

        int currentEvent = 0;

        Collections.sort(eventList, new Comparator<Events>() {
            @Override
            public int compare(Events lhs, Events rhs) {

                return lhs.startDate.compareTo(rhs.startDate);

            }
        });

        eventsPassed = 0;
        for (int i = 0; i < eventList.size(); i++) {


            Calendar eventDate = Calendar.getInstance();
            currentDate = Calendar.getInstance();
            eventDate.setTime(eventList.get(i).startDate);
            if (eventDate.before(currentDate)) {
                eventsPassed ++;
            } else {

            }

        }
        relativeLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);
        RelativeLayout.LayoutParams paramsCar = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        System.out.println(eventsPassed);
        if(eventsPassed == 0){
            eventsPassed = 1;
        }else{
            eventsPassed = eventsPassed + 1;
        }

        RelativeLayout.LayoutParams paramsCar0 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        paramsCar0.setMargins((eventsPassed * 400)-200,200,0,0);
        paramsCar0.width = 300;
        paramsCar0.height = 200;
        car.setLayoutParams(paramsCar0);




        for (int i = 0; i < eventList.size(); i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            ImageButton btn = new ImageButton(this);
            btn.setId(i);
            final int id_ = btn.getId();

            if (i%2==0) {
                    topMargin = 100;
            }else{
                    topMargin = 400;
                }



            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkDetail(id_);
                }
            });

            String subCategory = eventList.get(i).category.toString();

            switch (subCategory){
                case "Surgery":
                    btn.setBackgroundResource(R.drawable.kirurgi);
                    break;
                case "Xray":
                    btn.setBackgroundResource(R.drawable.xray);
                    break;
                case "Heart":
                    btn.setBackgroundResource(R.drawable.heartex);
                    break;
                case "Blood":
                    btn.setBackgroundResource(R.drawable.blodtest);
                    break;
                case "Dialys":
                    btn.setBackgroundResource(R.drawable.dialys);
                    break;
            }



            relativeLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);
            relativeLayout.addView(btn, params);

            ImageButton indexButton = ((ImageButton) findViewById(i));

            params.setMargins((currentEvent + 400), topMargin, 0, 0);
            params.width = 120;
            params.height = 120;
            indexButton.setLayoutParams(params);

            currentEvent = currentEvent + 500;


        }


    }


            public void popup(View v) {


                String vCategory = "";

                switch (v.getId()){

                    case 2131493011:
                        vCategory = "Surgery";
                        break;
                    case 2131493012:
                         vCategory = "Xray";
                        break;
                    case 2131493013:
                         vCategory = "Heart";
                        break;
                    case 2131493014:
                        vCategory = "Blood";
                        break;
                    case 2131493015:
                        vCategory = "Dialys";
                        break;

                }
                final String subCategory = vCategory;

                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.addeventpopup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView, 1000, 800 );

                popupWindow.setFocusable(true);
                popupWindow.update();


                TextView  categoryText = (TextView)popupView.findViewById(R.id.txt_category_journey);
                Button saveButton = (Button)popupView.findViewById(R.id.btn_saveEvent_Journey);
                ImageView categoryImage = (ImageView)popupView.findViewById(R.id.img_category_addevent_journey);
                final EditText notes  = (EditText)popupView.findViewById(R.id.txt_notes_journey);
                final DatePicker datePicker = (DatePicker)popupView.findViewById(R.id.datepicker_journey);
                final TimePicker timePicker = (TimePicker)popupView.findViewById(R.id.timepicker_journey);
                timePicker.setIs24HourView(true);
                categoryText.setText(vCategory);


                switch (subCategory){
                    case "Surgery":
                        categoryImage.setBackgroundResource(R.drawable.kirurgi);
                        break;
                    case "Xray":
                        categoryImage.setBackgroundResource(R.drawable.xray);
                        break;
                    case "Heart":
                        categoryImage.setBackgroundResource(R.drawable.heartex);
                        break;
                    case "Blood":
                        categoryImage.setBackgroundResource(R.drawable.blodtest);
                        break;
                    case "Dialys":
                        categoryImage.setBackgroundResource(R.drawable.dialys);
                        break;
                }



                notes.setFocusableInTouchMode(true);


                saveButton.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, datePicker.getYear());
                        cal.set(Calendar.MONTH, datePicker.getMonth());
                        cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        cal.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        cal.set(Calendar.MINUTE, timePicker.getMinute());
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);


                        Date date = cal.getTime();


                        Events event = new Events(subCategory, notes.getText().toString(), date, null, null, null);
                        eventList.add(event);

                        refreshEvents();


                    }});

                relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

                popupWindow.showAsDropDown(relativeLayout, 450, 140);

            }


    private void checkDetail(final int id_){

        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.detailpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, 700, 1200 );

        popupWindow.setFocusable(true);
        popupWindow.update();

        TextView eventDetail = (TextView)popupView.findViewById(R.id.lbl_eventInfo_journey);
        TextView noteDetail = (TextView)popupView.findViewById(R.id.lbl_noteDetail_journey);
        TextView timeDetail = (TextView)popupView.findViewById(R.id.lbl_TimeInfoDetail_journey);
        ImageView categoryImage = (ImageView)popupView.findViewById(R.id.img_detailcategory_journey);
        final Button deleteEvent = (Button)popupView.findViewById(R.id.btn_deleteEvent_journey);


            eventDetail.setText(eventList.get(id_).category.toString());
            noteDetail.setText(eventList.get(id_).notes.toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd-hh:mm");
            String dateString = simpleDateFormat.format(eventList.get(id_).startDate.getTime());
            timeDetail.setText(dateString);

        switch (eventList.get(id_).category){
            case "Surgery":
                categoryImage.setBackgroundResource(R.drawable.kirurgi);
                break;
            case "Xray":
                categoryImage.setBackgroundResource(R.drawable.xray);
                break;
            case "Heart":
                categoryImage.setBackgroundResource(R.drawable.heartex);
                break;
            case "Blood":
                categoryImage.setBackgroundResource(R.drawable.blodtest);
                break;
            case "Dialys":
                categoryImage.setBackgroundResource(R.drawable.dialys);
                break;
        }


        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < eventList.size(); i++) {

                    ViewGroup layout = (ViewGroup) findViewById(R.id.eventLayoutJourney);
                    View imageButton = layout.findViewById(i);
                    layout.removeView(imageButton);


                }
                eventList.remove(id_);
                popupWindow.dismiss();
                refreshEvents();
            }
        });



        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        popupWindow.showAsDropDown(relativeLayout, 500, 0);


    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                vID = view;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);

                return true;
            } else {
                return false;
            }
        }
    }


    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    if (event.getResult() == true){
                        addTreatment(vID);

                    }

                default:
                    break;
            }
            return true;
        }
    }
}


























































