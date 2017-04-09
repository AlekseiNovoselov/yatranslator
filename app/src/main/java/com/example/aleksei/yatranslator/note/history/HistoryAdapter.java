package com.example.aleksei.yatranslator.note.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleksei.yatranslator.R;
import com.example.aleksei.yatranslator.data.Task;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<Task> mTasks;

    public HistoryAdapter(List<Task> tasks) {
        mTasks = tasks;
        mTasks = tasks;
    }

    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.MyViewHolder holder, int position) {
        Task task = mTasks.get(position);
        holder.sourceText.setText(task.getSourceText());
        holder.resultText.setText(task.getResultText());
        holder.languages.setText("En-Ru");
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void replaceData(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView favotiresIcon;
        TextView sourceText;
        TextView resultText;
        TextView languages;
        public MyViewHolder(View itemView) {
            super(itemView);
            favotiresIcon = (ImageView) itemView.findViewById(R.id.favorites);
            sourceText = (TextView) itemView.findViewById(R.id.source_text);
            resultText = (TextView) itemView.findViewById(R.id.result_text);
            languages = (TextView) itemView.findViewById(R.id.languages);
        }
    }
}
