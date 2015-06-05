package edu.br.usp.each.si.fsi.ultimate.model;

import java.util.List;

import edu.br.usp.each.si.fsi.data.*;

public class Stage {

	private int id;
	private int lvl;
	private float time;
	private int id_boss = 0;
	private String source;
	private List<StageEnemyInfo> enemiesInfo;

	public Stage() {

	}

	public Stage(int lvl, float time, String source) {
		this.lvl = lvl;
		this.time = time;
		this.source = source;
	}

	public List<StageEnemyInfo> getEnemiesInfo() {
		return enemiesInfo;
	}

	public void setEnemiesInfo(List<StageEnemyInfo> enemiesInfo) {
		this.enemiesInfo = enemiesInfo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public int getId_boss() {
		return id_boss;
	}

	public void setId_boss(int id_boss) {
		this.id_boss = id_boss;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
