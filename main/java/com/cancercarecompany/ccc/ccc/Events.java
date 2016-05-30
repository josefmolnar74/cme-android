package com.cancercarecompany.ccc.ccc;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by robinlarsson on 04/05/16.
 */
public class Events {

    String category;
    String subCategory;
    String notes;
    Date startDate;
    Date startTime;
    Date endDate;
    Date endTime;


    public Events(String category, String subCategory, String notes, Date startDate, Date startTime, Date endDate, Date endTime){


        this.category = category;
        this.notes = notes;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.subCategory = subCategory;
    }

}
