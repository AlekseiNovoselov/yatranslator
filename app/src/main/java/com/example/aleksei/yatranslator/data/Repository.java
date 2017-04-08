package com.example.aleksei.yatranslator.data;

import android.support.annotation.NonNull;

public class Repository {

    private static Repository INSTANCE = null;

    private final DataSource mRemoteDataSource;
    private final DataSource mLocalDataSource;
    // Prevent direct instantiation.
    private Repository(@NonNull DataSource tasksRemoteDataSource,
                            @NonNull DataSource tasksLocalDataSource) {
        mRemoteDataSource = tasksRemoteDataSource;
        mLocalDataSource = tasksLocalDataSource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource  the device storage data source
     * @return the {@link Repository} instance
     */
    public static Repository getInstance(DataSource tasksRemoteDataSource,
                                              DataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }

    public long getTranslation(Task task, @NonNull final DataSource.LoadTranslationCallback callback) {
        return getTasksFromRemoteDataSource(task, callback);
    }

    private long getTasksFromRemoteDataSource(Task task, @NonNull final DataSource.LoadTranslationCallback callback) {
        return mRemoteDataSource.getTranslation(task, new DataSource.LoadTranslationCallback() {
            @Override
            public void onLoaded(String text) {
                callback.onLoaded(text);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
