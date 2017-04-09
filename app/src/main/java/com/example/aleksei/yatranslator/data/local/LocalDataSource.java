package com.example.aleksei.yatranslator.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.example.aleksei.yatranslator.data.DataSource;
import com.example.aleksei.yatranslator.data.Repository;
import com.example.aleksei.yatranslator.data.Task;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void getHistory(LoadTranslationCallback callback) {

        List<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_ENTRY_ID,
                TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_TEXT,
                TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_TEXT,
                TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_LANG,
                TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_LANG
        };

        Cursor c = db.query(
                TranslatePersistanceConstract.TranslateEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_ENTRY_ID));
                String sourceText = c.getString(c.getColumnIndexOrThrow(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_TEXT));
                String resultText = c.getString(c.getColumnIndexOrThrow(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_TEXT));
                String sourceLang = c.getString(c.getColumnIndexOrThrow(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_LANG));
                String resultLang = c.getString(c.getColumnIndexOrThrow(TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_LANG));
                Task task = new Task(itemId, sourceText, resultText, sourceLang, resultLang);
                tasks.add(task);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (tasks.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onLoaded(tasks);
        }
    }
}
