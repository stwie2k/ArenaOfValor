package com.example.alias.arenaofvalor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.alias.arenaofvalor.db.HeroBaseHelper;
import com.example.alias.arenaofvalor.db.HeroCursorWrapper;
import com.example.alias.arenaofvalor.db.HeroDbSchema.HeroTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class HeroLab {
    private static HeroLab sHeroLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static HeroLab get(Context context) {
        if (sHeroLab == null) {
            sHeroLab = new HeroLab(context);
        }
        return sHeroLab;
    }

    private HeroLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new HeroBaseHelper(mContext)
                .getWritableDatabase();
    }

    public List<Hero> getHeros() {
        List<Hero> Heros = new ArrayList<>();

        HeroCursorWrapper cursor = queryHeros(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Heros.add(cursor.getHero());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }


        //now it returns a snapshot of the whole database
        //rather than a reference of mHeros before
        return Heros;
    }

    public Hero getHero(UUID uuid) {
        HeroCursorWrapper cursor = queryHeros(
                HeroTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getHero();
        } finally {
            cursor.close();
        }

    }

    public void addHero(Hero c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(HeroTable.NAME, null, values);
    }

    public void updateHero(Hero Hero) {
        String name = Hero.getName();
        ContentValues values = getContentValues(Hero);

        mDatabase.update(HeroTable.NAME, values,
                HeroTable.Cols.UUID + " = ?",
                new String[]{Hero.getUuid().toString()});
    }


    private HeroCursorWrapper queryHeros(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                HeroTable.NAME,
                null, // Select al the columns
                whereClause,
                whereArgs,
                null, //groupBy
                null, //having
                null //orderBy
        );

        return new HeroCursorWrapper(cursor);

    }

    private static ContentValues getContentValues(Hero Hero) {
        ContentValues values = new ContentValues();
        values.put(HeroTable.Cols.UUID, Hero.getUuid().toString());
        values.put(HeroTable.Cols.NAME, Hero.getName());
        values.put(HeroTable.Cols.BACKGROUNDURL, Hero.getBackground_url());
        values.put(HeroTable.Cols.IMAGEURL, Hero.getImage_url());
        values.put(HeroTable.Cols.ALIAS, Hero.getAlias());
        values.put(HeroTable.Cols.POSITION, Hero.getPosition());
        values.put(HeroTable.Cols.LIVE, Hero.getLive());
        values.put(HeroTable.Cols.ATTACK, Hero.getAttack());
        values.put(HeroTable.Cols.SKILL, Hero.getSkill());
        values.put(HeroTable.Cols.DIFFICULTY, Hero.getDifficulty());

        return values;
    }

    public void deleteHero(Hero Hero) {
        mDatabase.delete(HeroTable.NAME,
                HeroTable.Cols.UUID + " = ? ",
                new String[]{Hero.getUuid().toString()});
        ;
    }
}