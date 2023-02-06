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

    public static TextureRegion[] playerRightA_Atack, playerLeftA_Atack, playerUpA_Atack, playerDownA_Atack;
    public static TextureRegion[] playerRightW_Atack, playerLeftW_Atack, playerUpW_Atack, playerDownW_Atack;
    public static TextureRegion[] playerRightS_Atack, playerLeftS_Atack, playerUpS_Atack, playerDownS_Atack;

    public static TextureRegion[] zombieRight_Animation, zombieLeft_Animation, zombieUp_Animation, zombieDown_Animation, zombieSpawn_Animation;

    //Skins Buttons
    public static Texture imgPlayBtn, imgSingleBtn, imgMultiBtn, imgReturnBtn;
    public static Texture btnMovUp, btnMovDown, btnMovLeft, btnMovRight;
    public static Texture btnAtacar;
    public static Texture hp_player;


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


        //Derecha War
        playerRightW_Animation = new TextureRegion[9];
        for (int i = 0; i < playerRightW_Animation.length; i++) {
            playerRightW_Animation[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 11, 64, 64);
        }

        //Izquierda War
        playerLeftW_Animation = new TextureRegion[9];
        for (int i = 0; i < playerLeftW_Animation.length; i++) {
            playerLeftW_Animation[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 9, 64, 64);
        }

        //Arriba War
        playerUpW_Animation = new TextureRegion[9];
        for (int i = 0; i < playerUpW_Animation.length; i++) {
            playerUpW_Animation[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 8, 64, 64);
        }

        //Abajo War
        playerDownW_Animation = new TextureRegion[9];
        for (int i = 0; i < playerDownW_Animation.length; i++) {
            playerDownW_Animation[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 10, 64, 64);
        }


        //Derecha Shield
        playerRightS_Animation = new TextureRegion[9];
        for (int i = 0; i < playerRightS_Animation.length; i++) {
            playerRightS_Animation[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 11, 64, 64);
        }

        //Izquierda Shield
        playerLeftS_Animation = new TextureRegion[9];
        for (int i = 0; i < playerLeftS_Animation.length; i++) {
            playerLeftS_Animation[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 9, 64, 64);
        }

        //Arriba Shield
        playerUpS_Animation = new TextureRegion[9];
        for (int i = 0; i < playerUpS_Animation.length; i++) {
            playerUpS_Animation[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 8, 64, 64);
        }

        //Abajo Shield
        playerDownS_Animation = new TextureRegion[9];
        for (int i = 0; i < playerDownS_Animation.length; i++) {
            playerDownS_Animation[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 10, 64, 64);
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




        //Derecha Warhammer Ataque
        playerRightW_Atack = new TextureRegion[6];
        for (int i = 0; i < playerRightW_Atack.length; i++) {
            playerRightW_Atack[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 15, 64, 64);
        }

        //Izquierda Warhammer Ataque
        playerLeftW_Atack = new TextureRegion[6];
        for (int i = 0; i < playerLeftW_Atack.length; i++) {
            playerLeftW_Atack[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 13, 64, 64);
        }

        //Arriba Warhammer Ataque
        playerUpW_Atack = new TextureRegion[6];
        for (int i = 0; i < playerUpW_Atack.length; i++) {
            playerUpW_Atack[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 12, 64, 64);
        }

        //Abajo Warhammer Ataque
        playerDownW_Atack = new TextureRegion[6];
        for (int i = 0; i < playerDownW_Atack.length; i++) {
            playerDownW_Atack[i] = new TextureRegion(sheetPlayerWar, i * 64, 64 * 14, 64, 64);
        }



        //Derecha Shield Ataque
        playerRightS_Atack = new TextureRegion[6];
        for (int i = 0; i < playerRightS_Atack.length; i++) {
            playerRightS_Atack[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 15, 64, 64);
        }

        //Izquierda Shield Ataque
        playerLeftS_Atack = new TextureRegion[6];
        for (int i = 0; i < playerLeftS_Atack.length; i++) {
            playerLeftS_Atack[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 13, 64, 64);
        }

        //Arriba Shield Ataque
        playerUpS_Atack = new TextureRegion[6];
        for (int i = 0; i < playerUpS_Atack.length; i++) {
            playerUpS_Atack[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 12, 64, 64);
        }

        //Abajo Shield Ataque
        playerDownS_Atack = new TextureRegion[6];
        for (int i = 0; i < playerDownS_Atack.length; i++) {
            playerDownS_Atack[i] = new TextureRegion(sheetPlayerShield, i * 64, 64 * 14, 64, 64);
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
        btnAtacar = new Texture(Gdx.files.internal("attack.png"));
        hp_player = new Texture(Gdx.files.internal("heart.png"));
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
        btnAtacar.dispose();
        hp_player.dispose();
    }
}