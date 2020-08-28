package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTaskActivity extends AppCompatActivity {
    final int RECEIVE_FAULT_VALUE = -2;
    boolean isFromList = true;
    private LocalDatabase database;
    Boolean isDateChosen = false;
    Date deadlineDate;
    int receiveMessage;
    Task task;

    @BindView(R.id.check_box)
    CheckBox checkBox;
    @BindView(R.id.choose_date_button)
    Button chooseDateButton;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @BindView(R.id.choose_notice_switch)
    Switch chooseNoticeSwitch;
    @BindView(R.id.create_task_title_text)
    TextView createTaskTitleText;
    @BindView(R.id.create_task_detail_text)
    TextView createTaskDetailText;
    @BindView(R.id.choose_calendar)
    DatePicker chooseCalendar;
    @BindView(R.id.save_button)
    Button saveButton;
    @BindView(R.id.back_button)
    ImageButton backButton;
    @BindView(R.id.delete_button)
    Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        database = LocalDatabase.getInstance(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        receiveMessage = intent.getIntExtra("id", RECEIVE_FAULT_VALUE);
        if (receiveMessage != RECEIVE_FAULT_VALUE) {
            createChangeTaskActivity();
        } else {
            isFromList = false;
        }
        createCreateTaskActivity();

    }

    private void createCreateTaskActivity() {
        chooseDateButton.setOnClickListener(new CalendarOnClickListener());
        createTaskTitleText.addTextChangedListener(new CreateTitleTextWatcher());
        saveButton.setOnClickListener(new SaveButtonOnClickListener());
        backButton.setOnClickListener(new BackButtonOnClickListener());
        if (isFromList) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    database.taskDao().delete(task);
                    deleteButton.setVisibility(View.INVISIBLE);
                    finish();
                }
            });
        }
    }

    private void createChangeTaskActivity() {
        task = database.taskDao().findById(receiveMessage);
        saveButton.setEnabled(true);
        deleteButton.setVisibility(View.VISIBLE);
        chooseDateButton.setText(new DateFormat().toChineseYearMonthDay(task.deadline));
        chooseDateButton.setTextColor(Color.parseColor("#6BA5E9"));
        createTaskTitleText.setText(task.taskTitle);
        createTaskDetailText.setText(task.taskDetail);
        checkBox.setChecked(task.isComplete);
        chooseNoticeSwitch.setChecked(task.isNotice);
    }

    class CreateTitleTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0 && isDateChosen) {
                saveButton.setEnabled(true);
            } else if (editable.length() > 0 && isFromList) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
        }
    }

    class CalendarOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showCalendar();
            Calendar calendar = Calendar.getInstance();
            int year, month, day;
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            chooseCalendar.init(year, month, day, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    int chooseYear = year - 1900;
                    int chooseMonth = monthOfYear;
                    int chooseDay = dayOfMonth;
                    deadlineDate = new Date(chooseYear, chooseMonth, chooseDay);
                    hideCalendar();
                    isDateChosen = true;
                    chooseDateButton.setText(new DateFormat().toChineseYearMonthDay(deadlineDate));
                    chooseDateButton.setTextColor(Color.parseColor("#6BA5E9"));
                    if (createTaskTitleText.getText().toString().length() > 0 && isDateChosen) {
                        saveButton.setEnabled(true);
                    } else {
                        saveButton.setEnabled(false);
                    }
                }
            });
        }
    }

    class SaveButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!isDateChosen) {
                deadlineDate = task.deadline;
            }
            boolean isComplete = checkBox.isChecked();
            boolean isNotice = chooseNoticeSwitch.isChecked();
            String taskTitle = createTaskTitleText.getText().toString();
            String taskDetail = createTaskDetailText.getText().toString();
            Task newTask = new Task(deadlineDate, isComplete, isNotice, taskTitle, taskDetail);
            deleteButton.setVisibility(View.INVISIBLE);
            if (isFromList) {
                newTask.id = receiveMessage;
                database.taskDao().update(newTask);
            } else {
                database.taskDao().insertAll(newTask);
            }
            finish();
        }
    }

    class BackButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            deleteButton.setVisibility(View.INVISIBLE);
            finish();
        }
    }

    public void showCalendar() {
        chooseCalendar.setVisibility(View.VISIBLE);
        createTaskDetailText.setVisibility(View.INVISIBLE);
        createTaskTitleText.setVisibility(View.INVISIBLE);
    }

    public void hideCalendar() {
        chooseCalendar.setVisibility(View.INVISIBLE);
        createTaskDetailText.setVisibility(View.VISIBLE);
        createTaskTitleText.setVisibility(View.VISIBLE);
    }

}