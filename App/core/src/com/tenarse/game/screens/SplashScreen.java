package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;

public class SplashScreen implements Screen {

    private Stage stage;
    private final Tenarse game;
    private Image imgSplash;

    public SplashScreen(Tenarse game){

        this.game = game;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        this.imgSplash = new Image(AssetManager.imgSplash);

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            this.imgSplash.setScale(3);
            this.imgSplash.setPosition(Gdx.graphics.getWidth() / 2 - imgSplash.getWidth() / 2 * 3, Gdx.graphics.getHeight() / 2 - imgSplash.getHeight() / 2 * 3.5f);
        } else {
            this.imgSplash.setScale(1.5f);
            this.imgSplash.setPosition(Gdx.graphics.getWidth() / 2 - imgSplash.getWidth() / 2 * 1.5f, Gdx.graphics.getHeight() / 2 - imgSplash.getHeight() / 2 * 1.5f);
        }

        stage.addActor(imgSplash);

    }

    @Override
    public void show() {
        imgSplash.addAction(Actions.sequence(Actions.alpha(0)
                ,Actions.fadeIn(4f), Actions.delay(3), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new MainMenuScreen(game));
                    }
                })));
    }

    @Override
    public void render(float delta) {
        // Limpiar la pantalla
        Gdx.gl.glClearColor(176/255f, 176/255f, 176/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar la imagen de la pantalla de inicio en el centro
        stage.draw();
        stage.act(delta);
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
        stage.dispose();
    }
}
