package com.botllelab.lowpolyrace.mvc.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.botllelab.lowpolyrace.mvc.world.bullet.Vehicle;
import com.botllelab.lowpolyrace.mvc.world.bullet.VehicleState;


public class VehicleController implements InputProcessor {
	
	Vehicle vehicle;
	
	public VehicleController(Vehicle _vehicle) {
		vehicle = _vehicle;
	}

	@Override
	public boolean keyDown(int keycode) {
		
		switch (keycode) {
		case Keys.LEFT:
			vehicle.fsm.setGlobalState(VehicleState.STEER_LEFT);
			break;

		case Keys.RIGHT:
			vehicle.fsm.setGlobalState(VehicleState.STEER_RIGHT);
			break;
			
		case Keys.UP:
			vehicle.fsm.changeState(VehicleState.ACCELERATE_FORWARD);
			break;
		
		case Keys.DOWN:
			vehicle.fsm.changeState(VehicleState.ACCELERATE_BACKWARD);
			break;
		
		case Keys.R:
			
			
			break;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			vehicle.fsm.setGlobalState(VehicleState.STEER_NONE);
			break;

		case Keys.RIGHT:
			vehicle.fsm.setGlobalState(VehicleState.STEER_NONE);
			break;
			
		case Keys.UP:
			vehicle.fsm.changeState(VehicleState.ACCELERATE_NONE);
			break;
			
		case Keys.DOWN:
			vehicle.fsm.changeState(VehicleState.ACCELERATE_NONE);
			break;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
