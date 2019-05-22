package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {
    ListView memberList, taskList;
    Button taskListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        memberList=(ListView)findViewById(R.id.memberList);
        taskList=(ListView)findViewById(R.id.taskList);
        taskListButton=(Button)findViewById(R.id.taskListBtn);

        Intent intent=getIntent();
        setTitle(intent.getStringExtra("name"));

        taskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 할일 목록 창 띄우기
            }
        });
    }
}
