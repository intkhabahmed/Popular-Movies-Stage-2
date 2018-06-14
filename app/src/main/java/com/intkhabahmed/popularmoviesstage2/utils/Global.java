package com.intkhabahmed.popularmoviesstage2.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.intkhabahmed.popularmoviesstage2.R;
import com.intkhabahmed.popularmoviesstage2.database.AppDatabase;

public class Global extends Application {

    private static Global sGlobalInstance;
    private static SharedPreferences sharedPreferences;

    public static Global getInstance() {
        return sGlobalInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sGlobalInstance = (Global) getApplicationContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public static AppDatabase getDbInstance() {
        return AppDatabase.getInstance(sGlobalInstance);
    }

    public static String getSortCriteriaString() {
        return sharedPreferences.getString(sGlobalInstance.getString(R.string.sort_criteria), AppConstants.POPULAR_MOVIES);
    }

    public static int getSortCriteriaOrder() {
        return sharedPreferences.getInt(sGlobalInstance.getString(R.string.sort_criteria_id), 1);
    }

    public static void saveSortCriteriaString(String sortCriteria) {
        sharedPreferences.edit().putString(sGlobalInstance.getString(R.string.sort_criteria), sortCriteria).apply();
    }

    public static void saveSortCriteriaOrder(int order) {
        sharedPreferences.edit().putInt(sGlobalInstance.getString(R.string.sort_criteria_id), order).apply();
    }
}
