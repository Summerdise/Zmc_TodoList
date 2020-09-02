package com.example.zmc_todolist.Controller;

import android.app.Notification;

import android.app.NotificationChannel;

import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.Context;

import android.content.Intent;

import android.os.Build;


import androidx.core.app.NotificationCompat;

import androidx.core.app.NotificationManagerCompat;

import com.example.zmc_todolist.R;
import com.example.zmc_todolist.View.TasksActivity;

public class MyNotification {
    private NotificationManagerCompat notificationManager;
    private static final String CHANNEL_ID = "notice";
    private static final String CHANNEL_NAME = "noFinishNotice";

    public MyNotification(Context context) {
        this.notificationManager = NotificationManagerCompat.from(context);
    }


    public void createNotification(Context context, String title, String detail, int id) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        Intent intent = new Intent(context, TasksActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = builder.setSmallIcon(R.drawable.todo_list_logo)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title + "未完成")
                .setContentText(detail)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(id, notification);
    }


    public void cancelNotificationById(int id) {
        notificationManager.cancel(id);
    }

}
