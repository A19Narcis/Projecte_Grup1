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
import com.tenarse.game.objects.ConnectionNode;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.objects.Map;
import com.tenarse.game.utils.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

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

    private int velocidad;
    private int fuerza;
    private int vidas;
    private int armadura;

    private Label textStatVel;
    private Label textStatFuerza;
    private Label textStatVida;
    private Label textStatArmadura;
    private Label titolCharacter;

    private JSONArray fullStats;
    private JSONObject statsAxe;
    private JSONObject statsWarhammer;
    private JSONObject statsShield;

    public ChooseCharacterScreen(Tenarse game) {
        this.game = game;

        //NodeJS Connection
        ConnectionNode nodeJs = new ConnectionNode();
        synchronized (nodeJs.lock) {
            try {
                nodeJs.lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        fullStats = nodeJs.getStatsArray();

        //Darle las stats a cada tipo (Axe, Warhammer, Shield)
        statsAxe = fullStats.getJSONObject(0);
        statsWarhammer = fullStats.getJSONObject(1);
        statsShield = fullStats.getJSONObject(2);


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
        labelStyleNormal.fontColor = Color.BLACK;

        titolCharacter = new Label("Axe", labelStyleBold);
        Label textoInicio = new Label("¡Elige tu personaje!", labelStyleNormal);
        Label textStatsTitol = new Label("Estadísticas", labelStyleNormal);
        textStatVel = new Label("Velocidad: " + velocidad, labelStyleNormal);
        textStatFuerza = new Label("Fuerza: " + fuerza, labelStyleNormal);
        textStatVida = new Label("Vidas: " + vidas, labelStyleNormal);
        textStatArmadura = new Label("Armadura: " + armadura, labelStyleNormal);





        //Add actors
        //stage.addActor(imgBackground);
        stage.addActor(imgBtnPlay);
        stage.addActor(imgChooseBox);
        stage.addActor(imgBtnLeft);
        stage.addActor(imgBtnRight);
        stage.addActor(botChooseAxe);
        stage.addActor(botChooseWarhammer);
        stage.addActor(botChooseShield);
        stage.addActor(titolCharacter);
        stage.addActor(textoInicio);
        stage.addActor(textStatsTitol);
        stage.addActor(textStatVel);
        stage.addActor(textStatFuerza);
        stage.addActor(textStatVida);
        stage.addActor(textStatArmadura);

        //Start Character && Start text
        stage.getActors().get(4).setVisible(false);
        stage.getActors().get(5).setVisible(false);
        stage.getActors().get(6).setVisible(false);

        if (selectedCharacter == 4){
            titolCharacter.setText("Axe");
            stage.getActors().get(4).setVisible(true);
            velocidad = (int) statsAxe.get("velocidad");
            fuerza = (int) statsAxe.get("fuerza");
            vidas = (int) statsAxe.get("vida");
            armadura = (int) statsAxe.get("armadura");
            textStatVel.setText("Velocidad: " + velocidad);
            textStatFuerza.setText("Fuerza: " + fuerza);
            textStatVida.setText("Vidas: " + vidas);
            textStatArmadura.setText("Armadura: " + armadura);
        }


        //Posicionamiento de los actores en la pantalla (Android / Desktop)
        textoInicio.setPosition(Gdx.graphics.getWidth() / 2 - textoInicio.getMinWidth() / 2, Gdx.graphics.getHeight() - textoInicio.getMinHeight());
        textStatsTitol.setPosition(Gdx.graphics.getWidth() / 1.75f, Gdx.graphics.getHeight() - textStatsTitol.getMinHeight() * 3.5f);
        textStatVel.setPosition(Gdx.graphics.getWidth() / 1.75f, Gdx.graphics.getHeight() - textStatVel.getMinHeight() * 5f);
        textStatFuerza.setPosition(Gdx.graphics.getWidth() / 1.75f, Gdx.graphics.getHeight() - textStatFuerza.getMinHeight() * 6f);
        textStatVida.setPosition(Gdx.graphics.getWidth() / 1.75f, Gdx.graphics.getHeight() - textStatVida.getMinHeight() * 7f);
        textStatArmadura.setPosition(Gdx.graphics.getWidth() / 1.75f, Gdx.graphics.getHeight() - textStatArmadura.getMinHeight() * 8f);


        if (Gdx.app.getType() == Application.ApplicationType.Android){
            imgBtnPlay.setPosition(Gdx.graphics.getWidth() - imgBtnPlay.getWidth() * 2.5f, 0 + imgBtnPlay.getHeight());
            imgBtnPlay.getImage().setScale(2f);
            imgChooseBox.setScale(3f);
            imgChooseBox.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2.5f - chooseBox.getHeight());
            imgBtnLeft.getImage().setScale(1.5f);
            imgBtnRight.getImage().setScale(1.5f);
            imgBtnLeft.setPosition(0 + btn_left.getWidth() * 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 11.75f, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolCharacter.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.5f);
            textoInicio.setPosition(Gdx.graphics.getWidth() / 2 - textoInicio.getMinWidth() / 2, Gdx.graphics.getHeight() - textoInicio.getMinHeight());
        } else {
            imgBtnPlay.setPosition(Gdx.graphics.getWidth() / 2 + imgBtnPlay.getWidth(), 0 + imgBtnPlay.getHeight());
            imgChooseBox.setScale(2f);
            imgChooseBox.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 - chooseBox.getHeight());
            imgBtnLeft.setPosition(0 + btn_left.getWidth() / 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 7, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolCharacter.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.1f);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        imgBtnPlay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(stage.getBatch(), stage.getViewport(), selectedCharacter, velocidad, fuerza, vidas, armadura));
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
                    titolCharacter.setText("Axe");
                    stage.getActors().get(4).setVisible(true);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    //Stats
                    velocidad = (int) statsAxe.get("velocidad");
                    fuerza = (int) statsAxe.get("fuerza");
                    vidas = (int) statsAxe.get("vida");
                    armadura = (int) statsAxe.get("armadura");
                    textStatVel.setText("Velocidad: " + velocidad);
                    textStatFuerza.setText("Fuerza: " + fuerza);
                    textStatVida.setText("Vidas: " + vidas);
                    textStatArmadura.setText("Armadura: " + armadura);
                } else if (selectedCharacter == 5){
                    titolCharacter.setText("Warhammer");
                    stage.getActors().get(5).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    //Stats
                    velocidad = (int) statsWarhammer.get("velocidad");
                    fuerza = (int) statsWarhammer.get("fuerza");
                    vidas = (int) statsWarhammer.get("vida");
                    armadura = (int) statsWarhammer.get("armadura");
                    textStatVel.setText("Velocidad: " + velocidad);
                    textStatFuerza.setText("Fuerza: " + fuerza);
                    textStatVida.setText("Vidas: " + vidas);
                    textStatArmadura.setText("Armadura: " + armadura);
                } else if (selectedCharacter == 6){
                    titolCharacter.setText("Shield");
                    stage.getActors().get(6).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(5).setVisible(false);
                    //Stats
                    velocidad = (int) statsShield.get("velocidad");
                    fuerza = (int) statsShield.get("fuerza");
                    vidas = (int) statsShield.get("vida");
                    armadura = (int) statsShield.get("armadura");
                    textStatVel.setText("Velocidad: " + velocidad);
                    textStatFuerza.setText("Fuerza: " + fuerza);
                    textStatVida.setText("Vidas: " + vidas);
                    textStatArmadura.setText("Armadura: " + armadura);
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
                    titolCharacter.setText("Axe");
                    stage.getActors().get(4).setVisible(true);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    //Stats
                    velocidad = (int) statsAxe.get("velocidad");
                    fuerza = (int) statsAxe.get("fuerza");
                    vidas = (int) statsAxe.get("vida");
                    armadura = (int) statsAxe.get("armadura");
                    textStatVel.setText("Velocidad: " + velocidad);
                    textStatFuerza.setText("Fuerza: " + fuerza);
                    textStatVida.setText("Vidas: " + vidas);
                    textStatArmadura.setText("Armadura: " + armadura);
                } else if (selectedCharacter == 5){
                    titolCharacter.setText("Warhammer");
                    stage.getActors().get(5).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    //Stats
                    velocidad = (int) statsWarhammer.get("velocidad");
                    fuerza = (int) statsWarhammer.get("fuerza");
                    vidas = (int) statsWarhammer.get("vida");
                    armadura = (int) statsWarhammer.get("armadura");
                    textStatVel.setText("Velocidad: " + velocidad);
                    textStatFuerza.setText("Fuerza: " + fuerza);
                    textStatVida.setText("Vidas: " + vidas);
                    textStatArmadura.setText("Armadura: " + armadura);
                } else if (selectedCharacter == 6){
                    titolCharacter.setText("Shield");
                    stage.getActors().get(6).setVisible(true);
                    stage.getActors().get(4).setVisible(false);
                    stage.getActors().get(5).setVisible(false);
                    //Stats
                    velocidad = (int) statsShield.get("velocidad");
                    fuerza = (int) statsShield.get("fuerza");
                    vidas = (int) statsShield.get("vida");
                    armadura = (int) statsShield.get("armadura");
                    textStatVel.setText("Velocidad: " + velocidad);
                    textStatFuerza.setText("Fuerza: " + fuerza);
                    textStatVida.setText("Vidas: " + vidas);
                    textStatArmadura.setText("Armadura: " + armadura);
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
        stage.dispose();
    }
}
