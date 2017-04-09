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

    public long getTranslation(Task task) {
        return getTasksFromRemoteDataSource(task);
    }

    private long getTasksFromRemoteDataSource(Task task) {
        return mRemoteDataSource.getTranslation(task, new RemoteLoadListener() {
            @Override
            public void onLoaded(Task task) {
                refreshLocalDataSource(task);
            }
        });
    }

    private void refreshLocalDataSource(Task task) {
        mLocalDataSource.saveTask(task);
    }

    public interface RemoteLoadListener {
        void onLoaded(Task task);
    }
}
