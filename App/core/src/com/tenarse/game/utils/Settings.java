package com.tenarse.game.utils;

public class Settings {

    //Propietats joc
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 620;
    public static final long SPAWN_INTERVAL = 3000000000l;// X nanosegundos * 1000000000 = X Zombies * Segundos

    public static final int PRESSED_UP = 1;
    public static final int PRESSED_LEFT = 2;
    public static final int PRESSED_DOWN = 3;
    public static final int PRESSED_RIGHT = 4;

    //Propietats jugador
    public static final float PLAYER_VELOCITY = 100;
    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;
    public static final int PLAYER_STRENGTH = 1;

    //Propietats Zombie
    public static final int ZOMBIE_WIDTH = 32;
    public static final int ZOMBIE_HEIGHT = 32;
    public static final int ZOMBIE_VELOCITY = 50;
    public static final int ZOMBIE_LIFE = 3;

    //Propietats Boss
    public static final int BOSS_WIDTH = 45;
    public static final int BOSS_HEIGHT = 45;
    public static final int BOSS_VELOCITY = 500;
}
