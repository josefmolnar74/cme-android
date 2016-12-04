package com.cancercarecompany.ccc.ccc;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by Josef on 2016-07-22.
 */
public class OfflineDataManager {
    private static OfflineDataManager ourInstance = new OfflineDataManager();
    private static ConnectionHandler connectHandler = ConnectionHandler.getInstance();

    private LoginData loginData;
    private Person person;
    private Patient patient;
    private InviteData invites;
    private HealthCareData healthcares;
    private EventData events;
    private BeverageData beverages;
    private SideeffectData sideeffects;
    private HealthDataData healthData;
    private StatusData status;

    private JournalData journal;

    public static final String FILENAME_LOGIN_DATA = "cme_login_data";
    public static final String FILENAME_EVENTS_DATA = "cme_events_data";
    public static final String FILENAME_STATUS_DATA = "cme_status_data";
    public static final String FILENAME_SIDEEFFECTS_DATA = "cme_sideeffects_data";
    public static final String FILENAME_HEALTH_DATA_DATA = "cme_health_data_data";
    public static final String FILENAME_BEVERAGES_DATA = "cme_beverages_data";
    public static final String FILENAME_PERSON_DATA = "cme_person_data";
    public static final String FILENAME_PATIENT_DATA = "cme_patient_data";
    public static final String FILENAME_INVITES_DATA = "cme_invites_data";
    public static final String FILENAME_HEALTHCARE_DATA = "cme_healthcare_data";

    public static OfflineDataManager getInstance() {
        return ourInstance;
    }

    private OfflineDataManager() {
        //constructor
        readLocalData();
    }

    public void readLocalData (){
        readLoginLocalData();
        readStatusLocalData();
        readSideeffectsLocalData();
        readBeveragesLocalData();
        readEventsLocalData();
        readPersonLocalData();
        readPatientLocalData();
        readInvitesLocalData();
        readHealthcareLocalData();
    }

