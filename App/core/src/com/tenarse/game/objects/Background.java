package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class Background extends Actor {

    private int width, height;
    private TextureRegion texture;

    public Background(int width, int height){
        this.width = width;
        this.height = height;

        texture = new TextureRegion(AssetManager.background);
        setBounds(0, 0, width, height);
    }

    @Override
    public void act(float delta) {
        // actualizar posición y tamaño si es necesario
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

}
