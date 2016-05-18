package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by robinlarsson on 12/04/16.
 */
public class Users implements Serializable {

    public String username;
    public String password;
    public String email;
    public Boolean patient;




    public Users(String username, String password, String email, Boolean patient){

        this.username = username;
        this.password = password;
        this.email = email;
        this.patient = patient;

    }


}




