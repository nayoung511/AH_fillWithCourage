package com.example.angelhack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAthu;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAthu = FirebaseAuth.getInstance();

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    private void signUp(){
        //이름, 전화번호,....
        String email = ((EditText)findViewById(R.id.user_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.user_password)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.user_passwordcheck)).getText().toString();


        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            if(password.equals(passwordCheck)) {
                mAthu.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAthu.getCurrentUser();
                                    profileUpdate();
                                    //show main challenge
                                    mystartActivity(ChallengeActivity.class);
                                    //success: updateUI(user);
                                } else {
                                    if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                    // If sign in fails, display a message to the user.
                                    //fail UIupdateUI(null);
                                }

                            }
                        });
            }else{
                startToast("비밀번호가 일치하지 않습니다");//입력한 비밀번호와 비밀번호 확인이 일치하지 않음
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();

            }
        }else{
            startToast("이메일 또는 비밀번호를 입력해 주세요");
        }
    }

    private void profileUpdate() {
        //user type
        radioGroup = findViewById(R.id.user_type);
        int selectedUserType = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton)findViewById(selectedUserType);

        String userType = radioButton.getText().toString();
        String name = ((EditText)findViewById(R.id.user_name)).getText().toString();
        String phone = ((EditText)findViewById(R.id.user_phone)).getText().toString();

        if (name.length() > 0 && phone.length() > 9 && userType != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            MemberInfo memberInfo = new MemberInfo(name, phone, userType);
            if (user != null) {
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보 등록을 성공했어요.");
                                mystartActivity(ChallengeActivity.class);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원정보 등록에 실패했어요. ");
                            }
                        });

            }
        }
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
