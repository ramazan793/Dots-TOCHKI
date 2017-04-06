package com.mygdx.dots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;

import javax.sound.sampled.Line;

import sun.rmi.runtime.Log;
import sun.security.provider.SHA;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture dotimg;
	OrthographicCamera camera;
	Actor dot;
	Stage stage;
	Viewport viewport;
	public int count = 0;
	Texture backg;
	int indx;
	BitmapFont font;
	int indy;
	int vert = 19;
	int horiz = 32;
	Red[][] reddots = new Red[horiz][vert];
	Blue[][] bluedots = new Blue[horiz][vert];
	ArrayList<Dot> outline = new ArrayList<Dot>();
	ArrayList<Integer> wall = new ArrayList<Integer>();
	int sum = 0;


	public static class drawer { // класс чертежник
		static ShapeRenderer drawer = new ShapeRenderer();

		public static void line(Vector2 start, Vector2 end, int lineWidth, Color color) { // рисует линии
			Gdx.gl.glLineWidth(lineWidth);
			drawer.begin(ShapeRenderer.ShapeType.Line);
			drawer.setColor(color);
			drawer.line(start, end);
			drawer.end();
		}
		public  static void point(float x, float y){
			drawer.begin(ShapeRenderer.ShapeType.Point);
			drawer.setColor(Color.CHARTREUSE);
			drawer.point(x,y,40);
			drawer.end();

		}
	}

	public boolean IndexExistanceChecking(Dot a[][], int x, int y) { //проверяет, существует ли точка на координате
		if (a[x][y] != null) {
			return true;
		} else {
			return false;
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
	public void checkunit(Dot a){
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
		for (int i2 = 2; i2 >= 0; i2--) {
			for (int i = 0; i < 3; i++) {
				if (IndexExistanceChecking(a.getClass()==Red.class ? reddots : bluedots, ex[i], ey[i2]) ) { //условие существования и незакрашенности
					count++;
					if (count ==3){a.isUnit=true;break;}
				}
			}
		}
	}
	public boolean wallchecking(Dot a,Dot b){ // не универсальный. временный. нашел альтернативу в методе DRAWOUTLINE
		int x = a.getMyX();
		int y = a.getMyY();
		if (b.getMyX()==x-1 &&b.getMyY()==y+1){
			if ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x-1,y) && (a.getClass()==Red.class ? bluedots[x-1][y].status : reddots[x-1][y].status==true )) && ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x,y+1) && (a.getClass()==Red.class ? bluedots[x][y+1].status : reddots[x][y+1].status==true )))){
				return true;
			}
		}
		if (b.getMyX()==x+1 &&b.getMyY()==y+1){
			if ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x,y+1) && (a.getClass()==Red.class ? bluedots[x][y+1].status : reddots[x][y+1].status==true )) && ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x+1,y) && (a.getClass()==Red.class ? bluedots[x+1][y].status : reddots[x+1][y].status==true )))){
				return true;
			}
		}
		if (b.getMyX()==x-1 &&b.getMyY()==y-1){
			if ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x,y-1) && (a.getClass()==Red.class ? bluedots[x][y-1].status : reddots[x][y-1].status==true )) && ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x-1,y) && (a.getClass()==Red.class ? bluedots[x-1][y].status : reddots[x-1][y].status==true )))){
				return true;
			}
		}
		if (b.getMyX()==x+1 &&b.getMyY()==y-1){
			if ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x,y-1) && (a.getClass()==Red.class ? bluedots[x][y-1].status : reddots[x][y-1].status==true )) && ((IndexExistanceChecking(a.getClass()==Red.class ? bluedots : reddots,x+1,y) && (a.getClass()==Red.class ? bluedots[x+1][y].status : reddots[x+1][y].status==true )))){
				return true;
			}
		}
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
		int count = 0;
		history.add(a);
		a.setStatus(true);
		acc++;
			for (int i2 = 2; i2 >= 0; i2--) {
				for (int i = 0; i < 3; i++) {
					if (IndexExistanceChecking(a.getClass()==Red.class ? reddots : bluedots, ex[i], ey[i2]) && reddots[ex[i]][ey[i2]].status == false) { //условие существования и незакрашенности
						count++;
						Gdx.app.log(Integer.toString(ex[i]) + ":" + Integer.toString(ey[i2]), " -- Прошёл проверку условий");
						return AroundDotsChecking(a.getClass() == Red.class ? reddots[ex[i]][ey[i2]] : bluedots[ex[i]][ey[i2]], history,acc);
					}
				}
			}

		if (((a.getMyX() == history.get(0).getMyX() && a.getMyY() == history.get(0).getMyY()) || (proximity(a, history.get(0)))) && (acc > 3)) { // условие завершения контура 1-совпадение координат(уже не нжуно вроде) ИЛИ близость) И больше 3 точек для контура
			return history;
		}

		if (count == 0) {
			for (int i = 0; i < history.size(); i++) {
				history.get(i).setStatus(false);
			}
			history.clear();

			return null;


		}
		for (int i = 0; i < history.size(); i++) {
			history.get(i).setStatus(false);
		}
		history.clear();
		return null;
	}


	public void algo(Dot a) { // сам алгоритм
		if (a.status == false) {
			ArrayList<Dot> history = new ArrayList<Dot>();
			int acc = 0;
			AroundDotsChecking(a, history, acc);
			if (history.size() > 0) {
				for (int i = 0; i < history.size(); i++) { // прорисовка контура
					if (i != history.size() - 1) {
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
						//outline.get(i + sum-1-wall.get(u) ).linking(outline.get(i  +  sum-wall.get(u)));
						drawer.line(new Vector2(outline.get(i + sum-1-wall.get(u) ).getX() + 10, outline.get(i +  sum-1-wall.get(u) ).getY() + 10), new Vector2(outline.get(i  +  sum-wall.get(u)).getX() + 10, outline.get( i +  sum-wall.get(u) ).getY() + 10), 8, outline.get(i + sum-1-wall.get(u)  ).getClass() == Red.class ? Color.RED : Color.BLUE);
					} else {
						drawer.line(new Vector2(outline.get(i + 0).getX() + 10, outline.get(i + 0).getY() + 10), new Vector2(outline.get(i + 1).getX() + 10, outline.get(1 + i).getY() + 10), 8, outline.get(0).getClass() == Red.class ? Color.RED : Color.BLUE);
					}
				} else {
					if (u > 0) {
						drawer.line(new Vector2(outline.get(i +  sum-1-wall.get(u) ).getX() + 10, outline.get(i +  sum-1-wall.get(u) ).getY() + 10), new Vector2(outline.get(  sum-1-wall.get(u)).getX() + 10, outline.get( sum-wall.get(u)-1).getY() + 10), 8, outline.get(i + sum-1-wall.get(u)  ).getClass() == Red.class ? Color.RED : Color.BLUE);
					} else {
						drawer.line(new Vector2(outline.get(i + 0).getX() + 10, outline.get(i + 0).getY() + 10), new Vector2(outline.get(0).getX() + 10, outline.get(0).getY() + 10), 8, outline.get(0).getClass() == Red.class ? Color.RED : Color.BLUE);
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
				r.setSize(24, 24);
				r.setPosition(xx, yy);
				r.setColor(Color.RED);
				r.setMyX(indx);
				r.setMyY(indy);
				reddots[indx][indy] = r;
				stage.addActor(r);


			} else {
				Blue b = new Blue();
				b.setSize(24, 24);
				b.setPosition(xx, yy);
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
		boolean status = false;
		boolean isUnit = false;

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

		void setStatus(boolean b) {
			this.status = b;
		}
	}



	class Red extends Dot{}
	class Blue extends Dot{}

	public void initDots(){
		for (int n1=0; n1 < 18; n1++) {
			for (int n = 0; n < 32; n++) {

				dot = new Dot();
				dot.addListener(new DotListener());
				dot.setSize(50, 50);
				dot.setPosition(60-13 + n * (60), 60-13 + n1 * 60);
				dot.setColor(0,0,0,0);
				stage.addActor(dot);

			}
		}
	}
	public void field() {
		for (int y = 0; y < 18; y++) {
			drawer.line(new Vector2(0, y * 60), new Vector2(1920, y * 60), 3, Color.SKY);
		}
		for (int x = 0; x < 32;x++){
			drawer.line(new Vector2(60*x, 0), new Vector2(60*x, 1080), 3, Color.SKY);
		}
	}
	public void checker(){ //юзает алгоритм для всех сущесвтуеюших точек
		for (int y = 0; y < 18; y++) {
			for (int x = 0; x < 30; x++) {
				String a;
				if (reddots[x][y] != null) { algo(reddots[x][y]); }
				if (bluedots[x][y] != null) { algo(bluedots[x][y]); }
			}
		}
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		Circle circles = new Circle();
		dotimg = new Texture("krug.png");
		backg = new Texture("backg.jpg");
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 1920, 1080);
		camera.update();
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(viewport, batch);
		Gdx.input.setInputProcessor(stage);
		initDots();
		font = new BitmapFont();
		font.setColor(Color.RED);



	}

	@Override
	public void render() { // ненавижу его
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		field();
		checker();
		drawoutline();

		batch.begin();

		font.draw(batch, "X: "+indx+" Y: "+indy, 100, 100);
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

