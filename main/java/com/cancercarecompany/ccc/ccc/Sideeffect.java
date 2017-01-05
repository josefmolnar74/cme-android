package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 06/25/16.
 */
public class Sideeffect {

    int sideeffect_ID;
    int patient_ID;
    int person_ID;
    String date;
    String time;
    String type;
    int severity;
    String value;
    String notes;

    public Sideeffect(int sideeffect_ID, int patient_ID, int person_ID, String date, String time, String type, int severity, String value, String notes){

        this.sideeffect_ID  = sideeffect_ID;
        this.patient_ID = patient_ID;
        this.person_ID  = person_ID;
        this.date = date;
        this.time = time;
        this.type = type;
        this.severity = severity;
        this.value = value;
        this.notes = notes;
    }
}
