
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-15.
 */
public class HealthCareData implements Serializable {

    public ArrayList<HealthCare> healthcare_data;

    public HealthCareData(ArrayList<HealthCare> healthcare_data) {
        this.healthcare_data = healthcare_data; //default value, does not matter because updated during read from database
    }
}