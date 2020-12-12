package com.example.pomotasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.Display;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "PomoTasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Tasks";
    private static final String TASK_ID = "id";
    private static final String TASK_NAME = "name";
    private static final String TASK_STATUS = "status";

//    private static final String TABLE2_NAME = "Completed Tasks";
//    private static final String COMPLETED_TASK_ID = "id";
//    private static final String COMPLETED_TASK_NAME = "name";
    private static final String COMPLETED_TASK_CAPTION = "caption";
    private static final String COMPLETED_TASK_IMAGE = "image";
    private static final String COMPLETED_TASK_LOCATION = "loc";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_NAME+
                " ("+TASK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TASK_NAME+" TEXT, "+
                TASK_STATUS+" INTEGER, "+
                COMPLETED_TASK_IMAGE+" TEXT, "+
                COMPLETED_TASK_CAPTION+" TEXT, "+
                COMPLETED_TASK_LOCATION+" TEXT); ";
        db.execSQL(query);
//        String query2 = "CREATE TABLE "+TABLE2_NAME+
//                " ("+COMPLETED_TASK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
//                COMPLETED_TASK_NAME+" TEXT, "+
//                COMPLETED_TASK_CAPTION+" TEXT, "+
//                COMPLETED_TASK_IMAGE + " TEX);";
//        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    void addTask(String title, int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_NAME, title);
        cv.put(TASK_STATUS, status);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String status2 = "0";
        String query ="SELECT * FROM "+TABLE_NAME+" WHERE "+TASK_STATUS+" = "+status2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

//    Cursor readAllData2(){
//        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+TASK_STATUS+"=1";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = null;
//        if(db!=null){
//            cursor = db.rawQuery(query, null);
//        }
//        return cursor;
//    }

    public ArrayList<ModelRecord> getAllRecords(String orderBy){
        ArrayList<ModelRecord> recordList = new ArrayList<>();
        String status = "1";
        String selectQuery = "SELECT * FROM "+TABLE_NAME+" WHERE "+TASK_STATUS+" = "+status;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                ModelRecord modelRecord = new ModelRecord(
                        ""+cursor.getInt(cursor.getColumnIndex(TASK_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(TASK_NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(COMPLETED_TASK_CAPTION)),
                        ""+cursor.getString(cursor.getColumnIndex(COMPLETED_TASK_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndex(COMPLETED_TASK_LOCATION))
                );
                recordList.add(modelRecord);
            }while (cursor.moveToNext());
        }
        return recordList;
    }

    public int getRecordCount(){
        String countQuery = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;

    }

    void updateData(String id, String name, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_ID,id);
        cv.put(TASK_NAME,name);
        cv.put(TASK_STATUS,status);
//        String query = "UPDATE "+TABLE_NAME+" SET "+TASK_NAME+" = "+name+" WHERE "+TASK_ID+" = "+id;
//        db.execSQL(query);
        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();
        }
    }

    void insertCompletedTaskRecord(String id, String caption, String img, String loc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TASK_STATUS,1);
        cv.put("caption",caption);
        cv.put("image",img);
        cv.put(COMPLETED_TASK_LOCATION,loc);
        String whereClause = "id=?";
        String whereArgs[]={id};
        db.update(TABLE_NAME, cv, whereClause, whereArgs);
    }
}