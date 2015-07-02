package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Item {
	private float size;
	private Texture icon;
	private Vector2 position;
	private Vector2 velocity;
	private Rectangle bounds;
	private double startTime;
	private double duration; //if less than 0, until the jet dies
	private Effect effect;
	private Animation animation; //can be null
	private int stateTime = 0;
	
	public Item (float size, Texture icon, double duration, Effect effect, Animation animation){
		this.size = size;
		this.bounds = new Rectangle();
		position = new Vector2();
		velocity = new Vector2();
		this.icon = icon;
		this.duration = duration;
		this.effect = effect;
		this.animation = animation;
		this.bounds.height = size;
		this.bounds.width = size;
	}
	
	public double getDuration(){
		return duration;
	}
	
	public Effect getEffect(){
		return effect;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}
	
	public float getSize(){
		return size;
	}
	
	public void setStartTime(double time){
		this.startTime = time;
	}
	
	public double getStartTime(){
		return this.startTime;
	}
	
	public void setPosition(Vector2 position){
		this.position = position;
	}
	
	public void setVelocity(Vector2 velocity){
		this.velocity = velocity;
	}
	
	public Vector2 getPosition(){
		return this.position;
	}
	
	public Vector2 getVelocity(){
		return this.velocity;
	}
	
	public Item clonar() {
		return new Item(size,icon,duration,effect,animation);
	}

	public Texture getIconTexture(){
		return icon;
	}
	
	public Animation getAnimation(){
		return animation;
	}
	
	public void update(float delta) {
		stateTime += delta;
		position.add(velocity.cpy().scl(delta));
	}
	
}
