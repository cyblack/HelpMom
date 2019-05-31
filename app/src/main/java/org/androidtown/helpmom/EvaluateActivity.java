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

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluateActivity extends AppCompatActivity {

    EditText point,feedback;
    Button confirm_feedback;
    String taskName,now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evaluate);
        point=findViewById(R.id.point);
        feedback=findViewById(R.id.feedback);
        confirm_feedback=findViewById(R.id.confirm_feedback);


        Intent intent=getIntent();
        taskName=intent.getStringExtra("taskName");




        confirm_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validate()){
                    evaluate();
                }


            }
        });
    }

    private boolean validate(){


        String p = point.getText().toString();
        boolean valid = true;
        int inp=0;

        if(point.length()==0){
            point.setError("비었습니다");
            valid = false;
        }else{
            inp = Integer.parseInt(p);
        }

        if(feedback.length()==0){
            feedback.setError("비었습니다");
            valid = false;
        }
        else if(p.isEmpty()){
            point.setError("비었습니다");
            valid = false;
        }else if(0>inp){
            point.setError("0보다 작을 수 없습니다.");
            valid = false;
        }else if(100<inp){
            point.setError("100보다 클 수 없습니다.");
            valid = false;
        }
        return valid;

    }


    private void evaluate() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("ehdwn", feedback.getText().toString()+point.getText().toString());
        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.evaluate(taskName,feedback.getText().toString(),point.getText().toString());

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                Toast.makeText(getApplicationContext(), "평가 완료되었습니다.", Toast.LENGTH_LONG).show();

                back();
            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "평가 실패" + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });
    }

    public void back(){
        Intent intent = new Intent();
        intent.putExtra("point",point.getText().toString());
        intent.putExtra("feedback",feedback.getText().toString());
        intent.putExtra("taskName",taskName);

        setResult(2,intent);
        finish();
    }

}
