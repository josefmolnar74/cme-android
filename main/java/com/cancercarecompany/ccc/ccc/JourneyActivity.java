
package com.cancercarecompany.ccc.ccc;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class JourneyActivity extends AppCompatActivity {

    ArrayList<Events> eventList;
    ImageButton addAppointment;
    ImageButton addTreatment;
    ImageButton addTest;
    ImageButton addFoto;
    ImageButton addHospital;
    int lastButtonX;

    ImageView page1;
    ImageView page2;
    ImageView page3;

    ImageButton sun;
    Boolean sunSwitch = true;
    int pages = 0;
    int currentPage = 1;


    ImageButton careTeamButton;
    ImageButton journalButton;
    ImageButton logoButton;
    int location = 0;

    String eventPage1 = "";
    String eventPage2 = "";
    String eventPage3 = "";


    RelativeLayout relativeLayout;
    RelativeLayout swipeLayout;
    int topMargin = 0;
    ImageView car;
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
    RelativeLayout journeyScreen;
    RelativeLayout layoutButtons;
    RelativeLayout eventLayout;
    RelativeLayout containerLayout;
    RelativeLayout bottomLayout;
    RelativeLayout bushesLayer;
    RelativeLayout relativeLayout3;
    RelativeLayout mountains_layer;
    RelativeLayout tree_layer;
    int eventsSameDate = 0;
    String subCategoryClicked = "";
    Date date;
    LinearLayout wholeScreen;
    int width;
    int height;
    int containerHeight;
    int containerWidth;
    TextView eventInfoText;
    TextView eventHeadline;

    ConnectionHandler connectHandler;


    View vID;
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);
        eventList = new ArrayList<Events>();

        connectHandler = ConnectionHandler.getInstance();

        // Display patient name on topbar
        TextView patientNameText = (TextView) findViewById(R.id.txt_journey_patient_name);
        if (connectHandler.patient != null){
            patientNameText.setText(patientNameText.getText().toString().concat(" ".concat(connectHandler.patient.patient_name)));
        }

        TextView loggedIn = (TextView) findViewById(R.id.txt_journal_loggedIn);
        if (connectHandler.person != null){
            loggedIn.setText(connectHandler.person.first_name);
        }

