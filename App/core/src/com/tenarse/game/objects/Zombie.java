package com.tenarse.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class Zombie extends Actor{
    private Vector2 position;
    private int width, height;
    private MapProperties mapProperties;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;
    private TextureRegion[] animacionSpawn;

    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private TiledMapTileLayer mapLayer;

    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    boolean spawned;

    public Zombie(int width, int height, MapProperties mapProperties) {
        this.width = width;
        this.height = height;
        this.mapProperties = mapProperties;
        position = createSpawnPosition();
        System.out.println(this.position.x+ ", " +this.position.y);

        animacionRight = AssetManager.zombieRight_Animation;
        animacionLeft = AssetManager.zombieLeft_Animation;
        animacionUp = AssetManager.zombieUp_Animation;
        animacionDown = AssetManager.zombieDown_Animation;
        animacionSpawn = AssetManager.zombieSpawn_Animation;

        spawned = false;
    }

    private int getRandomIntInclusive(int min, int max) {
        min = (int) Math.ceil(min);
        max = (int) Math.floor(max);
        return (int) Math.floor(Math.random() * (max - min + 1) + min); // The maximum is inclusive and the minimum is inclusive
    }

    public Vector2 createSpawnPosition(){
        Vector2 position = new Vector2();
        tileWidth         = mapProperties.get("tilewidth", Integer.class);
        tileHeight        = mapProperties.get("tileheight", Integer.class);
        mapWidthInTiles   = mapProperties.get("width", Integer.class);
        mapHeightInTiles  = mapProperties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        position.x = getRandomIntInclusive((int)(64), (mapWidthInPixels - 64));
        position.y = getRandomIntInclusive((int)(64), (mapHeightInPixels - 64));
        System.out.println(position.x + ", " + position.y);
        /*do {

        } while (searchColision(this.mapLayer));*/
        return position;
    }

    private boolean searchColision(TiledMapTileLayer mapLayer ) {
        int cellX = (int) ((this.position.x + (Settings.PLAYER_WIDTH / 2)) / tileWidth);
        int cellY = (int) ((this.position.y + (Settings.PLAYER_HEIGHT / 2)) / tileHeight);
        return mapLayer.getCell(cellX, cellY).getTile().getProperties().containsKey("colisionable");
    }

    private TextureRegion getZombieDirection() {
        if(!spawned){
            return AssetManager.zombieSpawn_Animation[currentFrame];
        }
        else{
            return AssetManager.zombieDown;
        }
    }

    public void act(float delta){
        stateTime += delta;
        if (stateTime >= frameTime){
            currentFrame++;
            if (currentFrame >= animacionSpawn.length){
                spawned = true;
                currentFrame = 0;
            }
            stateTime = 0;
        }
    }

    public TiledMapTileLayer getMapLayer() {
        return mapLayer;
    }

    public void setMapLayer(TiledMapTileLayer mapLayer) {
        this.mapLayer = mapLayer;
    }

    public void draw(Batch batch, float parentAlpha){
        batch.draw(getZombieDirection(), this.position.x, this.position.y, width, height);
    }
}
