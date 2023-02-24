package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    private Jugador jugador;
    private int width;
    private int height;
    boolean firstAnimation = true;

    public Arrow(int direction, Vector2 position, Jugador jugador) {
        this.direction = direction;
        this.position = new Vector2(position.x, position.y - 4);
        this.spawnPosition = new Vector2(position.x, position.y  - 4);
        this.jugador = jugador;
        switch (direction){
            case Settings.PRESSED_UP:
                textureArrow = AssetManager.arrowUp;
                width = 16;
                height = 16;
                break;
            case Settings.PRESSED_LEFT:
                textureArrow = AssetManager.arrowLeft;
                width = 16;
                height = 16;
                break;
            case Settings.PRESSED_DOWN:
                textureArrow = AssetManager.arrowDown;
                width = 16;
                height = 16;
                break;
            case Settings.PRESSED_RIGHT:
                textureArrow = AssetManager.arrowRight;
                width = 16;
                height = 16;
                break;
        }
        arrowColison = new Rectangle(position.x, position.y, width, height);
    }

    public void move(float delta) {
           switch (direction) {
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
           arrowColison.x = position.x;
           arrowColison.y = position.y;
    }

    public boolean setZombie(Zombie zombie) {
        boolean removed = false;
        if(position.x - zombie.getPosition().x < width && position.x - zombie.getPosition().x > -width) {
            if(position.y - zombie.getPosition().y < height && position.y - zombie.getPosition().y > -height) {
                zombie.setDamage(jugador.getFuerza() + jugador.getBonusMultiplier()[Settings.BONUS_DAMAGE]);
                zombie.die(this.direction);
                jugador.removeArrow(this);
                remove();
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(textureArrow, this.position.x, this.position.y, 32, 32);
    }
}
