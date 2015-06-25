package edu.br.usp.each.si.fsi.ultimate.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class World {

	/** The blocks making up the world **/
	ArrayList<Block> blocks = new ArrayList<Block>();
	/** The enemies that are in the world **/
	ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	/** The special enemies that are in the world **/
	ArrayList<Enemy> specialEnemies = new ArrayList<Enemy>();
	/** Our player controlled hero **/
	Enemy boss;
	Jet jet;
	/** Contains all the jet's shots/bullets that are on screen */
	ArrayList<Bullet> jetShots = new ArrayList<Bullet>();
	/** Contains all the enemy's shots/bullets that are on screen */
	ArrayList<Bullet> enemiesShots = new ArrayList<Bullet>();
	/** A world has a level through which jet needs to go through **/
	Level level;
	/** Lista de itens presentes no mundo*/
	ArrayList<Item> dropItens;
	/** Lista de itens que o usuário pegou*/
	ArrayList<Item> itensPlayer;
	/** Player Kill count */
	int killCount;
	private int bossMaxHp;
	// Getters -----------
	public Level getLevel(){
		return level;
	}
	
	public int getBossMaxHp(){
		return bossMaxHp;
	}
	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public Enemy getBoss() {
		return boss;
	}

	public void setBoss(Enemy boss) {
		this.boss = boss;
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

	public void setLevel(Level level) {
		this.level = level;
	}

	// --------------------
	public World() {
		this.killCount = 0;
		this.dropItens = new ArrayList<Item>();
		this.itensPlayer = new ArrayList<Item>();
		createDemoWorld();
	}

	public void shoot(Object source,double time) {
		Shot shot; Bullet bullet;
		if (source instanceof Jet) {
			shot =((Jet) source).getShot();
			for(int i=0; i<shot.getBulletsPerClick();i++){
				bullet = new Bullet(shot.getStartingAngle()+shot.getAngle()*i,shot.getSpeed(),jet.getPosition().cpy(),shot.getSize(),
						shot.getActionType());
				bullet.getPosition().y += jet.getSize() / 2 - bullet.getSize() / 2;
				this.jetShots.add(bullet);
			}
		} else {
			Enemy enemy = (Enemy) source;
			shot = enemy.getShot();
			if(shot.getActionType()==ActionType.MULTI){
				for(int i=0; i<shot.getBulletsPerClick();i++){
					bullet = new Bullet(shot.getStartingAngle()+shot.getAngle()*i,shot.getSpeed(),enemy.getPosition().cpy(),shot.getSize()
							,shot.getActionType());
					
					bullet.getPosition().y += enemy.getSize() / 2 - bullet.getSize() / 2;
					bullet.getPosition().x += enemy.getSize()-bullet.getSize();
					this.enemiesShots.add(bullet);
				}
			} else if(shot.getActionType()==ActionType.PROGRESSING){
				bullet = new Bullet(shot.getStartingAngle()+shot.getAngle(),shot.getSpeed(),enemy.getPosition().cpy(),shot.getSize(),
						shot.getActionType());
				bullet.getPosition().y += enemy.getSize() / 2 - bullet.getSize() / 2;
				bullet.getPosition().x += enemy.getSize()-bullet.getSize();
				shot.setStartingAngle(shot.getStartingAngle()+shot.getAngle());
				this.enemiesShots.add(bullet);
			} else if(shot.getActionType()==ActionType.BOMB){
				bullet = new Bullet(shot.getStartingAngle(),shot.getSpeed(),enemy.getPosition().cpy()
						,shot.getSize(),true,shot.getTimer(),time,shot.getAngle(),shot.getActionType(),shot.getPhases());
				bullet.setBullets(shot.getBulletsPerClick());
				bullet.getPosition().y += enemy.getSize() / 2 - bullet.getSize() / 2;
				bullet.getPosition().x += enemy.getSize()-bullet.getSize();
				this.enemiesShots.add(bullet);
			}
		}
	}
	
	public void explodeBomb(Bullet shot, boolean fromEnemy,double time){
		Bullet bullet;
		for(int i=0; i<shot.getBullets();i++){
			if(shot.getPhases()>1){
				bullet = new Bullet(shot.getAngle()+shot.getVariationAngle()*i,
						shot.velocity.len(),shot.getPosition().cpy()
						,shot.getSize()/2,true,shot.getTimer()/2,time,shot.getAngle(),ActionType.BOMB,shot.getPhases()-1);
				bullet.setBullets(shot.getBullets());
				Gdx.app.debug("Enemy", "bomb phase "+shot.getPhases()+": "+bullet.getBullets());
			} else {
				Gdx.app.debug("Enemy", "bomb phase "+shot.getPhases()+": "+shot.getBullets());
				bullet = new Bullet(shot.getAngle()+shot.getVariationAngle()*i,shot.velocity.len(),
						shot.getPosition().cpy(),shot.getSize()/2,ActionType.MULTI);
				bullet.getPosition().y += shot.getSize() / 4;
				bullet.getPosition().x += shot.getSize()/4;
				bullet.setSpin(shot.isSpin());
			}
			this.enemiesShots.add(bullet);
		}
	}

	private void createDemoWorld() {
		jet = new Jet(new Vector2(3, 5), new Shot(new Vector2(0, 0),
				"images/shot.png",-8f,ActionType.MULTI));
		jet.getShot().setBulletsPerClick(3);
		jet.getShot().setAngle(15f);// 30f);
		jet.getShot().setStartingAngle(75f);
		ArrayList<Item> itens = createItens();
		level = new Level(new Texture(
				Gdx.files.internal("images/backg.png")),itens);
		// this.blocks = getDrawableBlocks(level.getWidth(), level.getHeight());
	}
	
	private ArrayList<Item> createItens(){
		ArrayList<Item> itens = new ArrayList<Item>();
		itens.add(new Item(0.5f, new Texture(
				Gdx.files.internal("images/sprites/shield/shield3.png")), 5f,Effect.SHIELD,null));
		return itens;
	}

	public void createAllenemies() {
		if (enemies.isEmpty()) {
			int nrTypeEnemies = level.getEnemies().size();
			for (int i = 0; i < nrTypeEnemies; i++) {
				Random rd = new Random();
				int nrEnemies = rd.nextInt(level.getEnemies().get(i)
						.getMaxNumber());
				List<Integer> positions = new ArrayList<Integer>();
				for (int j = 0; j < level.getHeight(); j++) {
					positions.add(j);
				}

				while (nrEnemies > 0) {
					Collections.shuffle(positions);
					int yPosition = positions.get(0);
					positions.remove(0);
					nrEnemies--;

					Enemy enemy = new Enemy(new Vector2(-2, yPosition),new Shot(new Vector2(0, 0),
							"images/enemyShotTest.png",3f,ActionType.MULTI),
							1f,false);
					enemy.getShot().setAngle(15);
					enemy.getShot().setStartingAngle(75);
					enemy.getShot().setBulletsPerClick(3);

					enemies.add(enemy);
				}
			}

		}
	}

	public void createNormalEnemies(boolean createBoss) {
		if (enemies.isEmpty()) {
			boolean create=(boss!=null);
			create = (create)?(boss.getHp()>bossMaxHp/2):createBoss;
			if(create){
				Random rd = new Random();
				int nrEnemies = rd.nextInt(level.getHeight());
				int auxShotDefiner = nrEnemies;
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
					Enemy enemy;
					if(auxShotDefiner<=4){
						enemy= new Enemy(new Vector2(-2, yPosition),new Shot(new Vector2(0, 0),
							"images/enemyShotTest.png",3f,ActionType.MULTI),
							1f,false);
						enemy.getShot().setAngle(15);
						enemy.getShot().setStartingAngle(75);
						enemy.getShot().setBulletsPerClick(3);
						enemy.setyDirection(yDirection);
					}else{
						enemy= new Enemy(new Vector2(-2, yPosition),new Shot(new Vector2(0, 0),
								"images/enemyShotTest.png",3f,ActionType.MULTI),
								1f,false);
							enemy.getShot().setAngle(10);
							enemy.getShot().setStartingAngle(85);
							enemy.getShot().setBulletsPerClick(2);
							enemy.setyDirection(yDirection);
					}
	
					enemies.add(enemy);
				}
			}

		}

	}

	public void createSpecialEnemies(boolean createBoss) {
		if (specialEnemies.size() == 0) {
			boolean create=(boss!=null);
			create = (create)?(boss.getHp()>bossMaxHp/2):createBoss;
			if(create){
				Random rd = new Random();
	
				int yPosition = rd.nextInt(level.getHeight());
				// enemy.setType(Enemy.EnemyType.SPECIAL);
				Enemy enemy = new Enemy(new Vector2(-2, yPosition),new Shot(new Vector2(-2, yPosition),
						"images/enemyShotTest.png",4f,ActionType.PROGRESSING),
						0.08f,false);
				enemy.getShot().setAngle(10);
				//enemy.getShot().setTimer(1);
				enemy.getShot().setStartingAngle(90);
				enemy.getShot().setBulletsPerClick(36);
				enemy.setHp(Enemy.HP * 2);
				specialEnemies.add(enemy);
			}
		}

	}

	public void createBoss(float delta) {
		if (boss == null) {
			int yPosition;
			Random rd = new Random();
			yPosition = (int) (rd
					.nextInt((int) (level.getHeight() - 2 * Enemy.BOSS_SIZE)) + Enemy.BOSS_SIZE);

			this.boss = new Enemy(new Vector2(-2, yPosition), new Shot(new Vector2(-2, yPosition),
					"images/enemyShotTest.png",2f,ActionType.BOMB,0.5f,1), 5f, true);
			boss.getShot().setAngle(30);
			boss.getShot().setTimer(1);
			boss.getShot().setStartingAngle(90);
			boss.getShot().setBulletsPerClick(12);
			this.bossMaxHp = Enemy.HP * 80;
			this.boss.setHp(bossMaxHp);
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
	
	public void updateDropItems(float delta) {
		List<Item> rmvItems = new ArrayList<Item>();
		for (Item item : this.dropItens) {
			if (item.getPosition().x > level.getWidth())
				rmvItems.add(item);
		}
		dropItens.removeAll(rmvItems);
		for (Item item : this.dropItens) {
			item.update(delta);
		}
	}

	public void updateEnemyShots(float delta,double clock) {
		List<Bullet> rmvShots = new ArrayList<Bullet>();
		List<Bullet> bombs = new ArrayList<Bullet>();
 		for (Bullet shot : enemiesShots) {
			if (shot.position.x > level.getWidth() || (shot.position.x < 0))
				rmvShots.add(shot);
			else if(shot.getActionType().equals(ActionType.BOMB)){
				if(shot.getTimer()<=(clock-shot.getTimeStart())){
					bombs.add(shot);
					rmvShots.add(shot);
				}
			}
		}
		enemiesShots.removeAll(rmvShots);
		for (Bullet shot : enemiesShots) {
			shot.update(delta);
		}
		for (Bullet shot : bombs) {
			this.explodeBomb(shot, true,clock);
		}
	}

	public void upDmgJet() {
		int bull = jet.getShot().getBulletsPerClick();
		bull++;
		if (bull <= 5)
			jet.getShot().setBulletsPerClick(bull);
		switch (bull) {
		case 1:
			jet.getShot().setStartingAngle(90);
			break;
		case 2:
			jet.getShot().setAngle(20);
			jet.getShot().setStartingAngle(80);
			break;
		case 3:
			jet.getShot().setAngle(15);
			jet.getShot().setStartingAngle(75);
			break;
		case 4:
			jet.getShot().setAngle(10);
			jet.getShot().setStartingAngle(80);
			break;
		case 5:
			jet.getShot().setAngle(5);
			jet.getShot().setStartingAngle(80);
			break;
		default:
			break;
		}
	}

	public void downgradeDmgJet() {
		int bull = jet.getShot().getBulletsPerClick();
		bull--;
		if (bull >= 1)
			jet.getShot().setBulletsPerClick(bull);
		switch (bull) {
		case 1:
			jet.getShot().setStartingAngle(90);
			break;
		case 2:
			jet.getShot().setAngle(20);
			jet.getShot().setStartingAngle(80);
			break;
		case 3:
			jet.getShot().setAngle(10);
			jet.getShot().setStartingAngle(75);
			break;
		case 4:
			jet.getShot().setAngle(10);
			jet.getShot().setStartingAngle(80);
			break;
		default:
			break;
		}
	}
	
	public ArrayList<Item> getDropItens(){
		return this.dropItens;
	}
	
	public ArrayList<Item> getPlayerItens(){
		return this.itensPlayer;
	}
	
	public void dropItens(Vector2 position, Vector2 velocity){
			Item item = (Item)this.level.getItens().get(0).clonar();
			this.dropItens.add(item);
			item.setPosition(position);
			item.setVelocity(velocity);
	}

	public void updatePlayerItems(double time) {
		List<Item> rmv = new ArrayList<Item>();
		for(Item item:this.itensPlayer){
			if(time-item.getStartTime() >= item.getDuration()){
				rmv.add(item);
			}
		}
		this.itensPlayer.removeAll(rmv);
	}
}
