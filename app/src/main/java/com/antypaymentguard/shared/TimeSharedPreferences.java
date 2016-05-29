package com.antypaymentguard.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.antypaymentguard.GuardApplication;

public class TimeSharedPreferences {

    public static TimeSharedPreferences change() {
        return new TimeSharedPreferences();
    }

    private TimeSharedPreferences() {
        _editor = GuardApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
    }

    public static long getLastTime(final String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public TimeSharedPreferences setKey(final String key) {
        return setKey(key, System.currentTimeMillis());
    }

    public TimeSharedPreferences setKey(final String key, final long time) {
        _editor.putLong(key, time);
        return this;
    }

    public void commit() {
        _editor.apply();
    }

    public TimeSharedPreferences clear() {
        _editor.clear();
        return this;
    }

    private static SharedPreferences getSharedPreferences() {
        return GuardApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private static final String FILE_NAME = ".time";
    public static final String KEY_DATA_TIMESTAMP = "data_timestamp";

    private SharedPreferences.Editor _editor;
}
