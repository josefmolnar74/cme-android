package com.cancercarecompany.ccc.ccc;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by 23047371 on 2016-06-09.
 */
public class ConnectionHandler {
    private static ConnectionHandler ourInstance = new ConnectionHandler();
    OfflineDataManager offlineDataManager;

    public io.socket.client.Socket socket;

    String result;
    String function;
    Boolean socketBusy = false;

    // To be moved to data management Singleton class TBD
    LoginData loginData;
    Person person;
    Patient patient;
    InviteData invites;
    HealthCareData healthcare;
    EventData events;
    StatusData status;
    SideeffectData sideeffects;
    BeverageData beverages;
    JournalData journal;

    public static final String MESSAGE_LOGIN = "login";
    public static final String MESSAGE_CREATE = "create";
    public static final String MESSAGE_READ = "read";
    public static final String MESSAGE_UPDATE = "update";
    public static final String MESSAGE_DELETE = "delete";

    public static final String CONTENT_PERSON = "person";
    public static final String CONTENT_PATIENT = "patient";
    public static final String CONTENT_INVITE = "invite";
    public static final String CONTENT_HEALTHCARE = "healthcare";
    public static final String CONTENT_CARE_TEAM = "careteam ";
    public static final String CONTENT_EVENT = "event";
    public static final String CONTENT_STATUS = "status";
    public static final String CONTENT_SIDEEFFECT = "sideeffect";
    public static final String CONTENT_BEVERAGE = "beverage";
    public static final String CONTENT_JOURNAL = "journal";

    public static ConnectionHandler getInstance() {
        return ourInstance;
    }

    private ConnectionHandler() {
        // Constructor
        offlineDataManager = OfflineDataManager.getInstance();
        initializeSocket();
    }

    private void sendMessage(String function, String content, String message){
        if (socket == null){
            initializeSocket();
        }

        int message_ID = 0;
        String messageHeader = "";
        messageHeader += String.format("\"message_ID\": \"%d\", ", message_ID);
        messageHeader += String.format("\"function\": \"%s\", ", function);
        messageHeader += String.format("\"content\": \"%s\", ", content);

// when I want to change the send message, right now I leave it this way
//        MsgHeader msgHeader= new MsgHeader(0, function, content, "");
//        Gson gson = new Gson();
//        message = new StringBuilder(message).insert(0, gson.toJson(msgHeader)).toString();
        message = new StringBuilder(message).insert(1, messageHeader).toString();
        socketBusy = true;
        socket.emit(function, message);
    };

