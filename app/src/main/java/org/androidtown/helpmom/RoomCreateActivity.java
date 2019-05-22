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

        Button create=(Button)findViewById(R.id.createBtn);
        final EditText createName=(EditText) findViewById(R.id.createName);
        final EditText createPW=(EditText)findViewById(R.id.createPW);

        Intent intent = getIntent();
        _id = intent.getStringExtra("id");

        create.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //TODO: IMPLEMENTATION OF REQUEST DATABASE

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
                        Toast.makeText(getApplicationContext(), "그룹 생성:  " + r.getRes(), Toast.LENGTH_SHORT).show();
                       // onLoginSuccess();
                        Intent intent = new Intent();
                        intent.putExtra("addRoomName",createName.getText().toString());
                        setResult(1,intent);
                        finish();
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
}
