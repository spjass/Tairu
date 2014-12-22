package fi.tamk.tiko.libgdx.tairu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.game.Level;
import fi.tamk.tiko.libgdx.tairu.TairuGame;
import fi.tamk.tiko.libgdx.tairu.buttons.BackButton;
import fi.tamk.tiko.libgdx.tairu.buttons.LevelButton;

/**
 * Level selection screen
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class LevelScreen implements Screen {

    public TairuGame game;
    ArrayList<String> levelList;
    ArrayList<LevelButton> buttonList;
    static int HEIGHT = Gdx.graphics.getHeight();
    static int WIDTH = Gdx.graphics.getWidth();
    OrthographicCamera camera;
    StretchViewport viewPort;
    ShapeRenderer renderer;
    int cooldown;
    Sprite goldMedal;
    Sprite silverMedal;
    ArrayList<LevelButton>[] pages;
    int page;
    Rectangle next;
    Rectangle prev;
    BackButton back;

    /**
     * Constructor for the LevelScreen Screen.
     *
     * @param game Main game class
     */
    public LevelScreen (TairuGame game) {
        this.game = game;
        next = new Rectangle();
        prev = new Rectangle();
        page = 0;
        levelList = game.getLevelList();
        buttonList = new ArrayList<LevelButton>();
        game.setCurrentScreen(this);

        initPages();

        game.setLevelList(levelList);
        back = new BackButton(new Texture(Gdx.files.internal("back2.png")), game);
        goldMedal = new Sprite(new Texture(Gdx.files.internal("gold.png")));
        silverMedal = new Sprite(new Texture(Gdx.files.internal("silver.png")));
        renderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewPort = new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        createButtons();
        cooldown = 0;
    }
    /**
     * Initializes number of pages
     */
    public void initPages() {
        Gdx.app.log("LevelScreen", levelList.size()+"");
        Gdx.app.log("LevelScreen", (levelList.size()-1)/7 +1 +" pages");
        pages = new ArrayList[(levelList.size() - 1) / 7 + 1];
        for (int i = 0; i < pages.length; i++) {
            pages[i] = new ArrayList<LevelButton>();
        }
    }


    /**
     * Creates buttons for each page
     */
    public void createButtons() {

        int tmpPage = 0;

        int id = 0;
            for (int j = 0; j < levelList.size(); j++) {

                if (j % 7 == 0 && tmpPage < pages.length - 1 && j != 0) {

                    tmpPage++;
                    id = 0;

                }
                Gdx.app.log("LevelScreen", pages[tmpPage].size()+"");
                pages[tmpPage].add(new LevelButton(this, levelList.get(j), id, tmpPage));
                id++;


                Gdx.app.log("LevelScreen", levelList.get(j));

            }





        /*
        if (page == 1) {

            for (int i = 0; i < 7; i++) {
                buttonList.add(new LevelButton(this, levelList.get(i), i));
            }
        } else if (page == 2) {
            for(int i = 7; i < 14; i++) {
                buttonList.add(new LevelButton(this, levelList.get(i), i));
            }
        }
         */
    }
    /**
     * Draws all buttons
     */
    public void drawButtons() {

        for (int i = 0; i < pages[page].size(); i++) {
            pages[page].get(i).draw(game.batch);
        }

        back.draw(game.batch);


    }
    /**
     * Draws maxMoves indicator
     *
     * @param batch Spritebatch;
     */
    public void drawMaxMoves(SpriteBatch batch) {

        for (int i = 0; i < pages[page].size(); i++) {
            game.font.draw(batch, pages[page].get(i).getLevel().getMaxMoves()+"", Gdx.graphics.getWidth()*0.9f,
                    pages[page].get(i).getY());
        }

        if (page + 1 < pages.length) {


            String nextStr = "-->";
            next.setWidth(game.font.getBounds(nextStr).width);
            next.setHeight(game.font.getBounds(nextStr).height);
            next.setX(Gdx.graphics.getWidth() * 0.9f - next.getWidth() + game.font.getBounds("2").width);
            next.setY(Gdx.graphics.getHeight()*0.99f);

            game.font.draw(batch, nextStr, next.getX(), next.getY());
        }

        if(page - 1 >= 0) {


            String prevStr = "<--";
            prev.setWidth(game.font.getBounds(prevStr).width);
            prev.setHeight(game.font.getBounds(prevStr).height);
            prev.setX(Gdx.graphics.getWidth() * 0.15f + back.getWidth());
            prev.setY(Gdx.graphics.getHeight()*0.99f);
            game.font.draw(batch, prevStr, prev.getX(), prev.getY());
        }

    }

    /**
     * Draws medals according to saved data
     *
     * @param batch Spritebatch;
     */
    public void drawMedals(SpriteBatch batch) {

        Preferences prefs = game.getPrefs();
        for (int i = 0; i < pages[page].size(); i++) {

            int key = prefs.getInteger(levelList.get(i+(page*7)));
            float medalX = pages[0].get(1).getWidth() + pages[0].get(1).getX() +
                    (Gdx.graphics.getWidth()*0.9f - (pages[0].get(1).getX() +
                    pages[0].get(1).getWidth())) / 2 ;

            if (key == 2) {
                batch.draw(goldMedal, medalX
                        , pages[page].get(i).getY() - pages[page].get(i).getHeight() *1.2f);
            }
            else if (key == 1) {
                batch.draw(silverMedal, medalX,
                        pages[page].get(i).getY() - pages[page].get(i).getHeight() *1.2f);
            }
        }
    }

    public int getPage() {
        return page;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50/255f, 10/255f, 10/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        drawMedals(game.batch);
        drawButtons();
        drawMaxMoves(game.batch);

        game.batch.end();
        keyListens();
        cooldown++;
    }

    /**
     * Checks and handles touch events
     */
    public void keyListens() {

        if (Gdx.input.isTouched() && cooldown > 20){
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            cooldown = 0;
            for (int i = 0; i < pages[page].size(); i++) {
                LevelButton button = pages[page].get(i);

                if (x > button.getX() && x < button.getX()+button.getWidth()
                        && y > button.getY() - button.getHeight() && y < button.getY()) {
                    Level level = new Level(button.getMapName());
                    game.currentLevel = level;
                    game.currentLevelString = button.getMapName();
                    game.setScreen(new GameScreen(game, level));


                }
            }

            if (x > next.getX() && x < next.getX()+next.getWidth()
                    && y < next.getY() && y > next.getY()-next.getHeight() && page < pages.length -1) {
                page++;
                Gdx.app.log("LevelScreen", "touched");
            } else {
                Gdx.app.log("LevelScreen", "not touched " + next.getX() + " " + next.getY() + " "
                + next.getWidth() + " " + next.getHeight() + " " + x + " " + y);

            }

            if (x > prev.getX() && x < prev.getX()+next.getWidth()
                    && y < prev.getY() && y > prev.getY()-next.getHeight() && page > 0) {
                page--;
                Gdx.app.log("LevelScreen", "touched");
            } else {


            }

            back.onTouch(x,y);
        }
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
