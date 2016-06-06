package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class Patients implements Serializable {

    public int patient_ID;
    public String patient_name;
    public String relationship;
    public ArrayList<Care_team> care_team;
    public ArrayList<Events> events;




//    public Patients(int patient_ID, String patient_name, String relationship, Care_team[] care_team){
    public Patients(int patient_ID, String patient_name, String relationship, ArrayList<Care_team> care_team, ArrayList<Events> events) {
        this.patient_ID = patient_ID;
        this.patient_name = patient_name;
        this.relationship = relationship;
//        this.care_team = new ArrayList<>(Arrays.asList(care_team));
        this.care_team = care_team;
        this.events = events;
    }

}
