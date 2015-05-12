package edu.br.usp.each.si.fsi.ultimate.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.ultimate.controller.WorldController;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.World;
import edu.br.usp.each.si.fsi.ultimate.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {

	private World world;
	private WorldRenderer renderer;
	private WorldController controller;

	private Jet jet;
	private Vector2 click;

	// private int width, height;

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
		jet = world.getJet();
		click = new Vector2();
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
		if (jet.getState() != Jet.State.DYING) {
			click.set(renderer.convertPositionX(x),
					renderer.convertPositionY(y));

			if (click.dst((jet.getPosition().x + jet.getSize() / 2
					)+1F,
					jet.getPosition().y + jet.getSize() / 2) <= 1F) {// TODO
																		// mudar
																		// o
																		// raio
				Gdx.app.debug("Jet", "mover");
				controller.movePressed(click);
			} else {
				Gdx.app.debug("Jet", "shoot");
				world.shoot(jet);
			}
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		controller.moveReleased();
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (jet.getState() != Jet.State.DYING) {
			click.set(renderer.convertPositionX(x),
					renderer.convertPositionY(y));

			if (click.dst((jet.getPosition().x + jet.getSize() / 2)+1F,
					jet.getPosition().y + jet.getSize() / 2) <= 1F) {// TODO
																		// mudar
																		// o
																		// raio
				Gdx.app.debug("Jet", "mover");
				controller.movePressed(click);
			} else {
				Gdx.app.debug("Jet", "nao mover");
			}
			return true;
		} else {
			return false;
		}
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
