package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Jet {

	public enum State{IDLE,MOVING,FAST_MOVING,SHOOTING,TAKING_DAMAGE,DYING};
	
	//private float speed;
	//private float size;
	private Shot shot; //TODO pode passar pra shots
	public static final float SPEED = 4f;  // unit per second
	public static final float SIZE = 0.5f; // half a unit
	public static final int DAMAGE = 5;
	float stateTime = 0;
	
	Vector2 position = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;

    public Jet(Vector2 position, Shot shot) {
    	this.shot = shot;
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
    
    public Rectangle getBounds() {
		return this.bounds;
	}
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public Shot getShot(){
		return new Shot(shot.position,shot.getImgSrc());
	}
	
	public float getSize(){
		return SIZE;
	}

	private boolean facingLeft;
	
	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public void setState(State newState) {
		this.state = newState;
	}
	
	public float getStateTime() {
		return stateTime;
	}

	public void update(float delta) {
		stateTime += delta;
		//if(position.x != destination.x)
		//position.add(velocity.cpy().scl(delta));
	}
}
