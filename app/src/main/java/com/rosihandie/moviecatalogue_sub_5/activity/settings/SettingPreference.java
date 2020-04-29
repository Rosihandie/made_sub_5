package com.rosihandie.moviecatalogue_sub_5.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingPreference {
    private static final String PREFS_NAME = "setting_pref";
    private static final String DAILY_REMINDER = "isDaily";
    private static final String RELEASE_REMINDER = "isRelease";
    private final SharedPreferences sharedPreferences;

    public SettingPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setDailyReminder(boolean isActive){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY_REMINDER, isActive);
        editor.apply();
    }

    public void setReleaseReminder(boolean isActive){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(RELEASE_REMINDER, isActive);
        editor.apply();
    }

    public boolean getDailyReminder(){
        return sharedPreferences.getBoolean(DAILY_REMINDER, false);
    }

    public boolean getReleaseReminder(){
        return sharedPreferences.getBoolean(RELEASE_REMINDER, false);
    }
}
