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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTaskActivity extends AppCompatActivity {

    private LocalDatabase database;
    Boolean isDateChosen = false;
    Date deadlineDate;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        database = LocalDatabase.getInstance(this);
        ButterKnife.bind(this);
        chooseDateButton.setOnClickListener(new CalendarOnClickListener());
        createTaskTitleText.addTextChangedListener(new CreateTitleTextWatcher());
        saveButton.setOnClickListener(new SaveButtonOnClickListener());
        backButton.setOnClickListener(new BackButtonOnClickListener());
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
            boolean isComplete = checkBox.isChecked();
            boolean isNotice = chooseNoticeSwitch.isChecked();
            String taskTitle = createTaskTitleText.getText().toString();
            String taskDetail = createTaskDetailText.getText().toString();
            Task newTask = new Task(deadlineDate, isComplete, isNotice, taskTitle, taskDetail);
            database.taskDao().insertAll(newTask);
            Intent intent = new Intent(CreateTaskActivity.this, TasksActivity.class);
            startActivity(intent);
        }
    }

    class BackButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent(CreateTaskActivity.this, TasksActivity.class);
            startActivity(intent);
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