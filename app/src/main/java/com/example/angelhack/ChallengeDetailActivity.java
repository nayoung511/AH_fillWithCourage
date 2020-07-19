package com.example.angelhack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;

public class ChallengeDetailActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Button btn_check_others;
    Button participate_challenge;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengedetail);

        //category
        ImageView btn_category = findViewById(R.id.btn_category);
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout = findViewById(R.id.drawer_layout);
                if(!drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.openDrawer(Gravity.LEFT);
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

        //intent (setting title, exp)

        TextView challenge_title = findViewById(R.id.challenge_title);
        final TextView challenge_exp = findViewById(R.id.challenge_exp);
        ImageView challenge_photo = findViewById(R.id.challenge_photo);

        Intent intent = getIntent();

        String title = intent.getExtras().getString("challenge_title");
        challenge_title.setText(title);

        String exp = intent.getExtras().getString("challenge_exp");
        challenge_exp.setText(exp);

        Bundle extras = getIntent().getExtras();
        final byte[] byteArray = getIntent().getByteArrayExtra("challenge_photo");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        challenge_photo.setImageBitmap(bitmap);


        //click시 이동
        btn_check_others = findViewById(R.id.btn_check_others);
        btn_check_others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Intent intent = new Intent(getApplicationContext(), ChallengeCheckActivity.class);
                intent.putExtra("challenge_title", challenge_title.getText().toString());
                intent.putExtra("challenge_exp", challenge_exp.getText().toString());
                intent.putExtra("challenge_photo", byteArray);
                startActivity(intent);
            }
        });

        participate_challenge = findViewById(R.id.participate_challenge);
        participate_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Intent intent = new Intent(getApplicationContext(), MyChallengeActivity.class);
                intent.putExtra("challenge_title", challenge_title.getText().toString());
                intent.putExtra("challenge_exp", challenge_exp.getText().toString());
                intent.putExtra("challenge_photo", byteArray);
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
