package edu.br.usp.each.si.fsi.ultimate.model;

public class EnemyType {
	private int id;
	private float height;
	private float width;
	private int hp;
	private String skin_path;
	
	
	public EnemyType(int id, float height, float width, int hp,
			String skin_path) {
		super();
		this.id = id;
		this.height = height;
		this.width = width;
		this.hp = hp;
		this.skin_path = skin_path;
	}

	public EnemyType(){
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public String getSkin_path() {
		return skin_path;
	}
	public void setSkin_path(String skin_path) {
		this.skin_path = skin_path;
	}
}
