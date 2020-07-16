package com.example.angelhack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChallengeActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);


        //category
        ImageView category = findViewById(R.id.btn_category);
        category.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mystartActivity(DrawerActivity.class);
            }
        });

        //LinearView click시 이동
        LinearLayout challenge_subbox = findViewById(R.id.challenge_subbox);
        challenge_subbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mystartActivity(ChallengeDetailActivity.class);
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
