package edu.br.usp.each.si.fsi.ultimate.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.ultimate.controller.WorldControllerTest.Keys;
import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Jet.State;
import edu.br.usp.each.si.fsi.ultimate.model.World;

public class WorldController {

	enum Keys {
		BOOST, FIRE, MOVE
	}

	private World world;
	private Jet jet;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.MOVE, false);
		keys.put(Keys.BOOST, false);
		keys.put(Keys.FIRE, false);
	};

	public WorldController(World world) {
		this.world = world;
		this.jet = world.getJet();
	}

	// ** Key presses and touches **************** //

	public void movePressed(Vector2 click) {// ,boolean
																		// xNegative,
																		// boolean
																		// yNegative){
		/*
		 * jet.changeVelocity(xNegative, yNegative);
		 * jet.setDestination(destinationX,destinationY);
		 
		jet.getVelocity().set(destinationX, destinationY);
		jet.getVelocity().setLength(Jet.SPEED);// Não altera a velociade mas
												// sim a angulacao.
		 * 
		 */
		jet.getPosition().set(click.x-jet.getSize()/2, click.y-jet.getSize()/2);
		keys.get(keys.put(Keys.MOVE, true));
	}

	public void boostPressed() {
		keys.get(keys.put(Keys.BOOST, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}

	public void moveReleased() {
		keys.get(keys.put(Keys.MOVE, false));
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
		checkCollisionWithBlocks(delta);
		jet.update(delta);
	}

	/** Collision checking **/
	private void checkCollisionWithBlocks(float delta) {
		// scale velocity to frame units
		//jet.getVelocity().cpy().scl(delta);

		Rectangle jetRect = new Rectangle(jet.getPosition().x,
				jet.getPosition().y, jet.getBounds().width,
				jet.getBounds().height);

		// if jet collides, make his position (3, 5)
		for (Block block : world.getBlocks()) {
			
			if (block == null)
				continue;
			else{
				block.getBounds().x = block.getPosition().x;
				block.getBounds().y = block.getPosition().y;
			}
			if (jetRect.overlaps(block.getBounds())) {
				jet.getPosition().set(new Vector2(3, 5));
				world.getCollisionRects().add(block.getBounds());
				break;
			}
		}

	}

	/** Change Jet's state and parameters based on input controls **/
	private void processInput() {
		if (keys.get(Keys.MOVE)) {
			jet.setState(State.MOVING);
		}
		if (!(keys.get(Keys.MOVE))) {
			jet.setState(State.IDLE);
		}
	}
}
