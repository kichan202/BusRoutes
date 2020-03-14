package com.busRoutes.project.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Validations {
    //patter for password
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" + //at least 1 digit
                    "(?=.*[a-z])" + //at least 1 lower case letter
                    "(?=.*[A-Z])" + //at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" + //at least 1 special character
                    "(?=\\S+$)" + //no white spaces
                    ".{6,}" +               //at least 4 characters
                    "$"
            );

    public static boolean validateFields(String string){
        if(TextUtils.isEmpty(string)){
            return false;
        }else{
            return true;
        }
    }

    public static boolean validateEmail(String string) {
        if(TextUtils.isEmpty(string) || !Patterns.EMAIL_ADDRESS.matcher(string).matches()){
            return false;
        }else
        {
            return true;

        }
    }

    public static boolean validatePassword(String string){
        if(TextUtils.isEmpty(string) || !PASSWORD_PATTERN.matcher(string).matches()){
            return false;
        }else{
            return true;
        }
    }
}
