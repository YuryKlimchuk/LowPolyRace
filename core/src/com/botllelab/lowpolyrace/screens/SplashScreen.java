package com.botllelab.lowpolyrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.botllelab.lowpolyrace.GameManager;
import com.botllelab.lowpolyrace.utils.Constants;


public class SplashScreen implements Screen{

	GameManager gameManager = ((GameManager) Gdx.app.getApplicationListener());
    private boolean loadFlag = false;
    private Stage stage;
    private TextureAtlas atlas;

    @Override
    public void show() {
        Gdx.app.log(this.getClass().toString(), "show()");
        stage = new Stage(new StretchViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT));

        atlas = new TextureAtlas(Gdx.files.internal("splash/splash_screen.atlas"));

        Image logo = new Image(atlas.findRegion("company_logo"));
        logo.setSize(480f, 220f);
        logo.setPosition(160f, 160f);
        stage.addActor(logo);

        Image loadingPoint = new Image(atlas.findRegion("loading_point"));
        loadingPoint.setSize(60f, 60f);
        loadingPoint.setPosition(80f, 40f);
        loadingPoint.setOrigin(loadingPoint.getWidth()/2, loadingPoint.getHeight()/2);
        stage.addActor(loadingPoint);
        loadingPoint.addAction(Actions.forever(
                Actions.sequence(
                        Actions.parallel(
                                Actions.moveTo(660f, 40f, 1.5f, Interpolation.linear),
                                Actions.scaleTo(0.2f, 0.2f, 1.5f, Interpolation.linear),
                                Actions.fadeOut(1.5f, Interpolation.linear)
                        ),
                        Actions.parallel(
                                Actions.moveTo(80f, 40f),
                                Actions.scaleTo(1f, 1f),
                                Actions.fadeIn(0f)
                        )
                )
        ));
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(67 / 255f, 160 / 255f, 71 / 255f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if(gameManager.resMngr.finishLoad()) {
            if(!loadFlag) {
                loadFlag = true;
            }
            
            gameManager.resMngr.initResources();
            gameManager.screenMngr.setPlayScreen(1, 1);
            
        }

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
    	Gdx.app.log(this.getClass().toString(), "dispouse()");
    	stage.dispose();
    	atlas.dispose();	
    }
}
