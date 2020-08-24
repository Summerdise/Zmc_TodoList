package com.example.zmc_todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ViewModel viewModel;
    UserInformation userInformation;
    final String USER_INFORMATION_URL="https://twc-android-bootcamp.github.io/fake-data/data/user.json";
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
    @BindView(R.id.input_tips)
    TextView inputTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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

        loginInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findUserInformation(USER_INFORMATION_URL);
                try {
                    verifyInput();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class LetterNumberWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            userNameWrongTipText.setVisibility(View.INVISIBLE);
            passwordWrongTipText.setVisibility(View.INVISIBLE);
            inputTips.setVisibility(View.INVISIBLE);
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
    public void findUserInformation(String url) {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            final String result = Objects.requireNonNull(response.body()).string();
            Gson gson = new Gson();
            userInformation = gson.fromJson(result, UserInformation.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String src) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(src.getBytes());
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            String temp = Integer.toHexString(b & 0xff);
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            result.append(temp);
        }
        System.out.println(result.toString());
        return result.toString();
    }

    public boolean isUserNameExist(String input){
        return input.equals(userInformation.getName());
    }

    public boolean isPasswordExist(String input) throws Exception {
        String md5Input = encrypt(input);
        return md5Input.equals(userInformation.getPassword());
    }

    public void verifyInput() throws Exception {
        if(!isUserNameExist(userNameInput.getText().toString())){
            inputTips.setText("用户名不正确");
            inputTips.setVisibility(View.VISIBLE);
        }else if(!isPasswordExist(passwordInput.getText().toString())){
            inputTips.setText("密码不正确");
            inputTips.setVisibility(View.VISIBLE);
        }else{
            System.out.println("nb");
        }
    }
}
