package com.example.angelhack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeCheckActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengecheck);

        TextView challenge_title = findViewById(R.id.challenge_title);
        Intent intent = getIntent();

        String title = intent.getExtras().getString("challenge_title");
        challenge_title.setText(title);

        Button btn_success = findViewById(R.id.btn_success);
        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mystartActivity(ChallengeCheckSuccessPopUp.class);

            }
        });

        Button btn_fail = findViewById(R.id.btn_fail);
        btn_fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mystartActivity(ChallengeCheckFailPopUp.class);
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
