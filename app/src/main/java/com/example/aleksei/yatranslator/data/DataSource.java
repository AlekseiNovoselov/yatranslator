package com.example.aleksei.yatranslator.data;

public interface DataSource {

    long getTranslation(Task task, Repository.RemoteLoadListener loadTranslationCallback);

    void saveTask(Task task);

    interface LoadTranslationCallback {

        void onLoaded(Task task);

        void onDataNotAvailable();
    }
}
