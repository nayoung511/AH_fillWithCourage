package com.example.angelhack;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChallengeStartActivity extends AppCompatActivity {
    private String ImagePath;
    private Uri imgUri, photoURI, albumURI;
    private String mCurrentPhotoPath;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    int flag;
    int check_success;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView user_upload_image;
    Button btn_upload;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challengestart);

        //category
        ImageView btn_category = findViewById(R.id.btn_category);
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout = findViewById(R.id.drawer_layout);
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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


        user_upload_image = findViewById(R.id.user_upload_image);
        user_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgDialog();
            }
        });

        btn_upload = findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //파이어베이스에 사진 업로드
                uploadFile();
                if(check_success != 1) {
                    startToast("도전과제 제출 완료!");
                    mystartActivity(ChallengeSuccessPopUp.class);}
            }
        });


    }

    //프로필 이미지 업로드 선택
    private void imgDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(ChallengeStartActivity.this);
        alt_bld.setTitle("프로필 사진 업로드")
                .setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flag = 0;
                        cameraActivity();
                    }
                })
                .setNeutralButton("앨범선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        flag = 1;
                        galleryActivity();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = alt_bld.create();
        alertDialog.show();
    }


    //카메라 실행
    private void cameraActivity(){
        Log.e("프로필", "카메라 실행");
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager())!= null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if(photoFile!=null){
                    Uri providerURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
        }else{
            startToast("권한을 허용해 주세요.");
        }
    }

    //이미지 생성
    public File createImageFile() throws IOException {
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imgFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        imgFile = new File(storageDir, imgFileName);
        mCurrentPhotoPath = imgFile.getAbsolutePath();

        return imgFile;
    }

    //찍은 사진을 갤러리에 저장
    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        startToast("사진이 갤러리에 저장되었어요.");
    }


    //갤러리에서 사진 가져오기
    private void galleryActivity(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case FROM_ALBUM: {
                ImagePath = data.getStringExtra("ImagePath");
                Log.e("프로필", "앨범에서 사진가져오기");
                if (data.getData() != null){
                    try{
                        File albumFile = null;
                        albumFile = createImageFile();
                        photoURI = data.getData();
                        albumURI = Uri.fromFile(albumFile);
                        galleryAddPic();
                        //프로필 이미지에 띄우기
                        user_upload_image.setImageURI(photoURI);
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("profileAct", "에러났어요.");
                    }
                }
                break;
            }
            case FROM_CAMERA: {
                ImagePath = data.getStringExtra("ImagePath");
                //take a photo
                try{
                    galleryAddPic();
                    //프로필 이미지에 띄우기
                    user_upload_image.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    //upload the file
    private void uploadFile() {
        //업로드할 파일이 있으면 수행
        if (photoURI != null) {
            //storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            //Unique한 파일명을 만들자.
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
            Date now = new Date();
            String filename = formatter.format(now) + ".png";
            //storage 주소와 폴더 파일명을 지정해 준다.
            StorageReference storageRef = storage.getReferenceFromUrl("gs://angelhack-caa31.appspot.com").child("images/" + filename);
            //올라가거라...
            storageRef.putFile(photoURI)
                    //성공시
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            check_success = 0;
                        }
                    })
                    //실패시
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    //진행중
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                    double progress = (100 * taskSnapshot.getBytesTransferred()) /  taskSnapshot.getTotalByteCount();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
            check_success = 1;
        }
    }

    private void uploadImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        //create a reference to upload image
        StorageReference user_upload_img = storageReference.child("user_upload_img.jpg");

        //create a reference to 'challenges/user_upload_img.jpg'
        StorageReference user_upload_img_challenge = storageReference.child("challenge/user_upload_img.jpg");

        user_upload_img.getName().equals(user_upload_img_challenge.getName());
        user_upload_img.getPath().equals(user_upload_img_challenge.getPath());

        user_upload_image.setDrawingCacheEnabled(true);
        user_upload_image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable)user_upload_image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = user_upload_img_challenge.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                startToast("이미지 업로드에 실패했어요. ");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        Uri file = Uri.fromFile(new File(ImagePath));
        StorageReference upload_img = storageReference.child("challenge/"+file.getLastPathSegment());
        uploadTask = upload_img.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

    }

    public void mOnPopupClick(View view){
        Intent intent = new Intent(this, ChallengeSuccessPopUp.class);
        intent.putExtra("data", "+5 포인트");
        startActivityForResult(intent, 1);
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
