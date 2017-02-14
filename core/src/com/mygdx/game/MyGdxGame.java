package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.touchUp;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dotimg;
	OrthographicCamera camera;
	Actor dot;
	Vector2 touchPos;
	BitmapFont out;
	Stage stage;
	Viewport viewport;
	Texture red;
	Actor reddot;
	int count;

	class ActorListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			//event.getListenerActor().setSize(80, 80);
			if (count%2==0) {
				event.getListenerActor().setColor(Color.BLUE);
			} else {
				event.getListenerActor().setColor(Color.RED);
			}
			count++;
			return true;
		}


	}


	class Dot extends Actor {

		@Override
		public void draw(Batch batch, float parentAlpha) {
			Color batchColor = batch.getColor();

			final float r = batchColor.r;
			final float g = batchColor.g;
			final float b = batchColor.b;
			final float a = batchColor.a;

			Color dotColor = getColor();

			batch.setColor(dotColor.r, dotColor.g, dotColor.b, dotColor.a * parentAlpha);
			batch.draw(dotimg, getX(), getY(), getWidth(), getHeight());
			batch.setColor(r, g, b, a);
		}
	}
	class RedDot extends Actor {
		@Override
		public  void draw(Batch batch, float parentAlpha){
			batch.draw(red, getX(), getY(), getWidth(), getHeight());
		}
	}
	public void initDots(){
		for (int n1=0; n1 < 8; n1++) {
			for (int n = 0; n < 14; n++) {

				dot = new Dot();
				dot.addListener(new ActorListener());
				dot.setSize(40, 40);
				dot.setPosition(120-20 + n * (120), 120-20 + n1 * (120));
				stage.addActor(dot);
			}
		}
	}



	@Override
	public void create() {
		batch = new SpriteBatch();
		dotimg = new Texture("circle.png");
		red = new Texture("white.png");
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(viewport, batch);
		dot = new Dot();
		Gdx.input.setInputProcessor(stage);
		initDots();
		reddot = new RedDot();
		reddot.setPosition(0,0);
		reddot.setSize(120,120);
		reddot.setColor(247,2,2,1);


		/*(dot.setSize(40,40);
		dot.setPosition(120,120);
		stage.addActor(dot); */







	}

	//длин 60п между точками dotimg 40x40
	@Override
	public void render() {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		batch.dispose();
		dotimg.dispose();
	}
}

