package com.example.aleksei.yatranslator.data.local;

import android.provider.BaseColumns;

public class TranslatePersistanceConstract {

    private TranslatePersistanceConstract() {}

    /* Inner class that defines the table contents */
    public static abstract class TranslateEntry implements BaseColumns {
        public static final String TABLE_NAME = "translate";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_SOURCE_TEXT = "source_text";
        public static final String COLUMN_NAME_RESULT_TEXT = "result_text";
        public static final String COLUMN_NAME_SOURCE_LANG = "source_lang";
        public static final String COLUMN_NAME_RESULT_LANG = "result_lang";
        // TODO: дату, флаг избранное, ссылка на словарь;
    }
}
