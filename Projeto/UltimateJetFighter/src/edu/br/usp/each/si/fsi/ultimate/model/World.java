package edu.br.usp.each.si.fsi.ultimate.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public class World {

	/** The blocks making up the world **/
	ArrayList<Block> blocks = new ArrayList<Block>();
	/** The enemies that are in the world **/
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	/** Our player controlled hero **/
	Jet jet;
	/** Contains all the jet's shots/bullets that are on screen */
	ArrayList<Shot> jetShots = new ArrayList<Shot>();
	/** Contains all the enemy's shots/bullets that are on screen */
	ArrayList<Shot> enemiesShots = new ArrayList<Shot>();
	/** A world has a level through which Bob needs to go through **/
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

	public ArrayList<Shot> getJetShots() {
		return this.jetShots;
	}

	public ArrayList<Shot> getEnemiesShots() {
		return this.enemiesShots;
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

	// --------------------
	public World() {
		this.killCount = 0;
		createDemoWorld();
	}

	public void shoot(Object source) {
		if (source instanceof Jet) {
			Shot bullet = new Shot(((Jet) source).getPosition().cpy(), "");
			bullet.getPosition().y += jet.getSize() / 2 - bullet.getSize() / 2;// ((Jet)
																				// source).getShot();
			this.jetShots.add(bullet);
		}
	}

	private void createDemoWorld() {
		jet = new Jet(new Vector2(3, 5), new Shot(new Vector2(0, 0),
				"images/shot.png"));
		level = new Level();
		// this.blocks = getDrawableBlocks(level.getWidth(), level.getHeight());
	}

	public void createEnemies() {
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

				Enemy enemy = new Enemy(new Vector2(-2, yPosition));
				enemy.setyDirection(yDirection);

				enemies.add(enemy);
			}

		}

	}

	public void updateEnemies(float delta) {
		List<Enemy> rmvEnemies = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			if (enemy.position.x > level.getWidth() || enemy.position.y + enemy.getSize() < 0 || enemy.position.y - enemy.getSize() > level.getHeight())
				rmvEnemies.add(enemy);
		}
		enemies.removeAll(rmvEnemies);
		for (Enemy enemy : enemies) {
			enemy.update(delta);
		}

	}

	public void updateJetShots(float delta) {
		List<Shot> rmvShots = new ArrayList<Shot>();
		for (Shot shot : jetShots) {
			if (shot.position.x > level.getWidth() || (shot.position.x < 0))
				rmvShots.add(shot);
		}
		jetShots.removeAll(rmvShots);
		for (Shot shot : jetShots) {
			shot.update(delta);
		}
	}
}
