package edu.br.usp.each.si.fsi.ultimate;

import com.badlogic.gdx.Game;

import edu.br.usp.each.si.fsi.ultimate.screens.MenuScreen;


public class UltimateJetFighter extends Game{

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}

}
