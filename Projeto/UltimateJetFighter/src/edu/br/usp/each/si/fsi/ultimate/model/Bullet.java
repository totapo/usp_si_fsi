package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
	private float angle;
	private float size;
	private boolean spin;
	private double timer;
	private double timeStart;
	private int bullets;
	private float variationAngle;;
	private int phases;
	private ActionType actionType;
	private int hp;
	Vector2 position = new Vector2();
	Vector2 velocity = new Vector2();
	
	public int getPhases(){
		return phases;
	}
	
	public void setPhases(int phases){
		this.phases = phases;
	}
	
	public boolean isSpin() {
		return spin;
	}

	public void setSpin(boolean spin) {
		this.spin = spin;
	}

	public double getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

	public double getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(float timeStart) {
		this.timeStart = timeStart;
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}

	public float getVariationAngle() {
		return variationAngle;
	}

	public void setVariationAngle(float variationAngle) {
		this.variationAngle = variationAngle;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	Rectangle bounds = new Rectangle();
	
	public int getHp(){
		return hp;
	}
	
	public void setHp(int hp){
		this.hp = hp;
	}
	
	public Bullet(float ang, float speed,Vector2 position, float size, ActionType action){
		this.hp = 3;
		angle = ang;
		this.velocity=new Vector2(speed,0);
		velocity.rotate(angle-90);
		this.position = position;
		this.size = size;
		this.bounds = new Rectangle(position.x,position.y,size,size);
		this.actionType = action;
		this.phases=0;
	}
	
	public Bullet(float ang, float speed,Vector2 position, float size,boolean spin,
			double timer, double timeStart, float variationAngle, ActionType action, int phases){
		angle = ang;
		this.velocity=new Vector2(speed,0);
		velocity.rotate(angle-90);
		this.position = position;
		this.timer = timer;
		this.timeStart = timeStart;
		this.variationAngle = variationAngle;
		this.size = size;
		this.bounds = new Rectangle(position.x,position.y,size,size);
		this.actionType = action;
		this.phases=phases;
		this.spin = spin;
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
		position.add(velocity.cpy().scl(delta));
	}
}
