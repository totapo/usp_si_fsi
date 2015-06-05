package edu.br.usp.each.si.fsi.ultimate.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.br.usp.each.si.fsi.data.*;
import edu.br.usp.each.si.fsi.ultimate.model.Enemy;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Level;
import edu.br.usp.each.si.fsi.ultimate.model.Stage;

public class LevelLoader {

	public static final String LEVEL_PREFIX = "levels/level-";

	public Level loadLevel(Jet jet, int stageNumber) {

		Level level = new Level();
		this.createFiles(jet);
		level.setStage(StageDAO.getStage(1));
		loadLevelEnemies(level, stageNumber);
		return level;

	}

	public void createFiles(Jet jet) {
		// create the jet xml file if it doesn't exist
		File fileTemp = new File(JetDAO.STORAGE_PATH);
		if (!fileTemp.exists()) {

			JetDAO.CreateJetFile(jet);

		}
		fileTemp = new File(StageDAO.STORAGE_PATH);
		if (!fileTemp.exists()) {
			Stage stage = new Stage(1, 500, "stage1");
			StageDAO.CreateStageFile(stage);
		}
	}
	
	public void loadLevelEnemies(Level level, int stageNumber){
		ArrayList<Enemy> levelEnemies = new ArrayList<Enemy>();
		List<StageEnemyInfo> infoEnemies = StageInfoDAO.getStageEnemiesInfo(stageNumber);
		level.getStage().setEnemiesInfo(infoEnemies);
		Enemy enemy = new Enemy();
		for(StageEnemyInfo info: infoEnemies){
			enemy = EnemyDAO.getEnemy(info.getId_enemy());
			enemy.setType(EnemyTypeDAO.getType(enemy.getId_enemy_type()));
			enemy.setMaxNumber(info.getMax_number());
			levelEnemies.add(enemy);
		}
		level.setEnemies(levelEnemies);
		
	}
				
}
