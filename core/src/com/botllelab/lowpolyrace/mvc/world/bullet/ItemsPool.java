package com.botllelab.lowpolyrace.mvc.world.bullet;

import com.badlogic.gdx.utils.Pool;

public class ItemsPool extends Pool<ItemObject> {

	private BulletWorld bulletWorld;
	
	public ItemsPool(BulletWorld _bulletWorld) {
		bulletWorld = _bulletWorld;
	}
	
	
	@Override
	protected ItemObject newObject() {
		
		return null;
	}

}
