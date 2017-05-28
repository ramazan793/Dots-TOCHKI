package com.mygdx.dots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;

import javax.sound.sampled.Line;

import sun.rmi.runtime.Log;
import sun.security.provider.SHA;

public class GameScreen implements Screen {
	SpriteBatch batch;
	Texture dotimg;
	OrthographicCamera camera;
	Actor dot;
	Stage stage;
	Viewport viewport;
	public int count;
	int indx;
	int indy;
	int vert;
	int horiz;
	Actor[][] dots;
	Red[][] reddots;
	Blue[][] bluedots;
	ArrayList<Dot> outline;
	ArrayList<Integer> wall;
	float size;
	static Texture pause;
	Viewport viewport2;
	Stage stage2;
	Button.ButtonStyle backStyle;
	Texture backtext;
	Button back;
	public static float WORLD_WIDTH;
	public static float WORLD_HEIGHT;
	Core game;

	GameScreen(final Core game){
		this.game=game;
		backtext = new Texture("button/back2.png");
	}

	public static class drawer { // класс чертежник
		static ShapeRenderer drawer = new ShapeRenderer();

		public static void line(Vector2 start, Vector2 end, int lineWidth, Color color) { // рисует линии
			Gdx.gl.glLineWidth(lineWidth);
			drawer.begin(ShapeRenderer.ShapeType.Line);
			drawer.setColor(color);
			drawer.line(start,end);
			drawer.end();
		}

	}

	public boolean IndexExistanceChecking(Dot a[][], int x, int y) { //проверяет, существует ли точка на координате
		if (a[x][y] == null) {
			return false;
		} else {
			return true;
		}
	}



	public boolean proximity(Dot a, Dot b) { // проверяет, рядом ли лежат две точки
		int x = a.getMyX();
		int x2 = b.getMyX();
		int y = a.getMyY();
		int y2 = b.getMyY();
		if (Math.abs(x - x2) < 2 && Math.abs(y - y2) < 2) {
			return true;
		}
		return false;
	}
	public boolean checkunit(Dot a){
		int x = a.getMyX();
		int y = a.getMyY();
		int ex[] = new int[3];
		ex[0] = x - 1;
		ex[1] = x;
		ex[2] = x + 1;
		int ey[] = new int[3];
		ey[0] = y - 1;
		ey[1] = y;
		ey[2] = y + 1;
		int counta=0;

		for (int i2 = 2; i2 >= 0; i2--) {
			for (int i = 0; i < 3; i++) {
				if (IndexExistanceChecking(a.getClass()==Red.class ? reddots : bluedots, ex[i], ey[i2]) ) { //условие существования и незакрашенности
					counta++;
					if (counta ==3){ a.setStatus(2); return true;}
				}
			}
		}
		return false;
	}
	public boolean wallchecking(Dot a,Dot b){ // не универсальный. временный. нашел альтернативу в методе DRAWOUTLINE
		int x = a.getMyX();
		int y = a.getMyY();
		if (a.getClass()!=Red.class) {
			if (b.getMyX() == x - 1 && b.getMyY() == y + 1) {
				if (IndexExistanceChecking(reddots, x - 1, y) && IndexExistanceChecking(reddots, x, y + 1) && (reddots[x - 1][y].linkedlist.contains(reddots[x][y + 1]))) {
					return true;
				}
			}
			if (b.getMyX() == x + 1 && b.getMyY() == y + 1) {
				if (IndexExistanceChecking(reddots, x, y + 1) && IndexExistanceChecking(reddots, x + 1, y) && (reddots[x][y + 1].linkedlist.contains(reddots[x + 1][y]))) {
					return true;
				}
			}
			if (b.getMyX() == x - 1 && b.getMyY() == y - 1) {
				if (IndexExistanceChecking(reddots, x, y - 1) && IndexExistanceChecking(reddots, x - 1, y) && (reddots[x][y - 1].linkedlist.contains(reddots[x - 1][y]))) {
					return true;
				}
			}
			if (b.getMyX() == x + 1 && b.getMyY() == y - 1) {
				if (IndexExistanceChecking(reddots, x, y - 1) && IndexExistanceChecking(reddots, x + 1, y) && (reddots[x][y - 1].linkedlist.contains(reddots[x + 1][y]))) {
					return true;
				}
			}
		} else {
			if (b.getMyX()==x-1 &&b.getMyY()==y+1){
				if (IndexExistanceChecking(bluedots,x-1,y)  && IndexExistanceChecking(bluedots,x,y+1) && (bluedots[x-1][y].linkedlist.contains(bluedots[x][y+1]))) {
					return true;
				}
			}
			if (b.getMyX()==x+1 &&b.getMyY()==y+1){
				if (IndexExistanceChecking(bluedots,x,y+1)  && IndexExistanceChecking(bluedots,x+1,y) && (bluedots[x][y+1].linkedlist.contains(bluedots[x+1][y]))){
					return true;
				}
			}
			if (b.getMyX()==x-1 &&b.getMyY()==y-1){
				if (IndexExistanceChecking(bluedots,x,y-1)  && IndexExistanceChecking(bluedots,x-1,y) && (bluedots[x][y-1].linkedlist.contains(bluedots[x-1][y]))){
					return true;
				}
			}
			if (b.getMyX()==x+1 &&b.getMyY()==y-1){
				if (IndexExistanceChecking(bluedots,x,y-1) && IndexExistanceChecking(bluedots,x+1,y) && (bluedots[x][y-1].linkedlist.contains(bluedots[x+1][y]))){
					return true;
				}
			}
		}
//
		return false;
	}

