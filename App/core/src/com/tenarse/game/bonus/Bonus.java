package com.tenarse.game.bonus;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;


public class Bonus extends Actor {
    private Vector2 position;
    private int gemType;

    private float stateTime = 0;

    private int currentFrame = 0;
    private float frameTime = 0.1f;

    private TextureRegion gemTexture;

    public Bonus(Vector2 position) {
        this.position = position;
        float random = MathUtils.random();
        if(random >= 0 && random < Settings.B_LIVE_POSIBILITY){
            gemTexture = AssetManager.bonusLive;
        }else if (random > Settings.B_LIVE_POSIBILITY && random <= Settings.B_VELOCITY_POSIBILITY) {
            gemTexture = AssetManager.bonusVelocity;
        } else if (random > Settings.B_VELOCITY_POSIBILITY && random <= (1 - Settings.B_DAMAGE_POSIBILITY)) {
            gemTexture = AssetManager.bonusDamage;
        } else if (random > (1 - Settings.B_DAMAGE_POSIBILITY) && random < 1) {
            gemTexture = AssetManager.bonusShield;
        }
        System.out.println(random);
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

