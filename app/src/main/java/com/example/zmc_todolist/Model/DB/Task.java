package com.example.zmc_todolist.Model.DB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id=0;

    Date deadline;

    @ColumnInfo(name = "is_complete")
    boolean isComplete;

    @ColumnInfo(name = "is_notice")
    boolean isNotice;

    @ColumnInfo(name = "task_title")
    String taskTitle;

    @ColumnInfo(name = "task_detail")
    String taskDetail;

    public Task(Date deadline, boolean isComplete, boolean isNotice, String taskTitle, String taskDetail) {
        this.deadline = deadline;
        this.isComplete = isComplete;
        this.isNotice = isNotice;
        this.taskTitle = taskTitle;
        this.taskDetail = taskDetail;
    }

    public int getId() {
        return id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isNotice() {
        return isNotice;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setNotice(boolean notice) {
        isNotice = notice;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    @Override
    public String toString() {
        return "Task{" +
                "deadline=" + deadline +
                ", isComplete=" + isComplete +
                ", isNotice=" + isNotice +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskDetail='" + taskDetail + '\'' +
                '}';
    }
}
