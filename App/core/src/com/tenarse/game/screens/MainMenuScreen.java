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
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.utils.Settings;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Texture background;
    private Texture btnMenu;

    private Texture btnUpTexture, btnDownTexture, btnLeftTexture, btnRightTexture;

    //Imatge jugador que es mou sol
    private Jugador botIniciAxe;
    private Jugador botIniciWarHam;
    private Jugador botIniciShield;

    private Image imgMenuJoc;
    private Tenarse game;

    private ImageButton jugarBTN, btnU_img, btnD_img, btnL_img, btnR_img;

    public MainMenuScreen(Tenarse game){
        this.game = game;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        background = AssetManager.imgMainMenu;
        imgMenuJoc = new Image(background);
        imgMenuJoc.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            botIniciAxe = new Jugador(-100, 0, Settings.PLAYER_WIDTH * 7, Settings.PLAYER_HEIGHT * 7, true, 1);
            botIniciWarHam = new Jugador(-200, 0, Settings.PLAYER_WIDTH * 7, Settings.PLAYER_HEIGHT * 7, true, 2);
            botIniciShield = new Jugador(-300, 0, Settings.PLAYER_WIDTH * 7, Settings.PLAYER_HEIGHT * 7, true, 3);

        } else {
            botIniciAxe = new Jugador(-100, 0, Settings.PLAYER_WIDTH * 4, Settings.PLAYER_HEIGHT * 4, true, 1);
            botIniciWarHam = new Jugador(-200, 0, Settings.PLAYER_WIDTH * 4, Settings.PLAYER_HEIGHT * 4, true, 2);
            botIniciShield = new Jugador(-300, 0, Settings.PLAYER_WIDTH * 4, Settings.PLAYER_HEIGHT * 4, true, 3);
        }

        btnMenu = AssetManager.imgMainMenu;

        imgMenuJoc.setPosition(0, 0);
        stage.addActor(imgMenuJoc);

        btnMenu = AssetManager.imgPlayBtn;

        btnUpTexture = AssetManager.btnMovUp;
        btnDownTexture = AssetManager.btnMovDown;
        btnRightTexture = AssetManager.btnMovRight;
        btnLeftTexture = AssetManager.btnMovLeft;

        jugarBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnMenu)));

        stage.addActor(jugarBTN);
        stage.addActor(botIniciAxe);
        stage.addActor(botIniciWarHam);
        stage.addActor(botIniciShield);

        botIniciAxe.desplazarAutomaticamente(0, Gdx.graphics.getHeight() * 0.163f);
        botIniciWarHam.desplazarAutomaticamente(-200, Gdx.graphics.getHeight() * 0.163f);
        botIniciShield.desplazarAutomaticamente(-400, Gdx.graphics.getHeight() * 0.163f);

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth(), Gdx.graphics.getHeight() / 2 - jugarBTN.getHeight());
            jugarBTN.getImage().setScale(2f);

        } else {
            jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth() / 2, Gdx.graphics.getHeight()/ 2 - jugarBTN.getHeight() / 2);
        }

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

    }
}
