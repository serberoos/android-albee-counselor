package com.example.capstone_counselor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class BarChartActivity extends AppCompatActivity {
    BarChart stackedChart;
    int[] colorArray = new int[] {Color.RED, Color.YELLOW,Color.BLUE,Color.BLACK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        Button b = (Button) findViewById(R.id.bar_move);

        Button c = (Button) findViewById(R.id.my_button);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PieActivity.class);
                startActivity(intent);
            }
        });

        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Static_content.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
                finish();

            }
        });



        stackedChart = findViewById(R.id.stacked_BarChart);

        BarDataSet barDataSet = new BarDataSet(dataValues1(), "Bar Set");
        barDataSet.setColors(colorArray);

        BarData barData = new BarData(barDataSet);
        stackedChart.setData(barData);
        stackedChart.invalidate();
    }

    private ArrayList dataValues1(){
        ArrayList dataVals = new ArrayList<>();

        dataVals.add(new BarEntry(0, new float[]{2,5,5f,4,3}));
        dataVals.add(new BarEntry(1, new float[]{2,8f,4,2f,4}));
        dataVals.add(new BarEntry(2, new float[]{3,5,5f,4,5}));
        dataVals.add(new BarEntry(3, new float[]{4,6,5f,4,2}));
        dataVals.add(new BarEntry(4, new float[]{2,5,5f,3.3f,4}));

        return dataVals;
    }
}





