package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LobbyActivity extends AppCompatActivity {

    ListView listView_joinedRoom;
    Button btn_createRoom, btn_joinRoom, btn_logOut;
    TextView id,nickname;
    private String _id;
    private  List<String> joinedRoomList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        // get Views
        btn_createRoom = (Button) findViewById(R.id.btn_createRoom);
        btn_joinRoom = (Button) findViewById(R.id.btn_joinRoom);
        btn_logOut = (Button) findViewById(R.id.btn_logout_lobby);
        listView_joinedRoom = (ListView) findViewById(R.id.listView_roomList);
        id = (TextView)findViewById(R.id.txtView_lobby_userID);
        nickname = (TextView)findViewById(R.id.txtView_lobby_userNickName);

        Intent intent = getIntent();
        _id = intent.getStringExtra("id").toString();
        id.setText(_id);
        nickname.setText(intent.getStringExtra("name").toString());


        // 참가된 방 리스트 data.
        joinedRoomList = new ArrayList<>();
        onRequestRoomList();


//        joinedRoomList.add("A방");
//        joinedRoomList.add("B방");


        // arrayAdaper로 String을 listView에 바인딩, data를 listView에 display함.
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, joinedRoomList);
        listView_joinedRoom.setAdapter(arrayAdapter);

        listView_joinedRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent groupIntent=new Intent(LobbyActivity.this,GroupActivity.class);
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
                startActivity(new Intent(LobbyActivity.this, RoomJoinActivity.class));
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
            if(resultCode==1){
                joinedRoomList.add(data.getStringExtra("addRoomName"));
            }
        }
    }


    private void onRequestRoomList(){
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

                for(int i=0;i<roomList.length;i++){
                    joinedRoomList.add(roomList[i]);
                }
            }
            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });
    }

}
