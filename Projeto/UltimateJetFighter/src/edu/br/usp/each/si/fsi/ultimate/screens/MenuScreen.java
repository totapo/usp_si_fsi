package edu.br.usp.each.si.fsi.ultimate.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

	private Stage stage = new Stage();
	private Table table = new Table();

	private Skin skin = loadSkin();

	private TextButton buttonPlay = new TextButton("", skin.get("play",
			TextButtonStyle.class)), buttonScore = new TextButton("", skin.get(
			"score", TextButtonStyle.class));

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		buttonPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Same way we moved here from the Splash Screen
				// We set it to new Splash because we got no other screens
				// otherwise you put the screen there where you want to go
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen());
			}
		});
		buttonScore.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
				// or System.exit(0);
			}
		});

		// The elements are displayed in the order you add them.
		// The first appear on top, the last at the bottom.
		table.add(buttonPlay).size(420, 120).padBottom(20).row();
		table.add(buttonScore).size(420, 120).padBottom(20).row();

		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

	public Skin loadSkin() {
		Skin skin = new Skin();
		Pixmap pixmapStart = new Pixmap(Gdx.files.internal("skins/play.jpg"));

		skin.add("white", new Texture(pixmapStart));

		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		skin.add("default", bfont);
		TextButtonStyle textButtonStyleStart = new TextButtonStyle();
		textButtonStyleStart.up = skin.newDrawable("white", Color.WHITE);
		textButtonStyleStart.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyleStart.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyleStart.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyleStart.font = skin.getFont("default");
		skin.add("play", textButtonStyleStart);
		
		
		Pixmap pixmapExit = new Pixmap(Gdx.files.internal("skins/score.jpg"));
		skin.add("black", new Texture(pixmapExit));
		TextButtonStyle textButtonStyleExit = new TextButtonStyle();
		textButtonStyleExit.up = skin.newDrawable("black", Color.WHITE);
		textButtonStyleExit.down = skin.newDrawable("black", Color.DARK_GRAY);
		textButtonStyleExit.checked = skin.newDrawable("black", Color.BLUE);
		textButtonStyleExit.over = skin.newDrawable("black", Color.LIGHT_GRAY);
		textButtonStyleExit.font = skin.getFont("default");
		skin.add("score", textButtonStyleExit);
		return skin;
	}

}