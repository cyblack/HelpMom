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
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String NAME = name.getText().toString();
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        // TODO: Implement your own register logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onRegisterSuccess or onRegisterFailed
                        // depending on success
                        onRegisterSuccess();
                        // onRegisterFailed();
                        progressDialog.dismiss();
                    }
                }, 4000);
    }


    public void onRegisterSuccess() {
        registerBtn.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(registerIntent);
        finish();
    }

    public void onRegisterFailed() {
        Toast.makeText(getBaseContext(), "Register failed", Toast.LENGTH_LONG).show();
        registerBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String NAME = name.getText().toString();
        String ID = id.getText().toString();
        String PW = pw.getText().toString();

        if (NAME.isEmpty() || name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        if (ID.isEmpty()) {
            id.setError("enter a valid ID");
            valid = false;
        } else {
            id.setError(null);
        }

        if (PW.isEmpty() || PW.length() < 4 || PW.length() > 10) {
            pw.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pw.setError(null);
        }

        return valid;
    }//DB check required
}
