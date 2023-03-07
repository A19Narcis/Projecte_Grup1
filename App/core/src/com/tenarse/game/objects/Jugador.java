package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.TimeUtils;
import com.tenarse.game.effects.HitEffect;
import com.tenarse.game.effects.PoolBlood;
import com.tenarse.game.helpers.AMSprites;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

import java.util.ArrayList;

public class Jugador extends Actor {

    protected final int Crossbow_PLAYER = 1;
    protected final int WAR_PLAYER = 2;
    protected final int SHI_PLAYER = 3;

    protected final int Crossbow_PLAYER_STAND = 4;
    protected final int WAR_PLAYER_STAND = 5;
    protected final int SHI_PLAYER_STAND = 6;

    protected int direction = 3;
    protected boolean attack;
    protected boolean firstAnimationAttack;
    protected boolean firstAnimationDie;
    protected boolean doDamage;
    protected Vector2 position;
    protected int width, height;
    protected String categoria;
    protected int tipusJugador;
    protected int killsJugador;

    protected Boolean bntUpIsPressed = false;
    protected Boolean bntDownIsPressed = false;
    protected Boolean bntLeftIsPressed = false;
    protected Boolean bntRightIsPressed = false;
    protected Boolean bntAttackPressed = false;
    protected boolean isDead;

    protected TextureRegion[] animacionRight;
    protected TextureRegion[] animacionUp;
    protected TextureRegion[] animacionDown;
    protected TextureRegion[] animacionLeft;

    protected TextureRegion[] animacionAtaqueRight;
    protected TextureRegion[] animacionAtaqueLeft;
    protected TextureRegion[] animacionAtaqueUp;
    protected TextureRegion[] animacionAtaqueDown;
    protected TextureRegion[] animacionMuerte;
    protected long attackDelay;

    protected AMSprites playerSprites;

    protected int currentFrame = 0;
    protected float frameTime = 0.1f;
    protected float stateTime = 0;

    protected float oldx;
    protected float oldy;

    protected int vida;
    protected int armadura;
    protected int velocidad;
    protected int fuerza;

    protected Map map;

    protected Rectangle collisionRectPlayer;

    protected ArrayList<Arrow> arrowList = new ArrayList<>();

    protected int bonusMultiplier [] = {0, 0, 0, 0};
    protected long timeoutVelocity;
    protected long timeoutDamage;

    protected String username;

