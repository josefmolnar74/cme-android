package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 06/24/16.
 */
public class BeverageData implements Serializable {

    public ArrayList<Beverage> beverage_data;

    public BeverageData(ArrayList<Beverage> beverage_data) {
        this.beverage_data = beverage_data; //default value, does not matter because updated during read from database
    }
}