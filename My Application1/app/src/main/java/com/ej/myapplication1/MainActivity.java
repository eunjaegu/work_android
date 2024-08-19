package com.ej.myapplication1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;

    GestureDetector detector;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buttom);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.edittext);
    }

    public void onButtonClicked(View v){
        String str = editText.getText().toString();
        if(str.length() > 0){
            textView.setText(str);
        }
    }

 */

    @Override
    protected void onCreate(Bundle savedInstavceState) {
        super.onCreate(savedInstavceState);
        setContentView(R.layout.touch_main);

        textView = findViewById(R.id.textView);

        View view2 = findViewById(R.id.view2);
        View.OnTouchListener tt = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                float curX = motionEvent.getX();
                float curY = motionEvent.getY();

                if (action == MotionEvent.ACTION_DOWN) {
                    println("손가락 눌림 : " + curX + ", " + curY);
                } else if (action == MotionEvent.ACTION_MOVE) {
                    println("손가락 움직임 : " + curX + ", " + curY);
                } else if (action == MotionEvent.ACTION_UP) {
                    println("손가락 뗌 : " + curX + ", " + curY);
                }

                return true;
            }
        };

        view2.setOnTouchListener(tt);

        detector = new GestureDetector(this, new GestureDetector.OnGestureListener() {

            @Override
            public boolean onDown(MotionEvent motionEvent) {
                println("onDown() 호출됨.");

                return true;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                println("onShowPress() 호출됨.");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                println("onSingleTpaUp() 호출됨.");

                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                println("onScroll() 호출됨." + v + ", " + v1);

                return true;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                println("onLongPress() 호출됨.");
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                println("onScroll() 호출됨." + v + ", " + v1);

                return true;
            }

        });

        // View view2 = findViewById(R.id.view2);
        view2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

            public void println(String data) {
                textView.append(data + "\n");
            }

            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    println("시스템 [BACK] 버튼이 눌렸습니다.");
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    println("시스템 [중앙] 버튼이 눌렸습니다.");
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_CAMERA) {
                    println("시스템 [CAMERA] 버튼이 눌렸습니다.");
                    return true;
                }

                return false;
    }
}