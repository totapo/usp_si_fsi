package edu.br.usp.each.si.fsi.ultimate.controller;

import java.util.HashMap;
import java.util.Map;

import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Jet.State;
import edu.br.usp.each.si.fsi.ultimate.model.World;

public class WorldControllerTest {

	enum Keys {
		LEFT, RIGHT, BOOST, UP, DOWN, FIRE
	}

	private World 	world;
	private Jet 	jet;

	static Map<Keys, Boolean> keys = new HashMap<WorldControllerTest.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.BOOST, false);
		keys.put(Keys.FIRE, false);
	};

	public WorldControllerTest(World world) {
		this.world = world;
		this.jet = world.getJet();
	}

	// ** Key presses and touches **************** //

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void upPressed() {
		keys.get(keys.put(Keys.UP, true));
	}
	
	public void downPressed() {
		keys.get(keys.put(Keys.DOWN, true));
	}
	
	public void boostPressed() {
		keys.get(keys.put(Keys.BOOST, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void upReleased() {
		keys.get(keys.put(Keys.UP, false));
	}
	
	public void downReleased() {
		keys.get(keys.put(Keys.DOWN, false));
	}
	
	public void boostReleased() {
		keys.get(keys.put(Keys.BOOST, false));
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	/** The main update method **/
	public void update(float delta) {
		processInput();
		jet.update(delta);
	}

	/** Change Jet's state and parameters based on input controls **/
	private void processInput() {
		/*if (keys.get(Keys.LEFT)) {
			// left is pressed
			jet.setFacingLeft(true);
			jet.setState(State.MOVING);
			jet.getVelocity().x = -Jet.SPEED;
		}
		if (keys.get(Keys.RIGHT)) {
			// left is pressed
			jet.setFacingLeft(false);
			jet.setState(State.MOVING);
			jet.getVelocity().x = Jet.SPEED;
		}
		// need to check if both or none direction are pressed, then Jet is idle
		if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
				(!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
			jet.setState(State.IDLE);
			// acceleration is 0 on the x
			jet.getAcceleration().x = 0;
			// horizontal speed is 0
			jet.getVelocity().x = 0;
		}*/
	}
}
