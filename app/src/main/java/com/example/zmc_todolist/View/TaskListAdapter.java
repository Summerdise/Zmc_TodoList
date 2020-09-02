package com.example.zmc_todolist.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zmc_todolist.Controller.DateFormat;
import com.example.zmc_todolist.Controller.TaskListAdapterController;
import com.example.zmc_todolist.Model.DB.Task;
import com.example.zmc_todolist.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    TasksActivity tasksActivity;
    Context context;
    List<Task> tasksList;
    TaskListAdapterController taskListAdapterController = new TaskListAdapterController(this);


    RecyclerView mRecyclerView;
    OnItemClickListener listener;

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public TaskListAdapter(TasksActivity tasksActivity, Context context, List<Task> tasksList) {
        this.tasksActivity = tasksActivity;
        this.context = context;
        this.tasksList = tasksList;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position, int id);
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
        ((TaskHolder) holder).listIsComplete.setOnCheckedChangeListener((compoundButton, completeFlag) -> {
            if (completeFlag) {
                task.setComplete(true);
            } else {
                task.setComplete(false);
            }
            if (!mRecyclerView.isComputingLayout() && mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                taskListAdapterController.getDatabase().taskDao().update(task);
                getTaskList();
                notifyDataSetChanged();
            }
        });
        ((TaskHolder) holder).listTaskTitle.setOnClickListener(view -> {
            if (listener != null) {
                listener.OnItemClick(position, task.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    public Context getContext() {
        return context;
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
        tasksList.addAll(taskListAdapterController.getDatabase().taskDao().getCompleted());
        tasksList.addAll(taskListAdapterController.getDatabase().taskDao().getNotCompleted());
    }
}
