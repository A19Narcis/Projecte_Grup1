package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.objects.Map;
import com.tenarse.game.utils.Settings;

public class ChooseCharacterScreen implements Screen {

    private Tenarse game;
    private Stage stage;

    private Texture background, chooseBox;
    private Texture btnPlay, btn_left, btn_right;

    private Jugador botChooseAxe;
    private Jugador botChooseWarhammer;
    private Jugador botChooseShield;

    private Image imgBackground, imgChooseBox;
    private ImageButton imgBtnPlay, imgBtnLeft, imgBtnRight;

    private int selectedCharacter = 4;

    private BitmapFont fontBold, fontNormal, fontSub;

    public ChooseCharacterScreen(Tenarse game) {
        this.game = game;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //Background
        background = AssetManager.imgMainMenu;
        imgBackground = new Image(background);
        imgBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        imgBackground.setPosition(0, 0);

        //Mapa
        Map map = new Map();

        //Boton JUGAR
        btnPlay = AssetManager.imgPlayBtn;
        imgBtnPlay = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnPlay)));

        //Caja elegir personaje
        chooseBox = AssetManager.imgChooseBox;
        imgChooseBox = new Image(chooseBox);

        //Buttons LEFT - RIGHT
        btn_left = AssetManager.imgLeft_char;
        btn_right = AssetManager.imgRight_char;

        imgBtnLeft = new ImageButton(new TextureRegionDrawable(new TextureRegion(btn_left)));
        imgBtnRight = new ImageButton(new TextureRegionDrawable(new TextureRegion(btn_right)));


        //Characters
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            botChooseAxe = new Jugador(0 + chooseBox.getWidth() * 1.90f, Gdx.graphics.getHeight() - chooseBox.getHeight() * 4.1f, Settings.PLAYER_WIDTH * 12, Settings.PLAYER_HEIGHT * 12, false, 1, map);
            botChooseWarhammer = new Jugador(0 + chooseBox.getWidth() * 1.90f, Gdx.graphics.getHeight() - chooseBox.getHeight() * 4.1f, Settings.PLAYER_WIDTH * 12, Settings.PLAYER_HEIGHT * 12, false, 2, map);
            botChooseShield = new Jugador(0 + chooseBox.getWidth() * 1.90f, Gdx.graphics.getHeight() - chooseBox.getHeight() * 4.1f, Settings.PLAYER_WIDTH * 12, Settings.PLAYER_HEIGHT * 12, false, 3, map);

        } else {
            botChooseAxe = new Jugador(0 + chooseBox.getWidth() - 15, Gdx.graphics.getHeight() - chooseBox.getHeight() * 2 - 70, Settings.PLAYER_WIDTH * 8, Settings.PLAYER_HEIGHT * 8, false, 1, map);
            botChooseWarhammer = new Jugador(0 + chooseBox.getWidth() - 15, Gdx.graphics.getHeight() - chooseBox.getHeight() * 2 - 70, Settings.PLAYER_WIDTH * 8, Settings.PLAYER_HEIGHT * 8, false, 2, map);
            botChooseShield = new Jugador(0 + chooseBox.getWidth() - 15, Gdx.graphics.getHeight() - chooseBox.getHeight() * 2 - 70, Settings.PLAYER_WIDTH * 8, Settings.PLAYER_HEIGHT * 8, false, 3, map);
        }

        //Texto de los personajes
        FreeTypeFontGenerator.FreeTypeFontParameter parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            parametros.size = 30;
        }

        fontBold = AssetManager.fontTextBold.generateFont(parametros);
        fontBold.getData().setScale(2);

        fontNormal = AssetManager.fontTextNormal.generateFont(parametros);
        fontNormal.getData().setScale(2);

        Label.LabelStyle labelStyleBold = new Label.LabelStyle();
        labelStyleBold.font = fontBold;
        labelStyleBold.fontColor = Color.BLACK;

        Label.LabelStyle labelStyleNormal = new Label.LabelStyle();
        labelStyleNormal.font = fontNormal;
        labelStyleBold.fontColor = Color.BLACK;

        Label titolAxe = new Label("Axe", labelStyleBold);
        Label titolWarhammer = new Label("Warhammer", labelStyleBold);
        Label titolShield = new Label("Shield", labelStyleBold);
        Label textoInicio = new Label("¡Elige tu personaje!", labelStyleNormal);
        Label textStatsTitol = new Label("Estadísticas", labelStyleNormal);



        //Add actors
        //stage.addActor(imgBackground);
        stage.addActor(imgBtnPlay);
        stage.addActor(imgChooseBox);
        stage.addActor(imgBtnLeft);
        stage.addActor(imgBtnRight);
        stage.addActor(botChooseAxe);
        stage.addActor(botChooseWarhammer);
        stage.addActor(botChooseShield);
        stage.addActor(titolAxe);
        stage.addActor(titolWarhammer);
        stage.addActor(titolShield);
        stage.addActor(textoInicio);
        stage.addActor(textStatsTitol);

        //Start Character && Start text
        stage.getActors().get(4).setVisible(false);
        stage.getActors().get(5).setVisible(false);
        stage.getActors().get(6).setVisible(false);

        stage.getActors().get(7).setVisible(false);
        stage.getActors().get(8).setVisible(false);
        stage.getActors().get(9).setVisible(false);


        if (selectedCharacter == 4){
            stage.getActors().get(4).setVisible(true);
            stage.getActors().get(7).setVisible(true);
        }


        if (Gdx.app.getType() == Application.ApplicationType.Android){
            imgBtnPlay.setPosition(Gdx.graphics.getWidth() - imgBtnPlay.getWidth() * 2.5f, 0 + imgBtnPlay.getHeight());
            imgBtnPlay.getImage().setScale(2f);
            imgChooseBox.setScale(3f);
            imgChooseBox.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2.5f - chooseBox.getHeight());
            imgBtnLeft.getImage().setScale(1.5f);
            imgBtnRight.getImage().setScale(1.5f);
            imgBtnLeft.setPosition(0 + btn_left.getWidth() * 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 11.75f, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolAxe.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.5f);
            titolWarhammer.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.5f);
            titolShield.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.5f);
        } else {
            imgBtnPlay.setPosition(Gdx.graphics.getWidth() / 2 + imgBtnPlay.getWidth(), 0 + imgBtnPlay.getHeight());
            imgChooseBox.setScale(2f);
            imgChooseBox.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 - chooseBox.getHeight());
            imgBtnLeft.setPosition(0 + btn_left.getWidth() / 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 7, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolAxe.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.1f);
            titolWarhammer.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.1f);
            titolShield.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.1f);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        imgBtnPlay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport(), selectedCharacter));

                return true;
            }
        });

        imgBtnLeft.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedCharacter--;
                if (selectedCharacter == 3){
                    selectedCharacter = 6;
                }

                if (selectedCharacter == 4){
                    stage.getActors().get(4).setVisible(true);
                    stage.getActors().get(7).setVisible(true);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(8).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    stage.getActors().get(9).setVisible(false);
                } else if (selectedCharacter == 5){
                    stage.getActors().get(5).setVisible(true);
                    stage.getActors().get(8).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(7).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    stage.getActors().get(9).setVisible(false);
                } else if (selectedCharacter == 6){
                    stage.getActors().get(6).setVisible(true);
                    stage.getActors().get(9).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(7).setVisible(false);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(8).setVisible(false);
                }

                return true;
            }
        });

        imgBtnRight.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedCharacter++;
                if (selectedCharacter == 7){
                    selectedCharacter = 4;
                }

                if (selectedCharacter == 4){
                    stage.getActors().get(4).setVisible(true);
                    stage.getActors().get(7).setVisible(true);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(8).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    stage.getActors().get(9).setVisible(false);
                } else if (selectedCharacter == 5){
                    stage.getActors().get(5).setVisible(true);
                    stage.getActors().get(8).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(7).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    stage.getActors().get(9).setVisible(false);
                } else if (selectedCharacter == 6){
                    stage.getActors().get(6).setVisible(true);
                    stage.getActors().get(9).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(7).setVisible(false);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(8).setVisible(false);
                }

                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(210/255f, 180/255f, 140/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
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
