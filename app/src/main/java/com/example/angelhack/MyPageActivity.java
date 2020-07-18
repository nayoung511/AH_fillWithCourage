package com.example.angelhack;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int value = 10;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        progressBar = findViewById(R.id.exp_bar);

    }

    public void onClick(View view){
        progressBar.setProgress(value);
    }

    //현재 포인트
    //사용내역
    //개인정보 수정
    //경험치/ 레벨


}
