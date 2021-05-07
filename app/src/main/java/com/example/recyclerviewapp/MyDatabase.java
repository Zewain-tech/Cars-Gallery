package com.example.recyclerviewapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class MyDatabase extends SQLiteAssetHelper {
    public static final String NAME_DB = "cars.db";
    public static final int VERSION_DB = 1;

    public static final String CAR_TB_NAME = "cars";
    public static final String CAR_CLN_ID = "id";
    public static final String CAR_CLN_MODEL = "model";
    public static final String CAR_CLN_COLOR = "color";
    public static final String CAR_CLN_DESCRIPTION = "description";
    public static final String CAR_CLN_IMAGE = "image";
    public static final String CAR_CLN_DISTANCE = "distance";

    public MyDatabase(Context context) {
        super(context, NAME_DB, null, VERSION_DB);
    }

    /*@Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+CAR_TB_NAME+" ("+CAR_CLN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , " +
                ""+CAR_CLN_MODEL+" TEXT," +
                ""+CAR_CLN_COLOR+" TEXT , "+CAR_CLN_DISTANCE+" REAL)" );
    }*/

    /*@Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CAR_TB_NAME);
         onCreate(sqLiteDatabase);

    }*/


}

