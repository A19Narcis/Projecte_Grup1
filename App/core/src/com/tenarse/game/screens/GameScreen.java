package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.objects.Map;
import com.tenarse.game.objects.Zombie;
import com.tenarse.game.utils.Settings;

import java.util.ArrayList;

public class GameScreen implements Screen {

    private Boolean buttonUpPressed = false;
    private Boolean buttonDownPressed = false;
    private Boolean buttonLeftPressed = false;
    private Boolean buttonRightPressed = false;

    private Boolean buttonAttackPressed = false;

    private Stage stage;
    private Jugador jugador;

    private int zoomAndroid;
    private int zoomPc;

    public Map map;

    private Texture btnUpTexture, btnDownTexture, btnLeftTexture, btnRightTexture, btnAtacarTexture, corazonesTexture;
    private ImageButton btnU_img, btnD_img, btnL_img, btnR_img, btn_atacar, hp_player;
    private ArrayList<ImageButton> corazonesArray = new ArrayList<>();

    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer renderer;

    long lastZombieTime = 0;

    ArrayList<Zombie> enemies = new ArrayList<>();
    ArrayList<Jugador> players = new ArrayList<>();

    public GameScreen(Batch prevBatch, Viewport prevViewport, int tipus) {

        shapeRenderer = new ShapeRenderer();

        zoomAndroid = 6;
        zoomPc = 3;

        map = new Map(AssetManager.map);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        prevViewport.setCamera(camera);


        renderer = new OrthogonalTiledMapRenderer(map.getMap());

        if (tipus == 4){
            jugador = new Jugador(map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, false, 1, map);
            players.add(jugador);
        } else if (tipus == 5){
            jugador = new Jugador(map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, false, 2, map);
            players.add(jugador);
        } else if (tipus == 6){
            jugador = new Jugador(map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, false, 3, map);
            players.add(jugador);
        }

        //Crear stage
        stage = new Stage(prevViewport, prevBatch);

        //Añadir Actores
        jugador.setName("jugador");
        stage.addActor(jugador);


        //Zombie zombie = new Zombie(Settings.ZOMBIE_WIDTH, Settings.ZOMBIE_HEIGHT, map);
        //enemies.add(zombie);
        //stage.addActor(zombie);
        corazonesTexture = AssetManager.hp_player;

        for (int i = 1; i <= 5/*NumeroVidasJugador*/; i++) {
            hp_player = new ImageButton(new TextureRegionDrawable(new TextureRegion(corazonesTexture)));
            hp_player.setSize(12,12);
            corazonesArray.add(hp_player);
            stage.addActor(hp_player);
        }

        if (Gdx.app.getType() == Application.ApplicationType.Android) {//Zoom para Android
            stage.getViewport().setWorldSize(stage.getViewport().getWorldWidth() / zoomAndroid, stage.getViewport().getWorldHeight() / zoomAndroid);
            stage.getViewport().apply();

            //Cargar flechas para moverse en el movil
            btnUpTexture = AssetManager.btnMovUp;
            btnDownTexture = AssetManager.btnMovDown;
            btnRightTexture = AssetManager.btnMovRight;
            btnLeftTexture = AssetManager.btnMovLeft;

            btnAtacarTexture = AssetManager.btnAtacar;

            btnU_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnUpTexture)));
            btnD_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnDownTexture)));
            btnL_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnLeftTexture)));
            btnR_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnRightTexture)));

            btn_atacar = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnAtacarTexture)));

            btnU_img.setSize(25, 25);
            btnL_img.setSize(25, 25);
            btnD_img.setSize(25, 25);
            btnR_img.setSize(25, 25);

            btn_atacar.setSize(35, 35);

            stage.addActor(btnU_img);
            stage.addActor(btnD_img);
            stage.addActor(btnL_img);
            stage.addActor(btnR_img);

            stage.addActor(btn_atacar);
        } else {
            stage.getViewport().setWorldSize(stage.getViewport().getWorldWidth() / zoomPc, stage.getViewport().getWorldHeight() / zoomPc);
            stage.getViewport().apply();
        }

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            btn_atacar.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonAttackPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    buttonAttackPressed = false;
                }
            });

            btnU_img.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonUpPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    buttonUpPressed = false;
                }
            });

            btnL_img.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLeftPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    buttonLeftPressed = false;
                }


            });

            btnD_img.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonDownPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    buttonDownPressed = false;
                }
            });

            btnR_img.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    buttonRightPressed = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    buttonRightPressed = false;
                }
            });

        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cameraMapPosition();

        camera.update();

        if (buttonUpPressed) {
            jugador.goingUp();
        } else {
            jugador.stop();
        }

        if (buttonDownPressed) {
            jugador.goingDown();
        }

        if (buttonLeftPressed) {
            jugador.goingLeft();
        }

        if (buttonRightPressed) {
            jugador.goingRight();
        }



        renderer.setView(camera);
        renderer.render();
        stage.act(delta);

        for (Jugador player: players){
            for (Zombie zombie: enemies) {
                try {
                    zombie.calculateMovement(jugador.getCollisionRectPlayer(), delta);
                    zombie.colisionWithPlayer(jugador);
                    player.attacking(zombie);
                }catch (Exception e){//Elimina zombies muertos de la lista
                    zombie.remove();
                }
            }
        }

        spawnZombie();

        if (buttonAttackPressed) {
            jugador.startAttack();
        } else {
            jugador.stopAttack();
        }

        for (int i = 1; i <= corazonesArray.size(); i++) {
            corazonesArray.get(i-1).setPosition(camera.position.x - (camera.viewportWidth / 2 + 10) + 15 * i, camera.position.y + camera.viewportHeight / 2 - 20);
        }


        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            btnU_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 20, camera.position.y - camera.viewportHeight / 2 + 40);
            btnD_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 20, camera.position.y - camera.viewportHeight / 2);
            btnL_img.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2 + 20);
            btnR_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 40, camera.position.y - camera.viewportHeight / 2 + 20);

            btn_atacar.setPosition(camera.position.x + camera.viewportWidth / 2 - 50, camera.position.y - camera.viewportHeight / 2 + 10);
        }
        stage.draw();
    }

    private void spawnZombie() {
        if (TimeUtils.nanoTime() - lastZombieTime > Settings.SPAWN_INTERVAL) {
            Zombie zombie = new Zombie(Settings.ZOMBIE_WIDTH, Settings.ZOMBIE_HEIGHT, map);
            enemies.add(zombie);
            stage.addActor(zombie);
            lastZombieTime = TimeUtils.nanoTime();
        }
    }


    private void cameraMapPosition() {
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            if (jugador.getCollisionRectPlayer().x < (map.getMapWidthInPixels() - (Gdx.graphics.getWidth() / 2) / zoomAndroid) - (Settings.PLAYER_WIDTH / 2) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2) / zoomAndroid - (Settings.PLAYER_WIDTH / 2)) {
                camera.position.x = jugador.getCollisionRectPlayer().x + (Settings.PLAYER_WIDTH / 2);
            }
            if (jugador.getCollisionRectPlayer().y < (map.getMapHeightInPixels() - (Gdx.graphics.getHeight() / 2) / zoomAndroid) - (Settings.PLAYER_WIDTH / 2) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2) / zoomAndroid - (Settings.PLAYER_HEIGHT / 2)) {
                camera.position.y = jugador.getCollisionRectPlayer().y + (Settings.PLAYER_HEIGHT / 2);
            }
        } else {
            if (jugador.getCollisionRectPlayer().x < (map.getMapWidthInPixels() - (Gdx.graphics.getWidth() / 2) / zoomPc) - (Settings.PLAYER_WIDTH / 2) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2) / zoomPc - (Settings.PLAYER_WIDTH / 2)) {
                camera.position.x = jugador.getCollisionRectPlayer().x + (Settings.PLAYER_WIDTH / 2);
            }
            if (jugador.getCollisionRectPlayer().y < (map.getMapHeightInPixels() - (Gdx.graphics.getHeight() / 2) / zoomPc) - (Settings.PLAYER_WIDTH / 2) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2) / zoomPc - (Settings.PLAYER_HEIGHT / 2)) {
                camera.position.y = jugador.getCollisionRectPlayer().y + (Settings.PLAYER_HEIGHT / 2);
            }
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

    public Stage getStage() {
        return stage;
    }

    public Jugador getJugador() {
        return jugador;
    }

}
