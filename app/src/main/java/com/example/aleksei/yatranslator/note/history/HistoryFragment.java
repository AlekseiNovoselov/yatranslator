package com.example.aleksei.yatranslator.note.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksei.yatranslator.R;
import com.example.aleksei.yatranslator.data.Task;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements HistoryContract.View {

    private HistoryContract.Presenter mPresenter;

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_fragment, null);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        adapter = new HistoryAdapter(new ArrayList<Task>(0));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void showTasks(List<Task> tasks) {
        adapter.replaceData(tasks);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
}
