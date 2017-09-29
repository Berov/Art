package com.example.lukas.artgallerydrow.controller;

import android.text.TextUtils;

/**
 * Created by Lukas on 29.9.2017 г..
 */

public class Validator {

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean validatePassword(String pass){
        if(!pass.equals("") && pass != null && (pass.length() > 5 )){
            return true;
        }
        return false;
    }
}
