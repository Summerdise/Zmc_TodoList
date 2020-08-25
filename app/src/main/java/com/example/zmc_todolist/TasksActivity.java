package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity {
    private LocalDatabase database;

    @BindView(R.id.receive_test)
    TextView receiveTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        database = LocalDatabase.getInstance(this);
        ButterKnife.bind(this);
        List<Task> list= database.taskDao().getAll();
        receiveTest.setText(list.get(0).toString()+list.get(1).toString()+list.get(2).toString());
    }
}