package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

import java.util.Vector;

public class Jugador extends Actor {

    private final int AXE_PLAYER = 1;
    private final int WAR_PLAYER = 2;
    private final int SHI_PLAYER = 3;

    private Vector2 position;
    private int width, height;
    private int direction;
    private boolean isBot;
    private int tipusJugador;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;
    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private Rectangle collisionRectPlayer;

    public Jugador(float x, float y, int width, int height, boolean isBot, int tipusJugador){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

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
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
                this.position.x -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
                this.position.x += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
                this.position.y += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
                this.position.y -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }

            //Colision personaje con los bordes del mapa
            if (this.position.y <= 0 + 2){
                this.position.y = 5 + 2;
            }
            if (this.position.x <= 0){
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

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
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
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
            playerDir = animacionLeft[currentFrame];
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
            playerDir = animacionRight[currentFrame];
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
            playerDir = animacionUp[currentFrame];
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
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

}
