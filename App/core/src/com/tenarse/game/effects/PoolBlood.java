package com.tenarse.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class PoolBlood extends Actor {
    private Vector2 position;

    private float width;

    private float height;

    private TextureRegion blood_img = AssetManager.poolBloodImg;

    public PoolBlood(Vector2 position) {
        this.position = position;
        this.height = getRandomIntInclusive();
        this.width = getRandomIntInclusive();
    }

    @Override
    public void act(float delta) {
    }

    private int getRandomIntInclusive() {
        int min = (int) Math.ceil(28);
        int max = (int) Math.floor(32);
        return (int) Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(blood_img, this.position.x, this.position.y, width, height);
    }
}