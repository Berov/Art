package com.example.lukas.artgallerydrow.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by plame_000 on 29-Sep-17.
 */

public class BackgroundDBTasks extends AsyncTask<Object,Void,String> {
    Context ctx;
    BackgroundDBTasks(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... params) {

        String method = params[0].toString();
        DBOperations dbOperation = new DBOperations(ctx);

        if(method.equals("updateProfile")){
            Integer id = (Integer)params[1];
            String address = params[2].toString();
            String username = params[3].toString();
            String pass = params[4].toString();
            byte[] bytes = (byte[])params[5];
            SQLiteDatabase db = dbOperation.getWritableDatabase();
            dbOperation.updateUserProfile(db,id,address,username,pass,bytes);
            return "User is updated";
        }

        if(method.equals("uploadImage")){
            // String id = params[1];
            String title = params[2].toString();
            Double price = (Double)params[3];
            byte[] img = (byte[]) params[4];
            String type = params[5].toString();
            String subType = params[6].toString();
            String desc = params[7].toString();
            Integer sellerId = (Integer)params[8];
            SQLiteDatabase db = dbOperation.getWritableDatabase();
            dbOperation.addNewItem(db, title, price, img, type, subType, desc, sellerId);
            return "One item inserted";
        }

        if(method.equals("addNewUser")){
            //  String id = params[1];
            String address = params[2].toString();
            String user = params[3].toString();
            String email = params[4].toString();
            String pass = params[5].toString();
            String type = params[6].toString();
            Double money = (Double)params[7];
            SQLiteDatabase db = dbOperation.getWritableDatabase();
            dbOperation.addUserInfo(db,address, user, email, pass,type, money);
            return "One user inserted";
        }

        return "fuckk";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String res) {
        //Toast.makeText(ctx,res, Toast.LENGTH_LONG).show();
    }
}
