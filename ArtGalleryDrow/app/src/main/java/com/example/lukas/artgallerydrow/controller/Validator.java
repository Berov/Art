package com.example.lukas.artgallerydrow.controller;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;

/**
 * Created by Lukas on 29.9.2017 г..
 */

public class Validator {
    public static boolean isValidUsername(String target){
        if(target.matches("[a-zA-Z0-9 ]+")){
            return true;
        }
        return false;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean validatePassword(String pass){
        if(!pass.equals("") && pass != null && (pass.length() > 5 )){
            return true;
        }
        return false;
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
