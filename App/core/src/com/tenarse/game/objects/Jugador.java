package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.screens.GameScreen;
import com.tenarse.game.utils.Settings;

public class Jugador extends Actor {

    private final int AXE_PLAYER = 1;
    private final int WAR_PLAYER = 2;
    private final int SHI_PLAYER = 3;

    private Vector2 position;
    private int width, height;
    private boolean isBot;
    private int tipusJugador;

    private Boolean bntUpIsPressed = false;
    private Boolean bntDownIsPressed = false;
    private Boolean bntLeftIsPressed = false;
    private Boolean bntRightIsPressed = false;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;
    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private float oldx;
    private float oldy;

    private Map map;

    private Rectangle collisionRectPlayer;

    public Jugador(float x, float y, int width, int height, boolean isBot, int tipusJugador){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        this.map = map;

        this.isBot = isBot;

        collisionRectPlayer = new Rectangle();

        this.tipusJugador = tipusJugador;

        if (tipusJugador == AXE_PLAYER){
            animacionRight = AssetManager.playerRightA_Animation;
            animacionLeft = AssetManager.playerLeftA_Animation;
            animacionUp = AssetManager.playerUpA_Animation;
            animacionDown = AssetManager.playerDownA_Animation;
        } else if (tipusJugador == WAR_PLAYER){
            animacionRight = AssetManager.playerRightW_Animation;
            animacionLeft = AssetManager.playerLeftW_Animation;
            animacionUp = AssetManager.playerUpW_Animation;
            animacionDown = AssetManager.playerDownW_Animation;
        } else if (tipusJugador == SHI_PLAYER){
            animacionRight = AssetManager.playerRightS_Animation;
            animacionLeft = AssetManager.playerLeftS_Animation;
            animacionUp = AssetManager.playerUpS_Animation;
            animacionDown = AssetManager.playerDownS_Animation;
        }

        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

    public Jugador(float x, float y, int width, int height, boolean isBot, int tipusJugador, Map map){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        this.map = map;

        this.isBot = isBot;

        collisionRectPlayer = new Rectangle();

        this.tipusJugador = tipusJugador;

        if (tipusJugador == AXE_PLAYER){
            animacionRight = AssetManager.playerRightA_Animation;
            animacionLeft = AssetManager.playerLeftA_Animation;
            animacionUp = AssetManager.playerUpA_Animation;
            animacionDown = AssetManager.playerDownA_Animation;
        } else if (tipusJugador == WAR_PLAYER){
            animacionRight = AssetManager.playerRightW_Animation;
            animacionLeft = AssetManager.playerLeftW_Animation;
            animacionUp = AssetManager.playerUpW_Animation;
            animacionDown = AssetManager.playerDownW_Animation;
        } else if (tipusJugador == SHI_PLAYER){
            animacionRight = AssetManager.playerRightS_Animation;
            animacionLeft = AssetManager.playerLeftS_Animation;
            animacionUp = AssetManager.playerUpS_Animation;
            animacionDown = AssetManager.playerDownS_Animation;
        }

        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

        public void act(float delta){
        super.act(delta);
        if (this.isBot){
            this.position.x += 5;
            if (this.position.x >= Gdx.graphics.getWidth()){
                this.position.x = -400;
            }
        } else {
            oldx = this.position.x;
            oldy = this.position.y;
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || this.bntLeftIsPressed) {
                    this.position.x -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    this.position.x -= 8;
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }else {
                        this.position.x += 8;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || this.bntRightIsPressed) {
                    this.position.x += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    this.position.x += 8;
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }else {
                        this.position.x -= 8;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || this.bntUpIsPressed) {
                    this.position.y += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed) {
                    this.position.y -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    this.position.y -= 12;
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }else {
                        this.position.y += 12;
                    }
                }



            //Colision personaje con los bordes del mapa
            if (this.position.y <= 5){
                this.position.y = 5;
            }
            if (this.position.x <= 5){
                this.position.x = 5;
            }
            if (this.position.x >= 3840 - this.width - 5){
                this.position.x = 3840 - this.width - 5;
            }
            if (this.position.y >= 2160 - this.height - 2){
                this.position.y = 2160 - this.height- 2;
            }

            collisionRectPlayer.x = this.position.x;
            collisionRectPlayer.y = this.position.y;
            collisionRectPlayer.width = this.width;
            collisionRectPlayer.height = this.height;


        }
        stateTime += delta;
        if (stateTime >= frameTime){
            currentFrame++;
            if (currentFrame >= animacionRight.length){
                currentFrame = 0;
            }
            stateTime = 0;
        }
    }

    public void draw(Batch batch, float parentAlpha){
        batch.draw(getPLayerDirection(), this.position.x, this.position.y, width, height);
    }

    private TextureRegion getPLayerDirection() {
        TextureRegion playerDir = null;
        //Posicio per si no es mou
        if (this.tipusJugador == AXE_PLAYER){

            playerDir = AssetManager.playerDownA;
        } else if (this.tipusJugador == WAR_PLAYER){
            playerDir = AssetManager.playerDownW;
        } else if (this.tipusJugador == SHI_PLAYER){
            playerDir = AssetManager.playerDownS;
        }

        //ANIMACIONES POR DIRECCIONES
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || this.bntLeftIsPressed){
            playerDir = animacionLeft[currentFrame];
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || this.bntRightIsPressed){
            playerDir = animacionRight[currentFrame];
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || this.bntUpIsPressed){
            playerDir = animacionUp[currentFrame];
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed){
            playerDir = animacionDown[currentFrame];
        }

        if (this.isBot){
            playerDir = animacionRight[currentFrame];
        }

        return playerDir;
    }

    public void desplazarAutomaticamente(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }


    public Rectangle getCollisionRectPlayer() {
        return collisionRectPlayer;
    }

    public void goingUp() {
        this.bntUpIsPressed = true;
    }

    public void goingDown() {
        this.bntDownIsPressed = true;
    }

    public void goingLeft() {
        this.bntLeftIsPressed = true;
    }

    public void goingRight() {
        this.bntRightIsPressed = true;
    }

    public void stop() {
        this.bntUpIsPressed = false;
        this.bntLeftIsPressed = false;
        this.bntDownIsPressed = false;
        this.bntRightIsPressed = false;
    }
}
