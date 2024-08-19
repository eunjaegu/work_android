package com.ej.databasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //MainActivity는 Context를 상속받아 선언됨
    // 데이터베이스 이름,테이블 이름 추출
    EditText editText;
    EditText editText2;

    // 데이터 베이스 이름(~.db)
    TextView textView; //테이블 이름

    DatabaseHeplper dbHelper;
    SQLiteDatabase database;  // 상태를 출력

    String tableName; //생성된 테이블명 저장 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // editText에 입력된 데이터베이스 호출
                String databaseName = editText.getText().toString();
                // 데이터베이스 생성 메서드 호출
                createDatabase(databaseName);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //에 입력된 테이블명 추출
                tableName = editText2.getText().toString();

                // 테이블 생성 메서드 호출
                createTable(tableName);

                // 레코드 삽입 메서드 호출
                insertRecord();
            }
        });

        //레코드 조회 버튼
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //레코드 조회 메서드 호출..
                executeQuery();
            }
        });
    } // onCreate() END

    /*
    입력된 값(editText)를 이용하여 데이터베이스를 생성하는 메서드 선언     */
    private void createDatabase(String name) {
        println("createDatabase 호출됨.");

        database = openOrCreateDatabase(name, MODE_PRIVATE, null);
        println("데이터베이스 생성함 : " + name);
    } //createDatabase() END

    /* 입력된 값(editText2)을 이용하여 테이블을 생성하는 메서드 선언 */
    private void createTable(String name) {
        println("createTable 호출됨.");

        if (database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        // 기존에 테이블이 존재하면 생성하지 말고, 없으면 생성 : create table if not exists 테이블명
        // 테이블 생성 쿼리 작성
        String guery = "create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " mobile text)";
        // SQL을 실행시켜주는 메서드  : SQLiteDatabase 객체의 execSQL()
        database.execSQL(guery);

        println("테이블 생성함 : " + name);
    }

    /* 레코드를 추가하는 메서드 선언*/
    private void insertRecord() {
        println("insertRecord 호출됨.");

        if (database == null) {
            println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        if (tableName == null) {
            println("테이블을 먼저 생성하세요.");
            return;
        }
        // 레코드 삽입 쿼리 작성
        String query = "insert into " + tableName
                + "(name, age, mobile) "
                + " values "
                + "('John', 20, '010-1000-1000')";
        // SQL 실행메서드
        database.execSQL(query);

        println("레코드 추가함.");
    }

    /* 상태를 출력(textView)하는 메서드 선언 */
    public void println(String data) {
        textView.append(data + "\n");
    }

    public void executeQuery() {
        println("executeQuery 호출됨.");

        Cursor cursor = database.rawQuery("select _id, name, age, mobile from emp", null);
        int recordCount = cursor.getCount();
        println("레코드 개수 : " + recordCount);

        //조회된 전체 레코드들을 보유하고 있는 Cursor 객체로부터 하나씩 추출
        for (int i = 0; i < recordCount; i++) {

            //moveToNext : 그 다음 레코드를 가리킴. (레코드값 가져오기 위해)
            cursor.moveToNext();

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int age = cursor.getInt(2);
            String mobile = cursor.getString(3);

            println("레코드 #" + i + " : " + id + ", " + name + ", " + age + ", " + mobile);
        }

        // Cursor 객체 종료
        cursor.close();
    }

}