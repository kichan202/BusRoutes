package com.busRoutes.project.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceUtils {
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public PreferenceUtils(Context context) {
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(Constants.LOGIN_PREF,Context.MODE_PRIVATE);
    }

    public void saveEmail(String email){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.EMAIL,email);
        editor.commit();
    }

    public String getEmail(){
        return mSharedPreferences.getString(Constants.EMAIL, null);
    }

}
