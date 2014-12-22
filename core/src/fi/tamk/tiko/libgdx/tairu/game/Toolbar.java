package fi.tamk.tiko.libgdx.tairu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.TairuGame;
import fi.tamk.tiko.libgdx.tairu.screens.GameScreen;


/**
 * Creates a toolbar for the level, where user can choose color, restart map, exit to menu etc.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class Toolbar {

    float height;
    float width;
    float x;
    float y;
    Color baseColor;
    ArrayList<Integer> typeList;
    ArrayList<MenuTile> menuTiles;
    private static String TAG = "Toolbar";
    //int selection;
    ShapeRenderer menuRenderer;
    TairuGame game;
    GameScreen screen;
    int moves;
    BitmapFont font;
    Sprite restartButton;
    Sprite homeButton;


    public Toolbar(Level level) {
        this.game = game;
        this.screen = screen;
        menuRenderer = new ShapeRenderer();
        baseColor = new Color(50/255f,10/255f,10/255f,1);
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth()*0.15f;
        this.y = 0;
        this. x = Gdx.graphics.getWidth() - this.width;
        this.typeList = level.generateTypeList();
        menuTiles = new ArrayList<MenuTile>();
        generateTiles();
        //selection = 0;
        menuTiles.get(0).setSelected(true);
        font = new BitmapFont(Gdx.files.internal("font2.fnt"));
        restartButton = new Sprite(new Texture(Gdx.files.internal("restart.gif")));
        homeButton = new Sprite(new Texture(Gdx.files.internal("back2.png")));
    }

    /**
     * Draws menutiles
     *
     * @param renderer Shaperenderer
     */

    public void draw (ShapeRenderer renderer) {
        renderer.setColor(baseColor);
        renderer.rect(x, y, width, height); // Background

        for (int i = 0; i < menuTiles.size(); i++) {
            menuTiles.get(i).draw(renderer);
        }

    }

    /**
     * Draws maxMoves indicator text
     */
    public void render() {
        String str = moves + "/" + game.currentLevel.getMaxMoves();
        font.setScale(0.5f,0.5f);
        font.draw(game.batch, str, x+width/2 - game.font.getBounds(str)
                        .width/4, 0+Gdx.graphics.getHeight()*0.1f);

    }
    /**
     * Generates MenuTiles
     */
    public void generateTiles() {

        for (int i = 0; i < typeList.size(); i++) {
            MenuTile tile = new MenuTile(0,0,typeList.get(i));
            tile.setX(Gdx.graphics.getWidth()-this.width/2-tile.getWidth()/2);
            tile.setY(height - (height * 0.125f * (i + 1)));
            Gdx.app.log(TAG, tile.getX() + " " + tile.getY());
            menuTiles.add(tile);
        }
    }

    /**
     * Draws selection indicator
     */
    public void drawSelection() {

        menuRenderer.begin(ShapeRenderer.ShapeType.Line);
        menuRenderer.setColor(Color.YELLOW);
        for (int i = 0; i < menuTiles.size(); i++) {

            if (menuTiles.get(i).isSelected()) {
                menuRenderer.rect(menuTiles.get(i).getX()-menuTiles.get(i).getWidth()*(1 - 1.0f/1.182f),
                        menuTiles.get(i).getY()-menuTiles.get(i).getHeight()*(1 - 1.0f/1.182f),
                        menuTiles.get(i).getWidth()*1.3f,menuTiles.get(i).getHeight()*1.3f);
            }
        }
        menuRenderer.end();
    }

    /**
     * Draws restart button
     */
    public void drawRestartButton(SpriteBatch batch) {
        float width = restartButton.getWidth();
        float height = restartButton.getHeight();
        float restartx = (Gdx.graphics.getWidth() - this.width/2) - width/2;
        float restarty = (this.height * 0.14f);
        restartButton.setX(restartx);
        restartButton.setY(restarty);

        batch.draw(restartButton,restartx,restarty,width,height);
    }

    /**
     * Draws home/back
     */
    public void drawHomeButton(SpriteBatch batch) {
        float width = homeButton.getWidth();
        float height = homeButton.getHeight();

        float homex = (Gdx.graphics.getWidth() - this.width/2) - width/2;
        float homey = (this.height * 0.27f);
        homeButton.setX(homex);
        homeButton.setY(homey);

        batch.draw(homeButton,homex,homey,width,height);
    }

    public ArrayList<MenuTile> getMenuTiles() {
        return menuTiles;
    }

    public void setMenuTiles(ArrayList<MenuTile> menuTiles) {
        this.menuTiles = menuTiles;
    }

    /**
     * Clears all selections
     */
    public void clearSelections() {
        for (int i = 0; i < menuTiles.size(); i++) {
            menuTiles.get(i).setSelected(false);
        }
    }

    /**
     * Checks and handles touch events
     */
    public void checkTouch(float x, float y) {

        for (int i = 0; i < menuTiles.size(); i++) {
            MenuTile tile = menuTiles.get(i);
            if ((x > tile.getX() && x < tile.getX() + tile.getWidth()
                    && (y > tile.getY() && y < tile.getY() + tile.getHeight()))) {
                clearSelections();
                menuTiles.get(i).setSelected(true);
            }
        }

        buttonTouch(restartButton,x,y);
        buttonTouch(homeButton,x,y);

    }

    /**
     * Checks home & restart button touch events
     */
    public void buttonTouch(Sprite button, float x, float y) {


        Gdx.app.log(TAG, x + " " + y + " BUTTONTOUCH");
        Gdx.app.log(TAG, button.getX() + " " + button.getY() + " " + button.getWidth() + " "
                + button.getHeight() + " BUTTONTOUCH");

        if (x+ button.getWidth()*0.3f > button.getX()
                && x - button.getWidth()*0.3f < button.getX()+button.getWidth()
                && y + button.getHeight()*0.3f > button.getY()
                && y - button.getHeight()*0.3f < button.getY()+button.getHeight() ) {


            if (button == homeButton) {

                game.setScreen(game.getLevelScreen());
            } else if (button == restartButton) {
                GameScreen gameScreen = new GameScreen(game, new Level(game.currentLevelString));
                game.setScreen(gameScreen);
            }

        } else {

        }
    }



    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void addMove() {
        this.moves++;
    }

    public void setGame(TairuGame game) {
        this.game = game;
    }
}
