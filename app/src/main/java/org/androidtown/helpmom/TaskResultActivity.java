package org.androidtown.helpmom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TaskResultActivity extends AppCompatActivity {
        Button backBtn;
        TextView content;
        TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setFinishOnTouchOutside(false);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_task_result);

        backBtn=findViewById(R.id.backBtn);
        content=findViewById(R.id.content);
        score=findViewById(R.id.score);

        Intent intent=getIntent();
        String comment=intent.getStringExtra("comment");
        String point=intent.getStringExtra("score");
        if(comment.equals("none"))
        {
            content.setText("코멘트가 없습니다.");
        }
        else
        {content.setText(comment);}

        score.setText(point+"점");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
