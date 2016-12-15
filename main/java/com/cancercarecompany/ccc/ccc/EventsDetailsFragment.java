package com.cancercarecompany.ccc.ccc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventsDetailsFragment extends Fragment {

    private Event listItem;
    private ConnectionHandler connectHandler;
    private int position;
    private boolean admin = false;
    private GridLayout selectionLayout;
    private String category;
    private ImageView eventImage;
    private TextView eventText;
    private ImageButton informationButton;
    private ImageView subCategory1;
    private ImageView subCategory2;
    private ImageView subCategory3;
    private ImageView subCategory4;
    private ImageView subCategory5;
    private ImageView subCategory6;
    private ImageView subCategory7;
    private ImageView subCategory8;
    private ImageView subCategory9;
    private String subCategoryClicked;
    private EditText notes;
    private Button mDateButton;
    private Button mTimeButton;
    public Calendar mCalendar;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    DialogFragment dateFragment;
    DialogFragment timeFragment;
    private static final String EVENT_CATEGORY_APPOINTMENT = "appointments";
    private static final String EVENT_CATEGORY_TREATMENT = "treatments";
    private static final String EVENT_CATEGORY_TEST = "tests";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view = inflater.inflate(R.layout.fragment_events_details, container, false);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.lay_event_detail);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setHasOptionsMenu(true);
            // Disable viewpager scrolling, disable tabs in order to use the action bar for vertical use
            ((AppCompatActivity) getActivity()).findViewById(R.id.tabs).setVisibility(View.GONE);
            CustomViewPager viewPager = (CustomViewPager) getActivity().findViewById(R.id.container);
            viewPager.setPagingEnabled(false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.events_detail_header));
            layout.setVisibility(View.GONE);
        }

        connectHandler = ConnectionHandler.getInstance();

        mCalendar = Calendar.getInstance();

        final ImageButton deleteButton = (ImageButton) view.findViewById(R.id.btn_delete);
        final ImageButton saveButton = (ImageButton) view.findViewById(R.id.btn_save);
        saveButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);

            // check admin
        for (int i=0; i < connectHandler.patient.care_team.size(); i++){
            if ((connectHandler.person.person_ID == connectHandler.patient.care_team.get(i).person_ID) &&
                (connectHandler.patient.care_team.get(i).admin == 1)){
                    admin = true;
            }
        }

        if (admin){
            deleteButton.setVisibility(View.VISIBLE);
        }
        Context context = MyApplication.getContext();

        eventImage = (ImageView) view.findViewById(R.id.img_event);
        eventText = (TextView) view.findViewById(R.id.txt_event_category);
        informationButton = (ImageButton) view.findViewById(R.id.btn_event_information);
        final Button appointmentButton = (Button) view.findViewById(R.id.btn_event_selection_appointment);
        final Button treatmentButton = (Button) view.findViewById(R.id.btn_event_selection_treatment);
        final Button testButton = (Button) view.findViewById(R.id.btn_event_selection_test);
        mDateButton = (Button) view.findViewById(R.id.btn_pick_date);
        mTimeButton = (Button) view.findViewById(R.id.btn_pick_time);
        final TextView chooseCategory = (TextView) view.findViewById(R.id.event_choose_category);
        final LinearLayout showOnSelection = (LinearLayout) view.findViewById(R.id.show_on_selection);
        notes = (EditText) view.findViewById(R.id.event_notes);
        selectionLayout = (GridLayout) view.findViewById(R.id.lay_events_selection);
        subCategory1 = (ImageView) view.findViewById(R.id.img_subcategory1);
        subCategory2 = (ImageView) view.findViewById(R.id.img_subcategory2);
        subCategory3 = (ImageView) view.findViewById(R.id.img_subcategory3);
        subCategory4 = (ImageView) view.findViewById(R.id.img_subcategory4);
        subCategory5 = (ImageView) view.findViewById(R.id.img_subcategory5);
        subCategory6 = (ImageView) view.findViewById(R.id.img_subcategory6);
        subCategory7 = (ImageView) view.findViewById(R.id.img_subcategory7);
        subCategory8 = (ImageView) view.findViewById(R.id.img_subcategory8);
        subCategory9 = (ImageView) view.findViewById(R.id.img_subcategory9);


        if (listItem.sub_category.matches("create_new")){
            eventText.setText(context.getResources().getString(R.string.choose_event));
            showOnSelection.setVisibility(View.GONE);
            subCategoryClicked = "";
        }else{
            eventImage.setVisibility(View.VISIBLE);
            informationButton.setVisibility(View.VISIBLE);
            mDateButton.setText(listItem.date.substring(0,10));
            mTimeButton.setText(listItem.time.substring(0,5));
            eventImage.setImageResource(context.getResources().getIdentifier("event_"+listItem.sub_category+"_bubble", "drawable", context.getPackageName()));
            eventText.setText(context.getString(getActivity().getResources().getIdentifier("event_"+listItem.sub_category, "string", context.getPackageName())));
            notes.setText(listItem.notes);
            chooseCategory.setVisibility(View.GONE);
        }

        switch(listItem.category){
            case EVENT_CATEGORY_APPOINTMENT:
                appointmentButton.setSelected(true);
                break;
            case EVENT_CATEGORY_TREATMENT:
                treatmentButton.setSelected(true);
                break;
            case EVENT_CATEGORY_TEST:
                testButton.setSelected(true);
                break;
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteButton.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
            }
        });

        eventImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionLayout.getVisibility()==View.VISIBLE){
                    selectionLayout.setVisibility(View.GONE);
                }else{
                    selectionLayout.setVisibility(View.VISIBLE);
                    prepareSubCategories(listItem.category);
                }
            }
        });

        appointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnSelection.setVisibility(View.VISIBLE);
                chooseCategory.setVisibility(View.GONE);
                appointmentButton.setSelected(true);
                treatmentButton.setSelected(false);
                testButton.setSelected(false);
                category = EVENT_CATEGORY_APPOINTMENT;
                prepareSubCategories(category);
                selectionLayout.setVisibility(View.VISIBLE);
            }
        });

        treatmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnSelection.setVisibility(View.VISIBLE);
                chooseCategory.setVisibility(View.GONE);
                appointmentButton.setSelected(false);
                treatmentButton.setSelected(true);
                testButton.setSelected(false);
                category = EVENT_CATEGORY_TREATMENT;
                prepareSubCategories(category);
                selectionLayout.setVisibility(View.VISIBLE);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnSelection.setVisibility(View.VISIBLE);
                chooseCategory.setVisibility(View.GONE);
                appointmentButton.setSelected(false);
                treatmentButton.setSelected(false);
                testButton.setSelected(true);
                category = EVENT_CATEGORY_TEST;
                prepareSubCategories(category);
                selectionLayout.setVisibility(View.VISIBLE);
            }
        });

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                EventDialogFragment dialogFragment = new EventDialogFragment();
                Bundle args = new Bundle();
                args.putString(EventDialogFragment.SUB_CATEGORY, listItem.sub_category);
                dialogFragment.setArguments(args);
                dialogFragment.show(fm, "Josef");
            }
        });

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {        // Inflate the menu; this adds items to the action bar if it is present.
        if (admin){
            //only admin can save or delete
            inflater.inflate(R.menu.menu_details, menu);
            if (listItem.sub_category.matches("create_new")){
                MenuItem nextItem = menu.findItem(R.id.action_delete);
                nextItem.setVisible(false);
            }
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteEvent();
            return true;
        }

        if (id == R.id.action_save) {
            saveEvent();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setItem(Event selectedListItem){
        listItem = selectedListItem;
    }

    private void prepareSubCategories(String category){

        subCategory1.setVisibility(View.INVISIBLE);
        subCategory2.setVisibility(View.INVISIBLE);
        subCategory3.setVisibility(View.INVISIBLE);
        subCategory4.setVisibility(View.INVISIBLE);
        subCategory5.setVisibility(View.INVISIBLE);
        subCategory6.setVisibility(View.INVISIBLE);
        subCategory7.setVisibility(View.INVISIBLE);
        subCategory8.setVisibility(View.INVISIBLE);
        subCategory9.setVisibility(View.INVISIBLE);

        switch (category) {
            case EVENT_CATEGORY_APPOINTMENT:
                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);
                subCategory9.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.event_medical_oncologist_bubble);
                subCategory2.setBackgroundResource(R.drawable.event_hematologist_doctor_bubble);
                subCategory3.setBackgroundResource(R.drawable.event_nurse_bubble);
                subCategory4.setBackgroundResource(R.drawable.event_dentist_bubble);
                subCategory5.setBackgroundResource(R.drawable.event_surgeon_bubble);
                subCategory6.setBackgroundResource(R.drawable.event_anestetisten_bubble);
                subCategory7.setBackgroundResource(R.drawable.event_therapist_bubble);
                subCategory8.setBackgroundResource(R.drawable.event_physiotherapist_bubble);
                subCategory9.setBackgroundResource(R.drawable.event_dietician_bubble);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        subCategoryClicked = "medical_oncologist";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "hematologist_doctor";

                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "nurse";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "dentist";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "surgeon";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "anestetisten";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "therapist";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "physiotherapist";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "dietician";
                        setEventCategory(subCategoryClicked);
                    }
                });

                break;

            case EVENT_CATEGORY_TREATMENT:
                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.event_cytostatika_bubble);
                subCategory2.setBackgroundResource(R.drawable.event_surgery_bubble);
                subCategory3.setBackgroundResource(R.drawable.event_stem_cell_transplantation_bubble);
                subCategory4.setBackgroundResource(R.drawable.event_radiation_bubble);
                subCategory5.setBackgroundResource(R.drawable.event_dialysis_bubble);
                subCategory6.setBackgroundResource(R.drawable.event_biological_therapy_bubble);
                subCategory7.setBackgroundResource(R.drawable.event_targeted_therapy_bubble);
                subCategory8.setBackgroundResource(R.drawable.event_portacat_bubble);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "cytostatika";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "surgery";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "stem_cell_transplantation";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "radiation";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "dialysis";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "biological_therapy";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "targeted_therapy";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "portacat";
                        setEventCategory(subCategoryClicked);

                    }
                });

                break;

            case EVENT_CATEGORY_TEST:
                subCategory1.setVisibility(View.VISIBLE);
                subCategory2.setVisibility(View.VISIBLE);
                subCategory3.setVisibility(View.VISIBLE);
                subCategory4.setVisibility(View.VISIBLE);
                subCategory5.setVisibility(View.VISIBLE);
                subCategory6.setVisibility(View.VISIBLE);
                subCategory7.setVisibility(View.VISIBLE);
                subCategory8.setVisibility(View.VISIBLE);
                subCategory9.setVisibility(View.VISIBLE);

                subCategory1.setBackgroundResource(R.drawable.event_bloodtest_bubble);
                subCategory2.setBackgroundResource(R.drawable.event_mr_bubble);
                subCategory3.setBackgroundResource(R.drawable.event_dt_bubble);
                subCategory4.setBackgroundResource(R.drawable.event_hearing_tests_bubble);
                subCategory5.setBackgroundResource(R.drawable.event_bone_marrow_samples_bubble);
                subCategory6.setBackgroundResource(R.drawable.event_eeg_bubble);
                subCategory7.setBackgroundResource(R.drawable.event_ekg_bubble);
                subCategory8.setBackgroundResource(R.drawable.event_kidney_investigation_bubble);
                subCategory9.setBackgroundResource(R.drawable.event_ultrasound_bubble);

                subCategory1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "bloodtest";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "mr";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "dt";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "hearing_tests";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "bone_marrow_samples";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "eeg";
                        setEventCategory(subCategoryClicked);
                    }
                });
                subCategory7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "ekg";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "kidney_investigation";
                        setEventCategory(subCategoryClicked);

                    }
                });
                subCategory9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subCategoryClicked = "ultrasound";
                        setEventCategory(subCategoryClicked);

                    }
                });
                break;
        }
    }

    private void setEventCategory(String subCategoryClicked){
        eventImage.setVisibility(View.VISIBLE);
        informationButton.setVisibility(View.VISIBLE);
        eventImage.setImageResource(getActivity().getResources().getIdentifier("event_"+subCategoryClicked+"_bubble", "drawable", getActivity().getPackageName()));
        eventText.setText(getActivity().getString(getActivity().getResources().getIdentifier("event_"+subCategoryClicked, "string", getActivity().getPackageName())));
        if(!subCategoryClicked.isEmpty()){
            // Selection has been made, update listItem
            listItem.sub_category = subCategoryClicked;
            listItem.category = category;
        }

    }

    private void saveEvent(){

        // check if event is new or updated
        if (listItem.event_ID != 0){
            //update event
            Event updatedEvent = new Event(
                    listItem.event_ID,
                    listItem.patient_ID,
                    listItem.person_ID,
                    listItem.healthcare_ID,
                    listItem.healthcare_name,
                    listItem.category,
                    listItem.sub_category,
                    mDateButton.getText().toString(),
                    mTimeButton.getText().toString(),
                    notes.getText().toString(),
                    listItem.status,
                    listItem.emotion);

            connectHandler.updateEvent(updatedEvent);
            while(connectHandler.socketBusy){}
            getActivity().onBackPressed();
        }
        else{
            // new event, create new
            if (subCategoryClicked.isEmpty() ||
                    mDateButton.getText().toString().isEmpty() ||
                    mTimeButton.getText().toString().isEmpty()){
                // User has not given all input need for a new event
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                String alertText = String.format(getResources().getString(R.string.all_event_data_not_provided));
                alertDialogBuilder.setMessage(alertText);

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
            else{
                Event newEvent = new Event(
                        0,
                        connectHandler.patient.patient_ID,
                        connectHandler.person.person_ID,
                        0,
                        "",
                        category,
                        subCategoryClicked,
                        mDateButton.getText().toString(),
                        mTimeButton.getText().toString(),
                        notes.getText().toString(),
                        "",
                        "");
                connectHandler.createEvent(newEvent);
                while(connectHandler.socketBusy){}
                getActivity().onBackPressed();
            }
        }
    }

    private void deleteEvent(){
        // Generate dialog to make sure user wants to delete
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        String alertText = String.format(getString(R.string.remove_question));
        alertDialogBuilder.setMessage(alertText);

        alertDialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                connectHandler.deleteEvent(listItem.event_ID);
                while(connectHandler.socketBusy){}
                getActivity().onBackPressed();
                // remove item from list and from database
                // removeItemFromList(listItem.event_ID);
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            // do nothing
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void showDatePickerDialog(View v) {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");

    }

    public void showTimePickerDialog(View v) {
        timeFragment = new TimePickerFragment();
        timeFragment.show(getFragmentManager(), "timePicker");
    }

    public void updateDateButtonText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(mCalendar.getTime());
        mDateButton.setText(dateForButton);
    }
    private void updateTimeButtonText() {
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        mTimeButton.setText(timeForButton);
    }

    class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            int year = 0;
            int month = 0;
            int day = 0;
            if (listItem.date.isEmpty()){
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            }
            else
            {
                String dateString = mDateButton.getText().toString();
                String[] parts = dateString.split("-");
                year = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
                day = Integer.parseInt(parts[2]);
            }
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateButtonText();
        }
    }

    class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            int hour = 0;
            int minute = 0;
            if (listItem.time.isEmpty()){
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            }
            else
            {
                String timeString = mTimeButton.getText().toString();
                String[] parts = timeString.split(":");
                hour = Integer.parseInt(parts[0]);
                minute = Integer.parseInt(parts[1]);
            }
            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
            updateTimeButtonText();
        }
    }

}