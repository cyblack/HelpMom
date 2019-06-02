package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupActivity extends AppCompatActivity {
    ListView memberList, taskList;
    Button manageTaskListButton;
    String myid, roomName, leader,roomNumber;

    private List<Member> joinedMemberList;
    private MemberListAdapter memberAdapter;

    private List<Task> confirmedTaskList;
    private TaskListAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        memberList = findViewById(R.id.memberList);
        taskList = findViewById(R.id.taskList);
        manageTaskListButton = findViewById(R.id.taskListBtn);
        registerForContextMenu(taskList);

        Intent intent = getIntent();
        roomName = intent.getStringExtra("name");
        roomNumber = intent.getStringExtra("roomNumber");
        leader = intent.getStringExtra("leader");
        myid = intent.getStringExtra("myId");
        setTitle(roomName);

        Log.d("leader", leader);
        Log.d("roomNumber", roomNumber);

        if (!myid.equals(leader)) {//리더가 아니면 안보이게하기
            manageTaskListButton.setVisibility(View.INVISIBLE);
        }

        joinedMemberList = new ArrayList<Member>();
        confirmedTaskList = new ArrayList<Task>();

        // 추가된코드: confirmedTskList에 아무거나 하나 넣어보자.
        confirmedTaskList.add(new Task("설거지(아침)", "50%", "좋다" ,"70점","엄마",
                "오후 3시"));

        memberAdapter = new MemberListAdapter(getApplicationContext(), joinedMemberList, leader,myid);
        taskAdapter = new TaskListAdapter(getApplication(),confirmedTaskList);


        onRequestMemberList();
        onRequestTaskList();

        memberList.setAdapter(memberAdapter);
        taskList.setAdapter(taskAdapter);

        manageTaskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskIntent = new Intent(GroupActivity.this, ManageTaskActivty.class);
                taskIntent.putExtra("roomNumber", roomNumber);
                startActivityForResult(taskIntent, 2);
            }
        });

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(GroupActivity.this,TaskResultActivity.class);
                intent.putExtra("comment",confirmedTaskList.get(i).getComment());
                startActivity(intent);
            }
        });
        memberAdapter.notifyDataSetChanged();
        taskAdapter.notifyDataSetChanged();
}

    private void onRequestMemberList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getMember(roomNumber);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();
                String[] memberList = r.getMember();
                if (memberList.length == 0) {
                    return;
                }
                for (int i = 0; i < memberList.length; i++) {
                    Member m = new Member(memberList[i]);
                    joinedMemberList.add(m);
                }
                check1();
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });
    }

    //TaskList 가져오기
    private void onRequestTaskList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getTask(roomNumber);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();

                String[] taskList = r.getTask();
                String[] taskId = r.getTaskId();
                String[] progress = r.getProgress();
                String[] comment = r.getComment();
                String[] created = r.getCreated();
                String[] point = r.getPoint();
                String[] changedName = r.getChangedName();

                if (taskList.length == 0) {
                    return;
                }

                for (int i = 0; i < taskList.length; i++) {
                    String c =created[i];
                    String cn = changedName[i];
                    if(cn.equals("none")){
                        cn = "변경한 사람이 없습니다.";
                        c ="변경한 사람이 없습니다.";
                    }
                    Log.d("list",taskList[i]+" "+" "+progress[i]+" "+ comment[i]+" " +point[i]+" " +changedName[i]+" "+created[i]);
                    Task task = new Task(taskList[i],progress[i],comment[i],point[i],cn,c);
                    confirmedTaskList.add(task);
                }
                check2();
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });

    }

    //Task삭제 구현함수
    private void onDeleteTask() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        //수정필요.
        Call<RegisterResult> call = service.getTask(roomNumber);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();

            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });

    }

    private void check1() {
        memberAdapter.notifyDataSetChanged();
    }

    private void check2() {
        taskAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if (resultCode == 1) {

                // TODO: Implement successful signup logic here
                ArrayList<Task> task = (ArrayList<Task>) data.getSerializableExtra("task");
           //     Log.d("qwer",task.get(0).getTaskName());

                for (int i = 0; i < task.size(); i++) {
                    String c =task.get(i).getCreated();
                    String cn = task.get(i).getChangedName();
                    if(task.get(i).getCreated().equals("none")){
                        cn = "변경한 사람이 없습니다.";
                        c ="변경한 사람이 없습니다.";
                    }
                    Task t = new Task(task.get(i).getTaskName(),task.get(i).getProgress()
                            ,task.get(i).getComment(),task.get(i).getPoint(),cn,c);
                    confirmedTaskList.add(t);
                }
              taskAdapter.notifyDataSetChanged();
            }
            else if(resultCode==2){ //point와 feedback 돌아옴
                String p = data.getStringExtra("point");
                String f = data.getStringExtra("feedback");
                String tn = data.getStringExtra("taskName");
                //taskName이 프라이머리 키다.
                for(int i=0;i<confirmedTaskList.size();i++){
                    if(confirmedTaskList.get(i).getTaskName().equals(tn)){
                         confirmedTaskList.get(i).setComment(f);
                         confirmedTaskList.get(i).setPoint(p);
                         break;
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }else if(resultCode==3){//progress변경
                String p = data.getStringExtra("progress");
                String tn = data.getStringExtra("taskName");
                String nw = data.getStringExtra("now");
                Log.d("output",p);
                //taskName이 프라이머리 키다.
                for(int i=0;i<confirmedTaskList.size();i++){
                    if(confirmedTaskList.get(i).getTaskName().equals(tn)){
                        confirmedTaskList.get(i).setProgress(p);
                        confirmedTaskList.get(i).setChangedName(myid);
                        confirmedTaskList.get(i).setCreated(nw);
                        break;
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(GroupActivity.this, LoginActivity.class));
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_LONG).show();
                finish();
        }
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v,

                                    ContextMenu.ContextMenuInfo menuInfo) {

        // TODO Auto-generated method stub

        //res폴더의 menu플더안에 xml로 MenuItem추가하기.

        //mainmenu.xml 파일을 java 객체로 인플레이트(inflate)해서 menu객체에 추가

        getMenuInflater().inflate(R.menu.task_menu, menu);


        super.onCreateContextMenu(menu, v, menuInfo);

    }

    public boolean onContextItemSelected(MenuItem item) {


        //AdapterContextMenuInfo

        //AdapterView가 onCreateContextMenu할때의 추가적인 menu 정보를 관리하는 클래스

        //ContextMenu로 등록된 AdapterView(여기서는 Listview)의 선택된 항목에 대한 정보를 관리하는 클래스

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();


        int index = info.position; //AdapterView안에서 ContextMenu를 보여즈는 항목의 위치


        //선택된 ContextMenu의  아이템아이디를 구별하여 원하는 작업 수행

        //예제에서는 선택된 ListView의 항목(String 문자열) data와 해당 메뉴이름을 출력함

        switch (item.getItemId()) {


            case R.id.performTask:

                Intent intent2=new Intent(GroupActivity.this, PerformTaskActivity.class);
                intent2.putExtra("taskName",confirmedTaskList.get(index).getTaskName());
                intent2.putExtra("myId",myid);

                startActivityForResult(intent2,2);
                //startActivity(new Intent(GroupActivity.this,PerformTaskActivity.class));
                break;


            case R.id.evaluateTask:
                if(myid.equals(leader))
                {
                    Intent intent=new Intent(GroupActivity.this, EvaluateActivity.class);
                    intent.putExtra("taskName",confirmedTaskList.get(index).getTaskName());
                    startActivityForResult(intent,2);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "리더만 평가할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.deleteTask:
                if(myid.equals(leader))
                {
                    //TASK삭제 구현하기
                    onDeleteTask();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "리더만 삭제할 수 있습니다.", Toast.LENGTH_LONG).show();
                }
                break;


        }
        return true;

    }
}

