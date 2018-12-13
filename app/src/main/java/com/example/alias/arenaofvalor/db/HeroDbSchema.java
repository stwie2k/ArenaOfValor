package com.example.alias.arenaofvalor.db;

public class HeroDbSchema {

    public static final class HeroTable {
        public static final String NAME = "heros";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String IMAGEURL = "image_url";
            public static final String BACKGROUNDURL = "background_url";
            public static final String ALIAS = "alias";
            public static final String POSITION = "position";
            public static final String LIVE = "live";
            public static final String ATTACK = "attack";
            public static final String SKILL = "skill";
            public static final String DIFFICULTY = "difficulty";
        }
    }


}
