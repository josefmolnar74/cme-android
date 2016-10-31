package com.cancercarecompany.ccc.ccc;

/**
 * Created by Josef on 2016-10-30.
 */
public class Question {

    int question_ID;
    int patient_ID;
    int person_ID;
    String email;
    String question;
    String answer;
    String date;
    String time;
    String status;

    public Question(int question_ID, int patient_ID, int person_ID, String email, String question, String answer, String date, String time, String status){

        this.question_ID = question_ID;
        this.patient_ID = patient_ID;
        this.person_ID = person_ID;
        this.email = email;
        this.question = question;
        this.answer = answer;
        this.date = date;
        this.time = time;
        this.status = status;
    }
}
