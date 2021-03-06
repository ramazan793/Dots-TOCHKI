package com.mygdx.dots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Ramazan on 23.05.2017.
 */

public class SettingsScreen implements Screen {
    Core game;
    Stage stage;
    ExtendViewport viewport;
    TextButton button;
    TextButton button2;
    TextButton.TextButtonStyle langButtonStyle;
    TextButton.TextButtonStyle headStyle;
    static BitmapFont font;
    Texture lang;
    TextButton headButton;
    myActor back;
    Texture backtext;
    SpriteBatch batch;
    TextButton tutorial;
    TextButton.TextButtonStyle tutStyle;

    public static Preferences prefs;


    String slang;


    public static final float WORLD_WIDTH=800;
    public static final float WORLD_HEIGHT=480;
    SettingsScreen(final Core game){
        this.game=game;
        lang = new Texture("button/lang.png");
        backtext=new Texture("button/back2.png");



    }

    class myActor extends Actor{
        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(backtext, getX(), getY(), getWidth(), getHeight());
        }
    }
    class myListener extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(Core.menu);
        }
    }
    @Override
    public void show() {

        Core.latestScreen=2;
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        stage = new Stage(viewport,batch);
        Gdx.input.setInputProcessor(stage);
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
       // Color myCol= new Color(58, 115, 222,1);
        p.color = Color.RED;
        p.size=30;
        p.borderWidth=2;
        p.characters=Core.font_chars;
        font = gen.generateFont(p);

        langButtonStyle = new TextButton.TextButtonStyle();
        langButtonStyle.font = font;
        langButtonStyle.up = new TextureRegionDrawable(new TextureRegion(lang));

        tutStyle = new TextButton.TextButtonStyle();
        tutStyle.font = font;

        headStyle = new TextButton.TextButtonStyle();
        headStyle.font = font;

        back=new myActor();
        back.setSize(40,40);
        back.setPosition(0,440);
        back.addListener(new myListener());
        stage.addActor(back);

        button = new TextButton("RU",langButtonStyle);
        button2 = new TextButton("EN",langButtonStyle);
        headButton = new TextButton(Core.slang,headStyle);
        tutorial = new TextButton(Core.tutSet,Menu.textButtonStyle);
        tutorial.setPosition(WORLD_WIDTH/2-tutorial.getWidth()/2,100);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putBoolean("isrus",true);
                prefs.flush();
                prefs.putInteger("count",prefs.getInteger("count")+1);
                prefs.flush();
                Core.isrus=true;
                Gdx.input.vibrate(1000);
            }
        });
        button2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                prefs.putBoolean("isrus",false);
                prefs.flush();
                prefs.putInteger("count",prefs.getInteger("count")+1);
                prefs.flush();
                Core.isrus=false;
                Gdx.input.vibrate(1000);
            }
        });
        tutorial.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Core.tutorial);
            }
        });

        Table headTable = new Table();
        headTable.setFillParent(true);
        headTable.setPosition(0,200);
        stage.addActor(headTable);
        headTable.add(headButton).width(400);

        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(0,50);
        table.add(button).width(100).height(100).pad(0,0,0,100);
        table.add(button2).width(100).height(100).pad(0,100,0,0);

        stage.addActor(table);
        stage.addActor(tutorial);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Core.setLang();
        for (int y = 0; y < 18; y++) {
            GameScreen.drawer.line(new Vector2(0, y * 60), new Vector2(1920, y * 60), 3, Color.SKY);
        }
        for (int x = 0; x < 32;x++){
            GameScreen.drawer.line(new Vector2(60*x, 0), new Vector2(60*x, 1080), 3, Color.SKY);
        }
        batch.begin();
        batch.end();
        stage.act(delta);
        stage.draw();
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

    }
}
