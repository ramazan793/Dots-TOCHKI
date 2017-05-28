package com.mygdx.dots;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import static com.mygdx.dots.SettingsScreen.prefs;

/**
 * Created by Ramazan on 22.05.2017.
 */

public class Core extends Game {

   static Menu menu;
        static CreditsScreen creditsScreen;
        static GameScreen gameScreen;
        static SettingsScreen settingsScreen;
        static Tutorial tutorial;
        static final String font_chars = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        static String name;
        static String slang;
        static String play;
        static String settings;
        static String credits;
        static boolean isrus;
        static String tutortext;
        static String tutSet;
        static int latestScreen;

    static void setLang(){
        if (isrus){
            name="Фазылов Рамазан";
            play = " Играть ";
            settings = "Настройки";
            credits = "Авторы";
            slang = "Язык";
            tutortext="Обучение:"+"\n \n"+"Ставьте точки,"+"\n"+"окружайте вражеские"+"\n"+"для того, "+"\n"+"чтобы захватить их."+"\n"+"Победит тот, кто "+"\n"+"захватит больше точек." ;
            tutSet = " Правила ";
        } else {
            name="Fazylov Ramazan";
            slang = "Language";
            play = "Play";
            settings = "Settings";
            credits = "Credits";
            tutortext="How to play:"+"\n \n"+"Set the dots,"+"\n"+"surround the enemy"+"\n"+"in order to capture them."+"\n"+"Who captures more dots"+"\n"+"will win";
            tutSet="Rules";
        }
    }

    @Override
    public void create () {
        prefs= Gdx.app.getPreferences("MyPreferences");

        if (prefs.getInteger("count")%2!=0) {
            prefs.putBoolean("isrus", true);
            isrus=true;
        } else {
            prefs.putBoolean("isrus",false);
            isrus=false;
        }
        prefs.flush();
        creditsScreen=new CreditsScreen(this);
        gameScreen=new GameScreen(this);
        settingsScreen=new SettingsScreen(this);
        tutorial = new Tutorial(this);
        menu=new Menu(this);
        setScreen(menu);
    }

    @Override
    public void render() {
        super.render();
    }
}