    private void initializeSocket(){
        try {
            String server_url = "http://cancermeapp-cancerme.rhcloud.com";
//            String server_url = "http://127.0.0.1:8083/";
            socket = IO.socket(server_url);
            socket.connect();
            socket.open();
            socket.on("data", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    Gson gson = new Gson();
                    String result = args[0].toString();
                    int index = result.indexOf("}");
                    String resultHeader = result.substring(0, index+1);
                    String resultData = result.substring(index+2);
                    MsgHeader header = gson.fromJson(resultHeader, MsgHeader.class);
                    if ((header.errorCode == "") && (resultData != null)){
                        switch (header.function){
                            case MESSAGE_LOGIN:
                                person = gson.fromJson(resultData, Person.class);
                                offlineDataManager.savePersonData(resultData);
                                if (person.patient != null){
                                    patient = person.patient.get(0);
                                    offlineDataManager.savePatientData(gson.toJson(patient));
                                }
                                loginData = gson.fromJson(resultData, LoginData.class);
                                offlineDataManager.saveLoginData(resultData);
                                break;

                            case MESSAGE_CREATE:
                                switch (header.content){
                                    case CONTENT_PERSON:
                                        Person createdPerson =  gson.fromJson(resultData, Person.class);
                                        person.person_ID = createdPerson.person_ID; // update ID
                                        break;
                                    case CONTENT_PATIENT:
                                        Patient recievedPatient = gson.fromJson(resultData, Patient.class);
                                        patient.patient_ID = recievedPatient.patient_ID;
                                        break;
                                    case CONTENT_HEALTHCARE:
                                        // Must find a solution how to handle reponse message when creating and updating
//                                        HealthCareData createdHealthcare =  gson.fromJson(resultData, HealthCareData.class);
//                                        person.person_ID = createdPerson.person_ID; // update ID
                                        break;
                                    case CONTENT_EVENT:
//                                        event = gson.fromJson(resultData, EventData.class);
                                        break;
                                }
                                break;
                            case MESSAGE_READ:
                                switch (header.content){
                                    case CONTENT_PERSON:
                                        person = gson.fromJson(resultData, Person.class);
                                        offlineDataManager.savePersonData(resultData);
                                        break;
                                    case CONTENT_PATIENT:
                                        patient = gson.fromJson(resultData, Patient.class);
                                        offlineDataManager.savePatientData(resultData);
                                        break;
                                    case CONTENT_INVITE:
                                        invites = gson.fromJson(resultData, InviteData.class);
                                        offlineDataManager.saveInviteData(resultData);
                                        break;
                                    case CONTENT_HEALTHCARE:
                                        healthcare = gson.fromJson(resultData, HealthCareData.class);
                                        offlineDataManager.saveHealthCareData(resultData);
                                        break;
                                    case CONTENT_EVENT:
                                        offlineDataManager.saveEventData(resultData);
                                        events = gson.fromJson(resultData, EventData.class);
                                        break;
                                    case CONTENT_STATUS:
                                        status = gson.fromJson(resultData, StatusData.class);
                                        break;
                                    case CONTENT_SIDEEFFECT:
                                        sideeffects = gson.fromJson(resultData, SideeffectData.class);
                                        break;
                                    case CONTENT_BEVERAGE:
                                        beverages = gson.fromJson(resultData, BeverageData.class);
                                        break;

                                    case CONTENT_JOURNAL:
                                        journal = gson.fromJson(resultData, JournalData.class);
                                        offlineDataManager.saveJournalData(resultData);
                                        break;
                                }
                                break;

                            case MESSAGE_UPDATE:
                                switch (header.content){
                                    case CONTENT_PERSON:
//                                        person = gson.fromJson(resultData, Person.class);
                                        break;
                                    case CONTENT_PATIENT:
//                                        Patient patient = gson.fromJson(resultData, Patient.class);
                                        break;
                                    case CONTENT_CARE_TEAM:
//                                        CareTeamMember careTeam = gson.fromJson(resultData, CareTeamMember.class);
                                        break;
                                    case CONTENT_HEALTHCARE:
//                                        healthcare = gson.fromJson(resultData, HealthCareData.class);
                                        break;
                                }
                                break;

                        }

                    }
                    // error code handling
                    else {
                        switch (header.errorCode){
                            case "not_found":
                                person = null;
                                patient = null;
                                break;
                            case "login_failed":
                                break;
                            case "already_exists":
                                person = null;
                                patient = null;
                                break;
                        }
                    }
                    socketBusy = false;
                }
            });


        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void login (Person newUser){
        if (checkConnectivity()){
            function = "login";
            Gson gson = new Gson();
            String newUserString = gson.toJson(newUser);
            sendMessage(MESSAGE_LOGIN, CONTENT_PERSON, newUserString);
        } else {
            //offline mode, get data from internal file
            loginData = offlineDataManager.getLoginData();
            person = offlineDataManager.getPerson();
            patient = offlineDataManager.getPatient();
        }
    }

    public void createUser(Person newUser) {
        if (checkConnectivity()){
            Gson gson = new Gson();
            if (person == null) {
                person = new Person(newUser.person_ID, newUser.first_name, newUser.last_name, newUser.email,newUser.password,newUser.avatar, null);
            }
            String messageData = gson.toJson(person);
            sendMessage(MESSAGE_CREATE, CONTENT_PERSON, messageData);
        }
    }

    public void updateUser(Person newUser) {
        if (checkConnectivity()){
            Gson gson = new Gson();
            String messageData = gson.toJson(person);
            sendMessage(MESSAGE_UPDATE, CONTENT_PERSON, messageData);
        }
    }

