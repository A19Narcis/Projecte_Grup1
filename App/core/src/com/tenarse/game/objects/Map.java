package com.tenarse.game.objects;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.tenarse.game.utils.Settings;

public class Map {

    private TiledMap map;
    private MapProperties properties;
    private int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;
    private TiledMapTileLayer mapLayer;

    public Map() {
    }

    public Map(TiledMap map) {
        this.map = map;
        properties = map.getProperties();
        tileWidth         = properties.get("tilewidth", Integer.class);
        tileHeight        = properties.get("tileheight", Integer.class);
        mapWidthInTiles   = properties.get("width", Integer.class);
        mapHeightInTiles  = properties.get("height", Integer.class);
        mapWidthInPixels  = mapWidthInTiles  * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
        mapLayer = (TiledMapTileLayer) map.getLayers().get("Ground");
    }

    public TiledMap getMap() {
        return map;
    }

    public MapProperties getProperties() {
        return properties;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getMapWidthInTiles() {
        return mapWidthInTiles;
    }

    public int getMapHeightInTiles() {
        return mapHeightInTiles;
    }

    public int getMapWidthInPixels() {
        return mapWidthInPixels;
    }

    public int getMapHeightInPixels() {
        return mapHeightInPixels;
    }

    public TiledMapTileLayer getMapLayer() {
        return mapLayer;
    }

    public boolean searchColision(float positionX, float positionY) {
        int cellX = (int) ((positionX + (Settings.PLAYER_WIDTH / 2)) / this.getTileWidth());
        int cellY = (int) ((positionY + (Settings.PLAYER_HEIGHT / 2)) / this.getTileHeight());
        return this.getMapLayer().getCell(cellX, cellY).getTile().getProperties().containsKey("colisionable");
    }
}
