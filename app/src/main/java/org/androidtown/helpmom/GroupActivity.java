package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
    Button taskListButton;
    String _id,roomName;
    private List<Member>  joinedMemberList;
    private MemberListAdapter memberAdapter;
    List<String>  confirmedTaskList;

    ArrayAdapter<String> arrayAdapter2;
    String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        memberList=findViewById(R.id.memberList);
        taskList=findViewById(R.id.taskList);
        taskListButton=findViewById(R.id.taskListBtn);

        Intent intent=getIntent();
        roomName=intent.getStringExtra("name");
        roomNumber = intent.getStringExtra("roomNumber");
        setTitle(roomNumber);
        _id=intent.getStringExtra("leader");

        joinedMemberList=new ArrayList<Member>();
        confirmedTaskList=new ArrayList<>();

        memberAdapter = new MemberListAdapter(getApplicationContext(),joinedMemberList,_id);
        //arrayAdapter = new ArrayAdapter<>(GroupActivity.this, android.R.layout.simple_list_item_1, joinedMemberList);
        arrayAdapter2= new ArrayAdapter<>(GroupActivity.this, android.R.layout.simple_list_item_1, confirmedTaskList);

        onRequestMemberList();
        onRequestTaskList();


        memberList.setAdapter(memberAdapter);
        taskList.setAdapter(arrayAdapter2);

        taskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskIntent=new Intent(GroupActivity.this,ManageTaskActivty.class);
                taskIntent.putExtra("roomNumber", roomNumber);
                startActivityForResult(taskIntent,2);
            }
        });

        memberAdapter.notifyDataSetChanged();
        arrayAdapter2.notifyDataSetChanged();
    }

    private void onRequestMemberList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getMember(roomNumber);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();
                String[] memberList=r.getMember();
                if(memberList.length==0)
                {
                    return;
                }

                Toast.makeText(getApplicationContext(), memberList[0]+memberList.length, Toast.LENGTH_LONG).show();
                for(int i=0;i<memberList.length;i++){
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

    private void onRequestTaskList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getTask(roomNumber);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();

                String[] taskList=r.getTask();

                if(taskList.length==0)
                {
                    return;
                }

                Toast.makeText(getApplicationContext(), taskList[0]+taskList.length, Toast.LENGTH_LONG).show();

                for(int i=0;i<taskList.length;i++){
                    confirmedTaskList.add(taskList[i]);
                    Log.d("ddd",taskList[i]+taskList.length);
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

    private void check1(){
        memberAdapter.notifyDataSetChanged();
    }

    private void check2(){

        arrayAdapter2.notifyDataSetChanged();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if (resultCode == 1) {

                // TODO: Implement successful signup logic here
                ArrayList<String> task=(ArrayList<String>)data.getSerializableExtra("task");
                for(int i=0; i<task.size();i++){
                    confirmedTaskList.add(task.get(i));
                }
                arrayAdapter2.notifyDataSetChanged();
            }
        }
    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                startActivity(new Intent(GroupActivity.this,LoginActivity.class));
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_LONG).show();
                finish();
        }
        return true;
    }
}
