package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public abstract class BulletEntity {
	
	public ModelInstance instance;
	public btRigidBody body;
	public String name;
	
	public BulletEntity(Model _model) {
		instance = new ModelInstance(_model);
	}
	
	
	public void draw(ModelBatch batch, Environment env, boolean flag) {
		batch.render(instance);
	}

}
