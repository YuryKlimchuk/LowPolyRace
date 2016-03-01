package com.botllelab.lowpolyrace.utils;

import com.badlogic.gdx.math.Vector3;

public class Constants {
	
	public static final int
		APP_HEIGHT = 480,
		APP_WIDTH = 800,
		
		MAX_SUB_STEPS = 5;
	
	public static final float
		FIXED_TIME_STEP = 1/60f;
	
	
	public static final Vector3
		GRAVITY = new Vector3(0f, -9.81f, 0f);
		

	public static final int 
	
		STEER_LEFT = 0,
		STEER_RIGHT = 1,
		STEER_NONE = 2,
		
		ACCELERATE_FORWARD = 0,
		ACCELERATE_BACKWARD = 1,
		ACCELERATE_NONE = 2,
	
		STATIC_MODEL = 0,
		DINAMIC_MESH_MODEL = 1,
		DINAMIC_SHAPE_HULL = 2,
		STATIC_BOX_SHAPE = 3,
		
		
		MAX_ITEMS_COUNT = 8;
	
	
	
	public static final float
	
	
		MAX_FORWARD_FORCE = 1500f,
		DELTA_FORCE = 150f,
	
		MAX_STEERING_ANGLE = 30f, // angle in deg
		DELTA_STEERING_ANGLE = 20f;
	

	public static final Vector3[] 
			initPoints = new Vector3[] {
				
				new Vector3(138f, 2.5f, 220f),
				new Vector3(138f, 2.5f, 140f),
				new Vector3(138f, 2.5f, 70f),
				
				new Vector3(138f, 2.5f, -220f),
				new Vector3(138f, 2.5f, -140f),
				new Vector3(138f, 2.5f, -70f),
				
				new Vector3(-138f, 2.5f, 220f),
				new Vector3(-138f, 2.5f, 140f),
				new Vector3(-138f, 2.5f, 70f),
				
				new Vector3(-138f, 2.5f, -220f),
				new Vector3(-138f, 2.5f, -140f),
				new Vector3(-138f, 2.5f, -70f)
				
			};
	

	
}

