package edu.br.usp.each.si.fsi.ultimate.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.br.usp.each.si.fsi.data.ScoreDAO;
import edu.br.usp.each.si.fsi.ultimate.Numbers;
import edu.br.usp.each.si.fsi.ultimate.controller.LevelLoader;
import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Bullet;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.controller.WorldController;
import edu.br.usp.each.si.fsi.ultimate.model.ActionType;
import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Bullet;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy.State;
import edu.br.usp.each.si.fsi.ultimate.model.Item;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Shot;
import edu.br.usp.each.si.fsi.ultimate.model.World;
import edu.br.usp.each.si.fsi.ultimate.screens.GameOverScreen;
import edu.br.usp.each.si.fsi.ultimate.screens.GameScreen;

public class WorldRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float JET_FRAME_DURATION = 0.06f;
	private static final float EXPLOSION_FRAME_DURATION = 0.05f;

	private World world;
	private OrthographicCamera cam;
	private LevelLoader lvlLoader;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	/** Textures **/
	private Texture jetTexture;
	private Texture blockTexture;
	private Texture enemyTexture;
	private Texture specialEnemyTexture;

	private SpriteBatch spriteBatch;
	private SpriteBatch hudBatch;
	private Numbers num;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	// animations
	private Animation jetAnimation;
	private Animation deathAnimation;

	private ArrayList<Shot> auxRemoval;
	private Texture shotTexture;

	// frames for animations
	private TextureRegion jetFrame;
	private TextureRegion enemyFrame;

	float time;
	private Texture enemyShotTexture;
	private Texture squareTexture;
	private Texture enemyBombTexture;

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public float getPpuX() {
		return ppuX;
	}

	public float getPpuY() {
		return ppuY;
	}

	public float convertPositionX(int x) {
		return (float) x / ppuX;
	}

	public float convertPositionY(int y) {
		return (float) (height - y) / ppuY;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		// lvlLoader = new LevelLoader();
		time = 0;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		this.hudBatch = new SpriteBatch();
		this.auxRemoval = new ArrayList<Shot>();
		loadJetAnimations();
		loadDeathAnimations();
		loadTextures();
		// loadItemAnimations(world.getItens());
	}

	private void loadTextures() {
		jetTexture = new Texture(
				Gdx.files.internal("images/sprites/jet/jet.png"));
		blockTexture = new Texture(Gdx.files.internal("images/block.png"));
		enemyTexture = new Texture(
				Gdx.files.internal("images/sprites/enemy/enemy.png"));
		specialEnemyTexture = new Texture(
				Gdx.files.internal("images/specialEnemy.png"));
		shotTexture = new Texture(Gdx.files.internal("images/shot.png"));
		enemyShotTexture = new Texture(
				Gdx.files.internal("images/enemyShotTest.png"));
		enemyBombTexture = new Texture(Gdx.files.internal("images/bala.png"));
		squareTexture = new Texture(Gdx.files.internal("images/quadrado.png"));
	}

	// load the jet animation
	private void loadJetAnimations() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/jet/textures.pack"));
		TextureRegion[] animationFrames = new TextureRegion[4];
		for (int i = 1; i < 5; i++) {
			animationFrames[i - 1] = atlas.findRegion("jet" + i);
		}
		jetAnimation = new Animation(JET_FRAME_DURATION, animationFrames);
	}

	// load the explosion animation
	private void loadDeathAnimations() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures/effects/textures.pack"));
		TextureRegion[] animationFrames = new TextureRegion[4];
		for (int i = 1; i < 5; i++) {
			animationFrames[i - 1] = atlas.findRegion("explosion" + i);
		}
		deathAnimation = new Animation(EXPLOSION_FRAME_DURATION,
				animationFrames);
	}

	private Texture loadShotTexture(Shot shot) {
		return new Texture(Gdx.files.internal(shot.getImgSrc()));
	}

	public void render() {
		spriteBatch.begin();
		drawLevel();
		drawJet();
		drawPlayerItens();
		drawItens(world.getDropItens());
		drawSpecialEnemies();
		drawNormalEnemies();
		drawBoss(world.getBoss());
		drawJetShots();
		drawEnemyShots();
		spriteBatch.end();
		if (debug)
			drawDebug();
		this.hudBatch.begin();
		drawHud();
		this.hudBatch.end();
		drawLifes();
	}

	private void drawPlayerItens() {
		Jet jet = world.getJet();
		for (Item item : world.getPlayerItens()) {
			spriteBatch.draw(item.getIconTexture(),
					jet.getPosition().x - item.getSize() / 2,
					jet.getPosition().y + item.getSize() / 2, jet.getSize()
							+ item.getSize() / 2,
					jet.getSize() + item.getSize() / 2);
		}
	}

	private void drawItens(ArrayList<Item> itens) {
		for (Item item : itens) {
			spriteBatch.draw(item.getIconTexture(),
					item.getPosition().x * ppuX, item.getPosition().y * ppuY,
					item.getSize() * ppuX, item.getSize() * ppuY);
		}
	}

	private void drawEnemyShots() {
		for (Bullet bullet : world.getEnemiesShots()) {
			spriteBatch
					.draw(((bullet.getActionType().equals(ActionType.BOMB)) ? enemyBombTexture
							: enemyShotTexture), bullet.getPosition().x * ppuX,
							bullet.getPosition().y * ppuY,
							(bullet.getSize() / 2) * ppuX,
							(bullet.getSize() / 2) * ppuY, bullet.getSize()
									* ppuX, bullet.getSize() * ppuY, 1, 1,
							bullet.getAngle() - 90, 0, 0, enemyShotTexture
									.getWidth(), enemyShotTexture.getHeight(),
							false, false);
			if (bullet.isSpin())
				bullet.setAngle(bullet.getAngle() + bullet.getVariationAngle());
		}
	}

	private void drawLifes() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Filled);
		// DRAW JET LIFE
		Jet jet = world.getJet();
		int max = jet.getMaxLife();
		int hp = jet.getLife();
		Rectangle rectMax = new Rectangle();
		rectMax.set((10 - 0.4f), (0.1f), 0.2f, 6.8f);
		debugRenderer.setColor(Color.BLACK);
		debugRenderer.rect(rectMax.x, rectMax.y, rectMax.width, rectMax.height);
		debugRenderer.end();
		if (hp > 0) {
			debugRenderer.begin(ShapeType.Filled);
			// 6.8 = max, x = hp, max x = 6.8 hp, x = 6.8 * hp /max
			Rectangle rectMin = new Rectangle();
			rectMin.set((10 - 0.4f), (0.1f), 0.2f, ((6.8f * hp) / max));
			debugRenderer.setColor(Color.GREEN);
			debugRenderer.rect(rectMin.x, rectMin.y, rectMin.width,
					rectMin.height);
			debugRenderer.end();
		}

		if (world.getBoss() != null) {
			// DRAW BOSS LIFE
			debugRenderer.setProjectionMatrix(cam.combined);
			debugRenderer.begin(ShapeType.Filled);
			Enemy boss = world.getBoss();
			max = world.getBossMaxHp();
			hp = (int) boss.getHp();
			rectMax = new Rectangle();
			rectMax.set((0.2f), (0.1f), 0.2f, 6.8f);
			debugRenderer.setColor(Color.BLACK);
			debugRenderer.rect(rectMax.x, rectMax.y, rectMax.width,
					rectMax.height);
			debugRenderer.end();
			if (hp > 0) {
				debugRenderer.begin(ShapeType.Filled);
				// 6.8 = max, x = hp, max x = 6.8 hp, x = 6.8 * hp /max
				Rectangle rectMin = new Rectangle();
				rectMin.set((0.2f), (0.1f), 0.2f, ((6.8f * hp) / max));
				debugRenderer.setColor(Color.RED);
				debugRenderer.rect(rectMin.x, rectMin.y, rectMin.width,
						rectMin.height);
				debugRenderer.end();
			}
		}
	}

	private void drawHud() {
		hudBatch.draw(enemyTexture, (9 - 0.3f) * ppuX, 0.1f * ppuY, Enemy.SIZE
				* ppuX, Enemy.SIZE * ppuY);
		hudBatch.draw(num.DDOTS.getTexture(), (9 - 0.3f) * ppuX, Enemy.SIZE
				* ppuY, Enemy.SIZE * ppuX, Enemy.SIZE * ppuY);
		int kills = world.getKillCount();
		int[] positions = new int[(kills + "").length()];
		for (int i = positions.length - 1; i > -1; i--) {
			positions[i] = kills % 10;
			kills = kills / 10;
		}
		float aux = Enemy.SIZE + Enemy.SIZE / 2;
		for (int a : positions) {
			hudBatch.draw(Numbers.values()[a].getTexture(), (9 - 0.3f) * ppuX,
					aux * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE * ppuY);
			aux += Enemy.SIZE / 2;
		}
		Jet jet = world.getJet();
		hudBatch.draw(squareTexture,
				(jet.getPosition().x + jet.getSize() / 2 - jet.getSize() / 8)
						* ppuX,
				(jet.getPosition().y + jet.getSize() / 2 - jet.getSize() / 8)
						* ppuY, jet.getSize() / 4 * ppuX, jet.getSize() / 4
						* ppuY);
		/*
		 * time += Gdx.graphics.getRawDeltaTime(); int minutes = ((int)time) /
		 * 60; int seconds = ((int)time) % 60; positions=new
		 * int[(minutes+"").length()]; aux = positions.length * Enemy.SIZE/2;
		 * for(int a : positions){
		 * hudBatch.draw(Numbers.values()[a].getTexture(), 0.1f * ppuX, (aux-7)
		 * * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE ppuY); aux+=Enemy.SIZE/2; }
		 * hudBatch.draw(num.DDOTS.getTexture(),(0.1f)* ppuX, Enemy.SIZE * ppuY,
		 * Enemy.SIZE * ppuX, Enemy.SIZE ppuY);
		 */

	}

	private void drawLevel() {
		world.getLevel().getBackground();
		spriteBatch.draw(world.getLevel().getBackground(), 0, 0, world
				.getLevel().getWidth() * ppuX, world.getLevel().getHeight()
				* ppuY);
	}

	private void drawNormalEnemies() {
		List<Enemy> enemiesDead = new ArrayList<Enemy>();
		for (Enemy enemy : world.getEnemies()) {
			if (enemy.getState() == Enemy.State.DYING) {
				enemyFrame = deathAnimation.getKeyFrame(enemy.getStateTime(),
						true);

				spriteBatch.draw(enemyFrame, enemy.getPosition().x * ppuX,
						enemy.getPosition().y * ppuY, Enemy.SIZE * ppuX,
						Enemy.SIZE * ppuY);
				if (deathAnimation.isAnimationFinished(enemy.getStateTime())) {
					enemiesDead.add(enemy);
				}

			} else {
				spriteBatch.draw(enemyTexture, enemy.getPosition().x * ppuX,
						enemy.getPosition().y * ppuY, Enemy.SIZE * ppuX,
						Enemy.SIZE * ppuY);
			}
		}
		world.getEnemies().removeAll(enemiesDead);
	}

	private void drawSpecialEnemies() {
		List<Enemy> enemiesDead = new ArrayList<Enemy>();
		for (Enemy enemy : world.getSpecialEnemies()) {
			if (enemy.getState() == Enemy.State.DYING) {
				enemyFrame = deathAnimation.getKeyFrame(enemy.getStateTime(),
						true);

				spriteBatch.draw(enemyFrame, enemy.getPosition().x * ppuX,
						enemy.getPosition().y * ppuY, Enemy.SIZE * ppuX,
						Enemy.SIZE * ppuY);
				if (deathAnimation.isAnimationFinished(enemy.getStateTime())) {
					enemiesDead.add(enemy);
				}

			} else {
				spriteBatch.draw(specialEnemyTexture, enemy.getPosition().x
						* ppuX, enemy.getPosition().y * ppuY,
						Enemy.SIZE * ppuX, Enemy.SIZE * ppuY);
			}
		}
		world.getSpecialEnemies().removeAll(enemiesDead);
	}

	private void drawBoss(Enemy boss) {
		if (boss != null) {
			if (boss.getState() == Enemy.State.DYING) {
				enemyFrame = deathAnimation.getKeyFrame(boss.getStateTime(),
						true);

				spriteBatch.draw(enemyFrame, boss.getPosition().x * ppuX,
						boss.getPosition().y * ppuY, Enemy.BOSS_SIZE * ppuX,
						Enemy.BOSS_SIZE * ppuY);
				if (deathAnimation.isAnimationFinished(boss.getStateTime())) {
					world.setBoss(null);
					ScoreDAO.ModifyScoreFile(world.getKillCount());
					((Game) Gdx.app.getApplicationListener())
					.setScreen(new GameOverScreen(world.getKillCount()));
				}

			} else {
				spriteBatch.draw(specialEnemyTexture, boss.getPosition().x
						* ppuX, boss.getPosition().y * ppuY, Enemy.BOSS_SIZE
						* ppuX, Enemy.BOSS_SIZE * ppuY);
			}
		}
	}


	private void drawJet() {
		Jet jet = world.getJet();
		if (jet.getState() != Jet.State.DYING) {
			jetFrame = jetAnimation.getKeyFrame(jet.getStateTime(), true);
		} else {
			jetFrame = deathAnimation.getKeyFrame(jet.getStateTime(), true);
			if (deathAnimation.isAnimationFinished(jet.getStateTime())) {
				ScoreDAO.ModifyScoreFile(world.getKillCount());
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameOverScreen(world.getKillCount()));
			}
		}

		spriteBatch.draw(jetFrame, jet.getPosition().x * ppuX,
				jet.getPosition().y * ppuY, jet.getSize() * ppuX, jet.getSize()
						* ppuY);
	}

	private void drawJetShots() {
		for (Bullet bullet : world.getJetShots()) {
			spriteBatch.draw(shotTexture, (bullet.getPosition().x) * ppuX,
					(bullet.getPosition().y) * ppuY, bullet.getSize() / 2,
					bullet.getSize() / 2, bullet.getSize() * ppuX,
					bullet.getSize() * ppuY, 1, 1, bullet.getAngle() - 90, 0,
					0, shotTexture.getWidth(), shotTexture.getHeight(), false,
					false);
			/*
			 * texture, x, y, orign x e y, width height, scalex e y, rotation,
			 * srcX, srcY, srcwidthheight, bool flipxy
			 */
		}
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for (Block block : world.getBlocks()) {
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render jet
		Jet jet = world.getJet();
		Rectangle rect = jet.getBounds();
		float x1 = jet.getPosition().x + rect.x;
		float y1 = jet.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}

}