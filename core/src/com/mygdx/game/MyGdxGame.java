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
import java.util.Arrays;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dotimg;
	OrthographicCamera camera;
	Actor dot;
	Stage stage;
	Viewport viewport;
	public int count=0;
	Texture backg;
	int indx;
	BitmapFont font;
	int indy;
	int vert = 19;
	int horiz = 26;
	Red[][] reddots = new Red[vert][horiz];
	Blue[][] bluedots = new Blue[vert][horiz];

	public boolean IndexExistanceChecking(Dot a[][],int x,int y){
		if (a[y][x]!=null){
			return true;
		} else {
			return false;
		}
	}
	public int[][] AroundDotsChecking(Dot a[][] ,int x,int y){ //finished. //he is outputing next dots.
		int ex[]=new int[3];
		ex[0]=x-1;
		ex[1]=x;
		ex[2]=x+1;
		int ey[]=new int[3];
		ey[0]=y-1;
		ey[1]=y;
		ey[2]=y+1;
		int cy=0,cx=0;
		int s[][]=new int[cy][cx];
		for (int i=0; i < 3; i++) {
			for (int i2 = 0; i2 < 3; i++) {

				if (IndexExistanceChecking(a, ex[i], ey[i2]) && ex[i]!=ex[i2]) {
					s[cy][cx]=ey[i2];
					cx++;
					s[cy][cx]=ex[i];
					cy++;
				}
			}
		}
		if (cy==0) {
			return null;
		} else {
			return s;
		}
	}



	class DotListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			event.getListenerActor().remove();
			float xx = event.getListenerActor().getX();
			float yy = event.getListenerActor().getY();
			indx = (((int)xx-46)/69)+1;
			indy = (((int)yy-38)/(int)57.5)+1;
			if (count%2==0) {
				Red r = new Red();
				r.setSize(30,30);
				r.setPosition(xx,yy);
				r.setColor(Color.RED);
				reddots[indy][indx]=r;
				stage.addActor(r);


			} else {
				Blue b = new Blue();
				b.setSize(30,30);
				b.setPosition(xx,yy);
				b.setColor(Color.BLUE);
				bluedots[indy][indx]=b;
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
		font.setColor(Color.RED);






	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backg,0,0,1920,1080);
		font.draw(batch, "X: "+indx+" Y: "+indy+"Own: " + IndexExistanceChecking(reddots,1,1), 100, 100);
		batch.end();
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		batch.dispose();
		dotimg.dispose();
		stage.dispose();
	}
}

