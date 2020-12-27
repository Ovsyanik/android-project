package ovsyanik.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;
import ovsyanik.project.sql.InsertUser;
import ovsyanik.project.utils.ValidationHelper;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    private static final int GALLERY_REQUEST_CODE = 0;

    private RoundedImageView riv_imageUser;
    private MaterialButton btn_signUp, btn_back;
    private TextInputEditText et_name, et_login, et_password, et_password2;

    private String mCurrentPhotoPath = null;
    private byte[] image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_registration);

        riv_imageUser = findViewById(R.id.image_user_registration);

        btn_signUp = findViewById(R.id.sign_up_registration);
        btn_back = findViewById(R.id.back_registration);

        et_name = findViewById(R.id.name_registration);
        et_login = findViewById(R.id.login_registration);
        et_password = findViewById(R.id.password_registration);
        et_password2 = findViewById(R.id.password2_registration);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btn_signUp.setOnClickListener(v -> {
            String login = et_login.getText().toString();
            String password = et_password.getText().toString();
            String password2 = et_password2.getText().toString();
            String name = et_name.getText().toString();

            if(validationField(name, login, password, password2)) {
                if (ValidationHelper.validationLogin(login) &&
                        ValidationHelper.validationPassword(password) &&
                        ValidationHelper.validationPassword(password2)) {

                    if (password.equals(password2)) {
                        InsertUser insertUser = new InsertUser();
                        insertUser.execute(new User(
                                name,
                                image == null ? null : image,
                                login,
                                password,
                                TypeUser.DEFAULT,
                                0
                        ));

                        Intent intent = new Intent(RegistrationActivity.this,
                                AuthorizationActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegistrationActivity.this,
                                "Пароли не совпадают", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegistrationActivity.this,
                            "Введите корректные данные", Toast.LENGTH_LONG).show();
                }
            }

        });

        btn_back.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, AuthorizationActivity.class);
            startActivity(intent);
        });

        riv_imageUser.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bitmap = null;

        switch (requestCode) {
            case GALLERY_REQUEST_CODE: {
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(selectedImage != null) {
                        riv_imageUser.setImageBitmap(bitmap);
//                        mCurrentPhotoPath = selectedImage.toString();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        image = stream.toByteArray();

                    }
                }
                break;
            }
        }
    }

    private boolean validationField(String name, String login, String password, String password2) {
        if(ValidationHelper.validationField(name)) {
            Toast.makeText(RegistrationActivity.this,
                    "Поле name пустое", Toast.LENGTH_LONG).show();
            return false;
        } else if (ValidationHelper.validationField(login)) {
            Toast.makeText(RegistrationActivity.this,
                    "Поле login пустое", Toast.LENGTH_LONG).show();
            return false;
        } else if (ValidationHelper.validationField(password)) {
            Toast.makeText(RegistrationActivity.this,
                    "Поле password пустое", Toast.LENGTH_LONG).show();
            return false;
        } else if (ValidationHelper.validationField(password2)) {
            Toast.makeText(RegistrationActivity.this,
                    "Поле password2 пустое", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
