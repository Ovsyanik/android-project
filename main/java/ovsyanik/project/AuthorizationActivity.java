package ovsyanik.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import ovsyanik.project.data.User;
import ovsyanik.project.sql.GetUser;
import ovsyanik.project.utils.ValidationHelper;

public class AuthorizationActivity extends AppCompatActivity {
    private static final String TAG = AuthorizationActivity.class.getSimpleName();

    private Button btn_signIn, btn_signUp;
    private TextInputEditText et_login, et_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_authorization);

        btn_signIn = findViewById(R.id.sign_in);
        btn_signUp = findViewById(R.id.sign_up);

        et_login = findViewById(R.id.login_authorization);
        et_password = findViewById(R.id.password_authorization);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_signIn.setOnClickListener(v -> {
            String login = et_login.getText().toString();
            String password = et_password.getText().toString();

            if(validationField(login, password)) {
                if (ValidationHelper.validationLogin(login) &&
                        ValidationHelper.validationPassword(password)) {
                    GetUser getUser = new GetUser();

                    try {
                        User user = getUser.execute(login, password).get();
                        if (user != null) {
                            User.getInstance(user);
                            Log.d(TAG, User.getInstance().toString());

                            Intent intent = new Intent(AuthorizationActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AuthorizationActivity.this,
                                    "Такого пользователя не существует", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(AuthorizationActivity.this,
                                e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AuthorizationActivity.this,
                            "Введите корректные данные", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_signUp.setOnClickListener(v -> {
            Intent intent = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }

    private boolean validationField(String login, String password) {

       if (ValidationHelper.validationField(login)) {
            Toast.makeText(AuthorizationActivity.this,
                    "Поле login пустое", Toast.LENGTH_LONG).show();
            return false;
        } else if (ValidationHelper.validationField(password)) {
            Toast.makeText(AuthorizationActivity.this,
                    "Поле password пустое", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
