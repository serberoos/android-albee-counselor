package com.example.capstone_counselor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FoodMenuActivity extends AppCompatActivity {
    EditText name_et, age_et;
    Button add_bt, gallery_bt;
    int image;
    RecyclerView rv;
    FoodListAdapter adapter;
    private final int GET_GALLERY_IMAGE = 20;
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);
        name_et = findViewById(R.id.name_et);
        age_et = findViewById(R.id.age_et);
        add_bt = findViewById(R.id.add_bt);

        gallery_bt = findViewById(R.id.gallery_bt);
        gallery_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //갤러리 버튼시 반응
                imageview = (ImageView) findViewById(R.id.list_image);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }

        });


        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText 입력된 값 가져오기
                String name = name_et.getText().toString();
                int age = Integer.parseInt(age_et.getText().toString());


                image = R.drawable.food_icon;

                imageview = (ImageView) findViewById(R.id.list_image);

                //Person 객체 생성, 값 세팅
                Food_Data person = new Food_Data();
                person.setName(name);
                person.setAge(age);
                person.setImage(image);

                //ListAdapter에 객체 추가
                adapter.addItem(person);
                adapter.notifyDataSetChanged();
                //EditText 초기화

                name_et.setText("");
                age_et.setText("");

                //데이터 추가 확인 토스트 띄우기
                Toast.makeText(FoodMenuActivity.this, "목록에 추가되었습니다", Toast.LENGTH_SHORT).show();
                name_et.requestFocus();
            }
        });


        rv = findViewById(R.id.rv);
//        RecyclerView의 레이아웃 방식을 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
//        RecyclerView의 Adapter 세팅
        adapter = new FoodListAdapter();
        rv.setAdapter(adapter);
    }

}



