package com.example.capstone_counselor;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;      // 로그인 입력필드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        //파일 읽고 쓰는 권한 요청하기

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("RB-Counselor");

        mEtEmail = findViewById(R.id.email_edittext);
        mEtPwd = findViewById(R.id.password_edittext);

        Button btn_login = findViewById(R.id.email_login_button);
        Button btn_register = findViewById(R.id.email_signup_button);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                if(strEmail.equals("") || strPwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "아이디 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else {

                    mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //로그인 성공 !!
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); //로그인 액티비티가 지났을 때는 더 이상 해당 액티비티가 필요하지 않기 때문에 현재 액티비티를 파괴하고 넘어간다.
                            } else {
                                Toast.makeText(LoginActivity.this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
//    public void onStart() { // 자동로그인 시스템
//        super.onStart();
//        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
//        if(currentUser != null){
//            //로그인 성공 !!
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish(); //로그인 액티비티가 지났을 때는 더 이상 해당 액티비티가 필요하지 않기 때문에 현재 액티비티를 파괴하고 넘어간다.
//        }
//    }
}