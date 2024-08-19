package com.ej.thread440;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    MainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BackgroundThread thread = new BackgroundThread();
                thread.start();
            }
        });

        handler = new MainHandler();
    }

    class BackgroundThread extends  Thread{
        int value = 0;

        public void run(){
            for(int i = 0; i<100; i++){
                try{
                    Thread.sleep(1000);
                }catch(Exception e){}
                value +=1;
                Log.d("Thread", "value:" + value);

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);

                handler.sendMessage(message);
            }
        }

    }

    class MainHandler extends Handler{

        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            // 메세지의 데이터를 받아오는 코드를 번들에 담음

            int value = bundle.getInt("value");
            // get으로 데이터 가져오기
            textView.setText("value 값 : " + value);
            //데이터 전달을 하기위해
            //텍스트뷰 객체의 setText메서드를 호출하는 코드가
            // 핸들러 클래스의 handleMessage 메서드 안으로 이동해야하므로
            // handleMessage메서드로 value 값을 전달해야하는 문제가 생김.
            // 따라서 value 값을 Message객체를 넣어서 보내는 것이 필요.
        }

    }

}