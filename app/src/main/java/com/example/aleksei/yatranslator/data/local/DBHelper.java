package com.example.aleksei.yatranslator.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "yatranslator.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TranslatePersistanceConstract.TranslateEntry.TABLE_NAME + " (" +
                    TranslatePersistanceConstract.TranslateEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_TEXT + TEXT_TYPE + COMMA_SEP +
                    TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_TEXT + TEXT_TYPE + COMMA_SEP +
                    TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_SOURCE_LANG + TEXT_TYPE + COMMA_SEP +
                    TranslatePersistanceConstract.TranslateEntry.COLUMN_NAME_RESULT_LANG + TEXT_TYPE +
                    " )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
