package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

import java.util.Vector;

public class Jugador extends Actor {

    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRectPlayer;

    public Jugador(float x, float y, int width, int height){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);

        collisionRectPlayer = new Rectangle();

        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

    public void act(float delta){
        super.act(delta);

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

        collisionRectPlayer.x = this.position.x;
        collisionRectPlayer.y = this.position.y;
        collisionRectPlayer.width = this.width;
        collisionRectPlayer.height = this.height;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.playerDown, this.position.x, this.position.y, width, height);
    }


    public Rectangle getCollisionRectPlayer() {
        return collisionRectPlayer;
    }

}
