package com.example.zmc_todolist;

import android.text.Editable;
import android.text.TextWatcher;


public class UserLetterNumberWatcher implements TextWatcher {
    private final int USER_MIN_LETTER_NUMBER = 3;
    private final int USER_MAX_LETTER_NUMBER = 12;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int length = editable.length();
        if (length >= USER_MIN_LETTER_NUMBER && length <= USER_MAX_LETTER_NUMBER) {

        }

    }
}
