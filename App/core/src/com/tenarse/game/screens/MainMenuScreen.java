package com.tenarse.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Tenarse game;

    private Label title;

    private BitmapFont font;

    public MainMenuScreen(Tenarse game){
        this.game = game;

        //Camera del joc
        OrthographicCamera camera = new OrthographicCamera(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
        camera.setToOrtho(false);

        font = new BitmapFont();

        FitViewport viewport = new FitViewport(Settings.GAME_WIDTH, Settings.GAME_HEIGHT, camera);

        String textInici = "Bonvingu a Tenarse :)";
        title = new Label(textInici, new Label.LabelStyle(font, Color.WHITE));
        title.setPosition(Settings.GAME_WIDTH / 2 - title.getWidth() / 2, Settings.GAME_HEIGHT / 2);

        stage = new Stage(viewport);

        stage.addActor(new Image(AssetManager.background));
        stage.addActor(title);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);

        //Clic per començar el joc
        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
        }
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
}
