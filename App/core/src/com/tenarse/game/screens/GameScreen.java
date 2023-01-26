package com.tenarse.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    private Background background;

    private ShapeRenderer shapeRenderer;
    private Batch batch;

    public GameScreen(Batch prevBatch, Viewport prevViewport){

        //Camara

        //Start music

        //Crear Shaperender
        shapeRenderer = new ShapeRenderer();

        //Crear stage
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        //Crear el PJ
        jugador = new Jugador(Settings.PLAYER_STARTX, Settings.PLAYER_STARTY, Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT);
        background = new Background(3840, 2160);

        //Afegir actors a l'stage
        stage.addActor(background);
        stage.addActor(jugador);
        jugador.setName("jugador");

        //Gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        System.out.println(stage.getViewport().getCamera().position);
        cameraMapPosition();
        stage.draw();
        stage.act(delta);
    }

    private void cameraMapPosition() {
        if(jugador.getCollisionRectPlayer().x < background.getWidth() - (Gdx.graphics.getWidth() / 2) && jugador.getCollisionRectPlayer().x > (Gdx.graphics.getWidth() / 2)){
            System.out.println("PRIMERA CONDICION XXXXXXXXXXX");
            stage.getViewport().getCamera().position.set(jugador.getCollisionRectPlayer().x, stage.getViewport().getCamera().position.y, 0);
        }
        if(jugador.getCollisionRectPlayer().y < background.getHeight() - (Gdx.graphics.getHeight() / 2) && jugador.getCollisionRectPlayer().y > (Gdx.graphics.getHeight() / 2)) {
            stage.getViewport().getCamera().position.set(stage.getViewport().getCamera().position.x, jugador.getCollisionRectPlayer().y, 0);
            System.out.println("PRIMERA CONDICION YYYYYYYYYYY");
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
