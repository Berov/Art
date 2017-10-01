package com.example.lukas.artgallerydrow.controller;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class TempTestActivity extends AppCompatActivity {


    Button btnGetAllTypes;
    Button btnGetAllUsers;
    Button btnGetAllSubtypes;
    Button subtypes;

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

        subtypes = (Button)findViewById(R.id.itemsAll);
        subtypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItems();
            }
        });

    }

    private void showItems(){
        DBOperations dbOper = new DBOperations(TempTestActivity.this);
        Cursor res = dbOper.gelAllItems();


        if (res.getCount() == 0) {
            showMessage("Error!", "Nothing found!");
            return;
        }

        StringBuffer buff = new StringBuffer();
        while (res.moveToNext()) {
            buff.append("ID: " + res.getString(0) + "\n");
            buff.append("Title: " + res.getString(1) + "\n");
            buff.append("Price: " + res.getString(2) + "\n");
            buff.append("Type: " + res.getString(4) + "\n");
            buff.append("SubType: " + res.getString(5) + "\n");
            buff.append("Desc: " + res.getString(6) + "\n");
            buff.append("SellerID: " + res.getString(7) + "\n");
            buff.append("Buyer_id: " + res.getString(8) + "\n\n");
        }
        showMessage("Data", buff.toString());
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
