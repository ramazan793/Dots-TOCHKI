package com.mygdx.dots;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.dots.SettingsScreen.prefs;

/**
 * Created by Ramazan on 22.05.2017.
 */

public class Core extends Game {

   static Menu menu;
        static    CreditsScreen creditsScreen;
        static    MyGdxGame gameScreen;
        static    SettingsScreen settingsScreen;

    @Override
    public void create () {
        prefs= Gdx.app.getPreferences("MyPreferences");
        if (prefs.getInteger("count")==1) {
            prefs.putBoolean("isrus", true);
        } else {
            prefs.putBoolean("isrus",false);
        }
        prefs.flush();
        creditsScreen=new CreditsScreen(this);
        gameScreen=new MyGdxGame(this);
        settingsScreen=new SettingsScreen(this);
        menu=new Menu(this);
        setScreen(menu);
    }

    @Override
    public void render() {
        super.render();
    }
}
