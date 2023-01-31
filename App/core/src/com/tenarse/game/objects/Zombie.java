package com.tenarse.game.objects;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.tenarse.game.utils.Settings;

public class Zombie {
    private Vector2 position;
    private int width, height;
    private MapProperties mapProperties;
    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    public Zombie(int width, int height, MapProperties mapProperties) {
        /*Vector2 startPosition = createSpawnPosition();
        this.position.x = startPosition.x;
        this.position.y = startPosition.y;*/
        this.position.x = 5;
        this.position.y = 5;
        this.width = width;
        this.height = height;
        this.mapProperties = mapProperties;
    }

    public Vector2 createSpawnPosition(){
        Vector2 position = new Vector2();
        tileWidth         = mapProperties.get("tilewidth", Integer.class);
        tileHeight        = mapProperties.get("tileheight", Integer.class);
        mapWidthInTiles   = mapProperties.get("width", Integer.class);
        mapHeightInTiles  = mapProperties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        position.x = (int) Math.random()* (mapWidthInPixels - 5) + 5;
        position.y = (int) Math.random()* (mapHeightInPixels - 5) + 5;
        return position;
    }
}
