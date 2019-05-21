package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {

    ListView listView_joinedRoom;
    Button btn_createRoom, btn_joinRoom, btn_logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // get Views
        btn_createRoom = (Button) findViewById(R.id.btn_createRoom);
        btn_joinRoom = (Button) findViewById(R.id.btn_joinRoom);
        btn_logOut = (Button) findViewById(R.id.btn_logout_lobby);
        listView_joinedRoom = (ListView) findViewById(R.id.listView_roomList);

        // 참가된 방 리스트 data.
        List<String> joinedRoomList = new ArrayList<>();
        joinedRoomList.add("A방");
        joinedRoomList.add("B방");


        // arrayAdaper로 String을 listView에 바인딩, data를 listView에 display함.
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, joinedRoomList);
        listView_joinedRoom.setAdapter(arrayAdapter);

        btn_createRoom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(LobbyActivity.this, RoomCreateActivity.class));
            }
        });

        btn_joinRoom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LobbyActivity.this, RoomJoinActivity.class));
            }
        });

    }

}
