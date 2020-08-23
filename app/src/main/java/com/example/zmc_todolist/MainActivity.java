package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    ViewModel viewModel;
    int USER_MIN_LETTER_NUMBER = 3;
    int USER_MAX_LETTER_NUMBER = 12;
    int PASSWORD_MIN_LETTER_NUMBER = 6;
    int PASSWORD_MAX_LETTER_NUMBER = 18;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userNameInput.addTextChangedListener(new LetterNumberWatcher());
        passwordInput.addTextChangedListener(new LetterNumberWatcher());
        userNameWrongTipButton.setOnClickListener(view -> {
            int length = userNameInput.getText().toString().length();
            if (length < USER_MIN_LETTER_NUMBER) {
                userNameWrongTipText.setText("用户名需要输入至少3个字符");
                userNameWrongTipText.setVisibility(View.VISIBLE);
            }
            if (length > USER_MAX_LETTER_NUMBER) {
                userNameWrongTipText.setText("用户名最多输入12个字符");
                userNameWrongTipText.setVisibility(View.VISIBLE);
            }
        });
        passwordWrongTipButton.setOnClickListener(view -> {
            int length = passwordInput.getText().toString().length();
            if (length < PASSWORD_MIN_LETTER_NUMBER) {
                passwordWrongTipText.setText("用户名需要输入至少6个字符");
                passwordWrongTipText.setVisibility(View.VISIBLE);
            }
            if (length > PASSWORD_MAX_LETTER_NUMBER) {
                passwordWrongTipText.setText("用户名最多输入18个字符");
                passwordWrongTipText.setVisibility(View.VISIBLE);
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
                loginInButton.setEnabled(true);
                loginInButton.setBackground(getResources().getDrawable(R.drawable.btn_able_background_login_in));
            } else {
                loginInButton.setEnabled(false);
                loginInButton.setBackground(getResources().getDrawable(R.drawable.btn_unable_background_login_in));
            }
        }
    }

//    class wrongNameButtonClick implements View.OnClickListener {
//        int length = userNameInput.getText().toString().length();
//
//        @Override
//        public void onClick(View view) {
//            if (length < USER_MIN_LETTER_NUMBER) {
//                userNameWrongTipText.setText("用户名需要输入至少3个字符");
//                userNameWrongTipText.setVisibility(View.VISIBLE);
//            }
//            if (length > USER_MAX_LETTER_NUMBER) {
//                userNameWrongTipText.setText("用户名最多输入12个字符");
//                userNameWrongTipText.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    class wrongPasswordButtonClick implements View.OnClickListener {
//        int length = passwordInput.getText().toString().length();
//
//        @Override
//        public void onClick(View view) {
//            if (length < PASSWORD_MIN_LETTER_NUMBER) {
//                passwordWrongTipText.setText("用户名需要输入至少6个字符");
//                passwordWrongTipText.setVisibility(View.VISIBLE);
//            }
//            if (length > PASSWORD_MAX_LETTER_NUMBER) {
//                passwordWrongTipText.setText("用户名最多输入18个字符");
//                passwordWrongTipText.setVisibility(View.VISIBLE);
//            }
//        }
//    }

}
