package edu.br.usp.each.si.fsi.ultimate.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Jet {

	public enum State {
		IDLE, MOVING, FAST_MOVING, SHOOTING, TAKING_DAMAGE, DYING
	};

	// private float speed;
	// private float size;
	private Shot shot; // TODO pode passar pra shots
	private float speed = 4f; // unit per second
	private float size = 0.65f; // half a unit
	private int damage = 5;
	private float stateTime = 0;
	private String skin_path;
	private int hp;
	private int lvl;
	private long xp;
	private int defense;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;

	public Jet(Vector2 position, Shot shot) {
		this.shot = shot;
		this.position = position;
		this.bounds.height = size/4;
		this.bounds.width = size/4;
	}
	
	public Jet(){
		
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getSkin_path() {
		return skin_path;
	}

	public void setSkin_path(String skin_path) {
		this.skin_path = skin_path;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public long getXp() {
		return xp;
	}

	public void setXp(long xp) {
		this.xp = xp;
	}

	public void setShot(Shot shot) {
		this.shot = shot;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Shot getShot() {
		return shot;
	}

	public float getSize() {
		return size;
	}

	private boolean facingLeft;

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public State getState() {
		return state;
	}

	public void setState(State newState) {
		this.state = newState;
	}

	public float getStateTime() {
		return stateTime;
	}
	
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public void update(float delta) {
		stateTime += delta;
		// if(position.x != destination.x)
		// position.add(velocity.cpy().scl(delta));
	}
}
