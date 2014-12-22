package fi.tamk.tiko.libgdx.tairu.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


import java.util.ArrayList;

/**
 * Object for handling a .tmx file and generating a playable map out of it
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class Level {

    ArrayList<Tile> tileList;
    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;
    Tile[][] mapArray;
    ArrayList<Integer> typeList;
    public Toolbar bar;
    ArrayList<MenuTile> menuTiles;
    private int maxMoves;
    String mapName;
    Tile[][] originalMapArray;

    /**
     * Constructor for a Level object.
     *
     * @param mapName name of the .tmx file to load
     */
    public Level (String mapName) {
        tileList = new ArrayList<Tile>();

        tiledMap = new TmxMapLoader().load(mapName);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        mapArray = new Tile[16][10];
        originalMapArray = new Tile[16][10];
        generateCells();
        typeList = new ArrayList<Integer>();
        bar = new Toolbar(this);
        this.mapName = mapName;
        initMaxMoves();

    }

    public void draw(ShapeRenderer renderer) {

        for (int i = 0; i < 16; i++) {

            for (int j = 0; j < 10; j++) {

                mapArray[i][j].draw(renderer);

            }
        }

        bar.draw(renderer);


    }

    /**
     * Checks from map layers how many colors in the map
     *
     * @return ArrayList of all map colors/types
     */
    public ArrayList<Integer> generateTypeList() {
        typeList.clear();

        for (int i = 0; i < tiledMap.getLayers().getCount(); i++) {
            typeList.add(i);
        }

        return typeList;
    }
    /**
     * Checks from map properties the maxMoves value for each map
     */
    public void initMaxMoves() {

        MapProperties props = tiledMap.getProperties();
        String maxMovesstr = props.get("maxMoves", String.class);
        maxMoves = Integer.parseInt(maxMovesstr);

    }
    /**
     * Restarts the level
     */
    public void restart() {
        generateCells();
    }


    public int getMaxMoves() {
        return maxMoves;
    }

    public void setMaxMoves(int maxMoves) {
        this.maxMoves = maxMoves;
    }

    /**
     * Generates map array of tiles
     */
    public void generateCells() {

       for (int i = 0; i < tiledMap.getLayers().getCount(); i ++) {
           TiledMapTileLayer wallCells = (TiledMapTileLayer) tiledMap.getLayers().get(i);
           for (int j = 0; j < 16; j++) {
               for (int k = 0; k < 10; k++) {


                   if(wallCells.getCell(j,k) != null) {
                       Tile tmpTile = new Tile(j,k,i);
                       tileList.add(tmpTile);
                       mapArray[j][k] = tmpTile;
                       originalMapArray[j][k] = tmpTile;
                   }

               }

           }
       }

        Gdx.app.log("Level", "Size " + tileList.size());


    }

    public ArrayList<Tile> getTileList() {
        return tileList;
    }

    public void setTileList(ArrayList<Tile> tileList) {
        this.tileList = tileList;
    }

    public Tile[][] getMapArray() {
        return mapArray;
    }

    public void setMapArray(Tile[][] mapArray) {
        this.mapArray = mapArray;
    }

    public ArrayList<MenuTile> getMenuTiles() {
        return bar.getMenuTiles();
    }

    public MenuTile getSelectedMenuTile() {
        ArrayList<MenuTile> menuTiles = bar.getMenuTiles();

        for (int i = 0; i < menuTiles.size(); i++) {

            if (menuTiles.get(i).isSelected()) {

                return menuTiles.get(i);
            }
        }

        return null;
    }
}
