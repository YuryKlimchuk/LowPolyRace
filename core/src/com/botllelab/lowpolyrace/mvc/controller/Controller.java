package com.botllelab.lowpolyrace.mvc.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Disposable;
import com.botllelab.lowpolyrace.mvc.render.Render;
import com.botllelab.lowpolyrace.mvc.world.World;



public class Controller implements Disposable {
	
	
	
	World world;
	Render render;
	InputMultiplexer inputs = new InputMultiplexer();
	CameraInputController cameraController;
	GUI gui;

	VehicleController vehicleController;
	
	public Controller(World _world, Render _render) {
		world = _world;
		render = _render;
		
		cameraController = new CameraInputController(render.camera);
		inputs.addProcessor(cameraController);

		vehicleController = new VehicleController(world.bulletWorld.vehicle);
		inputs.addProcessor(vehicleController);
		
		gui = new GUI(world);
		inputs.addProcessor(gui);
		
		Gdx.input.setInputProcessor(inputs);
		
	}
	
	public void update(float delta) {
		cameraController.update();
		gui.act(delta);
		gui.draw();
	}

	@Override
	public void dispose() {}

}
