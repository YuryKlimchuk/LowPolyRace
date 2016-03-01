package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Pool.Poolable;

public class ItemObject extends BulletEntity implements Poolable  {
	
	BulletWorld bulletWorld;
	public boolean IS_ACTIVE = true;

	public ItemObject(Model _model, Vector3 _itemPositiob, BulletWorld _bulletWorld) {
		super(_model);
		instance.transform.setTranslation(_itemPositiob);
		
		
		body = new btRigidBody(0f, new MotionState(instance.transform), BulletWorld.createConvexHullShape(_model, true));
		body.setCollisionFlags(body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);

		body.userData = this;
		
		bulletWorld = _bulletWorld;
	}
	
	
	public void setActive(Vector3 _position) {

	}
	
	public void setUnactive() {
		
	}
	
	
	@Override
	public void draw(ModelBatch batch, Environment env, boolean flag) {
		if(IS_ACTIVE)
			super.draw(batch, env, flag);
	}


	@Override
	public void reset() {
		IS_ACTIVE = false;
		bulletWorld.removeRigidBody(body);
		body.translate(new Vector3(0f, 0f, 0f));
	}

}
