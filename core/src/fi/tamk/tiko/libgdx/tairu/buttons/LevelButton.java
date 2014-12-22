package fi.tamk.tiko.libgdx.tairu.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tamk.tiko.libgdx.tairu.game.Level;
import fi.tamk.tiko.libgdx.tairu.screens.GameScreen;
import fi.tamk.tiko.libgdx.tairu.screens.LevelScreen;
import fi.tamk.tiko.libgdx.tairu.TairuGame;

/**
 * Button objects for LevelScreen
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class LevelButton {

    private int id;
    private String mapName;
    private Level level;
    private float x;
    private float y;
    private float width;
    private float height;
    private String buttonText;
    private LevelScreen screen;
    private TairuGame game;

    /**
     * Constructor for a LevelButton object.
     *
     * @param screen selected LevelScreen
     * @param mapName Name of .tmx file
     * @param mapName id identification number
     * @param mapName page Page number
     */
    public LevelButton (LevelScreen screen, String mapName, int id, int page) {
        this.mapName = mapName;

        buttonText = "LEVEL " + (id + 1 + page * 7);

        this.screen = screen;
        this.game = screen.game;
        this.width = game.font.getBounds(buttonText).width;
        this.height = game.font.getBounds(buttonText).height;
        this.id = id;
        this.level = new Level(mapName);
        figurePosition();
    }
    /**
     * Figures coordinates on the screen according to level number
     */
    public void figurePosition() {
        id++;
        if (screen.getPage() == 1) {
            y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 8 * id + 1);
        } else {
            y = Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 8 * id + 1);
        }
        x = Gdx.graphics.getWidth()*0.1f;
    }

    public void choose() {

    }

    public String getMapName() {
        return this.mapName;
    }

    public void draw (SpriteBatch batch) {
        game.font.draw(batch, buttonText, x,
                y);
    }

    public int getId() {
        return this.id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
