package edu.br.usp.each.si.fsi.ultimate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum Numbers {
	ZERO ("images/sprites/hud/numbers/0.png"),
	ONE  ("images/sprites/hud/numbers/1.png"),
	TWO  ("images/sprites/hud/numbers/2.png"),
	THREE("images/sprites/hud/numbers/3.png"),
	FOUR ("images/sprites/hud/numbers/4.png"),
	FIVE ("images/sprites/hud/numbers/5.png"),
	SIX  ("images/sprites/hud/numbers/6.png"),
	SEVEN("images/sprites/hud/numbers/7.png"),
	EIGHT("images/sprites/hud/numbers/8.png"),
	NINE ("images/sprites/hud/numbers/9.png"),
	DDOTS("images/sprites/hud/numbers/dots.png");
	
	Numbers(String str){
		this.text = new Texture(Gdx.files.internal(str));
		Gdx.app.debug("Num","Carreguei");
	}
	
	public Texture getTexture(){
		return text;
	}
	
	private Texture text;
}
