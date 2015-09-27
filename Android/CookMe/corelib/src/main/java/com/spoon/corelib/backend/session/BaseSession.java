package com.spoon.corelib.backend.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lycha on 9/19/2015.
 */
public class BaseSession {


    // Shared Preferences
    SharedPreferences mPref;

    // Editor for Shared preferences
    SharedPreferences.Editor mEditor;

    // Context
    Context mContext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared pref file name
    private static final String PREF_NAME = "CookMe";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // Email address
    private static final String KEY_EMAIL = "email";

    // Constructor
    public BaseSession(Context context) {
        this.mContext = context;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String email) {
        // Storing login value as TRUE
        mEditor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        mEditor.putString(KEY_EMAIL, email);

        // commit changes
        mEditor.commit();
    }

    public String getEmail() {
        return mPref.getString(KEY_EMAIL, null);
    }

    public boolean isLoggedIn() {
        return mPref.getBoolean(IS_LOGIN, false);
    }

    public void logout() {
        mEditor.clear();
        mEditor.commit();
    }

}
