package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by robinlarsson on 28/04/16.
 */
public class Lcl_work_area implements Serializable {

    public int person_ID;
    public String first_name;
    public String last_name;
    public String email;
    public String password;
    public ArrayList<Patient> patient;


    public Lcl_work_area(int person_ID, String first_name, String last_name, String email, String password, Patient[] patient){

        this.person_ID = person_ID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.patient = new ArrayList<>(Arrays.asList(patient));

    }
}
