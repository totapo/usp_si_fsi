package edu.br.usp.each.si.fsi.ultimate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.ultimate.model.ActionType;
import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Bullet;
import edu.br.usp.each.si.fsi.ultimate.model.Effect;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
//import edu.br.usp.each.si.fsi.ultimate.model.Enemy.EnemyType;
import edu.br.usp.each.si.fsi.ultimate.model.Item;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Jet.State;
import edu.br.usp.each.si.fsi.ultimate.model.Shot;
import edu.br.usp.each.si.fsi.ultimate.model.World;

public class WorldController {

	enum Keys {
		BOOST, FIRE, MOVE
	}

	private static int TIMER_TAKING_DAMAGE = 2;
	
	private World world;
	private Jet jet;
	private double time;
	private boolean createBoss = true;
	private double lastTimeDamage;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.MOVE, false);
		keys.put(Keys.BOOST, false);
		keys.put(Keys.FIRE, false);
	};

	public WorldController(World world) {
		this.world = world;
		this.jet = world.getJet();
		time = 0;
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
		//checkCollisionWithBlocks(delta);
		//checkCollisionBetweenShots(delta); retirei essa colisao
		checkCollisionBetweenBulletsAndShield(delta);
		checkCollisionWithEnemies(delta);
		//if(jet.getState()==Jet.State.TAKING_DAMAGE){
		//	if(time-this.lastTimeDamage>=TIMER_TAKING_DAMAGE)
		//		jet.setState(Jet.State.MOVING);
		//} else
		checkCollisionWithBullets(delta);
		checkCollisionWithItens(delta);
		killEnemies(delta);
		moveNormalEnemies(delta);
		moveSpecialEnemies(delta);
		moveBoss(delta);
		makeEnemiesShoot(delta);
		world.updateEnemies(delta);
		world.updateSpecialEnemies(delta);
		world.updateJetShots(delta);
		world.createNormalEnemies(createBoss);
		world.createSpecialEnemies(createBoss);
		if (createBoss) {
			world.createBoss(delta);
			createBoss = false;
		}
		if (world.getBoss() != null)
			world.getBoss().update(delta);
		world.updateEnemyShots(delta,time);
		world.updatePlayerItems(time);
		world.updateDropItems(delta);
		//world.createNormalEnemies();
		jet.update(delta);
	}

	private void makeEnemiesShoot(float delta) {
		for(Enemy enemy: world.getEnemies()){
			if(time-enemy.getPreviousShot()>=enemy.getTimerShot()){
				world.shoot(enemy,time);
				enemy.setPreviousShot(time);
			}
		}
		for(Enemy enemy: world.getSpecialEnemies()){
			if(time-enemy.getPreviousShot()>=enemy.getTimerShot()){
				world.shoot(enemy,time);
				enemy.setPreviousShot(time);
			}
		}
		if(world.getBoss()!=null){
			if(time-world.getBoss().getPreviousShot()>=world.getBoss().getTimerShot()){
				if(world.getBoss().getHp()<world.getBossMaxHp()/2){
					Random rand = new Random();
					Shot shot;
					int type = rand.nextInt(3);
					switch(type){
					case 0:
						shot = new Shot(new Vector2(-2, world.getBoss().getPosition().y),
								"images/enemyShotTest.png",2f,ActionType.BOMB,0.5f,2);
						shot.setAngle(30);
						shot.setTimer(rand.nextDouble()*2+1);
						shot.setStartingAngle(90);
						shot.setBulletsPerClick(12);
						world.getBoss().setDamage(4);
						break;
					case 1:
						shot = new Shot(new Vector2(-2, world.getBoss().getPosition().y),
								"images/enemyShotTest.png",4f,ActionType.MULTI,0.2f,2);
						shot.setAngle(5);
						shot.setStartingAngle(65);
						shot.setBulletsPerClick(10);
						world.getBoss().setDamage(6);
						break;
					case 2:
						shot = new Shot(new Vector2(-2, world.getBoss().getPosition().y),
								"images/enemyShotTest.png",2f,ActionType.BOMB,0.3f,1);
						shot.setAngle(10);
						shot.setTimer(rand.nextDouble()*2+1);
						shot.setStartingAngle(90);
						shot.setBulletsPerClick(36);
						world.getBoss().setDamage(5);
						break;
					default:
						shot = new Shot(new Vector2(-2, world.getBoss().getPosition().y),
								"images/enemyShotTest.png",2f,ActionType.BOMB,0.5f,1);
						shot.setAngle(30);
						shot.setTimer(rand.nextDouble()*2+1);
						shot.setStartingAngle(90);
						shot.setBulletsPerClick(12);
						world.getBoss().setDamage(6);
					}
					world.getBoss().setShot(shot);
					world.getBoss().setTimerShot(rand.nextInt(2)+1.5f);
				}
				world.shoot(world.getBoss(),time);
				world.getBoss().setPreviousShot(time);
			}
		}
	}

	/** Collision checking **/
	private void checkCollisionBetweenBulletsAndShield(float delta){
		for(Item item: world.getPlayerItens()){
			if(item.getEffect() == Effect.SHIELD){
				Rectangle shieldRect = new Rectangle(jet.getPosition().x-item.getSize()/2,
						jet.getPosition().y+item.getSize()/2, jet.getSize()+item.getSize()/2,
						jet.getSize()+item.getSize()/2);
				List<Bullet> bullets = new ArrayList<Bullet>();
				for (Bullet shot : world.getEnemiesShots()) {
					if (shot == null)
						continue;
					else {
						shot.getBounds().x = shot.getPosition().x;
						shot.getBounds().y = shot.getPosition().y;
					}
					if (shieldRect.overlaps(shot.getBounds())) {
						bullets.add(shot);
						break;
					}
				}
				world.getEnemiesShots().removeAll(bullets);
			}
		}
	}
	
	private void checkCollisionWithItens(float delta) {
		if (jet.getState() != Jet.State.DYING) {
			Rectangle jetRect = new Rectangle(jet.getPosition().x,
					jet.getPosition().y, jet.getSize(),
					jet.getSize());

			List<Item> rmv = new ArrayList<Item>();
			for (Item item : world.getDropItens()) {

				if (item == null)
					continue;
				else {
					item.getBounds().x = item.getPosition().x;
					item.getBounds().y = item.getPosition().y;
				}
				if (jetRect.overlaps(item.getBounds())) {
					rmv.add(item);
					item = item.clonar();
					item.setStartTime(time);
					world.getPlayerItens().add(item);
				}
			}
			world.getDropItens().removeAll(rmv);
		}

	}

	private void checkCollisionWithEnemies(float delta) {
		if (jet.getState() != Jet.State.DYING) {
			Rectangle jetRect = new Rectangle(
					jet.getPosition().x + jet.getSize() / 2 - jet.getSize() / 8,
					jet.getPosition().y + jet.getSize() / 2 - jet.getSize() / 8,
					jet.getBounds().width, jet.getBounds().height);

			// if jet collides, make his position (3, 5)
			for (Enemy enemy : world.getEnemies()) {

				if (enemy == null)
					continue;
				else {
					enemy.getBounds().x = enemy.getPosition().x;
					enemy.getBounds().y = enemy.getPosition().y;
				}
				if (jetRect.overlaps(enemy.getBounds())) {
					//TODO game over
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
					//TODO game over
					jet.setState(Jet.State.DYING);
					jet.setStateTime(0);
					world.downgradeDmgJet();
					break;
				}
			}

			if (world.getBoss() != null) {
				Rectangle bossRect = new Rectangle(world.getBoss()
						.getPosition().x, world.getBoss().getPosition().y,
						Enemy.BOSS_SIZE, Enemy.BOSS_SIZE);
				if (jetRect.overlaps(bossRect)) {
					//TODO game over
					jet.setState(Jet.State.DYING);
					jet.setStateTime(0);
					world.downgradeDmgJet();
				}
			}

		}
	}

	private void checkCollisionWithBullets(float delta) {
		if (jet.getState() != Jet.State.DYING) {
			Rectangle jetRect = new Rectangle(
					jet.getPosition().x + jet.getSize() / 2 - jet.getSize() / 8,
					jet.getPosition().y + jet.getSize() / 2 - jet.getSize() / 8,
					jet.getBounds().width, jet.getBounds().height);
			
			// if jet collides, make his position (3, 5)
			List<Bullet> bullets = new ArrayList<Bullet>();
			for (Bullet shot : world.getEnemiesShots()) {
				if (shot == null)
					continue;
				else {
					shot.getBounds().x = shot.getPosition().x;
					shot.getBounds().y = shot.getPosition().y;
				}
				if (jetRect.overlaps(shot.getBounds())) {
					if(jet.getState()==Jet.State.TAKING_DAMAGE){
						//Nao remove vida nem bala
					} else {
						bullets.add(shot);
						jet.setLife(jet.getLife()-shot.getDamage());
						if(jet.getLife()==0){
							//TODO game Over;
						} else {
							world.getSoundDamage().play();
							jet.setState(Jet.State.TAKING_DAMAGE);
							this.lastTimeDamage = time;
							jet.setStateTime(0);
							world.downgradeDmgJet();
						}
						break;
					}
				}
			}
			world.getEnemiesShots().removeAll(bullets);
		}
	}
	
	private void checkCollisionBetweenShots(float delta) {
		List<Bullet> enemyBulletsRmv = new ArrayList<Bullet>();
		List<Bullet> jetBulletsRmv = new ArrayList<Bullet>();
		List<Bullet> enemyBullets = world.getEnemiesShots();
		List<Bullet> jetBullets = world.getJetShots();
		
		for (Bullet shot : jetBullets) {
			if (shot == null)
				continue;
			else {
				shot.getBounds().x = shot.getPosition().x;
				shot.getBounds().y = shot.getPosition().y;
				for(Bullet eShot:enemyBullets){
					if (eShot == null)
						continue;
					else {
						eShot.getBounds().x = eShot.getPosition().x;
						eShot.getBounds().y = eShot.getPosition().y;
					}
					if(shot.getBounds().overlaps(eShot.getBounds())){
						jetBulletsRmv.add(shot);
						if(eShot.getActionType()==ActionType.BOMB){
							world.explodeBomb(eShot, true,time);
							enemyBulletsRmv.add(eShot);
						} else if(eShot.getHp()==1){
							enemyBulletsRmv.add(eShot);
						} else {
							eShot.setHp(eShot.getHp()-1);
						}
						break;
					}
				}
			}
		}
		enemyBullets.removeAll(enemyBulletsRmv);
		jetBullets.removeAll(jetBulletsRmv);
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
					enemy.setHp(enemy.getHp() - jet.getDamage());
					shotsHit.add(shot);
					if (enemy.getHp() <= 0) {
						enemy.setState(Enemy.State.DYING);
						enemy.setStateTime(0);
						Random r = new Random();
						int chance = r.nextInt(100)+1;
						if(chance>=0){
							Gdx.app.debug("Item", "OK: "+enemy.getVelocity().len());
							world.dropItens(enemy.getPosition().cpy(),new Vector2(1,0));
						}
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
					enemy.setHp(enemy.getHp() - jet.getDamage());
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
		shotsHit.removeAll(shotsHit);
		if (world.getBoss() != null) {
			Rectangle bossRect = new Rectangle(world.getBoss().getPosition().x,
					world.getBoss().getPosition().y, Enemy.BOSS_SIZE,
					Enemy.BOSS_SIZE);

			// if enemy collides the bullet, make him disappear
			for (Bullet shot : world.getJetShots()) {
				if (shot == null)
					continue;
				else {
					shot.getBounds().x = shot.getPosition().x;
					shot.getBounds().y = shot.getPosition().y;
				}
				if (bossRect.overlaps(shot.getBounds())
						&& world.getBoss().getState() == Enemy.State.MOVING) {
					world.getBoss().setHp(
							world.getBoss().getHp() - jet.getDamage());
					shotsHit.add(shot);
					if (world.getBoss().getHp() <= 0) {
						world.getBoss().setState(Enemy.State.DYING);
						world.getBoss().setStateTime(0);
						world.setKillCount(world.getKillCount() + 20);
						//world.upDmgJet();
					}

					break;
				}
			}
		}
		world.getJetShots().removeAll(shotsHit);
	}

	/*
	 * public void moveAllenemies(float delta) { for (Enemy enemy :
	 * world.getEnemies()) { if (enemy.getMoveType() == Enemy.MoveType.NORMAL) {
	 * moveNormalEnemies(delta, enemy); } else { moveSpecialEnemies(delta,
	 * enemy); } } }
	 */

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
				enemy.setyDirection(0);

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

	public void moveBoss(float delta) {
		if (world.getBoss() != null) {
			if (world.getBoss().getPosition().x < Enemy.BOSS_LIMIT) {
				world.getBoss().getVelocity().x = Enemy.BOSS_SPEED;
				world.getBoss().getVelocity().y = world.getBoss()
						.getyDirection();
				world.getBoss().getPosition()
						.add(world.getBoss().getVelocity());
			} else {
				world.getBoss().getVelocity().x = 0;
				float jetyPosition = jet.getPosition().y+jet.getSize()/2;
				if (jetyPosition > world.getBoss().getPosition().y
						+ Enemy.BOSS_SIZE / 2) {
					world.getBoss().setyDirection(Enemy.BOSS_SPEED);
				} else if (jetyPosition < world.getBoss().getPosition().y
						+ Enemy.BOSS_SIZE / 2) {
					world.getBoss().setyDirection(-Enemy.BOSS_SPEED);
				} else {
					world.getBoss().setyDirection(0);
				}
				world.getBoss().getVelocity().y = world.getBoss()
						.getyDirection();
				world.getBoss().getPosition()
						.add(world.getBoss().getVelocity());
			}
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
