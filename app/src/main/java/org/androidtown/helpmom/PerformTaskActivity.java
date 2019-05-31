package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.helpmom.ApiService;
import org.androidtown.helpmom.R;
import org.androidtown.helpmom.RegisterResult;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerformTaskActivity extends AppCompatActivity {

    Button performBtn;
    EditText performProgress;
    String taskName,changeId,now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_perform_task);

        performBtn=findViewById(R.id.performBtn);
        performProgress=findViewById(R.id.performProgress);

        Intent intent=getIntent();
        taskName=intent.getStringExtra("taskName");
        changeId = intent.getStringExtra("myId");

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd / HH시 mm분 ss초");
        Date time = new Date();
        now = format1.format(time);

        Log.d("date",now);
        performBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    progress();
                }
            }
        });
    }

    private boolean validate(){


        String p = performProgress.getText().toString();
        boolean valid = true;
        int inp=0;

        if(performProgress.length()==0){
            performProgress.setError("비었습니다");
            valid = false;
        }else{
            inp = Integer.parseInt(p);
        }

        if(p.isEmpty()){
            performProgress.setError("비었습니다");
            valid = false;
        }else if(0>inp){
            performProgress.setError("0보다 작을 수 없습니다.");
            valid = false;
        }else if(100<inp){
            performProgress.setError("100보다 클 수 없습니다.");
            valid = false;
        }
        return valid;

    }

    private void progress() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.progress(taskName,performProgress.getText().toString(),changeId,now);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                Toast.makeText(getApplicationContext(), "진행 변경되었습니다.", Toast.LENGTH_LONG).show();
                back();
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "진행 실패" + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });
    }
    public void back(){
        Intent intent = new Intent();
        intent.putExtra("progress",performProgress.getText().toString());
        intent.putExtra("taskName",taskName);
        intent.putExtra("now",now);
        Log.d("progress",performProgress.getText().toString());

        setResult(3,intent);
        finish();
    }
}
