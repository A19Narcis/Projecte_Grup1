package com.tenarse.game.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AMSprites {
    public String nombreTipo;
    public Texture sheet;
    public TextureRegion M_U[] = new TextureRegion[9];
    public TextureRegion M_L[] = new TextureRegion[9];
    public TextureRegion M_D[] = new TextureRegion[9];
    public TextureRegion M_R[] = new TextureRegion[9];
    public TextureRegion A_U[] = new TextureRegion[6];
    public TextureRegion A_L[] = new TextureRegion[6];
    public TextureRegion A_D[] = new TextureRegion[6];
    public TextureRegion A_R[] = new TextureRegion[6];
    public TextureRegion Dead[] = new TextureRegion[6];
    public TextureRegion Spawn[] = new TextureRegion[5];
    private int StartAnimationAttack = 12;

    public AMSprites(String nombreTipo, Texture sheet) {
        this.nombreTipo = nombreTipo;
        this.sheet = sheet;
        if(nombreTipo.equals("Crossbow")){
            A_U = new TextureRegion[8];
            A_L = new TextureRegion[8];
            A_D = new TextureRegion[8];
            A_R = new TextureRegion[8];
            StartAnimationAttack = 4;
        }

        for (int i = 0; i < M_U.length; i++) {
            M_U[i] = new TextureRegion(sheet, i * 64, 64 * 8, 64, 64);
            M_L[i] = new TextureRegion(sheet, i * 64, 64 * 9, 64, 64);
            M_D[i] = new TextureRegion(sheet, i * 64, 64 * 10, 64, 64);
            M_R[i] = new TextureRegion(sheet, i * 64, 64 * 11, 64, 64);
        }

        for (int i = 0; i < A_U.length; i++) {
            A_U[i] = new TextureRegion(sheet, i * 64, 64 * StartAnimationAttack, 64, 64);
            A_L[i] = new TextureRegion(sheet, i * 64, 64 * (StartAnimationAttack + 1), 64, 64);
            A_D[i] = new TextureRegion(sheet, i * 64, 64 * (StartAnimationAttack + 2), 64, 64);
            A_R[i] = new TextureRegion(sheet, i * 64, 64 * (StartAnimationAttack + 3), 64, 64);
        }

        for (int i = 0; i < Dead.length; i++) {
            Dead[i] = new TextureRegion(sheet, i * 64, 64 * 20, 64, 64);
        }

        for (int i = Spawn.length - 1; i >= 0 ; i--) {
            Spawn[(Spawn.length - 1) - i] = new TextureRegion(sheet, i * 64, 64 * 20, 64, 64);
        }
    }
}
