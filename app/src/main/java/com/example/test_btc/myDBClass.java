package com.example.test_btc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.test_btc.entity.historyEntity;

import java.util.ArrayList;

public class myDBClass extends SQLiteOpenHelper {
    public  static  final String DATABASE_NAME= "Master.db";
    public  static  final String TABLE_NAME ="History";
    public static  final String COL_id="ID";
    public static  final String COL_time="TIME";
    public static final String COL_usd="USD";
    public static final String COL_eur="EUR";
    public static final String COL_gbp="GBP";


    public myDBClass(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+"(ID INTEGER PRIMARY KEY,TIME TEXT,USD TEXT,EUR TEXT,GBP TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

    }
    public boolean insertData(String time,String usd,String gbp,String eur)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_time,time);
        contentValues.put(COL_usd,usd);
        contentValues.put(COL_eur,eur);
        contentValues.put(COL_gbp,gbp);
        long result=database.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }



    public ArrayList<historyEntity> readCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<historyEntity> courseModalArrayList = new ArrayList<>();
        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new historyEntity(cursorCourses.getString(1),
                        cursorCourses.getString(4),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)));
            } while (cursorCourses.moveToNext());

        }
        cursorCourses.close();
        return courseModalArrayList;
    }

    public void addSelected(String time,String usd,String gbp,String eur) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_time,time);
        values.put(COL_usd,usd);
        values.put(COL_eur,eur);
        values.put(COL_gbp,gbp);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

//    public void addSelected(ArrayList<historyEntity> list)  {
//        int size = list.size();
//
//        SQLiteDatabase db = getWritableDatabase();
//        try {
//            for (int i = 0; i < size; i++) {
//                ContentValues cv = new ContentValues();
//                cv.put(COL_time, String.valueOf(list.get(i)));
//                cv.put(COL_usd, String.valueOf(list.get(i)));
//                cv.put(COL_usd, String.valueOf(list.get(i)));
//                cv.put(COL_gbp, String.valueOf(list.get(i)));
//                Log.d("Added ", "" + cv);
//                db.insertOrThrow(TABLE_NAME, null, cv);
//            }
//            db.close();
//        } catch (Exception e) {
//            Log.e("Problem", e + " ");
//        }
//    }

}