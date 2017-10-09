package com.example.lukas.artgallerydrow.controller;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;

public class LoginActivity extends AppCompatActivity {


    TextView txtSignUp;
    EditText txtEmailLogin;
    TextInputLayout txtPassLogin;

    Button btnTestSelect, btnLogin;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = (TextView) findViewById(R.id.txtViewClickable);
        txtEmailLogin = (EditText) findViewById(R.id.txtLoginEmail);
        txtPassLogin = (TextInputLayout) findViewById(R.id.txtLoginPassword);

//        btnTestSelect = (Button) findViewById(R.id.testSelect);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        sendDataForResult();
//        testView();
        signUpClick();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RegisterActivity.RESULT_CODE_CANCELED) {
            Toast.makeText(LoginActivity.this,"Register canceled!!!",Toast.LENGTH_LONG).show();
        }
        if (resultCode == RegisterActivity.RESULT_CODE_REG_OK) {
            if (data != null) {
                txtEmailLogin.setText(data.getStringExtra("email"));
                txtPassLogin.getEditText().setText(data.getStringExtra("pass"));
                Toast.makeText(LoginActivity.this, "Register success!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, "Somethings wrong with data!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void sendDataForResult() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                String type = "";
                String nameUser = "";
                String email = "";
                DBOperations dbOper = new DBOperations(LoginActivity.this);
                Cursor res = dbOper.testGetUserInfo();
                if (res.getCount() == 0) {
                    Toast.makeText(LoginActivity.this, "Failed connection with database or no user found",Toast.LENGTH_LONG).show();
                    return;
                }

                while (res.moveToNext()) {

                    if(txtEmailLogin.getText().toString().equals(res.getString(3)) && txtPassLogin.getEditText().getText().toString().equals(res.getString(4))){
                        flag = true;
                        userID = res.getInt(0);
                        nameUser = res.getString(2);
                        email = res.getString(3);
                        type = res.getString(5);
                        break;
                    }

                }
                if(flag) {
                    if(type.equals("Seller")){
                        Intent intent = new Intent(LoginActivity.this,SellerActivity.class);
                        intent.putExtra("userID",userID);
                        intent.putExtra("userType",type);
                        intent.putExtra("User", nameUser);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(LoginActivity.this,BuyerActivity.class);
                        intent.putExtra("userID",userID);
                        intent.putExtra("userType",type);
                        intent.putExtra("User", nameUser);
                        intent.putExtra("email", email);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid username or password!!", Toast.LENGTH_LONG).show();
                    txtPassLogin.getEditText().setText("");
                }
            }
        });
    }

    public void testView() {
        btnTestSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // test to access user profile activity
                Intent intent2 = new Intent(LoginActivity.this,TempTestActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void signUpClick() {
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
