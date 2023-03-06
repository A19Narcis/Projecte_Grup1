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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.objects.Map;
import com.tenarse.game.utils.Settings;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChooseCharacterScreen implements Screen {

    private Tenarse game;
    private Stage stage;

    private final int MULTIP = 1;
    private final int SINGLE = 0;

    private int modeJoc;

    private Texture background, chooseBox;
    private Texture btnMapSelect, btn_left, btn_right;

    private Texture btnReturn;
    private ImageButton returnBTN;

    private Jugador botChooseCrossbow;
    private Jugador botChooseWarhammer;
    private Jugador botChooseShield;

    private Image imgBackground, imgChooseBox;
    private ImageButton imgBtnMapSelect, imgBtnLeft, imgBtnRight;

    private int selectedCharacter = 4; //Para que salga Crossbow cuando entra

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
    private Label textNombre;
    private Label textErrorNombre;

    private JSONArray fullStats;
    private JSONObject statsCrossbow;
    private JSONObject statsWarhammer;
    private JSONObject statsShield;

    private JSONObject statsZombie;
    private JSONObject statsBoss;

    private TextField textUsername;
    private Skin skin;

    public ChooseCharacterScreen(Tenarse game, int modeJoc) {
        this.game = game;

        this.modeJoc = modeJoc;


        fullStats = AssetManager.fullStats;

        //Darle las stats a cada tipo (Crossbow, Warhammer, Shield)
        statsCrossbow = fullStats.getJSONObject(0);
        statsWarhammer = fullStats.getJSONObject(1);
        statsShield = fullStats.getJSONObject(2);

        statsZombie = fullStats.getJSONObject(3);
        statsBoss = fullStats.getJSONObject(4);


        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //Background
        background = AssetManager.imgMainMenu;
        imgBackground = new Image(background);
        imgBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        imgBackground.setPosition(0, 0);

        //Mapa
        Map map = new Map();

        //Boton ELEGIR MAPA
        btnMapSelect = AssetManager.imgPickMapBtn;
        imgBtnMapSelect = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnMapSelect)));

        //Flecha return
        btnReturn = AssetManager.imgReturnBtn;
        returnBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnReturn)));


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
            botChooseCrossbow = new Jugador(0 + chooseBox.getWidth() * 1.90f, Gdx.graphics.getHeight() - chooseBox.getHeight() * 4.1f, Settings.PLAYER_WIDTH * 12, Settings.PLAYER_HEIGHT * 12, "picker", 1, map);
            botChooseWarhammer = new Jugador(0 + chooseBox.getWidth() * 1.90f, Gdx.graphics.getHeight() - chooseBox.getHeight() * 4.1f, Settings.PLAYER_WIDTH * 12, Settings.PLAYER_HEIGHT * 12, "picker", 2, map);
            botChooseShield = new Jugador(0 + chooseBox.getWidth() * 1.90f, Gdx.graphics.getHeight() - chooseBox.getHeight() * 4.1f, Settings.PLAYER_WIDTH * 12, Settings.PLAYER_HEIGHT * 12, "picker", 3, map);

        } else {
            botChooseCrossbow = new Jugador(0 + chooseBox.getWidth() - 15, Gdx.graphics.getHeight() - chooseBox.getHeight() * 2 - 70, Settings.PLAYER_WIDTH * 8, Settings.PLAYER_HEIGHT * 8, "picker", 1, map);
            botChooseWarhammer = new Jugador(0 + chooseBox.getWidth() - 15, Gdx.graphics.getHeight() - chooseBox.getHeight() * 2 - 70, Settings.PLAYER_WIDTH * 8, Settings.PLAYER_HEIGHT * 8, "picker", 2, map);
            botChooseShield = new Jugador(0 + chooseBox.getWidth() - 15, Gdx.graphics.getHeight() - chooseBox.getHeight() * 2 - 70, Settings.PLAYER_WIDTH * 8, Settings.PLAYER_HEIGHT * 8, "picker", 3, map);
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

        titolCharacter = new Label("Crossbow", labelStyleBold);
        Label textoInicio = new Label("¡Elige tu personaje!", labelStyleNormal);
        Label textStatsTitol = new Label("Estadísticas", labelStyleNormal);
        textStatVel = new Label("Velocidad: " + velocidad, labelStyleNormal);
        textStatFuerza = new Label("Fuerza: " + fuerza, labelStyleNormal);
        textStatVida = new Label("Vidas: " + vidas, labelStyleNormal);
        textStatArmadura = new Label("Armadura: " + armadura, labelStyleNormal);
        textNombre = new Label("Usuario", labelStyleBold);
        textErrorNombre = new Label("Necesitas `Usuario` para jugar...", labelStyleNormal);
        textErrorNombre.setFontScale(1.5f);

        //Caja de texto
        skin = AssetManager.skinTextBox;

        textUsername = new TextField("", skin);
        textUsername.setWidth(textUsername.getMinWidth() * 1.5f);




        //Add actors
        //stage.addActor(imgBackground);
        stage.addActor(imgBtnMapSelect);
        stage.addActor(imgChooseBox);
        stage.addActor(imgBtnLeft);
        stage.addActor(imgBtnRight);
        stage.addActor(botChooseCrossbow);
        stage.addActor(botChooseWarhammer);
        stage.addActor(botChooseShield);
        stage.addActor(titolCharacter);
        stage.addActor(textoInicio);
        stage.addActor(textStatsTitol);
        stage.addActor(textStatVel);
        stage.addActor(textStatFuerza);
        stage.addActor(textStatVida);
        stage.addActor(textStatArmadura);
        stage.addActor(textUsername);
        stage.addActor(textNombre);
        stage.addActor(textErrorNombre);
        stage.addActor(returnBTN);

        //Start Character && Start text
        stage.getActors().get(4).setVisible(false);
        stage.getActors().get(5).setVisible(false);
        stage.getActors().get(6).setVisible(false);


        //Amagar el text d'error al principi
        stage.getActors().get(16).setVisible(false);

        if (selectedCharacter == 4){
            titolCharacter.setText("Crossbow");
            stage.getActors().get(4).setVisible(true);
            velocidad = (int) statsCrossbow.get("velocidad");
            fuerza = (int) statsCrossbow.get("fuerza");
            vidas = (int) statsCrossbow.get("vida");
            armadura = (int) statsCrossbow.get("armadura");
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
            textUsername.setSize(500, 75);
            textUsername.getStyle().font.getData().setScale(1.5f);
            textUsername.setAlignment(Align.center);
            imgBtnMapSelect.setPosition(Gdx.graphics.getWidth() - imgBtnMapSelect.getWidth() * 2.5f, 0 + imgBtnMapSelect.getHeight());
            imgBtnMapSelect.getImage().setScale(2f);
            imgChooseBox.setScale(3f);
            imgChooseBox.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2.5f - chooseBox.getHeight());
            imgBtnLeft.getImage().setScale(1.5f);
            imgBtnRight.getImage().setScale(1.5f);
            imgBtnLeft.setPosition(0 + btn_left.getWidth() * 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 11.75f, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolCharacter.setPosition(0 + chooseBox.getWidth() * 2, Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.5f);
            textoInicio.setPosition(Gdx.graphics.getWidth() / 2 - textoInicio.getMinWidth() / 2, Gdx.graphics.getHeight() - textoInicio.getMinHeight());
            textErrorNombre.setPosition(Gdx.graphics.getWidth() / 2 - textErrorNombre.getMinWidth() / 2, textErrorNombre.getMinHeight());
            textUsername.setPosition(Gdx.graphics.getWidth() / 2 - textUsername.getWidth() / 2, textUsername.getMinHeight() + textErrorNombre.getHeight() * 1.5f);
            textNombre.setPosition(Gdx.graphics.getWidth() / 2 - textNombre.getWidth() / 2, textNombre.getMinHeight() + textUsername.getMinHeight() + textErrorNombre.getHeight() * 1.5f);
            returnBTN.setPosition(0 + returnBTN.getWidth(), Gdx.graphics.getHeight() - returnBTN.getHeight() * 2.5f);
            returnBTN.getImage().setScale(2f);
        } else {
            imgBtnMapSelect.setPosition(Gdx.graphics.getWidth() / 2 + imgBtnMapSelect.getWidth() * 1.5f, 0 + imgBtnMapSelect.getHeight());
            imgChooseBox.setScale(2f);
            imgChooseBox.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 - chooseBox.getHeight());
            imgBtnLeft.setPosition(0 + btn_left.getWidth() / 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 7, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolCharacter.setPosition(0 + chooseBox.getWidth(), Gdx.graphics.getHeight() / 2 + chooseBox.getHeight() * 1.1f);
            textErrorNombre.setPosition(Gdx.graphics.getWidth() / 2 - textErrorNombre.getMinWidth() / 2, textErrorNombre.getMinHeight());
            textUsername.setPosition(Gdx.graphics.getWidth() / 2 - textUsername.getWidth() / 2, textUsername.getMinHeight() + textErrorNombre.getHeight());
            textNombre.setPosition(Gdx.graphics.getWidth() / 2 - textNombre.getWidth() / 2, textNombre.getMinHeight() + textUsername.getMinHeight() + textErrorNombre.getHeight());
            returnBTN.setPosition(0 + returnBTN.getWidth() / 2, Gdx.graphics.getHeight() - returnBTN.getHeight() * 1.25f);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        imgBtnMapSelect.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String username = textUsername.getText().replace(" ", "");
                if (username.length() > 0){
                    game.setScreen(new ChooseMapScreen(game, modeJoc, selectedCharacter, username));
                    /*
                    if (modeJoc == SINGLE){
                        game.setScreen(new GameScreen(game, stage.getBatch(), stage.getViewport(), username, selectedCharacter, velocidad, fuerza, vidas, armadura, statsZombie, statsBoss));
                    } else {
                        game.setScreen(new MultiGameScreen(game, stage.getBatch(), stage.getViewport(), username, selectedCharacter, velocidad, fuerza, vidas, armadura));
                    }
                    */
                } else {
                    stage.getActors().get(16).setVisible(true);
                }
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
                    titolCharacter.setText("Crossbow");
                    stage.getActors().get(4).setVisible(true);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    //Stats
                    velocidad = (int) statsCrossbow.get("velocidad");
                    fuerza = (int) statsCrossbow.get("fuerza");
                    vidas = (int) statsCrossbow.get("vida");
                    armadura = (int) statsCrossbow.get("armadura");
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
                    titolCharacter.setText("Crossbow");
                    stage.getActors().get(4).setVisible(true);
                    stage.getActors().get(5).setVisible(false);
                    stage.getActors().get(6).setVisible(false);
                    //Stats
                    velocidad = (int) statsCrossbow.get("velocidad");
                    fuerza = (int) statsCrossbow.get("fuerza");
                    vidas = (int) statsCrossbow.get("vida");
                    armadura = (int) statsCrossbow.get("armadura");
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

        returnBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
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
