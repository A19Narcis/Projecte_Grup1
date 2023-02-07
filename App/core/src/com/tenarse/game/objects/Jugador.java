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
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

public class Jugador extends Actor {

    private final int AXE_PLAYER = 1;
    private final int WAR_PLAYER = 2;
    private final int SHI_PLAYER = 3;

    private int direction = 3;
    private boolean attack;
    private boolean firstAnimationAttack;
    private boolean doDamage;
    private Vector2 position;
    private int width, height;
    private boolean isBot;
    private int tipusJugador;

    private Boolean bntUpIsPressed = false;
    private Boolean bntDownIsPressed = false;
    private Boolean bntLeftIsPressed = false;
    private Boolean bntRightIsPressed = false;
    private Boolean bntAttackPressed = false;

    private TextureRegion[] animacionRight;
    private TextureRegion[] animacionUp;
    private TextureRegion[] animacionDown;
    private TextureRegion[] animacionLeft;

    private TextureRegion[] animacionAtaqueRight;
    private TextureRegion[] animacionAtaqueLeft;
    private TextureRegion[] animacionAtaqueUp;
    private TextureRegion[] animacionAtaqueDown;
    private long attackDelay;

    private int currentFrame = 0;
    private float frameTime = 0.1f;
    private float stateTime = 0;

    private float oldx;
    private float oldy;

    private Map map;

    private Rectangle collisionRectPlayer;


