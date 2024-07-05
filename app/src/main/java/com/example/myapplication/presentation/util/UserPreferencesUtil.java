package com.example.myapplication.presentation.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.data.database.model.UserModel;
import com.google.gson.Gson;

public class UserPreferencesUtil {
    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER = "current_user";
    private static UserPreferencesUtil instance;
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private UserPreferencesUtil(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized UserPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            instance = new UserPreferencesUtil(context);
        }
        return instance;
    }

    public void saveCurrentUser(UserModel user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.contains(KEY_USER);
    }

    public UserModel getCurrentUser() {
        String userJson = sharedPreferences.getString(KEY_USER, null);
        return userJson != null ? gson.fromJson(userJson, UserModel.class) : null;
    }

    public void clearCurrentUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USER);
        editor.apply();
    }

}
