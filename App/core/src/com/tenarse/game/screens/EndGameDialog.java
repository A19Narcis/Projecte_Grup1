package com.tenarse.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;
import com.tenarse.game.Tenarse;

public class EndGameDialog extends Dialog {

    private Tenarse game;

    public EndGameDialog(String title, Skin skin, int puntosParida, int killsJugador, final Tenarse game) {
        super(title, skin);
        this.game = game;
        // Agrega el contenido que deseas mostrar en el diálogo
        Label texto = new Label("Partida terminada\n\nPuntos: " + puntosParida + "\nKills: " + killsJugador, skin);
        texto.setScale(0.80f);
        getContentTable().add(texto).pad(20f);
        // Agrega el botón para volver al menú
        button("Menu Principal", true);
    }

    @Override
    protected void result(Object obj) {
        if ((Boolean) obj) {
            // Aquí debes implementar el código para volver al menú
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
