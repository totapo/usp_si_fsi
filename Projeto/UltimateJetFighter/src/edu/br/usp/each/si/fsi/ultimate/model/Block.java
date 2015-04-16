package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public static final float SIZE=1f;
	
	Vector2 position = new Vector2();
	Rectangle   bounds = new Rectangle();
	 
	public Block(Vector2 pos) {
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public Vector2 getPosition(){
		return this.position;
	}

}
