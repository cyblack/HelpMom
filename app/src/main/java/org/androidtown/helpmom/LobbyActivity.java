package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LobbyActivity extends AppCompatActivity {
    Button btn_createRoom, btn_joinRoom, btn_logOut;
    TextView id,nickname;
    String _id;

    //List<String> joinedRoomList;
    //ArrayAdapter<String> arrayAdapter;

    private ListView listView_joinedRoom;
    private RoomListAdapter adapter;
    private List<Room> joinedRoomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // 참가된 방 리스트 data.



        //onRequestRoomList();

        setTitle("로비");
        // get Views
        btn_createRoom = findViewById(R.id.btn_createRoom);
        btn_joinRoom = findViewById(R.id.btn_joinRoom);
        btn_logOut = findViewById(R.id.btn_logout_lobby);
        listView_joinedRoom = findViewById(R.id.listView_roomList);
        joinedRoomList = new ArrayList<Room>();
        adapter = new RoomListAdapter(getApplicationContext(),joinedRoomList);
        listView_joinedRoom.setAdapter(adapter);
        id = findViewById(R.id.txtView_lobby_userID);
        nickname = findViewById(R.id.txtView_lobby_userNickName);

        Intent intent = getIntent();
        _id = intent.getStringExtra("id");
        final String[] roomList = intent.getStringArrayExtra("roomList");
        final HashMap<String,String> hashMap=(HashMap<String,String>)intent.getSerializableExtra("hashMap");

        for(int i=0;i<roomList.length;i++){
            Room r = new Room(roomList[i]);
            joinedRoomList.add(r);
        }
        id.setText(_id);
        nickname.setText(intent.getStringExtra("name"));

        // arrayAdaper로 String을 listView에 바인딩, data를 listView에 display함.

        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, joinedRoomList);
        //listView_joinedRoom.setAdapter(arrayAdapter);

        //onRequestRoomList();

        Log.d("request","onCreate");

        listView_joinedRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent groupIntent=new Intent(LobbyActivity.this,GroupActivity.class);
                String rName = joinedRoomList.get(position).getRoomName();
                groupIntent.putExtra("name", rName);
                groupIntent.putExtra("roomNumber", hashMap.get(rName));
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
                joinRoom.putExtra("id", _id);
                startActivityForResult(joinRoom, 1);
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent logoutIntent= new Intent(LobbyActivity.this,LoginActivity.class);
                startActivity(logoutIntent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {

        if(requestCode==1){
            if(resultCode==1) {
                Toast.makeText(getApplicationContext(), "lobbyResult " + resultCode, Toast.LENGTH_SHORT).show();

                //joinedRoomList.add(data.getStringExtra("addRoomName"));
            }
        }
    }
    protected void onStart(){
        super.onStart();
        Log.d("request","onStart");

    }

    protected void onResume() {
        super.onResume();
      //  arrayAdapter.notifyDataSetChanged();
    }
}
