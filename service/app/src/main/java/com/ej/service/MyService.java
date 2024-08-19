package com.ej.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final  String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {        //서비스 시작 직전 호출
        super.onCreate();
        Log.d(TAG,"onCreate() 호출됨.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand() 호출됨.");

        //MainActivity로부터 전달된 인텐트 객체의 값 추출
        if (intent == null){
            return Service.START_STICKY;
        } else{
            //메서드 선언 후 처리
            processCommand(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //전달받은 인텐트 객체가 null이 아닐 경우 호출되는 메서드
    private void processCommand(Intent intent){
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");

        Log.d(TAG, "command : " + command + ", name : " + name);

        for (int i = 0; i<5; i++){
            try{

                Thread.sleep(1000);
            }catch (Exception e){};
            Log.d(TAG, "Waiting" + i + "seconds");
        }

        Intent showIntent = new Intent (getApplicationContext(), MainActivity.class);

        showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);  //플러그 중복가능
        showIntent.putExtra("command", "show");
        showIntent.putExtra("name", name + "from service.");
        startActivity(showIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}