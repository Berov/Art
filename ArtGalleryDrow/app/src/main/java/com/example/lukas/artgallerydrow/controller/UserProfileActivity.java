package com.example.lukas.artgallerydrow.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfileActivity extends AppCompatActivity {

    ImageView imagee;
    EditText username;
    EditText address;
    EditText pass;
    EditText confirm;
    int userID;
    Button btnCancel, btnUpdate, btnUploadImage;

    boolean flagAddress = false;
    boolean flagUsername = false;
    boolean flagPass = false;
    boolean flag = false;
    byte[] bytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        imagee = (ImageView) findViewById(R.id.imgProfileChange);

        userID = getIntent().getExtras().getInt("userID");

        username = (EditText) findViewById(R.id.txtUsernameChange);
        txtUserTextListener();

        address = (EditText) findViewById(R.id.txtAddressChange);
        txtAddressTextListener();

        pass = (EditText) findViewById(R.id.txtPassChange);
        confirm = (EditText) findViewById(R.id.txtPassConfirmChange);
        txtPassTextListener();

        btnCancel = (Button)findViewById(R.id.btnProfileCancel);
        cancelListener();

        btnUpdate = (Button)findViewById(R.id.btnProfileUpdate);
        updateInfoListener();

        btnUploadImage = (Button)findViewById(R.id.btnProfileImgUpload);
        uploadImageListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {

            if(resultCode == RESULT_OK) {

                Uri uriImg = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(uriImg);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    if (image.getWidth() > 1300 || image.getHeight() > 1300) {
                        Toast.makeText(getApplicationContext(), "Picture is too big!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    bytes = Validator.getBytes(image);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    imagee.setImageBitmap(bitmap);

                    flag = true;
                    Toast.makeText(getApplicationContext(), "Image changed!", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    flag = false;
                    Toast.makeText(getApplicationContext(), "Unable to open image...", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void uploadImageListener() {
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPick, 10);
            }
        });
    }

    private void updateInfoListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flagPass && flagUsername && flagAddress && (pass.getText().toString().equals(confirm.getText().toString()))){
                    BackgroundDBTasks bt = new BackgroundDBTasks(UserProfileActivity.this);
                    if(flag){
                        bt.execute("updateProfile",userID,address.getText().toString(),username.getText().toString(),pass.getText().toString(),bytes);
                    }else{
                        bt.execute("updateProfile",userID,address.getText().toString(),username.getText().toString(),pass.getText().toString(),null);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Invalid fields...", Toast.LENGTH_SHORT).show();
                    return;
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void cancelListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void txtPassTextListener() {
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Validator.validatePassword(s.toString())){
                    flagPass = true;
                }else{
                    flagPass = false;
                    pass.setError("Invalid field..");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void txtAddressTextListener() {
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 10 || s.length() > 80){
                    address.setError("Invalid field!");
                    flagAddress = false;
                }else{
                    flagAddress = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void txtUserTextListener() {
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Validator.isValidUsername(s.toString())){
                    flagUsername = true;
                }else{
                    flagUsername = false;
                    username.setError("Invalid field..");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
