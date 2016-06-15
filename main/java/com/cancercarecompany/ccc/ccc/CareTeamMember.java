package com.cancercarecompany.ccc.ccc;

import java.util.ArrayList;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class CareTeamMember {

    public int person_ID;
    public String first_name;
    public String last_name;
    public String email;
    public String relationship;
    public Boolean admin;

    public CareTeamMember(int person_id, String firstName, String lastName, String email, String relation, Boolean admin) {

        this.person_ID = person_id; //default value, does not matter because updated during read from database
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.relationship = relation;
        this.admin = admin;
    }
}
