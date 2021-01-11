package com.example.eatit;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class SessionHelper {

    public static void  createUserSession(Context context,User user){
        Gson gson=new Gson();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString("current_user",gson.toJson(user) )
                .apply();

    }
    public static void logout(Context context){
        //session destroy
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove("current_user")
                .apply();
    }
    public static boolean isUserLoggedIn(Context context){
        String currentUserValue=PreferenceManager.getDefaultSharedPreferences(context)
                .getString("current_user",null);
        if (currentUserValue==null){
            return false;
        }else {
            return true;
        }

    }
    public static User getCurrentUser(Context context){
        String currentUserValue=PreferenceManager.getDefaultSharedPreferences(context)
                .getString("current_user",null);
        return new Gson().fromJson(currentUserValue,User.class);
    }

}
