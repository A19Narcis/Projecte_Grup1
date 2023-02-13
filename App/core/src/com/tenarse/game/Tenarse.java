package com.tenarse.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.screens.MainMenuScreen;


public class Tenarse extends Game {


	@Override
	public void create () {
		//Carregar totes les textures
		AssetManager.load();

		//Pantalla 'Inici'
		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
