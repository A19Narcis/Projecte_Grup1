package com.tenarse.game.utils;

public class Settings {

    //Propietats joc
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 620;

    public static final int PRESSED_UP = 1;
    public static final int PRESSED_LEFT = 2;
    public static final int PRESSED_DOWN = 3;
    public static final int PRESSED_RIGHT = 4;

    //Propietats jugador
    public static int PLAYER_VELOCITY = 1;
    public static int PLAYER_FUERZA = 1;
    public static int PLAYER_VIDAS = 1;
    public static int PLAYER_ARMADURA = 0;

    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;
    public static final long PLAYER_ATTACK_DELAY = 500000000l;

    //Propietats Zombie
    public static final int ZOMBIE_WIDTH = 32;
    public static final int ZOMBIE_HEIGHT = 32;
    public static final int ZOMBIE_VELOCITY = 50;
    public static final int ZOMBIE_LIFE = 2;
    public static final long ZOMBIE_HIT_DELAY = 500000000l;
    public static final int ZOMBIE_FUERZA = 1;
    public static final long ZOMBIE_SPAWN_INTERVAL = 30000000000l;

    //Propietats Boss
    public static final int BOSS_WIDTH = 32;
    public static final int BOSS_HEIGHT = 32;
    public static final int BOSS_VELOCITY = 60;
    public static final int BOSS_LIFE = 10;
    public static final long BOSS_HIT_DELAY = 750000000l;
    public static final int BOSS_FUERZA = 2;
    public static final long BOSS_SPAWN_INTERVAL = 3000000000l;

    //Efectos
    public static final long POOL_TIME_DESPAWN = 240000000000l;
    public static final long POOL_INTERVAL_ANIMATION = 1000000000l;

    //PROPIETATS ARROW
    public static final float ARROW_VELOCITY = 400;
    public static final float ARROW_VLOCITY_MULTIPLIER = 8;

    //Propietats bonus
    public static final float BONUS_POSIBILITY = 1f;
    public static final float B_LIVE_POSIBILITY = 0.15f; //15%
    public static final float B_DAMAGE_POSIBILITY = 0.35f; //35%
    public static final float B_VELOCITY_POSIBILITY = 0.35f;//35%
    public static final float B_SHIELD_POSIBILITY = 0.15f;//15%
    public static final int BONUS_LIVE = 0;
    public static final int BONUS_DAMAGE = 1;
    public static final int BONUS_VELOCITY = 2;
    public static final int BONUS_SHIELD = 3;
    public static final long BONUS_DELAY_ACTIVATION = 500000000l;
    public static final long BONUS_TIME_DESPAWN = 15000000000l;
    public static final long B_VELOCITY_TIMEOUT = 10000000000l;
}
