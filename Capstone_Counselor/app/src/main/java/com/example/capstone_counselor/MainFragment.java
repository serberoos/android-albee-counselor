package com.example.capstone_counselor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final int REQUEST_CODE = 0;
    private String fileName;

    private FirebaseAuth mFirebaseAuth;     // 파이어베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스

    private MyThread myThread; //소켓 스레드
    private Thread timeThread = null;
    private Boolean isRunning = true;
    private TextView mTimeTextView,mRecordTextView;
    private int i = 0;
    int j = i+1;
    int[] arr = new int[300];
    private Button count;
    int man = 1;
    int woman = 1;
    int boy = 1;
    int girl = 1;
    int request_Code = 1;
    private int totalsalesnum;
    private int totalsales;
    private String today;
    private final int Fragment_1 = 1;
    private final int Fragment_2 = 2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sales, container, false);
        count = view.findViewById(R.id.count);
        mFirebaseAuth = FirebaseAuth.getInstance();

        Date date = new Date(); //오늘 날짜
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        today = sdf.format(date);
        today = today.replace("/","-");
        System.out.println(today);


        ImageButton gostastics_button = view.findViewById(R.id.gostastics_button);
        Button makePicture = view.findViewById(R.id.make_picture);

        count.setText(j+"");

        view.findViewById(R.id.move_food).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity().getApplicationContext(),FoodMenuActivity.class);
                startActivity(intent2);
            }
        });


        view.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr[i] = 1;
                Toast.makeText(getContext(),man+"번째 성인 남성 ",Toast.LENGTH_SHORT).show();
                man++;
                i++;
                count.setText(i+"");
                if (i == 1)
                {
                    timeThread = new Thread(new timeThread());
                    timeThread.start();

                }
                else {
                    InterruptedException e;
                }}
        });
        view.findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr[i] = 2;
                Toast.makeText(getContext(),woman+"번째 성인 여성 ",Toast.LENGTH_SHORT).show();
                woman++;
                i++;
                count.setText(i+"");
                if (i == 1)
                {
                    timeThread = new Thread(new timeThread());
                    timeThread.start();

                }
                else {
                    InterruptedException e;
                }
            }
        });
        view.findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr[i] = 3;
                Toast.makeText(getContext(),boy+"번째 남자 아이 ",Toast.LENGTH_SHORT).show();
                boy++;
                i++;
                count.setText(i+"");
                if (i == 1)
                {
                    timeThread = new Thread(new timeThread());
                    timeThread.start();

                }
                else {
                    InterruptedException e;
                }
            }
        });
        view.findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr[i] = 4;
                Toast.makeText(getContext(),girl+"번째 여자 아이 ",Toast.LENGTH_SHORT).show();
                girl++;
                i++;
                count.setText(i+"");
                if (i == 1)
                {
                    timeThread = new Thread(new timeThread());
                    timeThread.start();

                }
                else {
                    InterruptedException e;
                }
            }
        });
        makePicture.setOnClickListener(new View.OnClickListener(){ //사진 촬영 버튼
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE); //카메라에서 이미지 파일로 바꿈

                myThread = new MyThread();
                myThread.start(); // 스레드 시작
            }
        });

        mTimeTextView = view.findViewById(R.id.timeView);
        mRecordTextView = view.findViewById(R.id.timeView);
        view.findViewById(R.id.endsales).setOnClickListener(new View.OnClickListener() { //영업 종료 버튼
            @Override
            public void onClick(View v) {
                AlertDialog.Builder oDialog = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
                oDialog.setMessage("영업을 종료 하시겠습니까")
                        .setTitle("영업 종료 Dialog")
                        .setNeutralButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.i("Dialog", "취소");
                                Toast.makeText(getContext(), "취소", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setPositiveButton("종료하기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                isRunning = !isRunning;
                                oDialog.setMessage("영업 시간 \n "+ mTimeTextView.getText().toString())
                                        .setTitle("종료 Dialog")
                                        .setNeutralButton("통계", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) { //통계 버튼
                                                Log.i("Dialog", "통계");
                                                Toast.makeText(getContext(), "통계", Toast.LENGTH_LONG).show();
                                                System.out.println(mTimeTextView.getText());
                                                totalsalesnum = woman + man + boy + girl; //모든 건수 합
                                                totalsales = -1; //임시
                                                try {
                                                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("RB-Counselor");
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("boy").setValue(boy);
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("girl").setValue(girl);
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("man").setValue(man);
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("byPerson").child("woman").setValue(woman);
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("worktime").setValue(mTimeTextView.getText());
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("totalsalesnum").setValue(totalsalesnum);
                                                    mDatabaseRef.child("UserAccount").child(mFirebaseAuth.getUid()).child("work"+today).child("bySales").child("totalsales").setValue(totalsales);
                                                }catch(NullPointerException e){
                                                    System.out.println("파이어 베이스에 로그인 안되있으면 뜸");
                                                }

                                                Intent intent = new Intent(getActivity().getApplicationContext(),Static.class);
//                                                intent.putExtra("WORKTIME", mTimeTextView.getText());
//                                                intent.putExtra("ADULTWOMAN", woman);
//                                                intent.putExtra("ADULTMAN", man);
//                                                intent.putExtra("BOY", boy);
//                                                intent.putExtra("GIRL", girl);
                                                startActivity(intent);


                                            }
                                        })
                                        .setPositiveButton("종료하기", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                mTimeTextView.setText("");
                                                timeThread.interrupt();
                                                Toast.makeText(getContext(), "임시)종료 대체", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setCancelable(false)   // 백버튼으로 팝업창이 닫히지 않도록 한다.
                                        .show();

                            }
                        })
                        .setCancelable(false)   // 백버튼으로 팝업창이 닫히지 않도록 한다.
                        .show();
            }
        });
        return view;


    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            int hour = (msg.arg1 / 100) / 360;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);
            if (result.equals("00:01:15:00")) {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
            mTimeTextView.setText(result);

        }
    };
    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mTimeTextView.setText("");
                                mTimeTextView.setText("00:00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    private class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Create some layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            // Create some text
            TextView textView = getTextView(container.getContext());
            textView.setText(String.valueOf(position));
            textView.setLayoutParams(layoutParams);

            RelativeLayout layout = new RelativeLayout(container.getContext());
            layout.setBackgroundColor(ContextCompat.getColor(container.getContext(), R.color.colorPrimary));
            layout.setLayoutParams(layoutParams);

            layout.addView(textView);
            container.addView(layout);
            return layout;
        }

        private TextView getTextView(Context context) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 사진촬영 관련
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    fileName = "predict_img_file"; // 임시로 정해둔 파일 이름

                    saveBitmapToJpg(img, /*"storage/external/0/"+*/ fileName); //맨앞에 슬러쉬 주의 할것

                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("사진 선택 취소");
            }
        }
    }
    public String saveBitmapToJpg(Bitmap bitmap , String name) { //사진촬영 관련
        /**
         * 캐시 디렉토리에 비트맵을 이미지파일로 저장하는 코드입니다.
         *
         * @version target API 28 ★ API29이상은 테스트 하지않았습니다.★
         * @param Bitmap bitmap - 저장하고자 하는 이미지의 비트맵
         * @param String fileName - 저장하고자 하는 이미지의 비트맵
         *
         * File storage = 저장이 될 저장소 위치
         *
         * return = 저장된 이미지의 경로
         *
         * 비트맵에 사용될 스토리지와 이름을 지정하고 이미지파일을 생성합니다.
         * FileOutputStream으로 이미지파일에 비트맵을 추가해줍니다.
         */

        File storage = getActivity().getCacheDir(); //  path = /data/user/0/YOUR_PACKAGE_NAME/cache
        String fileName = name + ".jpg";
        File imgFile = new File(storage, fileName);
        try {
            imgFile.createNewFile();
            FileOutputStream out = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); //100 max
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("saveBitmapToJpg","FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("saveBitmapToJpg","IOException : " + e.getMessage());
        }
        Log.d("imgPath" , getActivity().getCacheDir() + "/" +fileName);
        return getActivity().getCacheDir() + "/" +fileName;
    }
}

