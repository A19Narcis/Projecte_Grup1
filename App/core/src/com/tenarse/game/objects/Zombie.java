package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
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
    private float oldX, oldY;
    private int width, height;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;
    private TextureRegion[] animacionSpawn;

    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private Map map;

    boolean spawned;

    public Zombie(int width, int height, Map map) {
        this.width = width;
        this.height = height;
        this.map = map;
        position = new Vector2();
        //createSpawnPosition();
        position.x = map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2);
        position.y = map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2);

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

    public void createSpawnPosition(){
        do {
            this.position.x = getRandomIntInclusive((int)(64), (map.getMapWidthInPixels() - 64));
            this.position.y = getRandomIntInclusive((int)(64), (map.getMapHeightInPixels() - 64));
        } while (map.searchColision(this.position.x, this.position.y));
    }

    private TextureRegion getZombieDirection() {
        TextureRegion result = null;
        if(!spawned){
            result = AssetManager.zombieSpawn_Animation[currentFrame];
        }
        else{
            this.position.x -= Settings.ZOMBIE_VELOCITY * Gdx.graphics.getDeltaTime();
            if(oldX < this.position.x){
                result = AssetManager.zombieRight_Animation[currentFrame];
            }else if(oldX > this.position.x){
                result = AssetManager.zombieLeft_Animation[currentFrame];
            }else if(oldY < this.position.y){
                result = AssetManager.zombieLeft_Animation[currentFrame];
            }else if(oldY > this.position.y){
                result = AssetManager.zombieLeft_Animation[currentFrame];
            }

        }
        return result;
    }

    public void act(float delta){
        if(!spawned){
            stateTime += delta;
            if (stateTime >= frameTime){
                currentFrame++;
                if (currentFrame >= animacionSpawn.length){
                    spawned = true;
                    currentFrame = 0;
                }
                stateTime = 0;
            }
        }else{
            stateTime += delta;
            if (stateTime >= frameTime){
                currentFrame++;
                if (currentFrame >= animacionRight.length){
                    currentFrame = 0;
                }
                stateTime = 0;
            }
        }
    }

    public void draw(Batch batch, float parentAlpha){
        batch.draw(getZombieDirection(), this.position.x, this.position.y, width, height);
    }
}
