package edu.br.usp.each.si.fsi.ultimate.model;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class Level {
	private int width;
	private int height;
	private Block[][] blocks;
	private ArrayList<Enemy> enemies;
	private Stage stage;
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	private Texture background;
	private ArrayList<Item> itens;

	public Texture getBackground(){
		return background;
	}
	
	public ArrayList<Item> getItens(){
		return itens;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public Level() {
		loadDemoLevel();
		
	}
	
	public Level(Texture background, ArrayList<Item> itens) {
		this.background = background;
		this.itens = itens;
		loadDemoLevel();
	}

	public Block get(int x, int y) {
		return blocks[x][y];
	}

	private void loadDemoLevel() {
		width = 10;
		height = 7;
		/*blocks = new Block[width][height];
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}

		for (int col = 0; col < 10; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
			blocks[col][6] = new Block(new Vector2(col, 6));
			if (col > 2) {
				blocks[col][1] = new Block(new Vector2(col, 1));
			}
		}
		blocks[9][2] = new Block(new Vector2(9, 2));
		blocks[9][3] = new Block(new Vector2(9, 3));
		blocks[9][4] = new Block(new Vector2(9, 4));
		blocks[9][5] = new Block(new Vector2(9, 5));

		blocks[6][3] = new Block(new Vector2(6, 3));
		blocks[6][4] = new Block(new Vector2(6, 4));
		blocks[6][5] = new Block(new Vector2(6, 5));*/
	}
}
