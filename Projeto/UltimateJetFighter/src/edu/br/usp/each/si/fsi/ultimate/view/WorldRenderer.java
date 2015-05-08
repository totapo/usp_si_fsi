package edu.br.usp.each.si.fsi.ultimate.view;
import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import edu.br.usp.each.si.fsi.ultimate.model.Block;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Shot;
import edu.br.usp.each.si.fsi.ultimate.model.World;

public class WorldRenderer {
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private World world;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	/** Textures **/
	private Texture jetTexture;
	private Texture blockTexture;
	private Texture enemyTexture;

	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;	// pixels per unit on the X axis
	private float ppuY;	// pixels per unit on the Y axis
	
	private ArrayList<Shot> auxRemoval;
	private Texture shotTexture;
	public void setSize (int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}
	
	public float getPpuX(){
		return ppuX;
	}
	
	public float getPpuY(){
		return ppuY;
	}
	
	public float convertPositionX(int x){
		return (float)x/ppuX;
	}
	
	public float convertPositionY(int y){
		return (float)(height-y)/ppuY;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		this.auxRemoval = new ArrayList<Shot>();
		loadTextures();
	}

	private void loadTextures() {
		jetTexture = new  Texture(Gdx.files.internal("images/jet.png"));
		blockTexture = new Texture(Gdx.files.internal("images/block.png"));
		enemyTexture = new Texture(Gdx.files.internal("images/enemy.png"));
		shotTexture = new Texture(Gdx.files.internal("images/shot.png"));
	}
	
	private Texture loadShotTexture(Shot shot){
		return new Texture(Gdx.files.internal(shot.getImgSrc()));
	}

	public void render() {
		spriteBatch.begin();
			drawBlocks();
			drawJet();
			drawEnemies();
			drawJetShots();
		spriteBatch.end();
		if (debug)
			drawDebug();
	}

	private void drawBlocks() {
		for (Block block : world.getBlocks()) {
			spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
		}
	}
	
	private void drawEnemies() {
		for (Enemy enemy : world.getEnemies()) {
			spriteBatch.draw(enemyTexture, enemy.getPosition().x * ppuX, enemy.getPosition().y * ppuY, Enemy.SIZE * ppuX, Enemy.SIZE * ppuY);
		}
	}

	private void drawJet() {
		Jet jet = world.getJet();
		spriteBatch.draw(jetTexture, jet.getPosition().x * ppuX, jet.getPosition().y * ppuY, Jet.SIZE * ppuX, Jet.SIZE * ppuY);
	}
	
	private void drawJetShots(){
		for(Shot shot:world.getJetShots()){
			spriteBatch.draw(shotTexture, shot.getPosition().x * ppuX, shot.getPosition().y * ppuY, Shot.SIZE * ppuX, Shot.SIZE * ppuY);
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
		// render Bob
		Jet jet = world.getJet();
		Rectangle rect = jet.getBounds();
		float x1 = jet.getPosition().x + rect.x;
		float y1 = jet.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}
	
	
}