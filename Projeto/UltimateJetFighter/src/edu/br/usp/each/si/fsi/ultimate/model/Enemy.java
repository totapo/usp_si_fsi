package edu.br.usp.each.si.fsi.ultimate.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
	
	
	public enum State{MOVING,DYING};
	public enum MoveType{NORMAL, SPECIAL};
	public final int ID_NORMAL = 1, ID_SPECIAL = 2; 
	
	private EnemyType type;

	public static final float NORMAL_SPEED = 0.02f; // unit per second
	public static final float SPECIAL_SPEED = 0.01f; // unit per second
	
	//boss' attributes
	public static final float BOSS_SPEED = 0.01f;
	public static final float BOSS_SIZE = 2f;
	public static final int BOSS_LIMIT = 1;
	//
	
	
	public static final float SPECIAL_ACCELERATION = 0.05f; // unit per second
	public static final float SIZE = 0.65f; 
	public static final int HP = 10;
	float stateTime = 0;
	
	private int id;
	
	private int maxLife, life; 
	private int id_shot;
	private int damage;
	private float velocityTemp;
	private int id_enemy_type;
	
	
	private double timerShot; //controle de tiros por segundo
	private double previousShot;
	private int maxNumber;
	private float size;
	

	double hp;
	
	float yDirection;
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	Vector2 velocity = new Vector2();
	Vector2 acceleration = new Vector2();
	

	State state = State.MOVING;
	private MoveType moveType;
	

	private Shot shot;


	public Enemy(Vector2 position, Shot shot, double timerShot, boolean isBoss) {
		this.position = position;
		this.bounds.height = (isBoss)? BOSS_SIZE:SIZE;
		this.bounds.width = (isBoss)? BOSS_SIZE:SIZE;
		size = (isBoss)? BOSS_SIZE:SIZE;
		hp = HP;
		maxLife=(int)hp;
		this.shot = shot;
		this.setTimerShot(timerShot);
		this.setPreviousShot(Gdx.graphics.getRawDeltaTime());
		
	}
	
	public Enemy(){
		
	}
	
	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_shot() {
		return id_shot;
	}

	public void setId_shot(int id_shot) {
		this.id_shot = id_shot;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getVelocityTemp() {
		return velocityTemp;
	}

	public void setVelocityTemp(float velocityTemp) {
		this.velocityTemp = velocityTemp;
	}

	public int getId_enemy_type() {
		return id_enemy_type;
	}

	public void setId_enemy_type(int id_enemy_type) {
		this.id_enemy_type = id_enemy_type;
		
	}
	
	public Shot getShot(){
		return shot;
	}
	
	public void setShot(Shot shot){
		this.shot = shot;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
		this.maxLife =(int) hp;
	}

	public Rectangle getBounds() {
		return this.bounds;
	}

	public Vector2 getPosition() {
		return this.position;
	}
	
	public Vector2 getAcceleration() {
		return acceleration;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public float getSize() {
		return size;
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
	
	public void setyDirection(float yDirection) {
		this.yDirection = yDirection;
	}

	public float getyDirection() {
		return yDirection;
	}
	
	public EnemyType getType() {
		return type;
	}


	public void setType(EnemyType type) {
		this.type = type;
		if(this.type.getId() == ID_NORMAL){
			moveType = MoveType.NORMAL;
		}else{
			moveType = MoveType.SPECIAL;
		}
	}
	

	public void update(float delta) {
		stateTime += delta;
		position.add(velocity.cpy().scl(delta));
	}

	public double getTimerShot() {
		return timerShot;
	}

	public void setTimerShot(double timerShot) {
		this.timerShot = timerShot;
	}

	public double getPreviousShot() {
		return previousShot;
	}

	public void setPreviousShot(double previousShot) {
		this.previousShot = previousShot;
	}
}
