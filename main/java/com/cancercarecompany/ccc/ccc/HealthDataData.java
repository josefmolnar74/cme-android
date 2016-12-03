
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-12-02.
 */
public class HealthDataData implements Serializable {

    public ArrayList<HealthData> healthdata_data;

    public HealthDataData(ArrayList<HealthData> healthdata_data) {
        this.healthdata_data = healthdata_data; //default value, does not matter because updated during read from database
    }
}