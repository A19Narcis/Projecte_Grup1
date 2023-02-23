package com.tenarse.game.bonus;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tenarse.game.helpers.AssetManager;


public class Bonus extends Actor {
    private Vector2 position;
    private int gemType;

    private float stateTime = 0;

    private int currentFrame = 0;
    private float frameTime = 0.1f;

    private TextureRegion gemTexture;

    public Bonus(Vector2 position) {
        this.position = position;
        gemType = getRandomIntInclusive(1, 4);
        switch (gemType){
            case(1):
                gemTexture = AssetManager.bonusLive;
                break;
            case(2):
                gemTexture = AssetManager.bonusDamage;
                break;
            case(3):
                gemTexture = AssetManager.bonusVelocity;
                break;
            case(4):
                gemTexture = AssetManager.bonusShield;
                break;
        }
    }

    private int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
    }

    @Override
    public void act(float delta) {
        this.setZIndex(62);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(gemTexture, this.position.x, this.position.y, gemTexture.getRegionWidth() / 8, gemTexture.getRegionHeight() / 8);
    }
}

