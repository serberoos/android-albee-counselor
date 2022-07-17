package com.example.capstone_counselor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Static extends DemoBase {

    private String worktimeData;
    private int adultwomanNum;
    private int adultmanNum;
    private int boyNum;
    private int girlNum;

    private String adultwomanNumStr;
    private String adultmanNumStr;
    private String boyNumStr;
    private String girlNumStr;
    private String totalsalesnumStr;

    private CombinedChart chart;
    private final int count = 12;


    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    //Activity내에서 계산하는 값

    private int totalsales;

    private TextView workTimeTv;
    private TextView adultWomanNumTv;
    private TextView adultManNumTv;
    private TextView boyNumTv;
    private TextView girlNumTv;
    private TextView totalSalesNumTv;
    private TextView totalSalesTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("RB-Counselor");



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageButton move = (ImageButton) findViewById(R.id.detail_static);

        chart = findViewById(R.id.daychart);
        chart.getDescription().setEnabled(false);

        chart.animateY(2000, Easing.EasingOption.EaseInCubic);

        move.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PieActivity.class);
                startActivity(intent);
            }
        });

        workTimeTv = (TextView) findViewById(R.id.work_time);
        adultWomanNumTv = (TextView) findViewById(R.id.num_adult_woman);
        adultManNumTv = (TextView) findViewById(R.id.num_adult_man);
        boyNumTv = (TextView) findViewById(R.id.num_boy);
        girlNumTv = (TextView) findViewById(R.id.num_girl);
        totalSalesNumTv = (TextView) findViewById(R.id.num_totalsales);
        totalSalesTv = (TextView) findViewById(R.id.total_sales);

        Date date = new Date(); //오늘 날짜
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        String today = sdf.format(date);
        today = today.replace("/","-");
        System.out.println(today);

         totalsales = -1; //임시

        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("boy").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    boyNumStr = String.valueOf(task.getResult().getValue());
                    boyNumStr = boyNumStr.trim();
                    boyNumTv.setText("남자 아이 수 : " + boyNumStr);
                    //Log.d("firebase", (String.valueOf(task.getResult().getValue()));
                }
            }
        });
        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("girl").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    girlNumStr = String.valueOf(task.getResult().getValue());
                    girlNumStr = girlNumStr.trim();
                    girlNumTv.setText("여자 아이 수 : " + girlNumStr);
                    //Log.d("firebase", (String.valueOf(task.getResult().getValue()));
                }
            }
        });
        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("woman").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    adultwomanNumStr = String.valueOf(task.getResult().getValue());
                    adultwomanNumStr = adultwomanNumStr.trim();
                    adultWomanNumTv.setText("성인 여자 수 : " + adultwomanNumStr);
                    //Log.d("firebase", (String.valueOf(task.getResult().getValue()));
                }
            }
        });
        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("man").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    adultmanNumStr = String.valueOf(task.getResult().getValue());
                    adultmanNumStr = adultmanNumStr.trim();
                    adultManNumTv.setText("성인 남자 수 : " + adultmanNumStr);
                    //Log.d("firebase", (String.valueOf(task.getResult().getValue()));
                }
            }
        });
        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("worktime").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    worktimeData = String.valueOf(task.getResult().getValue());
                    worktimeData = worktimeData.trim();
                    workTimeTv.setText("오늘 일한 시간 : " + worktimeData);
                    //Log.d("firebase", (String.valueOf(task.getResult().getValue()));
                }
            }
        });
        mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("totalsalesnum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    totalsalesnumStr = String.valueOf(task.getResult().getValue());
                    totalsalesnumStr = totalsalesnumStr.trim();
                    totalSalesNumTv.setText("판매 수 : " + totalsalesnumStr);

                    //Log.d("firebase", (String.valueOf(task.getResult().getValue()));
                }
            }
        });
//        System.out.println("영업시간:"+worktimeData);
//        System.out.println("총 건수:"+totalsalesnumStr);
//        System.out.println("여자아이:"+girlNumStr);
//        System.out.println("성인남자:"+adultmanNumStr);
//        System.out.println("성인여자:"+adultwomanNumStr);
//        System.out.println("남자아이:"+boyNumStr);

        //임시
        totalSalesTv.setText("오늘의 매출 : " + totalsales); //가격?

//        try {
//
//            mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("boy").get();
//            mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("girl").get();
//            mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("man").get();
//            mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("woman").get();
//            workTimeTv.setText("오늘 일한 시간 : " + mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("worktime").get());
//            mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("totalsalesnum").get();
//            mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("totalsales").get();
//
//        }catch(NullPointerException e){
//            System.out.println("파이어 베이스에 로그인 안되있으면 뜸");
//        }



        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return months[(int) value % months.length];
            }
        });
        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setValueTypeface(tfLight);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        chart.setData(data);
        chart.invalidate();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int index = 0; index < count; index++)
            entries.add(new Entry(index + 0.5f, getRandom(15, 5)));

        LineDataSet set = new LineDataSet(entries, "Line DataSet");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    @Override
    public void saveToGallery() { /* Intentionally left empty */ }
}



