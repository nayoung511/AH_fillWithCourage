package com.example.angelhack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

public class ChallengeActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //category
        ImageView btn_category = findViewById(R.id.btn_category);
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout = findViewById(R.id.drawer_layout);
                if(!drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.openDrawer(Gravity.LEFT);
                }else{
                    drawerLayout.closeDrawer(Gravity.LEFT) ;
                }
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_challenge_list: //도전과제 목록
                        mystartActivity(ChallengeActivity.class);
                        break;
                    case R.id.nav_rank:  //랭킹
                        mystartActivity(RankingActivity.class);
                        break;
                    case R.id.nav_pointshop:  //포인트 상점
                        mystartActivity(PointShopActivity.class);
                        break;
                    case R.id.nav_my_challenge: //나의 도전과제
                        mystartActivity(MyChallengeActivity.class);
                        break;
                    case R.id.nav_my_page:   //마이 페이지
                        mystartActivity(MyPageActivity.class);
                        break;
                }
                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });

        //Define title, exp, image
        final TextView challenge_title = findViewById(R.id.challenge_title);
        final TextView challenge_exp = findViewById(R.id.challenge_exp);
        final ImageView challenge_photo = findViewById(R.id.challenge_photo);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)challenge_photo.getDrawable()).getBitmap();
        float scale = (float)(1024/(float)bitmap.getWidth());
        int image_w = (int)(bitmap.getWidth()*scale);
        int image_h = (int)(bitmap.getHeight()*scale);
        Bitmap resize= Bitmap.createScaledBitmap(bitmap, image_w, image_h,true);
        resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        final byte[] byteArray = stream.toByteArray();

        //LinearView click시 이동
        LinearLayout challenge_subbox = findViewById(R.id.challenge_subbox);
        challenge_subbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChallengeDetailActivity.class);
                intent.putExtra("challenge_title", challenge_title.getText().toString());
                intent.putExtra("challenge_exp", challenge_exp.getText().toString());
                intent.putExtra("challenge_photo", byteArray);
                startActivity(intent);
            }
        });

        Button btn_participate = findViewById(R.id.btn_participate);
        btn_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChallengeDetailActivity.class);
                intent.putExtra("challenge_title", challenge_title.getText().toString());
                intent.putExtra("challenge_exp", challenge_exp.getText().toString());

                startActivity(intent);
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




