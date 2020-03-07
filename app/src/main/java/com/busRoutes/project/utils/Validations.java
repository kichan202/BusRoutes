package com.busRoutes.project.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class Validations {
    public static boolean validateFields(String string){
        if(TextUtils.isEmpty(string)){
            return false;
        }else{
            return true;
        }
    }

    public static boolean validateEmail(String email){
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return false;
        }else {
            return true;
        }
    }
}
