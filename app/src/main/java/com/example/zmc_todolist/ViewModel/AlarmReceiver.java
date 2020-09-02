package com.example.zmc_todolist.ViewModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class AlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "NOTIFICATION")) {
            String title = intent.getStringExtra("title");
            int id = intent.getIntExtra("id", 0);
            String detail = intent.getStringExtra("detail");
            MyNotification myNotification = new MyNotification(context);
            myNotification.createNotification(context, title,detail,id);
        }
    }

}

