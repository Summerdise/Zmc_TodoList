package com.example.zmc_todolist;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;

public class UserAndPasswordRightlyWatch implements TextWatcher {
    @BindView(R.id.user_name_input)
    EditText userNameInput;
    @BindView(R.id.password_input)
    EditText passwordInput;
    @BindView(R.id.user_name_wrong_tip_button)
    Button userNameWrongTipButton;
    @BindView(R.id.password_wrong_tip_button)
    Button passwordWrongTipButton;
    @BindView(R.id.user_name_wrong_tip)
    TextView userNameWrongTipText;
    @BindView(R.id.password_wrong_tip)
    TextView passwordWrongTipText;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
