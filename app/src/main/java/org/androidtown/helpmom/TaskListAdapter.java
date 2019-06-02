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
        Log.d("taskList", this.taskList.isEmpty()+"");
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

        View resultView=null;



        // 6-02: 100%이면 task_progress_complete.xml 파일을 레이아웃으로 씀.(빨간배경)
        if(taskList.get(i).getProgress().equals("100")){
            View v = View.inflate(context,R.layout.task_prgoress_complete,null);

            TextView taskName = (TextView)v.findViewById(R.id.taskName);
            Log.d("taskName", "task name"+R.id.taskName+"");
            TextView progress = (TextView)v.findViewById(R.id.progress);
            TextView changed = (TextView)v.findViewById(R.id.changed);
            TextView date = (TextView)v.findViewById(R.id.date);

            // 6-02 추가: progress가 100 일 경우 할 일이름 앞에 "(완료)" 붙이기
            taskName.setText("(완료)"+taskList.get(i).getTaskName());

            progress.setText(taskList.get(i).getProgress());
            changed.setText(taskList.get(i).getChangedName());
            date.setText(taskList.get(i).getCreated());

            resultView = v;
        }
        // 6-02 : 진행률이 0보다 크고 100미만이면 이 레이아웃을 사용(초록배경)
        else if (!taskList.get(i).getProgress().equals("0") && !taskList.get(i).getProgress().equals("100")){
            View v = View.inflate(context,R.layout.task_progress_not_complete,null);

            TextView taskName = (TextView)v.findViewById(R.id.taskName);
            Log.d("taskName", "task name"+R.id.taskName+"");
            TextView progress = (TextView)v.findViewById(R.id.progress);
            TextView changed = (TextView)v.findViewById(R.id.changed);
            TextView date = (TextView)v.findViewById(R.id.date);

            // 6-02 : (진행중) 앞에 붙이기.
            taskName.setText("(진행중)" + taskList.get(i).getTaskName());


            progress.setText(taskList.get(i).getProgress());


            changed.setText(taskList.get(i).getChangedName());
            date.setText(taskList.get(i).getCreated());

            resultView = v;
        }

        // 6-02 : 진행률이 0%이면 흰색배경의 레이아웃을 씀.
        else{
            View v = View.inflate(context,R.layout.task_progress_unstarted,null);

            TextView taskName = (TextView)v.findViewById(R.id.taskName);
            Log.d("taskName", "task name"+R.id.taskName+"");
            TextView progress = (TextView)v.findViewById(R.id.progress);
            TextView changed = (TextView)v.findViewById(R.id.changed);
            TextView date = (TextView)v.findViewById(R.id.date);

            taskName.setText(taskList.get(i).getTaskName());
            progress.setText(taskList.get(i).getProgress());


            changed.setText(taskList.get(i).getChangedName());
            date.setText(taskList.get(i).getCreated());

            resultView = v;

        }



        return resultView;
    }
}
