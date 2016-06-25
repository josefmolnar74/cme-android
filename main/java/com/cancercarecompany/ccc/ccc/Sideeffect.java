package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 06/25/16.
 */
public class Sideeffect {

    int status_ID;
    int patient_ID;
    int person_ID;
    String date;
    String time;
    String type;
    String value;

    public Sideeffect(int status_ID, int patient_ID, int person_ID, String date, String time, String type, String value){

        this.status_ID  = status_ID;
        this.patient_ID = patient_ID;
        this.person_ID  = person_ID;
        this.date       = date;
        this.time       = time;
        this.type     = type;
        this.value    = value;
    }
}