    public void deleteUser(Person newUser) {
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(newUser);
            sendMessage(MESSAGE_DELETE, CONTENT_PERSON, msgData);
        }
    }

    public void createPatient(Patient newPatient, String relationship) {
        if (checkConnectivity()){
            patient = newPatient;
            Gson gson = new Gson();
            String msgData = gson.toJson(newPatient);
            String msgRelationshipData = String.format("\"person_ID\":\"%d\",\"relationship\":\"%s\",\"admin\":%d,", person.person_ID, relationship, 1);
            msgData = new StringBuilder(msgData).insert(1, msgRelationshipData).toString();
            sendMessage(MESSAGE_CREATE, CONTENT_PATIENT, msgData);
        }
    }

    public void getPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_PATIENT, msgData);
        } else {
            //offline mode, get data from internal file
        }
    }

    public void createCareTeamMember(CareTeamMember newCareTeamMember, int patientID) {
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(newCareTeamMember);
            //Create patient with existing patient_ID only creates new care team junction
            sendMessage(MESSAGE_CREATE, CONTENT_PATIENT, msgData);
        }
    }

    public void inviteCareTeamMember(Invite newInvite) {
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(newInvite);
            sendMessage(MESSAGE_CREATE, CONTENT_INVITE, msgData);
        }
    }

    public void findCareTeamInvite(String invitedEmail) {
        if (checkConnectivity()){
            invites = null; // reset any previous invite querys
            String msgData = String.format("{\"invited_email\":\"%s\"}", invitedEmail);
            sendMessage(MESSAGE_READ, CONTENT_INVITE, msgData);
        }
    }

    public void getInvitedCareTeamMembers(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_INVITE, msgData);
        } else {
            invites = offlineDataManager.getInvites();
        }
    }

    public void acceptCareTeamInvite() {
        if (checkConnectivity()){
            Invite invite = invites.invite_data.get(0); // Always accept first found invite
            invite.invite_accepted = 1;
            invite.person_ID = person.person_ID;
            Gson gson = new Gson();
            String msgData = gson.toJson(invite);
            sendMessage(MESSAGE_UPDATE, CONTENT_INVITE, msgData);
        }
    }

    public void createHealthcare(HealthCare healthcare){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(healthcare);
            sendMessage(MESSAGE_CREATE, CONTENT_HEALTHCARE, msgData);
        }
    }

    public void getHealthcareForPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_HEALTHCARE, msgData);
        } else {
            //offline mode, get data from internal file
            healthcare = offlineDataManager.getHealthcare(patientID);
        }
    }

    public void getHealthcare(int healthcareID){
        if (checkConnectivity()){
            String msgData = String.format("{\"healthcare_ID\":\"%d\"}", healthcareID);
            sendMessage(MESSAGE_READ, CONTENT_HEALTHCARE, msgData);
        }
    }

    public void updateHealthcare(HealthCare healthcare){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(healthcare);
            sendMessage(MESSAGE_UPDATE, CONTENT_HEALTHCARE, msgData);
        }
    }

    public void deleteHealthcare(int healthcareID){
        if (checkConnectivity()){
            String msgData = String.format("{\"healthcare_ID\":\"%d\"}", healthcareID);
            sendMessage(MESSAGE_DELETE, CONTENT_HEALTHCARE, msgData);
        }
    }

    public void createEvent(Event event){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(event);
            sendMessage(MESSAGE_CREATE, CONTENT_EVENT, msgData);
        }
    }

    public void getEventsForPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_EVENT, msgData);
        } else {
            //offline mode, get data from internal file
           events = offlineDataManager.getEvents(patientID);
        }
    }

    public void updateEvent(Event event){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(event);
            sendMessage(MESSAGE_UPDATE, CONTENT_EVENT, msgData);
        }
    }

    public void deleteEvent(int eventID){
        if (checkConnectivity()){
            String msgData = String.format("{\"event_ID\":\"%d\"}", eventID);
            sendMessage(MESSAGE_DELETE, CONTENT_EVENT, msgData);
        }
    }

    public void createStatus(Status status){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(status);
            sendMessage(MESSAGE_CREATE, CONTENT_STATUS, msgData);
        }
    }

    public void getStatusForPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_STATUS, msgData);
        } else {
            //offline mode, get data from internal file
//            offlineDataManager.getStatus(patientID);
        }
    }

    public void updateStatus(Status status){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(status);
            sendMessage(MESSAGE_UPDATE, CONTENT_STATUS, msgData);
        }
    }

    public void deleteStatus(int statusID){
        if (checkConnectivity()){
            String msgData = String.format("{\"status_ID\":\"%d\"}", statusID);
            sendMessage(MESSAGE_DELETE, CONTENT_STATUS, msgData);
        }
    }

    public void createSideeffect(Sideeffect sideeffect){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(sideeffect);
            sendMessage(MESSAGE_CREATE, CONTENT_SIDEEFFECT, msgData);
        }
    }

    public void getSideeffectForPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_SIDEEFFECT, msgData);
        } else {
            //offline mode, get data from internal file
//            offlineDataManager.getSideeffects(patientID);
        }
    }

    public void updateSideeffect(Sideeffect sideeffect){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(status);
            sendMessage(MESSAGE_UPDATE, CONTENT_SIDEEFFECT, msgData);
        }
    }

    public void deleteSideeffect(int sideeffectID){
        if (checkConnectivity()){
            String msgData = String.format("{\"sideeffect_ID\":\"%d\"}", sideeffectID);
            sendMessage(MESSAGE_DELETE, CONTENT_SIDEEFFECT, msgData);
        }
    }

    public void createBeverage(Beverage beverage){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(beverage);
            sendMessage(MESSAGE_CREATE, CONTENT_BEVERAGE, msgData);
        }
    }

    public void getBeveragesForPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_BEVERAGE, msgData);
        } else {
            //offline mode, get data from internal file
//            offlineDataManager.getBeverage(patientID);
        }
    }

    public void updateBeverage(Beverage beverage){
        if (checkConnectivity()){
            Gson gson = new Gson();
            String msgData = gson.toJson(beverage );
            sendMessage(MESSAGE_UPDATE, CONTENT_BEVERAGE, msgData);
        }
    }

    public void deleteBeverage(int beverageID){
        if (checkConnectivity()){
            String msgData = String.format("{\"beverage_ID\":\"%d\"}", beverageID);
            sendMessage(MESSAGE_DELETE, CONTENT_BEVERAGE, msgData);
        }
    }

    public void getJournalForPatient(int patientID){
        if (checkConnectivity()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_JOURNAL, msgData);
        } else {
            //offline mode, get data from internal file
            journal =  offlineDataManager.getJournal(patientID);
        }
    }

    private boolean checkConnectivity(){
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
