package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userNameInput.addTextChangedListener(new LetterNumberWatcher());
        passwordInput.addTextChangedListener(new LetterNumberWatcher());
    }

    class LetterNumberWatcher implements TextWatcher {
        private final int USER_MIN_LETTER_NUMBER = 3;
        private final int USER_MAX_LETTER_NUMBER = 12;
        private final int PASSWORD_MIN_LETTER_NUMBER = 6;
        private final int PASSWORD_MAX_LETTER_NUMBER = 18;


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            judgeUserNameRightly();
            judgePasswordRightly();
        }

        public boolean judgeUserNameRightly(){
            int length = userNameInput.getText().toString().length();
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
        public boolean judgePasswordRightly(){
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

    }

}
