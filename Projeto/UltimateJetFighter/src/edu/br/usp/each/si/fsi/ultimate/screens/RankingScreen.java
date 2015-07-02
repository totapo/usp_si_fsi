package edu.br.usp.each.si.fsi.ultimate.screens;

import java.io.IOException;

import org.jdom2.JDOMException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import edu.br.usp.each.si.fsi.data.ScoreDAO;

public class RankingScreen implements Screen {

	private Stage stage = new Stage();
	private Table table = new Table();

	private Skin skin = loadSkin();

	private TextButton buttonBack = new TextButton("", skin.get("back",
			TextButtonStyle.class));
	
	private Label lblScore1 = new Label("", skin.get("label",
			LabelStyle.class));
	private Label lblScore2 = new Label("", skin.get("label",
			LabelStyle.class));
	private Label lblScore3 = new Label("", skin.get("label",
			LabelStyle.class));
	private Label lblScore4 = new Label("", skin.get("label",
			LabelStyle.class));
	private Label lblScore5 = new Label("", skin.get("label",
			LabelStyle.class));

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
		buttonBack.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Same way we moved here from the Splash Screen
				// We set it to new Splash because we got no other screens
				// otherwise you put the screen there where you want to go
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new MenuScreen());
			}
		});

		// The elements are displayed in the order you add them.
		// The first appear on top, the last at the bottom.
		int[] scores = ScoreDAO.getScore();
		
		lblScore1.setText("1. "+String.valueOf(scores[0]));
		lblScore2.setText("2. "+String.valueOf(scores[1]));
		lblScore3.setText("3. "+String.valueOf(scores[2]));
		lblScore4.setText("4. "+String.valueOf(scores[3]));
		lblScore5.setText("5. "+String.valueOf(scores[4]));
		lblScore1.setFontScale(6);
		lblScore2.setFontScale(6);
		lblScore3.setFontScale(6);
		lblScore4.setFontScale(6);
		lblScore5.setFontScale(6);
		table.add(lblScore1).align(Align.top).size(420, 150).padBottom(20)
		.row();
		table.add(lblScore2).align(Align.top).size(420, 150).padBottom(20)
		.row();
		table.add(lblScore3).align(Align.top).size(420, 150).padBottom(20)
		.row();
		table.add(lblScore4).align(Align.top).size(420, 150).padBottom(20)
		.row();
		table.add(lblScore5).align(Align.top).size(420, 150).padBottom(20)
		.row();
		table.add(buttonBack).align(Align.top).size(420, 150).padBottom(20)
				.row();
		table.background(skin.newDrawable("menu"));
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
		Pixmap pixmapStart = new Pixmap(Gdx.files.internal("skins/back.png"));

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
		skin.add("back", textButtonStyleStart);
		
		Pixmap pixmapLbl = new Pixmap(100, 100, Format.RGBA8888);
		pixmapLbl.setColor(Color.CLEAR);
		pixmapLbl.fill();
		skin.add("black", new Texture(pixmapLbl));
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.background = skin.newDrawable("black");
		labelStyle.fontColor = Color.WHITE;
		labelStyle.font = skin.getFont("default");
		
		skin.add("label", labelStyle);

		Pixmap pixmapMenu = new Pixmap(Gdx.files.internal("skins/menu.jpg"));
		skin.add("menu", new Texture(pixmapMenu));
		return skin;
	}

}
