package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by robinlarsson on 28/04/16.
 */
public class LoginData implements Serializable {

    public int person_ID;
    public String name;
    public String email;
    public String password;
    public ArrayList<Patient> patient;


    public LoginData(int person_ID, String name, String email, String password, Patient[] patient){

        this.person_ID = person_ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.patient = new ArrayList<>(Arrays.asList(patient));
    }
}

