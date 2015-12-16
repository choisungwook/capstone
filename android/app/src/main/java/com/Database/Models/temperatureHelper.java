package com.Database.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by choisunguk on 2015-11-14.
 */
public class temperatureHelper extends SQLiteOpenHelper {
    private static temperatureHelper Instance;
    private static final String DATABASE_NAME = "roasting.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "tempature";

    private temperatureHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized temperatureHelper getInstance(Context context)
    {
        if(Instance == null)
            Instance = new temperatureHelper(context.getApplicationContext());
        return Instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" +
                "Time TEXT, " +
                "Tempature INTEGER, " +
                ")" ;

        sqLiteDatabase.execSQL( CREATE_TABLE);
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
}
