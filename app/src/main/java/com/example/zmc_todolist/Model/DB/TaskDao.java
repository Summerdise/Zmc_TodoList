package com.example.zmc_todolist.Model.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE is_complete = 0 ORDER BY deadline")
    List<Task> getCompleted();

    @Query("SELECT * FROM tasks WHERE is_complete = 1 ORDER BY deadline")
    List<Task> getNotCompleted();

    @Query("SELECT * FROM tasks WHERE id = :task")
    Task findById(int task);

    @Insert
    void insertAll(Task... tasks);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
