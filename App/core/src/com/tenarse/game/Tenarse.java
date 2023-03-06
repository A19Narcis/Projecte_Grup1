package com.tenarse.game;

import com.badlogic.gdx.Game;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.screens.MainMenuScreen;
import com.tenarse.game.screens.SplashScreen;


public class Tenarse extends Game {

	@Override
	public void create () {
		//Carregar totes les textures
		AssetManager.load();

		//Pantalla 'Inici'
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