	public ArrayList<Dot> AroundDotsChecking(Dot a, ArrayList<Dot> history,int acc) { // проходит по контуру
		int x = a.getMyX();
		int y = a.getMyY();
		int ex[] = new int[3];
		ex[0] = x - 1;
		ex[1] = x;
		ex[2] = x + 1;
		int ey[] = new int[3];
		ey[0] = y - 1;
		ey[1] = y;
		ey[2] = y + 1;
		int countt = 0;
		history.add(a);
		if (a.status==0) {
			a.setStatus(1);
		} else if (a.status==2){
			a.setStatus(3);
		}
		acc++;
		if (a.getClass() == Red.class) {  // для красных
			for (int i2 = 2; i2 >= 0; i2--) {
				for (int i = 0; i < 3; i++) {
					if (IndexExistanceChecking(reddots, ex[i], ey[i2]) && (reddots[ex[i]][ey[i2]].status != 1 && reddots[ex[i]][ey[i2]].status != 3 ) && wallchecking(a,reddots[ex[i]][ey[i2]])==false) { //условие существования и незакрашенности
						countt++;
						return AroundDotsChecking(reddots[ex[i]][ey[i2]], history,acc);
					}
				}
			}
		} else { // для синих
			for (int i2 = 2; i2 >= 0; i2--) {
				for (int i = 0; i < 3; i++) {
					if (IndexExistanceChecking(bluedots, ex[i], ey[i2]) && (bluedots[ex[i]][ey[i2]].status !=1&& bluedots[ex[i]][ey[i2]].status != 3)&& wallchecking(a,bluedots[ex[i]][ey[i2]])==false) { //условие существования и незакрашенности
						countt++;
						return AroundDotsChecking(bluedots[ex[i]][ey[i2]], history,acc);
					}
				}
			}
		}
		if (((a.getMyX() == history.get(0).getMyX() && a.getMyY() == history.get(0).getMyY()) || (proximity(a, history.get(0)))) && (acc > 3)) { // условие завершения контура 1-совпадение координат(уже не нжуно вроде) ИЛИ близость) И больше 3 точек для контура
			for (int i = 0; i < history.size(); i++) {
				history.get(i).status=2;
			}
			return history;
		}


		for (int i = 0; i < history.size(); i++) {
			if (history.get(i).status==1) {
				history.get(i).setStatus(0);
			} else if (history.get(i).status==3){
				history.get(i).setStatus(2);
			}
		}
		history.clear();
		return null;
	}

