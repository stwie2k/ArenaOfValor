package com.example.alias.arenaofvalor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alias.arenaofvalor.db.HeroDbSchema.HeroTable;

public class HeroBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "heroBase.db";

    public HeroBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + HeroTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                HeroTable.Cols.UUID + ", " +
                HeroTable.Cols.NAME + ", " +
                HeroTable.Cols.IMAGEURL + ", " +
                HeroTable.Cols.BACKGROUNDURL + ", " +
                HeroTable.Cols.ALIAS + ", " +
                HeroTable.Cols.POSITION + ", " +
                HeroTable.Cols.LIVE + ", " +
                HeroTable.Cols.ATTACK + ", " +
                HeroTable.Cols.SKILL + ", " +
                HeroTable.Cols.DIFFICULTY +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
