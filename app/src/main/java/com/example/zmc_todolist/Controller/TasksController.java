package com.example.zmc_todolist.Controller;

import android.content.Context;

import com.example.zmc_todolist.Model.DB.LocalDatabase;
import com.example.zmc_todolist.Model.DB.Task;
import com.example.zmc_todolist.View.TasksActivity;

import java.util.List;

public class TasksController {
    TasksActivity tasksActivity;
    Context context;
    LocalDatabase database;


    public TasksController(TasksActivity tasksActivity, Context context) {
        this.tasksActivity = tasksActivity;
        this.context = context;
        database = LocalDatabase.getInstance(tasksActivity);
    }

    public LocalDatabase getDatabase() {
        return database;
    }

    public List<Task> getTaskList() {
        List<Task> list = database.taskDao().getCompleted();
        list.addAll(database.taskDao().getNotCompleted());
        return list;
    }
}
