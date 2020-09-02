package com.example.zmc_todolist.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zmc_todolist.ViewModel.DateFormat;
import com.example.zmc_todolist.Model.DB.LocalDatabase;
import com.example.zmc_todolist.Model.DB.Task;
import com.example.zmc_todolist.R;

import java.util.List;


public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LocalDatabase database;
    private List<Task> tasksList;
    RecyclerView mRecyclerView;

    OnItemClickListener listener;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public TaskListAdapter(Context context, LocalDatabase database, List<Task> tasksList) {
        this.context = context;
        this.database = database;
        this.tasksList = tasksList;
    }

    public interface OnItemClickListener {
        public void OnItemClick(int position, int id);
    }

    public void setOnItemClick(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class TaskHolder extends RecyclerView.ViewHolder {
        TextView listTaskTitle;
        CheckBox listIsComplete;
        TextView listDeadline;
        RecyclerView tasksRecyclerView;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            listTaskTitle = itemView.findViewById(R.id.list_task_title);
            listIsComplete = itemView.findViewById(R.id.list_is_complete);
            listDeadline = itemView.findViewById(R.id.list_deadline);
            tasksRecyclerView = itemView.findViewById(R.id.tasks_recycler_view);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Task task = tasksList.get(position);
        ((TaskHolder) holder).listIsComplete.setChecked(task.isComplete());
        String showingTitle = getSimpleTitle(task.getTaskTitle());
        ((TaskHolder) holder).listTaskTitle.setText(showingTitle);
        if (task.isComplete()) {
            ((TaskHolder) holder).listTaskTitle.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ((TaskHolder) holder).listTaskTitle.setTextColor(Color.parseColor("#EEE5DE"));
        } else {
            ((TaskHolder) holder).listTaskTitle.getPaint().setFlags(0);
            ((TaskHolder) holder).listTaskTitle.setTextColor(Color.parseColor("#BEAAAA"));
        }
        ((TaskHolder) holder).listDeadline.setText(new DateFormat().toChineseMonthDay(task.getDeadline()));
        ((TaskHolder) holder).listIsComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean completeFlag) {
                if (completeFlag) {
                    task.setComplete(true);
                } else {
                    task.setComplete(false);
                }
                if (!mRecyclerView.isComputingLayout() && mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    database.taskDao().update(task);
                    getTaskList();
                    notifyDataSetChanged();
                }
            }
        });
        ((TaskHolder) holder).listTaskTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.OnItemClick(position, task.id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public String getSimpleTitle(String originalTitle) {
        if (originalTitle.length() > 25) {
            return originalTitle.substring(0, 24) + "...";
        } else {
            return originalTitle;
        }
    }

    private void getTaskList() {
        tasksList.clear();
        tasksList.addAll(database.taskDao().getCompleted());
        tasksList.addAll(database.taskDao().getNotCompleted());
    }
}
