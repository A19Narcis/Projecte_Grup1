package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.tenarse.game.utils.Settings;

public class JugadorOnline extends Jugador{

    public JugadorOnline(float x, float y, int width, int height, int tipusJugador, Map map) {
        super(x, y, width, height, "player", tipusJugador, map);
    }



    @Override
    public void act(float delta){
        if(vida > 0) {
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

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(getPlayerAnimation(), this.position.x, this.position.y, width, height);
        doDamage = false;
    }


    private TextureRegion getPlayerAnimation() {
        TextureRegion playerDir = null;
        boolean moving = true;
        if(oldx == position.x && oldy == position.y){
            moving = false;
        }
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
                if (moving && direction == Settings.PRESSED_LEFT) {
                    playerDir = animacionLeft[currentFrame];
                } else if (moving && direction == Settings.PRESSED_RIGHT) {
                    playerDir = animacionRight[currentFrame];
                } else if (moving && direction == Settings.PRESSED_UP) {
                    playerDir = animacionUp[currentFrame];
                } else if (moving && direction == Settings.PRESSED_DOWN) {
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
}
