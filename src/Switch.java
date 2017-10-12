import org.newdawn.slick.SlickException;

/**
 * This class represents one switch on the screen. A switch opens a door.
 */
public class Switch extends Sprite {

    /**
     * This is the constructor for a switch.
     * @param image_src     Source of the Image
     * @param pos           Position of the switch
     * @throws SlickException if there is no image specified
     */
    public Switch(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }

    /**
     * Updates the switch for one frame.
     */
    public void update() {
        // Check if the current switch is covered. If it is, set the
        // door to open.
        GameManager.switchDoor(checkCovered());
    }

    /**
     * This checks if the current switch is covered by a block.
     * @return boolean of whether this switch is covered by a block.
     */
    public boolean checkCovered() {
        for (Sprite sprite : GameManager.getTilesAtPos(pos)) {
            if (sprite instanceof Block) {
                return true;
            }
        }
        return false;
    }

}