    public Jugador(float x, float y, int width, int height, String categoria, int tipusJugador, Map map){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        this.attack = false;
        firstAnimationAttack = false;
        firstAnimationDie = true;
        doDamage = false;
        attackDelay = Settings.PLAYER_ATTACK_DELAY;
        this.map = map;

        this.categoria = categoria;

        collisionRectPlayer = new Rectangle();
        collisionRectPlayer.width = this.width;
        collisionRectPlayer.height = this.height;

        this.tipusJugador = tipusJugador;

        fuerza = AssetManager.fullStats.getJSONObject(this.tipusJugador - 1).getInt("fuerza");
        vida = AssetManager.fullStats.getJSONObject(this.tipusJugador - 1).getInt("vida");
        armadura = AssetManager.fullStats.getJSONObject(this.tipusJugador - 1).getInt("armadura");
        velocidad = AssetManager.fullStats.getJSONObject(this.tipusJugador - 1).getInt("velocidad") * 30;;
        isDead = false;

        playerSprites = AssetManager.SpritesPlayers.get(tipusJugador - 1);


        animacionRight = playerSprites.M_R;
        animacionLeft = playerSprites.M_L;
        animacionUp = playerSprites.M_U;
        animacionDown = playerSprites.M_D;
        animacionAtaqueRight = playerSprites.A_R;
        animacionAtaqueLeft = playerSprites.A_L;
        animacionAtaqueUp = playerSprites.A_U;
        animacionAtaqueDown = playerSprites.A_D;
        animacionMuerte = playerSprites.Dead;


        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

        public void act(float delta){
        super.act(delta);
        if(TimeUtils.nanoTime() - timeoutVelocity >= Settings.B_VELOCITY_TIMEOUT && bonusMultiplier[Settings.BONUS_VELOCITY] > 0){
            bonusMultiplier[Settings.BONUS_VELOCITY]--;
            timeoutVelocity = TimeUtils.nanoTime();
        }
        if(TimeUtils.nanoTime() - timeoutDamage >= Settings.B_VELOCITY_TIMEOUT && bonusMultiplier[Settings.BONUS_DAMAGE] > 0){
            bonusMultiplier[Settings.BONUS_DAMAGE]--;
            timeoutDamage = TimeUtils.nanoTime();
        }
        if(vida > 0) {
            if (this.categoria.equals("bot")) {
                this.position.x += 5;
                if (this.position.x >= Gdx.graphics.getWidth()) {
                    this.position.x = -400;
                }
            } else if (this.categoria.equals("player")) {
                oldx = this.position.x;
                oldy = this.position.y;
                if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || this.bntLeftIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_LEFT;
                    this.position.x -= (this.velocidad + (bonusMultiplier[Settings.BONUS_VELOCITY] * 10)) * Gdx.graphics.getDeltaTime();
                    this.position.x -= 8;
                    if (map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    } else {
                        this.position.x += 8;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || this.bntRightIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_RIGHT;
                    this.position.x += (this.velocidad + (bonusMultiplier[Settings.BONUS_VELOCITY] * 10)) * Gdx.graphics.getDeltaTime();
                    this.position.x += 8;
                    if (map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    } else {
                        this.position.x -= 8;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || this.bntUpIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_UP;
                    this.position.y += (this.velocidad + (bonusMultiplier[Settings.BONUS_VELOCITY] * 10)) * Gdx.graphics.getDeltaTime();
                    if (map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_DOWN;
                    this.position.y -= (this.velocidad + (bonusMultiplier[Settings.BONUS_VELOCITY] * 10)) * Gdx.graphics.getDeltaTime();
                    this.position.y -= 12;
                    if (map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    } else {
                        this.position.y += 12;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || bntAttackPressed) && !attack && TimeUtils.nanoTime() - attackDelay >= Settings.PLAYER_ATTACK_DELAY) {
                        attack = true;
                        firstAnimationAttack = true;
                        doDamage = true;
                }

                //Colision personaje con los bordes del mapa
                if (this.position.y <= 5) {
                    this.position.y = 5;
                }
                if (this.position.x <= 5) {
                    this.position.x = 5;
                }
                if (this.position.x >= 3840 - this.width - 5) {
                    this.position.x = 3840 - this.width - 5;
                }
                if (this.position.y >= 2160 - this.height - 2) {
                    this.position.y = 2160 - this.height - 2;
                }

                collisionRectPlayer.x = this.position.x;
                collisionRectPlayer.y = this.position.y;

            }
            if (attack) {
                if (firstAnimationAttack) {
                    currentFrame = 0;
                    firstAnimationAttack = false;
                }
                stateTime += delta;
                if (stateTime >= frameTime) {
                    currentFrame++;
                    if (tipusJugador == 1 && currentFrame == 5) {
                        Arrow arrow = new Arrow(direction, position, this);
                        arrowList.add(arrow);
                        getStage().addActor(arrow);
                        arrow.setZIndex(0);
                        AssetManager.arrowSound.play();
                    }
                    if (tipusJugador != 1 && currentFrame == 3){
                        if (tipusJugador == 2){
                            AssetManager.warSound.play();
                        } else {
                            AssetManager.shieldSound.play();
                        }
                    }
                    if (currentFrame >= animacionAtaqueRight.length) {
                        currentFrame = 0;
                        attack = false;
                        attackDelay = TimeUtils.nanoTime();
                    }
                    stateTime = 0;
                }
            } else {
                stateTime += delta;
                if (stateTime >= frameTime) {
                    currentFrame++;
                    if (currentFrame >= animacionRight.length) {
                        currentFrame = 0;
                    }
                    stateTime = 0;
                }
            }
        }else{
            if(firstAnimationDie){
                currentFrame = 0;
                firstAnimationDie = false;
            }
            stateTime += delta;
            if (stateTime >= frameTime) {
                currentFrame++;
                if (currentFrame >= animacionMuerte.length) {
                    currentFrame = 5;
                    isDead = true;
                }
                stateTime = 0;
            }
        }
    }

    public void draw(Batch batch, float parentAlpha){
        batch.draw(getPlayerAnimation(), this.position.x, this.position.y, width, height);
        doDamage = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    private TextureRegion getPlayerAnimation() {
        TextureRegion playerDir = null;
        //Posicio per si no es mou
        if(vida > 0) {
            if (!this.categoria.equals("bot")) {
                switch (direction) {
                    case Settings.PRESSED_UP:
                        playerDir = animacionUp[0];
                        break;
                    case Settings.PRESSED_LEFT:
                        playerDir = animacionLeft[0];
                        break;
                    case Settings.PRESSED_DOWN:
                        playerDir = animacionDown[0];
                        break;
                    case Settings.PRESSED_RIGHT:
                        playerDir = animacionRight[0];
                        break;
                }
                //ANIMACIONES POR DIRECCIONES
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || this.bntLeftIsPressed) {
                    playerDir = animacionLeft[currentFrame];
                } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || this.bntRightIsPressed) {
                    playerDir = animacionRight[currentFrame];
                } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || this.bntUpIsPressed) {
                    playerDir = animacionUp[currentFrame];
                } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed) {
                    playerDir = animacionDown[currentFrame];
                }
                if (attack && direction == Settings.PRESSED_RIGHT) {
                    playerDir = animacionAtaqueRight[currentFrame];
                } else if (attack && direction == Settings.PRESSED_LEFT) {
                    playerDir = animacionAtaqueLeft[currentFrame];
                } else if (attack && direction == Settings.PRESSED_UP) {
                    playerDir = animacionAtaqueUp[currentFrame];
                } else if (attack && direction == Settings.PRESSED_DOWN) {
                    playerDir = animacionAtaqueDown[currentFrame];
                }
            }

            if (this.categoria.equals("bot")) {
                playerDir = animacionRight[currentFrame];
            }

            if (this.categoria.equals("picker")) {
                playerDir = animacionDown[0];
            }


        }else{
            if(firstAnimationDie){
                currentFrame = 0;
            }
            playerDir = animacionMuerte[currentFrame];
        }
        return playerDir;
    }

