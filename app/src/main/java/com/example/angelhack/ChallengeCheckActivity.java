package com.example.angelhack;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeCheckActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengecheck);

        ImageView user_upload_image = findViewById(R.id.user_upload_image);
    }
}
