package com.example.aleksei.yatranslator.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.aleksei.yatranslator.data.DataSource;
import com.example.aleksei.yatranslator.data.Repository;
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
    public long getTranslation(Task task, Repository.RemoteLoadListener loadTranslationCallback) {
        return 0L;
    }

    @Override
    public void saveTask(Task task) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        values.put(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_TEXT, task.getSourceText());
        values.put(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_TEXT, task.getResultText());
        values.put(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_LANG, task.getSourceLang());
        values.put(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_LANG, task.getResultLang());

        db.insert(TranslatePersistanceConstract.TranslateEntry.TABLE_NAME, null, values);

        db.close();
    }
}
