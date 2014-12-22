package fi.tamk.tiko.libgdx.tairu.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.tamk.tiko.libgdx.tairu.screens.HelpScreen;
import fi.tamk.tiko.libgdx.tairu.TairuGame;


/**
 * Sprite class for help button.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class HelpButton extends Sprite {
    TairuGame game;

    /**
     * Constructor for a HelpButton sprite.
     *
     * @param texture Wanted texture for the sprite
     * @param game Main game class
     */
    public HelpButton(Texture texture, TairuGame game) {
        super(texture);
        this.game = game;
        this.setX(Gdx.graphics.getWidth()*0.87f);
        this.setY(Gdx.graphics.getHeight()*0.97f - this.getHeight());

    }

    public void onTouch (float x, float y) {
        if (x > this.getX() && x < this.getX() + this.getWidth()
                && y > this.getY() && y < this.getY() + this.getHeight()) {
            game.setScreen(new HelpScreen(game));
        }
    }

    public void draw(SpriteBatch batch) {

        batch.draw(this, this.getX(), this.getY(), this.getWidth(), this.getHeight());

    }
}
