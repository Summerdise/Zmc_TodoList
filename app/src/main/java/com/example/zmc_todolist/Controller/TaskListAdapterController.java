package com.example.zmc_todolist.Controller;

import com.example.zmc_todolist.Model.DB.LocalDatabase;
import com.example.zmc_todolist.View.TaskListAdapter;

public class TaskListAdapterController {
    TaskListAdapter taskListAdapter;
    LocalDatabase database;

    public TaskListAdapterController(TaskListAdapter taskListAdapter) {
        this.taskListAdapter = taskListAdapter;
        database = LocalDatabase.getInstance(taskListAdapter.getContext());

    }

    public LocalDatabase getDatabase() {
        return database;
    }
}
