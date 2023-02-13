package com.tenarse.game.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tenarse.game.helpers.AssetManager;

public class HitEffect extends Actor {
    private Vector2 position;

    private float stateTime = 0;

    private int currentFrame = 0;
    private float frameTime = 0.1f;

    private TextureRegion[] hit_Animation= AssetManager.hit_Animation;

    public HitEffect(Vector2 position) {
        this.position = position;
    }

    @Override
    public void act(float delta) {
        this.setZIndex(62);
        stateTime += delta;
        if (stateTime >= frameTime){
            currentFrame++;
            if (currentFrame >= hit_Animation.length){
                remove();
            }
            stateTime = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(hit_Animation[currentFrame], this.position.x, this.position.y, 32, 32);
    }
}
