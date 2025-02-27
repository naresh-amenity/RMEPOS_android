package com.rme.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Rajnish on 10/6/2015.
 */
public class MySession {
    public static final String PREFERENCES_Session = "Session";
    public static final String Share_DefaultStock = "DefaultStock";
    public static final String Share_BranchID = "BranchID";
    public static final String Share_SetupDate = "SetupDate";
    public static final String Share_AppLock = "AppLock";
    public static final String Share_DownloadLock = "DownloadLock";
    public static final String Share_ServerLock = "ServerLock";
    public static final String Share_Pin = "Pin";
    public static final String Share_LastSetupFilePath = "LastSetupFilePath";

    public static SharedPreferences sharedpreferences_Session;
    public static Context mContext = null;


    public static void setPin(Context context, String pin) {
        mContext = context;
        sharedpreferences_Session = context.getSharedPreferences(PREFERENCES_Session,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences_Session.edit();
        editor.putString(Share_Pin, pin);
        editor.commit();

    }

    public static String getPin(Context context) {
        mContext = context;
        sharedpreferences_Session = context.getSharedPreferences(PREFERENCES_Session,
                Context.MODE_PRIVATE);
        if (sharedpreferences_Session.contains(Share_Pin)) {
            return sharedpreferences_Session.getString(Share_Pin, "");
        } else {
            return "";
        }
    }

    public static void set(Context context, String sValue, String sKey) {
        mContext = context;
        sharedpreferences_Session = context.getSharedPreferences(PREFERENCES_Session, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences_Session.edit();
        editor.putString(sKey, sValue);
        editor.apply();
    }


    public static String get(Context context, String sKey) {
        mContext = context;
        String s = "";
        try {
            sharedpreferences_Session = context.getSharedPreferences(PREFERENCES_Session, Context.MODE_PRIVATE);
            if (sharedpreferences_Session.contains(sKey)) {
                s = sharedpreferences_Session.getString(sKey, "");
            } else {
                s = "";
            }
        } catch (Exception e) {
        }
        return s;
    }
    public static String getDefaultStock(Context context) {
        mContext = context;
        String s = "";
        try {
            sharedpreferences_Session = context.getSharedPreferences(PREFERENCES_Session, Context.MODE_PRIVATE);
            if (sharedpreferences_Session.contains(Share_DefaultStock)) {
                s = sharedpreferences_Session.getString(Share_DefaultStock, "");
            } else {
                s = "1";
            }
        } catch (Exception e) {
        }
        return s;
    }

}
