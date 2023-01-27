package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.utils.Settings;

public class GameScreen implements Screen {

    private Stage stage;
    private Jugador jugador;

    private TiledMap map;

    private TiledMapTileLayer mapLayer;

    private Texture btnUpTexture, btnDownTexture, btnLeftTexture, btnRightTexture;
    private ImageButton jugarBTN, btnU_img, btnD_img, btnL_img, btnR_img;


    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer renderer;

    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    public GameScreen(Batch prevBatch, Viewport prevViewport){

        map = AssetManager.map;
        MapProperties properties = map.getProperties();
        tileWidth         = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        mapLayer = (TiledMapTileLayer) map.getLayers().get("Mountains");
        System.out.println(mapLayer);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        prevViewport.setCamera(camera);


        renderer = new OrthogonalTiledMapRenderer(map);

        jugador = new Jugador(mapWidthInPixels / 2, mapHeightInPixels / 2, Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, false, 1);

        //Crear stage
        stage = new Stage(prevViewport, prevBatch);
        if (Gdx.app.getType() == Application.ApplicationType.Android){//Zoom para Android
            stage.getViewport().setWorldSize(stage.getViewport().getWorldWidth() / 2, stage.getViewport().getWorldHeight() / 2);//Da error esta linea
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



            btnU_img.setPosition(camera.position.x, camera.position.y);
            btnL_img.setPosition(Gdx.graphics.getWidth() * 0.02f, Gdx.graphics.getHeight() * 0.11f);
            btnD_img.setPosition(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.00f);
            btnR_img.setPosition(Gdx.graphics.getWidth() * 0.14f, Gdx.graphics.getHeight() * 0.11f);
        }

        //Añadir Actores
        jugador.setName("jugador");
        stage.addActor(jugador);
        stage.addActor(btnU_img);
        stage.addActor(btnD_img);
        stage.addActor(btnL_img);
        stage.addActor(btnR_img);


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
        playerMapColision();
        cameraMapPosition();
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stage.draw();
        stage.act(delta);
    }

    private void playerMapColision() {
        int cellX = (int)(jugador.getCollisionRectPlayer().x / tileWidth);
        int cellY = (int)(jugador.getCollisionRectPlayer().y / tileHeight);
        System.out.println("CELDA: ("+ cellX +", "+ cellY +" )");
        boolean colisionable = mapLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("colisionable"); //<---------AQUÍ DA EL ERROR!!!!
        System.out.println(colisionable);
        //MapProperties properties = tile.getProperties();
        //boolean colisionable = properties.get("colisionable", Boolean.class);
        //System.out.println(colisionable);
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
