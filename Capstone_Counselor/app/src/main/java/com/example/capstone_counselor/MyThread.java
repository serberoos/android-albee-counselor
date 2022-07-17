package com.example.capstone_counselor;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class MyThread extends Thread {

    private File file;
    private Socket clientSocket; //클라이언트 소켓
    private DataInputStream dis;
    private DataOutputStream dos;
    private final int port = 7777; //서버 port
    private final String ip = "117.17.12.121"; // 서버 ip
    private FirebaseAuth mFirebaseAuth;

    @Override
    public void run (){
        try {
            clientSocket = new Socket(ip, port); //클라이언트 소켓 객체 생성
            mFirebaseAuth = FirebaseAuth.getInstance();

            file = new File("/data/user/0/com.example.capstone_counselor/cache/predict_img_file.jpg"); // --> 특정 디렉토리에 있는 파일의 리스트를 가져온다.
            Log.d("EXTERNAL_STORAGEDIRECTORY_PATH","/data/user/0/com.example.capstone_counselor/cache/predict_img_file.jpg");
            dis = new DataInputStream(new FileInputStream(file)); // inputStream에 파일을 연결
            dos = new DataOutputStream(clientSocket.getOutputStream()); //outputStream에 서버를 연결

        }catch (Exception e) {
            e.printStackTrace();
        }

        try { //데이터 찾기


            long fileSize = file.length();
            byte[] buf = new byte[1024];

            long totalReadBytes = 0;

            int readBytes;
            //데이터 찾기 끝

            while ((readBytes = dis.read(buf))>0) { //길이를 정해주고 서버로 전송
                System.out.println("while");

                dos.write(buf, 0, readBytes); // 파일 보내기
                totalReadBytes += readBytes;
            }//클라이언트의 사진 전송 (음식 사진과 숫자 라벨이 포함된 사진)
            dos.writeUTF(mFirebaseAuth.getUid()); //"firebase_uid" ->전송

            dos.close(); //데이터 보내기 끝

        }catch(IOException e){
            Log.d("TCP","don't send message");
            e.printStackTrace();
        }
    }
}

class MyHandler extends Handler { // 메세지 수신후 UI 건드릴때 쓰는 핸들러
    @Override
    public void handleMessage(Message msg) {
        //adapter.add("server : " + msg.obj.toString());
    }
}