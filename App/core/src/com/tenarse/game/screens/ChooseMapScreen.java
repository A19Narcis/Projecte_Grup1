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

public class ChooseMapScreen implements Screen {

    private Tenarse game;
    private Stage stage;

    private final int MULTIP = 1;
    private final int SINGLE = 0;

    private int selectedCharacter;
    private String username;

    private int modeJoc;

    private Texture background;
    private Texture btnPlay, btn_left, btn_right;
    private Texture mapa;

    private Texture btnReturn;
    private ImageButton returnBTN;

    private Image imgBackground;
    private ImageButton imgBtnPlay, imgBtnLeft, imgBtnRight;

    private BitmapFont fontBold, fontNormal;

    private Label titolMapa;

    private JSONArray fullStats;

    private JSONObject statsZombie;
    private JSONObject statsBoss;

    private TextField textUsername;
    private Skin skin;

    public ChooseMapScreen(Tenarse game, int modeJoc, int selectedCharacter, String usuario) {
        this.game = game;

        this.modeJoc = modeJoc;
        this.username = usuario;
        this.selectedCharacter = selectedCharacter;

        fullStats = AssetManager.fullStats;

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //Background
        background = AssetManager.imgMainMenu;
        imgBackground = new Image(background);
        imgBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        imgBackground.setPosition(0, 0);

        //Boton JUGAR
        btnPlay = AssetManager.imgPlayBtn;
        imgBtnPlay = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnPlay)));

        //Flecha return
        btnReturn = AssetManager.imgReturnBtn;
        returnBTN = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnReturn)));

        //Buttons LEFT - RIGHT
        btn_left = AssetManager.imgLeft_char;
        btn_right = AssetManager.imgRight_char;

        imgBtnLeft = new ImageButton(new TextureRegionDrawable(new TextureRegion(btn_left)));
        imgBtnRight = new ImageButton(new TextureRegionDrawable(new TextureRegion(btn_right)));

        //Textos
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

        titolMapa = new Label("MAPA 1", labelStyleBold);
        Label textoInicio = new Label("¡Elige el mapa!", labelStyleNormal);

        //Caja de texto
        skin = AssetManager.skinTextBox;

        textUsername = new TextField("", skin);
        textUsername.setWidth(textUsername.getMinWidth() * 1.5f);




        //Add actors
        //stage.addActor(imgBackground);
        stage.addActor(imgBtnPlay);

        stage.addActor(imgBtnLeft);
        stage.addActor(imgBtnRight);
        stage.addActor(titolMapa);
        stage.addActor(textoInicio);
        stage.addActor(textUsername);
        stage.addActor(returnBTN);


        //Posicionamiento de los actores en la pantalla (Android / Desktop)
        textoInicio.setPosition(Gdx.graphics.getWidth() / 2 - textoInicio.getMinWidth() / 2, Gdx.graphics.getHeight() - textoInicio.getMinHeight());

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            imgBtnPlay.setPosition(Gdx.graphics.getWidth() - imgBtnPlay.getWidth() * 2.5f, 0 + imgBtnPlay.getHeight());
            imgBtnPlay.getImage().setScale(2f);
            imgBtnLeft.getImage().setScale(1.5f);
            imgBtnRight.getImage().setScale(1.5f);
            imgBtnLeft.setPosition(0 + btn_left.getWidth() * 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 11.75f, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolMapa.setPosition(0  , 0);
            returnBTN.setPosition(0 + returnBTN.getWidth(), Gdx.graphics.getHeight() - returnBTN.getHeight() * 2.5f);
            returnBTN.getImage().setScale(2f);
        } else {
            imgBtnPlay.setPosition(Gdx.graphics.getWidth() / 2 + imgBtnPlay.getWidth() * 1.5f, 0 + imgBtnPlay.getHeight());
            imgBtnLeft.setPosition(0 + btn_left.getWidth() / 2, Gdx.graphics.getHeight() / 2 - btn_left.getHeight() / 2);
            imgBtnRight.setPosition(0 + btn_right.getWidth() * 7, Gdx.graphics.getHeight() / 2 - btn_right.getHeight() / 2);
            titolMapa.setPosition(0, 0);
            returnBTN.setPosition(0 + returnBTN.getWidth() / 2, Gdx.graphics.getHeight() - returnBTN.getHeight() * 1.25f);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        imgBtnPlay.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (modeJoc == SINGLE){
                    game.setScreen(new GameScreen(game, stage.getBatch(), stage.getViewport(), username, selectedCharacter));
                } else {
                    //game.setScreen(new MultiGameScreen(game, stage.getBatch(), stage.getViewport(), username, selectedCharacter));
                }
                return true;
            }
        });

        imgBtnLeft.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        imgBtnRight.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        returnBTN.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ChooseCharacterScreen(game, modeJoc));
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
