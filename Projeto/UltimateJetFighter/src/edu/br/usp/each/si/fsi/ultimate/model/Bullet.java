package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
	private float angle;
	private float speed;
	private float size;
	Vector2 position = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	
	
	public Bullet(float ang, float speed,Vector2 position, float size){
		angle = ang;
		this.speed = speed;
		this.velocity=new Vector2(speed,0);
		velocity.rotate(angle-90);
		this.position = position;
		this.size = size;
		this.bounds = new Rectangle(position.x,position.y,size,size);
	}
	
	public float getAngle(){
		return angle;
	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}
	
	public float getSize() {
		return size;
	}

	public Vector2 getPosition() {
		return this.position;
	}
	
	public void update(float delta) {
		//position.x += speed;
		position.add(velocity.cpy().scl(delta));
	}
}
