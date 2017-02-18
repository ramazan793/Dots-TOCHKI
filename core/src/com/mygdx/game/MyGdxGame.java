package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dotimg;
	OrthographicCamera camera;
	Actor dot;
	BitmapFont out;
	Stage stage;
	Viewport viewport;
	public int count=0;
	Texture backg;
	ArrayList<Red> reddots;
	int ind;
	BitmapFont font;



	class DotListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			event.getListenerActor().remove();
			float xx = event.getListenerActor().getX();
			float yy = event.getListenerActor().getY();
			ind = ((int)xx-46)/69;
			if (count%2==0) {
				Red r = new Red();
				r.setSize(30,30);
				r.setPosition(xx,yy);
				r.setColor(Color.RED);
				//reddots.add(ind,r);
				stage.addActor(r);


			} else {
				Blue b = new Blue();
				b.setSize(30,30);
				b.setPosition(xx,yy);
				b.setColor(Color.BLUE);
				stage.addActor(b);
			}
			count++;
			return true;
		}


	}

	class Dot extends Actor { //invisible

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
	class Red extends Dot{} class Blue extends Dot{} //material

	public void initDots(){
		for (int n1=0; n1 < 18; n1++) {
			for (int n = 0; n < 25; n++) {

				dot = new Dot();
				dot.addListener(new DotListener());
				dot.setSize(35, 35);
				dot.setPosition(61-15 + n * (69), 53-15 + n1 * ((float)57.5));
				dot.setColor(0,0,0,0);
				stage.addActor(dot);

			}
		}
	}



	@Override
	public void create() {
		batch = new SpriteBatch();
		dotimg = new Texture("circle.png");
		backg = new Texture("backg.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(viewport, batch);
		Gdx.input.setInputProcessor(stage);
		initDots();
		font = new BitmapFont();
		reddots =  new ArrayList();







	}

	//длин 60п между точками dotimg 40x40
	@Override
	public void render() {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backg,0,0,1920,1080);
		font.draw(batch, "ind", 100, 100);
		batch.end();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		batch.dispose();
		dotimg.dispose();
	}
}

