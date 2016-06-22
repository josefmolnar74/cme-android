
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-21.
 */
public class StatusData implements Serializable {

    public ArrayList<Status> status_data;

    public StatusData(ArrayList<Status> status_data) {
        this.status_data = status_data; //default value, does not matter because updated during read from database
    }
}