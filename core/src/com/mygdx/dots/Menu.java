package com.mygdx.dots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ramazan on 22.05.2017.
 */

public class Menu extends ScreenAdapter {

    Core game;
    Stage stage;
    ExtendViewport viewport;
    Texture pause;
    Texture backg;

    TextButton button;
    TextButton settingbutton;
    TextButton creditbutton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    public static final float WORLD_WIDTH=800;
    public static final float WORLD_HEIGHT=480;



    public Menu(final Core game){

        this.game=game;
        pause = new Texture("pause2.png");
        backg = new Texture("backg.jpg");
    }

    @Override
    public void show() {

        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();

        p.color = Color.RED;
        p.size=40;
        p.borderWidth=3;
        font = gen.generateFont(p);

        skin = new Skin();
        buttonAtlas = new TextureAtlas("button/mypack.atlas");
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");;

        button = new TextButton("Play", textButtonStyle);
        settingbutton = new TextButton("Settings",textButtonStyle);
        creditbutton = new TextButton("Credits",textButtonStyle);

        button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeListener.ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
                game.setScreen(new MyGdxGame(game));
            }
        });
        settingbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.settingsScreen);
            }
        });
        creditbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.creditsScreen);
            }
        });

        Table table=new Table();
        table.setFillParent(true);

        stage.addActor(table);
        table.defaults().pad(10);
        table.add(button).row();
        table.add(settingbutton).width(410).row();
        table.add(creditbutton).width(350).row();
        stage.setDebugAll(true);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        for (int y = 0; y < 18; y++) {
            MyGdxGame.drawer.line(new Vector2(0, y * 60), new Vector2(1920, y * 60), 3, Color.SKY);
        }
        for (int x = 0; x < 32;x++){
            MyGdxGame.drawer.line(new Vector2(60*x, 0), new Vector2(60*x, 1080), 3, Color.SKY);
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        stage.getViewport().update(width,height,true);
    }
}
