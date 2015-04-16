package edu.br.usp.each.si.fsi.ultimate.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import edu.br.usp.each.si.fsi.ultimate.controller.WorldController;
import edu.br.usp.each.si.fsi.ultimate.model.World;
import edu.br.usp.each.si.fsi.ultimate.view.WorldRenderer;

public class GameScreen implements Screen,InputProcessor{

	private World 			world;
	private WorldRenderer renderer;
	private WorldController	controller;
	//private int width, height;

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
		Gdx.input.setInputProcessor(this);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	// * InputProcessor methods ***************************//

	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		float jetX = world.getJet().getPosition().x; //* renderer.getPpuX();
		float jetY = world.getJet().getPosition().y; //* renderer.getPpuY();
		float newX = renderer.convertPositionX(x);
		float newY = renderer.convertPositionY(y);
		float distX = -(jetX - newX);
		float distY = -(jetY - newY);
		Gdx.app.debug("Press\nJet","JetX: "+jetX+", JetY: "+jetY+"\nClcX: "+x+", ClcY: "+y
				+"\nNewX: "+newX+", NewY: "+newY);
		controller.movePressed(distX,distY);
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		controller.moveReleased();
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		float jetX = world.getJet().getPosition().x; //* renderer.getPpuX();
		float jetY = world.getJet().getPosition().y; //* renderer.getPpuY();
		float newX = renderer.convertPositionX(x);
		float newY = renderer.convertPositionY(y);
		float distX = -(jetX - newX);
		float distY = -(jetY - newY);
		Gdx.app.debug("Drag\nJet","JetX: "+jetX+", JetY: "+jetY+"\nClcX: "+x+", ClcY: "+y
				+"\nNewX: "+newX+", NewY: "+newY);
		controller.movePressed(distX,distY);
		return true;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
