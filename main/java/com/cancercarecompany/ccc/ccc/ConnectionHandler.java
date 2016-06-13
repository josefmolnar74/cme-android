package com.cancercarecompany.ccc.ccc;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by 23047371 on 2016-06-09.
 */
public class ConnectionHandler {
    private static ConnectionHandler ourInstance = new ConnectionHandler();

    public io.socket.client.Socket socket;

    String result;
    String function;
    Boolean socketBusy = false;

    // To be moved to data management Singleton class TBD
    public Lcl_work_area lcl;
    public Person person;
    public Patient patient;
    JSONObject findUser;

    public static final String MESSAGE_LOGIN = "login";
    public static final String MESSAGE_CREATE = "create";
    public static final String MESSAGE_READ = "read";
    public static final String MESSAGE_UPDATE = "update";
    public static final String MESSAGE_DELETE = "delete";

    public static final String CONTENT_PERSON = "person";
    public static final String CONTENT_PATIENT = "patient";
    public static final String CONTENT_EVENT = "event";
    public static final String CONTENT_STATUS = "status";

    public static ConnectionHandler getInstance() {
        return ourInstance;
    }

    private ConnectionHandler() {
        // Constructor
        initializeSocket();
    }

    private void sendMessage(String function, String content, String message){
        if (socket == null){
            initializeSocket();
        }

        int message_ID = 0;
        String messageHeader = String.format("\"message_ID\": \"%d\", \"function\": \"%s\", \"content\": \"%s\", ",message_ID, function, content);
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
                    String resultData = result.substring(index+1);
                    MsgHeader header = gson.fromJson(resultHeader, MsgHeader.class);
                    if (header.errorCode == ""){
                        switch (header.function){
                            case MESSAGE_LOGIN:
                                person = gson.fromJson(resultData, Person.class);
                                if (person.patient != null){
                                    patient = person.patient.get(0);
                                }
                                lcl = gson.fromJson(resultData, Lcl_work_area.class);
                                break;

                            case MESSAGE_CREATE:
                                switch (header.content){
                                    case CONTENT_PERSON:
                                        Person recievedPerson = gson.fromJson(resultData, Person.class);
                                        person.person_ID = recievedPerson.person_ID;
                                        break;
                                    case CONTENT_PATIENT:
                                        Patient recievedPatient = gson.fromJson(resultData, Patient.class);
                                        patient.patient_ID = recievedPatient.patient_ID;
                                        break;
                                }
                                break;
                            case MESSAGE_READ:
                                switch (header.content){
                                    case CONTENT_PERSON:
                                        person = gson.fromJson(resultData, Person.class);
                                        break;
                                    case CONTENT_PATIENT:
                                        Patient patient = gson.fromJson(resultData, Patient.class);
                                        break;
                                }
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
        function = "login";
        Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        sendMessage(MESSAGE_LOGIN, CONTENT_PERSON, newUserString);
    }

    public void createUser(Person newUser) {
        Gson gson = new Gson();
        if (person == null) {
            person = new Person(newUser.person_ID, newUser.first_name, newUser.last_name, newUser.email,newUser.password, null);
        }
        String messageData = gson.toJson(person);
        sendMessage(MESSAGE_CREATE, CONTENT_PERSON, messageData);
    }

    public void editUser(Person newUser) {
        Gson gson = new Gson();
        String messageData = gson.toJson(person);
        sendMessage(MESSAGE_UPDATE, CONTENT_PERSON, messageData);
    }

    public void deleteUser(Person newUser) {
        Gson gson = new Gson();
        String msgData = gson.toJson(newUser);
        sendMessage(MESSAGE_DELETE, CONTENT_PERSON, msgData);
    }

    public void findUser(String email) {
        Gson gson = new Gson();
        String msgData = String.format("{\"email\":\"%s\"}", email);
        sendMessage(MESSAGE_READ, CONTENT_PERSON, msgData);
    }

    public void createPatient(Patient newPatient, String relationship) {
        patient = newPatient;
        Gson gson = new Gson();
        String msgData = gson.toJson(newPatient);
        String msgRelationshipData = String.format("\"person_ID\":\"%d\",\"relationship\":\"%s\",\"admin\":%d,", person.person_ID, relationship, 1);
        msgData = new StringBuilder(msgData).insert(1, msgRelationshipData).toString();
        sendMessage(MESSAGE_CREATE, CONTENT_PATIENT, msgData);
    }
}
