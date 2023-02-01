package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
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
    private Texture btnMenu, btnSingle, btnMulti, btnReturn;

    //Imatge jugador que es mou sol
    private Jugador botIniciAxe;
    private Jugador botIniciWarHam;
    private Jugador botIniciShield;

    private Image imgMenuJoc;
    private Tenarse game;

    private ImageButton jugarBTN, multiBTN, singleBTN, returnBTN;

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


        imgMenuJoc.setPosition(0, 0);
        stage.addActor(imgMenuJoc);

        btnMenu = AssetManager.imgPlayBtn;
        btnSingle = AssetManager.imgSingleBtn;
        btnMulti = AssetManager.imgMultiBtn;
        btnReturn = AssetManager.imgReturnBtn;

        jugarBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnMenu)));
        multiBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnMulti)));
        singleBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnSingle)));
        returnBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnReturn)));

        stage.addActor(jugarBTN);        //1
        stage.addActor(botIniciAxe);     //2
        stage.addActor(botIniciWarHam);  //3
        stage.addActor(botIniciShield);  //4
        stage.addActor(multiBTN);        //5
        stage.addActor(singleBTN);       //6
        stage.addActor(returnBTN);       //7


        stage.getActors().get(5).setVisible(false);
        stage.getActors().get(6).setVisible(false);
        stage.getActors().get(7).setVisible(false);

        botIniciAxe.desplazarAutomaticamente(0, Gdx.graphics.getHeight() * 0.163f);
        botIniciWarHam.desplazarAutomaticamente(-200, Gdx.graphics.getHeight() * 0.163f);
        botIniciShield.desplazarAutomaticamente(-400, Gdx.graphics.getHeight() * 0.163f);

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth(), Gdx.graphics.getHeight() / 2 - jugarBTN.getHeight());
            jugarBTN.getImage().setScale(2f);

        } else {
            jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth() / 2, Gdx.graphics.getHeight()/ 2 - jugarBTN.getHeight() / 2);
        }

        System.out.println(stage.getActors().get(1));

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        jugarBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.getActors().get(1).setVisible(false);
                stage.getActors().get(5).setVisible(true);
                stage.getActors().get(6).setVisible(true);
                stage.getActors().get(7).setVisible(true);

                if (Gdx.app.getType() == Application.ApplicationType.Android){
                    multiBTN.setPosition(Gdx.graphics.getWidth() / 2 - multiBTN.getWidth(), Gdx.graphics.getHeight() / 2 - multiBTN.getHeight() / 2 - multiBTN.getHeight() + multiBTN.getHeight() - multiBTN.getHeight() / 2);
                    multiBTN.getImage().setScale(2f);
                    singleBTN.setPosition(Gdx.graphics.getWidth() / 2 - singleBTN.getWidth(), Gdx.graphics.getHeight() / 2 - singleBTN.getHeight() / 2 + multiBTN.getHeight() * 2);
                    singleBTN.getImage().setScale(2f);
                    returnBTN.setPosition(0 + returnBTN.getWidth(), Gdx.graphics.getHeight() - returnBTN.getHeight() * 3);
                    returnBTN.getImage().setScale(2f);

                } else {
                    singleBTN.setPosition(Gdx.graphics.getWidth() / 2 - singleBTN.getWidth() / 2, Gdx.graphics.getHeight() / 2 + singleBTN.getHeight());
                    multiBTN.setPosition(Gdx.graphics.getWidth() / 2 - multiBTN.getWidth() / 2, Gdx.graphics.getHeight() / 2 + multiBTN.getHeight() - multiBTN.getHeight() * 1.2f);
                    returnBTN.setPosition(0 + returnBTN.getWidth() / 2, Gdx.graphics.getHeight() - returnBTN.getHeight() * 1.5f);
                }

                return true;
            }
        });

        multiBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
                return true;
            }
        });

        singleBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport()));
                return true;
            }
        });

        returnBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                stage.getActors().get(1).setVisible(true);
                stage.getActors().get(5).setVisible(false);
                stage.getActors().get(6).setVisible(false);
                stage.getActors().get(7).setVisible(false);
                if (Gdx.app.getType() == Application.ApplicationType.Android){
                    jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth(), Gdx.graphics.getHeight() / 2 - jugarBTN.getHeight());
                    jugarBTN.getImage().setScale(2f);

                } else {
                    jugarBTN.setPosition(Gdx.graphics.getWidth() / 2 - jugarBTN.getWidth() / 2, Gdx.graphics.getHeight()/ 2 - jugarBTN.getHeight() / 2);
                }
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
