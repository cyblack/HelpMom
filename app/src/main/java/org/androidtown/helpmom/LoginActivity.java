package org.androidtown.helpmom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;
    private static final String TAG = "LoginActivity";

    private EditText id,pw;
    private Button login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        id=findViewById(R.id.id);
        pw=findViewById(R.id.pw);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        login.setEnabled(false);

        /* Progress Effect */
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("로그인 중...");
        progressDialog.show();





        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                      //  onLoginSuccess();
                        // onLoginFailed();
                        onRequest();
                        progressDialog.dismiss();
                    }
                }, 4000);
    }

    private void onRequest(){
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-79-126.ap-northeast-2.compute.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<RegisterResult> call = service.getLogin(ID,PW);

        call.enqueue(new Callback<RegisterResult>() {
            @Override
            public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {


                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "code " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                RegisterResult r = response.body();


                if(r.getRes().equals("fail")){
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 다릅니다. ", Toast.LENGTH_SHORT).show();
                    onLoginFailed();
                }else {

                    Toast.makeText(getApplicationContext(), "result2 :  " + r.getRes(), Toast.LENGTH_SHORT).show();

                    onLoginSuccess(r.getId(),r.getName());
                }
            }
            @Override
            public void onFailure(Call<RegisterResult> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "실패: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                onLoginFailed();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here

                this.finish();
            }
        }
    }

    /*public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }*/

    public void onLoginSuccess(String id,String name) {
        login.setEnabled(true);
        Intent loginIntent = new Intent(LoginActivity.this, LobbyActivity.class);
        loginIntent.putExtra("id",id);
        loginIntent.putExtra("name",name);
        startActivity(loginIntent);
        finish();
    }

    public void onLoginFailed() {
       // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String ID = id.getText().toString();
        String PW = pw.getText().toString();
        if (ID.isEmpty()) {
            id.setError("형식이 올바르지 않습니다");
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
    }//require to add database check
}