    public Jugador(float x, float y, int width, int height, boolean isBot, int tipusJugador, Map map){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        this.attack = false;
        firstAnimationAttack = false;
        doDamage = false;
        attackDelay = Settings.PLAYER_ATTACK_DELAY;
        this.map = map;

        this.isBot = isBot;

        collisionRectPlayer = new Rectangle();
        collisionRectPlayer.width = this.width;
        collisionRectPlayer.height = this.height;

        this.tipusJugador = tipusJugador;

        if (tipusJugador == AXE_PLAYER){
            animacionRight = AssetManager.playerRightA_Animation;
            animacionLeft = AssetManager.playerLeftA_Animation;
            animacionUp = AssetManager.playerUpA_Animation;
            animacionDown = AssetManager.playerDownA_Animation;
            animacionAtaqueRight = AssetManager.playerRightA_Attack;
            animacionAtaqueLeft = AssetManager.playerLeftA_Attack;
            animacionAtaqueUp = AssetManager.playerUpA_Attack;
            animacionAtaqueDown = AssetManager.playerDownA_Attack;
        } else if (tipusJugador == WAR_PLAYER){/*SUSTITUIR ANIMAIONES DE PEGAR WARHAMER Y SHIELD*/
            animacionRight = AssetManager.playerRightW_Animation;
            animacionLeft = AssetManager.playerLeftW_Animation;
            animacionUp = AssetManager.playerUpW_Animation;
            animacionDown = AssetManager.playerDownW_Animation;
            animacionAtaqueRight = AssetManager.playerRightW_Atack;
            animacionAtaqueLeft = AssetManager.playerLeftW_Atack;
            animacionAtaqueUp = AssetManager.playerUpW_Atack;
            animacionAtaqueDown = AssetManager.playerDownW_Atack;
        } else if (tipusJugador == SHI_PLAYER){
            animacionRight = AssetManager.playerRightS_Animation;
            animacionLeft = AssetManager.playerLeftS_Animation;
            animacionUp = AssetManager.playerUpS_Animation;
            animacionDown = AssetManager.playerDownS_Animation;
            animacionAtaqueRight = AssetManager.playerRightS_Atack;
            animacionAtaqueLeft = AssetManager.playerLeftS_Atack;
            animacionAtaqueUp = AssetManager.playerUpS_Atack;
            animacionAtaqueDown = AssetManager.playerDownS_Atack;

        }

        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

        public void act(float delta){
        super.act(delta);
        if (this.isBot){
            this.position.x += 5;
            if (this.position.x >= Gdx.graphics.getWidth()){
                this.position.x = -400;
            }
        } else {
            oldx = this.position.x;
            oldy = this.position.y;
                if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || this.bntLeftIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_LEFT;
                    this.position.x -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    this.position.x -= 8;
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }else {
                        this.position.x += 8;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || this.bntRightIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_RIGHT;
                    this.position.x += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    this.position.x += 8;
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }else {
                        this.position.x -= 8;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || this.bntUpIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_UP;
                    this.position.y += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed) && !attack) {
                    this.direction = Settings.PRESSED_DOWN;
                    this.position.y -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
                    this.position.y -= 12;
                    if(map.searchColision(position.x, position.y)) {
                        this.position.x = oldx;
                        this.position.y = oldy;
                    }else {
                        this.position.y += 12;
                    }
                }
                if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || bntAttackPressed) && !attack && TimeUtils.nanoTime() - attackDelay >= Settings.PLAYER_ATTACK_DELAY) {
                    attack = true;
                    firstAnimationAttack = true;
                    doDamage = true;
                }

            //Colision personaje con los bordes del mapa
            if (this.position.y <= 5){
                this.position.y = 5;
            }
            if (this.position.x <= 5){
                this.position.x = 5;
            }
            if (this.position.x >= 3840 - this.width - 5){
                this.position.x = 3840 - this.width - 5;
            }
            if (this.position.y >= 2160 - this.height - 2){
                this.position.y = 2160 - this.height - 2;
            }

            collisionRectPlayer.x = this.position.x;
            collisionRectPlayer.y = this.position.y;

        }
        if(attack){
            if(firstAnimationAttack){
                currentFrame = 0;
                firstAnimationAttack = false;
            }
            stateTime += delta;
            if (stateTime >= frameTime){
                currentFrame++;
                if (currentFrame >= animacionAtaqueRight.length){
                    currentFrame = 0;
                    attack = false;
                    attackDelay = TimeUtils.nanoTime();
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
        batch.draw(getPlayerAnimation(), this.position.x, this.position.y, width, height);
        doDamage = false;
    }

    private TextureRegion getPlayerAnimation() {
        TextureRegion playerDir = null;
        //Posicio per si no es mou
        if(!isBot){
            switch (direction){
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
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A) || this.bntLeftIsPressed){
                playerDir = animacionLeft[currentFrame];
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D) || this.bntRightIsPressed){
                playerDir = animacionRight[currentFrame];
            } else if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) || this.bntUpIsPressed){
                playerDir = animacionUp[currentFrame];
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S) || this.bntDownIsPressed){
                playerDir = animacionDown[currentFrame];
            }
            if(attack && direction == Settings.PRESSED_RIGHT) {
                playerDir = animacionAtaqueRight[currentFrame];
            }else if(attack && direction == Settings.PRESSED_LEFT) {
                playerDir = animacionAtaqueLeft[currentFrame];
            }else if(attack && direction == Settings.PRESSED_UP) {
                playerDir = animacionAtaqueUp[currentFrame];
            }else if(attack && direction == Settings.PRESSED_DOWN) {
                playerDir = animacionAtaqueDown[currentFrame];
            }
        }

        if (this.isBot){
            playerDir = animacionRight[currentFrame];
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

    public void attacking(Zombie zombie) {
        if (attack) {
            float calculoX = zombie.getCollisionRectZombie().x - collisionRectPlayer.x;
            float calculoY = zombie.getCollisionRectZombie().y - collisionRectPlayer.y;
            switch (direction){
                case Settings.PRESSED_UP:
                    if ((calculoY > 0 && calculoY < 24) && (calculoX > -24 && calculoX < 24) && doDamage) {
                        zombie.setDamage(Settings.PLAYER_STRENGTH);
                        zombie.die();

                    }
                    break;
                case Settings.PRESSED_LEFT:
                    if ((calculoY > -24 && calculoY < 24) && (calculoX > -24 && calculoX < 0) && doDamage) {
                        zombie.setDamage(Settings.PLAYER_STRENGTH);
                        zombie.die();

                    }
                    break;
                case Settings.PRESSED_DOWN:
                    if ((calculoY > -24 && calculoY < 0) && (calculoX > -24 && calculoX < 24) && doDamage) {
                        zombie.setDamage(Settings.PLAYER_STRENGTH);
                        zombie.die();

                    }
                    break;
                case Settings.PRESSED_RIGHT:
                    if ((calculoY > -24 && calculoY < 24) && (calculoX > 0 && calculoX < 24) && doDamage) {
                        zombie.setDamage(Settings.PLAYER_STRENGTH);
                        zombie.die();

                    }
                    break;
            }
        }
    }
}
