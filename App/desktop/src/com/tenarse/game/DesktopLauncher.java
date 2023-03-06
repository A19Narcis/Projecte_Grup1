package com.tenarse.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.tenarse.game.Tenarse;
import com.tenarse.game.helpers.AssetManager;
import com.tenarse.game.utils.Settings;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(Settings.GAME_WIDTH, Settings.GAME_HEIGHT);
		config.setTitle("Baldhead");
		config.setWindowIcon(System.getProperty("user.dir") + "/assets/logoGame.png");
		config.setResizable(false);
		new Lwjgl3Application(new Tenarse(), config);
	}
}
