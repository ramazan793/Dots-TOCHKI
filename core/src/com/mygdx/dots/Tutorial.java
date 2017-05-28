package com.mygdx.dots;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Ramazan on 28.05.2017.
 */

public class Tutorial implements Screen {
    public static final float WORLD_WIDTH=800;
    public static final float WORLD_HEIGHT=480;
    myActor back;
    Texture backtext;
    Core game;
    Stage stage;
    Viewport viewport;
    Batch batch;
    TextButton guide;
    TextButton.TextButtonStyle guideStyle;

    Tutorial(final Core game){
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
            game.setScreen(Core.gameScreen);
        }
    }
    @Override
    public void show() {
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        stage = new Stage(viewport,batch);
        back=new myActor();
        back.setSize(40,40);
        back.setPosition(0,440);
        back.addListener(new myListener());
        stage.addActor(back);

        guideStyle = new TextButton.TextButtonStyle();
        guideStyle.font = SettingsScreen.font;
        guide = new TextButton("AZAZAZAZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAZZZZZZZZZZZZZZZ KONEC GAIDA",guideStyle);
        stage.addActor(guide);


    }

    @Override
    public void render(float delta) {

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
