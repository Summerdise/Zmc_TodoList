package com.example.zmc_todolist.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.zmc_todolist.Controller.Util.AlarmReceiver;
import com.example.zmc_todolist.Controller.Util.MyNotification;
import com.example.zmc_todolist.Model.DB.LocalDatabase;
import com.example.zmc_todolist.View.CreateTaskActivity;

import java.util.Date;

public class CreateTaskController {
    CreateTaskActivity createTaskActivity;
    Context context;
    LocalDatabase database;

    public CreateTaskController(CreateTaskActivity createTaskActivity, Context context) {
        this.createTaskActivity = createTaskActivity;
        this.context = context;
        this.database = LocalDatabase.getInstance(createTaskActivity);
    }

    public LocalDatabase getDatabase() {
        return database;
    }

    public void addNotification(int id, String title, String detail, Date date) {
        if (date.getTime() > System.currentTimeMillis()) {
            Intent intent = new Intent(createTaskActivity, AlarmReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("id", id);
            intent.putExtra("detail", detail);
            intent.setAction("NOTIFICATION");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(createTaskActivity, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) createTaskActivity.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        }
    }


    public void cancelNotification(int id) {
        MyNotification myNotification = new MyNotification(createTaskActivity);
        myNotification.cancelNotificationById(id);
    }
}
