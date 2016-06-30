
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;

/**
 * Created by 23047371 on 2016-06-15.
 */
public class Invite implements Serializable {

    public int invite_ID;
    public String invited_by;
    public int patient_ID;
    public String patient_name;
    public String invited_first_name;
    public String invited_last_name;
    public String invited_email;
    public String invited_relationship;
    public int invited_avatar;
    public int invited_admin;
    public int invite_accepted;
    public int person_ID;

    public Invite(int invite_ID, String invited_by, int patient_ID, String patient_name, String invited_first_name,
                  String invited_last_name, String invited_email, String invited_relationship, int invited_avatar, int invited_admin, int invite_accepted, int person_ID) {
        this.invite_ID = 0; //default value, does not matter because updated during read from database
        this.invited_by = invited_by;
        this.patient_ID = patient_ID;
        this.patient_name = patient_name;
        this.invited_first_name = invited_first_name;
        this.invited_last_name = invited_last_name;
        this.invited_email = invited_email;
        this.invited_relationship = invited_relationship;
        this.invited_avatar = invited_avatar;
        this.invited_admin = invited_admin;
        this.invite_accepted = invite_accepted;
        this.person_ID = person_ID;
    }
}