	public void algo(Dot a) { // сам алгоритм
		if (a.status == 0) {
			ArrayList<Dot> history = new ArrayList<Dot>();
			int acc = 0;
			AroundDotsChecking(a, history, acc);
			if (history.size() > 0) {
				for (int i = 0; i < history.size(); i++) { // прорисовка контура
					if (i != history.size() - 1) {
						history.get(i).status=2;
						outline.add(history.get(i));
					} else {
						wall.add(i);
					}

				}
				outline.add(history.get(history.size() - 1));

			}
		}


	}
	//drawer.line(new Vector2(outline.get(i + u > 0 ? wall.get(u - 1) + 1 : 0).getX() + 10, outline.get(i + u > 0 ? wall.get(u - 1) + 1 : 0).getY() + 10), new Vector2(outline.get(i + 1 + u > 0 ? wall.get(u - 1) + 1 : 0).getX() + 10, outline.get(1 + i + u > 0 ? wall.get(u - 1) + 1 : 0).getY() + 10), 8, outline.get(0).getClass() == Red.class ? Color.RED : Color.BLUE);
	public void drawoutline(){
		int sum = 0;
		for (int u = 0; u < wall.size();u++) {
			sum+=wall.get(u)+1;
			for (int i = 0; i < wall.get(u)+1; i++) {// wall.get(u)=history.size()-1;
				if (i != wall.get(u)) {
					if (u > 0) {

						outline.get(i + sum-1-wall.get(u)).linkedlist.add(outline.get(i  +  sum-wall.get(u)));   // -- ALTERNATE WALL CHECKING
						outline.get(i  +  sum-wall.get(u)).linkedlist.add(outline.get(i + sum-1-wall.get(u)));   // -- ALTERNATE WALL CHECKING
						drawer.line(new Vector2(outline.get(i + sum-1-wall.get(u) ).getX() + 13, outline.get(i +  sum-1-wall.get(u) ).getY() + 13), new Vector2(outline.get(i  +  sum-wall.get(u)).getX() + 13, outline.get( i +  sum-wall.get(u) ).getY() + 13), 8, outline.get(i + sum-1-wall.get(u)  ).getClass() == Red.class ? Color.RED : Color.BLUE);
					} else {
						outline.get(i + 0).linkedlist.add(outline.get(1 + i));
						outline.get(1 + i).linkedlist.add(outline.get(i + 0));
						drawer.line(new Vector2(outline.get(i + 0).getX() + 13, outline.get(i + 0).getY() + 13), new Vector2(outline.get(i + 1).getX() + 13, outline.get(1 + i).getY() + 10), 8, outline.get(0).getClass() == Red.class ? Color.RED : Color.BLUE);
					}
				} else {
					if (u > 0) {
						outline.get(i +  sum-1-wall.get(u) ).linkedlist.add(outline.get( sum-wall.get(u)-1));
						outline.get(sum-wall.get(u)-1).linkedlist.add(outline.get(i +  sum-1-wall.get(u)));
						drawer.line(new Vector2(outline.get(i +  sum-1-wall.get(u) ).getX() + 13, outline.get(i +  sum-1-wall.get(u) ).getY() + 13), new Vector2(outline.get(  sum-1-wall.get(u)).getX() + 13, outline.get( sum-wall.get(u)-1).getY() + 13), 8, outline.get(i + sum-1-wall.get(u)  ).getClass() == Red.class ? Color.RED : Color.BLUE);
					} else {
						outline.get(i + 0).linkedlist.add(outline.get(0));
						outline.get(0).linkedlist.add(outline.get(i + 0));
						drawer.line(new Vector2(outline.get(i + 0).getX() + 13, outline.get(i + 0).getY() + 13), new Vector2(outline.get(0).getX() + 13, outline.get(0).getY() + 13), 8, outline.get(0).getClass() == Red.class ? Color.RED : Color.BLUE);
					}
				}
			}

		}
	}


