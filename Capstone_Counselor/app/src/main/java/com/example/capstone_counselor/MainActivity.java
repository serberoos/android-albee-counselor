package com.example.capstone_counselor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton startSalesButton = (ImageButton)findViewById(R.id.startsalesbutton);
        ImageButton startStatisticsButton = (ImageButton)findViewById(R.id.startsatisbutton);

        startSalesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NaviActivity.class);
                startActivity(intent);
            }
        });
        startStatisticsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Static.class);
                startActivity(intent);
            }
        });
    }
}
