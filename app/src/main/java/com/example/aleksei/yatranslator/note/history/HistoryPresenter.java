package com.example.aleksei.yatranslator.note.history;

import android.support.annotation.NonNull;

import com.example.aleksei.yatranslator.data.DataSource;
import com.example.aleksei.yatranslator.data.Repository;
import com.example.aleksei.yatranslator.data.Task;

import java.util.List;

public class HistoryPresenter implements HistoryContract.Presenter {

    private final Repository mRepository;
    private final HistoryContract.View mHistoryView;

    public HistoryPresenter(@NonNull Repository repository, @NonNull HistoryContract.View historyView) {
        mRepository = repository;
        mHistoryView = historyView;
        mHistoryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadHistory();
    }

    private void loadHistory() {
        mRepository.getHistory(new DataSource.LoadTranslationCallback() {
            @Override
            public void onLoaded(List<Task> tasks) {
                mHistoryView.showTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadTasks() {

    }
}
