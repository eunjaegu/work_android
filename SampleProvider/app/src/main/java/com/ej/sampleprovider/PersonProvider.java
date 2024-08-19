package com.ej.sampleprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.Nullable;

public class PersonProvider extends ContentProvider {

    private static final String AUTHORITY = "com.ej.sampleprovider";
    private static final String BASE_PATH = "person";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, PERSONS);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", PERSON_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                cursor =  database.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.ALL_COLUMNS,
                        s,null,null,null,DatabaseHelper.PERSON_NAME +" ASC");
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                return "vnd.android.cursor.dir/persons";
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        // 테이블에 새로운 레코드 추가  : 이때 insert() 는 추가된 레코드의 개수를 반환
        long id = database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

        //레코드가 추가되었으면
        if (id > 0) {
            //추가된 레코드의 위치(주소)를 추출
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            
            // 내 앱의 Context에게 변경 사항이 있음을 알림.
            getContext().getContentResolver().notifyChange(_uri, null);
            
            // 하여 반환
            return _uri;
        }

        // id 값이 0 또는 -1 일 경우에는 레코드 추가에 실패했기 때문에 예외 발생 시킴..
        throw new SQLException("추가 실패 -> URI :" + uri);
    }

    //컨텐트 삭제 메서드(전체 삭제 vs 지정한 아이디만 삭제)
    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count =  database.delete(DatabaseHelper.TABLE_NAME, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    // 수정 메서드(전체 수정 vs 지정하여 수정)
    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case PERSONS:
                count =  database.update(DatabaseHelper.TABLE_NAME, contentValues, s, strings);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

}
