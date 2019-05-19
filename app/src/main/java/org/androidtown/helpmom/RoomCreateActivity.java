package org.androidtown.helpmom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RoomCreateActivity extends AppCompatActivity {

    Button btn_createRoom, btn_cancel;
    EditText editText_roomName, editText_pw, editText_pwRe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_create);

        // View 로딩 - 버튼2개, editText 2개
        btn_createRoom = (Button)findViewById(R.id.btn_create);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        editText_roomName = (EditText)findViewById(R.id.editText_roomNameInput);
        editText_pw = (EditText)findViewById(R.id.editText_roomPwInput);
        editText_pwRe = (EditText)findViewById(R.id.editText_roomPwInputRe);

        // 방생성버튼 이벤트리스너
        btn_createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 취소버튼 이벤트리스너
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
