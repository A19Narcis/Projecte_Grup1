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
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

import java.util.ArrayList;

public class Jugador extends Actor {

    private final int Crossbow_PLAYER = 1;
    private final int WAR_PLAYER = 2;
    private final int SHI_PLAYER = 3;

    private final int Crossbow_PLAYER_STAND = 4;
    private final int WAR_PLAYER_STAND = 5;
    private final int SHI_PLAYER_STAND = 6;

    private int direction = 3;
    private boolean attack;
    private boolean firstAnimationAttack;
    private boolean firstAnimationDie;
    private boolean doDamage;
    private Vector2 position;
    private int width, height;
    private String categoria;
    private int tipusJugador;
    private int killsJugador;

    private Boolean bntUpIsPressed = false;
    private Boolean bntDownIsPressed = false;
    private Boolean bntLeftIsPressed = false;
    private Boolean bntRightIsPressed = false;
    private Boolean bntAttackPressed = false;
    private boolean isDead;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;

    private TextureRegion[] animacionAtaqueRight;
    private TextureRegion[] animacionAtaqueLeft;
    private TextureRegion[] animacionAtaqueUp;
    private TextureRegion[] animacionAtaqueDown;
    private TextureRegion[] animacionMuerte;
    private long attackDelay;

    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private float oldx;
    private float oldy;

    private int vida;
    private int armadura;

    private Map map;

    private Rectangle collisionRectPlayer;

    private ArrayList<Arrow> arrowList = new ArrayList<>();


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

        vida = Settings.PLAYER_VIDAS;
        armadura = Settings.PLAYER_ARMADURA;
        isDead = false;

        if (tipusJugador == Crossbow_PLAYER){
            animacionRight = AssetManager.playerRightA_Animation;
            animacionLeft = AssetManager.playerLeftA_Animation;
            animacionUp = AssetManager.playerUpA_Animation;
            animacionDown = AssetManager.playerDownA_Animation;
            animacionAtaqueRight = AssetManager.playerRightA_Attack;
            animacionAtaqueLeft = AssetManager.playerLeftA_Attack;
            animacionAtaqueUp = AssetManager.playerUpA_Attack;
            animacionAtaqueDown = AssetManager.playerDownA_Attack;
            animacionMuerte = AssetManager.CrossbowlDeadAnimation;
        } else if (tipusJugador == WAR_PLAYER){
            animacionRight = AssetManager.playerRightW_Animation;
            animacionLeft = AssetManager.playerLeftW_Animation;
            animacionUp = AssetManager.playerUpW_Animation;
            animacionDown = AssetManager.playerDownW_Animation;
            animacionAtaqueRight = AssetManager.playerRightW_Atack;
            animacionAtaqueLeft = AssetManager.playerLeftW_Atack;
            animacionAtaqueUp = AssetManager.playerUpW_Atack;
            animacionAtaqueDown = AssetManager.playerDownW_Atack;
            animacionMuerte = AssetManager.WarhamerDeadAnimation;
        } else if (tipusJugador == SHI_PLAYER){
            animacionRight = AssetManager.playerRightS_Animation;
            animacionLeft = AssetManager.playerLeftS_Animation;
            animacionUp = AssetManager.playerUpS_Animation;
            animacionDown = AssetManager.playerDownS_Animation;
            animacionAtaqueRight = AssetManager.playerRightS_Atack;
            animacionAtaqueLeft = AssetManager.playerLeftS_Atack;
            animacionAtaqueUp = AssetManager.playerUpS_Atack;
            animacionAtaqueDown = AssetManager.playerDownS_Atack;
            animacionMuerte = AssetManager.ShieldDeadAnimation;
        }

        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

        public void act(float delta){
        super.act(delta);
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
                    this.position.x -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
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
                    this.position.x += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
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
                    this.position.y += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    if (map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_DOWN;
                    this.position.y -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
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
            System.out.println(currentFrame);
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
        zombie.setDamage(Settings.PLAYER_FUERZA);
        zombie.die(this.direction);
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

    public ArrayList<Arrow> getArrowList() {
        return arrowList;
    }

    public void setDamage(int damage) {
        if(armadura > 0){
            this.armadura -= damage;
        }else{
            this.vida -= damage;
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

    public int getArmadura() {
        return armadura;
    }

    public boolean isDead() {
        return isDead;
    }
}
