package com.mygdx.dots;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ramazan on 22.05.2017.
 */

public class Core extends Game {

    Menu menu;
    CreditsScreen creditsScreen;
    MyGdxGame gameScreen;
    SettingsScreen settingsScreen;

    @Override
    public void create () {

        creditsScreen=new CreditsScreen(this);
        gameScreen=new MyGdxGame(this);
        settingsScreen=new SettingsScreen(this);
        menu=new Menu(this);
        setScreen(menu);
    }

}