    public void desplazarAutomaticamente(float x, float y){
        this.position.x = x;
        this.position.y = y;
    }


    public Rectangle getCollisionRectPlayer() {
        return collisionRectPlayer;
    }

    public void startAttack(){
        this.bntAttackPressed = true;
    }

    public void stopAttack(){
        this.bntAttackPressed = false;
    }

    public void goingUp() {
        this.bntUpIsPressed = true;
    }

    public void goingDown() {
        this.bntDownIsPressed = true;
    }

    public void goingLeft() {
        this.bntLeftIsPressed = true;
    }

    public void goingRight() {
        this.bntRightIsPressed = true;
    }

    public void stop() {
        this.bntUpIsPressed = false;
        this.bntLeftIsPressed = false;
        this.bntDownIsPressed = false;
        this.bntRightIsPressed = false;
    }

    public void attacking(Zombie zombie, float delta) {
        if (attack) {
            if (tipusJugador != 1) {
                float calculoX = zombie.getRectanguloDeteccion().x - collisionRectPlayer.x;
                float calculoY = zombie.getRectanguloDeteccion().y - collisionRectPlayer.y;
                switch (direction) {
                    case Settings.PRESSED_UP:
                        if ((calculoY > 0 && calculoY < 32) && (calculoX > -24 && calculoX < 24) && doDamage) {
                            setDamageZombie(zombie);
                        }
                        break;
                    case Settings.PRESSED_LEFT:
                        if ((calculoY > -24 && calculoY < 24) && (calculoX > -32 && calculoX < 0) && doDamage) {
                            setDamageZombie(zombie);
                        }
                        break;
                    case Settings.PRESSED_DOWN:
                        if ((calculoY > -32 && calculoY < 0) && (calculoX > -24 && calculoX < 24) && doDamage) {
                            setDamageZombie(zombie);

                        }
                        break;
                    case Settings.PRESSED_RIGHT:
                        if ((calculoY > -24 && calculoY < 24) && (calculoX > 0 && calculoX < 32) && doDamage) {
                            setDamageZombie(zombie);

                        }
                        break;
                }
            }else{
                for (int i = 0; i < arrowList.size(); i++) {
                    if(arrowList.get(i).setZombie(zombie)){
                        i--;
                    }
                }
            }
        }
    }

