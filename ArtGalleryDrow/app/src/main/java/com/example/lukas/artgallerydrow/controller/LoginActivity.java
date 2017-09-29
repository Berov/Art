package com.example.lukas.artgallerydrow.controller;

import android.content.Intent;
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

    String userString, passString, typeString, emailString, addressString;
    Button btnTestSelect, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = (TextView) findViewById(R.id.txtViewClickable);
        txtEmailLogin = (EditText) findViewById(R.id.txtLoginEmail);
        txtPassLogin = (TextInputLayout) findViewById(R.id.txtLoginPassword);

        btnTestSelect = (Button) findViewById(R.id.testSelect);
        btnLogin = (Button) findViewById(R.id.btnLogin);


        signUpClick();


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



    public void sendDataForResult(View view) {
        //TODO tuk shte se izprashta informaciqta ot login formata za proverka dali ima takuv potrebitel v bazata
    }

    public void goToBuyerActivity(View view) {
        Intent startBuyerActivity = new Intent(LoginActivity.this, BuyerActivity.class);
        startActivity(startBuyerActivity);
    }

}
