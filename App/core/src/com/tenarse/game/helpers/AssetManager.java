package com.tenarse.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tenarse.game.utils.Settings;

public class AssetManager {

    //Textures
    public static Texture sheetPlayer, sheetMap;

    //Images
    public static TextureRegion playerUp, playerDown, playerLeft, playerRight, background;

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
        playerDown.flip(true, false);

        background = new TextureRegion(sheetMap, 0, 0, Settings.GAME_WIDTH, Settings.GAME_HEIGHT);

    }

    public static void dispose() {
        sheetPlayer.dispose();
    }

}
