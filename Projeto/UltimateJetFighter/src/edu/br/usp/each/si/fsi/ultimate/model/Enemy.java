package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	public static final float SPEED = 0.02f; // unit per second
	public static final float SIZE = 0.5f; // half a unit

	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();

	public Enemy(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
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
		position.x += SPEED;
		position.add(velocity.cpy().scl(delta));
	}
}
