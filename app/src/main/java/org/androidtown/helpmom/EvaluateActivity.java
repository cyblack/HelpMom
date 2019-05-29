package org.androidtown.helpmom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class EvaluateActivity extends AppCompatActivity {
    EditText progress,feedback;
    Button confirm_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_evaluate);
        progress=findViewById(R.id.progress);
        feedback=findViewById(R.id.feedback);
        confirm_feedback=findViewById(R.id.confirm_feedback);

        confirm_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
