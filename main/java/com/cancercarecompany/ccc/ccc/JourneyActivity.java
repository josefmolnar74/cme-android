package com.cancercarecompany.ccc.ccc;

import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.Image;
import android.media.MediaPlayer;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
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
    ImageButton addAppointment;
    ImageButton addTreatment;
    ImageButton addTest;
    ImageButton addOther;
    ImageButton sun;

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
    String subCategoryClicked = "";
    Date date;
    RelativeLayout wholeScreen;

    View vID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        eventList = new ArrayList<Events>();

        car = (ImageView) findViewById(R.id.img_car_journey);
        addAppointment = (ImageButton) findViewById(R.id.btn_appointment_journey);
        addTreatment = (ImageButton) findViewById(R.id.btn_treatment_journey);
        addTest = (ImageButton) findViewById(R.id.btn_test_journey);
        addOther = (ImageButton) findViewById(R.id.btn_other_journey);
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
        wholeScreen = (RelativeLayout) findViewById(R.id.journeyscreen);
        sun = (ImageButton) findViewById(R.id.btn_sun_journey);

        addAppointment.setOnTouchListener(new MyTouchListener());
        eventLayout.setOnDragListener(new MyDragListener());
        addTreatment.setOnTouchListener(new MyTouchListener());
        addTest.setOnTouchListener(new MyTouchListener());
        addOther.setOnTouchListener(new MyTouchListener());

        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                long eventPosition = currentDate.getTime() - startDate;
                eventPosition = eventPosition / 1000000;
                int eventIntPosition = (int) eventPosition;

                addTreatment(v, eventIntPosition);

            }
        });


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
             date = sdf.parse("10/04/2016");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        currentDate = Calendar.getInstance().getTime();
        Events diagnoseStart = new Events("Start", "Start", "Journey starts here", date , null, null, null);
        eventList.add(diagnoseStart);
        journeyStart = eventList.get(0).startDate;

        TransitionDrawable transition = (TransitionDrawable) sun.getBackground();
        transition.startTransition(1000);
        transition.reverseTransition(500);


        animateSun();
        ExampleJourney();
        refreshEvents();
        generateClouds();
        generateBushes();


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


        long carPosition = currentDate.getTime() - startDate;
        carPosition = carPosition / 1000000;
        int carIntPosition = (int) carPosition;

       // paramsCar.setMargins(carIntPosition * 6, -10, 0, 0);
        paramsCar.setMargins(0, -10, 0, 0);
        paramsCar.width = 300;
        paramsCar.height = 200;


        car.setLayoutParams(paramsCar);
        animateCar(carIntPosition);


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

            String category = eventList.get(i).subCategory.toString();

            switch (category) {
                case "Doctor":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Nurse":
                    btn.setBackgroundResource(R.drawable.orangebubblee);
                    break;
                case "Curator":
                    btn.setBackgroundResource(R.drawable.bluebubble);
                    break;
                case "Dietitian":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Physiotherapist":
                    btn.setBackgroundResource(R.drawable.orangebubble);
                    break;
                case "Dentist":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Anestetist":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Surgeon":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Chemotherapy":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Radiation":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "TargetedTherapy":
                    btn.setBackgroundResource(R.drawable.treatmenttargetbubble);
                    break;
                case "Dialys":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "BiologicalTherapy":
                    btn.setBackgroundResource(R.drawable.treatmentbiologicbubble);
                    break;
                case "Transplantation":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Bloodtest":
                    btn.setBackgroundResource(R.drawable.testbloodbubble);
                    break;
                case "Heart":
                    btn.setBackgroundResource(R.drawable.testheartbubble);
                    break;
                case "ImageDiagnosis":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "CellCompatibility":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Nutrition":
                    btn.setBackgroundResource(R.drawable.otherdropbubble);
                    break;
                case "Portakat":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Hospital":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "ImageMemory":
                    btn.setBackgroundResource(R.drawable.greenbubble);
                    break;
                case "Start":
                    btn.setBackgroundResource(R.drawable.journeystart);
                    break;
            }


            relativeLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);
            relativeLayout.addView(btn, params);

            ImageButton indexButton = ((ImageButton) findViewById(i));

            Calendar currentEventCal = Calendar.getInstance();
            currentEventCal.setTime(eventList.get(i).startDate);
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
            currentEventLong = currentEventLong/1000000;
            int currentEventInt = (int) currentEventLong;


            params.setMargins((currentEventInt * 2) +300 , topMargin, 0, 0);
            params.width = 150;
            params.height = 150;
            if (category == "Start") {
                params.setMargins((currentEventInt * 2), topMargin, 0, 0);
                params.width = 300;
                params.height = 300;
            }
            indexButton.setLayoutParams(params);


        }


    }


    public void popup(View v, int location) {


        String vCategory = "";

        switch (v.getId()) {

            case 2131558556:
                vCategory = "Appointment";
                break;
            case 2131558557:
                vCategory = "Treatment";
                break;
            case 2131558558:
                vCategory = "Test";
                break;
            case 2131558559:
                vCategory = "Other";
                break;

        }
        final String Category = vCategory;

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.addeventpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, 1200, 1200);

        popupWindow.setFocusable(true);
        popupWindow.update();


        final TextView subCategoryText = (TextView) popupView.findViewById(R.id.txt_subcategory_journey);
        ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.btn_saveEvent_Journey);
        final EditText notes = (EditText) popupView.findViewById(R.id.txt_notes_journey);
        final DatePicker datePicker = (DatePicker) popupView.findViewById(R.id.datepicker_journey);
        final TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.timepicker_journey);
        final ImageView subCategory1 = (ImageView) popupView.findViewById(R.id.img_subcategory1);
        final ImageView subCategory2 = (ImageView) popupView.findViewById(R.id.img_subcategory2);
        final ImageView subCategory3 = (ImageView) popupView.findViewById(R.id.img_subcategory3);
        final ImageView subCategory4 = (ImageView) popupView.findViewById(R.id.img_subcategory4);
        final ImageView subCategory5 = (ImageView) popupView.findViewById(R.id.img_subcategory5);
        final ImageView subCategory6 = (ImageView) popupView.findViewById(R.id.img_subcategory6);
        final ImageView subCategory7 = (ImageView) popupView.findViewById(R.id.img_subcategory7);
        final ImageView subCategory8 = (ImageView) popupView.findViewById(R.id.img_subcategory8);

        timePicker.setIs24HourView(true);



        System.out.println("popup location = " + location + "     popup id = " + vID.getId());

        Calendar c = Calendar.getInstance();
        c.setTime(eventList.get(0).startDate);
        System.out.println(location);
        location = (location/172);
        c.add(Calendar.DATE, location-2);



        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));


        switch (Category) {
            case "Appointment":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.greenbubble);
                subCategory2.setBackgroundResource(R.drawable.bluebubble);
                subCategory3.setBackgroundResource(R.drawable.pinkbubble);
                subCategory4.setBackgroundResource(R.drawable.orangebubble);
                subCategory5.setBackgroundResource(R.drawable.orangebubblee);
                subCategory6.setBackgroundResource(R.drawable.pinkbubble);
                subCategory7.setBackgroundResource(R.drawable.bluebubble);
                subCategory8.setBackgroundResource(R.drawable.greenbubble);

                subCategoryClicked = "Doctor";
                subCategoryText.setText(subCategoryClicked);


                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Doctor";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Nurse";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Curator";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Dietitian";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Physiotherapist";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Dentist";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Anestetist";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Surgeon";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });

                break;
            case "Treatment":
                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);


                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.greenbubble);
                subCategory2.setBackgroundResource(R.drawable.bluebubble);
                subCategory3.setBackgroundResource(R.drawable.treatmenttarget);
                subCategory4.setBackgroundResource(R.drawable.orangebubble);
                subCategory5.setBackgroundResource(R.drawable.treatmentbiologic);
                subCategory6.setBackgroundResource(R.drawable.pinkbubble);

                subCategoryClicked = "Chemotherapy";
                subCategoryText.setText(subCategoryClicked);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Chemotherapy";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Radiation";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "TargetedTherapy";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Dialys";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "BiologicalTherapy";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Transplantation";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });

                break;
            case "Test":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.testblood);
                subCategory2.setBackgroundResource(R.drawable.testheart);
                subCategory3.setBackgroundResource(R.drawable.pinkbubble);
                subCategory4.setBackgroundResource(R.drawable.orangebubble);

                subCategoryClicked = "Bloodtest";
                subCategoryText.setText(subCategoryClicked);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Bloodtest";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Heart";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Image Diagnosis";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "CellCompatibility";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });

                break;
            case "Other":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.otherdrop);
                subCategory2.setBackgroundResource(R.drawable.bluebubble);
                subCategory3.setBackgroundResource(R.drawable.pinkbubble);
                subCategory4.setBackgroundResource(R.drawable.orangebubble);

                subCategoryClicked = "Nutrition";
                subCategoryText.setText(subCategoryClicked);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Nutrition";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Portakat";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Hospital";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "ImageMemory";
                        subCategoryText.setText(subCategoryClicked);
                    }
                });

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
                    if (date.getTime() > startDate) {

                        Events event = new Events(Category, subCategoryClicked, notes.getText().toString(), date, null, null, null);
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

        String subCategory = eventList.get(id_).subCategory.toString();

        switch (subCategory) {
            case "Doctor":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Nurse":
                btn.setBackgroundResource(R.drawable.orangebubblee);
                break;
            case "Curator":
                btn.setBackgroundResource(R.drawable.bluebubble);
                break;
            case "Dietitian":
                btn.setBackgroundResource(R.drawable.pinkbubble);
                break;
            case "Physiotherapist":
                btn.setBackgroundResource(R.drawable.orangebubble);
                break;
            case "Dentist":
                btn.setBackgroundResource(R.drawable.pinkbubble);
                break;
            case "Anestetist":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Surgeon":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Chemotherapy":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Radiation":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "TargetedTherapy":
                btn.setBackgroundResource(R.drawable.treatmenttargetbubble);
                break;
            case "Dialys":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "BiologicalTherapy":
                btn.setBackgroundResource(R.drawable.treatmentbiologicbubble);
                break;
            case "Transplantation":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Bloodtest":
                btn.setBackgroundResource(R.drawable.testbloodbubble);
                break;
            case "Heart":
                btn.setBackgroundResource(R.drawable.testheartbubble);
                break;
            case "ImageDiagnosis":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "CellCompatibility":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Nutrition":
                btn.setBackgroundResource(R.drawable.otherdropbubble);
                break;
            case "Portakat":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Hospital":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "ImageMemory":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Start":
                btn.setBackgroundResource(R.drawable.greenbubble);
                break;
        }


        relativeLayout = (RelativeLayout) findViewById(R.id.eventLayoutJourney);

        relativeLayout.addView(btn, params);

        ImageButton indexButton = ((ImageButton) findViewById(eventList.size() -1));

        Calendar currentEventCal = Calendar.getInstance();
        currentEventCal.setTime(eventList.get(id_).startDate);
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
        currentEventLong = currentEventLong/1000000;
        int currentEventInt = (int) currentEventLong;

       /*
        currentEvent = eventList.get(id_).startDate.getTime() - startDate;
        currentEvent = currentEvent / 1000000;
        int currentIntEvent = (int) currentEvent;
        System.out.println(currentIntEvent);
*/

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

            params.setMargins((currentEventInt * 2) + 300, topMargin, 0, 0);
            params.width = 150;
            params.height = 150;
            if (subCategory == "Start") {
                params.setMargins((currentEventInt * 2), topMargin, 0, 0);
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


            eventDetail.setText(eventList.get(id_).subCategory.toString());
            noteDetail.setText(eventList.get(id_).notes.toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd-hh:mm");
            String dateString = simpleDateFormat.format(eventList.get(id_).startDate.getTime());
            timeDetail.setText(dateString);

        switch (eventList.get(id_).subCategory){
            case "Doctor":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Nurse":
                categoryImage.setBackgroundResource(R.drawable.orangebubblee);
                break;
            case "Curator":
                categoryImage.setBackgroundResource(R.drawable.bluebubble);
                break;
            case "Dietitian":
                categoryImage.setBackgroundResource(R.drawable.pinkbubble);
                break;
            case "Physiotherapist":
                categoryImage.setBackgroundResource(R.drawable.orangebubble);
                break;
            case "Dentist":
                categoryImage.setBackgroundResource(R.drawable.journeystart);
                break;
            case "Anestetist":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Surgeon":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Chemotherapy":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Radiation":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "TargetedTherapy":
                categoryImage.setBackgroundResource(R.drawable.treatmenttargetbubble);
                break;
            case "Dialys":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "BiologicalTherapy":
                categoryImage.setBackgroundResource(R.drawable.treatmentbiologicbubble);
                break;
            case "Transplantation":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Bloodtest":
                categoryImage.setBackgroundResource(R.drawable.testbloodbubble);
                break;
            case "Heart":
                categoryImage.setBackgroundResource(R.drawable.testheartbubble);
                break;
            case "ImageDiagnosis":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "CellCompatibility":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Nutrition":
                categoryImage.setBackgroundResource(R.drawable.otherdropbubble);
                break;
            case "Portakat":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Hospital":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "ImageMemory":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "Start":
                categoryImage.setBackgroundResource(R.drawable.greenbubble);
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

    private void generateBushes() {

        Collections.sort(eventList, new Comparator<Events>() {
            @Override
            public int compare(Events lhs, Events rhs) {

                return lhs.startDate.compareTo(rhs.startDate);

            }
        });
        int lastEvent = eventList.size() - 1;
        long lastEventLong = eventList.get(lastEvent).startDate.getTime();
        long screenSize = lastEventLong - startDate;
        long signCount = screenSize / 1000000;
        signCount = signCount / 200;


        for (int i = 0; i <= signCount; i++) {

            ImageButton btn = new ImageButton(this);
            btn.setId(i + 2000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);



            Random r = new Random();
            int bushesRandom = r.nextInt(3);

            switch (bushesRandom) {
                case 0:
                    btn.setBackgroundResource(R.drawable.bushes1);
                    params.setMargins(i * 1000, 100, 0, 0);
                    params.width = 150;
                    params.height = 100;
                    break;
                case 1:
                    btn.setBackgroundResource(R.drawable.bushes2);
                    params.setMargins(i * 1000, 60, 0, 0);
                    params.width = 300;
                    params.height = 150;
                    break;
                case 2:
                    btn.setBackgroundResource(R.drawable.bushes3);
                    params.setMargins(i * 1000, 100, 0, 0);
                    params.width = 150;
                    params.height = 100;
                    break;


            }
            bottomLayout.addView(btn, params);
            System.out.println(btn.getId());

        }
    }

    private void animateCar(final int carIntPosition){


        final RelativeLayout.LayoutParams paramsCar2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
     paramsCar2.setMargins(carIntPosition * 6 + 100,-10,0,0);
                paramsCar2.width = 300;
                paramsCar2.height = 200;

        TranslateAnimation transAnimation1= new TranslateAnimation(0, 2000, 0, 0);
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
                        eventScroll.smoothScrollTo(carIntPosition * 2,0);
                        car.setLayoutParams(paramsCar2);
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    private void animateSun(){


        TranslateAnimation transAnimation1= new TranslateAnimation(0, 0, 0, 0);
        transAnimation1.setDuration(1000);
        transAnimation1.getRepeatMode();
        sun.startAnimation(transAnimation1);

        transAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("animation sun start");
                TransitionDrawable transition = (TransitionDrawable) sun.getBackground();
                transition.startTransition(1000);
                transition.reverseTransition(500);

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                System.out.println("animation repeat");
            }
        });


    }


    private void ExampleJourney(){
        Calendar c = Calendar.getInstance();
        c.setTime(eventList.get(0).startDate);

        c.add(Calendar.DATE, 1);
        Events event1 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event1);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 2);
        Events event2 = new Events("Treatment", "Dialys", "take meds before!", c.getTime(), null, null, null );
        eventList.add(event2);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 5);
        Events event3 = new Events("Appointment", "Curator", "help", c.getTime(), null, null, null );
        eventList.add(event3);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 6);
        Events event4 = new Events("Test", "Bloodtest", "bloodtest1", c.getTime(), null, null, null );
        eventList.add(event4);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 7);
        Events event5 = new Events("Other", "ImageMemory", "photo of me", c.getTime(), null, null, null );
        eventList.add(event5);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 8);
        Events event6 = new Events("Appointment", "Dentist", "xray at dentist", c.getTime(), null, null, null );
        eventList.add(event6);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 9);
        Events event7 = new Events("Treatment", "Radiation", "at hospital", c.getTime(), null, null, null );
        eventList.add(event7);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 10);
        Events event8 = new Events("Treatment", "Chemotherapy", "whole day", c.getTime(), null, null, null );
        eventList.add(event8);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 12);
        Events event9 = new Events("Treatment", "TargetedTherapy", "ohh noo =(", c.getTime(), null, null, null );
        eventList.add(event9);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 13);
        Events event10 = new Events("Treatment", "BiologicalTherapy", "evening", c.getTime(), null, null, null );
        eventList.add(event10);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 14);
        Events event11 = new Events("Treatment", "Transplantation", "zzzzz", c.getTime(), null, null, null );
        eventList.add(event11);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 15);
        Events event12 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event12);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 17);
        Events event13 = new Events("Test", "ImageDiagnosis", "take images", c.getTime(), null, null, null );
        eventList.add(event13);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 18);
        Events event14 = new Events("Test", "CellCompatibility", "wooow", c.getTime(), null, null, null );
        eventList.add(event14);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 19);
        Events event15 = new Events("Appointment", "Doctor", "Dr Edith", c.getTime(), null, null, null );
        eventList.add(event15);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 22);
        Events event16 = new Events("Appointment", "Dietitian", "eating habits", c.getTime(), null, null, null );
        eventList.add(event16);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 24);
        Events event17 = new Events("Appointment", "Physiotherapist", "at Malmö", c.getTime(), null, null, null );
        eventList.add(event17);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 26);
        Events event18 = new Events("Appointment", "Anestetist", "whole day", c.getTime(), null, null, null );
        eventList.add(event18);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 27);
        Events event19 = new Events("Appointment", "Surgeon", "eat before", c.getTime(), null, null, null );
        eventList.add(event19);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 28);
        Events event20 = new Events("Other", "Nutrition", "dropp näring", c.getTime(), null, null, null );
        eventList.add(event20);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 29);
        Events event21 = new Events("Other", "Hospital", "visit lund hospital", c.getTime(), null, null, null );
        eventList.add(event21);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 30);
        Events event22 = new Events("Other", "Portakat", "tråkigt", c.getTime(), null, null, null );
        eventList.add(event22);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 31);
        Events event23 = new Events("Appointment", "Dietitian", "eating habits", c.getTime(), null, null, null );
        eventList.add(event23);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 32);
        Events event24 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event24);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 34);
        Events event25 = new Events("Appointment", "Dentist", "xray at dentist", c.getTime(), null, null, null );
        eventList.add(event25);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 36);
        Events event26 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event26);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 40);
        Events event27 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event27);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 41);
        Events event28 = new Events("Treatment", "Transplantation", "zzzzz", c.getTime(), null, null, null );
        eventList.add(event28);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 45);
        Events event29 = new Events("Appointment", "Curator", "help", c.getTime(), null, null, null );
        eventList.add(event29);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 46);
        Events event30 = new Events("Treatment", "Chemotherapy", "whole day", c.getTime(), null, null, null );
        eventList.add(event30);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 48);
        Events event31 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event31);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 52);
        Events event32 = new Events("Other", "ImageMemory", "photo of me", c.getTime(), null, null, null );
        eventList.add(event32);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 55);
        Events event33 = new Events("Appointment", "Anestetist", "whole day", c.getTime(), null, null, null );
        eventList.add(event33);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 57);
        Events event34 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event34);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 61);
        Events event35 = new Events("Other", "Portakat", "tråkigt", c.getTime(), null, null, null );
        eventList.add(event35);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 62);
        Events event36 = new Events("Appointment", "Dietitian", "eating habits", c.getTime(), null, null, null );
        eventList.add(event36);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 64);
        Events event37 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event37);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 66);
        Events event38 = new Events("Appointment", "Dietitian", "eating habits", c.getTime(), null, null, null );
        eventList.add(event38);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 70);
        Events event39 = new Events("Test", "Bloodtest", "bloodtest1", c.getTime(), null, null, null );
        eventList.add(event39);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 71);
        Events event40 = new Events("Treatment", "Chemotherapy", "whole day", c.getTime(), null, null, null );
        eventList.add(event40);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 74);
        Events event41 = new Events("Test", "Bloodtest", "bloodtest1", c.getTime(), null, null, null );
        eventList.add(event41);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 76);
        Events event42 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event42);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 78);
        Events event43 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event43);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 80);
        Events event44 = new Events("Test", "CellCompatibility", "wooow", c.getTime(), null, null, null );
        eventList.add(event44);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 82);
        Events event45 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event45);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 84);
        Events event46 = new Events("Appointment", "Doctor", "at hospital", c.getTime(), null, null, null );
        eventList.add(event46);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 86);
        Events event47 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event47);
        c.setTime(currentDate);
        c.add(Calendar.DATE, 88);
        Events event48 = new Events("Test", "CellCompatibility", "wooow", c.getTime(), null, null, null );
        eventList.add(event48);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 89);
        Events event49 = new Events("Test", "Heart", "Lunds sjukhus", c.getTime(), null, null, null );
        eventList.add(event49);
        c.setTime(eventList.get(0).startDate);


    }

}


























































