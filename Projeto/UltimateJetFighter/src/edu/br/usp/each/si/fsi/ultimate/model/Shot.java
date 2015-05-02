package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Shot {
	public static final float SIZE = 0.1f; // 1/10 of a unit, for now

	private float speed;
	private String imgSrc;
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();

	public Shot(Vector2 position, String imgSrc) {
		this.speed=-0.1f;
		this.imgSrc = imgSrc;
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public String getImgSrc(){
		return this.imgSrc;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public float getSize() {
		return SIZE;
	}

	public void update(float delta) {
		position.x += speed;
		//position.add(velocity.cpy().scl(delta));
	}
}

