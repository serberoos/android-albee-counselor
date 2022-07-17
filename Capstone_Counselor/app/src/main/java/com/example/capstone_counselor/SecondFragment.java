package com.example.capstone_counselor;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {



    private String worktimeData;
    private int adultwomanNum;
    private int adultmanNum;
    private int boyNum;
    private int girlnum;

    //Activity내에서 계산하는 값
    private int totalsalesnum;
    private int totalsales=133333;
    DecimalFormat formatter = new DecimalFormat("###,###.##");

    private TextView workTimeTv;
    private TextView adultWomanNumTv;
    private TextView adultManNumTv;
    private TextView boyNumTv;
    private TextView girlNumTv;
    private TextView totalSalesNumTv;
    private TextView totalSalesTv;
    private TextView title;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);





        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        workTimeTv = (TextView) view.findViewById(R.id.work_time);
        adultWomanNumTv = (TextView) view.findViewById(R.id.num_adult_woman);
        adultManNumTv = (TextView) view.findViewById(R.id.num_adult_man);
        boyNumTv = (TextView) view.findViewById(R.id.num_boy);
        girlNumTv = (TextView) view.findViewById(R.id.num_girl);
        totalSalesNumTv = (TextView) view.findViewById(R.id.num_totalsales);
        totalSalesTv = (TextView) view.findViewById(R.id.total_sales);
        title = (TextView) view.findViewById(R.id.static_title);

        title.setText("Week 통계");

        worktimeData = "5"; // 일한시간
        adultwomanNum = 15; //성인 여자
        adultmanNum = 20; //성인 남자
        boyNum = 10; //남자 아이
        girlnum = 30; //여자 아이


        workTimeTv.setText("오늘 일한 시간 : " + worktimeData);
        adultWomanNumTv.setText("성인 여자 수 : " + adultwomanNum);
        adultManNumTv.setText("성인 남자 수 : " + adultmanNum);
        boyNumTv.setText("남자 아이 수 : " + boyNum);
        girlNumTv.setText("여자 아이 수 : " + girlnum);


        view.findViewById(R.id.date_lookup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), Date_List_MainActivity.class);
                startActivity(intent);
            }
        });

        //Activity내에서 계산하는 값
        totalsalesnum = adultwomanNum + adultmanNum + boyNum + girlnum; //모든 건수 합
        totalSalesNumTv.setText("판매 수 : " + totalsalesnum);
        //totalsales = -1; //임시
        totalSalesTv.setText("오늘의 매출 : " + formatter.format(totalsales)+ "원"); //가격?

        return view;

    }
}