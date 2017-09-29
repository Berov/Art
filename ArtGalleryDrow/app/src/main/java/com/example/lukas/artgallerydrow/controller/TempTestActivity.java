package com.example.lukas.artgallerydrow.controller;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lukas.artgallerydrow.R;

public class TempTestActivity extends AppCompatActivity {


    Button btnGetAllTypes;
    Button btnGetAllUsers;
    Button btnGetAllSubtypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_test);

        btnGetAllSubtypes = (Button)findViewById(R.id.btnGetAllSubTypes);
        btnGetAllSubtypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSubTypeView();
            }
        });

        btnGetAllTypes = (Button) findViewById(R.id.btnGetAllTypes);
        btnGetAllTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testTypeView();
            }
        });

        btnGetAllUsers = (Button) findViewById(R.id.btnGetAllUsers);
        btnGetAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testUsersView();
            }
        });
    }

    private void testSubTypeView(){
        DBOperations dbOper = new DBOperations(TempTestActivity.this);
        Cursor res = dbOper.testGetSubTypeInfo();
        if (res.getCount() == 0) {
            showMessage("Error!", "Nothing found!");
            return;
        }

        StringBuffer buff = new StringBuffer();
        while (res.moveToNext()) {
            buff.append("ID: " + res.getString(0) + "\n");
            buff.append("SubType: " + res.getString(1) + "\n");
            buff.append("Type_ID: " + res.getString(2) + "\n\n");
        }
        showMessage("Data", buff.toString());
    }

    private void testUsersView(){
        DBOperations dbOper = new DBOperations(TempTestActivity.this);
        Cursor res = dbOper.testGetUserInfo();
        if (res.getCount() == 0) {
            showMessage("Error!", "Nothing found!");
            return;
        }

        StringBuffer buff = new StringBuffer();
        while (res.moveToNext()) {
            buff.append("ID: " + res.getString(0) + "\n");
            buff.append("ADDRESS: " + res.getString(1) + "\n");
            buff.append("Name: " + res.getString(2) + "\n");
            buff.append("Email: : " + res.getString(3) + "\n");
            buff.append("Pass: " + res.getString(4) + "\n");
            buff.append("Type: " + res.getString(5) + "\n");
            buff.append("Money: " + res.getString(6) + "\n\n");
        }

        showMessage("Data", buff.toString());
    }

    public void testTypeView(){
        DBOperations dbOper = new DBOperations(TempTestActivity.this);
        Cursor res = dbOper.testGetTypeInfo();
        if (res.getCount() == 0) {
            showMessage("Error!", "Nothing found!");
            return;
        }

        StringBuffer buff = new StringBuffer();
        while (res.moveToNext()) {
            buff.append("ID: " + res.getString(0) + "\n");
            buff.append("Type: " + res.getString(1) + "\n\n");
        }
        showMessage("Data", buff.toString());
    }

    public void showMessage(String title, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.show();
    }
}
