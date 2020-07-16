package com.example.angelhack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView btn_category = findViewById(R.id.btn_category);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_challenge_list: //도전과제 목록
                mystartActivity(ChallengeActivity.class);
            case R.id.nav_rank:  //랭킹
                mystartActivity(RankingActivity.class);
            case R.id.nav_pointshop:  //포인트 상점
                mystartActivity(PointShopActivity.class);
            case R.id.nav_my_challenge: //나의 도전과제
                mystartActivity(MyChallengeActivity.class);
            case R.id.nav_my_page:   //마이 페이지
                mystartActivity(MyPageActivity.class);
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}