package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 06/24/16.
 */
public class Beverage {

    int beverage_ID;
    int patient_ID;
    int person_ID;
    String date;
    String time;
    int amount;


    public Beverage(int beverage_ID, int patient_ID, int person_ID, String date, String time, int amount){

        this.beverage_ID  = beverage_ID;
        this.patient_ID = patient_ID;
        this.person_ID  = person_ID;
        this.date       = date;
        this.time       = time;
        this.amount     = amount;
    }
}
