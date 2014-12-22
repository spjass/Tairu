package fi.tamk.tiko.libgdx.tairu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import fi.tamk.tiko.libgdx.tairu.screens.GameScreen;

/**
 * Object for a single tile
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class Tile {

    private float x;
    private float y;
    private Color color;
    private int tilex;
    private int tiley;
    private int type;
    private float width;
    private float height;

    public Tile() {

    }


    /**
     * Constructor for a Tile object.
     *
     * @param tilex x-coordinate in map array
     * @param tiley y-coordinate in map array
     * @param type tile color/type
     */
    public Tile(int tilex, int tiley, int type) {
        this.tilex = tilex;
        this.tiley = tiley+1;
        this.x = tilex* Gdx.graphics.getWidth()/ GameScreen.TILES_AMOUNT_WIDTH*0.85f;
        this.y = tiley*Gdx.graphics.getHeight()/GameScreen.TILES_AMOUNT_HEIGHT;
        this.type = type;
        color = new Color(0/255f,0/255f,0/255f,1);
        width = Gdx.graphics.getWidth()/GameScreen.TILES_AMOUNT_WIDTH*0.85f;
        height = Gdx.graphics.getHeight()/GameScreen.TILES_AMOUNT_HEIGHT;

    }

    /**
     * Draws tile according to its color type.
     *
     * @param shapeRenderer Shaperenderer
     */

    public void draw(ShapeRenderer shapeRenderer) {
            switch(type) {

                case 0: color = new Color(234/255f,181/255f,137/255f,1); // LIGHT
                    break;
                case 1: color = new Color(109/255f,187/255f,109/255f,1); // GREEN
                    break;
                case 2: color = new Color(82/255f,140/255f,140/255f,1); // BLUE
                    break;
                case 3: color = new Color(142/255f,0/255f,0/255f,1); // RED
                    break;
                case 4: color = new Color(142/255f,64/255f,0/255f,1); // BROWN
                    break;
            }
            shapeRenderer.setColor(color);


            shapeRenderer.rect(x,y,width,height);


    }

    public float getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getTilex() {
        return tilex;
    }

    public void setTilex(int tilex) {
        this.tilex = tilex;
    }

    public int getTiley() {
        return tiley;
    }

    public void setTiley(int tiley) {
        this.tiley = tiley;
    }
}
