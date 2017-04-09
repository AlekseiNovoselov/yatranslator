package com.example.aleksei.yatranslator.note.history;

import com.example.aleksei.yatranslator.BasePresenter;
import com.example.aleksei.yatranslator.BaseView;
import com.example.aleksei.yatranslator.data.Task;

import java.util.List;

public interface HistoryContract {
    interface View extends BaseView<HistoryContract.Presenter> {
        void showTasks(List<Task> tasks);
    }

    interface Presenter extends BasePresenter {
        void loadTasks();
    }
}
