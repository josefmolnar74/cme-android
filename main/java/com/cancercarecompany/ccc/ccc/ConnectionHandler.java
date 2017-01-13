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
    HealthDataData healthData;
    JournalData journal;
    QuestionData questions;
    ArticleData articles;

    public static final String MESSAGE_LOGIN = "login";
    public static final String MESSAGE_CREATE = "create";
    public static final String MESSAGE_READ = "read";
    public static final String MESSAGE_UPDATE = "update";
    public static final String MESSAGE_DELETE = "delete";

    public static final String CONTENT_PERSON = "person";
    public static final String CONTENT_PATIENT = "patient";
    public static final String CONTENT_INVITE = "invite";
    public static final String CONTENT_HEALTHCARE = "healthcare";
    public static final String CONTENT_CARETEAM = "careteam";
    public static final String CONTENT_EVENT = "event";
    public static final String CONTENT_STATUS = "status";
    public static final String CONTENT_SIDEEFFECT = "sideeffect";
    public static final String CONTENT_HEALTH_DATA = "healthdata";
    public static final String CONTENT_JOURNAL = "journal";
    public static final String CONTENT_QUESTION = "question";
    public static final String CONTENT_ARTICLE = "article";
    public static final String CONTENT_PASSWORD = "password";


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
            String server_url = "http://cancermeapp-cancerme.rhcloud.com/";
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
                                        patient = recievedPatient;
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
                                        offlineDataManager.saveEventsData(resultData);
                                        events = gson.fromJson(resultData, EventData.class);
                                        break;
                                    case CONTENT_STATUS:
                                          status = gson.fromJson(resultData, StatusData.class);
                                        break;
                                    case CONTENT_SIDEEFFECT:
                                        sideeffects = gson.fromJson(resultData, SideeffectData.class);
                                        break;
                                    case CONTENT_HEALTH_DATA:
                                        healthData = gson.fromJson(resultData, HealthDataData.class);
                                        break;
                                    case CONTENT_QUESTION:
                                        questions = gson.fromJson(resultData, QuestionData.class);
                                        break;
                                    case CONTENT_ARTICLE:
                                        articles = gson.fromJson(resultData, ArticleData.class);
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
                                        person = gson.fromJson(resultData, Person.class);
                                        break;
                                    case CONTENT_PATIENT:
//                                        Patient patient = gson.fromJson(resultData, Patient.class);
                                        break;
                                    case CONTENT_CARETEAM:
                                        CareTeamMember careTeam = gson.fromJson(resultData, CareTeamMember.class);
                                        for (int i = 0; i < patient.care_team.size(); i++) {
                                            if (patient.care_team.get(i).person_ID == careTeam.person_ID) {
                                                patient.care_team.get(i).relationship = careTeam.relationship;
                                                patient.care_team.get(i).admin = careTeam.admin;
                                                break;
                                            }
                                        }
                                        break;
                                    case CONTENT_HEALTHCARE:
//                                        healthcare = gson.fromJson(resultData, HealthCareData.class);
                                        break;
                                }
                                break;

                            case MESSAGE_DELETE:
                                switch (header.content){
                                    case CONTENT_PERSON:
//                                        person = gson.fromJson(resultData, Person.class);
                                        break;
                                    case CONTENT_PATIENT:
//                                        Patient patient = gson.fromJson(resultData, Patient.class);
                                        break;
                                    case CONTENT_CARETEAM:
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

    public void login (Person newUser){//
        if (checkConnection()){
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
        if (checkConnection()){
            Gson gson = new Gson();
            if (person == null) {
                person = new Person(newUser.person_ID, newUser.name, newUser.email,newUser.password,newUser.avatar, null);
            }
            String messageData = gson.toJson(person);
            sendMessage(MESSAGE_CREATE, CONTENT_PERSON, messageData);
        }
    }

    public void updateUser(Person newUser) {
        if (checkConnection()){
            Gson gson = new Gson();
            String messageData = gson.toJson(newUser);
            sendMessage(MESSAGE_UPDATE, CONTENT_PERSON, messageData);
        }
    }


    public void deleteUser(int userID){
        if (checkConnection()){
            String msgData = String.format("{\"person_ID\":\"%d\"}", userID);
            sendMessage(MESSAGE_DELETE, CONTENT_PERSON, msgData);
        } else {
//            offlineDataManager.deleteUser(userID);
        }
    }

    public void createPatient(Patient newPatient, String relationship) {
        if (checkConnection()){
            patient = newPatient;
            Gson gson = new Gson();
            String msgData = gson.toJson(newPatient);
            String msgRelationshipData = String.format("\"person_ID\":\"%d\",\"relationship\":\"%s\",\"admin\":%d,", person.person_ID, relationship, 1);
            msgData = new StringBuilder(msgData).insert(1, msgRelationshipData).toString();
            sendMessage(MESSAGE_CREATE, CONTENT_PATIENT, msgData);
        }
    }

    public void getPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_PATIENT, msgData);
        } else {
            //offline mode, get data from internal file
        }
    }

    public void updateCareTeamMember(CareTeamMember mCareTeamMember, int patientID) {
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(mCareTeamMember);
            String msgRelationshipData = String.format("\"patient_ID\":\"%d\",", patientID);
            msgData = new StringBuilder(msgData).insert(1, msgRelationshipData).toString();
            sendMessage(MESSAGE_UPDATE, CONTENT_CARETEAM, msgData);
        }
    }

    public void removeCareTeamMember(int patientID, int personID) {
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\", \"person_ID\":\"%d\"}", patientID, personID);
            sendMessage(MESSAGE_DELETE, CONTENT_CARETEAM, msgData);
        }
    }

    public void inviteCareTeamMember(Invite newInvite) {
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(newInvite);
            sendMessage(MESSAGE_CREATE, CONTENT_INVITE, msgData);
        }
    }

    public void findCareTeamInvite(String invitedEmail) {
        if (checkConnection()){
            invites = null; // reset any previous invite querys
            String msgData = String.format("{\"invited_email\":\"%s\"}", invitedEmail);
            sendMessage(MESSAGE_READ, CONTENT_INVITE, msgData);
        }
    }

    public void getInvitedCareTeamMembers(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_INVITE, msgData);
        } else {
            invites = offlineDataManager.getInvites();
        }
    }

    public void deleteCareTeamInvite(int inviteID){
        if (checkConnection()){
            String msgData = String.format("{\"invite_ID\":\"%d\"}", inviteID);
            sendMessage(MESSAGE_DELETE, CONTENT_INVITE, msgData);
        } else {
//            invites = offlineDataManager.deleteInvites();
        }
    }

    public void acceptCareTeamInvite() {
        if (checkConnection()){
            Invite invite = invites.invite_data.get(0); // Always accept first found invite
            invite.invite_accepted = 1;
            invite.person_ID = person.person_ID;
            Gson gson = new Gson();
            String msgData = gson.toJson(invite);
            sendMessage(MESSAGE_UPDATE, CONTENT_INVITE, msgData);
        }
    }

    public void createHealthcare(HealthCare healthcare){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(healthcare);
            sendMessage(MESSAGE_CREATE, CONTENT_HEALTHCARE, msgData);
        } else {
            offlineDataManager.createHealthcare(healthcare);
        }
    }

    public void getHealthcareForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_HEALTHCARE, msgData);
        } else {
            //offline mode, get data from internal file
            healthcare = offlineDataManager.getHealthcares();
        }
    }

    public void getHealthcare(int healthcareID){
        if (checkConnection()){
            String msgData = String.format("{\"healthcare_ID\":\"%d\"}", healthcareID);
            sendMessage(MESSAGE_READ, CONTENT_HEALTHCARE, msgData);
        }
    }

    public void updateHealthcare(HealthCare healthcare){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(healthcare);
            sendMessage(MESSAGE_UPDATE, CONTENT_HEALTHCARE, msgData);
        } else {
            offlineDataManager.updateHealthcare(healthcare);
        }
    }

    public void deleteHealthcare(int healthcareID){
        if (checkConnection()){
            String msgData = String.format("{\"healthcare_ID\":\"%d\"}", healthcareID);
            sendMessage(MESSAGE_DELETE, CONTENT_HEALTHCARE, msgData);
        } else {
            offlineDataManager.deleteHealthcare(healthcareID);
        }
    }

    public void createEvent(Event event){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(event);
            sendMessage(MESSAGE_CREATE, CONTENT_EVENT, msgData);
        } else {
            offlineDataManager.createEvents(event);
        }
    }

    public void createQuestion(Question question){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(question);
            sendMessage(MESSAGE_CREATE, CONTENT_QUESTION, msgData);
        } else {
//            offlineDataManager.createEvents(event);
        }
    }

    public void getQuestionsForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_QUESTION, msgData);
        } else {
            //offline mode, get data from internal file
            offlineDataManager.getSideeffects();
        }
    }

    public void getArticles(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_ARTICLE, msgData);
        } else {
            //offline mode, get data from internal file
            offlineDataManager.getSideeffects();
        }
    }

    public void getEventsForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_EVENT, msgData);
        } else {
            //offline mode, get data from internal file
           events = offlineDataManager.getEvents();
        }
    }

    public void updateEvent(Event event){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(event);
            sendMessage(MESSAGE_UPDATE, CONTENT_EVENT, msgData);
        } else {
            offlineDataManager.updateEvent(event);
        }
    }

    public void deleteEvent(int eventID){
        if (checkConnection()){
            String msgData = String.format("{\"event_ID\":\"%d\"}", eventID);
            sendMessage(MESSAGE_DELETE, CONTENT_EVENT, msgData);
        } else {
            offlineDataManager.deleteEvent(eventID);
        }
    }

    public void createStatus(Status status){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(status);
            sendMessage(MESSAGE_CREATE, CONTENT_STATUS, msgData);
        } else {
            offlineDataManager.createStatus(status);
        }
    }

    public void getStatusForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_STATUS, msgData);
        } else {
            //offline mode, get data from internal file
            offlineDataManager.getStatus();
        }
    }

    public void updateStatus(Status status){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(status);
            sendMessage(MESSAGE_UPDATE, CONTENT_STATUS, msgData);
        } else {
            offlineDataManager.updateStatus(status);
        }
    }

    public void deleteStatus(int statusID){
        if (checkConnection()){
            String msgData = String.format("{\"status_ID\":\"%d\"}", statusID);
            sendMessage(MESSAGE_DELETE, CONTENT_STATUS, msgData);
        } else {
            offlineDataManager.deleteStatus(statusID);
        }
    }

    public void createHealthData(HealthData healthData){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(healthData);
            sendMessage(MESSAGE_CREATE, CONTENT_HEALTH_DATA, msgData);
        } else {
            offlineDataManager.createHealthData(healthData);
        }
    }

    public void getHealthDataForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_HEALTH_DATA, msgData);
        } else {
            //offline mode, get data from internal file
            offlineDataManager.getHealthData();
        }
    }

    public void updateHealthData(HealthData healthData){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(healthData);
            sendMessage(MESSAGE_UPDATE, CONTENT_HEALTH_DATA, msgData);
        } else {
            offlineDataManager.updateHealthData(healthData);
        }
    }


    public void deleteHealthData(int healthDataID){
        if (checkConnection()){
            String msgData = String.format("{\"healthdata_ID\":\"%d\"}", healthDataID);
            sendMessage(MESSAGE_DELETE, CONTENT_HEALTH_DATA, msgData);
        } else {
            offlineDataManager.deleteHealthData(healthDataID);
        }
    }

    public void createSideeffect(Sideeffect sideeffect){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(sideeffect);
            sendMessage(MESSAGE_CREATE, CONTENT_SIDEEFFECT, msgData);
        } else {
            offlineDataManager.createSideeffects(sideeffect);
        }
    }

    public void getSideeffectForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_SIDEEFFECT, msgData);
        } else {
            //offline mode, get data from internal file
            offlineDataManager.getSideeffects();
        }
    }

    public void updateSideeffect(Sideeffect sideeffect){
        if (checkConnection()){
            Gson gson = new Gson();
            String msgData = gson.toJson(sideeffect);
            sendMessage(MESSAGE_UPDATE, CONTENT_SIDEEFFECT, msgData);
        } else {
            offlineDataManager.updateSideeffect(sideeffect);
        }
    }

    public void deleteSideeffect(int sideeffectID){
        if (checkConnection()){
            String msgData = String.format("{\"sideeffect_ID\":\"%d\"}", sideeffectID);
            sendMessage(MESSAGE_DELETE, CONTENT_SIDEEFFECT, msgData);
        } else {
            offlineDataManager.deleteSideeffect(sideeffectID);
        }
    }

    public void getJournalForPatient(int patientID){
        if (checkConnection()){
            String msgData = String.format("{\"patient_ID\":\"%d\"}", patientID);
            sendMessage(MESSAGE_READ, CONTENT_JOURNAL, msgData);
        } else {
            //offline mode, get data from internal file
            journal =  offlineDataManager.getJournal();
        }
    }

    public void updatePassword(int personID, String oldPassword, String  newPassword){
        if (checkConnection()){
            String msgData = String.format("{\"person_ID\":\"%d\",\"old_password\":\"%s\",\"new_password\":\"%s\"}", personID, oldPassword, newPassword);
            sendMessage(MESSAGE_UPDATE, CONTENT_PASSWORD, msgData);
        }
    }

    private boolean checkConnection(){
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected =  activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
