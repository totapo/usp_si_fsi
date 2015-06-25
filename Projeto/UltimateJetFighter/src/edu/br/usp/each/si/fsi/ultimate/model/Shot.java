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
	private int bulletsPerClick; //quantidade de tiros com um clique.
	private float angle; //ângulo de variacao entre tiros
	private float startingAngle; //ângulo do primeiro tiro.
	private ActionType bulletType;
	private int phases;
	
	private double timer;

	private float size;
	
	public void setStartingAngle(float angle){
		this.startingAngle = angle;
	}
	
	public float getStartingAngle(){
		return startingAngle;
	}
	
	public void setAngle(float angle){
		this.angle = angle;
	}
	
	public void setBulletsPerClick(int bulletsPerClick){
		this.bulletsPerClick = bulletsPerClick;
	}
	
	public float getAngle(){
		return this.angle;
	}
	
	public ActionType getActionType(){
		return this.bulletType;
	}
	
	public int getBulletsPerClick(){
		return this.bulletsPerClick;
	}
	
	public Shot(Vector2 position, String imgSrc,float speed,ActionType action,float size,int phases) {
		this.speed=speed;//-0.1f;
		this.imgSrc = imgSrc;
		this.position = position;
		this.bounds.height = size;
		this.bounds.width = size;
		this.bulletType = action;
		this.size = size;
		this.phases=phases;
	}
	public Shot(Vector2 position, String imgSrc,float speed,ActionType action) {
		this.speed=speed;//-0.1f;
		this.imgSrc = imgSrc;
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		size = SIZE;
		this.bulletType = action;
		this.phases=0;
	}
	
	public int getPhases(){
		return phases;
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
		return size;
	}

	public float getSpeed() {
		return speed;
	}

	public double getTimer() {
		return timer;
	}

	public void setTimer(double timer) {
		this.timer = timer;
	}
}

