package com.cancercarecompany.ccc.ccc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

/**
 * Created by robinlarsson on 25/04/16.
 */
public class Socket extends AppCompatActivity {

    public io.socket.client.Socket mSocket;

    String result;
    String function;

    public Lcl_work_area lcl;
    public Person person;
    public Patient patient;

    JSONObject findUser;

    public static final String MESSAGE_LOGIN = "login";
    public static final String MESSAGE_CREATE = "create";
    public static final String MESSAGE_GET = "get";
    public static final String MESSAGE_UPDATE = "update";
    public static final String MESSAGE_DELETE = "delete";
    public static final String MESSAGE_FIND = "find";

    public static final String CONTENT_PERSON = "person";
    public static final String CONTENT_PATIENT = "patient";
    public static final String CONTENT_EVENT = "event";
    public static final String CONTENT_STATUS = "status";

    private void sendMessage(String function, String content, String message){
        if (mSocket == null){
            initializeSocket();
        }

        int message_ID = 0;
        String messageHeader = String.format("\"message_ID\": \"%d\", \"function\": \"%s\", \"content\": \"%s\", ",message_ID, function, content);
        message = new StringBuilder(message).insert(1, messageHeader).toString();
        mSocket.emit(function, message);
    };

    private void initializeSocket(){
        try {
            String server_url = "http://cancermeapp-cancerme.rhcloud.com";
            mSocket = IO.socket(server_url);
            mSocket.connect();
            mSocket.open();
            mSocket.on("data", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    Gson gson = new Gson();
                    String result = args[0].toString();
                    int index = result.indexOf("}");
                    String resultHeader = result.substring(0, index+1);
                    String resultData = result.substring(index+1);
                    MsgHeader header = gson.fromJson(resultHeader, MsgHeader.class);
                    switch (header.function){
                        case MESSAGE_LOGIN:
                            lcl = gson.fromJson(resultData, Lcl_work_area.class);
                            break;

                        case MESSAGE_CREATE:
                            switch (header.content){
                                case CONTENT_PERSON:
                                    Person recievedPerson = gson.fromJson(resultData, Person.class);
                                    person.person_ID = recievedPerson.person_ID;
                                    break;
                                case CONTENT_PATIENT:
                                    break;
                            }
                            break;
                        case MESSAGE_GET:
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
        String newUserString = gson.toJson(newUser);
        sendMessage(MESSAGE_CREATE, CONTENT_PERSON, newUserString);
    }

    public void editUser(Person newUser) {
        Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        sendMessage(MESSAGE_UPDATE, CONTENT_PERSON, newUserString);
    }

    public void deleteUser(Person newUser) {
        Gson gson = new Gson();
        String msgData = gson.toJson(newUser);
        sendMessage(MESSAGE_DELETE, CONTENT_PERSON, msgData);
    }

    public void findUser(String email) {
        Gson gson = new Gson();
        String msgData = String.format("{\"email\":\"%s\"}", email);
        sendMessage(MESSAGE_GET, CONTENT_PERSON, msgData);
    }

}