/*        connectHandler.getEventsForPatient(connectHandler.patient.patient_ID);
        while (connectHandler.socketBusy){}

        for (int i=0; i < connectHandler.events.event_data.size();i++){
            eventList.add(connectHandler.events.event_data.get(i));
        }
        */

        Scroll_background = (HorizontalScrollView)findViewById(R.id.Scroll_background);
        Scroll_background2 = (HorizontalScrollView)findViewById(R.id.Scroll_background2);
        Scroll_background3 = (HorizontalScrollView) findViewById(R.id.Scroll_background3);

        tree_layer = (RelativeLayout) findViewById(R.id.relativeLayout_background3);
        mountains_layer = (RelativeLayout) findViewById(R.id.relativeLayout_background2);
        car = (ImageView) findViewById(R.id.img_car_journey);
        addAppointment = (ImageButton) findViewById(R.id.btn_appointment_journey);
        addTreatment = (ImageButton) findViewById(R.id.btn_treatment_journey);
        addTest = (ImageButton) findViewById(R.id.btn_test_journey);
        addFoto = (ImageButton) findViewById(R.id.btn_foto_journey);
        addHospital = (ImageButton) findViewById(R.id.btn_hospital_journey);
        eventScroll = (HorizontalScrollView) findViewById(R.id.Scroll_eventlayer);
        bottomScroll = (HorizontalScrollView) findViewById(R.id.Scroll_roadlayer);
        Scroll_bushes = (HorizontalScrollView) findViewById(R.id.Scroll_Bushes);
        layoutButtons = (RelativeLayout) findViewById(R.id.relativeLayout3);
        containerLayout = (RelativeLayout) findViewById(R.id.layerCoantainerJourney);
        eventLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);
        bottomLayout = (RelativeLayout) findViewById(R.id.relativeLayout_roadlayout);
        bushesLayer = (RelativeLayout) findViewById(R.id.bushesLayer);
        journeyScreen = (RelativeLayout) findViewById(R.id.journeyscreen);
        wholeScreen = (LinearLayout) findViewById(R.id.journeyLayout);
        careTeamButton = (ImageButton) findViewById(R.id.contactsButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);
        logoButton = (ImageButton) findViewById(R.id.logoButton);
        sun = (ImageButton) findViewById(R.id.btn_sun_journey);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);

        addAppointment.setOnTouchListener(new MyTouchListener());
        eventLayout.setOnDragListener(new MyDragListener());
        addTreatment.setOnTouchListener(new MyTouchListener());
        addTest.setOnTouchListener(new MyTouchListener());
        addFoto.setOnTouchListener(new MyTouchListener());
        addHospital.setOnTouchListener(new MyTouchListener());

        // Statusbar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse("01/06/2016");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        currentDate = Calendar.getInstance().getTime();
        Events diagnoseStart = new Events("Start", "Start", "Journey starts here", date, null, null, null);
        eventList.add(diagnoseStart);
        journeyStart = eventList.get(0).startDate;

        ((ViewGroup)car.getParent()).removeView(car);

        animateSun();
        ExampleJourney();
        refreshEvents();
       // generateClouds();

        final ViewTreeObserver observer = containerLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                containerHeight = containerLayout.getHeight();
                containerWidth = containerLayout.getWidth();
                System.out.println("container Height" + containerHeight);
                generateBushes();
                generateMountains();
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
                bottomScroll.setScrollX(scrollX * 2);
                Scroll_bushes.setScrollX(scrollX * 3);

            }
        });

        careTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                careTeam();
            }
        });

        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                journal();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        popup(v);

    }

    private void refreshEvents() {

        for (int i = 0; i < eventList.size(); i++) {

            ViewGroup layout = (ViewGroup) findViewById(R.id.relativeLayout_eventlayer);
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

        RelativeLayout.LayoutParams paramsCar = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        long carPosition = currentDate.getTime() - startDate;
        carPosition = carPosition / 1000000;
        int carIntPosition = (int) carPosition;
        carIntPosition = (carIntPosition * 2) +300;


        paramsCar.setMargins(0, height - 600, 0, 0);

        paramsCar.width = 600;
        paramsCar.height = 500;

        car.setLayoutParams(paramsCar);
        bottomLayout.addView(car, paramsCar);
        animateCar(carIntPosition);



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


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkDetail(id_);
                }
            });

            subCategoryClicked = eventList.get(i).subCategory.toString();

            switch (subCategoryClicked) {
                case "Medical_Oncologist":
                    btn.setBackgroundResource(R.drawable.doctormanbubble);
                    break;
                case "Hematologist_Doctor":
                    btn.setBackgroundResource(R.drawable.anestetistbubble);
                    break;
                case "Nurse":
                    btn.setBackgroundResource(R.drawable.nursebubble);
                    break;
                case "Bloodtest":
                    btn.setBackgroundResource(R.drawable.bloodtestbubble);
                    break;
                case "Dentist":
                    btn.setBackgroundResource(R.drawable.dentistbubble);
                    break;
                case "Surgeon":
                    btn.setBackgroundResource(R.drawable.surgeonbubble);
                    break;
                case "Anestetisten":
                    btn.setBackgroundResource(R.drawable.anestetistbubble);
                    break;
                case "Therapist":
                    btn.setBackgroundResource(R.drawable.therapybubble);
                    break;
                case "Physiotherapist":
                    btn.setBackgroundResource(R.drawable.therapybubble);
                    break;
                case "Dietician":
                    btn.setBackgroundResource(R.drawable.nursebubble);
                    break;
                case "MR":
                    btn.setBackgroundResource(R.drawable.mrbubble);
                    break;
                case "DT":
                    btn.setBackgroundResource(R.drawable.bluebubble);
                    break;
                case "Hearing_Tests":
                    btn.setBackgroundResource(R.drawable.hearingtestbubble);
                    break;
                case "Bone_Marrow_Samples":
                    btn.setBackgroundResource(R.drawable.bonemarrowbubble);
                    break;
                case "EEG":
                    btn.setBackgroundResource(R.drawable.eegbubble);
                    break;
                case "EKG":
                    btn.setBackgroundResource(R.drawable.ekgbubble);
                    break;
                case "Kidney_Investigation":
                    btn.setBackgroundResource(R.drawable.kidneyinvestigationbubble);
                    break;
                case "Ultrasound":
                    btn.setBackgroundResource(R.drawable.ultrasoundbubble);
                    break;
                case "Alternating Current":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Transplantation":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Cytistatika":
                    btn.setBackgroundResource(R.drawable.cytistatikabubble);
                    break;
                case "Surgery":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Stem_Cell_Transplantation":
                    btn.setBackgroundResource(R.drawable.stemcelltransplantationbubble);
                    break;
                case "Radiation":
                    btn.setBackgroundResource(R.drawable.radiationbubble);
                    break;
                case "Dialysis":
                    btn.setBackgroundResource(R.drawable.dialysisbubble);
                    break;
                case "Biological_Therapy":
                    btn.setBackgroundResource(R.drawable.biologicaltherapybubble);
                    break;
                case "Targeted_Therapy":
                    btn.setBackgroundResource(R.drawable.targetedtherapybubble);
                    break;
                case "Portacat":
                    btn.setBackgroundResource(R.drawable.pinkbubble);
                    break;
                case "Hospital":
                    btn.setBackgroundResource(R.drawable.hospital);
                    break;
                case "Picture_Memory":
                    btn.setBackgroundResource(R.drawable.photominne);
                    break;
                case "Start":
                    btn.setBackgroundResource(R.drawable.journeystart);
                    break;
            }


            relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);
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
            currentEventLong = currentEventLong / 1000000;
            int currentEventInt = (int) currentEventLong;


            params.setMargins((currentEventInt * 2) + 300, topMargin, 0, 0);
            params.width = height / 7;
            params.height = height / 7;
            if (subCategoryClicked == "Start") {
                params.setMargins((currentEventInt * 2), topMargin, 0, 0);
                params.width = (height / 7) * 2;
                params.height = (height / 7) * 2;
            }

            indexButton.setLayoutParams(params);
            lastButtonX = (currentEventInt * 2) + 300;



        }



    }


    public void popup(View v) {

        System.out.println(vID);
        System.out.println(v);
        int appointmentID = addAppointment.getId();
        int treatmentID = addTreatment.getId();
        int testID = addTest.getId();
        int fotoID = addFoto.getId();
        int hospitalID = addHospital.getId();

        int viewID = v.getId();
        String vCategory = "";


        if(viewID == appointmentID) {
               vCategory = "Appointments";
        }
        if(viewID == treatmentID) {
            vCategory = "Treatments";
        }
        if(viewID == testID) {
            vCategory = "Tests";
        }
        if(viewID == fotoID) {
            vCategory = "Picture_Memory";
        }
        if(viewID == hospitalID) {
            vCategory = "Hospital";
        }
            System.out.println(vCategory);

        final String Category = vCategory;
        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.addeventpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, (int) (width * 0.90), (int) (height * 0.85));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);


        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupView.bringToFront();
        popupWindow.setFocusable(true);
        popupWindow.update();

        ImageButton saveButton = (ImageButton) popupView.findViewById(R.id.btn_saveEvent_Journey);
        final ImageView subCategory1 = (ImageView) popupView.findViewById(R.id.img_subcategory1);
        final ImageView subCategory2 = (ImageView) popupView.findViewById(R.id.img_subcategory2);
        final ImageView subCategory3 = (ImageView) popupView.findViewById(R.id.img_subcategory3);
        final ImageView subCategory4 = (ImageView) popupView.findViewById(R.id.img_subcategory4);
        final ImageView subCategory5 = (ImageView) popupView.findViewById(R.id.img_subcategory5);
        final ImageView subCategory6 = (ImageView) popupView.findViewById(R.id.img_subcategory6);
        final ImageView subCategory7 = (ImageView) popupView.findViewById(R.id.img_subcategory7);
        final ImageView subCategory8 = (ImageView) popupView.findViewById(R.id.img_subcategory8);
        final ImageView subCategory9 = (ImageView) popupView.findViewById(R.id.img_subcategory9);
        ImageButton cancel = (ImageButton) popupView.findViewById(R.id.btn_cancel_addevent);
        final DatePicker datePicker = (DatePicker) popupView.findViewById(R.id.datepicker_journey);
        final TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.timepicker_journey);
        final EditText eventNotes = (EditText) popupView.findViewById(R.id.et_notes_add_journey);
        timePicker.setIs24HourView(true);
        page1 = (ImageView) popupView.findViewById(R.id.img_page1);
        page2 = (ImageView) popupView.findViewById(R.id.img_page2);
        page3 = (ImageView) popupView.findViewById(R.id.img_page3);
        eventInfoText = (TextView) popupView.findViewById(R.id.txt_subcategory_main);
        eventHeadline = (TextView) popupView.findViewById(R.id.txt_subcategory_journey);
        swipeLayout = (RelativeLayout) popupView.findViewById(R.id.swipeEventLayout);

        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(JourneyActivity.this) {


            public void onSwipeRight() {

                if(currentPage > 1) {

                    currentPage--;

                }

                System.out.println(currentPage);



                eventInfoText.setText(getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt"+currentPage, "string", getPackageName())));

                switch (currentPage) {



                    case 1:

                        page1.setBackgroundResource(R.drawable.bluecircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 2:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.bluecircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 3:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.bluecircle);

                        break;



                }



            }

            public void onSwipeLeft() {

                if(currentPage < pages) {

                    currentPage++;

                }

                System.out.println(currentPage);

                eventInfoText.setText(getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt"+currentPage, "string", getPackageName())));



                switch (currentPage) {



                    case 1:

                        page1.setBackgroundResource(R.drawable.bluecircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 2:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.bluecircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 3:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.bluecircle);

                        break;



                }

            }



        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        System.out.println("popup location = " + location + "     popup id = " + vID.getId());

        if(location>0) {
            Calendar c = Calendar.getInstance();
            c.setTime(eventList.get(0).startDate);
            System.out.println(location);

            location = (location / 172);
            c.add(Calendar.DATE, location - 2);


            datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }else{
            Calendar c = Calendar.getInstance();
            c.setTime(currentDate);
            c.add(Calendar.DATE, 1);
            datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }


        switch (Category) {
            case "Appointments":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);
                subCategory7.setVisibility(View.INVISIBLE);
                subCategory8.setVisibility(View.INVISIBLE);
                subCategory9.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);
                subCategory9.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.doctorman);
                subCategory2.setBackgroundResource(R.drawable.anestetien);
                subCategory3.setBackgroundResource(R.drawable.nurse);
                subCategory4.setBackgroundResource(R.drawable.dentist);
                subCategory5.setBackgroundResource(R.drawable.surgeon);
                subCategory6.setBackgroundResource(R.drawable.anestetien);
                subCategory7.setBackgroundResource(R.drawable.therapy);
                subCategory8.setBackgroundResource(R.drawable.doctorman);
                subCategory9.setBackgroundResource(R.drawable.anestetien);

                subCategoryClicked = "Medical_Oncologist";
                eventInfoText(subCategoryClicked);


                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        subCategoryClicked = "Medical_Oncologist";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Hematologist_Doctor";

                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Nurse";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Dentist";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Surgeon";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Anestetisten";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Therapist";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Physiotherapist";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Dietician";
                        eventInfoText(subCategoryClicked);
                    }
                });

                break;
            case "Treatments":
                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);
                subCategory7.setVisibility(View.INVISIBLE);
                subCategory8.setVisibility(View.INVISIBLE);
                subCategory9.setVisibility(View.INVISIBLE);


                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.cytatiska);
                subCategory2.setBackgroundResource(R.drawable.ekg);
                subCategory3.setBackgroundResource(R.drawable.targettherapy);
                subCategory4.setBackgroundResource(R.drawable.treatmentradio);
                subCategory5.setBackgroundResource(R.drawable.treatmentdialys);
                subCategory6.setBackgroundResource(R.drawable.treatmentbiologic);
                subCategory7.setBackgroundResource(R.drawable.treatmenttarget);
                subCategory8.setBackgroundResource(R.drawable.portakat);

                subCategoryClicked = "Cytistatika";
                eventInfoText(subCategoryClicked);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Cytistatika";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Surgery";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Stem_Cell_Transplantation";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Radiation";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Dialysis";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Biological_Therapy";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Targeted_Therapy";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Portacat";
                        eventInfoText(subCategoryClicked);

                    }
                });

                break;

            case "Tests":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);
                subCategory7.setVisibility(View.INVISIBLE);
                subCategory8.setVisibility(View.INVISIBLE);
                subCategory9.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);
                subCategory9.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.blooddrop);
                subCategory2.setBackgroundResource(R.drawable.mr);
                subCategory3.setBackgroundResource(R.drawable.dt);
                subCategory4.setBackgroundResource(R.drawable.hearingtest);
                subCategory5.setBackgroundResource(R.drawable.bonemarrow);
                subCategory6.setBackgroundResource(R.drawable.eegtest);
                subCategory7.setBackgroundResource(R.drawable.ekg);
                subCategory8.setBackgroundResource(R.drawable.kidney);
                subCategory9.setBackgroundResource(R.drawable.ultrajudback);

                subCategoryClicked = "Bloodtest";


                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Bloodtest";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "MR";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "DT";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Hearing_Tests";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Bone_Marrow_Samples";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "EEG";
                        eventInfoText(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "EKG";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Kidney_Investigation";
                        eventInfoText(subCategoryClicked);

                    }
                });
                subCategory9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Ultrasound";
                        eventInfoText(subCategoryClicked);

                    }
                });

                break;
            case "Picture_Memory":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);
                subCategory7.setVisibility(View.INVISIBLE);
                subCategory8.setVisibility(View.INVISIBLE);
                subCategory9.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.photominne);

                subCategoryClicked = "Picture_Memory";
                eventInfoText(subCategoryClicked);


                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Picture_Memory";
                        eventInfoText(subCategoryClicked);

                    }
                });

                break;

            case "Hospital":

                subCategory1.setVisibility(View.INVISIBLE);
                subCategory2.setVisibility(View.INVISIBLE);
                subCategory3.setVisibility(View.INVISIBLE);
                subCategory4.setVisibility(View.INVISIBLE);
                subCategory5.setVisibility(View.INVISIBLE);
                subCategory6.setVisibility(View.INVISIBLE);
                subCategory7.setVisibility(View.INVISIBLE);
                subCategory8.setVisibility(View.INVISIBLE);
                subCategory9.setVisibility(View.INVISIBLE);

                subCategory1.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.hospital);

                subCategoryClicked = "Hospital";

                eventInfoText(subCategoryClicked);


                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "Hospital";
                        eventInfoText(subCategoryClicked);

                    }
                });

                break;
        }


        saveButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                cal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                cal.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                eventsSameDate = 0;

                Date date = cal.getTime();

                for (int i = 0; i < eventList.size(); i++) {

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String thisEvent = df.format(date);
                    String indexEvent = df.format(eventList.get(i).startDate);

                    if (thisEvent.equals(indexEvent)) {
                        eventsSameDate = eventsSameDate + 1;
                        System.out.println(eventsSameDate);

                    }
                }
                if (eventsSameDate < 3) {
                    if (date.getTime() > startDate) {

                        Events event = new Events(Category, subCategoryClicked, eventNotes.getText().toString(), date, null, null, null);
                        eventList.add(event);
                        createEventButton();
                        popupWindow.dismiss();
                        SimpleDateFormat toastDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                        String toastStringDate = toastDate.format(date);
                        String toastMessage = "Event created at: " + toastStringDate;
                        toastFunction(toastMessage);

                    } else {
                        System.out.println("can't set date before start");
                    }
                } else {
                    String message = "you can maximum have three events per day!";
                    toastFunction(message);
                }
            }
        });
    }

    private void eventInfoText(String subCategoryClicked){
        currentPage = 1;
        pages = 0;
        eventPage1 = "";
        eventPage2 = "";
        eventPage3 = "";

        eventInfoText.setText("Det finns ingen information om detta eventet ännu!");

        eventHeadline.setText(getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked, "string", getPackageName())));
        int resource1 = getResources().getIdentifier("Event_"+subCategoryClicked+"_txt1", "string", getPackageName());
        int resource2 = getResources().getIdentifier("Event_"+subCategoryClicked+"_txt2", "string", getPackageName());
        int resource3 = getResources().getIdentifier("Event_"+subCategoryClicked+"_txt3", "string", getPackageName());


        if(resource1 != 0){
            eventInfoText.setText(getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt1", "string", getPackageName())));
            eventPage1 = getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt1", "string", getPackageName()));
            pages ++;
        }

        if(resource2 != 0){
            eventPage2 = getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt2", "string", getPackageName())) ;
            pages ++;
        }

        if(resource3 != 0){
            eventPage3 = getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt3", "string", getPackageName())) ;
            pages ++;
        }

        switch (pages) {

            case 0:
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.VISIBLE);
                break;

        }


    }


    private void createEventButton() {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        ImageButton btn = new ImageButton(this);
        btn.setId(eventList.size() - 1);
        final int id_ = btn.getId();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetail(id_);
            }
        });

        String subCategory = eventList.get(id_).subCategory.toString();

        switch (subCategory) {
            case "Medical_Oncologist":
                btn.setBackgroundResource(R.drawable.doctormanbubble);
                break;
            case "Hematologist_Doctor":
                btn.setBackgroundResource(R.drawable.doctormanbubble);
                break;
            case "Nurse":
                btn.setBackgroundResource(R.drawable.nursebubble);
                break;
            case "Dentist":
                btn.setBackgroundResource(R.drawable.dentistbubble);
                break;
            case "Surgeon":
                btn.setBackgroundResource(R.drawable.surgeonbubble);
                break;
            case "Anestetisten":
                btn.setBackgroundResource(R.drawable.anestetistbubble);
                break;
            case "Therapist":
                btn.setBackgroundResource(R.drawable.therapybubble);
                break;
            case "Physiotherapist":
                btn.setBackgroundResource(R.drawable.orangebubble);
                break;
            case "Dietician":
                btn.setBackgroundResource(R.drawable.anestetistbubble);
                break;
            case "MR":
                btn.setBackgroundResource(R.drawable.mrbubble);
                break;
            case "DT":
                btn.setBackgroundResource(R.drawable.kidneyinvestigationbubble);
                break;
            case "Hearing_Tests":
                btn.setBackgroundResource(R.drawable.hearingtestbubble);
                break;
            case "Bone_Marrow_Samples":
                btn.setBackgroundResource(R.drawable.bonemarrowbubble);
                break;
            case "EEG":
                btn.setBackgroundResource(R.drawable.eegbubble);
                break;
            case "EKG":
                btn.setBackgroundResource(R.drawable.ekgbubble);
                break;
            case "Kidney_Investigation":
                btn.setBackgroundResource(R.drawable.kidneyinvestigationbubble);
                break;
            case "Ultrasound":
                btn.setBackgroundResource(R.drawable.ultrasoundbubble);
                break;
            case "Alternating_Current":
                btn.setBackgroundResource(R.drawable.otherdropbubble);
                break;
            case "Transplantation":
                btn.setBackgroundResource(R.drawable.pinkbubble);
                break;
            case "Cytistatika":
                btn.setBackgroundResource(R.drawable.cytistatikabubble);
                break;
            case "Surgery":
                btn.setBackgroundResource(R.drawable.pinkbubble);
                break;
            case "Stem_Cell_Transplantation":
                btn.setBackgroundResource(R.drawable.stemcelltransplantationbubble);
                break;
            case "Radiation":
                btn.setBackgroundResource(R.drawable.radiationbubble);
                break;
            case "Dialysis":
                btn.setBackgroundResource(R.drawable.dialysisbubble);
                break;
            case "Biological_Therapy":
                btn.setBackgroundResource(R.drawable.treatmentbiologic);
                break;
            case "Targeted_Therapy":
                btn.setBackgroundResource(R.drawable.targetedtherapybubble);
                break;
            case "Portacat":
                btn.setBackgroundResource(R.drawable.pinkbubble);
                break;
            case "Picture_Memory":
                btn.setBackgroundResource(R.drawable.photominne);
                break;
            case "Hospital":
                btn.setBackgroundResource(R.drawable.hospital);
                break;
            case "Start":
                btn.setBackgroundResource(R.drawable.journeystart);
                break;
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);

        relativeLayout.addView(btn, params);

        ImageButton indexButton = ((ImageButton) findViewById(eventList.size() - 1));

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
        currentEventLong = currentEventLong / 1000000;
        int currentEventInt = (int) currentEventLong;

        for (int i = 0; i < eventList.size(); i++) {
            if (i != id_) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String thisEvent = df.format(eventList.get(id_).startDate);
                String indexEvent = df.format(eventList.get(i).startDate);

                if (thisEvent.equals(indexEvent)) {
                    eventsSameDate = eventsSameDate + 1;
                    ImageButton collidateButton = ((ImageButton) findViewById(i));
                    topMargin = (int) collidateButton.getY() + (height / 7);
                    System.out.println("collidatebutton" + collidateButton.getY());
                    System.out.println("esstimatedtopmargoin" + topMargin);
                }

            }

        }

        params.setMargins((currentEventInt * 2) + 300, topMargin, 0, 0);
        params.width = (height / 7);
        params.height = (height / 7);
        if (subCategory == "Start") {
            params.setMargins((currentEventInt * 2), topMargin, 0, 0);
            params.width = (height / 7) * 2;
            params.height = (height / 7) * 2;
        }
        System.out.println("topmargin" + topMargin);
        indexButton.setLayoutParams(params);
        topMargin = 100;
    }


    private void checkDetail(final int id_) {
        System.out.println(id_);

        LayoutInflater layoutInflater
                = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.detailpopup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView, (int) (width * 0.90), (int) (height * 0.85));
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);

        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupView.bringToFront();
        popupWindow.setFocusable(true);
        popupWindow.update();

        eventInfoText = (TextView) popupView.findViewById(R.id.txt_subcategory_main);
        eventHeadline = (TextView) popupView.findViewById(R.id.txt_subcategory_journey);
        page1 = (ImageView) popupView.findViewById(R.id.img_page1);
        page2 = (ImageView) popupView.findViewById(R.id.img_page2);
        page3 = (ImageView) popupView.findViewById(R.id.img_page3);
        final TextView notesDetail = (TextView) popupView.findViewById(R.id.txt_notes_detail_journey);
        final TextView timeDetail = (TextView) popupView.findViewById(R.id.txt_time_detail_journey);
        ImageView categoryImage = (ImageView) popupView.findViewById(R.id.img_subcategory1);
        final EditText editNotes = (EditText) popupView.findViewById(R.id.et_notes_detail_journey);
        final DatePicker editDate = (DatePicker) popupView.findViewById(R.id.datepicker_journey);
        final TimePicker editTime = (TimePicker) popupView.findViewById(R.id.timepicker_journey);
        final ImageButton deleteEvent = (ImageButton) popupView.findViewById(R.id.btn_deleteEvent_journey);
        ImageButton cancelButtonDetail = (ImageButton) popupView.findViewById(R.id.btn_cancel_addevent);
        final ImageButton saveButtonDetail = (ImageButton) popupView.findViewById(R.id.btn_saveEvent_Journey);
        final ImageButton editButtonDetail = (ImageButton) popupView.findViewById(R.id.btn_edit_detail_journey);
        final ImageButton cancelEditMode = (ImageButton)popupView.findViewById(R.id.btn_cancelEditModer_journey);
        editTime.setIs24HourView(true);
        swipeLayout = (RelativeLayout) popupView.findViewById(R.id.swipeEventLayout);

        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(JourneyActivity.this) {


                public void onSwipeRight() {

                if(currentPage > 1) {

                    currentPage--;

                }

                System.out.println(currentPage);



                eventInfoText.setText(getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt"+currentPage, "string", getPackageName())));

                switch (currentPage) {



                    case 1:

                        page1.setBackgroundResource(R.drawable.bluecircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 2:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.bluecircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 3:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.bluecircle);

                        break;



                }



            }

            public void onSwipeLeft() {

                if(currentPage < pages) {

                    currentPage++;

                }

                System.out.println(currentPage);

                eventInfoText.setText(getResources().getString(getResources().getIdentifier("Event_"+subCategoryClicked+"_txt"+currentPage, "string", getPackageName())));





                switch (currentPage) {



                    case 1:

                        page1.setBackgroundResource(R.drawable.bluecircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 2:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.bluecircle);

                        page3.setBackgroundResource(R.drawable.blackcircle);

                        break;

                    case 3:

                        page1.setBackgroundResource(R.drawable.blackcircle);

                        page2.setBackgroundResource(R.drawable.blackcircle);

                        page3.setBackgroundResource(R.drawable.bluecircle);

                        break;



                }

            }



        });



        subCategoryClicked = eventList.get(id_).subCategory.toString();
        eventHeadline.setText(eventList.get(id_).subCategory.toString());
        eventInfoText(subCategoryClicked);

        notesDetail.setText(eventList.get(id_).notes.toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd-HH:mm");


        String dateString = simpleDateFormat.format(eventList.get(id_).startDate.getTime());
        timeDetail.setText(dateString);

        cancelButtonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        editButtonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editButtonDetail.setVisibility(View.INVISIBLE);
                timeDetail.setVisibility(View.INVISIBLE);
                notesDetail.setVisibility(View.INVISIBLE);


                saveButtonDetail.setVisibility(View.VISIBLE);
                cancelEditMode.setVisibility(View.VISIBLE);
                editNotes.setVisibility(View.VISIBLE);
                editDate.setVisibility(View.VISIBLE);
                editTime.setVisibility(View.VISIBLE);

                    Calendar c = Calendar.getInstance();
                    c.setTime(eventList.get(id_).startDate);

                    editDate.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

            }
        });

        cancelEditMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editButtonDetail.setVisibility(View.VISIBLE);
                timeDetail.setVisibility(View.VISIBLE);
                notesDetail.setVisibility(View.VISIBLE);


                saveButtonDetail.setVisibility(View.INVISIBLE);
                editNotes.setVisibility(View.INVISIBLE);
                editDate.setVisibility(View.INVISIBLE);
                editTime.setVisibility(View.INVISIBLE);
                cancelEditMode.setVisibility(View.INVISIBLE);

            }
        });


        saveButtonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String detailCategory = eventList.get(id_).category.toString();
                ViewGroup layout = (ViewGroup) findViewById(R.id.relativeLayout_eventlayer);
                View imageButton = layout.findViewById(id_);
                layout.removeView(imageButton);
                eventList.remove(id_);
                reArrangeBtnId(id_);


                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, editDate.getYear());
                cal.set(Calendar.MONTH, editDate.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, editDate.getDayOfMonth());
                cal.set(Calendar.HOUR_OF_DAY, editTime.getCurrentHour());
                cal.set(Calendar.MINUTE, editTime.getCurrentMinute());
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                eventsSameDate = 0;

                Date date = cal.getTime();

                for (int i = 0; i < eventList.size(); i++) {

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String thisEvent = df.format(date);
                    String indexEvent = df.format(eventList.get(i).startDate);

                    if (thisEvent.equals(indexEvent)) {
                        eventsSameDate = eventsSameDate + 1;
                        System.out.println(eventsSameDate);

                    }
                }
                if (eventsSameDate < 3) {
                    if (date.getTime() > startDate) {

                        Events event = new Events(detailCategory, subCategoryClicked, editNotes.getText().toString(), date, null, null, null);
                        eventList.add(event);
                        createEventButton();
                        popupWindow.dismiss();
                        SimpleDateFormat toastDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
                        String toastStringDate = toastDate.format(date);
                        String toastMessage = "Event edited!"+toastStringDate;
                        toastFunction(toastMessage);

                    } else {
                        System.out.println("can't set date before start");
                    }
                } else {
                    String message = "you can maximum have three events per day!";
                    toastFunction(message);
                }

            }
        });



        switch (eventList.get(id_).subCategory) {

            case "Medical_Oncologist":
                categoryImage.setBackgroundResource(R.drawable.doctorman);
                break;
            case "Hematologist_Doctor":
                categoryImage.setBackgroundResource(R.drawable.doctorman);
                break;
            case "Nurse":
                categoryImage.setBackgroundResource(R.drawable.nurse);
                break;
            case "Dentist":
                categoryImage.setBackgroundResource(R.drawable.dentist);
                break;
            case "Surgeon":
                categoryImage.setBackgroundResource(R.drawable.surgeon);
                break;
            case "Anestetisten":
                categoryImage.setBackgroundResource(R.drawable.anestetien);
                break;
            case "Therapist":
                categoryImage.setBackgroundResource(R.drawable.therapy);
                break;
            case "Physiotherapist":
                categoryImage.setBackgroundResource(R.drawable.therapy);
                break;
            case "Dietician":
                categoryImage.setBackgroundResource(R.drawable.nurse);
                break;
            case "MR":
                categoryImage.setBackgroundResource(R.drawable.mr);
                break;
            case "DT":
                categoryImage.setBackgroundResource(R.drawable.dt);
                break;
            case "Hearing_Tests":
                categoryImage.setBackgroundResource(R.drawable.hearingtest);
                break;
            case "Bone_Marrow_Samples":
                categoryImage.setBackgroundResource(R.drawable.bonemarrow);
                break;
            case "EEG":
                categoryImage.setBackgroundResource(R.drawable.eegtest);
                break;
            case "EKG":
                categoryImage.setBackgroundResource(R.drawable.ekg);
                break;
            case "Kidney_Investigation":
                categoryImage.setBackgroundResource(R.drawable.kidney);
                break;
            case "Ultrasound":
                categoryImage.setBackgroundResource(R.drawable.ultrajudback);
                break;
            case "Cytistatika":
                categoryImage.setBackgroundResource(R.drawable.cytistatika);
                break;
            case "Surgery":
                categoryImage.setBackgroundResource(R.drawable.surgery);
                break;
            case "Stem_Cell_Transplantation":
                categoryImage.setBackgroundResource(R.drawable.stemcelltransplantation);
                break;
            case "Radiation":
                categoryImage.setBackgroundResource(R.drawable.radiation);
                break;
            case "Dialysis":
                categoryImage.setBackgroundResource(R.drawable.dialysis);
                break;
            case "Biological_Therapy":
                categoryImage.setBackgroundResource(R.drawable.biologicaltherapy);
                break;
            case "Targeted_Therapy":
                categoryImage.setBackgroundResource(R.drawable.targetedtherapy);
                break;
            case "Portacat":
                categoryImage.setBackgroundResource(R.drawable.portakat);
                break;
            case "Bloodtest":
                categoryImage.setBackgroundResource(R.drawable.bloodtest);
                break;
            case "Hospital":
                categoryImage.setBackgroundResource(R.drawable.hospital);
                break;
            case "Picture_memory":
                categoryImage.setBackgroundResource(R.drawable.photominne);
                break;
            case "Start":
                categoryImage.setBackgroundResource(R.drawable.journeystart);
                break;
        }


        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(id_);
                ViewGroup layout = (ViewGroup) findViewById(R.id.relativeLayout_eventlayer);
                View imageButton = layout.findViewById(id_);
                layout.removeView(imageButton);
                eventList.remove(id_);
                reArrangeBtnId(id_);
                popupWindow.dismiss();
                String toastMessage = "Event deleted!";
                toastFunction(toastMessage);
            }


        });


        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_eventlayer);


        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        popupView.bringToFront();

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

    private void generateClouds() {

        Collections.sort(eventList, new Comparator<Events>() {
            @Override
            public int compare(Events lhs, Events rhs) {

                return lhs.startDate.compareTo(rhs.startDate);

            }
        });
        int lastEvent = eventList.size() - 1;
        long lastEventLong = eventList.get(lastEvent).startDate.getTime();
        long screenSize = lastEventLong - startDate;
        long cloudCount = screenSize / 1000000;
        cloudCount = cloudCount / 5;
        cloudCount = cloudCount / 400;


        for (int i = 0; i <= cloudCount; i++) {

            ImageButton btn = new ImageButton(this);
            btn.setId(i + 1000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(i * 1000, 0, 0, 0);
            params.width = width / 4;
            params.height = (height / 6);
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

           // cloudLayout.addView(btn, params);
            System.out.println(btn.getId());
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



    private void generateBushes() {


        int bushesCount = lastButtonX / 400;
        bushesCount = bushesCount * 3;
        for (int i = 0; i <= bushesCount; i++) {

            ImageView img = new ImageView(this);
            img.setId(i + 2000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);


            Random r = new Random();
            int bushesRandom = r.nextInt(3);

            switch (bushesRandom) {
                case 0:
                    DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY1 = Math.round(containerHeight - 81 / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.bushes1);
                    params.setMargins(i * 400, bushesY1, 0, 0);
                  //  params.width = cont;
                  //  params.height = bushesHeight;
                    break;
                case 1:
                    DisplayMetrics displayMetrics2 = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY2 = Math.round(containerHeight - 71 / (displayMetrics2.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.bushes2);
                    params.setMargins(i * 400, bushesY2, 0, 0);
                  //  params.width = bushesWidth;
                  //  params.height = bushesHeight;
                    break;
                case 2:
                    DisplayMetrics displayMetrics3 = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY3 = Math.round(containerHeight - 140/ (displayMetrics3.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.bushes3);
                    params.setMargins(i * 400, bushesY3, 0, 0);
                   // params.width = bushesWidth;/   params.height = bushesHeight*2;
                    break;


            }
            bushesLayer.addView(img, params);

        }
    }


    private void generateMountains() {


        int mountainsCount = lastButtonX / 300;
        mountainsCount = mountainsCount * 3;
        for (int i = 0; i <= mountainsCount; i++) {

            ImageView img = new ImageView(this);
            img.setId(i + 3000);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);


            Random r = new Random();
            int bushesRandom = r.nextInt(4);

            switch (bushesRandom) {
                case 0:
                    DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY1 = Math.round(containerHeight - containerHeight/4 / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.mid1);
                    params.setMargins(i * 500, 300, 0, 0);
                      params.width = containerWidth / 2 ;
                      params.height = containerHeight / 2 ;
                    break;
                case 1:
                    DisplayMetrics displayMetrics2 = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY2 = Math.round(containerHeight - containerHeight/4 / (displayMetrics2.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.mid2);
                    params.setMargins(i * 500, 300, 0, 0);
                    params.width = containerWidth / 2 ;
                    params.height = containerHeight / 2 ;
                    break;
                case 2:
                    DisplayMetrics displayMetrics3 = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY3 = Math.round(containerHeight - containerHeight/4/ (displayMetrics3.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.mid3);
                    params.setMargins(i * 500, 300, 0, 0);
                    params.width = containerWidth / 2 ;
                    params.height = containerHeight / 2 ;
                    break;
                case 3:
                    DisplayMetrics displayMetrics4 = getApplicationContext().getResources().getDisplayMetrics();
                    int bushesY4 = Math.round(containerHeight - containerHeight/4/ (displayMetrics4.xdpi / DisplayMetrics.DENSITY_DEFAULT));
                    img.setBackgroundResource(R.drawable.mid4);
                    params.setMargins(i * 500, 300 + 60, 0, 0);
                    params.width = containerWidth / 2 ;
                    params.height = containerHeight / 2 ;
                    break;


            }
            mountains_layer.addView(img, params);

        }
    }


    private void animateCar(final int carIntPosition) {


        final RelativeLayout.LayoutParams paramsCar2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsCar2.setMargins((carIntPosition * 2)-1000, height -600, 0, 0);
        System.out.println("CARPOSITION"+carIntPosition * 2);
        paramsCar2.width = 600;
        paramsCar2.height = 500;

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
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


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


    private void ExampleJourney() {
        Calendar c = Calendar.getInstance();
        c.setTime(eventList.get(0).startDate);

        c.add(Calendar.DATE, 1);
        Events event1 = new Events("Appointments", "Medical_Oncologist", "at hospital", c.getTime(), null, null, null);
        eventList.add(event1);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 2);
        Events event2 = new Events("Treatments", "Cytistatika", "take meds before!", c.getTime(), null, null, null);
        eventList.add(event2);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 5);
        Events event3 = new Events("Appointments", "Nurse", "help", c.getTime(), null, null, null);
        eventList.add(event3);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 6);
        Events event4 = new Events("Tests", "Bone_Marrow_Samples", "bloodtest1", c.getTime(), null, null, null);
        eventList.add(event4);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 7);
        Events event5 = new Events("Image_Memory", "Picture_Memory", "photo of me", c.getTime(), null, null, null);
        eventList.add(event5);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 8);
        Events event6 = new Events("Hospital", "Hospital", "xray at dentist", c.getTime(), null, null, null);
        eventList.add(event6);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 9);
        Events event7 = new Events("Appointments", "Medical_Oncologist", "at hospital", c.getTime(), null, null, null);
        eventList.add(event7);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 10);
        Events event8 = new Events("Treatments", "Surgery", "whole day", c.getTime(), null, null, null);
        eventList.add(event8);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 12);
        Events event9 = new Events("Treatments", "Surgery", "ohh noo =(", c.getTime(), null, null, null);
        eventList.add(event9);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 13);
        Events event10 = new Events("Treatments", "Cytistatika", "evening", c.getTime(), null, null, null);
        eventList.add(event10);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 14);
        Events event11 = new Events("Appointments", "Medical_Oncologist", "zzzzz", c.getTime(), null, null, null);
        eventList.add(event11);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 15);
        Events event12 = new Events("Tests", "MR", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event12);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 17);
        Events event13 = new Events("Tests", "DT", "take images", c.getTime(), null, null, null);
        eventList.add(event13);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 18);
        Events event14 = new Events("Tests", "Bone_Marrow_Samples", "wooow", c.getTime(), null, null, null);
        eventList.add(event14);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 19);
        Events event15 = new Events("Appointments", "Medical_Oncologist", "Dr Edith", c.getTime(), null, null, null);
        eventList.add(event15);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 22);
        Events event16 = new Events("Appointments", "Medical_Oncologist", "eating habits", c.getTime(), null, null, null);
        eventList.add(event16);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 24);
        Events event17 = new Events("Appointments", "Nurse", "at Malmö", c.getTime(), null, null, null);
        eventList.add(event17);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 26);
        Events event18 = new Events("Appointments", "Therapist", "whole day", c.getTime(), null, null, null);
        eventList.add(event18);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 27);
        Events event19 = new Events("Appointments", "Therapist", "eat before", c.getTime(), null, null, null);
        eventList.add(event19);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 28);
        Events event20 = new Events("Tests", "Bloodtest", "dropp näring", c.getTime(), null, null, null);
        eventList.add(event20);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 29);
        Events event21 = new Events("Hospital", "Hospital", "visit lund hospital", c.getTime(), null, null, null);
        eventList.add(event21);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 30);
        Events event22 = new Events("Appointments", "Nurse", "tråkigt", c.getTime(), null, null, null);
        eventList.add(event22);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 31);
        Events event23 = new Events("Appointments", "Dietician", "eating habits", c.getTime(), null, null, null);
        eventList.add(event23);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 32);
        Events event24 = new Events("Tests", "Bone_Marrow_Samples", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event24);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 34);
        Events event25 = new Events("Appointments", "Dentist", "xray at dentist", c.getTime(), null, null, null);
        eventList.add(event25);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 36);
        Events event26 = new Events("Tests", "Bloodtest", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event26);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 40);
        Events event27 = new Events("Appointments", "Dietician", "at hospital", c.getTime(), null, null, null);
        eventList.add(event27);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 41);
        Events event28 = new Events("Appointments", "Nurse", "zzzzz", c.getTime(), null, null, null);
        eventList.add(event28);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 45);
        Events event29 = new Events("Appointments", "Therapist", "help", c.getTime(), null, null, null);
        eventList.add(event29);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 46);
        Events event30 = new Events("Treatments", "Surgery", "whole day", c.getTime(), null, null, null);
        eventList.add(event30);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 48);
        Events event31 = new Events("Hospital", "Hospital", "at hospital", c.getTime(), null, null, null);
        eventList.add(event31);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 52);
        Events event32 = new Events("Image_Memory", "Picture_Memory", "photo of me", c.getTime(), null, null, null);
        eventList.add(event32);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 55);
        Events event33 = new Events("Appointments", "Nurse", "whole day", c.getTime(), null, null, null);
        eventList.add(event33);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 57);
        Events event34 = new Events("Appointments", "Therapist", "at hospital", c.getTime(), null, null, null);
        eventList.add(event34);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 61);
        Events event35 = new Events("Appointments", "Nurse", "tråkigt", c.getTime(), null, null, null);
        eventList.add(event35);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 62);
        Events event36 = new Events("Appointments", "Anestetisten", "eating habits", c.getTime(), null, null, null);
        eventList.add(event36);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 64);
        Events event37 = new Events("Appointments", "Anestetisten", "at hospital", c.getTime(), null, null, null);
        eventList.add(event37);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 66);
        Events event38 = new Events("Appointments", "Therapist", "eating habits", c.getTime(), null, null, null);
        eventList.add(event38);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 70);
        Events event39 = new Events("Tests", "Bloodtest", "bloodtest1", c.getTime(), null, null, null);
        eventList.add(event39);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 71);
        Events event40 = new Events("Appointments", "Nurse", "whole day", c.getTime(), null, null, null);
        eventList.add(event40);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 74);
        Events event41 = new Events("Tests", "MR", "bloodtest1", c.getTime(), null, null, null);
        eventList.add(event41);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 76);
        Events event42 = new Events("Tests", "DT", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event42);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 78);
        Events event43 = new Events("Appointments", "Anestetisten", "at hospital", c.getTime(), null, null, null);
        eventList.add(event43);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 80);
        Events event44 = new Events("Hospital", "Hospital", "wooow", c.getTime(), null, null, null);
        eventList.add(event44);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 82);
        Events event45 = new Events("Tests", "Bone_Marrow_Samples", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event45);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 84);
        Events event46 = new Events("Appointments", "Anestetisten", "at hospital", c.getTime(), null, null, null);
        eventList.add(event46);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 86);
        Events event47 = new Events("Tests", "MR", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event47);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 88);
        Events event48 = new Events("Tests", "DT", "wooow", c.getTime(), null, null, null);
        eventList.add(event48);
        c.setTime(eventList.get(0).startDate);
        c.add(Calendar.DATE, 89);
        Events event49 = new Events("Tests", "MR", "Lunds sjukhus", c.getTime(), null, null, null);
        eventList.add(event49);
        c.setTime(eventList.get(0).startDate);

    }
}

