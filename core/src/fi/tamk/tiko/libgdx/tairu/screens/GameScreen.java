package fi.tamk.tiko.libgdx.tairu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.game.Level;
import fi.tamk.tiko.libgdx.tairu.game.MenuTile;
import fi.tamk.tiko.libgdx.tairu.game.Solver;
import fi.tamk.tiko.libgdx.tairu.TairuGame;
import fi.tamk.tiko.libgdx.tairu.game.Tile;

/**
 * Screen for actual gameplay, holds most of the gameplay logic.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class GameScreen implements Screen {


    Texture img;
    private OrthographicCamera camera;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch batch;
    int frame;
    float width;
    float height;
    Music music;
    TairuGame game;
    private Tile[][] mapArray;
    private ShapeRenderer shapeRenderer;
    FitViewport viewport;
    ArrayList<Tile> swapList;
    private int cooldown;
    Level level;
    boolean victory;
    int moves;
    int victorycd;
    boolean defeat;
    Sprite gold;
    Sprite silver;
    Solver solver;

    /*
     * Map size is 42 x 42 tiles.
     */
    public static int TILES_AMOUNT_WIDTH  = 16;
    public static int TILES_AMOUNT_HEIGHT = 10;

    /**
     * Constructor for the GameScreen Screen.
     *
     * @param game Main game class
     * @param level Selected level
     */
    public GameScreen(TairuGame game, Level level) {
        this.game = game;
        game.setCurrentScreen(this);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        camera.update();
        solver = new Solver(game);
        batch = new SpriteBatch();
        this.level = level;
        gold = new Sprite(new Texture(Gdx.files.internal("gold.png")));
        silver = new Sprite(new Texture(Gdx.files.internal("silver.png")));
        mapArray = level.getMapArray();
        shapeRenderer = new ShapeRenderer();
        swapList = new ArrayList<Tile>();
        cooldown = 0;
        shapeRenderer = new ShapeRenderer();
        victory = false;
        moves = 0;

        level.bar.setGame(game);


    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        camera.update();

        if (cooldown > 15) {
            checkTouch();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        level.draw(shapeRenderer);
        shapeRenderer.end();
        level.bar.drawSelection();
        batch.begin();

        batch.end();
        game.batch.begin();

        level.bar.render();
        drawVictoryText();
        drawDefeatText();

        level.bar.drawRestartButton(game.batch);
        level.bar.drawHomeButton(game.batch);
        game.batch.end();
        cooldown++;
    }


    /**
     * Swaps the color type of the selected tile to the selected type/color
     *
     * @param tile Tile object
     * @param type Swap type / color
     */
    public void swapTiles(Tile tile, int type) {
        if (tile.getType() != type) {
            tile.setType(type);
        }
    }

    /**
     * Method for handling victory text and medal drawing
     */
    public void drawVictoryText() {
        if (victory) {
            String victorystr = "VICTORY";
            game.font.draw(game.batch, victorystr, Gdx.graphics.getWidth()*0.85f/2-
                    game.font.getBounds(victorystr).width/2 , Gdx.graphics.getHeight()/2
                    + Gdx.graphics.getHeight()*0.2f);
            if (moves <= game.currentLevel.getMaxMoves()) {
                drawGold(true,game.batch);
            } else {
                drawGold(false,game.batch);
            }
        }
    }

    /**
     * Method for handling defeat text drawing
     */
    public void drawDefeatText() {
        if (defeat) {
            String defeatstr = "DEFEAT";
            game.font.draw(game.batch, defeatstr, Gdx.graphics.getWidth()*0.85f/2-
                    game.font.getBounds(defeatstr).width/2 , Gdx.graphics.getHeight()/2
                    + Gdx.graphics.getHeight()*0.2f);
        }
    }

    /**
     * Checks all surrounding tiles of given coordinates and swaps nearby tiles colors until
     * the whole node is checked and colored.
     *
     * @param tilex x tile coordinate in map array
     * @param tiley y tile coordinate in map array
     */
    public void checkNearbyTiles (int tilex, int tiley) {
        boolean found = true;
        int x = tilex;
        int y = tiley;
        int basetype = mapArray[tilex][tiley].getType();
        MenuTile menuTile = level.getSelectedMenuTile();
        int swapType = menuTile.getType();

        boolean same;

        if (swapType == basetype) {
            same = true;
            moves--;
        } else {
            same = false;
        }
        /*
        while (x + 1 < 16 && found) {

            if (colorMatch(basetype, mapArray[tilex+1][tiley])) {


            }
        }
        */

       if (tilex >= 0 && tilex < 16 && tiley >= 0 && tiley < 10 && !same) {

            if (tilex - 1 >= 0) {
                if(colorMatch(basetype, mapArray[tilex-1][tiley])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    checkNearbyTiles(tilex-1,tiley);

                }
            }

            if (tiley - 1 >= 0) {
                if(colorMatch(basetype, mapArray[tilex][tiley-1])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    checkNearbyTiles(tilex,tiley-1);
                }
            }

            if (tilex + 1 < 16) {
                if(colorMatch(basetype, mapArray[tilex+1][tiley])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    checkNearbyTiles(tilex+1,tiley);
                }
            }

            if (tiley + 1 < 10) {
                if(colorMatch(basetype, mapArray[tilex][tiley+1])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    checkNearbyTiles(tilex,tiley+1);
                }
            }

           swapTiles(mapArray[tilex][tiley],swapType);


        }
    }
    /**
     * Checks all surrounding tiles of given coordinates and swaps nearby tiles colors until
     * the whole node is checked and colored.
     *
     * @param basetype Color/type which to check against
     * @param tile2 Tile to check
     * @return True if colors match
     */
    public boolean colorMatch(int basetype, Tile tile2) {
        if (basetype == tile2.getType()) {
;
            return true;
        }
        return false;
    }

    /**
     * Method for checking and handling touch events
     */
    public void checkTouch () {
        if (Gdx.input.isTouched()) {
            cooldown = 0;
            float x = (Gdx.input.getX());
            int tilex = (int) (Gdx.input.getX() / (Gdx.graphics.getWidth() / TILES_AMOUNT_WIDTH * 0.85f));
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            int tiley = 9 - Gdx.input.getY() / (Gdx.graphics.getHeight() / TILES_AMOUNT_HEIGHT);
            if (tilex < GameScreen.TILES_AMOUNT_WIDTH && tiley < GameScreen.TILES_AMOUNT_HEIGHT) {
                checkNearbyTiles(tilex, tiley);
                moves++;
                game.clickSound.stop();
                game.clickSound.play();
                level.bar.setMoves(moves);

                if (checkForVictory() && !checkForDefeat()) {
                    victory = true;

                    Gdx.app.log("GameScreen", "victory");
                } else if(checkForDefeat()) {
                    defeat = true;

                }else {

                    Gdx.app.log("GameScreen", "no victory");
                }

            }else {
                level.bar.checkTouch(x, y);
            }
        }
    }
    /**
     * Checks if map is solved.
     *
     * @return True if victory achieved
     */
    public boolean checkForVictory() {

        ArrayList<Tile> tileList = level.getTileList();
        int lastType;
        lastType = 0;
        for (int i = 0; i < tileList.size(); i++) {

            if (i == 0) {
                lastType = tileList.get(i).getType();
            } else {
                if (tileList.get(i).getType() == lastType) {

                } else {
                    return false;
                }
                lastType = tileList.get(i).getType();
            }

        }
        if (victory) {
            game.setScreen(game.getLevelScreen());
        } else {
            game.victorySound.stop();
            game.victorySound.play();
        }

        victory = true;

        // Gold
        if (moves == level.getMaxMoves() && game.getPrefs().getInteger(game.currentLevelString) < 2) {
            game.getPrefs().putInteger(game.currentLevelString, 2);
            Gdx.app.log("GameScreen", "GOLD");
        } else {
            Gdx.app.log("GameScreen", "nope");

        }
        // Silver
        if (moves == level.getMaxMoves()+1  && game.getPrefs().getInteger(game.currentLevelString) < 1) {
            game.getPrefs().putInteger(game.currentLevelString, 1);
            Gdx.app.log("GameScreen", "SILVER");
        } else {
            Gdx.app.log("GameScreen", "nope");

        }

        game.getPrefs().flush();



        return true;

    }
    /**
     * Method for drawing medals
     *
     * @param yes True if gold, false if silver
     * @param batch Spritebatch
     */
    public void drawGold(boolean yes, SpriteBatch batch) {

        if (yes) {
            batch.draw(gold, Gdx.graphics.getWidth()*0.85f/2 - gold.getWidth()/2, Gdx.graphics.getHeight()/3);
        } else {
            batch.draw(silver, Gdx.graphics.getWidth()*0.85f/2 - silver.getWidth()/2, Gdx.graphics.getHeight()/3);

        }
    }
    /**
     * Method for checking if map is unsolvable within the minimum moves and handling defeat events
     *
     * @return True if unsolvable in maxMoves+1 moves
     */
    public boolean checkForDefeat()  {

        if (defeat) {
            Gdx.app.log("victory", "yayy");

            game.setScreen(new GameScreen(game, new Level(game.currentLevelString)));
        }

        if (moves > level.getMaxMoves() && !victory && !game.isDebug()) {
            defeat = true;

            return true;
        }

        return false;
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
        batch.dispose();
        shapeRenderer.dispose();

    }
}
