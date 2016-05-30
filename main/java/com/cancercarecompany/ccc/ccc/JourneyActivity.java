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
import java.util.Random;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.Toast;


import pl.polidea.view.ZoomView;

public class JourneyActivity extends AppCompatActivity {

    ArrayList<Events> eventList;
    ImageButton addDialys;
    ImageButton addSurgery;
    ImageButton addXray;
    ImageButton addHeart;
    ImageButton addBlood;

    ImageButton journeyButton;
    ImageButton careTeamButton;
    ImageButton journalButton;
    ImageButton logoButton;

    RelativeLayout relativeLayout;
    RelativeLayout cloudLayout;
    int topMargin = 0;
    ImageView car;
    long startDate;
    long currentEvent;
    int eventLocation = 0;
    Date currentDate;
    Date journeyStart;
    HorizontalScrollView eventScroll;
    HorizontalScrollView heavenScroll;
    HorizontalScrollView bottomScroll;
    RelativeLayout layoutButtons;
    RelativeLayout eventLayout;
    RelativeLayout bottomLayout;
    int eventsSameDate = 0;

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
        layoutButtons = (RelativeLayout) findViewById(R.id.relativeLayout3);
        eventLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);
        bottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        careTeamButton = (ImageButton) findViewById(R.id.contactsButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);
        logoButton = (ImageButton) findViewById(R.id.logoButton);


        addDialys.setOnTouchListener(new MyTouchListener());
        eventLayout.setOnDragListener(new MyDragListener());
        addBlood.setOnTouchListener(new MyTouchListener());
        addHeart.setOnTouchListener(new MyTouchListener());
        addSurgery.setOnTouchListener(new MyTouchListener());
        addXray.setOnTouchListener(new MyTouchListener());

        currentDate = Calendar.getInstance().getTime();
        Events diagnoseStart = new Events("Start", "Journey starts here", currentDate, null, null, null);
        eventList.add(diagnoseStart);


        ExampleJourney();
        refreshEvents();
        journeyStart = eventList.get(0).startDate;
        generateClouds();


        eventScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollX = eventScroll.getScrollX();

                heavenScroll.setScrollX(scrollX / 5);
                bottomScroll.setScrollX(scrollX * 3);


            }
        });

        heavenScroll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        bottomScroll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        careTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             careTeam();
            }
        });

    }

    private void careTeam(){
        Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
        startActivity(myIntent);
    }

    public void addTreatment(View v, int location) {
        System.out.println(v.getId());
        popup(v, location);

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

        int carPosition = (int) currentDate.getTime() - (int) startDate;
        paramsCar.setMargins(carPosition + 200, 0, 0, 0);
        paramsCar.width = 300;
        paramsCar.height = 200;

        car.setLayoutParams(paramsCar);


        for (int i = 0; i < eventList.size(); i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            ImageButton btn = new ImageButton(this);
            btn.setId(i);
            final int id_ = btn.getId();

            if (i % 3 == 0) {
                topMargin = 0;
            } else if (i % 3 == 1) {
                topMargin = 160;
            } else if (i % 3 == 2) {
                topMargin = 320;
            }


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkDetail(id_);
                }
            });

            String subCategory = eventList.get(i).category.toString();

            switch (subCategory) {
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
            int currentIntEvent = (int) currentEvent;



            params.setMargins((currentIntEvent * 2) + 300, topMargin, 0, 0);
            params.width = 150;
            params.height = 150;
            if (subCategory == "Start") {
                params.setMargins((currentIntEvent * 2), topMargin, 0, 0);
                params.width = 300;
                params.height = 300;
            }
            indexButton.setLayoutParams(params);


        }


    }


    public void popup(View v, int location) {


        String vCategory = "";

        switch (v.getId()) {

            case 2131493019:
                vCategory = "Surgery";
                break;
            case 2131493020:
                vCategory = "Xray";
                break;
            case 2131493021:
                vCategory = "Heart";
                break;
            case 2131493022:
                vCategory = "Blood";
                break;
            case 2131493023:
                vCategory = "Dialys";
                break;

        }
        final String subCategory = vCategory;

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.addeventpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1200, 1200);

        popupWindow.setFocusable(true);
        popupWindow.update();


        TextView categoryText = (TextView) popupView.findViewById(R.id.txt_category_journey);
        ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.btn_saveEvent_Journey);
        ImageView categoryImage = (ImageView) popupView.findViewById(R.id.img_category_addevent_journey);
        final EditText notes = (EditText) popupView.findViewById(R.id.txt_notes_journey);
        final DatePicker datePicker = (DatePicker) popupView.findViewById(R.id.datepicker_journey);
        final TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.timepicker_journey);
        timePicker.setIs24HourView(true);
        categoryText.setText(vCategory);


        System.out.println("popup location = " + location + "     popup id = " + vID.getId());

        Calendar c = Calendar.getInstance();
        c.setTime(journeyStart);
        location = (location/172);
        c.add(Calendar.DATE, location-2);



        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        switch (subCategory) {
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


        saveButton.setOnClickListener(new Button.OnClickListener() {

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
                eventsSameDate = 0;

                Date date = cal.getTime();

                for (int i = 0; i < eventList.size(); i++) {

                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        String thisEvent = df.format(date);
                        String indexEvent = df.format(eventList.get(i).startDate);

                        if (thisEvent.equals(indexEvent)) {
                            eventsSameDate = eventsSameDate +1;
                      System.out.println(eventsSameDate);

                        }
                    }
                if (eventsSameDate < 3) {
                    if (date.getTime() > currentDate.getTime()) {

                        Events event = new Events(subCategory, notes.getText().toString(), date, null, null, null);
                        eventList.add(event);
                        createEventButton();
                        popupWindow.dismiss();
                        String toastMessage = "Event created!";
                        toastFunction(toastMessage);
                    } else {
                        System.out.println("can't set date before start");
                    }
                }else{
                    String message = "you can maximum have three events per day!";
                    toastFunction(message);
                }
            }
        });

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        popupWindow.showAsDropDown(relativeLayout, 450, 0);


    }

    private void createEventButton() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        ImageButton btn = new ImageButton(this);
        btn.setId(eventList.size() -1);
        final int id_ = btn.getId();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetail(id_);
            }
        });

        String subCategory = eventList.get(id_).category.toString();

        switch (subCategory) {
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

        ImageButton indexButton = ((ImageButton) findViewById(eventList.size() -1));

        currentEvent = eventList.get(id_).startDate.getTime() - startDate;
        currentEvent = currentEvent / 1000000;
        int currentIntEvent = (int) currentEvent;
        System.out.println(currentIntEvent);

        for (int i = 0; i < eventList.size(); i++) {
            if (i != id_) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String thisEvent = df.format(eventList.get(id_).startDate);
                String indexEvent = df.format(eventList.get(i).startDate);

                if (thisEvent.equals(indexEvent)) {
                    eventsSameDate = eventsSameDate +1;
                    ImageButton collidateButton= ((ImageButton) findViewById(i));

                    topMargin = (int)collidateButton.getY() + 160;
                    if (topMargin > 330){
                        topMargin = 0;
                    }


                }
            }

        }

            params.setMargins((currentIntEvent * 2) + 300, topMargin, 0, 0);
            params.width = 150;
            params.height = 150;
            if (subCategory == "Start") {
                params.setMargins((currentIntEvent * 2), topMargin, 0, 0);
                params.width = 300;
                params.height = 300;
            }

            indexButton.setLayoutParams(params);

            topMargin = 0;
    }


    private void checkDetail(final int id_){
        System.out.println(id_);

        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.detailpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, 700, 800 );

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
                System.out.println(id_);
                ViewGroup layout = (ViewGroup) findViewById(R.id.eventLayoutJourney);
                View imageButton = layout.findViewById(id_);
                layout.removeView(imageButton);
                eventList.remove(id_);
                reArrangeBtnId(id_);
                popupWindow.dismiss();
                String toastMessage = "Event deleted!";
                toastFunction(toastMessage);
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

    private void generateClouds(){

        Collections.sort(eventList, new Comparator<Events>() {
            @Override
            public int compare(Events lhs, Events rhs) {

                return lhs.startDate.compareTo(rhs.startDate);

            }
        });
        int lastEvent = eventList.size() -1;
        long lastEventLong = eventList.get(lastEvent).startDate.getTime();
        long screenSize = lastEventLong - startDate;
        long cloudCount = screenSize/1000000;
        cloudCount = cloudCount/5;
        cloudCount = cloudCount/400;


        for (int i = 0; i <= cloudCount; i++) {

            ImageButton btn = new ImageButton(this);
            btn.setId(i+1000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(i*1000,0,0,0);
            params.width = 400;
            params.height = 200;
            Random r = new Random();
            int cloudRandom = r.nextInt(3);
            System.out.println(cloudRandom);
            switch (cloudRandom) {
                case 0:
                    btn.setBackgroundResource(R.drawable.cloud1);
                    break;
                case 1:
                    btn.setBackgroundResource(R.drawable.cloud2);
                    break;
                case 2:
                    btn.setBackgroundResource(R.drawable.cloud3);
                    break;
            }

            cloudLayout.addView(btn, params);
            System.out.println(btn.getId());
        }

    }

    private void reArrangeBtnId(int removedIndex){


        for (int i = removedIndex + 1; i <= eventList.size(); i++) {

            ViewGroup layout = (ViewGroup) findViewById(R.id.eventLayoutJourney);

            View imageButton = layout.findViewById(i);

                int buttonOldId = imageButton.getId();
                final int newID = buttonOldId - 1;
                imageButton.setId(newID);

            imageButton.setOnClickListener(null);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkDetail(newID);
                }
            });
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
                    eventLocation = (int) event.getX();

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult() == true) {

                        addTreatment(vID, eventLocation);
                        System.out.println(vID);
                    }

                default:
                    break;
            }
            return true;
        }
    }

    private void toastFunction(String toastMessage){

        Toast.makeText(this, toastMessage,
                Toast.LENGTH_SHORT).show();



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


    }

}


























































