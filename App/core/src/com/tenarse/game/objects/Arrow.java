package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class Arrow extends Actor {
    private int direction;
    private Vector2 position;
    private Vector2 spawnPosition;
    private TextureRegion textureArrow;
    private Rectangle arrowColison;

    public Arrow(int direction, Vector2 position) {
        this.direction = direction;
        this.position = new Vector2(position.x, position.y - 4);
        arrowColison = new Rectangle(position.x, position.y, 64, 64);
        this.spawnPosition = new Vector2(position.x, position.y  - 4);
        switch (direction){
            case Settings.PRESSED_UP:
                textureArrow = AssetManager.arrowUp;
                break;
            case Settings.PRESSED_LEFT:
                textureArrow = AssetManager.arrowLeft;
                break;
            case Settings.PRESSED_DOWN:
                textureArrow = AssetManager.arrowDown;
                break;
            case Settings.PRESSED_RIGHT:
                textureArrow = AssetManager.arrowRight;
                break;
        }
    }

   @Override
    public void act(float delta) {
       switch (direction){
           case Settings.PRESSED_UP:
               this.position.y += Settings.ARROW_VELOCITY * Gdx.graphics.getDeltaTime();
               break;
           case Settings.PRESSED_LEFT:
               this.position.x -= Settings.ARROW_VELOCITY * Gdx.graphics.getDeltaTime();
               break;
           case Settings.PRESSED_DOWN:
               this.position.y -= Settings.ARROW_VELOCITY * Gdx.graphics.getDeltaTime();
               break;
           case Settings.PRESSED_RIGHT:
               this.position.x += Settings.ARROW_VELOCITY * Gdx.graphics.getDeltaTime();
               break;
       }
    }

    public void setZombie(Zombie zombie) {
        if(arrowColison.overlaps(zombie.getCollisionRectZombie())) {
            zombie.setDamage(Settings.PLAYER_FUERZA);
            zombie.die(this.direction);
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(textureArrow, this.position.x, this.position.y, 32, 32);
    }
}
