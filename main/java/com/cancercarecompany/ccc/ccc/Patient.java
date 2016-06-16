package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class Patient implements Serializable {

    public int patient_ID;
    public String patient_name;
    public String year_of_birth;
    public String diagnose;
    public ArrayList<CareTeamMember> care_team;
    public ArrayList<Events> events;

    public Patient(int patient_ID, String patient_name, String year_of_birth, String diagnose, ArrayList<CareTeamMember> care_team, ArrayList<Events> events) {
        this.patient_ID = patient_ID;
        this.patient_name = patient_name;
        this.year_of_birth = year_of_birth;
        this.diagnose = diagnose;
        this.care_team = care_team;
        this.events = events;
    }
}
