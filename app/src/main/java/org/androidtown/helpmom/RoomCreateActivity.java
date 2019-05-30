package org.androidtown.helpmom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomCreateActivity extends AppCompatActivity {

    Button createBtn;
    EditText createName, createPW;

    private String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_room_create);

        createBtn=findViewById(R.id.createBtn);
        createName=findViewById(R.id.createName);
        createPW=findViewById(R.id.createPW);

        Intent intent = getIntent();
        _id = intent.getStringExtra("id");

        createBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //TODO: IMPLEMENTATION OF REQUEST DATABASE

                if(!validate()){
                    return;
                }

                String name = createName.getText().toString();
                String pw = createPW.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService service = retrofit.create(ApiService.class);
                Call<RegisterResult> call = service.getRoom(name,pw,_id);

                call.enqueue(new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        RegisterResult r = response.body();

                        if(r.getRes().equals("already")){

                        }else if(r.getRes().equals("fail")){ //등록 실패

                            Toast.makeText(getApplicationContext(), "방 생성 실패", Toast.LENGTH_SHORT).show();

                        }else{ //등록 성공

                            Toast.makeText(getApplicationContext(), "방 등록 성공" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("roomName",createName.getText().toString());
                            intent.putExtra("roomNumber",r.getRoomNumber()[0]);
                            setResult(1,intent);
                            finish();
                        }


                    }
                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private boolean validate(){

        String cn = createName.getText().toString();
        String cp = createPW.getText().toString();
        boolean valid = true;

        if(createName.length()==0){
            createName.setError("그룹 이름이 비었습니다");
            valid = false;
        }
        if(createPW.length()==0){
            createPW.setError("그룹 비밀번호가 비었습니다");
            valid = false;
        }

        return valid;
    }
}