package com.tenarse.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.tools.classfile.Opcode;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class PoolBlood extends Actor {
    private Vector2 position;

    private float width;

    private float height;

    private long timeSpawned;

    private long intervalAnimation;

    private int aleatorySprite;

    private float stateTime = 0;

    private int currentFrame = 0;
    private float frameTime = 0.1f;

    int direction;

    private TextureRegion[] blood_animation = null;

    public PoolBlood(Vector2 position, int direction) {
        this.position = new Vector2(position.x - (Settings.PLAYER_WIDTH/ 2), position.y - (Settings.PLAYER_HEIGHT - 8));
        this.height = 64;
        this.width = 64;
        aleatorySprite = getRandomIntInclusive(0, 1);
        timeSpawned = TimeUtils.nanoTime();
        intervalAnimation = TimeUtils.nanoTime();
        this.direction = direction;
        if(direction == Settings.PRESSED_DOWN || direction == Settings.PRESSED_RIGHT) {
            if (aleatorySprite == 0) {
                blood_animation = AssetManager.poolBloodAnimationR1;
            } else {
                blood_animation = AssetManager.poolBloodAnimationR2;
            }
        }else {
            if (aleatorySprite == 0) {
                blood_animation = AssetManager.poolBloodAnimationL1;
            } else {
                blood_animation = AssetManager.poolBloodAnimationL2;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (TimeUtils.nanoTime() - timeSpawned > Settings.POOL_TIME_DESPAWN) {
            if(TimeUtils.nanoTime() - intervalAnimation > Settings.POOL_INTERVAL_ANIMATION)
            stateTime += delta;
            if (stateTime >= frameTime){
                currentFrame++;
                intervalAnimation = TimeUtils.nanoTime();
                if (currentFrame >= blood_animation.length){
                    remove();
                }
                stateTime = 0;
            }
        }

    }

    private int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        Sprite bloodSprite = new Sprite(blood_animation[currentFrame]);
        if(direction == Settings.PRESSED_DOWN || direction == Settings.PRESSED_UP){
            bloodSprite.setRotation(180f);
        }
        batch.draw(bloodSprite, this.position.x, this.position.y, width, height);
        this.toBack();
    }
}