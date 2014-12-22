package fi.tamk.tiko.libgdx.tairu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.game.Level;
import fi.tamk.tiko.libgdx.tairu.game.Solver;
import fi.tamk.tiko.libgdx.tairu.screens.LevelScreen;
import fi.tamk.tiko.libgdx.tairu.screens.MainMenuScreen;

/**
 * Main game class
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class TairuGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public String currentLevelString;
    public Level currentLevel;
    Preferences prefs;
    ArrayList<String> levelList;
    boolean debug;
    public Screen currentScreen;
    public Screen previousScreen;
    public Music clickSound;
    public Music victorySound;
    public Music defeatSound;
    public Music startSound;
    public Music music;
    boolean mute;
    LevelScreen levelScreen;
    MainMenuScreen mainMenuScreen;



    @Override
    public void create () {
        font = new BitmapFont(Gdx.files.internal("font2.fnt"));
        batch = new SpriteBatch();
        prefs = Gdx.app.getPreferences("Tairu");
        levelList = initLevelList(21);
        debug = false;
        this.setLevelScreen(new LevelScreen(this));
        this.setMainMenuScreen(new MainMenuScreen(this));
        //solverTest();
        loadSounds();
        music.setLooping(true);
        music.setVolume(0.4f);
        music.play();
        this.setScreen(mainMenuScreen);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.stop();
        /*music.dispose();
        clickSound.dispose();
        defeatSound.dispose();
        startSound.dispose();
        victorySound.dispose();
        //font.dispose();
        //batch.dispose();
        */
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public void setPrefs(Preferences prefs) {
        this.prefs = prefs;
    }

    public ArrayList<String> getLevelList() {
        return levelList;
    }

    public void setLevelList(ArrayList<String> levelList) {
        this.levelList = levelList;
    }

    /**
     * Initializes the list of map names
     *
     * return ArrayList of map names
     */
    public ArrayList<String> initLevelList (int levelsLength) {

        ArrayList<String> levelList = new ArrayList<String>();


        levelList.clear();

        for(int i = 1; i <= levelsLength; i++) {
            if (i <= 9) {
                levelList.add("0" + i+".tmx");
            } else {
                levelList.add(i+".tmx");
            }

        }

        levelList.add("testi.tmx");


        this.levelList = levelList;
        return levelList;
    }

    public void solverTest() {
        Solver solver = new Solver(this);
        solver.solve(new Level("07.tmx"));
    }

    public void setCurrentScreen(Screen screen) {
        previousScreen = currentScreen;
        this.currentScreen = screen;
    }

    public Screen getPreviousScreen() {
        return previousScreen;
    }

    public void setLevelScreen (LevelScreen levelScreen) {
        this.levelScreen = levelScreen;
    }

    public LevelScreen getLevelScreen() {
        return levelScreen;
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
    }
    /**
     * Loads all sounds
     */
    public void loadSounds() {
        this.clickSound = Gdx.audio.newMusic(Gdx.files.internal("click.mp3"));
        this.defeatSound = Gdx.audio.newMusic(Gdx.files.internal("defeat.mp3"));
        this.victorySound = Gdx.audio.newMusic(Gdx.files.internal("victory.mp3"));
        this.startSound = Gdx.audio.newMusic(Gdx.files.internal("start.mp3"));
        this.music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));

    }




    public Screen getCurrentScreen() {
        return currentScreen;
    }

    @Override
    public void render () {
        super.render();
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }


}
