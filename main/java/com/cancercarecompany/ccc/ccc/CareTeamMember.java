package com.cancercarecompany.ccc.ccc;

import java.util.ArrayList;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class CareTeamMember {

    public int person_ID;
    public String name;
    public String email;
    public String relationship;
    public int avatar;
    public int admin;

    public CareTeamMember(int person_id, String name, String email, String relation, int avatar, int admin) {

        this.person_ID = person_id; //default value, does not matter because updated during read from database
        this.name = name;
        this.email = email;
        this.relationship = relation;
        this.avatar = avatar;
        this.admin = admin;
    }
}
