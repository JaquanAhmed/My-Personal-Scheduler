package com.example.jaquan.islamicplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";
    public static final String DATABASE_NAME = "Calendar Database";
    public static final String TABLE_NAME = "Events";
    public static final String ID = "ID";
    public static final String N = "eventName";
    public static final String D = "eventDate";
    public static final String T = "eventTime";
    public static final String RY = "repeatYearly";
    public static final String RE = "repeatEveryday";
    public static final String Monday = "monday";
    public static final String Tuesday = "tuesday";
    public static final String Wednesday = "wednesday";
    public static final String Thursday = "thursday";
    public static final String Friday = "friday";
    public static final String Saturday = "saturday";
    public static final String Sunday = "sunday";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, eventName TEXT, eventDate TEXT, eventTime TEXT, " +
                "repeatYearly INTEGER DEFAULT 0, repeatEveryday INTEGER DEFAULT 0, monday INTEGER DEFAULT 0, tuesday INTEGER DEFAULT 0," +
                "wednesday INTEGER DEFAULT 0, thursday INTEGER DEFAULT 0, friday INTEGER DEFAULT 0, saturday INTEGER DEFAULT 0, sunday INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean addTableData(String eventName, String eventDate, String eventTime, int repeatYearly, int repeatEveryday,
                                       int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(N, eventName);
        contentValues.put(D, eventDate);
        contentValues.put(T, eventTime);
        contentValues.put(RY, repeatYearly);
        contentValues.put(RE, repeatEveryday);
        contentValues.put(Monday, monday);
        contentValues.put(Tuesday, tuesday);
        contentValues.put(Wednesday, wednesday);
        contentValues.put(Thursday, thursday);
        contentValues.put(Friday, friday);
        contentValues.put(Saturday, saturday);
        contentValues.put(Sunday, sunday);

        Log.d(TAG, "addData: Adding " + eventName + ", " + eventDate + " and " + eventTime + " and " + repeatYearly + " and " +
                repeatEveryday + " and " + monday + " and " + tuesday + " and " + wednesday + " and " + thursday + " and " +
                friday + " and " + saturday + " and " + sunday + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(String date, String day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + RE + " = 1 OR " + day + "= 1 OR " + "(" + RY
                + " = 1 AND " + D + " = " + date + ")";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}
