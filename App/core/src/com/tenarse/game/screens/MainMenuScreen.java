package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Texture background;
    private Texture btnMenu;

    private Image imgMenuJoc;
    private Tenarse game;

    private ImageButton jugarBTN;

    public MainMenuScreen(Tenarse game){
        this.game = game;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        background = AssetManager.imgMainMenu;
        imgMenuJoc = new Image(background);
        imgMenuJoc.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        btnMenu = AssetManager.imgMainMenu;

        imgMenuJoc.setPosition(0, 0);
        stage.addActor(imgMenuJoc);

        btnMenu = AssetManager.imgPlayBtn;
        jugarBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnMenu)));

        stage.addActor(jugarBTN);
        stage.addActor(jugarBTN);

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth(), Gdx.graphics.getHeight() / 2 - jugarBTN.getHeight());
            jugarBTN.getImage().setScale(2f);
        } else {
            jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth() / 2, Gdx.graphics.getHeight()/ 2 - jugarBTN.getHeight() / 2);
        }

        //jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth() / 2, Gdx.graphics.getHeight()/ 2 - jugarBTN.getHeight() / 2);



        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        jugarBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
