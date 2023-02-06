package com.tenarse.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class AssetManager {

    //Tiled Map
    public static TiledMap map;
    public static TmxMapLoader mapLoader;

    //Textures
    public static Texture sheetPlayerAxe;
    public static Texture sheetPlayerWar;
    public static Texture sheetPlayerShield;
    public static Texture sheetZombie;
    public static Texture imgMainMenu;

    //Images
    public static TextureRegion playerUpA, playerDownA, playerLeftA, playerRightA;
    public static TextureRegion playerUpW, playerDownW, playerLeftW, playerRightW;
    public static TextureRegion playerUpS, playerDownS, playerLeftS, playerRightS;
    public static TextureRegion zombieUp, zombieDown, zombieLeft, zombieRight, zombieSpawn;

    public static TextureRegion[] playerRightA_Animation, playerLeftA_Animation, playerUpA_Animation, playerDownA_Animation, playerRightA_Attack, playerLeftA_Attack, playerUpA_Attack, playerDownA_Attack;
    public static TextureRegion[] playerRightW_Animation, playerLeftW_Animation, playerUpW_Animation, playerDownW_Animation;
    public static TextureRegion[] playerRightS_Animation, playerLeftS_Animation, playerUpS_Animation, playerDownS_Animation;
    public static TextureRegion[] zombieRight_Animation, zombieLeft_Animation, zombieUp_Animation, zombieDown_Animation, zombieSpawn_Animation;

    //Skins Buttons
    public static Texture imgPlayBtn, imgSingleBtn, imgMultiBtn, imgReturnBtn;
    public static Texture btnMovUp, btnMovDown, btnMovLeft, btnMovRight;


    //Sounds


    //Font


    public static void load(){
        //Tiled map load
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Mapas/developmentMap/developmentMap.tmx");


        //Jugador AXE
        sheetPlayerAxe = new Texture(Gdx.files.internal("animate_axe_0.png"));
        sheetPlayerAxe.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Jugador Warhammer
        sheetPlayerWar = new Texture(Gdx.files.internal("animate_warhammer.png"));
        sheetPlayerWar.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Jugador Shield
        sheetPlayerShield = new Texture(Gdx.files.internal("animate_kite_shield.png"));
        sheetPlayerShield.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Jugador Shield
        sheetZombie = new Texture(Gdx.files.internal("Boss.png"));
        sheetZombie.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Sheet PJ AXE
        playerDownA = new TextureRegion(sheetPlayerAxe, 0, 64*10, 64, 64);
        playerDownA.flip(false, false);

        playerUpA = new TextureRegion(sheetPlayerAxe, 0, 64*8, 64, 64);
        playerUpA.flip(false, false);

        playerLeftA = new TextureRegion(sheetPlayerAxe, 0, 64*9, 64, 64);
        playerLeftA.flip(false, false);

        playerRightA = new TextureRegion(sheetPlayerAxe, 0, 64*11, 64, 64);
        playerRightA.flip(false, false);

        //Sheet PJ WAR
        playerDownW = new TextureRegion(sheetPlayerWar, 0, 64*10, 64, 64);
        playerDownW.flip(false, false);

        playerUpW = new TextureRegion(sheetPlayerWar, 0, 64*8, 64, 64);
        playerUpW.flip(false, false);

        playerLeftW = new TextureRegion(sheetPlayerWar, 0, 64*9, 64, 64);
        playerLeftW.flip(false, false);

        playerRightW = new TextureRegion(sheetPlayerWar, 0, 64*11, 64, 64);
        playerRightW.flip(false, false);

        //Sheet PJ SHIELD
        playerDownS = new TextureRegion(sheetPlayerShield, 0, 64*10, 64, 64);
        playerDownS.flip(false, false);

        playerUpS = new TextureRegion(sheetPlayerShield, 0, 64*8, 64, 64);
        playerUpS.flip(false, false);

        playerLeftS = new TextureRegion(sheetPlayerShield, 0, 64*9, 64, 64);
        playerLeftS.flip(false, false);

        playerRightS = new TextureRegion(sheetPlayerShield, 0, 64*11, 64, 64);
        playerRightS.flip(false, false);

        //Sheet Zombie
        zombieDown = new TextureRegion(sheetZombie, 0, 64*10, 64, 64);
        zombieDown.flip(false, false);

        zombieUp = new TextureRegion(sheetZombie, 0, 64*8, 64, 64);
        zombieUp.flip(false, false);

        zombieLeft = new TextureRegion(sheetZombie, 0, 64*9, 64, 64);
        zombieLeft.flip(false, false);

        zombieRight = new TextureRegion(sheetZombie, 0, 64*11, 64, 64);
        zombieRight.flip(false, false);

        //Sheet Zombie spawn
        zombieSpawn = new TextureRegion(sheetZombie, 0, 64*20, 64, 64);
        zombieSpawn.flip(false, false);


        //Animaciones en Arrays[]
        //Derecha Axe
        playerRightA_Animation = new TextureRegion[9];
        for (int i = 0; i < playerRightA_Animation.length; i++) {
            playerRightA_Animation[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 11, 64, 64);
        }

        //Izquierda Axe
        playerLeftA_Animation = new TextureRegion[9];
        for (int i = 0; i < playerLeftA_Animation.length; i++) {
            playerLeftA_Animation[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 9, 64, 64);
        }

        //Arriba Axe
        playerUpA_Animation = new TextureRegion[9];
        for (int i = 0; i < playerUpA_Animation.length; i++) {
            playerUpA_Animation[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 8, 64, 64);
        }

        //Abajo Axe
        playerDownA_Animation = new TextureRegion[9];
        for (int i = 0; i < playerDownA_Animation.length; i++) {
            playerDownA_Animation[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 10, 64, 64);
        }









        //Derecha Axe Ataque
        playerRightA_Attack = new TextureRegion[6];
        for (int i = 0; i < playerRightA_Attack.length; i++) {
            playerRightA_Attack[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 15, 64, 64);
        }

        //Izquierda Axe Ataque
        playerLeftA_Attack = new TextureRegion[6];
        for (int i = 0; i < playerLeftA_Attack.length; i++) {
            playerLeftA_Attack[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 13, 64, 64);
        }

        //Arriba Axe Ataque
        playerUpA_Attack = new TextureRegion[6];
        for (int i = 0; i < playerUpA_Attack.length; i++) {
            playerUpA_Attack[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 12, 64, 64);
        }

        //Abajo Axe Ataque
        playerDownA_Attack = new TextureRegion[6];
        for (int i = 0; i < playerDownA_Attack.length; i++) {
            playerDownA_Attack[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 14, 64, 64);
        }










        //Derecha Warhammer
        playerRightW_Animation = new TextureRegion[9];
        for (int i = 0; i < playerRightW_Animation.length; i++) {
            playerRightW_Animation[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 11, 64, 64);
        }

        //Derecha Shield
        playerRightS_Animation = new TextureRegion[9];
        for (int i = 0; i < playerRightS_Animation.length; i++) {
            playerRightS_Animation[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 11, 64, 64);
        }

        //Derecha Zombie
        zombieRight_Animation = new TextureRegion[9];
        for (int i = 0; i < zombieRight_Animation.length; i++) {
            zombieRight_Animation[i] = new TextureRegion(sheetZombie, i * 64, 64 * 11, 64, 64);
        }

        //Izquierda Zombie
        zombieLeft_Animation = new TextureRegion[9];
        for (int i = 0; i < zombieLeft_Animation.length; i++) {
            zombieLeft_Animation[i] = new TextureRegion(sheetZombie, i * 64, 64 * 9, 64, 64);
        }

        //Arriba Zombie
        zombieUp_Animation = new TextureRegion[9];
        for (int i = 0; i < zombieUp_Animation.length; i++) {
            zombieUp_Animation[i] = new TextureRegion(sheetZombie, i * 64, 64 * 8, 64, 64);
        }

        //Abajo Zombie
        zombieDown_Animation = new TextureRegion[9];
        for (int i = 0; i < zombieDown_Animation.length; i++) {
            zombieDown_Animation[i] = new TextureRegion(sheetZombie, i * 64, 64 * 10, 64, 64);
        }

        //Spawn Zombie
        zombieSpawn_Animation = new TextureRegion[5];
        for (int i = zombieSpawn_Animation.length - 1; i >= 0 ; i--) {
            zombieSpawn_Animation[(zombieSpawn_Animation.length - 1) - i] = new TextureRegion(sheetZombie, i * 64, 64 * 20, 64, 64);
        }

        //Imatge fons menu
        imgMainMenu = new Texture(Gdx.files.internal("mainScreen.png"));

        //Skins Buttons
        imgPlayBtn = new Texture(Gdx.files.internal("button_jugar.png"));
        imgSingleBtn = new Texture(Gdx.files.internal("button_single.png"));
        imgMultiBtn = new Texture(Gdx.files.internal("button_multi.png"));
        imgReturnBtn = new Texture(Gdx.files.internal("returnArrow.png"));
        btnMovUp = new Texture(Gdx.files.internal("up_arr.png"));
        btnMovDown = new Texture(Gdx.files.internal("down_arr.png"));
        btnMovLeft = new Texture(Gdx.files.internal("left_arr.png"));
        btnMovRight = new Texture(Gdx.files.internal("right_arr.png"));
    }

    public static void dispose() {
        sheetPlayerAxe.dispose();
        sheetPlayerWar.dispose();
        sheetPlayerShield.dispose();
        imgMainMenu.dispose();
        imgMultiBtn.dispose();
        imgSingleBtn.dispose();
        imgMultiBtn.dispose();
        imgReturnBtn.dispose();
        btnMovUp.dispose();
        btnMovDown.dispose();
        btnMovLeft.dispose();
        btnMovRight.dispose();
    }
}