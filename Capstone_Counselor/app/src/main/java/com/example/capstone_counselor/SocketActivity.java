package com.example.capstone_counselor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SocketActivity extends AppCompatActivity { // test

    Button btn; //send 버튼

    private MyThread myThread;

    protected void onCreate(Bundle savedInstanceState) { //첫 액티비티 열면서 수행할 것들
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        btn = findViewById(R.id.sendButton); //send 버튼

        btn.setOnClickListener(new View.OnClickListener() { //send버튼이 눌리면
           @Override
           public void onClick(View v) { //파일 전송하기
               Toast.makeText(SocketActivity.this, "send 버튼눌림", Toast.LENGTH_SHORT).show();
               myThread = new MyThread();
               myThread.start(); // 스레드 시작
           }
        });
    }
}
