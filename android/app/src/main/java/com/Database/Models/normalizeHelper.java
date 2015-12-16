package com.Database.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by choisunguk on 2015-11-14.
 */
public class normalizeHelper extends SQLiteOpenHelper{
    private static String TAG = "normializerHelper";
    private static normalizeHelper Instance = null;
    private static SQLiteDatabase db;
    private static final String DATABASE_NAME = "tmp.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE = "info";

    private normalizeHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized normalizeHelper getInstance(Context context)
    {
        if(Instance == null) {
            Instance = new normalizeHelper(context.getApplicationContext());
        }
        return Instance;
    }

    public void close()
    {
        if(Instance != null)
            Instance = null;
    }

    public void insert(normalizeInfo in)
    {
        db = Instance.getWritableDatabase();
        db.beginTransaction();

        String qeury = "INSERT INTO " + TABLE +
                "(id, subject, date, weight, startTemp, firstcrackTime, firstcrackTemp, secondcrackTime, secondcrackTemp) " +
                " VALUES (" +
                null + ", " +
                "'" + in.roastername + "', " +
                "'" + in.roasterdate + "', " +
                in.roasterweight  + ", " +
                in.startTemp  + ", " +
                "'" + in.firstTime + "', " +
                in.firsttemp  + ", " +
                "'" + in.secondTime + "'," +
                in.secondtemp  +
                ");";

        db.execSQL(qeury);
        db.setTransactionSuccessful();

        db.endTransaction();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "subject TEXT, " +
                "date TEXT, " +
                "weight INTEGER, " +
                "startTemp INTEGER, " +
                "firstcrackTime TEXT, " +
                "firstcrackTemp INTEGER, " +
                "secondcrackTime TEXT, " +
                "secondcrackTemp INTEGER" +
                ");" ;


        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //oldversion != newversion
        if(i != i1)
        {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    //select sql
    public Cursor getAll()
    {
        db = Instance.getReadableDatabase();
        String sql = "SELECT * from " + TABLE;

        return db.rawQuery(sql, null);
    }

    public Cursor Search(int id)
    {
        db = Instance.getReadableDatabase();
        String sql = "SELECT * from " + TABLE + " WHERE id = " + id;

        Log.d(TAG, sql);
        return db.rawQuery(sql, null);
    }


    //delete branch
    public void delete(String sql)
    {
        db = Instance.getWritableDatabase();
        db.beginTransaction();
        db.execSQL(sql);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
