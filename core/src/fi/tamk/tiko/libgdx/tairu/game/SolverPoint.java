package fi.tamk.tiko.libgdx.tairu.game;

import java.awt.Point;

/**
 * Created by Spjass on 06-Dec-14.
 */
public class SolverPoint extends Point {
    int type;
    int value;

    public SolverPoint(int x, int y, int type, int value) {
        super(x, y);
        this.type = type;
        this.value = value;
    }
}
