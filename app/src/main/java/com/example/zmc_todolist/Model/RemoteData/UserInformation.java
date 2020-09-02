package com.example.zmc_todolist.Model.RemoteData;

import androidx.room.Entity;

@Entity
public class UserInformation {
    private int id;
    private String name;
    private String password;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
