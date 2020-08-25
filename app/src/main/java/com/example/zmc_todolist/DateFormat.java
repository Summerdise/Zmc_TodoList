package com.example.zmc_todolist;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {
    public String toChineseYearMonthDay(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter.format(date);
    }

    public String toChineseMonthDay(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        return formatter.format(date);
    }

    public String toEnglishWeekDay(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEEE，d", Locale.ENGLISH);
        String format = formatter.format(date);
        char lastChar = format.charAt(format.length()-1);
        if (lastChar == '1') {
            return format + "st";
        } else if (lastChar == '2') {
            return format + "nd";
        } else if (lastChar == '3') {
            return format + "rd";
        } else {
            return format + "th";
        }
    }

    public String toEnglishMonth(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        return formatter.format(date);
    }
}
