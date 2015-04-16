package edu.br.usp.each.si.fsi.ultimate.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.ultimate.controller.WorldControllerTest.Keys;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Jet.State;
import edu.br.usp.each.si.fsi.ultimate.model.World;

public class WorldController {

	enum Keys {
		 BOOST, FIRE, MOVE
	}

	private World 	world;
	private Jet 	jet;

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

	public void movePressed(float destinationX,float destinationY){//,boolean xNegative, boolean yNegative){
		/*jet.changeVelocity(xNegative, yNegative);
		jet.setDestination(destinationX,destinationY);*/
		jet.getVelocity().set(destinationX,destinationY);
		jet.getVelocity().setLength(Jet.SPEED);//Não altera a velociade mas sim a angulacao.
		keys.get(keys.put(Keys.MOVE,true));
	}
	
	public void boostPressed() {
		keys.get(keys.put(Keys.BOOST, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, true));
	}
	
	public void moveReleased(){
		keys.get(keys.put(Keys.MOVE,false));
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
		if (keys.get(Keys.MOVE)) {
			jet.setState(State.MOVING);
		}
		if (!(keys.get(Keys.MOVE))){
			jet.setState(State.IDLE);
			// acceleration is 0 on the x
			jet.getAcceleration().x = 0;
			// horizontal speed is 0
			jet.getVelocity().x = 0;
			// acceleration is 0 on the y
			jet.getAcceleration().y = 0;
			// vertical speed is 0
			jet.getVelocity().y = 0;
		}
	}
}
