package com.botllelab.lowpolyrace;

import com.badlogic.gdx.Game;
import com.botllelab.lowpolyrace.utils.AudioManager;
import com.botllelab.lowpolyrace.utils.PreferenceManager;
import com.botllelab.lowpolyrace.utils.ResourceManager;
import com.botllelab.lowpolyrace.utils.ScreenManager;




public class GameManager extends Game {

	
	public ScreenManager screenMngr;
	public ResourceManager resMngr;
	public AudioManager audioMnger;
	public PreferenceManager prefManager;
	
	@Override
	public void create () {
		initMngrs();
		screenMngr.setSplashScreen();
	}


	private void initMngrs() {
		screenMngr = new ScreenManager(this);
		resMngr = new ResourceManager();
		audioMnger = new AudioManager();
		prefManager = new PreferenceManager();
	}
	
	@Override
	public void dispose() {
		resMngr.dispose();
		screenMngr.dispose();
		super.dispose();
		
	}
}
