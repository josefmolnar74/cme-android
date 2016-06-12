package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;

/**
 * Created by 23047371 on 2016-06-08.
 */
public class MsgHeader implements Serializable{

    public int message_ID;
    public String function;
    public String content;
    public String errorCode;

    public MsgHeader (int message_ID, String function, String content, String errorCode){
        this.message_ID = message_ID;
        this.function = function;
        this.content = content;
        this.errorCode = errorCode;
    }
}
