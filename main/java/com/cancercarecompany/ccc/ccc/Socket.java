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

    String server_url;
    public io.socket.client.Socket mSocket;
    String result;
    String function;

   public Lcl_work_area lcl;

    JSONObject findUser;

    {
        try {
            server_url = "http://cancermeapp-cancerme.rhcloud.com";
            mSocket = IO.socket(server_url);
            mSocket.connect();
            mSocket.open();
            mSocket.on("data", new Emitter.Listener() {
                @Override
                public void call(final Object... args) {
                    if (function == "login") {
                        result = args[0].toString();
                        System.out.println(result);
                        Gson gson = new Gson();

                        result = result.substring(2);
                        lcl = gson.fromJson(result, Lcl_work_area.class);

                    }

                    }

            });


        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }


    }

    public io.socket.client.Socket createUser(Person newUser) {
        function = "create";
        Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        mSocket.emit("create", newUserString);


        return mSocket;
    }

    public io.socket.client.Socket login (Person newUser){
        function = "login";
        findUser = new JSONObject();
        LoginActivity loginActivity = new LoginActivity();

        final Gson gson = new Gson();
        String newUserString = gson.toJson(newUser);
        mSocket.emit("login", newUserString);

        return mSocket;
    }


    public void goToManageCareTeam(){

        Intent myIntent = new Intent(this, ManageCareTeamActivity.class);
        Gson gson = new Gson();
        String jsonPerson = gson.toJson(lcl);

        myIntent.putExtra("Person", jsonPerson);
        startActivity(myIntent);

    }




}


