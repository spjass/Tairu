package fi.tamk.tiko.libgdx.tairu;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.util.LinkedList;

public class TairuGame extends ApplicationAdapter {
    Texture img;
    private OrthographicCamera camera;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch batch;
    int frame;
    float width;
    float height;
    Music music;


    /**
     * Window width in pixels
     */
    private final int WINDOW_WIDTH = 1024;

    /**
     * Window height in pixels
     */
    private final int WINDOW_HEIGHT = 640;


    /*
     * Map size is 42 x 42 tiles.
     */
    static int TILES_AMOUNT_WIDTH  = 16;
    static int TILES_AMOUNT_HEIGHT = 10;

    /**
     * One tile is 32
     */
    static int TILE_WIDTH          = 64;
    static int TILE_HEIGHT         = 64;

    /**
     * World in pixels
     * WORLD_HEIGHT_PIXELS = 10 * 64= 640 pixels
     * WORLD_WIDTH_PIXELS  = 16 * 64 = 1024 pixels
     */
    public static int WORLD_HEIGHT_PIXELS = TILES_AMOUNT_HEIGHT * TILE_HEIGHT;
    public static int WORLD_WIDTH_PIXELS  = TILES_AMOUNT_WIDTH  * TILE_WIDTH;

    @Override
    public void create () {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,         // y points up
                WINDOW_WIDTH,            // width
                WINDOW_HEIGHT);          // height
        camera.update();
        tiledMap = new TmxMapLoader().load("map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.begin();
        batch.end();
    }
}
