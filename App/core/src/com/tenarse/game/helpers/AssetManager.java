package com.tenarse.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tenarse.game.utils.Settings;

public class AssetManager {

    //Tiled Map
    public static TiledMap map;
    public static TmxMapLoader mapLoader;

    //Textures
    public static Texture sheetPlayerAxe;
    public static Texture sheetPlayerWar;
    public static Texture sheetPlayerShield;
    public static Texture imgMainMenu;

    //Images
    public static TextureRegion playerUpA, playerDownA, playerLeftA, playerRightA;
    public static TextureRegion playerUpW, playerDownW, playerLeftW, playerRightW;
    public static TextureRegion playerUpS, playerDownS, playerLeftS, playerRightS;

    public static TextureRegion[] playerRightA_Animation, playerLeftA_Animation, playerUpA_Animation, playerDownA_Animation;
    public static TextureRegion[] playerRightW_Animation, playerLeftW_Animation, playerUpW_Animation, playerDownW_Animation;
    public static TextureRegion[] playerRightS_Animation, playerLeftS_Animation, playerUpS_Animation, playerDownS_Animation;

    //Skins Buttons
    public static Texture imgPlayBtn;

    //Sounds


    //Font


    public static void load(){
        //Tiled map load
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("colisionableMap.tmx");


        //Jugador AXE
        sheetPlayerAxe = new Texture(Gdx.files.internal("animate_axe_0.png"));
        sheetPlayerAxe.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Jugador Warhammer
        sheetPlayerWar = new Texture(Gdx.files.internal("animate_warhammer.png"));
        sheetPlayerWar.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Jugador Shield
        sheetPlayerShield = new Texture(Gdx.files.internal("animate_kite_shield.png"));
        sheetPlayerShield.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

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


        //Animaciones en Arrays[]
        //Derecha Axe
        playerRightA_Animation = new TextureRegion[9];
        for (int i = 0; i < playerRightA_Animation.length; i++) {
            playerRightA_Animation[i] = new TextureRegion(sheetPlayerAxe, i * 64, 64 * 11, 64, 64);
        }

        //Izq Axe
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

        //Imatge fons menu
        imgMainMenu = new Texture(Gdx.files.internal("mainScreen.png"));

        //Skins Buttons
        imgPlayBtn = new Texture(Gdx.files.internal("button_jugar.png"));
    }

    public static void dispose() {
        sheetPlayerAxe.dispose();
        sheetPlayerWar.dispose();
        sheetPlayerShield.dispose();
    }

}
