package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;

/**
 * Created by robinlarsson on 04/04/16.
 */
public class Contacts implements Serializable {

    public String name;
    public String phone;
    public String email;
    public String relation;
    public Boolean admin;



    public Contacts(String name, String phone, String email, String relation, Boolean admin){

        this.email = email;
        this.name = name;
        this.phone = phone;
        this.relation = relation;
        this.admin = admin;

    }



}
