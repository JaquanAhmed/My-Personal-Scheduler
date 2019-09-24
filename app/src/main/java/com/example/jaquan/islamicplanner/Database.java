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
    public static final String COL_1 = "ID";
    public static final String COL_2 = "eventName";
    public static final String COL_3 = "eventDate";
    public static final String COL_4 = "eventTime";
    public static final String COL_5 = "repeatYearly";
    public static final String COL_6 = "repeatEveryday";
    public static final String COL_7 = "monday";
    public static final String COL_8 = "tuesday";
    public static final String COL_9 = "wednesday";
    public static final String COL_10 = "thursday";
    public static final String COL_11 = "friday";
    public static final String COL_12 = "saturday";
    public static final String COL_13 = "sunday";


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
        contentValues.put(COL_2, eventName);
        contentValues.put(COL_3, eventDate);
        contentValues.put(COL_4, eventTime);
        contentValues.put(COL_5, repeatYearly);
        contentValues.put(COL_6, repeatEveryday);
        contentValues.put(COL_7, monday);
        contentValues.put(COL_8, tuesday);
        contentValues.put(COL_9, wednesday);
        contentValues.put(COL_10, thursday);
        contentValues.put(COL_11, friday);
        contentValues.put(COL_12, saturday);
        contentValues.put(COL_13, sunday);

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

    public Cursor getData(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_6 + " == 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}
