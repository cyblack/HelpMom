package org.androidtown.helpmom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText name;
    EditText id;
    EditText pw;
    Button registerBtn;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.input_name);
        id=(EditText)findViewById(R.id.input_id);
        pw=(EditText)findViewById(R.id.input_pw);
        registerBtn=(Button)findViewById(R.id.registerBtn);
        loginLink=(TextView)findViewById(R.id.link_login);

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                register();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    public void register(){
        Log.d(TAG,"Register");

        if (!validate()) {
            onRegisterFailed();
            return;
        }

        registerBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("회원가입 중...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onRegisterSuccess or onRegisterFailed
                        // depending on success
                        onRegister();
                        onRegisterSuccess();
                        // onRegisterFailed();
                        progressDialog.dismiss();
                    }
                }, 4000);
    }

    public void onRegister(){

        String NAME = name.getText().toString();
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        // TODO: Implement your own register logic here.

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getRegister(ID,PW,NAME);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisterResult r = response.body();
                Toast.makeText(getApplicationContext(), "result :  " + r.getRes(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                onRegisterFailed();
            }
        });
    }


    public void onRegisterSuccess() {
        registerBtn.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        Toast.makeText(getBaseContext(), "가입 성공", Toast.LENGTH_LONG).show();
        startActivity(registerIntent);
        finish();
    }

    public void onRegisterFailed() {
        Toast.makeText(getBaseContext(), "가입 실패", Toast.LENGTH_LONG).show();
        registerBtn.setEnabled(true);
    }
//

    public boolean validate() {
        boolean valid = true;

        String NAME = name.getText().toString();
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        if (NAME.isEmpty() || name.length() < 3) {
            name.setError("3자 이상 입력하세요");
            valid = false;
        } else {
            name.setError(null);
        }

        if (ID.isEmpty()) {
            id.setError("아이디를 입력하세요");
            valid = false;
        } else {
            id.setError(null);
        }

        if (PW.isEmpty() || PW.length() < 4 || PW.length() > 10) {
            pw.setError("4자리 이상 입력하세요");
            valid = false;
        } else {
            pw.setError(null);
        }

        return valid;
    }//DB check required
}
