package com.example.zmc_todolist.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.zmc_todolist.Controller.DateFormat;
import com.example.zmc_todolist.Controller.TasksController;
import com.example.zmc_todolist.Model.DB.LocalDatabase;
import com.example.zmc_todolist.Model.DB.Task;
import com.example.zmc_todolist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity {
    List<Task> taskList;
    TasksController tasksController;

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
    @BindView(R.id.create_new_button)
    FloatingActionButton createNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadListLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListLayout();
    }

    private void loadListLayout() {
        setContentView(R.layout.activity_tasks);
        tasksController = new TasksController(this,getBaseContext());
        taskList = tasksController.getTaskList();
        ButterKnife.bind(this);
        loadRecyclerView();
        Date date = new Date(System.currentTimeMillis());
        listNowDate.setText(new DateFormat().toEnglishWeekDay(date));
        listNowMonth.setText(new DateFormat().toEnglishMonth(date));
        listTaskNumber.setText(taskList.size() + "个任务");
        createNewButton.setOnClickListener(new createNewOnClickListener());
        listMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.exit_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(TasksActivity.this, MainActivity.class);
                startActivity(intent);
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putBoolean("isCorrect", false);
                editor.apply();
                finish();
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                popupMenu.dismiss();
            }
        });
        popupMenu.show();
    }

    private void loadRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tasksRecyclerView.setLayoutManager(layoutManager);
        TaskListAdapter adapter = new TaskListAdapter(this, tasksController.getDatabase(), taskList);
        tasksRecyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new TaskListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, int id) {
                Intent intent = new Intent(TasksActivity.this, CreateTaskActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    class createNewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(TasksActivity.this, CreateTaskActivity.class);
            startActivity(intent);
        }
    }
}