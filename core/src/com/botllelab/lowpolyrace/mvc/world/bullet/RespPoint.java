package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.math.Vector3;

public class RespPoint {
	
	public Vector3 position;
	public boolean isUse;
	
	public RespPoint(Vector3 _position, boolean _isUse) {
		position = _position;
		isUse = _isUse;
	}

}
