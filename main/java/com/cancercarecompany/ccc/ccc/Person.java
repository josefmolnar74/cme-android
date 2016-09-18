package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robinlarsson on 25/04/16.
 */
public class Person implements Serializable {

    public int person_ID;
    public String name;
    public String email;
    public String password;
    public int avatar;
    public ArrayList<Patient> patient;


    public Person (int person_ID, String name, String email, String password, int avatar, ArrayList<Patient> patient){
        this.person_ID = person_ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.patient = patient;
        this.avatar = avatar;
    }

}
