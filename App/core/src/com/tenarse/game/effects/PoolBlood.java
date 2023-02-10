package com.tenarse.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
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

    private float stateTime = 0;

    private int currentFrame = 0;
    private float frameTime = 0.1f;

    private TextureRegion[] blood_animation = AssetManager.poolBloodAnimationL2;

    public PoolBlood(Vector2 position) {
        this.position = position;
        this.height = getRandomIntInclusive();
        this.width = getRandomIntInclusive();
        timeSpawned = TimeUtils.nanoTime();
        intervalAnimation = TimeUtils.nanoTime();
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

    private int getRandomIntInclusive() {
        int min = (int) Math.ceil(32);
        int max = (int) Math.floor(64);
        return (int) Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(blood_animation[currentFrame], this.position.x, this.position.y, width, height);
        System.out.println(currentFrame);
    }
}