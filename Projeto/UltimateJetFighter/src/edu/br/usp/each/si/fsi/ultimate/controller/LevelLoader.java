package edu.br.usp.each.si.fsi.ultimate.controller;

import java.io.File;

import edu.br.usp.each.si.fsi.data.JetDAO;
import edu.br.usp.each.si.fsi.ultimate.model.Jet;
import edu.br.usp.each.si.fsi.ultimate.model.Level;

public class LevelLoader {

	private static final String LEVEL_PREFIX = "levels/level-";

	public static Level loadLevel(Jet jet) {

		Level level = new Level();
		// create the jet xml file if it doesn't exist
		File jets = new File(JetDAO.STORAGE_PATH);
		if (!jets.exists()) {

			JetDAO.CreateJetFile(jet);
			JetDAO.ModifyJetFile(jet);

		}

		return level;

	}

}
