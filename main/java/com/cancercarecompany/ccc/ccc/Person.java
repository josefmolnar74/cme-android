package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robinlarsson on 25/04/16.
 */
public class Person implements Serializable {

    public int person_ID;
    public String first_name;
    public String last_name;
    public String email;
    public String password;
    public ArrayList<Patient> patient;


    public Person (int person_ID, String firstname, String lastname, String email, String password, ArrayList<Patient> patient){
        this.person_ID = person_ID;
        this.first_name = firstname;
        this.last_name = lastname;
        this.email = email;
        this.password = password;
        this.patient = patient;
    }

}
