package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity {
    private LocalDatabase database;
    List<Task> taskList;
    @BindView(R.id.tasks_recycler_view)
    RecyclerView tasksRecyclerView;
    @BindView(R.id.list_now_date)
    TextView listNowDate;
    @BindView(R.id.list_now_month)
    TextView listNowMonth;
    @BindView(R.id.list_more_button)
    ImageButton listMoreButton;
    @BindView(R.id.list_task_number)
    TextView listTaskNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        taskList = getTaskList();
        ButterKnife.bind(this);
        loadRecyclerView();
        Date date = new Date(System.currentTimeMillis());
        listNowDate.setText(new DateFormat().toEnglishWeekDay(date));
        listNowMonth.setText(new DateFormat().toEnglishMonth(date));
        listTaskNumber.setText(taskList.size()+"个任务");

    }

    private List<Task> getTaskList(){
        database = LocalDatabase.getInstance(this);
        List<Task> list = database.taskDao().getCompleted();
        list.addAll(database.taskDao().getNotCompleted());
        return list;
    }

    private void loadRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tasksRecyclerView.setLayoutManager(layoutManager);
        TaskListAdapter adapter = new TaskListAdapter(taskList);
        tasksRecyclerView.setAdapter(adapter);
    }
}