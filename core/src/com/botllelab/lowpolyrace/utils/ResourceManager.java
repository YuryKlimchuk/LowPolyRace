package com.botllelab.lowpolyrace.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.utils.Disposable;

public class ResourceManager implements Disposable{
	public AssetManager aManager = new AssetManager();
	
	
	public ResourceManager() {
		startLoad();
	}
	
	
	private void startLoad() {
		aManager.load("models/track_desert_demo.g3dj", Model.class);
		FileHandleResolver resolver = new InternalFileHandleResolver();
		aManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		aManager.setLoader(BitmapFont.class, ".TTF", new FreetypeFontLoader(resolver));
		
		FreeTypeFontLoaderParameter size1Params = new FreeTypeFontLoaderParameter();
		size1Params.fontFileName = "fonts/SHOWG.TTF";
		size1Params.fontParameters.size = 26;
		aManager.load("font26.TTF", BitmapFont.class, size1Params);


	}
	
	public boolean finishLoad() {
        return aManager.update();
    }

	
	public void initResources() {}
	

	@Override
	public void dispose() {
		aManager.dispose();
	}
}
