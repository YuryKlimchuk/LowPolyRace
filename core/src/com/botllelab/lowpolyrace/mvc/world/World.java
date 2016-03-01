package com.botllelab.lowpolyrace.mvc.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.botllelab.lowpolyrace.mvc.world.bullet.BulletWorld;
import com.botllelab.lowpolyrace.mvc.world.bullet.CollisionCallBack;
import com.botllelab.lowpolyrace.mvc.world.bullet.ItemsPool;
import com.botllelab.lowpolyrace.mvc.world.bullet.RespPoint;
import com.botllelab.lowpolyrace.mvc.world.graphics.GraphicsWorld;
import com.botllelab.lowpolyrace.utils.Constants;

public class World implements Disposable {


	public BulletWorld bulletWorld;
	public GraphicsWorld graphicsWorld;
	
	private btCollisionDispatcher dispatcher;
	private btDbvtBroadphase broadphase;
	private btSequentialImpulseConstraintSolver solver;
	private btDefaultCollisionConfiguration collisionConfiguration;
	
	public Array<RespPoint> respPoints = new Array<RespPoint>();
	public ItemsPool itemsPool;
	private CollisionCallBack cb;
	
	public int countItems = 0;
	
	public World() {
		
		for (int i = 0; i < Constants.initPoints.length; i++) {
			respPoints.add(new RespPoint(Constants.initPoints[i], false));
		}
		
		initBulletWorld();
		initGraphicsWorld();
		
		
	
	}
	
	
	private void initGraphicsWorld() {
		graphicsWorld = new GraphicsWorld();
	}
	
	private void initBulletWorld() {
		Bullet.init();
		
		collisionConfiguration = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfiguration);
		solver = new btSequentialImpulseConstraintSolver();	
		broadphase = new btDbvtBroadphase();
		bulletWorld = new BulletWorld(dispatcher, broadphase, solver, collisionConfiguration, this);
		
		
		
		JsonReader json = new JsonReader();
		G3dModelLoader loader = new G3dModelLoader(json);
		ModelBuilder modelBuilder = new ModelBuilder();
		

		final Model terrainModel = loader.loadModel(Gdx.files.internal("models/track_desert_demo.g3dj"));
		bulletWorld.addGroundObject(terrainModel);
		
		

		final Model chassisModel = modelBuilder.createBox(
				2.5f, 0.8f, 3.5f,  
				new Material(new ColorAttribute(ColorAttribute.Diffuse, Color.GOLDENROD)),
				Usage.Position | Usage.Normal);
		
		final Model wheelModel = modelBuilder.createCylinder(
				0.8f, 0.6f, 0.8f, 100, 
				new Material(new ColorAttribute(ColorAttribute.Diffuse, Color.WHITE)), 
				Usage.Position | Usage.Normal);
		wheelModel.nodes.get(0).rotation.set(Vector3.Z, 90f);
		bulletWorld.addVehicle(chassisModel, 1000f, new Vector3(138f, 10f, 0f), wheelModel);
		
		final Model itemModel = modelBuilder.createCylinder(
				3f, 3f, 3f, 100, 
				new Material(new ColorAttribute(ColorAttribute.Diffuse, Color.RED)), 
				Usage.Position | Usage.Normal);
		
		//bulletWorld.addItem(itemModel, new Vector3(138f, 2.5f, 50f));
		//bulletWorld.addItem(itemModel, new Vector3(138f, 2.5f, 100f));
		//bulletWorld.addItem(itemModel, new Vector3(138f, 2.5f, -50f));
		
		
		for (int i = 0; i < Constants.MAX_ITEMS_COUNT; i++) {
			bulletWorld.addItem(itemModel, new Vector3().set(respPoints.get(i).position));
			respPoints.get(i).isUse = true;
		}
		
		
		
		cb = new CollisionCallBack();
		
		itemsPool = new ItemsPool(bulletWorld);
		
	}
	
	public void update(float delta) {
		bulletWorld.update(delta);
		graphicsWorld.update(delta);
	}
	
	@Override
	public void dispose() {
		graphicsWorld.dispose();
		bulletWorld.dispose();
		collisionConfiguration.dispose();
		dispatcher.dispose();
		broadphase.dispose();
		solver.dispose();
		cb.dispose();
	}



}
