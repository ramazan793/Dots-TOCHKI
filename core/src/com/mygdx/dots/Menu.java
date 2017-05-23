package com.mygdx.dots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ramazan on 22.05.2017.
 */

public class Menu implements Screen {
    Core game;
    Stage stage;
    Viewport viewport;
    SpriteBatch batch;
    Texture pause;
    Texture backg;

    TextButton button;
    TextButton settingbutton;
    TextButton creditbutton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    public float width = Gdx.graphics.getWidth();
    public float height = Gdx.graphics.getHeight();


    public Menu(final Core game){
        this.game=game;
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void show() {
        pause = new Texture("pause2.png");

        font = new BitmapFont();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();

        p.color = Color.RED;
        p.size=100;
        p.borderWidth=3;
        font = gen.generateFont(p);
        font.getData().setScale(0.8f);

        backg = new Texture("backg.jpg");

        skin = new Skin();
        buttonAtlas = new TextureAtlas("button/mypack.atlas");
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");;

        button = new TextButton("Play", textButtonStyle);
        settingbutton = new TextButton("Settings",textButtonStyle);
        creditbutton = new TextButton("Credits",textButtonStyle);

        settingbutton.setSize((float)0.4*width,(float)0.25*height);
        creditbutton.setSize((float)0.4*width,(float)0.25*height);
        button.setSize((float)0.4*width,(float)0.25*height);

        button.setPosition(width/2-button.getWidth()/2,-button.getHeight()/2+height/16*13);
        settingbutton.setPosition(width/2-button.getWidth()/2,height/2-button.getHeight()/2);
        creditbutton.setPosition(width/2-button.getWidth()/2,button.getHeight()/2-height/16);

        System.out.println(width+"  "+height);
        stage.addActor(settingbutton);
        stage.addActor(creditbutton);
        stage.addActor(button);
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
                game.setScreen(new SettingsScreen(game));
            }
        });
        creditbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditsScreen(game));
            }
        });
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
        batch.begin();
        //batch.draw(backg,0,0,width,height);
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
