package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
}
