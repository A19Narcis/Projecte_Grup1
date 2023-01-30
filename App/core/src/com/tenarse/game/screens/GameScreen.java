package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.utils.Settings;

public class GameScreen implements Screen {

    private Boolean buttonUpPressed = false;
    private Boolean buttonDownPressed = false;
    private Boolean buttonLeftPressed = false;
    private Boolean buttonRightPressed = false;

    private Stage stage;
    private Jugador jugador;

    private TiledMap map;

    private TiledMapTileLayer mapLayer;

    private Texture btnUpTexture, btnDownTexture, btnLeftTexture, btnRightTexture;
    private ImageButton btnU_img, btnD_img, btnL_img, btnR_img;

    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer renderer;

    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    private Vector3 vector3;

    public GameScreen(Batch prevBatch, Viewport prevViewport){

        shapeRenderer = new ShapeRenderer();

        vector3 = new Vector3();

        map = AssetManager.map;
        MapProperties properties = map.getProperties();
        tileWidth         = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        mapLayer = (TiledMapTileLayer) map.getLayers().get("Ground");
        System.out.println(mapLayer);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        prevViewport.setCamera(camera);


        renderer = new OrthogonalTiledMapRenderer(map);

        jugador = new Jugador(mapWidthInPixels / 2 - (Settings.PLAYER_WIDTH / 2), mapHeightInPixels / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, false, 1);

        //Crear stage
        stage = new Stage(prevViewport, prevBatch);
        if (Gdx.app.getType() == Application.ApplicationType.Android){//Zoom para Android
            stage.getViewport().setWorldSize(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2);
            stage.getViewport().apply();

            //Cargar flechas para moverse en el movil
            btnUpTexture = AssetManager.btnMovUp;
            btnDownTexture = AssetManager.btnMovDown;
            btnRightTexture = AssetManager.btnMovRight;
            btnLeftTexture = AssetManager.btnMovLeft;

            btnU_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnUpTexture)));
            btnD_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnDownTexture)));
            btnL_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnLeftTexture)));
            btnR_img = new ImageButton(new TextureRegionDrawable(new TextureRegion(btnRightTexture)));

            btnU_img.setSize(100, 100);
            btnL_img.setSize(100, 100);
            btnD_img.setSize(100, 100);
            btnR_img.setSize(100, 100);

            stage.addActor(btnU_img);
            stage.addActor(btnD_img);
            stage.addActor(btnL_img);
            stage.addActor(btnR_img);
        }

        //Añadir Actores
        jugador.setName("jugador");
        stage.addActor(jugador);


        //Gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter(){
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                // Acción a realizar cuando el dedo se mueve fuera del área del botón
                Vector2 posDedo = new Vector2(screenX, screenY);
                btnU_img.screenToLocalCoordinates(posDedo);
                return true;
            }
        }));
    }


    @Override
    public void show() {
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            btnU_img.addListener(new InputListener(){
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

            btnL_img.addListener(new InputListener(){
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

            btnD_img.addListener(new InputListener(){
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

            btnR_img.addListener(new InputListener(){
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
        jugador.playerMapColision(tileWidth, tileHeight, mapLayer);

        cameraMapPosition();

        camera.update();
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            btnU_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 75, camera.position.y - camera.viewportHeight / 2 + 150);
            btnD_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 75, camera.position.y - camera.viewportHeight / 2);
            btnL_img.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2 + 75);
            btnR_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 150, camera.position.y - camera.viewportHeight / 2 + 75);
        }

        if (buttonUpPressed){
            jugador.goingUp();
        } else {
            jugador.stop();
        }

        if (buttonDownPressed){
            jugador.goingDown();
        }

        if (buttonLeftPressed){
            jugador.goingLeft();
        }

        if (buttonRightPressed){
            jugador.goingRight();
        }

        renderer.setView(camera);
        renderer.render();
        stage.draw();
        stage.act(delta);
    }



    private void cameraMapPosition() {
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            if(jugador.getCollisionRectPlayer().x < (mapWidthInPixels - (Gdx.graphics.getWidth() / 2) / 2) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2) / 2){
                camera.position.x = jugador.getCollisionRectPlayer().x + (Settings.PLAYER_WIDTH / 2);
            }
            if(jugador.getCollisionRectPlayer().y < (mapHeightInPixels - (Gdx.graphics.getHeight() / 2) / 2) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2) / 2) {
                camera.position.y = jugador.getCollisionRectPlayer().y + (Settings.PLAYER_HEIGHT / 2);
            }
        } else {
            if(jugador.getCollisionRectPlayer().x < (mapWidthInPixels - (Gdx.graphics.getWidth() / 2) - (Settings.PLAYER_WIDTH / 2)) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2) - (Settings.PLAYER_WIDTH / 2)){
                camera.position.x = jugador.getCollisionRectPlayer().x + (Settings.PLAYER_WIDTH / 2);
            }
            if(jugador.getCollisionRectPlayer().y < (mapHeightInPixels - (Gdx.graphics.getHeight() / 2) - (Settings.PLAYER_HEIGHT / 2)) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2) - (Settings.PLAYER_HEIGHT / 2)) {
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
