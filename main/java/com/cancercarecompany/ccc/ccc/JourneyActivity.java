package com.cancercarecompany.ccc.ccc;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;

import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class JourneyActivity extends AppCompatActivity {

    String languageString;

    ArrayList<Event> eventList;
    ArrayList<Question> questionList;


    int patientID = 0;
    int personID = 0;
    int lastButtonX;

    MediaController mediaController;

    ImageView page1;
    ImageView page2;
    ImageView page3;
    ImageView eventInfoImage;
    ImageView lion;
    ImageButton question_sign;
    ImageView sign1;
    ImageView sign2;
    ImageView sign3;

    ImageButton sun;
    Boolean sunSwitch = true;
    Boolean questionCreated = false;
    int pages = 0;
    int currentPage = 1;

    int location = 0;

    String eventPage1 = "";
    String eventPage2 = "";
    String eventPage3 = "";
    VideoView videoView;


    RelativeLayout relativeLayout;
    RelativeLayout swipeLayout;
    int topMargin = 0;
    ImageView car;
    ImageView img_lion_text;
    long startDate;
    long currentEvent;
    int eventLocation = 0;
    Date currentDate;
    Date journeyStart;

    HorizontalScrollView eventScroll;
    HorizontalScrollView bottomScroll;
    HorizontalScrollView Scroll_background3;
    HorizontalScrollView Scroll_background2;
    HorizontalScrollView Scroll_background;
    HorizontalScrollView Scroll_bushes;
    HorizontalScrollView Scroll_lion;

    RelativeLayout journeyScreen;
    GridLayout layoutButtons;
    RelativeLayout eventLayout;
    RelativeLayout containerLayout;
    RelativeLayout bottomLayout;
    RelativeLayout lion_layer;
    RelativeLayout front_bushes_layer;
    GridLayout relativeLayout3;
    RelativeLayout big_mountain_layer;
    RelativeLayout bushes_layer;
    RelativeLayout mountains_layer;
    RelativeLayout background_layer;
    RelativeLayout road_layer;
    RelativeLayout cloud_layout;

    int eventsSameDate = 0;
    String subCategoryClicked = "";
    LinearLayout wholeScreen;
    int width;
    int height;
    int containerHeight;
    int containerWidth;
    int carIntPosition = 0;
    float screenPositionClicked;
    TextView eventInfoText;
    TextView eventHeadline;

    ConnectionHandler connectHandler;

    int soundResourceId;

    View vID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(getApplicationContext()).clearDiskCache();
            }
        }).start();

        connectHandler = ConnectionHandler.getInstance();

        Glide.get(getApplicationContext()).clearMemory();

        eventList = new ArrayList<Event>();
        questionList = new ArrayList<Question>();

        // Check language settings
        SharedPreferences prefs = this.getSharedPreferences(
                "language_settings", Context.MODE_PRIVATE);

        languageString = prefs.getString("language_settings", "");
        System.out.println("LANGUAGE SETTINGS: "+languageString);
        //////////////////////////

        connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy) {}

        if (connectHandler.events.event_data != null){
            for (int i = 0; i < connectHandler.events.event_data.size(); i++) {
                eventList.add(connectHandler.events.event_data.get(i));
            }
        }

        Scroll_background = (HorizontalScrollView) findViewById(R.id.Scroll_background);
        Scroll_background2 = (HorizontalScrollView) findViewById(R.id.Scroll_background2);
        Scroll_background3 = (HorizontalScrollView) findViewById(R.id.Scroll_background3);
        eventScroll = (HorizontalScrollView) findViewById(R.id.Scroll_eventlayer);
        bottomScroll = (HorizontalScrollView) findViewById(R.id.Scroll_roadlayer);
        Scroll_bushes = (HorizontalScrollView) findViewById(R.id.Scroll_Bushes);
        Scroll_lion = (HorizontalScrollView) findViewById(R.id.Scroll_lion);

        lion = (ImageView) findViewById(R.id.img_lion);
        img_lion_text = (ImageView) findViewById(R.id.img_lion_text);
        background_layer = (RelativeLayout) findViewById(R.id.relativeLayout_background);
        bushes_layer = (RelativeLayout) findViewById(R.id.bushes_layer);
        big_mountain_layer = (RelativeLayout) findViewById(R.id.big_mountains_layer);
        car = (ImageView) findViewById(R.id.img_car_journey);

        cloud_layout = (RelativeLayout) findViewById(R.id.cloud_layout);
        road_layer = (RelativeLayout) findViewById(R.id.roadlayer);
        layoutButtons = (GridLayout) findViewById(R.id.relativeLayout3);
        containerLayout = (RelativeLayout) findViewById(R.id.layerCoantainerJourney);
        eventLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);
        bottomLayout = (RelativeLayout) findViewById(R.id.relativeLayout_roadlayout);
        front_bushes_layer = (RelativeLayout) findViewById(R.id.front_bushes_layer);
        journeyScreen = (RelativeLayout) findViewById(R.id.journeyscreen);
        mountains_layer = (RelativeLayout) findViewById(R.id.mountains_layer);
        lion_layer = (RelativeLayout) findViewById(R.id.lion_layer);
        wholeScreen = (LinearLayout) findViewById(R.id.journeyLayout);
        sun = (ImageButton) findViewById(R.id.btn_sun_journey);

        sign1 = (ImageView) findViewById(R.id.sign1);
        sign2 = (ImageView) findViewById(R.id.sign2);
        sign3 = (ImageView) findViewById(R.id.sign3);


        relativeLayout3 = (GridLayout) findViewById(R.id.relativeLayout3);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        currentDate = Calendar.getInstance().getTime();
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
        String timeString = new SimpleDateFormat("kk:mm:ss").format(currentDate);

        if (eventList.size() == 0) {

            Event startEvent = new Event(0, patientID, personID, 0, "", "start", "start", dateString, timeString, "Min resa startar här!", "", "");
            eventList.add(startEvent);
            connectHandler.createEvent(startEvent);
        }
        journeyStart = convertToDate(eventList.get(0).date, eventList.get(0).time);

        ((ViewGroup) car.getParent()).removeView(car);


        animateSun();
        refreshEvents();
        adjustLayouts();

        final ViewTreeObserver observer = containerLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                containerHeight = containerLayout.getHeight();
                containerWidth = containerLayout.getWidth();
                generateCar();
                generateBushes();
                generateMountains();
                generateLion();
                generateSigns();
                generateClouds();

                containerLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        eventScroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollX = eventScroll.getScrollX();
                Scroll_background.setScrollX(scrollX / 4);
                Scroll_background2.setScrollX(scrollX / 3);
                Scroll_background3.setScrollX(scrollX / 2);
                Scroll_lion.setScrollX(scrollX / 2);
                bottomScroll.setScrollX(scrollX * 2);
                Scroll_bushes.setScrollX(scrollX * 3);

            }
        });


        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunPopup();
            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCar(carIntPosition);
            }
        });

        eventScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    Rect hitBox = new Rect();
                    lion.getGlobalVisibleRect(hitBox);
                    if (hitBox.contains((int) event.getRawX(), (int) event.getRawY())) {
                        lionPopup("lion");
                    }
                    sign1.getGlobalVisibleRect(hitBox);
                    if (hitBox.contains((int) event.getRawX(), (int) event.getRawY())) {
                        lionPopup("sign1");
                    }
                    sign2.getGlobalVisibleRect(hitBox);
                    if (hitBox.contains((int) event.getRawX(), (int) event.getRawY())) {
                        lionPopup("sign2");
                    }
                    sign3.getGlobalVisibleRect(hitBox);
                    if (hitBox.contains((int) event.getRawX(), (int) event.getRawY())) {
                        lionPopup("sign3");
                    }


                    if (questionCreated == true) {
                        Rect hitBoxQuestion = new Rect();
                        question_sign.getGlobalVisibleRect(hitBoxQuestion);
                        if (hitBoxQuestion.contains((int) event.getRawX(), (int) event.getRawY())) {
                            questionPopup();
                        }
                    }



                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void careTeam() {
        Intent myIntent = new Intent(this, CareTeamActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void journal() {
        Intent myIntent = new Intent(this, JournalActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void addTreatment(View v) {
        System.out.println(v.getId());

    }

    private void refreshEvents() {

        for (int i = 0; i < eventList.size(); i++) {

            ViewGroup layout = (ViewGroup) findViewById(R.id.relativeLayout_eventlayer);
            View imageButton = layout.findViewById(i);
            layout.removeView(imageButton);
        }

        Collections.sort(eventList, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {

                return lhs.date.compareTo(rhs.date);

            }
        });

        startDate = convertToDate(eventList.get(0).date, eventList.get(0).time).getTime();
        journeyStart = convertToDate(eventList.get(0).date, eventList.get(0).time);

        for (int i = 0; i < eventList.size(); i++) {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            ImageButton btn = new ImageButton(this);
            btn.setId(i);
            final int id_ = btn.getId();

            if (i % 3 == 0) {
                topMargin = 100;
            } else if (i % 3 == 1) {
                topMargin = 100 + (height / 7);
            } else if (i % 3 == 2) {
                topMargin = 100 + (height / 7) + (height / 7);
            }


            subCategoryClicked = eventList.get(i).sub_category.toString();

            switch (subCategoryClicked) {
                case "medical_oncologist":
                    btn.setBackgroundResource(R.drawable.event_medical_oncologist_bubble);
                    break;
                case "hematologist_doctor":
                    btn.setBackgroundResource(R.drawable.event_hematologist_doctor_bubble);
                    break;
                case "nurse":
                    btn.setBackgroundResource(R.drawable.event_nurse_bubble);
                    break;
                case "bloodtest":
                    btn.setBackgroundResource(R.drawable.event_bloodtest_bubble);
                    break;
                case "dentist":
                    btn.setBackgroundResource(R.drawable.event_dentist_bubble);
                    break;
                case "surgeon":
                    btn.setBackgroundResource(R.drawable.event_surgeon_bubble);
                    break;
                case "anestetisten":
                    btn.setBackgroundResource(R.drawable.event_anestetisten_bubble);
                    break;
                case "therapist":
                    btn.setBackgroundResource(R.drawable.event_therapist_bubble);
                    break;
                case "physiotherapist":
                    btn.setBackgroundResource(R.drawable.event_physiotherapist_bubble);
                    break;
                case "dietician":
                    btn.setBackgroundResource(R.drawable.event_dietician_bubble);
                    break;
                case "mr":
                    btn.setBackgroundResource(R.drawable.event_mr_bubble);
                    break;
                case "dt":
                    btn.setBackgroundResource(R.drawable.dt);
                    break;
                case "hearing_tests":
                    btn.setBackgroundResource(R.drawable.event_hearing_tests_bubble);
                    break;
                case "bone_marrow_samples":
                    btn.setBackgroundResource(R.drawable.event_bone_marrow_samples_bubble);
                    break;
                case "eeg":
                    btn.setBackgroundResource(R.drawable.event_eeg_bubble);
                    break;
                case "ekg":
                    btn.setBackgroundResource(R.drawable.event_ekg_bubble);
                    break;
                case "kidney_investigation":
                    btn.setBackgroundResource(R.drawable.event_kidney_investigation_bubble);
                    break;
                case "ultrasound":
                    btn.setBackgroundResource(R.drawable.event_ultrasound_bubble);
                    break;
                case "alternating current":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "transplantation":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "cytostatika":
                    btn.setBackgroundResource(R.drawable.event_cytostatika_bubble);
                    break;
                case "surgery":
                    btn.setBackgroundResource(R.drawable.event_surgery_bubble);
                    break;
                case "stem_cell_transplantation":
                    btn.setBackgroundResource(R.drawable.event_stem_cell_transplantation_bubble);
                    break;
                case "radiation":
                    btn.setBackgroundResource(R.drawable.event_radiation_bubble);
                    break;
                case "dialysis":
                    btn.setBackgroundResource(R.drawable.event_dialysis_bubble);
                    break;
                case "biological_therapy":
                    btn.setBackgroundResource(R.drawable.event_biological_therapy_bubble);
                    break;
                case "targeted_therapy":
                    btn.setBackgroundResource(R.drawable.event_targeted_therapy_bubble);
                    break;
                case "portacat":
                    btn.setBackgroundResource(R.drawable.event_portacat_bubble);
                    break;
                case "hospital":
                    btn.setBackgroundResource(R.drawable.event_hospital_bubble);
                    break;
                case "picture_memory":
                    btn.setBackgroundResource(R.drawable.photominne);
                    break;
                case "start":
                    btn.setBackgroundResource(R.drawable.journeystart);
                    break;
            }


            relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);
            relativeLayout.addView(btn, params);

            ImageButton indexButton = ((ImageButton) findViewById(i));

            Calendar currentEventCal = Calendar.getInstance();
            currentEventCal.setTime(convertToDate(eventList.get(i).date, eventList.get(i).time));
            currentEventCal.set(Calendar.HOUR_OF_DAY, 0);
            currentEventCal.set(Calendar.MINUTE, 0);
            currentEventCal.set(Calendar.SECOND, 0);
            currentEventCal.set(Calendar.MILLISECOND, 0);

            Calendar startEventCal = Calendar.getInstance();
            startEventCal.setTime(journeyStart);
            startEventCal.set(Calendar.HOUR_OF_DAY, 0);
            startEventCal.set(Calendar.MINUTE, 0);
            startEventCal.set(Calendar.SECOND, 0);
            startEventCal.set(Calendar.MILLISECOND, 0);

            long currentEventLong = currentEventCal.getTime().getTime() - startEventCal.getTime().getTime();
            currentEventLong = currentEventLong / 1000000;
            int currentEventInt = (int) currentEventLong;


            params.setMargins((currentEventInt * 2) + 300, topMargin, 0, 0);
            params.width = height / 7;
            params.height = height / 7;
            if (subCategoryClicked.equals("start")) {
                params.setMargins((currentEventInt * 2), topMargin, 0, 0);
                params.width = (height / 7) * 2;
                params.height = (height / 7) * 2;
            }

            indexButton.setLayoutParams(params);
            lastButtonX = (currentEventInt * 2) + 300;
        }

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

    private void generateCar() {

        RelativeLayout.LayoutParams paramsCar = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        long carPosition = currentDate.getTime() - startDate;
        carPosition = carPosition / 1000000;
        carIntPosition = (int) carPosition;
        carIntPosition = (carIntPosition * 2) + 300;

        if (carIntPosition < width) {
            paramsCar.setMargins(carIntPosition, 0, 0, 50);
        } else {
            paramsCar.setMargins(0, 0, 0, 50);
        }

        car.setLayoutParams(paramsCar);
        bottomLayout.addView(car, paramsCar);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) car.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        car.setLayoutParams(layoutParams);

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateCar(carIntPosition);
            }
        });

    }

    private void generateClouds() {

        int cloudCount = background_layer.getWidth() / 500;

        for (int i = 0; i <= cloudCount; i++) {

            ImageButton btn = new ImageButton(this);
            btn.setId(i + 1000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            Random r = new Random();
            int cloudRandom = r.nextInt(3);
            int cloudPositionRandom = r.nextInt(5);
            int cloudPositionY = 0;

            switch (cloudPositionRandom){
                case 0:
                    cloudPositionY = 0;
                    break;
                case 1:
                    cloudPositionY = height/16;
                    break;
                case 2:
                    cloudPositionY = height/13;
                    break;
                case 3:
                    cloudPositionY = height/10;
                    break;
                case 4:
                    cloudPositionY = height/9;
                    break;
            }
            switch (cloudRandom) {
                case 0:
                    btn.setBackgroundResource(R.drawable.cloud1_small);
                    params.setMargins(i * width/2 -100, cloudPositionY, 0, 0);
                    break;
                case 1:
                    btn.setBackgroundResource(R.drawable.cloud2_small);
                    params.setMargins(i * width/2 -100, cloudPositionY, 0, 0);
                    break;
                case 2:
                    btn.setBackgroundResource(R.drawable.cloud3_small);
                    params.setMargins(i * width/2 -100, cloudPositionY, 0, 0);
                    break;
            }

            cloud_layout.addView(btn, params);
        }

    }

    private void reArrangeBtnId(int removedIndex) {


        for (int i = removedIndex + 1; i <= eventList.size(); i++) {

            ViewGroup layout = (ViewGroup) findViewById(R.id.relativeLayout_eventlayer);

            View imageButton = layout.findViewById(i);

            int buttonOldId = imageButton.getId();
            final int newID = buttonOldId - 1;
            imageButton.setId(newID);

            imageButton.setOnClickListener(null);
        }
    }

    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, final DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    System.out.println(event.getX());
                    location =(int) event.getX();
                    System.out.println("LOCATION = " + location);
                    addTreatment(vID);


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult() == false){
                        location = 0;
                        addTreatment(vID);
                    }

                default:
                    break;
            }
            return true;
        }
    }

    private void toastFunction(String toastMessage) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(toastMessage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }


    private void adjustLayouts() {
        System.out.println(lastButtonX);
        if (lastButtonX < 8000){
            lastButtonX = 8000;
            eventLayout.getLayoutParams().width = lastButtonX;
        }
        cloud_layout.getLayoutParams().width = lastButtonX;
        big_mountain_layer.getLayoutParams().width = lastButtonX;
        big_mountain_layer.setBackgroundResource(R.drawable.backmountainxml);
        mountains_layer.getLayoutParams().width = lastButtonX;
        lion_layer.getLayoutParams().width = lastButtonX;
        lion_layer.setBackgroundResource(R.drawable.lionnaked);
        bushes_layer.getLayoutParams().width = lastButtonX * 2;
        bushes_layer.setBackgroundResource(R.drawable.bushesxml);
        road_layer.getLayoutParams().width = lastButtonX * 2;
        road_layer.setBackgroundResource(R.drawable.roadxml);
        front_bushes_layer.getLayoutParams().width = lastButtonX * 3;

    }


    private void generateBushes() {

        int bushesCount = lastButtonX / 500;
        bushesCount = bushesCount * 3;
        for (int i = 0; i <= bushesCount; i++) {

            ImageView img = new ImageView(this);
            img.setId(i + 2000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Random r = new Random();
            int bushesRandom = r.nextInt(2);

            switch (bushesRandom) {
                case 0:
                    img.setBackgroundResource(R.drawable.bushes1_small);
                    params.setMargins(i * width/2, 0, 0, 0);

                    break;
                case 1:

                    img.setBackgroundResource(R.drawable.bushes2_small);
                    params.setMargins(i * width/2, 0, 0, 0);

                    break;

            }
            front_bushes_layer.addView(img, params);

            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams)img.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            img.setLayoutParams(layoutParams);

        }
    }

    private void generateLion(){
        final RelativeLayout.LayoutParams paramsLion = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        paramsLion.setMargins(carIntPosition / 2 + width/2,0,0,0);
        lion.setLayoutParams(paramsLion);
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams)lion.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        lion.setLayoutParams(layoutParams);

        if (carIntPosition < width) {
            lionMessage();
        }else{
//            animateCar(carIntPosition);
        }

    }

    private void generateMountains() {
        int mountainsCount = lastButtonX / 500;
        mountainsCount = mountainsCount * 3;
        for (int i = 0; i <= mountainsCount; i++) {

            ImageView img = new ImageView(this);
            img.setId(i + 3000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            Random r = new Random();
            int bushesRandom = r.nextInt(3);

            switch (bushesRandom) {
                case 0:
                    img.setBackgroundResource(R.drawable.hill1_small);
                    params.setMargins(i * width/2 -100, 0, 0, 0);
                    break;
                case 1:
                    img.setBackgroundResource(R.drawable.hill2_small);
                    params.setMargins(i * width/2 -100, 0, 0, 0);
                    break;
                case 2:
                    img.setBackgroundResource(R.drawable.hill3_small);
                    params.setMargins(i * width/2 -100, 0, 0, 0);
                    break;

            }
            mountains_layer.addView(img, params);

            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams)img.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            img.setLayoutParams(layoutParams);
        }
    }

    private void animateCar(final int carIntPosition) {

        final RelativeLayout.LayoutParams paramsCar2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsCar2.setMargins((carIntPosition * 2)-800, 0, 0, 50);


        TranslateAnimation transAnimation1 = new TranslateAnimation(0, 2000, 0, 0);
        transAnimation1.setDuration(3500);
        car.startAnimation(transAnimation1);

        transAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("animation start");
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.carsound);
                mp.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                System.out.println("animation end");
                eventScroll.post(new Runnable() {


                    @Override
                    public void run() {
                        eventScroll.isSmoothScrollingEnabled();
                        eventScroll.smoothScrollTo((carIntPosition)-500, 0);
                        car.setLayoutParams(paramsCar2);

                        RelativeLayout.LayoutParams layoutParams =
                                (RelativeLayout.LayoutParams)car.getLayoutParams();
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                        car.setLayoutParams(layoutParams);
                        lionMessage();
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void lionMessage(){

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.lion);
        mp.start();
        CountDownTimer lionTimer = new CountDownTimer(6000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                final RelativeLayout.LayoutParams paramsLionText = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                paramsLionText.setMargins(lion.getLeft() - 100,lion.getTop() - height/4,0,0);
                img_lion_text.setLayoutParams(paramsLionText);


                img_lion_text.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                img_lion_text.setVisibility(View.INVISIBLE);
            }
        }.start();

    }

    private void lionPopup(String clickedImage){

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.lion_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        relativeLayout = (RelativeLayout) findViewById(R.id.layerCoantainerJourney);


        ImageButton cancel = (ImageButton)popupView.findViewById(R.id.img_journey_cancellion);
        TextView lionText = (TextView)popupView.findViewById(R.id.txt_journey_lion);
        TextView lionHeadText = (TextView)popupView.findViewById(R.id.txt_journey_lion_head);
        ImageButton soundButton = (ImageButton)popupView.findViewById(R.id.btn_journey_sign_sound);

        switch(clickedImage){
            case "lion":
                lionText.setText(getResources().getString(getResources().getIdentifier("txt_lion", "string", getPackageName())));
                lionHeadText.setText(getResources().getString(getResources().getIdentifier("txt_lion_head", "string", getPackageName())));
                soundResourceId = getApplicationContext().getResources().getIdentifier("journey_lion", "raw", getPackageName());
                break;
            case "sign1":
                lionText.setText(getResources().getString(getResources().getIdentifier("txt_sign1", "string", getPackageName())));
                lionHeadText.setText(getResources().getString(getResources().getIdentifier("txt_sign1_head", "string", getPackageName())));
                soundResourceId = getApplicationContext().getResources().getIdentifier("journey_sign1", "raw", getPackageName());
                break;
            case "sign2":
                lionText.setText(getResources().getString(getResources().getIdentifier("txt_sign2", "string", getPackageName())));
                lionHeadText.setText(getResources().getString(getResources().getIdentifier("txt_sign2_head", "string", getPackageName())));
                soundResourceId = getApplicationContext().getResources().getIdentifier("journey_sign2", "raw", getPackageName());
                break;
            case "sign3":
                lionText.setText(getResources().getString(getResources().getIdentifier("txt_sign3", "string", getPackageName())));
                lionHeadText.setText(getResources().getString(getResources().getIdentifier("txt_sign3_head", "string", getPackageName())));
                soundResourceId = getApplicationContext().getResources().getIdentifier("journey_sign3", "raw", getPackageName());
                break;

        }

        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupView.bringToFront();
        popupWindow.setFocusable(true);
        popupWindow.update();

        soundButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (soundResourceId != 0) {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), soundResourceId);
                    mp.start();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }

    private void sunPopup() {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.sunpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, (int) (width * 0.40), (int) (height * 0.60));
        relativeLayout = (RelativeLayout) findViewById(R.id.layerCoantainerJourney);

        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

        popupView.bringToFront();
        popupWindow.setFocusable(true);
        popupWindow.update();

        ImageButton cancel = (ImageButton) popupView.findViewById(R.id.img_journey_cancelquestion);
        ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.img_journey_savequestion);
        final EditText journeyQuestion = (EditText) popupView.findViewById(R.id.et_journey_question);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateString = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);

                Question newQuestion = new Question(0, patientID, personID, journeyQuestion.getText().toString(), null, dateString, null, "unanswered");
                questionList.add(newQuestion);

                question_sign= new ImageButton(JourneyActivity.this);
                question_sign.setTag("question_sign"+ (questionList.size() - 1));

                question_sign.setBackgroundResource(R.drawable.question_sign);
                lion_layer.addView(question_sign);
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(carIntPosition / 2 + width / 5,0,0,0);
                question_sign.setLayoutParams(params);
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams)question_sign.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                question_sign.setLayoutParams(layoutParams);
                popupWindow.dismiss();
                eventScroll.smoothScrollTo(carIntPosition - width/3, 0);
                questionCreated = true;

            }
        });



    }

    private void questionPopup() {

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.question_popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, (int) (width * 0.50), (int) (height * 0.55));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);


        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupView.bringToFront();
        popupWindow.setFocusable(true);
        popupWindow.update();

        ImageButton cancel = (ImageButton) popupView.findViewById(R.id.img_journey_cancel_question);
        TextView question = (TextView) popupView.findViewById(R.id.txt_journey_question);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        question.setText(questionList.get(questionList.size() - 1).question.toString());



    }

    private void animateSun() {

        TranslateAnimation transAnimation1 = new TranslateAnimation(0, 0, 0, 0);
        transAnimation1.setDuration(300);
        transAnimation1.setRepeatCount(-1);
        transAnimation1.setRepeatMode(Animation.INFINITE);
        sun.setAnimation(transAnimation1);

        transAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

                if (sunSwitch == true) {
                    sun.setBackgroundResource(R.drawable.sun);

                    sunSwitch = false;
                } else {
                    sun.setBackgroundResource(R.drawable.sun2);
                    sunSwitch = true;
                }


            }
        });


    }

    private void generateSigns() {

        //sign1
        RelativeLayout.LayoutParams paramsSign1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsSign1.setMargins(lastButtonX/10,0,0,0);
        sign1.setLayoutParams(paramsSign1);
        RelativeLayout.LayoutParams layoutParams1 =
                (RelativeLayout.LayoutParams)sign1.getLayoutParams();
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

        sign1.setLayoutParams(paramsSign1);
        sign1.setLayoutParams(layoutParams1);


        ///sign 2

        RelativeLayout.LayoutParams paramsSign2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsSign2.setMargins(lastButtonX / 3,0,0,0);
        sign2.setLayoutParams(paramsSign2);
        RelativeLayout.LayoutParams layoutParams2 =
                (RelativeLayout.LayoutParams)sign2.getLayoutParams();
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        sign2.setLayoutParams(paramsSign2);
        sign2.setLayoutParams(layoutParams2);
        sign2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunPopup();
            }
        });

        ///sign3

        final RelativeLayout.LayoutParams paramsSign3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsSign3.setMargins(lastButtonX / 2,0,0,0);
        sign3.setLayoutParams(paramsSign3);
        RelativeLayout.LayoutParams layoutParams3 =
                (RelativeLayout.LayoutParams)sign3.getLayoutParams();
        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        sign3.setLayoutParams(paramsSign3);
        sign3.setLayoutParams(layoutParams3);
        sign3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunPopup();
            }
        });

    }

    private Date convertToDate(String date, String time){
        Date convertedDate = new Date();
        date = date.split("T")[0];
        date += "T" +time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss");
        try {
            convertedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Failure when parsing the dateString");
        }

        return convertedDate;
    }

    public void onClickStartCar(View view) {
        animateCar(carIntPosition);
    }

}