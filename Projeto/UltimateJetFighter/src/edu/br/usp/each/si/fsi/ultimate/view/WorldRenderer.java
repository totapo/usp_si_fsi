package edu.br.usp.each.si.fsi.ultimate.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.utils.SharedLibraryLoader;

import edu.br.usp.each.si.fsi.ultimate.Numbers;
import edu.br.usp.each.si.fsi.ultimate.controller.WorldController;
import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Bullet;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy.State;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Shot;
import edu.br.usp.each.si.fsi.ultimate.model.World;

public class WorldRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;
	private static final float JET_FRAME_DURATION = 0.06f;
	private static final float EXPLOSION_FRAME_DURATION = 0.05f;

	private World world;
	private OrthographicCamera cam;

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
		time=0;
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
		drawBlocks();
		drawJet();
		drawSpecialEnemies();
		drawNormalEnemies();
		
		drawJetShots();
		spriteBatch.end();
		if (debug)
			drawDebug();
		this.hudBatch.begin();
		drawHud();
		this.hudBatch.end();
	}
	
	private void drawHud(){
		hudBatch.draw(enemyTexture, 0.1f * ppuX,
					0.1f * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE
							* ppuY);
		hudBatch.draw(num.DDOTS.getTexture(),(0.1f)* ppuX,
				Enemy.SIZE * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE
				* ppuY);
		int kills = world.getKillCount();
		int[] positions=new int[(kills+"").length()];
		for(int i=positions.length-1;i>-1;i--){
			positions[i]=kills%10;
			kills = kills/10;
		}
		float aux=Enemy.SIZE+Enemy.SIZE/2;
		for(int a : positions){
			hudBatch.draw(Numbers.values()[a].getTexture(), 0.1f * ppuX,
					aux * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE
							* ppuY);
			aux+=Enemy.SIZE/2;
		}
		/*time += Gdx.graphics.getRawDeltaTime();
		int minutes = ((int)time) / 60;
	    int seconds = ((int)time) % 60;
	    positions=new int[(minutes+"").length()];
		aux = positions.length * Enemy.SIZE/2;
		for(int a : positions){
			hudBatch.draw(Numbers.values()[a].getTexture(), 0.1f * ppuX,
					(aux-7) * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE
							* ppuY);
			aux+=Enemy.SIZE/2;
		}
		hudBatch.draw(num.DDOTS.getTexture(),(0.1f)* ppuX,
				Enemy.SIZE * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE
				* ppuY);*/
		
	}

	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX,
					block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE
							* ppuY);
		}
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
				spriteBatch.draw(specialEnemyTexture, enemy.getPosition().x * ppuX,
						enemy.getPosition().y * ppuY, Enemy.SIZE * ppuX,
						Enemy.SIZE * ppuY);
			}
		}
		world.getSpecialEnemies().removeAll(enemiesDead);
	}

	private void drawJet() {
		Jet jet = world.getJet();
		if (jet.getState() != Jet.State.DYING) {
			jetFrame = jetAnimation.getKeyFrame(jet.getStateTime(), true);
		}else{
			jetFrame = deathAnimation.getKeyFrame(jet.getStateTime(), true);
			if(deathAnimation.isAnimationFinished(jet.getStateTime())){
				jet.setState(Jet.State.IDLE);
				jet.getPosition().set(new Vector2(9, 3));
			}
		}
		spriteBatch.draw(jetFrame, jet.getPosition().x * ppuX,
				jet.getPosition().y * ppuY, Jet.SIZE * ppuX, Jet.SIZE * ppuY);
	}

	private void drawJetShots() {
		for (Bullet bullet : world.getJetShots()) {
			spriteBatch.draw(
					shotTexture, 
					(bullet.getPosition().x)* ppuX,
					(bullet.getPosition().y) * ppuY,
					bullet.getSize()/2, 
					bullet.getSize()/2,
					bullet.getSize() * ppuX,
					bullet.getSize()* ppuY,
					1,1,
					bullet.getAngle()-90,
					0,0,
					shotTexture.getWidth(),
					shotTexture.getHeight(),
					false,false);
			/*
			texture,
			x,
			y,
			orign x e y,
			width height, 
			scalex e y, 
			rotation, 
			srcX, srcY, 
			srcwidthheight, bool flipxy
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