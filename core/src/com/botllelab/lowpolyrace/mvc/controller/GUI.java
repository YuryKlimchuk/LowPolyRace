package com.botllelab.lowpolyrace.mvc.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.botllelab.lowpolyrace.GameManager;
import com.botllelab.lowpolyrace.mvc.world.World;

public class GUI extends Stage {
	
	World world;
	
	BitmapFont font;
	
	float time = 300f;
	
	public GUI(World _world) {
		AssetManager aMngr = ((GameManager) Gdx.app.getApplicationListener()).resMngr.aManager;
		font = aMngr.get("font26.TTF", BitmapFont.class);
		
		world = _world;
	}
	
	
	@Override
	public void draw() {
		time -= Gdx.graphics.getDeltaTime();
		
		getBatch().begin();
		font.draw(getBatch(), "FPS : " + Gdx.graphics.getFramesPerSecond(), 50f, 50f);
		font.draw(getBatch(), "SPEED : " + world.bulletWorld.vehicle.getSpeed(), 350f, 50f);
		font.draw(getBatch(), "FORCE : " + (int) world.bulletWorld.vehicle.force, 600f, 50f);
		font.draw(getBatch(), "ITEMS : " + world.countItems + "/20", 50f, 400f);
		font.draw(getBatch(), "TIME : " + (int) time, 650f, 400f);
		getBatch().end();
	}

}
