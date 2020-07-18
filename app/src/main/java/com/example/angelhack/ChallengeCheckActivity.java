package com.example.angelhack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeCheckActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengecheck);

        ImageView user_upload_image = findViewById(R.id.user_upload_image);

        Button btn_success = findViewById(R.id.btn_success);
        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToast("성공했군요!");
                mystartActivity(ChallengeActivity.class);
            }
        });

        Button btn_fail = findViewById(R.id.btn_fail);
        btn_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startToast("실패했군요 ㅠㅠ");
                mystartActivity(ChallengeActivity.class);
            }
        });
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    //go to c activity
    private void mystartActivity(Class c){
        Intent intent = new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
