package com.example.alias.arenaofvalor;

import java.io.Serializable;
import java.util.UUID;

public class Hero implements Serializable {
    private UUID uuid;
    private String image_url;
    private String background_url;
    private String name;
    private String alias;
    private String position;
    private int live;
    private int attack;
    private int skill;
    private int difficulty;


    public Hero(String imageurl, String background_url, String name, String alias, String position, int live, int attack, int skill, int difficulty) {
        this.uuid = UUID.randomUUID();
        this.image_url = imageurl;
        this.background_url = background_url;
        this.name = name;
        this.alias = alias;
        this.position = position;
        this.live = live;
        this.attack = attack;
        this.skill = skill;
        this.difficulty = difficulty;
    }

    public Hero(UUID tUuid, String imageurl, String background_url, String name, String alias, String position, int live, int attack, int skill, int difficulty) {
        this.uuid = tUuid;
        this.image_url = imageurl;
        this.background_url = background_url;
        this.name = name;
        this.alias = alias;
        this.position = position;
        this.live = live;
        this.attack = attack;
        this.skill = skill;
        this.difficulty = difficulty;
    }

    public String getBackground_url() {
        return background_url;
    }

    public void setBackground_url(String background_url) {
        this.background_url = background_url;
    }

    public int getAttack() {
        return attack;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getLive() {
        return live;
    }

    public int getSkill() {
        return skill;
    }

    public String getAlias() {
        return alias;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setLive(int live) {
        this.live = live;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public UUID getUuid() {
        return uuid;
    }

}
