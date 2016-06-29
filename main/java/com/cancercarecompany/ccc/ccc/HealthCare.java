package com.cancercarecompany.ccc.ccc;

/**
 * Created by robinlarsson on 27/04/16.
 */
public class HealthCare {

    public int healthcare_ID;
    public int patient_ID;
    public String title;
    public String name;
    public String department;
    public String phone_number1;
    public String phone_number2;
    public String phone_number3;
    public String email;
    public int avatar;

    public HealthCare(int healthcare_ID, int patient_ID, String title, String name, String department,
                      String phone_number1, String phone_number2, String phone_number3, String email, int avatar) {

        this.healthcare_ID = healthcare_ID; //default value, does not matter because updated during read from database
        this.patient_ID = patient_ID;
        this.title = title;
        this.name = name;
        this.department = department;
        this.phone_number1 = phone_number1;
        this.phone_number2 = phone_number2;
        this.phone_number3 = phone_number3;
        this.email = email;
        this.avatar = avatar;
    }
}
