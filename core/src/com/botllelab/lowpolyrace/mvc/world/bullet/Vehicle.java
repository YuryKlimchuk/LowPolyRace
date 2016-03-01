package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btDefaultVehicleRaycaster;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRaycastVehicle;
import com.badlogic.gdx.physics.bullet.dynamics.btRaycastVehicle.btVehicleTuning;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btVehicleRaycaster;
import com.badlogic.gdx.physics.bullet.dynamics.btWheelInfo;
import com.botllelab.lowpolyrace.utils.Constants;

public class Vehicle extends BulletEntity {
	
	public btVehicleRaycaster raycaster;
	public btRaycastVehicle vehicle;
	public btVehicleTuning tuning;
	

	BulletWorld bulletWorld;
	
	ModelInstance[] wheelInstances = new ModelInstance[4];
	
	public DefaultStateMachine<Vehicle, VehicleState> fsm = new DefaultStateMachine<Vehicle, VehicleState>(this, VehicleState.UNACTIVE, VehicleState.STEER_NONE);
	
	public float force = 0f;
	float angle = 0f;
	
	
	
	public Vehicle(Model _chassisModel, Model _wheelModel, btRigidBody _body, BulletWorld _bulletWorld) {
		super(_chassisModel);
		body = _body;
		
		bulletWorld = _bulletWorld;
		
		raycaster = new btDefaultVehicleRaycaster(_bulletWorld);
		tuning = new btVehicleTuning();
		tuning.setMaxSuspensionTravelCm(20f);
		tuning.setSuspensionStiffness(50f);	
		tuning.setSuspensionDamping(20f);
		tuning.setFrictionSlip(1000f);
		tuning.setSuspensionCompression(0.35f);
		tuning.setSuspensionDamping(1000f);
		tuning.setMaxSuspensionForce(500000f);
		
		vehicle = new btRaycastVehicle(tuning, body, raycaster);
		vehicle.getRigidBody().setCollisionFlags(vehicle.getRigidBody().getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
		
		
		_bulletWorld.addVehicle(vehicle);
		vehicle.setCoordinateSystem(0, 1, 2);
		
		btWheelInfo wheelInfo;
		Vector3 point = new Vector3();
		Vector3 direction = new Vector3(0, -1, 0);
		Vector3 axis2 = new Vector3(-1, 0, 0);
		
		wheelInfo = vehicle.addWheel(
				point.set(1f, 0.3f, 1.25f), 
				direction, 
				axis2,
				0.8f, 
				0.6f, 
				tuning, 
				true);
		
		wheelInfo = vehicle.addWheel(
				point.set(-1f, 0.3f, 1.25f), 
				direction, 
				axis2,
				0.8f, 
				0.6f, 
				tuning, 
				true);
		
		wheelInfo = vehicle.addWheel(
				point.set(1f, 0.3f, -1.25f), 
				direction, 
				axis2,
				0.8f,
				0.6f, 
				tuning, 
				false);
		
		wheelInfo = vehicle.addWheel(
				point.set(-1f, 0.3f, -1.25f), 
				direction, 
				axis2,
				0.8f, 
				0.6f, 
				tuning, 
				false);
		
		wheelInstances[0] = new ModelInstance(_wheelModel);
		wheelInstances[1] = new ModelInstance(_wheelModel);
		wheelInstances[2] = new ModelInstance(_wheelModel);
		wheelInstances[3] = new ModelInstance(_wheelModel);
		
		vehicle.setBrake(5f, 0);
		vehicle.setBrake(5f, 1);
		
		body.userData = this;
	}
	
	
	public void accelerate(int flag) {
		
		switch (flag) {
		case Constants.ACCELERATE_FORWARD:
			force += Gdx.graphics.getDeltaTime() * Constants.DELTA_FORCE;
			force = MathUtils.clamp(force, 0, Constants.MAX_FORWARD_FORCE);
			break;

		case Constants.ACCELERATE_BACKWARD:
			force -= Gdx.graphics.getDeltaTime() * Constants.DELTA_FORCE;
			force = MathUtils.clamp(force, -Constants.MAX_FORWARD_FORCE, 0f);
			break;
			
		case Constants.ACCELERATE_NONE:
			force = 0;
			break;
		}
		
		vehicle.applyEngineForce(force, 0);
		vehicle.applyEngineForce(force, 1);
		
	}
	
	
	public void steer(int flag) {
		
		switch (flag) {
		case Constants.STEER_LEFT:
			angle += Gdx.graphics.getDeltaTime() * Constants.DELTA_STEERING_ANGLE;
			angle = MathUtils.clamp(angle, 0f, Constants.MAX_STEERING_ANGLE);
			break;

		case Constants.STEER_RIGHT:
			angle -= Gdx.graphics.getDeltaTime() * Constants.DELTA_STEERING_ANGLE;
			angle = MathUtils.clamp(angle, -Constants.MAX_STEERING_ANGLE, 0f);
			break;
		
		case Constants.STEER_NONE:
			angle = 0;
			break;
		}
		
		vehicle.setSteeringValue(angle * MathUtils.degreesToRadians, 0);
		vehicle.setSteeringValue(angle * MathUtils.degreesToRadians, 1);
		
	}
	
	public boolean collectItem(btCollisionObject colObj) {
		Gdx.app.log(null, "vehicle.collectItem()");
		if (colObj.userData instanceof ItemObject) {
			Gdx.app.log(null, "vehicle.collectItem(), ITEM_OBJECT");
			ItemObject item = ((ItemObject) colObj.userData);
			item.reset();
			bulletWorld.world.countItems++;
		}
		return true;
	}
	
	
	public int getSpeed() {
		return (int) vehicle.getCurrentSpeedKmHour();
	}
	
	@Override
	public void draw(ModelBatch batch, Environment env, boolean flag) {
		
		fsm.update();
		
		for (int i = 0; i < 4; i++) {
			vehicle.getWheelInfo(i).getWorldTransform().getOpenGLMatrix(wheelInstances[i].transform.val);
		}
		
		batch.render(instance, env);
		for (ModelInstance wheel : wheelInstances) {
			batch.render(wheel, env);
		}
		
		//Gdx.app.log(null, "SPEED = " + getSpeed());
		
		//Gdx.app.log(null, " ватерион = " + instance.transform.getRotation(new Quaternion()));

	}

}
