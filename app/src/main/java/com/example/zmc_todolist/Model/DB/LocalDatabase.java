package com.example.zmc_todolist.Model.DB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.zmc_todolist.Controller.Util.Converters;

@TypeConverters(value = {Converters.class})
@Database(entities = {Task.class}, version = 1, exportSchema = false)

public abstract class LocalDatabase extends RoomDatabase {
    public static LocalDatabase localDatabase;

    public static LocalDatabase getInstance(Context context){
        if (localDatabase == null){
            synchronized (LocalDatabase.class){
                if (localDatabase == null){
                    localDatabase = Room.databaseBuilder( context,LocalDatabase.class,"tasks").allowMainThreadQueries().build();
                }
            }
        }
        return localDatabase;
    }
    public abstract TaskDao taskDao();
}

