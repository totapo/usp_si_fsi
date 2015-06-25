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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameOverScreen implements Screen {

	private Stage stage = new Stage();
	private Table table = new Table();
	private int score;

	private Skin skin = loadSkin();

	private TextButton buttonOk = new TextButton("", skin.get("ok",
			TextButtonStyle.class));
	private Label lblGameOver = new Label("Game Over", skin.get("label",
			LabelStyle.class));
	private Label lblScore = new Label("Score: "+score, skin.get("label",
			LabelStyle.class));

	public GameOverScreen(int score) {
		this.score = score;
		lblScore.setText("Score: "+score);
	}

	@Override
	public void show() {
		buttonOk.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {

				((Game) Gdx.app.getApplicationListener())
						.setScreen(new MenuScreen());
			}
		});

		// The elements are displayed in the order you add them.
		// The first appear on top, the last at the bottom.
		lblGameOver.setFontScale(4);
		lblScore.setFontScale(4);
		table.add(lblGameOver).align(Align.center).size(300, 80).padBottom(80).row();
		table.add(lblScore).align(Align.center).size(300, 80).padBottom(80).row();
		
		table.add(buttonOk).align(Align.center).size(300, 80).padBottom(20).row();

		table.setFillParent(true);
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();

	}

	public Skin loadSkin() {
		Skin skin = new Skin();
		Pixmap pixmapOk = new Pixmap(Gdx.files.internal("skins/ok.png"));

		skin.add("white", new Texture(pixmapOk));
		Pixmap pixmapLbl = new Pixmap(100, 100, Format.RGBA8888);
		pixmapLbl.setColor(Color.CLEAR);
		pixmapLbl.fill();
		skin.add("black", new Texture(pixmapLbl));
		// Store the default libgdx font under the name "default".
		BitmapFont bfont = new BitmapFont();
		skin.add("default", bfont);
		TextButtonStyle textButtonStyleOk = new TextButtonStyle();
		textButtonStyleOk.up = skin.newDrawable("white", Color.WHITE);
		textButtonStyleOk.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyleOk.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyleOk.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyleOk.font = skin.getFont("default");
		skin.add("ok", textButtonStyleOk);
		
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.background = skin.newDrawable("black");
		labelStyle.fontColor = Color.WHITE;
		labelStyle.font = skin.getFont("default");
		
		skin.add("label", labelStyle);
		return skin;
	}

}
