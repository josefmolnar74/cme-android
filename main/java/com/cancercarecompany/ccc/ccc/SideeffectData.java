
package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 23047371 on 2016-06-21.
 */
public class SideeffectData implements Serializable {

    public ArrayList<Sideeffect> sideeffect_data;

    public SideeffectData(ArrayList<Sideeffect> sideeffect_data) {
        this.sideeffect_data = sideeffect_data; //default value, does not matter because updated during read from database
    }
}