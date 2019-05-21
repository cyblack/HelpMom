package org.androidtown.helpmom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class RoomCreateActivity extends AppCompatActivity {

    Button createBtn;
    EditText createName, createPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_room_create);

        Button create=(Button)findViewById(R.id.createBtn);
        EditText createName=(EditText) findViewById(R.id.createName);
        EditText createPW=(EditText)findViewById(R.id.createPW);

        // 방생성버튼 이벤트리스너
    }
}
