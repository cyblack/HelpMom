package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {
    Button btn_createRoom, btn_joinRoom;
//    TextView id,nickname;
    String _id,leader;


    private RoomListAdapter roomAdapter;

    private ListView listView_joinedRoom;
    private RoomListAdapter adapter;
    private List<Room> joinedRoomList;

    private HashMap<String,String> hashMap,roomMaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // 참가된 방 리스트 data.

        Intent intent = getIntent();
        _id = intent.getStringExtra("id"); //내아이디
        final String[] roomList = intent.getStringArrayExtra("roomList");
        hashMap=(HashMap<String,String>)intent.getSerializableExtra("hashMap");
        roomMaker=(HashMap<String,String>)intent.getSerializableExtra("roomMaker");

        //onRequestRoomList();

        setTitle("로비");
        // get Views
        btn_createRoom = findViewById(R.id.btn_createRoom);
        btn_joinRoom = findViewById(R.id.btn_joinRoom);
        listView_joinedRoom = findViewById(R.id.listView_roomList);
        joinedRoomList = new ArrayList<Room>();
        roomAdapter=new RoomListAdapter(LobbyActivity.this, joinedRoomList, _id);
        Room room1=new Room("동주네 집","장동주");
        Room room2=new Room("형섭이네 집","송형섭");
        Room room3=new Room("원식이네 집","신원식");
        joinedRoomList.add(room1);
        joinedRoomList.add(room2);
        joinedRoomList.add(room3);

        //onRequestRoomList();
        listView_joinedRoom.setAdapter(roomAdapter);
//        id = findViewById(R.id.txtView_lobby_userID);
//        nickname = findViewById(R.id.txtView_lobby_userNickName);


        for(int i=0;i<roomList.length;i++){
            Room r = new Room(roomList[i],roomMaker.get(roomList[i]));
            joinedRoomList.add(r);

        }
//        id.setText(_id);
//        nickname.setText(intent.getStringExtra("name"));

        // arrayAdaper로 String을 listView에 바인딩, data를 listView에 display함.



        listView_joinedRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent groupIntent=new Intent(LobbyActivity.this,GroupActivity.class);
                String rName = joinedRoomList.get(position).getRoomName();
                groupIntent.putExtra("name", rName);
                groupIntent.putExtra("roomNumber", hashMap.get(rName));
                groupIntent.putExtra("leader",roomMaker.get(rName));
                groupIntent.putExtra("myId",_id);
                //방이름과 방번호와 방장 넘겨줌
                startActivity(groupIntent);
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

                Room r = new Room(data.getStringExtra("roomName"),_id);
                String rNum = data.getStringExtra("roomNumber");
                hashMap.put(data.getStringExtra("roomName"),rNum);
                roomMaker.put(data.getStringExtra("roomName"),_id);

                joinedRoomList.add(r);
                adapter.notifyDataSetChanged();

                //room만드려면 이름과 방만든사람이 필요함.

            }else if(resultCode==2){
                Room r = new Room(data.getStringExtra("roomName"),data.getStringExtra("maker"));
                String rNum = data.getStringExtra("roomNumber");
                hashMap.put(data.getStringExtra("roomName"),rNum);
                roomMaker.put(data.getStringExtra("roomName"),data.getStringExtra("maker"));

               // Log.d("value" ,hashMap.get(data.getStringExtra("roomName")));
                joinedRoomList.add(r);
                adapter.notifyDataSetChanged();


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
}
