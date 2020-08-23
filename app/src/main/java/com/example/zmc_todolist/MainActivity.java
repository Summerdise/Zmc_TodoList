package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    ViewModel viewModel;

    @BindView(R.id.user_name_input)
    TextView userNameInput;
    @BindView(R.id.password_input)
    TextView passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameInput.addTextChangedListener(new UserLetterNumberWatcher());

    }
}