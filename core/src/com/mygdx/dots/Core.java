package com.mygdx.dots;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ramazan on 22.05.2017.
 */

public class Core extends Game {

    SpriteBatch batch;



    @Override
    public void create () {
        batch = new SpriteBatch();
        setScreen(new Menu(this));

    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
