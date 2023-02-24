package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tenarse.game.Tenarse;
import com.tenarse.game.bonus.Bonus;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.helpers.SpawnInterval;
import com.tenarse.game.objects.Arrow;
import com.tenarse.game.objects.ConnectionNode;
import com.tenarse.game.objects.ContadorTiempo;
import com.tenarse.game.objects.Hud;
import com.tenarse.game.objects.Jugador;
import com.tenarse.game.objects.JugadorOnline;
import com.tenarse.game.objects.Map;
import com.tenarse.game.objects.Zombie;
import com.tenarse.game.utils.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MultiplayerGameScreen implements Screen {

    private EndGameDialog dialog;

    private ContadorTiempo contadorTiempo;

    private Boolean buttonUpPressed = false;
    private Boolean buttonDownPressed = false;
    private Boolean buttonLeftPressed = false;
    private Boolean buttonRightPressed = false;

    private Boolean buttonAttackPressed = false;

    private String username;

    private Stage stage;
    private Jugador jugador;
    private JugadorOnline jugador2;

    private int bonusPoints = 1;

    private int zoomAndroid;
    private int zoomPc;

    public Map map;

    private Texture btnUpTexture, btnDownTexture, btnLeftTexture, btnRightTexture, btnAtacarTexture, corazonesTexture, armaduraTexture;
    private ImageButton btnU_img, btnD_img, btnL_img, btnR_img, btn_atacar, hp_player, armor_player;
    private ArrayList<ImageButton> corazonesArray = new ArrayList<>();
    private ArrayList<ImageButton> armorArray = new ArrayList<>();

    private ShapeRenderer shapeRenderer;

    private OrthographicCamera camera;

    private OrthogonalTiledMapRenderer renderer;
    private int puntosPartida;
    private BitmapFont fontBold;

    long lastZombieTime = 0;
    long lastBossTime = 0;

    float delta;

    ArrayList<Zombie> enemies = new ArrayList<>();
    ArrayList<Jugador> players = new ArrayList<>();
    ArrayList<Arrow> arrowList = new ArrayList<>();
    ArrayList<Bonus> bonusList = new ArrayList<>();

    private Socket socket;

    private Hud hud;

    private Tenarse game;

    private long tiempoBonusPoints;

    public MultiplayerGameScreen(Tenarse game, Batch prevBatch, Viewport prevViewport, String username, int tipus, int selectedMap) {

        try {
            socket = IO.socket("http://192.168.2.127:7074/");
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected");
                }
            }).on("socketID", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try{
                        String id = data.getString("id");
                        System.out.println("My socket ID: " + id);
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                }
            }).on("getPlayers", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONArray objects = (JSONArray) args[0];
                    System.out.println(objects);
                    try {
                        for (int i = 0; i < objects.length(); i++) {
                            System.out.println(objects.getJSONObject(i));
                        }
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                    if(objects.length() != 0) {
                        for (int i = 0; i < objects.length(); i++) {
                            jugador2 = new JugadorOnline((float)objects.getJSONObject(i).getInt("x"), (float)objects.getJSONObject(i).getInt("y"), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, objects.getJSONObject(i).getInt("tipo"), map);
                            players.add(jugador2);
                            stage.addActor(jugador2);
                        }
                    }
                }
            }).on("new_player", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try{
                        String id = data.getString("id");
                        System.out.println(data);
                        System.out.println("New Player: " + id);
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                    jugador2 = new JugadorOnline((float)data.getInt("xPl"), (float)data.getInt("yPl"), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, data.getInt("tipoPl"), map);
                    players.add(jugador2);
                    stage.addActor(jugador2);
                }
            }).on("coorNuevas", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try{
                        jugador2.setOldx(jugador2.getPosition().x);
                        jugador2.setOldy(jugador2.getPosition().y);
                        jugador2.setPosition((float)data.getInt("x"), (float)data.getInt("y"));
                        jugador2.setDirection(data.getInt("direccion"));
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                }
            }).on("player_disc", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try{
                        String id = data.getString("id");
                        System.out.println("Disconnected Socket: " + id);
                        jugador2.remove();
                        players.remove(jugador2);
                    } catch (JSONException e){
                        System.out.println(e);
                    }
                }
            });
        } catch (URISyntaxException e) {
            Gdx.app.error("SocketIO", e.getMessage());
        }


        this.game = game;

        contadorTiempo = new ContadorTiempo();

        this.username = username;

        shapeRenderer = new ShapeRenderer();

        zoomAndroid = 6;
        zoomPc = 3;

        if(selectedMap == 0){
            map = new Map(AssetManager.map1);
        }else if(selectedMap == 1){
            map = new Map(AssetManager.map2);
        }



        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        prevViewport.setCamera(camera);


        renderer = new OrthogonalTiledMapRenderer(map.getMap());

        if (tipus == 4){
            jugador = new Jugador(map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, "player", 1, map);
            players.add(jugador);
        } else if (tipus == 5){
            jugador = new Jugador(map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, "player", 2, map);
            players.add(jugador);
        } else if (tipus == 6){
            jugador = new Jugador(map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2), Settings.PLAYER_WIDTH, Settings.PLAYER_HEIGHT, "player", 3, map);
            players.add(jugador);
        }

        socket.emit("newStatsPlayer", jugador.getPosition().x, jugador.getPosition().y, jugador.getTipusJugador(), jugador.getDirection());

        //Crear stage
        stage = new Stage(prevViewport, prevBatch);

        //Añadir Actores
        jugador.setName("jugador");
        stage.addActor(jugador);



        /*Zombie zombie = new Zombie(Settings.ZOMBIE_WIDTH, Settings.ZOMBIE_HEIGHT, map, 1);
        enemies.add(zombie);
        stage.addActor(zombie);
        Zombie zombie2 = new Zombie(Settings.ZOMBIE_WIDTH, Settings.ZOMBIE_HEIGHT, map, 1);
        enemies.add(zombie2);
        stage.addActor(zombie2);
        Zombie zombie3 = new Zombie(Settings.ZOMBIE_WIDTH, Settings.ZOMBIE_HEIGHT, map, 1);
        enemies.add(zombie3);
        stage.addActor(zombie3);*/

        corazonesTexture = AssetManager.hp_player;
        armaduraTexture = AssetManager.armor_player;

        Skin skin = AssetManager.skinTextBox;
        dialog = new EndGameDialog("Fin de la partida", skin, puntosPartida, jugador.getKillsJugador(), game);
        dialog.toFront();



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

            dialog.setScale(0.8f);
        }


        for (int i = 1; i <= jugador.getVida(); i++) {
            hp_player = new ImageButton(new TextureRegionDrawable(new TextureRegion(corazonesTexture)));
            hp_player.setSize(12,12);
            corazonesArray.add(hp_player);
            stage.addActor(hp_player);

        }

        for (int i = 0; i < jugador.getArmadura(); i++) {
            armor_player = new ImageButton(new TextureRegionDrawable(new TextureRegion(armaduraTexture)));
            armor_player.setSize(12, 12);
            armorArray.add(armor_player);
            stage.addActor(armor_player);
        }

        //Texto de los personajes
        FreeTypeFontGenerator.FreeTypeFontParameter parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();

        if (Gdx.app.getType() == Application.ApplicationType.Android){
            parametros.size = 30;
        }

        fontBold = AssetManager.fontTextBold.generateFont(parametros);

        hud = new Hud(stage.getBatch());

        stage.addActor(hud);

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
        this.delta = delta;

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

        socket.emit("coorJugador", jugador.getPosition().x, jugador.getPosition().y, jugador.getDirection());

        for (Zombie zombie1: enemies){
            if(!zombie1.isDetected()) {
                for (Zombie zombie2 : enemies) {
                    if (!zombie2.isDetected()) {
                        if (zombie1 != zombie2) {
                            zombie1.colisionWithZombie(zombie2);
                        }
                    }
                }
            }
        }

        for (Jugador player: players){
            arrowList = player.getArrowList();
            for (Zombie zombie: enemies) {
                int atacado = zombie.colisionWithPlayer(player);
                if(atacado > 0){
                        for (int i = atacado; i > 0 && corazonesArray.size() > 0; i--) {
                            if(armorArray.size() > 0) {
                                armorArray.get(armorArray.size() - 1).remove(); //Remove actor
                                armorArray.remove(armorArray.size() - 1); //Remove array
                            }else{
                                corazonesArray.get(corazonesArray.size() - 1).remove();
                                corazonesArray.remove(corazonesArray.size() - 1);
                            }
                        }
                }
                zombie.calculateMovement(player.getCollisionRectPlayer(), delta);
                player.attacking(zombie, delta);
            }
        }

        for (Jugador player: players){
            arrowList= player.getArrowList();
            for(int i = 0; i < arrowList.size() && arrowList.size() > 0; i++){
                if(enemies.size() <= 0) {
                    arrowList.get(i).remove();
                    arrowList.clear();
                }else {
                    arrowList.get(i).move(delta);
                }
            }
            for (int i = 0; i < bonusList.size(); i++) {
                if(bonusList.get(i).isDelete()){
                    bonusList.remove(i);
                    i--;
                }else {
                    if (bonusList.get(i).isActive() && bonusList.get(i).getColisionRectangle().overlaps(player.getCollisionRectPlayer()) && jugador.getVida() > 0) {
                        if (bonusList.get(i).getGemType() == Settings.BONUS_LIVE) {
                            hp_player = new ImageButton(new TextureRegionDrawable(new TextureRegion(corazonesTexture)));
                            hp_player.setSize(12, 12);
                            corazonesArray.add(hp_player);
                            stage.addActor(hp_player);
                            player.subirVida();
                        } else if (bonusList.get(i).getGemType() == Settings.BONUS_VELOCITY) {
                            player.subirVelocidad();
                        } else if (bonusList.get(i).getGemType() == Settings.BONUS_DAMAGE) {
                            player.subirFuerza();
                        } else if (bonusList.get(i).getGemType() == Settings.BONUS_SHIELD) {
                            armor_player = new ImageButton(new TextureRegionDrawable(new TextureRegion(armaduraTexture)));
                            armor_player.setSize(12, 12);
                            armorArray.add(armor_player);
                            stage.addActor(armor_player);
                            player.subirArmadura();
                        } else if (bonusList.get(i).getGemType() == Settings.BONUS_POINTS){
                            bonusPoints++;
                            tiempoBonusPoints = TimeUtils.nanoTime();
                        }
                        bonusList.get(i).remove();
                        bonusList.remove(bonusList.get(i));
                        i--;
                    }
                }
            }
        }


        for (int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).isDead()){
                if(MathUtils.random() < Settings.BONUS_POSIBILITY) {
                    Bonus bonus = new Bonus(enemies.get(i).getPosition());
                    bonusList.add(bonus);
                    getStage().addActor(bonus);
                }
                if (TimeUtils.nanoTime() - tiempoBonusPoints >= Settings.B_POINTS_TIMEOUT && bonusPoints > 1){
                    bonusPoints--;
                    tiempoBonusPoints = TimeUtils.nanoTime();
                }
                puntosPartida = puntosPartida + enemies.get(i).getKillPoints() * bonusPoints;
                enemies.remove(enemies.get(i));
                jugador.unaKillMas();
                hud.getScoreLabel().setText(puntosPartida);
                i--;
            }
        }

        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).isDead()){
                for (int j = 0; j < enemies.size(); j++) {
                    players.get(i).die(enemies.get(i).getDirection());
                }
                players.remove(players.get(i));
                if (players.size() == 0){
                    //Enviar POST de addNewPartida
                    contadorTiempo.detener();
                    String tiempo = contadorTiempo.getTiempo();
                    ConnectionNode nodeJS = new ConnectionNode();
                    //nodeJS.addNewPartida(this.username, jugador.getTypePlayer(), jugador.getKillsJugador(), tiempo, puntosPartida);
                }
                i--;
            }
        }

        if (players.size() > 0){
            spawnEnemies();
        } else {
            dialog.setZIndex(150);
            stage.addActor(dialog);
            dialog.getTexto().setText("Partida terminada\n\nPuntos: " + puntosPartida + "\nKills: " + jugador.getKillsJugador());
            if (Gdx.app.getType() == Application.ApplicationType.Android) {
                dialog.getTitleLabel().setFontScale(0.90f);
                dialog.setPosition(jugador.getCollisionRectPlayer().x - dialog.getWidth() / 2.5f , jugador.getCollisionRectPlayer().y - 60);
            } else {
                dialog.setPosition(jugador.getCollisionRectPlayer().x - 35 , jugador.getCollisionRectPlayer().y - 50);
            }
        }

        if (buttonAttackPressed) {
            jugador.startAttack();
        } else {
            jugador.stopAttack();
        }


        //1 - 345 ; 2 - 335 ; 3 - 325

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            btnU_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 20, camera.position.y - camera.viewportHeight / 2 + 40);
            btnD_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 20, camera.position.y - camera.viewportHeight / 2);
            btnL_img.setPosition(camera.position.x - camera.viewportWidth / 2, camera.position.y - camera.viewportHeight / 2 + 20);
            btnR_img.setPosition(camera.position.x - camera.viewportWidth / 2 + 40, camera.position.y - camera.viewportHeight / 2 + 20);

            btn_atacar.setPosition(camera.position.x + camera.viewportWidth / 2 - 50, camera.position.y - camera.viewportHeight / 2 + 10);

            btn_atacar.toFront();
            btnD_img.toFront();
            btnL_img.toFront();
            btnR_img.toFront();
            btnU_img.toFront();
        }

        for (int i = 1; i <= corazonesArray.size(); i++) {
            corazonesArray.get(i-1).setPosition(camera.position.x - (camera.viewportWidth / 2 + 10) + 15 * i, camera.position.y + camera.viewportHeight / 2 - 20);
            corazonesArray.get(i-1).setZIndex(100);
        }
        for (int i = 1; i <= armorArray.size(); i++) {
            armorArray.get(i-1).setPosition(camera.position.x - (camera.viewportWidth / 2 + 10) + 15 * i, camera.position.y + camera.viewportHeight / 2 - 33);
            armorArray.get(i-1).setZIndex(100);
        }



        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        hud.stage.draw();
    }

    private void spawnEnemies() {
        ArrayList<SpawnInterval> intervals = AssetManager.ZombiesInterval;
        for (int i = 0; i < intervals.size(); i++) {
            if (TimeUtils.nanoTime() - intervals.get(i).time > intervals.get(i).interval) {
                Zombie zombie = new Zombie(Settings.ZOMBIE_WIDTH, Settings.ZOMBIE_HEIGHT, map, i + 1);
                enemies.add(zombie);
                stage.addActor(zombie);
                zombie.toBack();
                intervals.get(i).time = TimeUtils.nanoTime();
            }
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
        this.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public ArrayList<Zombie> getEnemies() {
        return enemies;
    }

    public ArrayList<Jugador> getPlayers() {
        return players;
    }
}
