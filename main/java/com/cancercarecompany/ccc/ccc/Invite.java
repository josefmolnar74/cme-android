
package com.cancercarecompany.ccc.ccc;

/**
 * Created by 23047371 on 2016-06-15.
 */
public class Invite {

    public int invite_ID;
    public Boolean invite_accepted;
    public String invited_by;
    public int patient_ID;
    public String patient_name;
    public String invited_email;
    public String invited_relationship;
    public int invited_admin;

    public Invite(int invite_ID, Boolean invite_accepted, String invited_by, int patient_ID, String patient_name,
                  String invited_email, String invited_relationship, int invited_admin) {
        this.invite_ID = 0; //default value, does not matter because updated during read from database
        this.invite_accepted = invite_accepted;
        this.invited_by = invited_by;
        this.patient_ID = patient_ID;
        this.patient_name = patient_name;
        this.invited_email = invited_email;
        this.invited_relationship = invited_relationship;
        this.invited_admin = invited_admin;
    }
}