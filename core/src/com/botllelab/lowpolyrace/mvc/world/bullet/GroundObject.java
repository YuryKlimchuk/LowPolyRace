package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class GroundObject extends BulletEntity {

	
	
	public GroundObject(Model _model, btRigidBody _body) {
		super(_model);
		body = _body;
	}

	

	
}