    private void setDamageZombie(Zombie zombie) {
        int damage = this.fuerza + bonusMultiplier[Settings.BONUS_DAMAGE];
        zombie.setDamage(damage);
        zombie.die(this.direction);
    }

    public int getTipusJugador() {
        return tipusJugador;
    }

    public String getTypePlayer(){
        String tipus = "";
        if (this.tipusJugador == 1){
            tipus = "Crossbow";
        } else if (this.tipusJugador == 2){
            tipus = "Warhammer";
        } else if (this.tipusJugador == 3){
            tipus = "Shield";
        }
        return tipus;
    }

    public void removeArrow(Arrow arrow){
        arrowList.remove(arrow);
    }

    public void unaKillMas(){
        this.killsJugador++;
    }

    public int getKillsJugador() {
        return this.killsJugador;
    }

    public void setKillsJugador(int killsJugador) {
        this.killsJugador = killsJugador;
    }

    public ArrayList<Arrow> getArrowList() {
        return arrowList;
    }

    public void setDamage(int damage) {
        if(armadura > 0){
            this.armadura -= damage;
        }else if (vida > 0){
            this.vida -= damage;
            if (this.vida < 0){
                this.vida = 0;
            }
        }
        getStage().addActor(new HitEffect(this.position));
    }

    public void die(int direction) {
        if(vida <= 0) {
            getStage().addActor(new PoolBlood(this.position, direction));
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getArmadura() {
        return armadura;
    }

    public boolean isDead() {
        return isDead;
    }

    public int[] getBonusMultiplier() {
        return bonusMultiplier;
    }

    public void subirVida() {
        bonusMultiplier[Settings.BONUS_LIVE]++;
        vida += bonusMultiplier[Settings.BONUS_LIVE];
        bonusMultiplier[Settings.BONUS_LIVE] = 0;
    }

    public void subirArmadura() {
        bonusMultiplier[Settings.BONUS_SHIELD]++;
        armadura += bonusMultiplier[Settings.BONUS_SHIELD];
        bonusMultiplier[Settings.BONUS_SHIELD] = 0;
    }

    public void subirVelocidad() {
        bonusMultiplier[Settings.BONUS_VELOCITY] += 1;
        timeoutVelocity = TimeUtils.nanoTime();
    }

    public void subirFuerza() {
        bonusMultiplier[Settings.BONUS_DAMAGE] += 1;
        timeoutDamage = TimeUtils.nanoTime();
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
        this.collisionRectPlayer.x = x;
        this.collisionRectPlayer.y = y;
    }

    public int getFuerza() {
        return this.fuerza;
    }

    public float getOldx() {
        return oldx;
    }

    public void setOldx(float oldx) {
        this.oldx = oldx;
    }

    public float getOldy() {
        return oldy;
    }

    public void setOldy(float oldy) {
        this.oldy = oldy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
}
