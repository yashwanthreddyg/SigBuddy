package com.android.test.sigbuddy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Muneer on 14-02-2016.
 */
public class UserLocalStore
{
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void setAsFirstTime(boolean isFirstTime)
    {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putBoolean("isFirstTime", isFirstTime);
        userLocalDatabaseEditor.commit();
    }

    public boolean isFirstTime()
    {
        boolean isFirstTime = userLocalDatabase.getBoolean("isFirstTime", true);
        return isFirstTime;
    }

    public void setUsername(String username) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("username", username);
        userLocalDatabaseEditor.commit();
    }

    public String getUsername() {
        String username = userLocalDatabase.getString("username", null);
        return username;
    }
}
