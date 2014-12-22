package fi.tamk.tiko.libgdx.tairu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.TairuGame;
import fi.tamk.tiko.libgdx.tairu.buttons.BackButton;

/**
 * Stats screen
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class StatsScreen implements Screen {
    Preferences prefs;
    TairuGame game;
    ArrayList<String> levelList;
    static String TAG = "StatsScreen";
    OrthographicCamera camera;
    StretchViewport viewPort;
    int gold;
    int silver;
    int completed;
    int matches;
    String total;
    BackButton back;

    float WIDTH = Gdx.graphics.getWidth();
    float HEIGHT = Gdx.graphics.getHeight();

    /**
     * Constructor for the StatsScreen Screen.
     *
     * @param game Main game class
     */
    public StatsScreen(TairuGame game) {
        this.game = game;
        back = new BackButton(new Texture(Gdx.files.internal("back2.png")), game);
        game.setCurrentScreen(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewPort = new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        prefs = game.getPrefs();
        levelList = game.getLevelList();
        matches = levelList.size();
        calculate();
    }
    /**
     * Calculates all stats
     */
    public void calculate() {
        gold = countGold();
        silver = countSilver();
        completed = gold + silver;
        total = numberFormatter(completed*1.0f / matches*1.0f * 100.00f)+"%";
    }
    /**
     * Counts number of gold medals earned
     *
     * return number of gold medals
     */
    public int countGold() {
        int gold = 0;

        for (int i = 0; i < levelList.size(); i++) {

            if (prefs.getInteger(levelList.get(i)) == 2) {
                Gdx.app.log(TAG, "gold found " + gold);
                gold++;
            } else {
                Gdx.app.log(TAG, "gold not found " + gold);

            }
        }

        return gold;
    }
    /**
     * Counts number of silver medals earned
     *
     * return number of silver medals
     */
    public int countSilver() {
        int silver = 0;

        for (int i = 0; i < levelList.size(); i++) {

            if (prefs.getInteger(levelList.get(i)) == 1) {
                Gdx.app.log(TAG, "silver found " + silver);
                silver++;
            }
        }

        return silver;
    }

    /**
     * Draws all stat texts.
     */
    public void drawStats() {
        String startStr = "COMPLETED: " + (silver + gold) + "/" + matches  ;
        String statsStr = "TOTAL: " + total;
        String goldStr = "GOLD: " + gold + "/" + matches;
        String silverStr = "SILVER: " + (silver + gold) + "/" + matches;
        float WIDTH = Gdx.graphics.getWidth();
        float HEIGHT = Gdx.graphics.getHeight();



        game.font.draw(game.batch, startStr, WIDTH*0.1f, HEIGHT*0.7f);
        game.font.draw(game.batch, statsStr, WIDTH*0.1f, HEIGHT*0.55f);
        game.font.draw(game.batch, goldStr, WIDTH*0.1f, HEIGHT*0.40f);
        game.font.draw(game.batch, silverStr, WIDTH*0.1f, HEIGHT*0.25f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50/255f, 10/255f, 10/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawStats();
        back.draw(game.batch);
        game.batch.end();
        checkTouch();
        camera.update();
    }

    public void checkTouch () {

        if (Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            back.onTouch(x, y);
        }
    }
    /**
     * Number formatter for floats
     *
     * return String representing a float with two decimals
     */
    public String numberFormatter(Float number) {

        String numberStr = String.format( "%.2f", number);

        return numberStr;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
