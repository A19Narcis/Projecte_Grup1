package com.tenarse.game.bonus;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.utils.Settings;

import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Bonus extends Actor {
    private Vector2 position;
    private int gemType;

    private float stateTime = 0;

    private int currentFrame = 0;
    private float frameTime = 0.1f;

    private TextureRegion gemTexture;
    private Rectangle colisionRectangle;

    private boolean active = false;
    private long timeFromStart;
    private boolean delete;

    public Bonus(Vector2 position) {
        this.position = position;
        float random = MathUtils.random(); // entre 0 y 1 (decimales)
        if(random >= 0 && random <= Settings.B_LIVE_POSIBILITY){ //Entre 0 y 0.10
            gemTexture = AssetManager.bonusLive;
            gemType = Settings.BONUS_LIVE;
        }else if (random > Settings.B_LIVE_POSIBILITY && random <= Settings.B_DAMAGE_POSIBILITY) { //Entre 0.11 y 0.30
            gemTexture = AssetManager.bonusDamage;
            gemType = Settings.BONUS_DAMAGE;
        } else if (random > Settings.B_DAMAGE_POSIBILITY && random <= Settings.B_VELOCITY_POSIBILITY) { //Entre 0.31 y 0.60
            gemTexture = AssetManager.bonusVelocity;
            gemType = Settings.BONUS_VELOCITY;
        } else if (random > Settings.B_VELOCITY_POSIBILITY && random <= Settings.B_SHIELD_POSIBILITY) { //Entre 0.61 y
            gemTexture = AssetManager.bonusShield;
            gemType = Settings.BONUS_SHIELD;
        } else if (random > Settings.B_SHIELD_POSIBILITY && random <= Settings.B_POINTS_POSIBILITY) {
            gemTexture = AssetManager.bonusPoints;
            gemType = Settings.BONUS_POINTS;
        }
        this.position.x += (gemTexture.getRegionWidth() / 8);
        colisionRectangle = new Rectangle(position.x, position.y, gemTexture.getRegionWidth() / 8, gemTexture.getRegionHeight() / 8);
        timeFromStart = TimeUtils.nanoTime();
        delete = false;
    }



    @Override
    public void act(float delta) {
        if(TimeUtils.nanoTime() - timeFromStart >= Settings.BONUS_DELAY_ACTIVATION && !isActive()){
            active = true;
        }else if(TimeUtils.nanoTime() - timeFromStart >= Settings.BONUS_TIME_DESPAWN){
            remove();
            delete = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(gemTexture, this.position.x, this.position.y, gemTexture.getRegionWidth() / 8, gemTexture.getRegionHeight() / 8);
    }

    public Rectangle getColisionRectangle() {
        return colisionRectangle;
    }

    public boolean isActive() {
        return active;
    }

    public int getGemType() {
        return gemType;
    }

    public boolean isDelete() {
        return delete;
    }
}

