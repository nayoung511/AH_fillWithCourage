package com.example.angelhack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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

        //click시 이동

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




