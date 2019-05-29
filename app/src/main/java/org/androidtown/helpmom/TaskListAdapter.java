package org.androidtown.helpmom;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskListAdapter extends BaseAdapter {

    private Context context;
    private List<Task> taskList;
    public TaskListAdapter(Context context,List<Task> taskList){
        this.taskList = taskList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context,R.layout.task,null);

        TextView taskName = (TextView)v.findViewById(R.id.taskName);
        TextView progress = (TextView)v.findViewById(R.id.progress);
        TextView changed = (TextView)v.findViewById(R.id.changed);
        TextView point = (TextView)v.findViewById(R.id.point);

        taskName.setText(taskList.get(i).getTaskName());
        progress.setText(taskList.get(i).getProgress());
        changed.setText(taskList.get(i).getChangedName());
        point.setText(taskList.get(i).getPoint());

        return v;
    }
}
