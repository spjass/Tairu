package fi.tamk.tiko.libgdx.tairu.game;

/**
 * Object for a toolbar color selection tile
 *
 * @author Juho Rautio
 * @version 1.0
 * @since 1.0
 */
public class MenuTile extends Tile {

    boolean selected;

    /**
     * Constructor for a MenuTile object.
     *
     * @param tilex x-coordinate in map array
     * @param tiley y-coordinate in map array
     * @param type tile color/type
     */
    public MenuTile(int tilex, int tiley, int type) {
        super(tilex, tiley, type);


        this.selected = false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }


}
