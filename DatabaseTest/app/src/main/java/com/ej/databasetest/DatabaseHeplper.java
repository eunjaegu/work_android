package com.ej.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHeplper extends SQLiteOpenHelper {

    public static String NAME = "employee.db";
    public static int VERSION = 1;

    public DatabaseHeplper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // 새로운 데이터베이스 
        println("onCreate 호출됨");

        //테이블(emp) 생성
        String sql = "create table if not exists emp("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " mobile text)";

        db.execSQL(sql);
    }

    public void onOpen(SQLiteDatabase db) {
        //상태 값만 출력
        println("onOpen 호출됨");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        println("onUpgrade 호출됨 : " + oldVersion + " -> " + newVersion);

        //기존 데이터베이스 버전과 신규 데이터 베이스 버전 비교
        if (newVersion > 1) {
            db.execSQL("DROP TABLE IF EXISTS emp");
        }
    }

    public void println(String data) {
        Log.d("DatabaseHelper", data);
    }

}
