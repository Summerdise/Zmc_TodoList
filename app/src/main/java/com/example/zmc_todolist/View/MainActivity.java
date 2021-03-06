package com.example.zmc_todolist.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zmc_todolist.Controller.MainController;
import com.example.zmc_todolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;
    MainController mainController = new MainController(this, getBaseContext());
    final String USER_INFORMATION_URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";
    final String USER_WRONG_INPUT_TIP = "用户名长度必须是3-12个字符";
    final String PASSWORD_WRONG_INPUT_TIP = "密码长度必须是6-18位字符";
    final int USER_MIN_LETTER_NUMBER = 3;
    final int USER_MAX_LETTER_NUMBER = 12;
    final int PASSWORD_MIN_LETTER_NUMBER = 6;
    final int PASSWORD_MAX_LETTER_NUMBER = 18;

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
    @BindView(R.id.login_in_button)
    Button loginInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("isCorrect", false)) {
            startTasksActivity();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);
        userNameInput.addTextChangedListener(new LetterNumberWatcher());
        passwordInput.addTextChangedListener(new LetterNumberWatcher());
        userNameWrongTipButton.setOnClickListener(view -> {
            int length = userNameInput.getText().toString().length();
            if (length < USER_MIN_LETTER_NUMBER || length > USER_MAX_LETTER_NUMBER) {
                userNameWrongTipText.setText(USER_WRONG_INPUT_TIP);
                userNameWrongTipText.setVisibility(View.VISIBLE);
            }
        });
        passwordWrongTipButton.setOnClickListener(view -> {
            int length = passwordInput.getText().toString().length();
            if (length < PASSWORD_MIN_LETTER_NUMBER || length > PASSWORD_MAX_LETTER_NUMBER) {
                passwordWrongTipText.setText(PASSWORD_WRONG_INPUT_TIP);
                passwordWrongTipText.setVisibility(View.VISIBLE);
            }
        });

        loginInButton.setOnClickListener(view -> {
            mainController.findUserInformation(USER_INFORMATION_URL);
            try {
                verifyInput();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    class LetterNumberWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            userNameWrongTipText.setVisibility(View.INVISIBLE);
            passwordWrongTipText.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            judgeUserNameRightly();
            judgePasswordRightly();
            loginButtonChange();
        }

        public boolean judgeUserNameRightly() {
            int length = userNameInput.getText().toString().length();
            int USER_MIN_LETTER_NUMBER = 3;
            int USER_MAX_LETTER_NUMBER = 12;

            if (length == 0) {
                userNameWrongTipButton.setVisibility(View.INVISIBLE);
                return false;
            } else if (length < USER_MIN_LETTER_NUMBER || length > USER_MAX_LETTER_NUMBER) {
                userNameWrongTipButton.setVisibility(View.VISIBLE);
                return false;
            } else {
                userNameWrongTipButton.setVisibility(View.INVISIBLE);
                return true;
            }
        }

        public boolean judgePasswordRightly() {
            int length = passwordInput.getText().toString().length();
            if (length == 0) {
                passwordWrongTipButton.setVisibility(View.INVISIBLE);
                return false;
            } else if (length < PASSWORD_MIN_LETTER_NUMBER || length > PASSWORD_MAX_LETTER_NUMBER) {
                passwordWrongTipButton.setVisibility(View.VISIBLE);
                return false;
            } else {
                passwordWrongTipButton.setVisibility(View.INVISIBLE);
                return true;
            }
        }


        @SuppressLint("UseCompatLoadingForDrawables")
        public void loginButtonChange() {
            if (judgeUserNameRightly() && judgePasswordRightly()) {
                loginInButton.setTextColor(Color.parseColor("#ffffff"));
                loginInButton.setBackground(getResources().getDrawable(R.drawable.btn_able_background_login_in));
                loginInButton.setEnabled(true);
            } else {
                loginInButton.setTextColor(Color.parseColor("#000000"));
                loginInButton.setBackground(getResources().getDrawable(R.drawable.btn_unable_background_login_in));
                loginInButton.setEnabled(false);
            }
        }
    }

    public void verifyInput() throws Exception {
        if (!mainController.isUserNameExist(userNameInput.getText().toString())) {
            showToast("用户不存在");
        } else if (!mainController.isPasswordExist(passwordInput.getText().toString())) {
            showToast("密码错误");
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isCorrect", true);
            editor.apply();
            startTasksActivity();
        }
    }

    public void startTasksActivity() {
        Intent intent = new Intent(MainActivity.this, TasksActivity.class);
        startActivity(intent);
        finish();
    }

    public void showToast(String tips) {
        View view = View.inflate(this, R.layout.toast_show, findViewById(R.id.input_tips));
        Toast toast = new Toast(this);
        toast.setView(view);
        ((TextView) view.findViewById(R.id.input_tips)).setText(tips);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
