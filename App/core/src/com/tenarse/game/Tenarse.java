package com.tenarse.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.screens.LoadingScreen;
import com.tenarse.game.screens.MainMenuScreen;


public class Tenarse extends Game {

	public static AssetManager assetManager;

	@Override
	public void create () {
		//Carregar totes les textures
		assetManager = new AssetManager();

		//Pantalla 'Inici'
		setScreen(new LoadingScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
