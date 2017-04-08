package com.example.aleksei.yatranslator.data;

public interface DataSource {

    long getTranslation(Task task, LoadTranslationCallback loadTranslationCallback);

    interface LoadTranslationCallback {

        void onLoaded(String text);

        void onDataNotAvailable();
    }
}
