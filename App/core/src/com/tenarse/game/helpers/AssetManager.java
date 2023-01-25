package com.tenarse.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tenarse.game.utils.Settings;

public class AssetManager {

    //Textures
    public static Texture sheetMap;
    public static Texture sheetPlayer;
    public static Texture imgMainMenu;

    //Images
    public static TextureRegion playerUp;
    public static TextureRegion playerDown;
    public static TextureRegion playerLeft;
    public static TextureRegion playerRight;
    public static TextureRegion background;

    //Skins Buttons
    public static Texture imgPlayBtn;

    //Sounds


    //Font


    public static void load(){
        //Textures
        sheetPlayer = new Texture(Gdx.files.internal("sheetPlayer.png"));
        sheetPlayer.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        sheetMap = new Texture(Gdx.files.internal("sheetMap.png"));
        sheetMap.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Sheet PJ
        playerDown = new TextureRegion(sheetPlayer, 0, 0, 16, 16);
        playerDown.flip(false, false);

        playerUp = new TextureRegion(sheetPlayer, 16, 0, 16, 16);
        playerUp.flip(false, false);

        playerLeft = new TextureRegion(sheetPlayer, 32, 0, 16, 16);
        playerLeft.flip(true, false);

        playerRight = new TextureRegion(sheetPlayer, 32, 0, 16, 16);
        playerRight.flip(false, false);

        //Imatge fons menu
        imgMainMenu = new Texture(Gdx.files.internal("mainScreen.png"));

        //Mapa
        background = new TextureRegion(sheetMap, 0, 0, 3840, 2160);

        //Skins Buttons
        imgPlayBtn = new Texture(Gdx.files.internal("button_jugar.png"));
    }

    public static void dispose() {
        sheetPlayer.dispose();
        sheetMap.dispose();
    }

}
