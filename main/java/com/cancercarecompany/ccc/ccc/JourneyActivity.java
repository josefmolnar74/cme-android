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
    ImageView car;
    long startDate;
    long currentEvent;
    Date currentDate;
    HorizontalScrollView eventScroll;
    HorizontalScrollView heavenScroll;
    HorizontalScrollView bottomScroll;
    RelativeLayout layoutButtons;
    RelativeLayout eventLayout;
    RelativeLayout bottomLayout;

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
        bottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);

        addDialys.setOnTouchListener(new MyTouchListener());
        eventLayout.setOnDragListener(new MyDragListener());
        addBlood.setOnTouchListener(new MyTouchListener());
        addHeart.setOnTouchListener(new MyTouchListener());
        addSurgery.setOnTouchListener(new MyTouchListener());
        addXray.setOnTouchListener(new MyTouchListener());

        currentDate = Calendar.getInstance().getTime();
        Events diagnoseStart = new Events("Start", "Journey starts here", currentDate, null, null, null );
        eventList.add(diagnoseStart);


        ExampleJourney();
        refreshEvents();




        eventScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollX = eventScroll.getScrollX();

                heavenScroll.setScrollX(scrollX/4);
                bottomScroll.setScrollX(scrollX*3);


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

        Collections.sort(eventList, new Comparator<Events>() {
            @Override
            public int compare(Events lhs, Events rhs) {

                return lhs.startDate.compareTo(rhs.startDate);

            }
        });

        startDate = eventList.get(0).startDate.getTime();

        relativeLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        RelativeLayout.LayoutParams paramsCar = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        int carPosition = (int) currentDate.getTime() -(int) startDate;
        paramsCar.setMargins(carPosition + 200,0,0,0);
        paramsCar.width = 300;
        paramsCar.height = 200;


        for (int i = 0; i < eventList.size(); i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            ImageButton btn = new ImageButton(this);
            btn.setId(i);
            final int id_ = btn.getId();

            if (i%2==0) {
                    topMargin = 0;
            }else{
                    topMargin = 200;
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
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Xray":
                    btn.setBackgroundResource(R.drawable.orangebubblee);
                    break;
                case "Heart":
                    btn.setBackgroundResource(R.drawable.bluebubble);
                    break;
                case "Blood":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Dialys":
                    btn.setBackgroundResource(R.drawable.orangebubble);
                    break;
                case "Start":
                    btn.setBackgroundResource(R.drawable.journeystart);
                    break;
            }



            relativeLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);
            relativeLayout.addView(btn, params);

            ImageButton indexButton = ((ImageButton) findViewById(i));

            currentEvent = eventList.get(i).startDate.getTime() - startDate;
            currentEvent = currentEvent / 1000000;
            System.out.println(currentEvent);
            int currentIntEvent = (int)currentEvent;
            System.out.println(currentIntEvent);


            params.setMargins((currentIntEvent * 2) + 300, topMargin, 0, 0);
            params.width = 150;
            params.height = 150;
            if (subCategory == "Start"){
                params.setMargins((currentIntEvent * 2), topMargin, 0, 0);
                params.width = 300;
                params.height = 300;
            }
            indexButton.setLayoutParams(params);


        }


    }


            public void popup(View v) {


                String vCategory = "";

                switch (v.getId()){

                    case 2131493010:
                        vCategory = "Surgery";
                        break;
                    case 2131493011:
                         vCategory = "Xray";
                        break;
                    case 2131493012:
                         vCategory = "Heart";
                        break;
                    case 2131493013:
                        vCategory = "Blood";
                        break;
                    case 2131493014:
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
                        categoryImage.setBackgroundResource(R.drawable.greenbubble);
                        break;
                    case "Xray":
                        categoryImage.setBackgroundResource(R.drawable.orangebubblee);
                        break;
                    case "Heart":
                        categoryImage.setBackgroundResource(R.drawable.bluebubble);
                        break;
                    case "Blood":
                        categoryImage.setBackgroundResource(R.drawable.pinkbubble);
                        break;
                    case "Dialys":
                        categoryImage.setBackgroundResource(R.drawable.orangebubble);
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

    private void ExampleJourney(){
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        c.add(Calendar.DATE, 1);
        Events event1 = new Events("Surgery", "at hospital", c.getTime(), null, null, null );
        eventList.add(event1);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 2);
        Events event2 = new Events("Heart", "take meds before!", c.getTime(), null, null, null );
        eventList.add(event2);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 5);
        Events event3 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event3);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 6);
        Events event4 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event4);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 7);
        Events event5 = new Events("Dialys", "Dialys at Dr Edith", c.getTime(), null, null, null );
        eventList.add(event5);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 8);
        Events event6 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event6);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 9);
        Events event7 = new Events("Xray", "Xray whole day", c.getTime(), null, null, null );
        eventList.add(event7);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 10);
        Events event8 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event8);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 12);
        Events event9 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event9);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 13);
        Events event10 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event10);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 14);
        Events event11 = new Events("Surgery", "at hospital", c.getTime(), null, null, null );
        eventList.add(event11);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 15);
        Events event12 = new Events("Heart", "take meds before!", c.getTime(), null, null, null );
        eventList.add(event12);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 17);
        Events event13 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event13);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 18);
        Events event14 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event14);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 19);
        Events event15 = new Events("Dialys", "Dialys at Dr Edith", c.getTime(), null, null, null );
        eventList.add(event15);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 22);
        Events event16 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event16);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 24);
        Events event17 = new Events("Xray", "Xray whole day", c.getTime(), null, null, null );
        eventList.add(event17);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 26);
        Events event18 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event18);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 27);
        Events event19 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event19);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 28);
        Events event20 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event20);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 29);
        Events event21 = new Events("Surgery", "at hospital", c.getTime(), null, null, null );
        eventList.add(event21);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 30);
        Events event22 = new Events("Heart", "take meds before!", c.getTime(), null, null, null );
        eventList.add(event22);
        c.add(Calendar.DATE, 31);
        Events event23 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event23);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 32);
        Events event24 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event24);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 34);
        Events event25 = new Events("Dialys", "Dialys at Dr Edith", c.getTime(), null, null, null );
        eventList.add(event25);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 36);
        Events event26 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event26);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 40);
        Events event27 = new Events("Xray", "Xray whole day", c.getTime(), null, null, null );
        eventList.add(event27);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 41);
        Events event28 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event28);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 45);
        Events event29 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event29);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 46);
        Events event30 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event30);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 48);
        Events event31 = new Events("Surgery", "at hospital", c.getTime(), null, null, null );
        eventList.add(event31);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 52);
        Events event32 = new Events("Heart", "take meds before!", c.getTime(), null, null, null );
        eventList.add(event32);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 55);
        Events event33 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event33);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 57);
        Events event34 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event34);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 61);
        Events event35 = new Events("Dialys", "Dialys at Dr Edith", c.getTime(), null, null, null );
        eventList.add(event35);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 62);
        Events event36 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event36);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 64);
        Events event37 = new Events("Xray", "Xray whole day", c.getTime(), null, null, null );
        eventList.add(event37);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 66);
        Events event38 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event38);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 70);
        Events event39 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event39);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 71);
        Events event40 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event40);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 74);
        Events event41 = new Events("Surgery", "at hospital", c.getTime(), null, null, null );
        eventList.add(event41);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 76);
        Events event42 = new Events("Heart", "take meds before!", c.getTime(), null, null, null );
        eventList.add(event42);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 78);
        Events event43 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event43);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 80);
        Events event44 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event44);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 82);
        Events event45 = new Events("Dialys", "Dialys at Dr Edith", c.getTime(), null, null, null );
        eventList.add(event45);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 84);
        Events event46 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event46);
        c.add(Calendar.DATE, 86);
        Events event47 = new Events("Xray", "Xray whole day", c.getTime(), null, null, null );
        eventList.add(event47);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 88);
        Events event48 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event48);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 89);
        Events event49 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event49);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 90);
        Events event50 = new Events("Blood", "Bloodtest", c.getTime(), null, null, null );
        eventList.add(event50);
        c.setTime(currentDate);


    }

}


























































