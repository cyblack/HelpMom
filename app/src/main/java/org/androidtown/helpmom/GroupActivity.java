package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    List<String>  joinedMemberList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        memberList=findViewById(R.id.memberList);
        taskList=findViewById(R.id.taskList);
        taskListButton=findViewById(R.id.taskListBtn);

        Intent intent=getIntent();
        roomName=intent.getStringExtra("name");
        setTitle(roomName);
        _id=intent.getStringExtra("id");

        joinedMemberList=new ArrayList<>();
        onRequestMemberList();

        arrayAdapter = new ArrayAdapter<String>(GroupActivity.this, android.R.layout.simple_list_item_1, joinedMemberList);
        memberList.setAdapter(arrayAdapter);

        taskListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 할일 목록 창 띄우기
                Intent taskIntent=new Intent(GroupActivity.this,ManageTaskActivty.class);
                startActivityForResult(taskIntent, 2);
            }
        });
    }

    private void onRequestMemberList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getMember(roomName);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();

                String[] memberList=r.getMember();
                Toast.makeText(getApplicationContext(), memberList[0]+memberList.length, Toast.LENGTH_LONG).show();
                for(int i=0;i<memberList.length;i++){
                    joinedMemberList.add(memberList[i]);
                }

                arrayAdapter.notifyDataSetChanged();
                Log.d("ddd",joinedMemberList.get(0));
            }
            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });
    }
}
