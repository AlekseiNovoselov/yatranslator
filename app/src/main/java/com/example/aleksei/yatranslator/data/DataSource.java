package com.example.aleksei.yatranslator.data;

import java.util.List;

public interface DataSource {

    long getTranslation(Task task, Repository.RemoteLoadListener loadTranslationCallback);

    void saveTask(Task task);

    void getHistory(LoadTranslationCallback callback);

    interface LoadTranslationCallback {

        void onLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }
}
