package com.example.zmc_todolist.Controller;

import com.example.zmc_todolist.Model.DB.LocalDatabase;
import com.example.zmc_todolist.Model.DB.Task;
import com.example.zmc_todolist.View.TaskListAdapter;

import java.util.List;

public class TaskListAdapterController {
    TaskListAdapter taskListAdapter;
    LocalDatabase database;

    public TaskListAdapterController(TaskListAdapter taskListAdapter) {
        this.taskListAdapter = taskListAdapter;
        database = LocalDatabase.getInstance(taskListAdapter.getContext());

    }

    public List<Task> getCompletedList(){
        return database.taskDao().getCompleted();
    }
    public List<Task> getNotCompletedList(){
        return database.taskDao().getNotCompleted();
    }

    public void updateDatabase(Task task){
        database.taskDao().update(task);
    }

}
