package com.cancercarecompany.ccc.ccc;

import java.util.ArrayList;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class Care_team {

    public String first_name;
    public String last_name;
    public String email;
    public String relationship;
    public int    admin;

    public Care_team(String FirstName, String LastName, String Email, String Relation, Integer Admin) {

        this.first_name   = FirstName;
        this.last_name    = LastName;
        this.email        = Email;
        this.relationship = Relation;
        this.admin        = Admin;
    }
}