	class DotListener extends InputListener {
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			event.getListenerActor().remove();
			float xx = event.getListenerActor().getX();
			float yy = event.getListenerActor().getY();
			indx = ((int) xx  / 60) + 1;
			indy = ((int) yy  / 60) + 1;
			if (count % 2 == 0) {
				Red r = new Red();
				r.setSize(size/2, size/2);
				r.setPosition(xx+size/4, yy+size/4);
				r.setColor(Color.RED);
				r.setMyX(indx);
				r.setMyY(indy);
				reddots[indx][indy] = r;
				stage.addActor(r);


			} else {
				Blue b = new Blue();
				b.setSize(size/2, size/2);
				b.setPosition(xx+size/4, yy+size/4);
				b.setColor(Color.BLUE);
				b.setMyX(indx);
				b.setMyY(indy);
				bluedots[indx][indy] = b;
				stage.addActor(b);
			}
			count++;
			return true;
		}
	}

	class Dot extends Actor { //invisible
		int X;
		int Y;
		int status = 0;
		boolean isUnit = false;
		ArrayList<Dot> linkedlist = new ArrayList<Dot>();
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

		void setMyX(float x) {
			this.X = (int) x;
		}

		int getMyX() {
			return X;
		}

		void setMyY(float y) {
			this.Y = (int) y;
		}

		int getMyY() {
			return Y;
		}

		void setStatus(int b) {
			this.status = b;
		}

	}




	class Red extends Dot{}
	class Blue extends Dot{}



	public void initDots(){
		for (int n1=0; n1 < 18; n1++) {
			for (int n = 0; n < 32; n++) {
				if(n1!=0 || n!=0) {
					dot = new Dot();
					dots[n1][n] = dot;
					dot.addListener(new DotListener());
					dot.setSize(size, size);
					dot.setPosition(60 - size / 2 + n *
							60/**(float)rate*/, 60 - size / 2 + n1 * 60/**(float)rate*/);
					dot.setColor(0, 0, 0, 0);
					stage.addActor(dot);
				}

			}
		}
	}
	public void field() {
		for (int y = 0; y < 18; y++) {
			drawer.line(new Vector2(0, y * 60), new Vector2(WORLD_WIDTH, y * 60), 3, Color.SKY);
		}
		for (int x = 0; x < 32;x++){
			drawer.line(new Vector2(60*x, 0), new Vector2(60*x, WORLD_HEIGHT), 3, Color.SKY);
		}
	}
	public void checker(){ //юзает алгоритм для всех сущесвтуеюших точек
		for (int y = 0; y < 18; y++) {
			for (int x = 0; x < 30; x++) {


				if (reddots[x][y] != null && reddots[x][y].status!=2) { algo(reddots[x][y]);  }
				if (bluedots[x][y] != null && bluedots[x][y].status!=2) { algo(bluedots[x][y]); }

			}
		}
	}
/*	public void renderer(){
		for (int n1=0; n1 < 18; n1++) {
			for (int n = 0; n < 32; n++) {
				if (currentScreen!=GameScreen.GAME_SCREEN){
					dots[n1][n].setTouchable(Touchable.disabled);
				} else {
					dots[n1][n].setTouchable(Touchable.enabled);
				}
			}
		}
		for (int y = 0; y < 18; y++) {
			for (int x = 0; x < 32; x++) {
				if (reddots[x][y] != null && currentScreen == GameScreen.MENU_SCREEN) {
					reddots[x][y].setVisible(false);
					reddots[x][y].setTouchable(Touchable.disabled);
				} else if (reddots[x][y] != null) {
					reddots[x][y].setVisible(true);
					reddots[x][y].setTouchable(Touchable.enabled);
				}
				if (bluedots[x][y] != null && currentScreen == GameScreen.MENU_SCREEN) {
					bluedots[x][y].setVisible(false);
					bluedots[x][y].setTouchable(Touchable.disabled);
				} else if (bluedots[x][y] != null) {
					bluedots[x][y].setVisible(true);
					bluedots[x][y].setTouchable(Touchable.enabled);
				}
			}
		}
	}*/






	@Override
	public void show() {
		WORLD_WIDTH=1780;
		WORLD_HEIGHT=1080;
		dotimg = new Texture("krug.png");
		pause = new Texture("button/back2.png");

		vert = 21;
		horiz = 32;


		camera = new OrthographicCamera();
		camera.setToOrtho(true, (float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
		camera.update();
		viewport = new StretchViewport((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
		viewport2 = new ExtendViewport(WORLD_WIDTH,WORLD_HEIGHT);
		batch = new SpriteBatch();
		stage = new Stage(viewport, batch);
		stage2=new Stage(viewport2,batch);
		Gdx.input.setInputProcessor(stage);


		size = 60;


		dots = new Actor[21][32];
		reddots = new Red[horiz][vert];
		bluedots = new Blue[horiz][vert];
		outline = new ArrayList<Dot>();
		wall = new ArrayList<Integer>();

		backStyle = new Button.ButtonStyle();
		backStyle.up = new TextureRegionDrawable(new TextureRegion(backtext));
		back=new Button(backStyle);

		back.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(Core.menu);
				outline.clear();
				wall.clear();

				dispose();
			}
		});

		back.setSize(80,80);
		back.setPosition(0,0);
		stage.addActor(back);
		initDots();
		count=0;



	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1); // VECTOR2 multis: y multiplyer=0.55555556 x mult=0.56179775
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		field();
//		drawer.line(new Vector2(0,0),new Vector2(890,540),1,Color.BLACK);

		if (count > 6) {
			checker();
		}
		drawoutline();
		batch.begin();
		batch.end();

		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		dotimg.dispose();
		stage.dispose();
		game.dispose();
	}
}

