package edu.br.usp.each.si.fsi.ultimate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Jet.State;
import edu.br.usp.each.si.fsi.ultimate.model.Shot;
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
		 * 
		 * jet.getVelocity().set(destinationX, destinationY);
		 * jet.getVelocity().setLength(Jet.SPEED);// Não altera a velociade mas
		 * // sim a angulacao.
		 */
		jet.getPosition().set(click.x - jet.getSize() / 2,
				click.y - jet.getSize() / 2);
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
		checkCollisionWithEnemies(delta);
		checkCollisionWithBullets(delta);
		killEnemies(delta);
		world.updateEnemies(delta);
		world.updateJetShots(delta);
		world.createEnemies();
	}

	/** Collision checking **/
	private void checkCollisionWithBlocks(float delta) {

		Rectangle jetRect = new Rectangle(jet.getPosition().x,
				jet.getPosition().y, jet.getBounds().width,
				jet.getBounds().height);

		// if jet collides, make his position (3, 5)
		for (Block block : world.getBlocks()) {

			if (block == null)
				continue;
			else {
				block.getBounds().x = block.getPosition().x;
				block.getBounds().y = block.getPosition().y;
			}
			if (jetRect.overlaps(block.getBounds())) {
				jet.getPosition().set(new Vector2(3, 5));
				break;
			}
		}

	}

	private void checkCollisionWithEnemies(float delta) {

		Rectangle jetRect = new Rectangle(jet.getPosition().x,
				jet.getPosition().y, jet.getBounds().width,
				jet.getBounds().height);

		// if jet collides, make his position (3, 5)
		for (Enemy enemy : world.getEnemies()) {

			if (enemy == null)
				continue;
			else {
				enemy.getBounds().x = enemy.getPosition().x;
				enemy.getBounds().y = enemy.getPosition().y;
			}
			if (jetRect.overlaps(enemy.getBounds())) {
				jet.getPosition().set(new Vector2(3, 5));
				break;
			}
		}
	}

	private void checkCollisionWithBullets(float delta) {
		Rectangle jetRect = new Rectangle(jet.getPosition().x,
				jet.getPosition().y, jet.getBounds().width,
				jet.getBounds().height);

		// if jet collides, make his position (3, 5)
		for (Shot shot : world.getEnemiesShots()) {
			if (shot == null)
				continue;
			else {
				shot.getBounds().x = shot.getPosition().x;
				shot.getBounds().y = shot.getPosition().y;
			}
			if (jetRect.overlaps(shot.getBounds())) {
				jet.getPosition().set(new Vector2(3, 5));
				break;
			}
		}
	}

	private void killEnemies(float delta) {
		List<Enemy> enemieskilled = new ArrayList<Enemy>();
		List<Shot> shotsHit = new ArrayList<Shot>();
		for (Enemy enemy : world.getEnemies()) {
			Rectangle enemyRect = new Rectangle(enemy.getPosition().x,
					enemy.getPosition().y, enemy.getBounds().width,
					enemy.getBounds().height);

			// if enemy collides the bullet, make him disappear
			for (Shot shot : world.getJetShots()) {
				if (shot == null)
					continue;
				else {
					shot.getBounds().x = shot.getPosition().x;
					shot.getBounds().y = shot.getPosition().y;
				}
				if (enemyRect.overlaps(shot.getBounds())) {
					enemy.setHp(enemy.getHp() - Jet.DAMAGE);
					shotsHit.add(shot);
					if (enemy.getHp() <= 0) {
						enemieskilled.add(enemy);
						
					}
					
					break;
				}
			}
		}

		world.getEnemies().removeAll(enemieskilled);
		world.getJetShots().removeAll(shotsHit);
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
