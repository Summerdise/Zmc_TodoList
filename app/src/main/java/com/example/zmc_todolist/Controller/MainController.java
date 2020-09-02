package com.example.zmc_todolist.Controller;

import android.content.Context;


import com.example.zmc_todolist.Model.RemoteData.UserInformation;
import com.example.zmc_todolist.View.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainController {
    MainActivity mainActivity;
    Context context;
    UserInformation userInformation;

    public MainController(MainActivity mainActivity, Context context) {
        this.mainActivity = mainActivity;
        this.context = context;
    }

    public void findUserInformation(String url) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            final String result = Objects.requireNonNull(response.body()).string();
            Gson gson = new Gson();
            userInformation = gson.fromJson(result, UserInformation.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String src) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(src.getBytes());
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result.append(temp);
        }
        System.out.println(result.toString());
        return result.toString();
    }

    public boolean isUserNameExist(String input) {
        return input.equals(userInformation.getName());
    }

    public boolean isPasswordExist(String input) throws Exception {
        String md5Input = encrypt(input);
        return md5Input.equals(userInformation.getPassword());
    }
}
