package fi.tamk.tiko.libgdx.tairu.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Calendar;

import fi.tamk.tiko.libgdx.tairu.screens.HelpScreen;
import fi.tamk.tiko.libgdx.tairu.TairuGame;


/**
 * Sprite class for mute button.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class MuteButton extends Sprite {
    private TairuGame game;
    private Texture texture;
    private boolean mute;
    private int timeout;

    /**
     * Constructor for a MuteButton sprite.
     *
     * @param game Main game class
     */
    public MuteButton(TairuGame game) {
        super(new Texture(Gdx.files.internal("soundon.png")));
        this.mute = false;
        this.game = game;
        this.setX(Gdx.graphics.getWidth()*0.77f);
        this.setY(Gdx.graphics.getHeight()*0.97f - this.getHeight());
        this.timeout = 15; //frames




    }

    public void onTouch (float x, float y) {
        if (x > this.getX() && x < this.getX() + this.getWidth()
                && y > this.getY() && y < this.getY() + this.getHeight() && timeout > 15) {
            switchMute();
            timeout = 0;
        }
    }
    /**
     * Switches between sounds off / on
     */
    public void switchMute() {
        if(mute) {
            game.music.setVolume(0.4f);
            game.clickSound.setVolume(0.5f);
            game.victorySound.setVolume(0.5f);
            game.startSound.setVolume(0.5f);
            game.defeatSound.setVolume(0.5f);
            super.setTexture(new Texture(Gdx.files.internal("soundon.png")));
            mute = false;
        } else {
            game.music.setVolume(0);
            game.clickSound.setVolume(0);
            game.victorySound.setVolume(0);
            game.startSound.setVolume(0);
            game.defeatSound.setVolume(0);
            super.setTexture(new Texture(Gdx.files.internal("soundmute.png")));
            mute = true;
        }
    }

    public void draw(SpriteBatch batch) {

        batch.draw(this, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        timeout++;
    }
}
