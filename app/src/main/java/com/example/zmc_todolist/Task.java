package com.example.zmc_todolist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "tasks")

public class Task {
    @NonNull
    @PrimaryKey
    Date deadline;

    @ColumnInfo(name = "is_complete")
    boolean isComplete;

    @ColumnInfo(name = "is_notice")
    boolean isNotice;

    @ColumnInfo(name = "task_title")
    String taskTitle;

    @ColumnInfo(name = "task_detail")
    String taskDetail;

    public Task(@NonNull Date deadline, boolean isComplete, boolean isNotice, String taskTitle, String taskDetail) {
        this.deadline = deadline;
        this.isComplete = isComplete;
        this.isNotice = isNotice;
        this.taskTitle = taskTitle;
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
