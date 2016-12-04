package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 12/02/16.
 */
public class HealthData {

    int healthdata_ID;
    int patient_ID;
    int person_ID;
    String date;
    String time;
    String type;
    String value;

    public HealthData(int sideeffect_ID, int patient_ID, int person_ID, String date, String time, String type, String value){

        this.healthdata_ID  = sideeffect_ID;
        this.patient_ID = patient_ID;
        this.person_ID  = person_ID;
        this.date = date;
        this.time = time;
        this.type = type;
        this.value = value;
    }
}
