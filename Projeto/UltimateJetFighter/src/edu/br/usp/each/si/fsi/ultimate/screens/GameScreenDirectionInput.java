package edu.br.usp.each.si.fsi.ultimate.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import edu.br.usp.each.si.fsi.ultimate.controller.WorldController;
import edu.br.usp.each.si.fsi.ultimate.controller.WorldControllerTest;
import edu.br.usp.each.si.fsi.ultimate.model.World;
import edu.br.usp.each.si.fsi.ultimate.view.WorldRenderer;

public class GameScreenDirectionInput implements Screen,InputProcessor{

	private World 			world;
	private WorldRenderer 	renderer;
	private WorldControllerTest	controller;
	private int width, height;

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldControllerTest(world);
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
		this.width = width;
		this.height = height;
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
		if (keycode == Keys.LEFT)
			controller.leftPressed();
		if (keycode == Keys.RIGHT)
			controller.rightPressed();
		if (keycode == Keys.Z)
			controller.upPressed();
		if (keycode == Keys.X)
			controller.firePressed();
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
			controller.leftReleased();
		if (keycode == Keys.RIGHT)
			controller.rightReleased();
		if (keycode == Keys.Z)
			controller.upReleased();
		if (keycode == Keys.X)
			controller.fireReleased();
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
		Gdx.app.debug("Jet","JetX: "+jetX+", JetY: "+jetY+"\nClcX: "+x+", ClcY: "+y
				+"\nNewX: "+newX+", NewY: "+newY);
		if(newX<jetX){
			controller.leftPressed();
		} 
		if(newX>jetX){
			controller.rightPressed();
		}
		if(newY<jetY){
			Gdx.app.debug("Jet","DOWN");
			controller.downPressed();
		} 
		if(newY>jetY){
			Gdx.app.debug("Jet","UP");
			controller.upPressed();
		}
		/*if (x < width / 2) {
			controller.leftPressed();
		}
		if (x > width / 2) {
			controller.rightPressed();
		}
		if(y > height / 2){
			controller.upPressed();
		}
		if(y < height / 2){
			controller.downPressed();
		}*/
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		float jetX = world.getJet().getPosition().x;
		float jetY = world.getJet().getPosition().y;
		float newX = renderer.convertPositionX(x);
		float newY = renderer.convertPositionY(y);
		if(newX<jetX){
			controller.leftReleased();
		} 
		if(newX>jetX){
			controller.rightReleased();
		}
		if(newY<jetY){
			controller.downReleased();
		} 
		if(newY>jetY){
			controller.upReleased();
		}
		/*if (x < width / 2) {
			controller.leftReleased();
		}
		if (x > width / 2) {
			controller.rightReleased();
		}
		if(y > height / 2){
			controller.upReleased();
		}
		if(y < height / 2){
			controller.downReleased();
		}*/
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
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
