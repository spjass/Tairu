package fi.tamk.tiko.libgdx.tairu.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

import fi.tamk.tiko.libgdx.tairu.TairuGame;

/**
 * Created by Spjass on 06-Dec-14.
 */
public class Solver {
    TairuGame game;
    ArrayList<Level> levelList;
    Tile[][] mapArray;
    Tile[][] tempArray;
    ArrayList<Tile> tileList;
    Level level;
    ArrayList<Integer> typeList;
    ArrayList<SolverPoint> moveList;
    int mainNodes;
    int count;
    int turn;

    public Solver(TairuGame game) {
        this.game = game;

    }

    public void solve(Level level) {
        mapArray = level.getMapArray();
        tempArray = level.getMapArray();
        tileList = level.getTileList();
        typeList = level.generateTypeList();
        moveList = new ArrayList<SolverPoint>();
        this.level = level;
        this.mainNodes = countMainNodes();
        count = 0;
        turn = 0;
        Gdx.app.log("Solver", countMainNodes() + " Main nodes");
        countColorNodes();

    }

    public int countMainNodes() {
        int amount = 0;
        int nodes = typeList.size();
        for (int k = 0; k < typeList.size(); k++) {
             amount = 0;

            for (int i = 0; i < 16; i++) {

                for (int j = 0; j < 10; j++) {

                    Tile tile = mapArray[i][j];
                    int type = tile.getType();

                    if (typeList.get(k) == type) {
                        amount++;
                    }

                }

            }
            if (amount == 0) {
                nodes--;
            }
        }

        return nodes;
    }

    public void countColorNodes() {

        int oldCount = 0;
        for (int k = 0; k < countMainNodes(); k++) {

            for (int i = 0; i < 16; i++) {

                for (int j = 0; j < 10; j++) {
                    //mapArray = null;
                   // mapArray = level.getMapArray();
                    Tile tile = mapArray[i][j];
                    int type = tile.getType();
                    oldCount = count;
                    checkNearbyTiles(i,j,k);
                    if (countMainNodes() < mainNodes) {
                        Gdx.app.log("Solver", "nodes amount changed");
                        SolverPoint point = new SolverPoint(i,j,k,100 + count - oldCount);
                        moveList.add(point);
                    } else if (oldCount != count) {
                        SolverPoint point = new SolverPoint(i,j,k,count - oldCount);
                        //moveList.add(point);

                    }

                    if (moveList.size() > 25) {
                        moveList.clear();
                        level.restart();
                        turn = 0;
                        countColorNodes();

                    }



                    turn++;
                    mainNodes = countMainNodes();
                    Gdx.app.log("Solver", countMainNodes() + " Main nodes, turn " + turn +
                            " movelist " + moveList.size());


                }

            }

        }

        for (int i = 0; i < moveList.size(); i++) {
            SolverPoint point = moveList.get(i);
            Gdx.app.log("Solver", "X: " + point.getX() + " Y: " + point.getY()
                    + " type: " + point.type + " value " + point.value);
        }

    }

    public void runMoveList(ArrayList<SolverPoint> moveList) {

        for (int i = 0; i < moveList.size(); i++) {


            checkNearbyTiles(moveList.get(i).x,moveList.get(i).y,moveList.get(i).type);
        }

    }

    public void checkNearbyTiles (int tilex, int tiley, int swapType) {
        boolean found = true;
        int x = tilex;
        int y = tiley;
        int basetype = mapArray[tilex][tiley].getType();
        Gdx.app.log("Solver", count + " Count");
        boolean same;

        if (swapType == basetype) {
            same = true;

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
                    count++;
                    checkNearbyTiles(tilex-1,tiley,swapType);

                }
            }

            if (tiley - 1 >= 0) {
                if(colorMatch(basetype, mapArray[tilex][tiley-1])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    count++;
                    checkNearbyTiles(tilex,tiley-1,swapType);
                }
            }

            if (tilex + 1 < 16) {
                if(colorMatch(basetype, mapArray[tilex+1][tiley])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    count++;
                    checkNearbyTiles(tilex+1,tiley,swapType);
                }
            }

            if (tiley + 1 < 10) {
                if(colorMatch(basetype, mapArray[tilex][tiley+1])) {
                    swapTiles(mapArray[tilex][tiley],swapType);
                    count++;
                    checkNearbyTiles(tilex,tiley+1,swapType);
                }
            }

            swapTiles(mapArray[tilex][tiley],swapType);
            count++;


        }
    }

    public boolean colorMatch(int basetype, Tile tile2) {
        if (basetype == tile2.getType()) {

            return true;
        }
        return false;
    }

    public void swapTiles(Tile tile, int type) {
        if (tile.getType() != type) {
            tile.setType(type);
        }
    }

}
