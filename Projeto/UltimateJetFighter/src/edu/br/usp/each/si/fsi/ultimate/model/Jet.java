package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Jet {

	public enum State{IDLE,MOVING,FAST_MOVING,SHOOTING,TAKING_DAMAGE,DYING};
	
	//private float speed;
	//private float size;
	
	static final float SPEED = 2f;  // unit per second
	static final float SIZE = 0.5f; // half a unit

	
	Vector2 position = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;

    public Jet(Vector2 position) {
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
    
	//private boolean facingLeft;
	
	
	//GETTERS AND SETTERS
	/*public float getSpeed(){
		return this.speed;
	}
	
	public float getSize(){
		return this.size;
	}
	
	public void setSize(float size){
		this.size = size;
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}*/
}