package com.thereza.sqlitemcc.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thereza.sqlitemcc.data.FormDataModel;

import java.util.ArrayList;

/**
 * Created by theReza on 1/11/2018.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    public static MyDBHelper sInstance;

    public static final String DB_NAME = "sqlite_db";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase mDb;
    public static final String DB_TABLE = "testData";
    public static final String ID_FIELD = "_id";
    public static final String NAME_FIELD = "name";
    public static final String AGE_FIELD = "age";
    public static final String PHONE_FIELD = "phone";
    public static final String EMAIL_FIELD = "email";
    private static final String KEY_IMAGE = "image_data";

    public static final String DB_TABLE_SQL = "CREATE TABLE "
            + DB_TABLE + " (" + ID_FIELD + " INTEGER PRIMARY KEY, "
            + NAME_FIELD + " TEXT, " + AGE_FIELD + " TEXT, " + PHONE_FIELD
            + " TEXT, " + EMAIL_FIELD + " TEXT," +
            KEY_IMAGE + " BLOB NOT NULL )";


    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static MyDBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MyDBHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(DB_TABLE_SQL);
        // db.execSQL("CREATE TABLE TASK (_id INTEGER PRIMARY KEY, task_name TEXT, task_description TEXT)");
        Log.e("TABLE CREATE", DB_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {


    }

    // insert
    public long insertStudent(FormDataModel s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_FIELD, s.getName());
        values.put(PHONE_FIELD, s.getPhone());
        values.put(AGE_FIELD, s.getAge());
        values.put(EMAIL_FIELD, s.getEmail());
        values.put(KEY_IMAGE,s.getImageData());

        long inserted = db.insert(DB_TABLE, null, values);

        db.close();
        return inserted;
    }

    // query
    public ArrayList<FormDataModel> getAllStudents() {
        ArrayList<FormDataModel> allStudents = new ArrayList<FormDataModel>();
        SQLiteDatabase db = this.getReadableDatabase();

        // String[] columns={NAME_FIELD, EMAIL_FIELD, PHONE_FIELD};
        // SELECT * FROM EMPLOYEE;
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null,
                null);

        // Cursor cursor = db.rawQuery("SELECT * FROM EMPLOYEE", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                //
                int id = cursor.getInt(cursor.getColumnIndex(ID_FIELD));
                String name = cursor.getString(cursor
                        .getColumnIndex(NAME_FIELD));
                String age = cursor.getString(cursor
                        .getColumnIndex(AGE_FIELD));
                String phone = cursor.getString(cursor
                        .getColumnIndex(PHONE_FIELD));
                String email = cursor.getString(cursor
                        .getColumnIndex(EMAIL_FIELD));
                byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));

                FormDataModel st = new FormDataModel(name, age, phone,email,blob);
                allStudents.add(st);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return allStudents;
    }


}
