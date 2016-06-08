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

    JSONObject findUser;

    public static final String MESSAGE_LOGIN = "login";
    public static final String MESSAGE_CREATE = "create";
    public static final String MESSAGE_GET = "read";
    public static final String MESSAGE_UPDATE = "update";
    public static final String MESSAGE_DELETE = "delete";

    public static final String CONTENT_PERSON = "person";
    public static final String CONTENT_PATIENT = "patient";
    public static final String CONTENT_EVENT = "event";
    public static final String CONTENT_STATUS = "status";

    private void sendMessage(String serverFunction, String messageContent, String messageData){
        if (mSocket == null){
            initializeSocket();
        }
        function = serverFunction;
        String content = messageContent;
        String messageHeader = "\"content\": \"" + messageContent + "\", ";
        messageData = new StringBuilder(messageData).insert(1, messageHeader).toString();
        mSocket.emit("login", messageData);
    };

    private String RecieveMessage(String data){

        return data;
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
                    result = args[0].toString();
                    System.out.println(result);
                    switch (function){
                        case "login":
                            int index = result.indexOf("}");
                            result = result.substring(index+1);
                            Gson gson = new Gson();
                            lcl = gson.fromJson(result, Lcl_work_area.class);
                            break;

                        case "create":
                            break;
                    }
                }
            });


        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    public void createUser(Person newUser) {
        Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        newUserString = new StringBuilder(newUserString).insert(1, "\"content\": \"person\", ").toString();
        sendMessage(MESSAGE_CREATE, CONTENT_PERSON, newUserString);
    }

    public void login (Person newUser){
        final Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        sendMessage(MESSAGE_LOGIN, CONTENT_PERSON, newUserString);
    }


    public void goToManageCareTeam(){

        Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
        Gson gson = new Gson();
        String jsonPerson = gson.toJson(lcl);

        myIntent.putExtra("Person", jsonPerson);
        startActivity(myIntent);

    }




}


