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
	private boolean multiAction;

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
	
	public int getBulletsPerClick(){
		return this.bulletsPerClick;
	}
	
	public boolean isMultiAction(){
		return this.multiAction;
	}
	
	public Shot(Vector2 position, String imgSrc,float speed,boolean multiAction) {
		this.speed=speed;//-0.1f;
		this.imgSrc = imgSrc;
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.multiAction = multiAction;
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

	public float getSpeed() {
		return speed;
	}
}

