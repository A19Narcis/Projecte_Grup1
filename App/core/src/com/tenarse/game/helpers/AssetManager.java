package com.tenarse.game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tenarse.game.objects.ConnectionNode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

public class AssetManager {

    //Tiled Map
    public static TiledMap map1;
    public static TiledMap map2;
    public static TiledMap map3;
    public static TmxMapLoader mapLoader;
    public static Texture mapa_png1;
    public static Texture mapa_png2;
    public static Texture mapa_png3;
    public static ArrayList<Texture> mapasPNG = new ArrayList<>();

    //Textures
    public static Texture sheetArrowL;
    public static Texture sheetArrowR;
    public static Texture sheetArrowU;
    public static Texture sheetArrowD;

    public static Texture imgSoundOn;
    public static Texture imgSoundOff;

    public static Texture sheetBonusLive;
    public static Texture sheetBonusDamage;
    public static Texture sheetBonusVelocity;
    public static Texture sheetBonusShield;
    public static Texture sheetBonusPoints;

    public static Texture sheetHit;
    public static Texture imgMainMenu;
    public static Texture imgChooseBox;
    public static Texture imgLeft_char;
    public static Texture imgRight_char;
    public static Texture sheetPoolBloodL1;
    public static Texture sheetPoolBloodL2;

    public static Texture sheetPoolBloodR1;
    public static Texture sheetPoolBloodR2;

    //Images
    public static TextureRegion arrowLeft;
    public static TextureRegion arrowUp;
    public static TextureRegion arrowRight;
    public static TextureRegion arrowDown;

    public static TextureRegion bonusLive;
    public static TextureRegion bonusDamage;
    public static TextureRegion bonusVelocity;
    public static TextureRegion bonusShield;
    public static TextureRegion bonusPoints;

    public static Texture imgSplash;

    public static TextureRegion[] playerRightA_Animation, playerLeftA_Animation, playerUpA_Animation, playerDownA_Animation, playerRightA_Attack, playerLeftA_Attack, playerUpA_Attack, playerDownA_Attack;
    public static TextureRegion[] playerRightW_Animation, playerLeftW_Animation, playerUpW_Animation, playerDownW_Animation;
    public static TextureRegion[] playerRightS_Animation, playerLeftS_Animation, playerUpS_Animation, playerDownS_Animation;

    public static TextureRegion[] playerRightW_Atack, playerLeftW_Atack, playerUpW_Atack, playerDownW_Atack;
    public static TextureRegion[] playerRightS_Atack, playerLeftS_Atack, playerUpS_Atack, playerDownS_Atack;


    public static TextureRegion[] hit_Animation;
    public static TextureRegion[] poolBloodAnimationL1;
    public static TextureRegion[] poolBloodAnimationL2;
    public static TextureRegion[] poolBloodAnimationR1;
    public static TextureRegion[] poolBloodAnimationR2;

    public static ArrayList<AMSprites> SpritesPlayers = new ArrayList<>();
    public static ArrayList<SpawnInterval> ZombiesInterval = new ArrayList<>();

    //Skins Buttons
    public static Texture imgPlayBtn, imgSingleBtn, imgMultiBtn, imgReturnBtn, imgPickMapBtn;
    public static Texture btnMovUp, btnMovDown, btnMovLeft, btnMovRight;
    public static Texture btnAtacar;
    public static Texture hp_player;
    public static Texture armor_player;


    //Sounds
    public static Music menuMusic;

    public static Music mapa1Music;
    public static Music mapa2Music;
    public static Music mapa3Music;

    public static Sound arrowSound;
    public static Sound shieldSound;
    public static Sound warSound;

    public static Sound zombiePunch;
    public static Sound dieSound;
    public static Sound zombieSound;
    public static Sound gameOver;

    //Font
    public static FreeTypeFontGenerator fontTextBold;
    public static FreeTypeFontGenerator fontTextNormal;

    //Cajas de texto
    public static Skin skinTextBox;

    //Conexion Nodejs
    public static JSONArray fullStats;

