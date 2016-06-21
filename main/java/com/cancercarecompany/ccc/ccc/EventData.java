
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-15.
 */
public class EventData implements Serializable {

    public ArrayList<Event> event_data;

    public EventData(ArrayList<Event> event_data) {
        this.event_data = event_data; //default value, does not matter because updated during read from database
    }
}