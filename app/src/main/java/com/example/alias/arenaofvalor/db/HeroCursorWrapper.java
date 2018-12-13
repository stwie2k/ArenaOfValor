package com.example.alias.arenaofvalor.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.alias.arenaofvalor.Hero;
import com.example.alias.arenaofvalor.db.HeroDbSchema.HeroTable;

import java.util.UUID;

public class HeroCursorWrapper extends CursorWrapper {
    public HeroCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Hero getHero() {
        String uuidString = getString(getColumnIndex(HeroTable.Cols.UUID));
        String image_url = getString(getColumnIndex(HeroTable.Cols.IMAGEURL));
        String name = getString(getColumnIndex(HeroTable.Cols.NAME));
        String alias = getString(getColumnIndex(HeroTable.Cols.ALIAS));
        String position = getString(getColumnIndex(HeroTable.Cols.POSITION));
        String background_url = getString(getColumnIndex(HeroTable.Cols.BACKGROUNDURL));

        int live = getInt(getColumnIndex(HeroTable.Cols.LIVE));
        int attack = getInt(getColumnIndex(HeroTable.Cols.ATTACK));
        int skill = getInt(getColumnIndex(HeroTable.Cols.SKILL));
        int difficulty = getInt(getColumnIndex(HeroTable.Cols.DIFFICULTY));

//        Hero hero = new Hero(UUID.fromString(uuidString));
        Hero hero = new Hero(UUID.fromString(uuidString), image_url, background_url, name, alias, position, live, attack, skill, difficulty);

        return hero;
    }
}
