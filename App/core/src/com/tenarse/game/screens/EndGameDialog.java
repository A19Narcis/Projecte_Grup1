package com.tenarse.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;

import io.socket.client.Socket;

public class EndGameDialog extends Dialog {

    private Tenarse game;

    private int puntosPartida;
    private int killsJugador;
    private Label texto;
    private Socket socket;
    private boolean multiplayer = false;

    public int getPuntosPartida() {
        return puntosPartida;
    }

    public void setPuntosPartida(int puntosPartida) {
        this.puntosPartida = puntosPartida;
    }

    public int getKillsJugador() {
        return killsJugador;
    }

    public void setKillsJugador(int killsJugador) {
        this.killsJugador = killsJugador;
    }

    public Label getTexto() {
        return texto;
    }

    public EndGameDialog(String title, Skin skin, int puntosParida, int killsJugador, final Tenarse game, Socket socket) {
        super(title, skin);
        this.game = game;

        this.puntosPartida = puntosParida;
        this.killsJugador = killsJugador;
        this.socket = socket;

        this.multiplayer = true;

        // Agrega el contenido que deseas mostrar en el diálogo
        this.texto = new Label("", skin);
        this.texto.setScale(0.80f);

        getContentTable().add(this.texto).pad(5f);
        // Agrega el botón para volver al menú
        TextButton button = new TextButton("Menu Principal", skin, "default");
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            texto.setFontScale(0.90f);
            button.getLabel().setFontScale(0.85f);
        }
        this.button(button, true);
    }

    public EndGameDialog(String title, Skin skin, int puntosParida, int killsJugador, final Tenarse game) {
        super(title, skin);
        this.game = game;

        this.puntosPartida = puntosParida;
        this.killsJugador = killsJugador;

        // Agrega el contenido que deseas mostrar en el diálogo
        this.texto = new Label("", skin);
        this.texto.setScale(0.80f);

        getContentTable().add(this.texto).pad(5f);
        // Agrega el botón para volver al menú
        TextButton button = new TextButton("Menu Principal", skin, "default");
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            texto.setFontScale(0.90f);
            button.getLabel().setFontScale(0.85f);
        }
        this.button(button, true);
    }

    @Override
    protected void result(Object obj) {
        if ((Boolean) obj) {
            // Aquí debes implementar el código para volver al menú
            game.setScreen(new MainMenuScreen(game));
            AssetManager.mapa1Music.stop();
            AssetManager.mapa2Music.stop();
            AssetManager.mapa3Music.stop();
        }
    }

}
