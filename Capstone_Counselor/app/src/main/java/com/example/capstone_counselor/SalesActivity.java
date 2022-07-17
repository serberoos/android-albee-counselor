package com.example.capstone_counselor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SalesActivity extends AppCompatActivity implements View.OnClickListener {


    private int i = 1;
    int[] arr = new int[300];
    private Button count;
    int man = 1;
    int woman = 1;
    int boy = 1;
    int girl = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        count = findViewById(R.id.count);

        ImageButton btn1 = findViewById(R.id.btn1);
        ImageButton btn2 = findViewById(R.id.btn2);
        ImageButton btn3 = findViewById(R.id.btn3);
        ImageButton btn4 = findViewById(R.id.btn4);

        ImageButton gosalescounting_button = findViewById(R.id.gosalescounting_button);
        ImageButton gostastics_button = findViewById(R.id.gostastics_button);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        count.setText(i+"");

        gostastics_button.setOnClickListener(new View.OnClickListener(){ // 통계 액티비티로 이동
            public void onClick(View view) {
                Intent intent=new Intent(SalesActivity.this,Static.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btn1:
            {
                arr[i] = 1;
                Toast.makeText(this,man+"번째 성인 남성 ",Toast.LENGTH_SHORT).show();
                man++;
                i++;
                count.setText(i+"");
                break;
            }
            case R.id.btn2:
            {
                arr[i] = 2;
                Toast.makeText(this,woman+"번째 성인 여성 ",Toast.LENGTH_SHORT).show();
                woman++;
                i++;
                count.setText(i+"");
                break;
            }
            case R.id.btn3:
            {
                arr[i] = 3;
                Toast.makeText(this,boy+"번째 남자 아이 ",Toast.LENGTH_SHORT).show();
                boy++;
                i++;
                count.setText(i+"");
                break;
            }
            case R.id.btn4:
            {
                arr[i] = 4;
                Toast.makeText(this,girl+"번째 여자 아이 ",Toast.LENGTH_SHORT).show();
                girl++;
                i++;
                count.setText(i+"");
                break;
            }
        }
    }

}
