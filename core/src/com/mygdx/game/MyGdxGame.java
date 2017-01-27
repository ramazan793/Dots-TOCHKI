package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dotimg;
	OrthographicCamera camera;
	Actor dot;
	Vector2 touchPos;
	BitmapFont out;

	class Dot extends Actor {

		public void draw(SpriteBatch batch, float parentAlpha){
			batch.draw(dotimg, getX(), getY(), getWidth(), getHeight());
		}
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		dotimg = new Texture("dotimg.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		dot = new Actor();

		dot.setX(0);
		dot.setY(0);


		touchPos = new Vector2();

		out = new BitmapFont();


	}

	//длин 60п между точками dotimg 40x40
	@Override
	public void render() {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (Gdx.input.isTouched()) {
			batch.draw(dotimg, dot.getX(), dot.getY());
		}
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		dotimg.dispose();
	}
}

