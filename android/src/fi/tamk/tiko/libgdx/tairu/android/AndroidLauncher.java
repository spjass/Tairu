package fi.tamk.tiko.libgdx.tairu.android;

import android.os.Bundle;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import fi.tamk.tiko.libgdx.tairu.TairuGame;

public class AndroidLauncher extends AndroidApplication {
    TairuGame game;
    Screen screen;
    AndroidApplicationConfiguration config;
    Bundle state;

    @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        game = new TairuGame();
        state = savedInstanceState;
		config = new AndroidApplicationConfiguration();
		initialize(game, config);
        screen = game.getCurrentScreen();

	}

    @Override
    public void onBackPressed() {

        String className = game.getCurrentScreen().getClass().getSimpleName();
        //if (game.getCurrentScreen() != game.getMainMenuScreen()) {

            //game.setScreen(new MainMenuScreen(game));

            game.setScreen(game.getMainMenuScreen());

        //} else {
            //exit();
        //}
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


}
