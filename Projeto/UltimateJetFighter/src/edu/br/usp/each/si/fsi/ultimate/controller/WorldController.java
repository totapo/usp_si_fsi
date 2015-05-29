package edu.br.usp.each.si.fsi.ultimate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Bullet;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy.EnemyType;
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
	private double time;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.MOVE, false);
		keys.put(Keys.BOOST, false);
		keys.put(Keys.FIRE, false);
	};

	public WorldController(World world) {
		this.world = world;
		this.jet = world.getJet();
		time=0;
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
		float x = ((click.x - jet.getSize() / 2) - 1);
		if (x < 0)
			x = 0;
		float y = click.y - jet.getSize() / 2;
		if (y < 0)
			y = 0;
		else if (y + jet.getSize() / 2 > 7 - jet.getSize() / 2)
			y = 7 - jet.getSize();// 6+jet.getSize()/2;
		jet.getPosition().set(x, y);
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
		time+=Gdx.graphics.getRawDeltaTime();
		checkCollisionWithBlocks(delta);
		checkCollisionWithEnemies(delta);
		checkCollisionWithBullets(delta);
		killEnemies(delta);
		moveNormalEnemies(delta);
		moveSpecialEnemies(delta);
		makeEnemiesShoot(delta);
		world.updateEnemies(delta);
		world.updateSpecialEnemies(delta);
		world.createSpecialEnemies();
		world.updateJetShots(delta);
		world.updateEnemyShots(delta);
		world.createNormalEnemies();
		jet.update(delta);
	}

	private void makeEnemiesShoot(float delta) {
		for(Enemy enemy: world.getEnemies()){
			if(time-enemy.getPreviousShot()>=enemy.getTimerShot()){
				world.shoot(enemy);
				enemy.setPreviousShot(time);
			}
		}
		for(Enemy enemy: world.getSpecialEnemies()){
			if(time-enemy.getPreviousShot()>=enemy.getTimerShot()){
				world.shoot(enemy);
				enemy.setPreviousShot(time);
			}
		}
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
		if (jet.getState() != Jet.State.DYING) {
			Rectangle jetRect = new Rectangle(jet.getPosition().x+jet.getSize()/2-jet.getSize()/8,
					jet.getPosition().y+jet.getSize()/2-jet.getSize()/8, jet.getBounds().width,
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
					jet.setState(Jet.State.DYING);
					jet.setStateTime(0);
					world.downgradeDmgJet();
					break;
				}
			}

			for (Enemy enemy : world.getSpecialEnemies()) {

				if (enemy == null)
					continue;
				else {
					enemy.getBounds().x = enemy.getPosition().x;
					enemy.getBounds().y = enemy.getPosition().y;
				}
				if (jetRect.overlaps(enemy.getBounds())) {
					jet.setState(Jet.State.DYING);
					jet.setStateTime(0);
					world.downgradeDmgJet();
					break;
				}
			}
		}
	}

	private void checkCollisionWithBullets(float delta) {
		Rectangle jetRect = new Rectangle(jet.getPosition().x+jet.getSize()/2-jet.getSize()/8,
				jet.getPosition().y+jet.getSize()/2-jet.getSize()/8, jet.getBounds().width,
				jet.getBounds().height);

		// if jet collides, make his position (3, 5)
		for (Bullet shot : world.getEnemiesShots()) {
			if (shot == null)
				continue;
			else {
				shot.getBounds().x = shot.getPosition().x;
				shot.getBounds().y = shot.getPosition().y;
			}
			if (jetRect.overlaps(shot.getBounds())) {
				jet.setState(Jet.State.DYING);
				world.downgradeDmgJet();
				// jet.getPosition().set(new Vector2(3, 5));
				break;
			}
		}
	}

	private void killEnemies(float delta) {
		List<Bullet> shotsHit = new ArrayList<Bullet>();
		List<Enemy> enemiesTemp = world.getSpecialEnemies();
		for (Enemy enemy : enemiesTemp) {
			Rectangle enemyRect = new Rectangle(enemy.getPosition().x,
					enemy.getPosition().y, enemy.getBounds().width,
					enemy.getBounds().height);

			// if enemy collides the bullet, make him disappear
			for (Bullet shot : world.getJetShots()) {
				if (shot == null)
					continue;
				else {
					shot.getBounds().x = shot.getPosition().x;
					shot.getBounds().y = shot.getPosition().y;
				}
				if (enemyRect.overlaps(shot.getBounds())
						&& enemy.getState() == Enemy.State.MOVING) {
					enemy.setHp(enemy.getHp() - Jet.DAMAGE);
					shotsHit.add(shot);
					if (enemy.getHp() <= 0) {
						enemy.setState(Enemy.State.DYING);
						enemy.setStateTime(0);
						world.setKillCount(world.getKillCount() + 1);
					}

					break;
				}
			}
		}
		enemiesTemp = world.getEnemies();
		for (Enemy enemy : enemiesTemp) {
			Rectangle enemyRect = new Rectangle(enemy.getPosition().x,
					enemy.getPosition().y, enemy.getBounds().width,
					enemy.getBounds().height);

			// if enemy collides the bullet, make him disappear
			for (Bullet shot : world.getJetShots()) {
				if (shot == null)
					continue;
				else {
					shot.getBounds().x = shot.getPosition().x;
					shot.getBounds().y = shot.getPosition().y;
				}
				if (enemyRect.overlaps(shot.getBounds())
						&& enemy.getState() == Enemy.State.MOVING) {
					enemy.setHp(enemy.getHp() - Jet.DAMAGE);
					shotsHit.add(shot);
					if (enemy.getHp() <= 0) {
						enemy.setState(Enemy.State.DYING);
						enemy.setStateTime(0);
						world.setKillCount(world.getKillCount() + 1);
						world.upDmgJet();
						
					}

					break;
				}
			}
		}
		world.getJetShots().removeAll(shotsHit);
	}

	public void moveNormalEnemies(float delta) {
		for (Enemy enemy : world.getEnemies()) {

			enemy.getVelocity().x = Enemy.NORMAL_SPEED;
			enemy.getVelocity().y = enemy.getyDirection();
			enemy.getPosition().add(enemy.getVelocity());
		}
	}

	public void moveSpecialEnemies(float delta) {
		for (Enemy enemy : world.getSpecialEnemies()) {

			enemy.getVelocity().x = Enemy.SPECIAL_SPEED;
			float jetyPosition = jet.getPosition().y;
			float jetxPosition = jet.getPosition().x;
			if (jetxPosition <= enemy.getPosition().x) {
				enemy.getAcceleration().x = Enemy.SPECIAL_ACCELERATION;
				enemy.setyDirection(0);;
			} else {
				if (jetyPosition > enemy.getPosition().y) {
					enemy.setyDirection(Enemy.SPECIAL_SPEED);
				} else if (jetyPosition < enemy.getPosition().y) {
					enemy.setyDirection(-Enemy.SPECIAL_SPEED);
				} else {
					enemy.setyDirection(0);
				}
			}

			enemy.getVelocity().y = enemy.getyDirection();
			enemy.getVelocity().add(enemy.getAcceleration());
			enemy.getPosition().add(enemy.getVelocity());
		}
	}

	/** Change Jet's state and parameters based on input controls **/
	private void processInput() {
		if (jet.getState() != Jet.State.DYING) {
			if (keys.get(Keys.MOVE)) {
				jet.setState(State.MOVING);
			}
			if (!(keys.get(Keys.MOVE))) {
				jet.setState(State.IDLE);
			}
		}
	}
}
