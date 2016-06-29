
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-21.
 */
public class JournalData implements Serializable {

    public ArrayList<Event> event_data;
    public ArrayList<Status> status_data;
    public ArrayList<Sideeffect> sideeffect_data;
    public ArrayList<Beverage> beverage_data;

    public JournalData( ArrayList<Event> event_data,
                        ArrayList<Status> status_data,
                        ArrayList<Sideeffect> sideeffect_data,
                        ArrayList<Beverage> beverage_data){
        this.event_data = event_data;
        this.status_data = status_data;
        this.sideeffect_data = sideeffect_data;
        this.beverage_data = beverage_data;
    }
}