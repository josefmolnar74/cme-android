package com.cancercarecompany.ccc.ccc;

import java.sql.Time;
import java.util.Date;

/**
 * Created by robinlarsson on 04/05/16.
 */
public class Event {

    int event_ID;
    int patient_ID;
    int person_ID;
    int healthcare_ID;
    String healthcare_name;
    String category;
    String sub_category;
    Date date;
    long time;
    String notes;
    String status;
    String emotion;


    public Event( int event_ID, int patient_ID, int person_ID, int healthcare_ID, String healthcare_name, String category, String subCategory,
                  Date date, long time, String notes, String status, String emotion){

        this.event_ID   = event_ID;
        this.patient_ID = patient_ID;
        this.person_ID = person_ID;
        this.healthcare_ID = healthcare_ID;
        this.healthcare_name = healthcare_name;
        this.category = category;
        this.sub_category = subCategory;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.status = status;
        this.emotion = emotion;
    }
}
