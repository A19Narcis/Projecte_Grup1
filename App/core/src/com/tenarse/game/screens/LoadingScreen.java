package com.tenarse.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;

import java.util.concurrent.CountDownLatch;

public class LoadingScreen implements Screen {
    private Tenarse game;
    private Texture loadingTexture;
    private SpriteBatch batch;
    private float progress;

    public LoadingScreen(Tenarse game) {
        this.game = game;
    }

    @Override
    public void show() {
        loadingTexture = new Texture("shield.png");
        batch = new SpriteBatch();
        progress = 0f;

        // Inicia el httprequest en un hilo separado
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Realiza el httprequest y carga los datos
                // ...
                    Tenarse.assetManager.load();

                // Cambia a la pantalla principal del juego
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new MainMenuScreen(game));
                    }
                });
            }
        }).start();
    }

    @Override
    public void render(float delta) {
       // progress = MathUtils.lerp(progress, Tenarse.assets.getProgress(), 0.1f);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //batch.draw(loadingTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //batch.draw(loadingBarTexture, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 25, progress * 200, 50);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    // Implementa los demás métodos de la interfaz Screen
    // ...
}