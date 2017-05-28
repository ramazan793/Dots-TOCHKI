package com.mygdx.dots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by Ramazan on 23.05.2017.
 */

public class CreditsScreen implements Screen {
    Core game;
    Stage stage;
    ExtendViewport viewport;
    myActor back;
    Texture backtext;
    SpriteBatch batch;
    TextButton headButton;
    TextButton.TextButtonStyle headStyle;
    BitmapFont font;

    public static final float WORLD_WIDTH=800;
    public static final float WORLD_HEIGHT=480;
    CreditsScreen(final Core game){
        this.game=game;
        backtext=new Texture("button/back2.png");
    }

    class myActor extends Actor {
        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(backtext, getX(), getY(), getWidth(), getHeight());
        }
    }
    class myListener extends ClickListener {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            game.setScreen(Core.menu);
        }
    }

    @Override
    public void show() {
        batch=new SpriteBatch();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        stage = new Stage(viewport,batch);
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.color = Color.RED;
        p.size=30;
        p.borderWidth=2;
        p.characters=Core.font_chars;
        font = gen.generateFont(p);
        headStyle = new TextButton.TextButtonStyle();
        headStyle.font = font;
        headButton = new TextButton(Core.name,headStyle);

        back=new myActor();
        back.setSize(40,40);
        back.setPosition(0,440);
        back.addListener(new myListener());
        stage.addActor(back);

        Table headTable = new Table();
        headTable.setFillParent(true);
        headTable.setPosition(0,0);
        stage.addActor(headTable);
        headTable.add(headButton).width(400);

        stage.addActor(headTable);

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
