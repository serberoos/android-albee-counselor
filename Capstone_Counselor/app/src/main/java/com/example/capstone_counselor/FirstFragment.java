package com.example.capstone_counselor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FirstFragment extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }


    //Activity내에서 계산하는 값

    private String today;
    private TextView workTimeTv;
    private TextView adultWomanNumTv;
    private TextView adultManNumTv;
    private TextView boyNumTv;
    private TextView girlNumTv;
    private TextView totalSalesNumTv;
    private TextView totalSalesTv;

    private String worktimeData;
    private String adultwomanNumStr;
    private String adultmanNumStr;
    private String boyNumStr;
    private String girlNumStr;
    private String totalsalesnumStr;

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    //Activity내에서 계산하는 값
    private int totalsales;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("RB-Counselor");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        view.findViewById(R.id.date_lookup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), Date_List_MainActivity.class);
               startActivity(intent);
            }
        });
        workTimeTv = (TextView) view.findViewById(R.id.work_time);
        adultWomanNumTv = (TextView) view.findViewById(R.id.num_adult_woman);
        adultManNumTv = (TextView) view.findViewById(R.id.num_adult_man);
        boyNumTv = (TextView) view.findViewById(R.id.num_boy);
        girlNumTv = (TextView) view.findViewById(R.id.num_girl);
        totalSalesNumTv = (TextView) view.findViewById(R.id.num_totalsales);
        totalSalesTv = (TextView) view.findViewById(R.id.total_sales);

        Date date = new Date(); //오늘 날짜
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        today = sdf.format(date);
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

        //임시
        totalSalesTv.setText("오늘의 매출 : " + totalsales); //가격?

        return view;
    }
}