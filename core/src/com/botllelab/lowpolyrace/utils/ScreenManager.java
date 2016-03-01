package com.botllelab.lowpolyrace.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Disposable;
import com.botllelab.lowpolyrace.screens.PlayScreen;
import com.botllelab.lowpolyrace.screens.SplashScreen;


public class ScreenManager implements Disposable {
	
	
	Game game;
	
	public ScreenManager(Game _game) {
		game = _game;
	}
	
	public void setSplashScreen() {
		game.setScreen(new SplashScreen());
	}
	
	public void setPlayScreen(int _boxCount, int _level) {
		PlayScreen playScreen = new PlayScreen(_boxCount,_level);
		game.setScreen(playScreen);
	}

	@Override
	public void dispose() {}
	
}
