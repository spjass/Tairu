package fi.tamk.tiko.libgdx.tairu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.TairuGame;
import fi.tamk.tiko.libgdx.tairu.buttons.BackButton;


/**
 * Screen for help menu.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class HelpScreen implements Screen {
    private TairuGame game;
    OrthographicCamera camera;
    int HEIGHT = Gdx.graphics.getHeight();
    int WIDTH = Gdx.graphics.getWidth();
    StretchViewport viewPort;
    Sprite logo;
    float startHeight;
    float startWidth;
    float statsWidth;
    float statsHeight;
    String startStr;
    String statsStr;
    BackButton backButton;
    ArrayList<String> strList;


    /**
     * Constructor for the HelpScreen Screen.
     *
     * @param game Main game class
     */
    public HelpScreen(TairuGame game) {
        super();

        backButton = new BackButton(new Texture(Gdx.files.internal("back2.png")), game);
        this.game = game;
        game.setCurrentScreen(this);
        strList = initHelpText();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewPort = new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        camera.update();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50/255f, 10/255f, 10/255f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        initHelpText();
        drawText();
        backButton.draw(game.batch);


        game.batch.end();



        if (Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y = HEIGHT - Gdx.input.getY();

            backButton.onTouch(x,y);

        }
    }
    /**
     * Initializes texts for the help page.
     *
     * @return ArrayList of help text strings
     */
    public ArrayList<String> initHelpText() {
        strList = new ArrayList<String>();
        strList.add("OBJECTIVE");
        strList.add("The goal of the game");
        strList.add("is to colour all tiles");
        strList.add("with the same colour");
        strList.add("in as few moves");
        strList.add("as possible");


        //statsWidth = WIDTH/2-(game.font.getBounds(statsStr).width/2);
        return strList;
    }

    /**
     * Draws text for the page
     */
    public void drawText() {

        float textHeight = 0;

        for (int i = 0; i < strList.size(); i++) {
            if (i == 0) {
                textHeight = HEIGHT - HEIGHT*i*0.1f - HEIGHT*0.03f;
            } else {
                textHeight = HEIGHT - HEIGHT*i*0.15f - HEIGHT*0.1f;
            }
            float textWidth = WIDTH / 2 - (game.font.getBounds(strList.get(i)).width / 2);

            game.font.draw(game.batch, strList.get(i), textWidth, textHeight);
        }
    }


    @Override
    public void resize(int width, int height) {
        viewPort.update(width,height);
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
