package com.amrendra.gridgame.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * Created by Amrendra Kumar on 25/10/15.
 */
public class SharedPreferenceMgr {

    private static final String SHARED_PREF_FILE_NAME = "spot_it";

    SharedPreferences mSharedPreference;
    SharedPreferences.Editor mEditor;

    static SharedPreferenceMgr mSharedPreferenceMgr = null;

    private SharedPreferenceMgr(Context context) {
        mSharedPreference = context.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
    }

    public static SharedPreferenceMgr getInstance(Context context) {
        if (mSharedPreferenceMgr == null) {
            mSharedPreferenceMgr = new SharedPreferenceMgr(context);
        }
        return mSharedPreferenceMgr;
    }

    public int readValue(String key) {
        return mSharedPreference.getInt(key, 0);
    }

    public void writeValue(String key, int value) {
        mEditor.putInt(key, value).commit();
    }

    public void debug() {
        Debug.preferences(mSharedPreference);
    }
}
