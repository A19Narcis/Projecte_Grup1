package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tenarse.game.objects.Background;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.utils.Settings;

public class GameScreen implements Screen {

    private Stage stage;
    private Jugador jugador;

    private TiledMap map;

    private TmxMapLoader mapLoader;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer renderer;

    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    public GameScreen(Batch prevBatch, Viewport prevViewport){

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("assets/colisionableMap.tmx");
        MapProperties properties = map.getProperties();
        tileWidth         = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        prevViewport.setCamera(camera);


        renderer = new OrthogonalTiledMapRenderer(map);

        jugador = new Jugador(mapWidthInPixels / 2, mapHeightInPixels / 2, Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, false);

        //Crear stage
        stage = new Stage(prevViewport, prevBatch);
        if (Gdx.app.getType() == Application.ApplicationType.Android){//Zoom para Android
            stage.getViewport().setWorldSize(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2);
            stage.getViewport().apply();
        }

        //Añadir Actores
        jugador.setName("jugador");
        stage.addActor(jugador);


        //Gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cameraMapPosition();
        System.out.println(camera.position.x);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stage.draw();
        stage.act(delta);
    }

    private void cameraMapPosition() {
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            if(jugador.getCollisionRectPlayer().x < (mapWidthInPixels - (Gdx.graphics.getWidth() / 2) / 2) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2) / 2){
                camera.position.x = jugador.getCollisionRectPlayer().x;
            }
            if(jugador.getCollisionRectPlayer().y < (mapHeightInPixels - (Gdx.graphics.getHeight() / 2) / 2) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2) / 2) {
                camera.position.y = jugador.getCollisionRectPlayer().y;
            }
        } else {
            if(jugador.getCollisionRectPlayer().x < (mapWidthInPixels - (Gdx.graphics.getWidth() / 2)) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2)){
                camera.position.x = jugador.getCollisionRectPlayer().x;
            }
            if(jugador.getCollisionRectPlayer().y < (mapHeightInPixels - (Gdx.graphics.getHeight() / 2)) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2)) {
                camera.position.y = jugador.getCollisionRectPlayer().y;
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
