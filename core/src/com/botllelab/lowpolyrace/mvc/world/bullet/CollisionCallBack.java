package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.ContactListener;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;


public class CollisionCallBack extends ContactListener {

	@Override
    public void onContactStarted (btCollisionObject colObj0, btCollisionObject colObj1) {
		
		if(colObj0 instanceof btRigidBody) {
			Gdx.app.log(null, "colObj0 - btRigidBody");
			
			if(colObj0.userData instanceof Vehicle) {
				Gdx.app.log(null, "colObj0 - Vehicle");
				((Vehicle) colObj0.userData).collectItem(colObj1);
			}
		}
		
		if(colObj1 instanceof btRigidBody) {
			Gdx.app.log(null, "colObj1 - btRigidBody");
			
			if(colObj1.userData instanceof Vehicle) {
				Gdx.app.log(null, "colObj1 - Vehicle");
				((Vehicle) colObj0.userData).collectItem(colObj0);
			}
		}
		
		
    }
}
