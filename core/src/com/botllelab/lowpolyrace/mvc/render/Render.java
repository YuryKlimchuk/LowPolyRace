package com.botllelab.lowpolyrace.mvc.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Disposable;
import com.botllelab.lowpolyrace.mvc.world.World;
import com.botllelab.lowpolyrace.mvc.world.bullet.BulletEntity;


public class Render implements Disposable {
	
	public ModelBatch modelBatch;
	public Environment environment;
	public ChaseCamera camera;
	//public PerspectiveCamera camera;
	
	public World world;
	
	DebugDrawer debugDrawer;
	
	public Render(World _world) {
		world = _world;
		
		initCamera();
		initEnvironment();
		initModelBatch();
		
		debugDrawer = new DebugDrawer();
		debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
		world.bulletWorld.setDebugDrawer(debugDrawer);
		
		camera.transform = world.bulletWorld.vehicle.instance.transform;
	}
	
	
	public void render(float delta) {

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        
        //camera.transform = world.chassis.body.getWorldTransform();
        camera.update();
        
        modelBatch.begin(camera);
        
        	for (BulletEntity entity : world.bulletWorld.entities) {
				entity.draw(modelBatch, environment, true);
			}
        	
        	for (BulletEntity entity : world.bulletWorld.items) {
				entity.draw(modelBatch, environment, true);
			}
				
			
        modelBatch.end();
        
        debugDrawer.begin(camera);
        	//world.bulletWorld.debugDrawWorld();
        debugDrawer.end();
	}
	
	

	
	
	private void initCamera() {
		camera = new ChaseCamera(67f, 800, 480);
		
		
		//camera = new PerspectiveCamera(67f, 800, 480);
		camera.position.set(138f, 10f, -10f);
		camera.lookAt(138f, 3f, 0f);
		camera.far = 2000f;
        camera.near = 0.1f;
        camera.up.set(Vector3.Y);
        camera.update(); 
        
        
        
	}
	
	private void initModelBatch() {
		modelBatch = new ModelBatch();
	}
	
	private void initEnvironment() {
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.set(new ColorAttribute(new ColorAttribute(ColorAttribute.Fog, 0.13f, 0.13f, 0.13f, 1f)));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	@Override
	public void dispose() {}

}
