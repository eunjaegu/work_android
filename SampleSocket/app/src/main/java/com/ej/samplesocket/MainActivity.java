package com.ej.samplesocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    // 서버 구동 후, 사용자가 입력한 값 추출을 위한
    EditText editText;

    // 클라이언트와 서버에 주고 받은 데이터 출력을 위한
    TextView textView;
    TextView textView2;

    // 전송받은 데이터를 액티비티의 위젯에 출현하기 위한
    // 핸들러는 데이터를 받는 쪽에 선언!!!
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        //Client: 전송 버튼 클릭 시
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자 입력 값 추출(스레드를 통해 전달되기 때문에, 클릭할 때마다 재선언)
                final String data = editText.getText().toString();

                // 스레드
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        //서버에게 데이터를 전송하는 메서드 호출(send())
                        send(data);
                    }
                }).start();
            }
        });

        //Server: 서버 시작 버튼 클릭 시
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //서버를 구동하는 메서드 호출(startServer())
                        startServer();
                    }
                }).start();
            }
        });
    }

    //Client쪽 로그를 화면에 있는 텍스트뷰를 출력하기 위해 핸들러 사용하기
    public void printClientLog(final String data){
        Log.d("MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });
    }

    //Sever쪽 로그를 화면에 있는 텍스트뷰를 출력하기 위해 핸들러 사용하기
    public void printServerLog(final String data){
        Log.d("MainActivity", data);

        handler.post(new Runnable() {
            @Override
            public void run() {
                textView2.append(data+"\n");
            }
        });
    }

    /* Client : 서버에게 입력된 값을 전송하는 메서드 선언 */
    public void send(String data){
        try{
            // 서버와 통신할 수 있는 포트
            int portNumber=5001;
            // 클라이언트 소켓
            Socket sock = new Socket("localhost", portNumber);
            printClientLog("소켓 연결함.");

            // 서버에게 보낼 데이터 (Stream 방식)
            ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());

            // 데이터 쓰기
            outstream.writeObject(data);
            outstream.flush();
            printClientLog("데이터 전송함.");

            // 서버가 보내온 데이터 읽기 (Stream 방식)
            ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
            printClientLog("서버로부터 받음:" + instream.readObject());

            // 소켓 닫기
            sock.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /*Server : 서버를 구동시키는 메서드 선언*/
    public void startServer(){
        try {
            // 클라이언트와 통신할 수 있는 포트
            int portNumber = 5001;

            // 서버 소켓
            ServerSocket server = new ServerSocket(portNumber);
            printServerLog("서버 시작함 : " + portNumber);

            // 서버 계속 수동 중..
            while(true) {
                // 클라이언트의 소켓에 대한 감지...
                Socket sock = server.accept();
                // 클라이언트 주소
                InetAddress clientHost = sock.getLocalAddress();
                // 클라이언트 포트
                int clientPort = sock.getPort();
                printServerLog("클라이언트 연결됨 : " + clientHost + " : " + clientPort);

                //데이터 읽기(Stream 방식)
                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                // Stream 데이터 읽기
                Object obj = instream.readObject();
                printServerLog("데이터 받음 : " + obj);

                //클라이언트에게 전송할 데이터(Stream 방식)
                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                //클라이언트에게 전송할 데이터 쓰기
                outstream.writeObject(obj + " from Server.");
                outstream.flush();
                printServerLog("데이터 보냄.");

                //소켓 닫기
                sock.close();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}

