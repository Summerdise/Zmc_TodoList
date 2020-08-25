package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTaskActivity extends AppCompatActivity {

    @BindView(R.id.check_box)
    Button checkBox;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);

        chooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseCalendar.setVisibility(View.VISIBLE);
                createTaskDetailText.setVisibility(View.INVISIBLE);
                createTaskTitleText.setVisibility(View.INVISIBLE);
                Calendar calendar = Calendar.getInstance();
                int year,month,day;
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                chooseCalendar.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int chooseYear = year;
                        int chooseMonth = (monthOfYear + 1);
                        int chooseDay = dayOfMonth;
                        Date date = new Date(chooseYear, chooseMonth, chooseDay);
                        System.out.println(date.toString());
                    }
                });
            }
        });
    }


    public String dateFormat(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter.format(date);
    }
}