
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-15.
 */
public class InviteData implements Serializable {

    public ArrayList<Invite> invite_data;

    public InviteData(ArrayList<Invite> invite_data) {
        this.invite_data = invite_data; //default value, does not matter because updated during read from database
    }
}