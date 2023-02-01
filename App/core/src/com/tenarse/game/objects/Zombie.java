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

    TextureRegion zombieDir;

    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    public Zombie(int width, int height, MapProperties mapProperties) {
        this.width = width;
        this.height = height;
        this.mapProperties = mapProperties;
        Vector2 startPosition = createSpawnPosition();
        /*this.position.x = startPosition.x;
        this.position.y = startPosition.y;*/
        this.position = new Vector2();
        this.position.x = getRandomIntInclusive((int)(mapWidthInPixels / 2 - (Settings.ZOMBIE_WIDTH / 2) - 100), (int)(mapWidthInPixels / 2 - (Settings.ZOMBIE_WIDTH / 2) + 100));//Math.random()* (mapWidthInPixels / 2 - (Settings.ZOMBIE_WIDTH / 2) - 100) - (mapWidthInPixels / 2 - (Settings.ZOMBIE_WIDTH / 2) + 100);
        this.position.y = getRandomIntInclusive((int)(mapHeightInPixels / 2 - (Settings.ZOMBIE_WIDTH / 2) - 100), (int)(mapHeightInPixels / 2 - (Settings.ZOMBIE_WIDTH / 2) + 100));//(int) Math.random()* (mapHeightInPixels / 2 - (Settings.ZOMBIE_HEIGHT / 2) - 100) + (mapHeightInPixels / 2 - (Settings.ZOMBIE_HEIGHT / 2) + 100);
        System.out.println(this.position.x+ ", " +this.position.y);



        animacionRight = AssetManager.zombieRight_Animation;
        animacionLeft = AssetManager.zombieLeft_Animation;
        animacionUp = AssetManager.zombieUp_Animation;
        animacionDown = AssetManager.zombieDown_Animation;


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
        position.x = (int) Math.random()* (mapWidthInPixels - 64) + 64;
        position.y = (int) Math.random()* (mapHeightInPixels - 64) + 64;
        return position;
    }

    private TextureRegion getZombieDirection() {
        return AssetManager.zombieDown;
    }

    public void act(float delta){

    }

    public void draw(Batch batch, float parentAlpha){
        batch.draw(getZombieDirection(), this.position.x, this.position.y, width, height);
    }
}
