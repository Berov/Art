package com.example.lukas.artgallerydrow.controller;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;

public class RegisterActivity extends AppCompatActivity {

    public static final int RESULT_CODE_REG_OK = 6;
    public static final int RESULT_CODE_CANCELED = 2;

    EditText txtAddress;
    EditText txtUser;
    EditText txtEmail;
    TextInputLayout txtPass, txtConfirm;
    RadioGroup radioGroupBS;
    RadioButton radioButton;
    Button btnCancel;
    Button btnRegister;

    boolean flagAddress = false;
    boolean flagUsername = false;
    boolean flagEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        radioGroupBS = (RadioGroup)findViewById(R.id.radioSwitch);
        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtAddressTextListener();

        txtUser = (EditText)findViewById(R.id.txtUsername);
        txtUserTextListener();

        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtEmailTextListener();

        txtPass = (TextInputLayout) findViewById(R.id.txtPassword);
        txtConfirm = (TextInputLayout)findViewById(R.id.txtConfirmPassword);

        registerClick();

        cancelClick();

    }

    private void txtEmailTextListener() {
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean a = Validator.isValidEmail(s);
                if(a){
                    flagEmail = true;
                }else{
                    txtEmail.setError("Invalid field!");
                    flagEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void txtUserTextListener() {
        txtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 1){
                    flagUsername = false;
                    txtUser.setError("Invalid field!");
                }else{
                    flagUsername = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void txtAddressTextListener() {
        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() < 10){
                    txtAddress.setError("Invalid field!");
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


    private void registerClick() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int a = radioGroupBS.getCheckedRadioButtonId();
                radioButton = (RadioButton)findViewById(a);

                boolean isTruePass = Validator.validatePassword(txtPass.getEditText().getText().toString());

                if(isTruePass) {
                    if (txtPass.getEditText().getText().toString().equals(txtConfirm.getEditText().getText().toString()) && flagUsername
                            && flagEmail && flagAddress) {

                        Toast.makeText(RegisterActivity.this, "Register succsessful!!!", Toast.LENGTH_SHORT).show();

                    } else {

                        txtPass.getEditText().setText("");
                        txtConfirm.getEditText().setText("");
                        Toast.makeText(RegisterActivity.this, "Invalid fields!!!", Toast.LENGTH_LONG).show();
                        return;

                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Invalid password!!!", Toast.LENGTH_LONG).show();
                    txtPass.getEditText().setText("");
                    txtConfirm.getEditText().setText("");
                    return;
                }

                Intent intent = new Intent();

                intent.putExtra("email",txtEmail.getText().toString());
                intent.putExtra("pass",txtPass.getEditText().getText().toString());
                intent.putExtra("type", radioButton.getText().toString());

                String address = txtAddress.getText().toString();
                String user = txtUser.getText().toString();
                String email = txtEmail.getText().toString();
                String pass = txtPass.getEditText().getText().toString();
                String type = radioButton.getText().toString();

                saveData(address,user, email, pass, type);
                setResult(RESULT_CODE_REG_OK,intent);
                finish();
            }
        });
    }

    public void saveData(String addressString, String userString, String emailString, String pass, String type) {
        BackgroundDBTasks dbTask = new BackgroundDBTasks(this);
        dbTask.execute("addNewUser", null,addressString, userString, emailString, pass, type, 1000.00);
    }

    private void cancelClick() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CODE_CANCELED);
                finish();
            }
        });
    }


}
