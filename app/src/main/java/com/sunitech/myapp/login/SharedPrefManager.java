package com.sunitech.myapp.login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager userSharedPrefManager;
    private static Context userSharedPrefContext;

    /** NOTE : Do not Change these values */
    private static final String SHARED_PREF_NAME = "panditjiclickapp";


    private SharedPrefManager(Context context) {
        userSharedPrefContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (userSharedPrefManager == null) {
            userSharedPrefManager = new SharedPrefManager(context);
        }
        return userSharedPrefManager;
    }

    // -----------
    public void getShared(){

        SharedPreferences sharedPreferences = userSharedPrefContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


    }

    public boolean logout() {
        SharedPreferences sharedPreferences = userSharedPrefContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.getAll().clear();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // TO STORE DATA...
//        editor.putLong(KEY_USER_MOBILE, user.getMobile());
        editor.clear();
        editor.apply();
        return true;
    }

}
