package com.cancercarecompany.ccc.ccc;

import java.util.Date;

/**
 * Created by 23047371 on 06/21/16.
 */
public class Status {

    int status_ID;
    int patient_ID;
    int person_ID;
    String date;
    String time;
    String status;
    String emotion;
    int share;


    public Status(int status_ID, int patient_ID, int person_ID, String date, String time, String status, String emotion, int share){

        this.status_ID  = status_ID;
        this.patient_ID = patient_ID;
        this.person_ID  = person_ID;
        this.date       = date;
        this.time       = time;
        this.status     = status;
        this.emotion    = emotion;
        this.share       = share;
    }
}
