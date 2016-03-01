package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.botllelab.lowpolyrace.utils.Constants;

public enum VehicleState implements State<Vehicle> {
	
	STEER_NONE() {
		@Override
		public void update(Vehicle entity) {
			entity.steer(Constants.STEER_NONE);
		}
	},
	
	STEER_LEFT() {
		@Override
		public void update(Vehicle entity) {
			entity.steer(Constants.STEER_LEFT);
		}
	},
	
	STEER_RIGHT() {
		@Override
		public void update(Vehicle entity) {
			entity.steer(Constants.STEER_RIGHT);
		}
	},
	
	UNACTIVE() {},
	
	ACCELERATE_NONE() {
		@Override
		public void update(Vehicle entity) {
			entity.accelerate(Constants.ACCELERATE_NONE);
		}
	},
	
	ACCELERATE_FORWARD() {
		@Override
		public void update(Vehicle entity) {
			entity.accelerate(Constants.ACCELERATE_FORWARD);
		}
	},
	
	ACCELERATE_BACKWARD() {
		@Override
		public void update(Vehicle entity) {
			entity.accelerate(Constants.ACCELERATE_BACKWARD);
		}
	};

	@Override
	public void enter(Vehicle entity) {}

	@Override
	public void update(Vehicle entity) {}

	@Override
	public void exit(Vehicle entity) {}

	@Override
	public boolean onMessage(Vehicle entity, Telegram telegram) {
		return false;
	}

}
