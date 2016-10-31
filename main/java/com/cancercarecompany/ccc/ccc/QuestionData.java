package com.cancercarecompany.ccc.ccc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Josef on 2016-10-30.
 */

public class QuestionData implements Serializable {

    public ArrayList<Question> question_data;

    public QuestionData(ArrayList<Question> question_data) {
        this.question_data = question_data;
    }
}