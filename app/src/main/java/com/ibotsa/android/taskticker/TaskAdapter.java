package com.ibotsa.android.taskticker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<Task> mTaskList;
    private int mPositionSelectedTask;

    private ArrayList<Observer> mObserverList;

    public TaskAdapter(ArrayList<Task> taskList) {
        mTaskList = taskList;
        mPositionSelectedTask = -1;
        mObserverList = new ArrayList<Observer>();
    }

    public Task getSelectedTask() {
        if (mPositionSelectedTask >= 0) {
            return mTaskList.get(mPositionSelectedTask);
        }
        return null;
    }

    private void selectTask(int positionSelectedTask) {
        if (mPositionSelectedTask != positionSelectedTask) {
            if(mPositionSelectedTask != -1 ) {
                notifyItemChanged(mPositionSelectedTask);
            }
            mPositionSelectedTask = positionSelectedTask;
        } else {
            mPositionSelectedTask = -1;
        }
        if(positionSelectedTask != -1 ) {
            notifyItemChanged(positionSelectedTask);
        }
        notifySelectedTaskChanged();
    }

    public void deselectTask() {
        selectTask(-1);
    }

    public void add(Task task) {
        add(getItemCount(), task);
    }

    public void add(int position, Task task) {
        mTaskList.add(position, task);
        notifyItemInserted(position);
    }

    public void remove(Task task) {
        int position = mTaskList.indexOf(task);
        mTaskList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tasklist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task task = mTaskList.get(position);

        holder.setTask(task);

        holder.layoutTask.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectTask(position);
                return true;
            }
        });

        holder.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTask(position);
            }
        });

        holder.imgCategory.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), task.getType().getCategory().getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.imgType.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), task.getType().getName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutTask;
        private ImageView imgCategory;
        private ImageView imgType;
        private TextView txtTimeRemaining;
        private TextView txtTime;

        public ViewHolder(View v) {
            super(v);
            layoutTask = (RelativeLayout) v.findViewById(R.id.layoutTask);
            imgCategory = (ImageView) v.findViewById(R.id.iconCategory);
            imgType = (ImageView) v.findViewById(R.id.iconType);
            txtTimeRemaining = (TextView) v.findViewById(R.id.textTimeRemaining);
            txtTime = (TextView) v.findViewById(R.id.textTime);
        }

        public void setTask(Task task) {
            if (getSelectedTask() == task) {
                imgCategory.setImageResource(task.getType().getCategory().getIconSelected());
            } else {
                imgCategory.setImageResource(task.getType().getCategory().getIcon());
            }
            imgCategory.setContentDescription(task.getType().getCategory().getName());
            imgType.setImageResource(task.getType().getIcon());
            imgType.setContentDescription(task.getType().getName());
            setSecondsRemaining(task.getSecondsRemaining());
        }

        public void setSecondsRemaining(long secondsRemaining) {
            txtTimeRemaining.setText(TimeHelper.getTimeRemaining(secondsRemaining));
            txtTime.setText(TimeHelper.getTime(secondsRemaining));
        }
    }

    public void registerObserver(Observer observer) {
        mObserverList.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        mObserverList.remove(observer);
    }

    private void notifySelectedTaskChanged() {
        for (Observer observer : mObserverList) {
            observer.taskListChanged(Observer.CHANGED_SELECTED_TASK);
        }
    }

    public interface Observer {
        public static final int CHANGED_SELECTED_TASK = 1;

        public abstract void taskListChanged(int changed);

    }

}