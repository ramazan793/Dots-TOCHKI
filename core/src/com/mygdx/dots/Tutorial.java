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
        import com.badlogic.gdx.scenes.scene2d.ui.Button;
        import com.badlogic.gdx.scenes.scene2d.ui.Label;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.Table;
        import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
        import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
        import com.badlogic.gdx.utils.viewport.ExtendViewport;
        import com.badlogic.gdx.utils.viewport.Viewport;

        import static com.badlogic.gdx.utils.Align.center;

/**
 * Created by Ramazan on 28.05.2017.
 */

public class Tutorial implements Screen {
    public static float WORLD_WIDTH;
    public static float WORLD_HEIGHT;
    myActor back;
    Texture backtext;
    Core game;
    Stage stage;
    Viewport viewport;
    Batch batch;
    Label label;
    Label.LabelStyle skin;
    BitmapFont font;

    Tutorial(final Core game){
        this.game=game;
        WORLD_WIDTH=800;
        WORLD_HEIGHT=480;
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
            if (Core.latestScreen==2) {
                game.setScreen(Core.settingsScreen);
            } else {
                game.setScreen(Core.gameScreen);
            }
        }
    }
    @Override
    public void show() {
        Core.setLang();
        viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
        batch = new SpriteBatch();
        stage = new Stage(viewport,batch);
        back=new myActor();
        back.setSize(40,40);
        back.setPosition(0,WORLD_HEIGHT-back.getHeight());
        back.addListener(new myListener());
        skin = new Label.LabelStyle();

        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();

        p.characters=Core.font_chars;
        p.color = Color.RED;
        p.size=30;
        font = gen.generateFont(p);

        skin.font = font;

        Table table = new Table();
        table.setPosition(0,0);
        table.setSize(WORLD_WIDTH,WORLD_HEIGHT);

        label = new Label("Some long string here...", skin);
        label.setFillParent(true);
        label.setAlignment(center);
        label.setPosition(0,0);
        label.setWidth(40);
        label.setHeight(label.getPrefHeight());
        label.setText(Core.tutortext);

        table.addActor(label);

        stage.addActor(table);
        stage.addActor(back);
        Gdx.input.setInputProcessor(stage);



    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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