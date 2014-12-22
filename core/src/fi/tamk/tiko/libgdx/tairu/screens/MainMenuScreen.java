package fi.tamk.tiko.libgdx.tairu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import fi.tamk.tiko.libgdx.tairu.TairuGame;
import fi.tamk.tiko.libgdx.tairu.buttons.HelpButton;
import fi.tamk.tiko.libgdx.tairu.buttons.MuteButton;


/**
 * Screen for main menu.
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class MainMenuScreen implements Screen {
    private TairuGame game;
    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    int selectedItem;
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
    public boolean mainMenu;
    HelpButton helpButton;
    MuteButton muteButton;



    /**
     * Constructor for the MainMenuScreen Screen.
     *
     * @param game Main game class
     */
    public MainMenuScreen(TairuGame game) {
        super();
        Gdx.app.log("Menu", HEIGHT + "");
        mainMenu = true;
        helpButton = new HelpButton(new Texture(Gdx.files.internal("help.png")), game);
        this.game = game;
        muteButton = new MuteButton(game);
        logo = new Sprite(new Texture(Gdx.files.internal("logo.png")));
        startHeight = 0;
        startWidth = 0;
        statsWidth = 0;
        statsHeight = 0;
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewPort = new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);
        selectedItem = 0;
        camera.update();
        game.setCurrentScreen(this);
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(50/255f, 10/255f, 10/255f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        startStr = "START GAME";
        statsStr = "STATS";

        game.batch.draw(logo,WIDTH/2-(logo.getWidth()/2), HEIGHT/2.7f);
        startWidth = WIDTH/2-(game.font.getBounds(startStr).width/2);
        statsWidth = WIDTH/2-(game.font.getBounds(statsStr).width/2);
        startHeight = HEIGHT/3.1f;
        statsHeight = HEIGHT/6.1f;
        helpButton.draw(game.batch);
        muteButton.draw(game.batch);
        game.font.draw(game.batch, startStr, startWidth, HEIGHT/3.1f);
        game.font.draw(game.batch, statsStr, statsWidth, HEIGHT/6.1f);

        game.batch.end();

        checkTouch();


    }

    public void select() {

        if (selectedItem == 0) {

        } else if (selectedItem == 1) {

        }
    }
    /**
     * Checks and handles touch events
     */
    public void checkTouch() {

        if (Gdx.input.isTouched()){
            float x = Gdx.input.getX();
            float y = HEIGHT - Gdx.input.getY();
            Gdx.app.log("Menu", x + " " + y);
            if (x > startWidth && x < startWidth + game.font.getBounds(startStr).width
                    && y < startHeight && y > startHeight - game.font.getBounds(startStr).height) {
                game.setScreen(game.getLevelScreen());
            } else if (x > statsWidth && x < statsWidth + game.font.getBounds(statsStr).width
                    && y < statsHeight && y > statsHeight - game.font.getBounds(statsStr).height) {
                game.setScreen(new StatsScreen(game));
            }

            helpButton.onTouch(x,y);
            muteButton.onTouch(x,y);
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
