import org.newdawn.slick.SlickException;

/**
 *  This class represents a door in the game world.
 *  A door is a sprite which can be opened by pushing a block over the switch
 *  tile.
 */
public class Door extends Sprite {

    // variable for whether the door is open or not
    private boolean isOpen;

    /** Constructor for the Block class
     * @param image_src     directory source for the image
     * @param pos           coordinate of the position of this block
     * @throws SlickException in case the sprite has no image
     */
    public Door(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
        this.isOpen = false;
    }

    /**
     * This renders the door. If the door is not open, then do not render the
     * door.
     */
    @Override
    public void render() {
        if (!isOpen) {
            img.drawCentered(getPosX(), getPosY());
        }
    }

    /**
     * This is a getter for whether the door is open or not.
     * @return boolean      true or false whether this door is open or not.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * This is a setter for whether the door is open or not.
     * @param open          set the door being open to this boolean
     */
    public void setOpen(boolean open) {
        this.isOpen = open;
    }
}
