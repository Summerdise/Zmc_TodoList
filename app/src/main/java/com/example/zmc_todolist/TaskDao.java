package com.example.zmc_todolist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE is_complete = 0 ORDER BY deadline")
    List<Task> getCompleted();

    @Query("SELECT * FROM tasks WHERE is_complete = 1 ORDER BY deadline")
    List<Task> getNotCompleted();
//
//    @Query("SELECT * FROM tasks WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    Task findByName(String first, String last);

    @Insert
    void insertAll(Task... users);

    @Delete
    void delete(Task user);
}
