package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;

/**
 * Created by 23047371 on 2016-06-08.
 */
public class MsgHeader implements Serializable{

    public int message_ID;
    public String function;
    public String content;

    public MsgHeader (int person_ID, String content){
        this.message_ID = message_ID;
        this.function = function;
        this.content = content;
    }
}
