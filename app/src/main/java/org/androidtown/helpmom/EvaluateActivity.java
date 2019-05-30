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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EvaluateActivity extends AppCompatActivity {
    EditText point,feedback;
    Button confirm_feedback;
    String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evaluate);
        point=findViewById(R.id.point);
        feedback=findViewById(R.id.feedback);
        confirm_feedback=findViewById(R.id.confirm_feedback);

        confirm_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                finish();
            }
        });
    }


    private void evaluate() {
        Intent intent=getIntent();
        taskId=intent.getStringExtra("taskId");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("ehdwn", feedback.getText().toString()+point.getText().toString());
        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.evaluate(taskId,feedback.getText().toString(),point.getText().toString());

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {

                Toast.makeText(getApplicationContext(), "평가 완료되었습니다.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "평가 실패" + t.getMessage(), Toast.LENGTH_SHORT).show();
                // onLoginFailed();
            }
        });
    }

}