    public void readLoginLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_LOGIN_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            loginData =  gson.fromJson(sb.toString(), LoginData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readEventsLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_EVENTS_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            events =  gson.fromJson(sb.toString(), EventData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readStatusLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_STATUS_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            status =  gson.fromJson(sb.toString(), StatusData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readBeveragesLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_BEVERAGES_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            beverages =  gson.fromJson(sb.toString(), BeverageData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readSideeffectsLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_SIDEEFFECTS_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            sideeffects =  gson.fromJson(sb.toString(), SideeffectData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readHealthDataLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_HEALTH_DATA_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            healthData =  gson.fromJson(sb.toString(), HealthDataData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readPersonLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_PERSON_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            person =  gson.fromJson(sb.toString(), Person.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readPatientLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_PATIENT_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            patient =  gson.fromJson(sb.toString(), Patient.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readInvitesLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_INVITES_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            invites =  gson.fromJson(sb.toString(), InviteData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void readHealthcareLocalData(){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_HEALTHCARE_DATA);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            Gson gson = new Gson();
            healthcares =  gson.fromJson(sb.toString(), HealthCareData.class);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (UnsupportedEncodingException e) {
//            return "";
        } catch (IOException e) {
//            return "";
        }
    }

    public void saveLocaldata (){
        // Save data to internal file
    }

    public void saveLoginData (String loginData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_LOGIN_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(loginData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveLoginData failed");
        }
    }

    public void savePersonData (String personData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_PERSON_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(personData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SavePerson data failed");
        }
    }

    public void savePatientData (String patientData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_PATIENT_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(patientData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SavePatient data failed");
        }
    }

    public void saveInviteData (String inviteData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_INVITES_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(inviteData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveInvite data failed");
        }
    }

    public void saveHealthCareData (String healthcareData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_HEALTHCARE_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(healthcareData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveHealthcare data failed");
        }
    }

    public void saveEventsData (String eventData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_EVENTS_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(eventData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveEvents data failed");
        }
    }

    public void saveStatusData (String statusData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_STATUS_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(statusData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveStatus data failed");
        }
    }

    public void saveSideeffectsData (String sideeffectsData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_SIDEEFFECTS_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sideeffectsData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveSideeffects data failed");
        }
    }

    public void saveHealthDataData (String healthDataData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_HEALTH_DATA_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(healthDataData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveSideeffects data failed");
        }
    }

    public void saveBeveragesData (String beveragesData){
        File file = new File(MyApplication.getContext().getFilesDir(), FILENAME_BEVERAGES_DATA);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(beveragesData.getBytes());
            fos.close();
        } catch (Exception e){
            // file write failed
            System.out.println("SaveBeverages data failed");
        }
    }

    public void saveJournalData (String journalData){
        Gson gson = new Gson();
        journal = gson.fromJson(journalData, JournalData.class);
        EventData events = new EventData(journal.event_data);
        saveEventsData(gson.toJson(events));
        StatusData status = new StatusData(journal.status_data);
        saveStatusData(gson.toJson(status));
        SideeffectData sideeffects = new SideeffectData(journal.sideeffect_data);
        saveSideeffectsData(gson.toJson(sideeffects));
        BeverageData beverages = new BeverageData(journal.beverage_data);
        saveBeveragesData(gson.toJson(beverages));
    }

    public JournalData getJournal(){
        journal = new JournalData(events.event_data, status.status_data, sideeffects.sideeffect_data, beverages.beverage_data);
        return journal;
    }

    public LoginData getLoginData(){
        return loginData;
    }

    public Person getPerson(){
        return person;
    }

    public Patient getPatient(){
        return patient;
    }

    public InviteData getInvites(){
        return invites;
    }

    public EventData getEvents(){
        return events;
    }

    public HealthCareData getHealthcares(){
        return healthcares;
    }

    public BeverageData getBeverages(){
        return beverages;
    }

    public SideeffectData getSideeffects(){
        return sideeffects;
    }

    public HealthDataData getHealthData(){
        return healthData;
    }

    public StatusData getStatus(){
        return status;
    }


    public void createInvite(Invite newInvite){
        invites.invite_data.add(newInvite);
    }

    public void createEvents(Event newEvent){
        events.event_data.add(newEvent);
    }

    public void createHealthcare(HealthCare newHealthcare){
        healthcares.healthcare_data.add(newHealthcare);
    }

    public void createBeverages(Beverage newBeverage){
        beverages.beverage_data.add(newBeverage);
    }

    public void createSideeffects(Sideeffect newSideeffect){
        sideeffects.sideeffect_data.add(newSideeffect);
    }

    public void createHealthData(HealthData newHealthData){
        healthData.healthdata_data.add(newHealthData);
    }

    public void createStatus(Status newStatus){
        status.status_data.add(newStatus);
    }


    public void deleteEvent(int index){
        for (int i = 0; i < events.event_data.size(); i++){
            if (index == events.event_data.get(i).event_ID){
                events.event_data.remove(i);
            }
        }
    }

    public void deleteHealthcare(int index){
        for (int i = 0; i < healthcares.healthcare_data.size(); i++){
            if (index == healthcares.healthcare_data.get(i).healthcare_ID){
                healthcares.healthcare_data.remove(i);
            }
        }
    }

    public void deleteBeverage(int index){
        for (int i = 0; i < beverages.beverage_data.size(); i++) {
            if (index == beverages.beverage_data.get(i).beverage_ID) {
                beverages.beverage_data.remove(i);
            }
        }
    }

    public void deleteSideeffect(int index){
        for (int i = 0; i < sideeffects.sideeffect_data.size(); i++) {
            if (index == sideeffects.sideeffect_data.get(i).sideeffect_ID) {
                sideeffects.sideeffect_data.remove(i);
            }
        }
    }

    public void deleteHealthData(int index){
        for (int i = 0; i < healthData.healthdata_data.size(); i++) {
            if (index == healthData.healthdata_data.get(i).healthdata_ID) {
                healthData.healthdata_data.remove(i);
            }
        }
    }

    public void deleteStatus(int index){
        for (int i = 0; i < status.status_data.size(); i++) {
            if (index == status.status_data.get(i).status_ID) {
                status.status_data.remove(i);
            }
        }
    }


    public void updateEvent(Event updateEvent){
        for (int i = 0; i < events.event_data.size(); i++){
            if (updateEvent.event_ID == events.event_data.get(i).event_ID){
                events.event_data.set(i, updateEvent);
            }
        }
    }

    public void updateHealthcare(HealthCare updateHealthcare){
        for (int i = 0; i < healthcares.healthcare_data.size(); i++){
            if (updateHealthcare.healthcare_ID == healthcares.healthcare_data.get(i).healthcare_ID){
                healthcares.healthcare_data.set(i, updateHealthcare);
            }
        }
    }

    public void updateBeverage(Beverage updateBeverage){
        for (int i = 0; i < beverages.beverage_data.size(); i++) {
            if (updateBeverage.beverage_ID == beverages.beverage_data.get(i).beverage_ID) {
                beverages.beverage_data.set(i, updateBeverage);
            }
        }
    }

    public void updateSideeffect(Sideeffect updateSideeffect){
        for (int i = 0; i < sideeffects.sideeffect_data.size(); i++) {
            if (updateSideeffect.sideeffect_ID == sideeffects.sideeffect_data.get(i).sideeffect_ID) {
                sideeffects.sideeffect_data.set(i, updateSideeffect);
            }
        }
    }

    public void updateHealthData(HealthData updateHealthData){
        for (int i = 0; i < healthData.healthdata_data.size(); i++) {
            if (updateHealthData.healthdata_ID == healthData.healthdata_data.get(i).healthdata_ID) {
                healthData.healthdata_data.set(i, updateHealthData);
            }
        }
    }

    public void updateStatus(Status updateStatus){
        for (int i = 0; i < status.status_data.size(); i++) {
            if (updateStatus.status_ID == status.status_data.get(i).status_ID) {
                status.status_data.set(i, updateStatus);
            }
        }
    }

}
