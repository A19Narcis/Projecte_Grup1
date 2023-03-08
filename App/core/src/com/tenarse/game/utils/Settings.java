package com.tenarse.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.tenarse.game.helpers.AssetManager;

public class Settings {
    public static final String IP_SERVER = "labs.inspedralbes.cat";
    public static final String PUERTO_PETICIONES = "7073";
    public static final String PUERTO_SOCKETS = "7074";

    //Propietats joc
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 620;

    public static final int PRESSED_UP = 1;
    public static final int PRESSED_LEFT = 2;
    public static final int PRESSED_DOWN = 3;
    public static final int PRESSED_RIGHT = 4;

    //Propietats jugador
    public static final int PLAYER_WIDTH = 32;

    public static final int PLAYER_HEIGHT = 32;
    public static final long PLAYER_ATTACK_DELAY = 250000000l;

    //Propietats Zombie
    public static final int ZOMBIE_WIDTH = 32;
    public static final int ZOMBIE_HEIGHT = 32;
    public static final long ZOMBIE_HIT_DELAY = 1500000000l;
    public static long ZOMBIE_SPAWN_INTERVAL;

    //Efectos
    public static final long POOL_TIME_DESPAWN = 240000000000l;
    public static final long POOL_INTERVAL_ANIMATION = 100000000000l;

    //PROPIETATS ARROW
    public static final float ARROW_VELOCITY = 400;

    //Propietats bonus
    public static final float BONUS_POSIBILITY = 0.2f;
    public static final float B_LIVE_POSIBILITY = 0.10f; //10%
    public static final float B_DAMAGE_POSIBILITY = 0.30f; //20%
    public static final float B_VELOCITY_POSIBILITY = 0.65f;//35%
    public static final float B_SHIELD_POSIBILITY = 0.75f; //10%
    public static final float B_POINTS_POSIBILITY = 1f;//25%
    public static final int BONUS_LIVE = 0;
    public static final int BONUS_DAMAGE = 1;
    public static final int BONUS_VELOCITY = 2;
    public static final int BONUS_SHIELD = 3;
    public static final int BONUS_POINTS = 4;
    public static final long BONUS_DELAY_ACTIVATION = 500000000l;
    public static final long BONUS_TIME_DESPAWN = 15000000000l;
    public static final long B_VELOCITY_TIMEOUT = 10000000000l;
    public static final long B_POINTS_TIMEOUT = 10000000000l;

    public static Preferences prefs = Gdx.app.getPreferences("My Preferences");

}
