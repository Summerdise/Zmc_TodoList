package com.example.zmc_todolist;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(value = {Converters.class})
@Database(entities = {Task.class}, version = 1, exportSchema = false)

public abstract class LocalDatabase extends RoomDatabase {
    public static LocalDatabase localDatabase;

    public static LocalDatabase getInstance(Context context){
        if (localDatabase == null){
            synchronized (LocalDatabase.class){
                if (localDatabase == null){
                    localDatabase = Room.databaseBuilder( context,LocalDatabase.class,"tasks").build();
                }
            }
        }
        return localDatabase;
    }
    public abstract TaskDao taskDao();
}

