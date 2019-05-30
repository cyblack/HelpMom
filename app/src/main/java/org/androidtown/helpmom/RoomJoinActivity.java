package org.androidtown.helpmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomJoinActivity extends AppCompatActivity {
    EditText joinID;
    EditText joinName;
    EditText joinPW;
    Button joinBtn;
    String myid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_room_join);

        joinName = findViewById(R.id.joinName);
        joinPW = findViewById(R.id.joinPW);
        joinID = findViewById(R.id.joinID);
        joinBtn = findViewById(R.id.joinBtn);
        Intent joinIntent=getIntent();
        myid= joinIntent.getStringExtra("id");

        joinBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: ENROLL ON DATABASE

                if(!validate()){
                    return;
                }

                String id = joinID.getText().toString();
                String name = joinName.getText().toString();
                String pw = joinPW.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService service = retrofit.create(ApiService.class);
                Call<RegisterResult> call = service.getJoin(id, name, pw, myid);

                call.enqueue(new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {


                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        RegisterResult r = response.body();

                        if(r.getRes().equals("fail")){
                            Toast.makeText(getApplicationContext(), "방 가입에 실패했습니다." + response.code(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "방에 가입했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("roomName", r.getJoinRoom());
                            intent.putExtra("maker", r.getRoomMaker()[0]);
                            intent.putExtra("roomNumber", r.getRoomNumber()[0]);
                            setResult(2, intent);
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        // onLoginFailed();
                    }
                });

            }
        });
    }

    private boolean validate(){

        String n = joinName.getText().toString();
        String p = joinPW.getText().toString();
        String d = joinID.getText().toString();
        boolean valid = true;

        if(joinName.length()==0){
            joinName.setError("그룹 이름이 비었습니다");
            valid = false;
        }
        if(joinPW.length()==0){
            joinPW.setError("그룹 비밀번호가 비었습니다");
            valid = false;
        }
        if(joinID.length()==0){
            joinID.setError("리더 아이디가 비었습니다");
            valid = false;
        }

        return valid;

    }
}