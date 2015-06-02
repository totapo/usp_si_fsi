package edu.br.usp.each.si.fsi.ultimate.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class World {

	/** The blocks making up the world **/
	ArrayList<Block> blocks = new ArrayList<Block>();
	/** The enemies that are in the world **/
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	/** The special enemies that are in the world **/
	ArrayList<Enemy> specialEnemies = new ArrayList<Enemy>();
	/** Our player controlled hero **/
	Jet jet;
	/** Contains all the jet's shots/bullets that are on screen */
	ArrayList<Bullet> jetShots = new ArrayList<Bullet>();
	/** Contains all the enemy's shots/bullets that are on screen */
	ArrayList<Bullet> enemiesShots = new ArrayList<Bullet>();
	/** A world has a level through which jet needs to go through **/
	Level level;
	/** Player Kill count */
	int killCount;

	// Getters -----------

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public Jet getJet() {
		return jet;
	}

	public int getKillCount() {
		return this.killCount;
	};

	public void setKillCount(int n) {
		this.killCount = n;
	}

	public ArrayList<Bullet> getJetShots() {
		return this.jetShots;
	}

	public ArrayList<Bullet> getEnemiesShots() {
		return this.enemiesShots;
	}

	public ArrayList<Enemy> getSpecialEnemies() {
		return specialEnemies;
	}

	public void setSpecialEnemies(ArrayList<Enemy> specialEnemies) {
		this.specialEnemies = specialEnemies;
	}

	// --------------------

	/** Return only the blocks that need to be drawn **/
	public ArrayList<Block> getDrawableBlocks(int width, int height) {
		int x = (int) jet.getPosition().x - width;
		int y = (int) jet.getPosition().y - height;
		if (x < 0) {
			x = 0;
		}
		if (y < 0) {
			y = 0;
		}
		int x2 = x + 2 * width;
		int y2 = y + 2 * height;
		if (x2 > level.getWidth()) {
			x2 = level.getWidth() - 1;
		}
		if (y2 > level.getHeight()) {
			y2 = level.getHeight() - 1;
		}

		ArrayList<Block> blocks = new ArrayList<Block>();
		Block block;
		for (int col = x; col <= x2; col++) {
			for (int row = y; row <= y2; row++) {
				block = level.getBlocks()[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	// --------------------
	public World() {
		this.killCount = 0;
		createDemoWorld();
	}

	public void shoot(Object source) {
		Shot shot; Bullet bullet;
		if (source instanceof Jet) {
			shot =((Jet) source).getShot();
			for(int i=0; i<shot.getBulletsPerClick();i++){
				bullet = new Bullet(shot.getStartingAngle()+shot.getAngle()*i,shot.getSpeed(),jet.getPosition().cpy(),shot.getSize());
				bullet.getPosition().y += jet.getSize() / 2 - bullet.getSize() / 2;
				this.jetShots.add(bullet);
			}
		} else {
			Enemy enemy = (Enemy)source;
			shot = enemy.getShot();
			if(shot.isMultiAction()){
				for(int i=0; i<shot.getBulletsPerClick();i++){
					bullet = new Bullet(shot.getStartingAngle()+shot.getAngle()*i,shot.getSpeed(),enemy.getPosition().cpy(),shot.getSize());
					
					bullet.getPosition().y += enemy.getSize() / 2 - bullet.getSize() / 2;
					bullet.getPosition().x += enemy.getSize()-bullet.getSize();
					this.enemiesShots.add(bullet);
				}
			} else {
				bullet = new Bullet(shot.getStartingAngle()+shot.getAngle(),shot.getSpeed(),enemy.getPosition().cpy(),shot.getSize());
				bullet.getPosition().y += enemy.getSize() / 2 - bullet.getSize() / 2;
				bullet.getPosition().x += enemy.getSize()-bullet.getSize();
				shot.setStartingAngle(shot.getStartingAngle()+shot.getAngle());
				this.enemiesShots.add(bullet);
			}
			
		}
	}

	private void createDemoWorld() {
		jet = new Jet(new Vector2(3, 5), new Shot(new Vector2(0, 0),
				"images/shot.png",-8f,true));
		jet.getShot().setBulletsPerClick(3);
		jet.getShot().setAngle(15f);//30f);
		jet.getShot().setStartingAngle(75f);
		level = new Level();
		// this.blocks = getDrawableBlocks(level.getWidth(), level.getHeight());
	}

	public void createNormalEnemies() {
		if (enemies.isEmpty()) {
			Random rd = new Random();
			int nrEnemies = rd.nextInt(level.getHeight());
			List<Integer> positions = new ArrayList<Integer>();
			for (int i = 0; i < level.getHeight(); i++) {
				positions.add(i);
			}

			float yDirection;
			boolean isDirectionRight = rd.nextBoolean();
			if (isDirectionRight) {
				yDirection = ((float) rd.nextInt(200)) / 10000;
			} else {
				yDirection = -((float) rd.nextInt(200)) / 10000;
			}
			while (nrEnemies > 0) {
				Collections.shuffle(positions);
				int yPosition = positions.get(0);
				positions.remove(0);
				nrEnemies--;

				Enemy enemy = new Enemy(new Vector2(-2, yPosition),new Shot(new Vector2(0, 0),
						"images/enemyShotTest.png",3f,true),
						1f);
				enemy.getShot().setAngle(15);
				enemy.getShot().setStartingAngle(75);
				enemy.getShot().setBulletsPerClick(3);
				enemy.setyDirection(yDirection);

				enemies.add(enemy);
			}

		}

	}

	public void createSpecialEnemies() {
		if (specialEnemies.size() == 0) {

			/*
			 * List<Integer> positions = new ArrayList<Integer>(); for (int i =
			 * 0; i < level.getHeight(); i++) { if (!specialEnemies.contains(i))
			 * { positions.add(i); } }
			 */
			Random rd = new Random();

			int yPosition = rd.nextInt(level.getHeight());

			Enemy enemy = new Enemy(new Vector2(-2, yPosition),new Shot(new Vector2(-2, yPosition),
					"images/enemyShotTest.png",4f,false),
					0.02f);
			enemy.getShot().setAngle(10);
			enemy.getShot().setStartingAngle(0);
			enemy.getShot().setBulletsPerClick(36);
			enemy.setType(Enemy.EnemyType.SPECIAL);
			enemy.setHp(Enemy.HP * 2);
			specialEnemies.add(enemy);

		}

	}

	public void updateEnemies(float delta) {
		List<Enemy> rmvEnemies = new ArrayList<Enemy>();
		int yDirectionTemp = 1;
		for (Enemy enemy : enemies) {
			if (enemy.position.x > level.getWidth())

				rmvEnemies.add(enemy);
			else if (enemy.position.y < 0
					|| enemy.position.y + enemy.getSize() > level.getHeight()) {
				yDirectionTemp = -1;
			}
		}
		enemies.removeAll(rmvEnemies);
		for (Enemy enemy : enemies) {
			enemy.yDirection *= yDirectionTemp;
			enemy.update(delta);
		}

	}

	public void updateSpecialEnemies(float delta) {
		List<Enemy> rmvEnemies = new ArrayList<Enemy>();
		for (Enemy enemy : specialEnemies) {
			if (enemy.position.x > level.getWidth())
				rmvEnemies.add(enemy);
		}
		specialEnemies.removeAll(rmvEnemies);
		for (Enemy enemy : specialEnemies) {
			enemy.update(delta);
		}

	}

	public void updateJetShots(float delta) {
		List<Bullet> rmvShots = new ArrayList<Bullet>();
		for (Bullet shot : jetShots) {
			if (shot.position.x > level.getWidth() || (shot.position.x < 0))
				rmvShots.add(shot);
		}
		jetShots.removeAll(rmvShots);
		for (Bullet shot : jetShots) {
			shot.update(delta);
		}
	}

	public void updateEnemyShots(float delta) {
		List<Bullet> rmvShots = new ArrayList<Bullet>();
		for (Bullet shot : enemiesShots) {
			if (shot.position.x > level.getWidth() || (shot.position.x < 0))
				rmvShots.add(shot);
		}
		enemiesShots.removeAll(rmvShots);
		for (Bullet shot : enemiesShots) {
			shot.update(delta);
		}
	}

	public void upDmgJet() {
		int bull = jet.getShot().getBulletsPerClick();
		bull++;
		if(bull<=5)jet.getShot().setBulletsPerClick(bull);
		switch(bull){
		case 1: 
			jet.getShot().setStartingAngle(90);break;
		case 2:
			jet.getShot().setAngle(20);
			jet.getShot().setStartingAngle(80);break;
		case 3:
			jet.getShot().setAngle(15);
			jet.getShot().setStartingAngle(75);break;
		case 4:
			jet.getShot().setAngle(10);
			jet.getShot().setStartingAngle(80);break;
		case 5:
			jet.getShot().setAngle(5);
			jet.getShot().setStartingAngle(80);break;
		default:break;
		}
	}

	public void downgradeDmgJet() {
		int bull = jet.getShot().getBulletsPerClick();
		bull--;
		if(bull>=1)jet.getShot().setBulletsPerClick(bull);
		switch(bull){
		case 1: 
			jet.getShot().setStartingAngle(90);break;
		case 2:
			jet.getShot().setAngle(20);
			jet.getShot().setStartingAngle(80);break;
		case 3:
			jet.getShot().setAngle(10);
			jet.getShot().setStartingAngle(75);break;
		case 4:
			jet.getShot().setAngle(10);
			jet.getShot().setStartingAngle(80);break;
		default:break;
		}
	}
}
