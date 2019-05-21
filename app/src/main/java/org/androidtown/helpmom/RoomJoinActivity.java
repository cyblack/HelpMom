package org.androidtown.helpmom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class RoomJoinActivity extends AppCompatActivity {
    EditText joinID;
    EditText joinName;
    EditText joinPW;
    Button joinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_room_join);

        joinName=(EditText)findViewById(R.id.joinName);
        joinPW=(EditText)findViewById(R.id.joinPW);
        joinID=(EditText)findViewById(R.id.joinID);
        joinBtn=(Button)findViewById(R.id.joinBtn);

        joinBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //TODO: ENROLL ON DATABASE
                finish();
            }
        });
    }
}
