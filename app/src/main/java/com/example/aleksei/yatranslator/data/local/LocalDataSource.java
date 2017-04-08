package com.example.aleksei.yatranslator.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.aleksei.yatranslator.data.DataSource;
import com.example.aleksei.yatranslator.data.Task;

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;

    private DBHelper mDbHelper;

    private LocalDataSource(@NonNull Context context) {
        mDbHelper = new DBHelper(context);
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public long getTranslation(Task task, LoadTranslationCallback loadTranslationCallback) {
        return 0L;
    }
}
