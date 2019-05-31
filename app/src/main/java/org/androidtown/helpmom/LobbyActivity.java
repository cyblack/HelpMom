package org.androidtown.helpmom;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LobbyActivity extends AppCompatActivity {
    Button btn_createRoom, btn_joinRoom;
    String _id;
    TextView userId,userName;

    private RoomListAdapter roomAdapter;

    private ListView listView_joinedRoom;

    private List<Room> joinedRoomList;

    private HashMap<String,String> hashMap,roomMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // 참가된 방 리스트 data.

        Intent intent = getIntent();
        _id = intent.getStringExtra("id"); //내아이디
        String name=intent.getStringExtra("name");
        final String[] roomList = intent.getStringArrayExtra("roomList");
        hashMap=(HashMap<String,String>)intent.getSerializableExtra("hashMap");
        roomMaker=(HashMap<String,String>)intent.getSerializableExtra("roomMaker");

        //onRequestRoomList();

        setTitle("로비");
        // get Views
        btn_createRoom = findViewById(R.id.btn_createRoom);
        btn_joinRoom = findViewById(R.id.btn_joinRoom);
        listView_joinedRoom = findViewById(R.id.listView_roomList);
        userId=findViewById(R.id.userId);
        userName=findViewById(R.id.userName);
        joinedRoomList = new ArrayList<Room>();
        roomAdapter=new RoomListAdapter(LobbyActivity.this, joinedRoomList, _id);

        //onRequestRoomList();
        listView_joinedRoom.setAdapter(roomAdapter);
        userId.setText(_id);
        userName.setText(name);
        for(int i=0;i<roomList.length;i++){
            Room r = new Room(roomList[i],roomMaker.get(roomList[i]),hashMap.get(roomList[i]));
            joinedRoomList.add(r);
        }
        // arrayAdaper로 String을 listView에 바인딩, data를 listView에 display함.


        listView_joinedRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                onRequestRoomList(joinedRoomList.get(position).getRoomNumber(),joinedRoomList.get(position).getRoomName());

            }
        });

        btn_createRoom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent createRoom = new Intent(LobbyActivity.this, RoomCreateActivity.class);
                createRoom.putExtra("id",_id);
                startActivityForResult(createRoom,1);
            }
        });

        btn_joinRoom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent joinRoom=new Intent(LobbyActivity.this,RoomJoinActivity.class);
                joinRoom.putExtra("id", _id);//내아이디 넘겨줌
                startActivityForResult(joinRoom, 1);
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if(requestCode==1){
            if(resultCode==1) {
                //Toast.makeText(getApplicationContext(), "lobbyResult " + resultCode, Toast.LENGTH_SHORT).show();
                String rNum = data.getStringExtra("roomNumber");
                Room r = new Room(data.getStringExtra("roomName"),_id,rNum);
                hashMap.put(data.getStringExtra("roomName"),rNum);
                roomMaker.put(data.getStringExtra("roomName"),_id);

                joinedRoomList.add(r);
                roomAdapter.notifyDataSetChanged();

                //room만드려면 이름과 방만든사람이 필요함.

            }else if(resultCode==2){
                String rNum = data.getStringExtra("roomNumber");
                Room r = new Room(data.getStringExtra("roomName"),data.getStringExtra("maker"),rNum);
                hashMap.put(data.getStringExtra("roomName"),rNum);
                roomMaker.put(data.getStringExtra("roomName"),data.getStringExtra("maker"));

               // Log.d("value" ,hashMap.get(data.getStringExtra("roomName")));
                joinedRoomList.add(r);
                roomAdapter.notifyDataSetChanged();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                startActivity(new Intent(LobbyActivity.this,LoginActivity.class));
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_LONG).show();
                finish();
        }
        return true;
    }

    private void onRequestRoomList(String rn,String rN){

        final String rNum = rn;
        final String rName = rN;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getListRoom(_id);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();
                String[] roomList = r.getRoomName();
                String[] roomNumber= r.getRoomNumber();
                String[] roomMaker = r.getRoomMaker();

                boolean isthere = false;
                for(int i=0;i<roomList.length;i++){
                    if(roomNumber[i].equals(rNum)){
                        isthere = true;
                        break;
                    }
                }
                if(!isthere){
                    Toast.makeText(getApplicationContext(),"방이 존재 하지 않습니다",Toast.LENGTH_SHORT).show();
                    listChange(roomList,roomNumber,roomMaker); //리스트 다시보여주기
                }else{
                    next(rName);
                }
            }
            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });

    }

    private void next(String roomName){

        Intent groupIntent=new Intent(LobbyActivity.this,GroupActivity.class);
        String rName = roomName;
        groupIntent.putExtra("name", rName);
        groupIntent.putExtra("roomNumber", hashMap.get(rName));
        groupIntent.putExtra("leader",roomMaker.get(rName));
        groupIntent.putExtra("myId",_id);
        //방이름과 방번호와 방장 넘겨줌
        startActivity(groupIntent);
    }
    private void listChange(String[] rl, String[] rn, String[] rm){
        joinedRoomList.clear();
        for(int i=0;i<rl.length;i++){
            Room r = new Room(rl[i],rm[i],rn[i]);
            joinedRoomList.add(r);
        }
        roomAdapter.notifyDataSetChanged();

    }
}
