package com.tenarse.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.tenarse.game.effects.HitEffect;
import com.tenarse.game.effects.PoolBlood;
import com.tenarse.game.helpers.AMSprites;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

import java.util.ArrayList;

public class Zombie extends Actor{
    private Vector2 position;
    private float oldX, oldY;
    private int width, height;
    private int direction = 3;

    private int vida;
    private boolean dead;
    private long timeHited;
    private boolean hited = false;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;
    private TextureRegion[] animacionSpawn;
    private TextureRegion[] animacionDead;
    private TextureRegion[] animacionAtackL, animacionAtackR, animacionAtackU, animacionAtackD;


    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private Map map;

    private Rectangle rectanguloDeteccion;

    private AMSprites playerSprites;

    private boolean spawned;
    private boolean colision;
    private boolean detected;
    private boolean oldColisionPlayer;
    private boolean attack;
    private boolean firstAnimationAttack;
    private boolean doDamage;

    private int damage;
    private int velocity;
    private int points;

    private long timeColisoningPlayer;

    private int tipoZombie;

    private Jugador presa;

    public Zombie(float x, float y, int width, int height, Map map, int tipoZombie){
        this(width, height, map, tipoZombie);
        this.position = new Vector2(x,y);
        rectanguloDeteccion = new Rectangle();
        rectanguloDeteccion.width = Settings.ZOMBIE_WIDTH;
        rectanguloDeteccion.height = Settings.ZOMBIE_HEIGHT;
        rectanguloDeteccion.setPosition(x, y);
    }

    public Zombie(int width, int height, Map map, final int tipoZombie) {
        this.width = width;
        this.height = height;
        this.map = map;
        this.tipoZombie = tipoZombie;
        if(position == null) {
            position = new Vector2();
            createSpawnPosition();
            //position.x = map.getMapWidthInPixels() / 2 - (Settings.PLAYER_WIDTH / 2) - 20; //SPAWN EN EL CENTRO PARA PRUEBAS
            //position.y = map.getMapHeightInPixels() / 2 - (Settings.PLAYER_WIDTH / 2);
        }

        dead = false;
        attack = false;
        firstAnimationAttack = false;
        doDamage = false;

        if(rectanguloDeteccion == null) {
            rectanguloDeteccion = new Rectangle();
            rectanguloDeteccion.width = Settings.ZOMBIE_WIDTH;
            rectanguloDeteccion.height = Settings.ZOMBIE_HEIGHT;
        }

        playerSprites = AssetManager.SpritesPlayers.get(tipoZombie + 2);

        animacionRight = playerSprites.M_R;
        animacionLeft = playerSprites.M_L;
        animacionUp = playerSprites.M_U;
        animacionDown = playerSprites.M_D;
        animacionSpawn = playerSprites.Spawn;
        animacionDead = playerSprites.Dead;
        animacionAtackL = playerSprites.A_L;
        animacionAtackR = playerSprites.A_R;
        animacionAtackU = playerSprites.A_U;
        animacionAtackD = playerSprites.A_D;
        damage = AssetManager.fullStats.getJSONObject(tipoZombie + 2).getInt("fuerza");
        velocity = AssetManager.fullStats.getJSONObject(tipoZombie + 2).getInt("velocidad") * 20;
        points = AssetManager.fullStats.getJSONObject(tipoZombie + 2).getInt("puntos");
        vida = AssetManager.fullStats.getJSONObject(tipoZombie + 2).getInt("vida");

        spawned = false;
        colision = false;
    }

