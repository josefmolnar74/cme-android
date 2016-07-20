package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-07-20.
 */
public class JournalEventAdapter extends ArrayAdapter<Event> {

    ArrayList<Event> eventList;

    public JournalEventAdapter(Context context, ArrayList<Event> eventList){
        super(context, R.layout.journal_events_listview, eventList);
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customListView = inflater.inflate(R.layout.journal_events_listview, parent, false);

        ImageView eventImage = (ImageView) customListView.findViewById(R.id.img_journal_listview_event);
        TextView subCategory = (TextView) customListView.findViewById(R.id.txt_journal_listview_event_sub_category);
        TextView date = (TextView) customListView.findViewById(R.id.txt_journal_listview_event_date);
        TextView time = (TextView) customListView.findViewById(R.id.txt_journal_listview_event_time);

        subCategory.setText(eventList.get(position).sub_category);
        date.setText(eventList.get(position).date.split("T")[0]);
        time.setText(eventList.get(position).time);

        switch (eventList.get(position).sub_category) {
            case "medical_oncologist":
                eventImage.setBackgroundResource(R.drawable.event_medical_oncologist_bubble);
                break;
            case "bloodtest":
                eventImage.setBackgroundResource(R.drawable.event_bloodtest_bubble);
                break;
            case "hematologist_doctor":
                eventImage.setBackgroundResource(R.drawable.event_hematologist_doctor_bubble);
                break;
            case "nurse":
                eventImage.setBackgroundResource(R.drawable.event_nurse_bubble);
                break;
            case "dentist":
                eventImage.setBackgroundResource(R.drawable.event_dentist_bubble);
                break;
            case "surgeon":
                eventImage.setBackgroundResource(R.drawable.event_surgeon_bubble);
                break;
            case "anestetisten":
                eventImage.setBackgroundResource(R.drawable.event_anestetisten_bubble);
                break;
            case "therapist":
                eventImage.setBackgroundResource(R.drawable.event_therapist_bubble);
                break;
            case "physiotherapist":
                eventImage.setBackgroundResource(R.drawable.event_physiotherapist_bubble);
                break;
            case "dietician":
                eventImage.setBackgroundResource(R.drawable.event_dietician_bubble);
                break;
            case "mr":
                eventImage.setBackgroundResource(R.drawable.event_mr_bubble);
                break;
            case "dt":
                eventImage.setBackgroundResource(R.drawable.dt);
                break;
            case "hearing_tests":
                eventImage.setBackgroundResource(R.drawable.event_hearing_test_bubble);
                break;
            case "bone_marrow_samples":
                eventImage.setBackgroundResource(R.drawable.event_bone_marrow_bubble);
                break;
            case "eeg":
                eventImage.setBackgroundResource(R.drawable.event_eeg_bubble);
                break;
            case "ekg":
                eventImage.setBackgroundResource(R.drawable.event_ekg_bubble);
                break;
            case "kidney_investigation":
                eventImage.setBackgroundResource(R.drawable.event_kidney_investigation_bubble);
                break;
            case "ultrasound":
                eventImage.setBackgroundResource(R.drawable.event_ultrasound_bubble);
                break;
            case "alternating_current":
                eventImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "transplantation":
                eventImage.setBackgroundResource(R.drawable.greenbubble);
                break;
            case "cytostatika":
                eventImage.setBackgroundResource(R.drawable.event_cytostatika_bubble);
                break;
            case "surgery":
                eventImage.setBackgroundResource(R.drawable.event_surgery_bubble);
                break;
            case "stem_cell_transplantation":
                eventImage.setBackgroundResource(R.drawable.event_stem_cell_transplantation_bubble);
                break;
            case "radiation":
                eventImage.setBackgroundResource(R.drawable.event_radiation_bubble);
                break;
            case "dialysis":
                eventImage.setBackgroundResource(R.drawable.event_dialysis_bubble);
                break;
            case "biological_therapy":
                eventImage.setBackgroundResource(R.drawable.event_biological_therapy_bubble);
                break;
            case "targeted_therapy":
                eventImage.setBackgroundResource(R.drawable.event_targeted_therapy_bubble);
                break;
            case "portacat":
                eventImage.setBackgroundResource(R.drawable.event_portakat_bubble);
                break;
            case "picture_memory":
                eventImage.setBackgroundResource(R.drawable.photominne);
                break;
            case "hospital":
                eventImage.setBackgroundResource(R.drawable.event_hospital_bubble);
                break;
            case "start":
                eventImage.setBackgroundResource(R.drawable.journeystart);
                break;
        }

        return customListView;
    }
}