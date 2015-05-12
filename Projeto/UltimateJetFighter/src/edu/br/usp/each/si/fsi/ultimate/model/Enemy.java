package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
	
	public enum State{MOVING,DYING};

	public static final float SPEED = 0.02f; // unit per second
	public static final float SIZE = 0.5f; // half a unit
	public static final int HP = 10;
	float stateTime = 0;
	

	double hp;
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();
	State state = State.MOVING;

	


	public Enemy(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		hp = HP;
	}
	

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
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
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return this.state;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public void update(float delta) {
		stateTime += delta;
		position.x += SPEED;
		position.add(velocity.cpy().scl(delta));
	}
}
