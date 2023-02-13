package com.tenarse.game.utils;

public class Settings {

    //Propietats joc
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 620;
    public static final long SPAWN_INTERVAL = 3 * 1000000000l;

    public static final int PRESSED_UP = 1;
    public static final int PRESSED_LEFT = 2;
    public static final int PRESSED_DOWN = 3;
    public static final int PRESSED_RIGHT = 4;

    //Propietats jugador
    public static int PLAYER_VELOCITY = 1;
    public static int PLAYER_FUERZA = 1;
    public static int PLAYER_VIDAS = 0;
    public static int PLAYER_ARMADURA = 0;

    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;
    public static final long PLAYER_ATTACK_DELAY = 1000000000l;

    //Propietats Zombie
    public static final int ZOMBIE_WIDTH = 32;
    public static final int ZOMBIE_HEIGHT = 32;
    public static final int ZOMBIE_VELOCITY = 50;
    public static final int ZOMBIE_LIFE = 2;
    public static final long ZOMBIE_HIT_DELAY = 1000000000l;

    //Propietats Boss
    public static final int BOSS_WIDTH = 45;
    public static final int BOSS_HEIGHT = 45;
    public static final int BOSS_VELOCITY = 500;

    //Efectos
    public static final long POOL_TIME_DESPAWN = 240000000000l;
    public static final long POOL_INTERVAL_ANIMATION = 1000000000l;

    //PROPIETATS ARROW
    public static final float ARROW_VELOCITY = 800;
}
