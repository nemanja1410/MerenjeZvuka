package com.example.myapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLite extends SQLiteOpenHelper {

    public static final String table = "AMPLITUDE_TABLE";
    public static final String sound = "SOUNDAMP";
    public static final String date = "DATE";
    public static final String time = "TIME";
    public static final String address = "ADDRESS";
    public static final String id = "ID";

    public SQLite(@Nullable Context context) {
        super(context, "sound.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ table +" (" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + sound + " INTEGER, " + date + " TEXT, " + time + " TEXT, " + address + " TEXT)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(Data data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(sound, data.getSoundamp());
        cv.put(date, data.getDate());
        cv.put(time, data.getTime());
        cv.put(address, data.getAddress());

        long insert = db.insert(table, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }
    public List<Data> getAll(){
        List<Data> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToLast()){
            do {
                int ID = cursor.getInt(0);
                int soundamp = cursor.getInt(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                String address = cursor.getString(4);
                Data newData = new Data(ID, soundamp, date, time, address);
                returnList.add(newData);
            }while(cursor.moveToPrevious());
        }

        cursor.close();
        db.close();
        return returnList;
    }

}