    public static void load(){

        //NodeJS Connection
        ConnectionNode nodeJs = new ConnectionNode();
        synchronized (nodeJs.lock) {
            try {
                nodeJs.getStats();
                nodeJs.lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fullStats = nodeJs.getStatsArray();

        for (int i = 0; i < fullStats.length(); i++) {

            JSONObject stats = fullStats.getJSONObject(i);
            URL url = null;
            try {
                 url = new URL(stats.getString("url"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            //Descargar IMGs
            Pixmap pixmap = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                byte[] bytes = IOUtils.toByteArray(inputStream);
                pixmap = new Pixmap(bytes, 0, bytes.length);
            } catch (Exception e) {
                e.printStackTrace();
            }

            AMSprites amSprites = new AMSprites(stats.getString("nombreTipo") ,new Texture(pixmap));
            SpritesPlayers.add(amSprites);

            if(i >= 3){
                ZombiesInterval.add(new SpawnInterval(60 / (long) stats.getInt("cantidadMinuto") * 1000000000L));
            }

        }

        //Tiled map load
        mapLoader = new TmxMapLoader();
        map1 = mapLoader.load("Mapas/developmentMap/developmentMap.tmx");

        mapLoader = new TmxMapLoader();
        map2 = mapLoader.load("Mapas/developmentMap2/developmentMap2.tmx");

        mapLoader = new TmxMapLoader();
        map3 = mapLoader.load("Mapas/developmentMap3/developmentMap3.tmx");






        //Flecha
        sheetArrowL = new Texture(Gdx.files.internal("ArrowLeft.png"));
        sheetArrowL.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetArrowU = new Texture(Gdx.files.internal("ArrowUp.png"));
        sheetArrowU.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetArrowR = new Texture(Gdx.files.internal("ArrowRight.png"));
        sheetArrowR.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetArrowD = new Texture(Gdx.files.internal("ArrowDown.png"));
        sheetArrowD.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Imagen sonidos ON / OFF
        imgSoundOn = new Texture(Gdx.files.internal("sonidoOn.png"));
        sheetArrowD.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        imgSoundOff = new Texture(Gdx.files.internal("sonidoOff.png"));
        sheetArrowD.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Hit Sangre
        sheetHit = new Texture(Gdx.files.internal("hit.png"));
        sheetHit.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //Charco Sangre
        sheetPoolBloodL1 = new Texture(Gdx.files.internal("bloodLeft1.png"));
        sheetPoolBloodL1.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetPoolBloodL2 = new Texture(Gdx.files.internal("bloodLeft2.png"));
        sheetPoolBloodL2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetPoolBloodR1 = new Texture(Gdx.files.internal("bloodRight1.png"));
        sheetPoolBloodR1.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetPoolBloodR2 = new Texture(Gdx.files.internal("bloodRight2.png"));
        sheetPoolBloodR2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        sheetBonusLive = new Texture(Gdx.files.internal("live.png"));
        sheetBonusLive.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetBonusDamage = new Texture(Gdx.files.internal("damage.png"));
        sheetBonusDamage.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetBonusVelocity = new Texture(Gdx.files.internal("velocity.png"));
        sheetBonusVelocity.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetBonusShield = new Texture(Gdx.files.internal("shield.png"));
        sheetBonusShield.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        sheetBonusPoints = new Texture(Gdx.files.internal("points.png"));
        sheetBonusPoints.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);



        arrowLeft = new TextureRegion(sheetArrowL, 0, 0, 64, 64);
        arrowUp = new TextureRegion(sheetArrowU, 0, 0, 64, 64);
        arrowDown = new TextureRegion(sheetArrowD, 0, 0, 64, 64);
        arrowRight = new TextureRegion(sheetArrowR, 0, 0, 64, 64);

        bonusLive = new TextureRegion(sheetBonusLive, 0, 0, 120, 114);
        bonusLive.flip(false, false);
        bonusDamage = new TextureRegion(sheetBonusDamage, 0, 0, 83, 123);
        bonusDamage.flip(false, false);
        bonusVelocity = new TextureRegion(sheetBonusVelocity, 0, 0, 83, 112);
        bonusVelocity.flip(false, false);
        bonusShield = new TextureRegion(sheetBonusShield, 0, 0, 107, 107);
        bonusShield.flip(false, false);
        bonusPoints = new TextureRegion(sheetBonusPoints, 0, 0, 83, 112);
        bonusPoints.flip(false, false);


        //Animacion golpe (sangre)
        hit_Animation = new TextureRegion[6];
        for (int i = 0; i < hit_Animation.length; i++) {
            hit_Animation[i] = new TextureRegion(sheetHit, i * 100, 100, 100, 100);
        }

        //Charco Sangre
        poolBloodAnimationL1 = new TextureRegion[8];
        poolBloodAnimationR1 = new TextureRegion[8];
        for (int j = 1; j >= 0; j--) {
            for (int i = (poolBloodAnimationL1.length / 2) - 1; i >= 0; i--) {
                poolBloodAnimationL1[(((poolBloodAnimationL1.length / (j + 1)) - 1) - i) + (j / 2)] = new TextureRegion(sheetPoolBloodL1, (i / (j + 1)) * 512, j * 512, 512, 512);
                poolBloodAnimationR1[(((poolBloodAnimationL1.length / (j + 1)) - 1) - i) + (j / 2)] = new TextureRegion(sheetPoolBloodR1, (i / (j + 1)) * 512, j * 512, 512, 512);
            }
        }

        poolBloodAnimationL2 = new TextureRegion[8];
        poolBloodAnimationR2 = new TextureRegion[8];
        for (int i = 0; i < poolBloodAnimationL2.length / 2; i++) {
            for (int j = 0; j < 2; j++) {
                poolBloodAnimationL2[i + (j * 4)] = new TextureRegion(sheetPoolBloodL2, i * 512, j * 512, 512, 512);
                poolBloodAnimationR2[i + (j * 4)] = new TextureRegion(sheetPoolBloodR2, i * 512, j * 512, 512, 512);
            }
        }

        //IMG SPLASH SCREEN
        imgSplash = new Texture(Gdx.files.internal("splashScreen.png"));
        
        //Imatge fons menu
        imgMainMenu = new Texture(Gdx.files.internal("mainScreen.png"));

        //Skins Buttons
        imgPlayBtn = new Texture(Gdx.files.internal("button_jugar.png"));
        imgSingleBtn = new Texture(Gdx.files.internal("button_single.png"));
        imgMultiBtn = new Texture(Gdx.files.internal("button_multi.png"));
        imgReturnBtn = new Texture(Gdx.files.internal("returnArrow.png"));
        imgPickMapBtn = new Texture(Gdx.files.internal("button_sel_mapa.png"));
        btnMovUp = new Texture(Gdx.files.internal("up_arr.png"));
        btnMovDown = new Texture(Gdx.files.internal("down_arr.png"));
        btnMovLeft = new Texture(Gdx.files.internal("left_arr.png"));
        btnMovRight = new Texture(Gdx.files.internal("right_arr.png"));
        btnAtacar = new Texture(Gdx.files.internal("attack.png"));
        hp_player = new Texture(Gdx.files.internal("heart.png"));
        armor_player = new Texture(Gdx.files.internal("armor.png"));
        imgChooseBox = new Texture(Gdx.files.internal("menuPicker.png"));
        imgLeft_char = new Texture(Gdx.files.internal("Icon_Left.png"));
        imgRight_char = new Texture(Gdx.files.internal("Icon_Right.png"));

        //Fonts
        fontTextBold = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/boldType.ttf"));
        fontTextNormal = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/normalType.ttf"));

        //Skin
        skinTextBox = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //Mapas PNG para elegir
        mapa_png1 = new Texture(Gdx.files.internal("Mapas/developmentMap/developmentMap.png"));
        mapa_png2 = new Texture(Gdx.files.internal("Mapas/developmentMap2/developmentMap2.png"));
        mapa_png3 = new Texture(Gdx.files.internal("Mapas/developmentMap3/developmentMap3.png"));

        mapasPNG.add(mapa_png1);
        mapasPNG.add(mapa_png2);
        mapasPNG.add(mapa_png3);

        //Sonidos
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/menuMusic.mp3"));
        menuMusic.setLooping(true);

        mapa1Music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/mapa1Music.mp3"));
        mapa1Music.setLooping(true);

        mapa2Music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/mapa2Music.mp3"));
        mapa2Music.setLooping(true);

        mapa3Music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/mapa3Music.mp3"));
        mapa3Music.setLooping(true);

        warSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/warSound.mp3"));
        arrowSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/arrowSound.mp3"));
        shieldSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/shieldSound.mp3"));

        zombiePunch = Gdx.audio.newSound(Gdx.files.internal("Sounds/zombiePunch.mp3"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/dieSound.mp3"));
        zombieSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/zombieSound.mp3"));
        gameOver = Gdx.audio.newSound(Gdx.files.internal("Sounds/dies.mp3"));

    }

    public static void dispose() {
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