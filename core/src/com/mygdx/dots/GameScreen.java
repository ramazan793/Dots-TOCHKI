/*
package com.mygdx.dots;

import com.badlogic.gdx.Gdx;

*/
/**
 * Created by Ramazan on 22.05.2017.
 *//*


public enum GameScreen{

    MENU_SCREEN{
        @Override
        public void create(MyGdxGame gdxGame){
            Gdx.input.setInputProcessor(gdxGame.unistage);

        }
        @Override
        public void render(MyGdxGame gdxGame) {

            //gdxGame.renderer();
            gdxGame.unistage.draw();
            gdxGame.unistage.act(Gdx.graphics.getDeltaTime());
            gdxGame.unibatch.begin();

            gdxGame.unibatch.end();
        }
    }, GAME_SCREEN{
        @Override
        public void create(MyGdxGame gdxGame){

            Gdx.input.setInputProcessor(gdxGame.stage);
            Gdx.app.log("CREATE", "C");
            gdxGame.initDots();
        }

        @Override
        public void render(MyGdxGame gdxGame) {


            gdxGame.field();
            if (gdxGame.count > 6) {
                gdxGame.checker();
            }
            gdxGame.drawoutline();
            //gdxGame.renderer();
            gdxGame.batch.begin();
            gdxGame.batch.end();
            gdxGame.stage.draw();
            gdxGame.stage.act(Gdx.graphics.getDeltaTime());

        }

    }, SETTINGS_SCREEN{
        @Override
        public void create(MyGdxGame gdxGame){

        }

        @Override
        public void render(MyGdxGame gdxGame) {

        }
    };
    public abstract void create(MyGdxGame gdxGame);
    public abstract void render(MyGdxGame gdxGame);
}*/
