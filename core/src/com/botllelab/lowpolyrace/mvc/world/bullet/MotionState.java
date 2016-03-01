package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

public class MotionState extends btMotionState {
	private final Matrix4 transform;

	public MotionState (final Matrix4 transform) {
		this.transform = transform;
	}

	/** For dynamic and static bodies this method is called by bullet once to get the initial state of the body. For kinematic
	 * bodies this method is called on every update, unless the body is deactivated. */
	@Override
	public void getWorldTransform (final Matrix4 worldTrans) {
		worldTrans.set(transform);
	}

	/** For dynamic bodies this method is called by bullet every update to inform about the new position and rotation. */
	@Override
	public void setWorldTransform (final Matrix4 worldTrans) {
		transform.set(worldTrans);
	}
}