    public int getKillPoints() {
        return points;
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

    public void calculateMovement(float delta) {
        if (spawned && !hited) {
            float oldX = position.x;
            float oldY = position.y;
            int colisionMov;
                Vector2 direction = new Vector2(presa.position.x - this.position.x, presa.position.y - this.position.y);
                direction.nor();
                position.x += direction.x * velocity * delta;
                if (oldX < position.x) {
                    colisionMov = 8;
                } else {
                    colisionMov = -8;
                }
                position.x += colisionMov;
                if (map.searchColision(position.x, position.y) || colision) {
                    position.x = oldX;
                } else {
                    position.x -= colisionMov;
                }


                position.y += direction.y * velocity * delta;
                if (oldY < position.y) {
                    colisionMov = 8;
                } else {
                    colisionMov = -8;
                }
                position.y += colisionMov;
                if (map.searchColision(position.x, position.y) || colision) {
                    position.y = oldY;
                } else {
                    position.y -= colisionMov;
                }
            rectanguloDeteccion.x = this.position.x;
            rectanguloDeteccion.y = this.position.y;
        }
    }

    public void colisionWithZombie(Zombie zombie){
            float calculoX = zombie.getRectanguloDeteccion().x - rectanguloDeteccion.x;
            float calculoY = zombie.getRectanguloDeteccion().y - rectanguloDeteccion.y;
            switch (direction){
                case Settings.PRESSED_UP:
                    if ((calculoY > 0 && calculoY < 20) && (calculoX > -12 && calculoX < 12)) {
                        colision = true;
                    }
                    break;
                case Settings.PRESSED_LEFT:
                    if ((calculoY > -12 && calculoY < 12) && (calculoX > -20 && calculoX < 0)) {
                        colision = true;
                    }
                    break;
                case Settings.PRESSED_DOWN:
                    if ((calculoY > -20 && calculoY < 0) && (calculoX > -12 && calculoX < 12)) {
                        colision = true;
                    }
                    break;
                case Settings.PRESSED_RIGHT:
                    if ((calculoY > -12 && calculoY < 12) && (calculoX > 0 && calculoX < 20)) {
                        colision = true;
                    }
                    break;
            }
            zombie.setDetected(colision);
    }

    public int colisionWithPlayer() {
        int damageDone = 0;
        if (!colision) {
            boolean result;
            float calculoX = presa.getCollisionRectPlayer().x - rectanguloDeteccion.x;
            float calculoY = presa.getCollisionRectPlayer().y - rectanguloDeteccion.y;
            if (calculoX < 17 && calculoX > -17) {
                if (calculoY < 24 && calculoY > -24) {
                    result = true;
                } else {
                    result = false;
                }
            } else {
                result = false;
            }
            if(!oldColisionPlayer){
                timeColisoningPlayer = TimeUtils.nanoTime();
            }
            oldColisionPlayer = result;
            colision = result;
            if(oldColisionPlayer && TimeUtils.nanoTime() - timeColisoningPlayer > Settings.ZOMBIE_HIT_DELAY && !doDamage && vida > 0){
                attack = true;
                firstAnimationAttack = true;
                doDamage = true;
                damageDone = damage;
                presa.setDamage(damage);
            }
        }
        return damageDone;
    }

    private TextureRegion getZombieAnimation() {
        TextureRegion result = null;
        if(vida > 0) {
            if (!spawned) {
                result = animacionSpawn[currentFrame];
                oldX = this.position.x;
                oldY = this.position.y;
            } else {
                switch (direction) {
                    case Settings.PRESSED_UP:
                        result = animacionUp[0];
                        if(attack && !firstAnimationAttack){
                            result = animacionAtackU[currentFrame];
                        }
                        break;
                    case Settings.PRESSED_LEFT:
                        result = animacionLeft[0];
                        if(attack && !firstAnimationAttack){
                            result = animacionAtackL[currentFrame];
                        }
                        break;
                    case Settings.PRESSED_DOWN:
                        result = animacionDown[0];
                        if(attack && !firstAnimationAttack){
                            result = animacionAtackD[currentFrame];
                        }
                        break;
                    case Settings.PRESSED_RIGHT:
                        result = animacionRight[0];
                        if(attack && !firstAnimationAttack){
                            result = animacionAtackR[currentFrame];
                        }
                        break;
                }


                if (oldX < this.position.x) {
                    result = animacionRight[currentFrame];
                    direction = Settings.PRESSED_RIGHT;
                } else if (oldX > this.position.x) {
                    result = animacionLeft[currentFrame];
                    direction = Settings.PRESSED_LEFT;
                } else if (oldY < this.position.y) {
                    result = animacionUp[currentFrame];
                    direction = Settings.PRESSED_UP;
                } else if (oldY > this.position.y) {
                    result = animacionDown[currentFrame];
                    direction = Settings.PRESSED_DOWN;
                }
                oldX = this.position.x;
                oldY = this.position.y;

            }
        }else{
            result = animacionDead[currentFrame];
        }
        return result;
    }

    public Rectangle getRectanguloDeteccion() {
        return rectanguloDeteccion;
    }

    public void act(float delta){
        colision = false;
        detected = false;
            if (vida > 0) {
                if (!spawned) {
                    stateTime += delta;
                    if (stateTime >= frameTime) {
                        currentFrame++;
                        if (currentFrame >= animacionSpawn.length) {
                            spawned = true;
                            currentFrame = 0;
                        }
                        stateTime = 0;
                    }
                } else {

                    if(attack){
                        if(firstAnimationAttack){
                            currentFrame = 0;
                            firstAnimationAttack = false;
                        }
                        stateTime += delta;
                        if (stateTime >= frameTime) {
                            currentFrame++;
                            if (currentFrame == 1){
                                AssetManager.zombiePunch.play();
                            }
                            if (currentFrame >= animacionAtackU.length) {
                                currentFrame = 0;
                                attack = false;
                                oldColisionPlayer = false;
                                doDamage = false;
                            }
                            stateTime = 0;
                        }
                    }else {
                        stateTime += delta;
                        if (stateTime >= frameTime) {
                            currentFrame++;
                            if (currentFrame >= animacionRight.length) {
                                currentFrame = 0;
                            }
                            stateTime = 0;
                        }
                    }
                }
            } else {
                stateTime += delta;
                if (stateTime >= frameTime) {
                    currentFrame++;
                    if (currentFrame >= animacionDead.length) {
                        currentFrame = 0;
                        remove();
                        dead = true;
                    }
                    stateTime = 0;
                }
            }
        if(hited && TimeUtils.nanoTime() - timeHited > Settings.ZOMBIE_HIT_DELAY){
            hited = false;
        }
    }

    public void draw(Batch batch, float parentAlpha){
        batch.draw(getZombieAnimation(), this.position.x, this.position.y, width, height);
    }

    public void setDamage(int damage) {
        this.vida -= damage;
        this.position.x = oldX;
        this.position.y = oldY;
        getStage().addActor(new HitEffect(this.position));
    }

    public void die(int direction) {
        if(vida <= 0) {
            currentFrame = 0;
            hited = true;
            timeHited = TimeUtils.nanoTime();
            PoolBlood pool = new PoolBlood(this.position, direction);
            getStage().addActor(pool);
            pool.toBack();
            AssetManager.dieSound.play();
        }
    }

    public boolean isDead() {
        return dead;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
        rectanguloDeteccion.x = this.position.x;
        rectanguloDeteccion.y = this.position.y;
    }

    public boolean isDetected() {
        return detected;
    }

    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    public int getDirection() {
        return direction;
    }

    public int getTipoZombie() {
        return tipoZombie;
    }

    public void instaKill() {
        this.vida = 0;
        currentFrame = 0;
        PoolBlood pool = new PoolBlood(this.position, direction);
        getStage().addActor(pool);
        pool.toBack();
    }

    public void focus(ArrayList<Jugador> players) {
        float minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < players.size(); i++) {
            float distanceX = Math.abs(players.get(i).position.x - this.position.x);
            float distanceY = Math.abs(players.get(i).position.y - this.position.y);
            float actualDistance = distanceX + distanceY;
            if (actualDistance < minDistance) {
                minDistance = actualDistance;
                presa = players.get(i);
            }
        }
    }

    public Jugador getPresa() {
        return presa;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
        this.doDamage = attack;
        if(attack) {
            currentFrame = 0;
        }
    }
}
