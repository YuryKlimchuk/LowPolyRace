package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btConvexHullShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btShapeHull;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.botllelab.lowpolyrace.mvc.world.World;
import com.botllelab.lowpolyrace.utils.Constants;


public class BulletWorld extends btDiscreteDynamicsWorld implements Disposable {
	
	private Vector3 bufferVector3 = new Vector3();
	
	public Array<BulletEntity> entities = new Array<BulletEntity>();
	
	public Array<ItemObject> items = new Array<ItemObject>();
	
	public World world;
	
	
	public Vehicle vehicle;

	public BulletWorld(
			btCollisionDispatcher _dispatcher, 
			btDbvtBroadphase _broadphase,
			btSequentialImpulseConstraintSolver _solver,
			btDefaultCollisionConfiguration _collisionConfiguration,
			World _world) {	
		super(_dispatcher, _broadphase, _solver, _collisionConfiguration);
		setGravity(Constants.GRAVITY);
		
		world = _world;
		
	}
	
	
	public void addGroundObject(final Model model) {
		btCollisionShape shape = Bullet.obtainStaticNodeShape(model.nodes);
		
		btRigidBody body = new btRigidBody(0f, null, shape, Vector3.Zero);
		GroundObject ground = new GroundObject(model, body);
		
		addRigidBody(body);
		entities.add(ground);
		
	}
	
	public void addVehicle(Model _chassisModel, float _mass, Vector3 _chassisPos, Model _wheelModel) {
		btConvexHullShape chassisShape = createConvexHullShape(_chassisModel, true);
		chassisShape.calculateLocalInertia(_mass, bufferVector3);
		Vector3 localInertia = bufferVector3;
		
		btRigidBody chassisBody = new btRigidBody(_mass, null, chassisShape, localInertia);
		vehicle = new Vehicle(_chassisModel, _wheelModel, chassisBody, this);
		vehicle.instance.transform.setTranslation(_chassisPos);
		
		MotionState motionState = new MotionState(vehicle.instance.transform);
		chassisBody.setMotionState(motionState);
		
		chassisBody.setActivationState(Collision.DISABLE_DEACTIVATION);
		
		
		addRigidBody(chassisBody);
		entities.add(vehicle);
		
	}
	
	public void addItem(Model _itemModel, Vector3 _itemPosition) {
		ItemObject item = new ItemObject(_itemModel, _itemPosition, this);
		addRigidBody(item.body);
		items.add(item);
	}


	public void update(float delta) {
		stepSimulation(delta);
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	public static btConvexHullShape createConvexHullShape (final Model model, boolean optimize) {
		final Mesh mesh = model.nodes.get(0).parts.get(0).meshPart.mesh;
		final btConvexHullShape shape = new btConvexHullShape(mesh.getVerticesBuffer(), mesh.getNumVertices(), mesh.getVertexSize());
		if (!optimize) return shape;
		// now optimize the shape
		final btShapeHull hull = new btShapeHull(shape);
		hull.buildHull(shape.getMargin());
		final btConvexHullShape result = new btConvexHullShape(hull);
		// delete the temporary shape
		shape.dispose();
		hull.dispose();
		return result;
	}

}
