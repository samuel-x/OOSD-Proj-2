import org.newdawn.slick.SlickException;

/**
 * This class represents a rogue in the world. The rogue moves when the player
 * moves. Initially it will move left, until it hits a wall in which case it
 * will turn around. The rogue can also move blocks like the player can.
 */
public class Rogue extends Enemy {

    /**
     * This is the constructor for rogue.
     * @param image_src     Source of the Image
     * @param pos           Position of the rogue
     * @throws SlickException if there is no image specified
     */
    public Rogue(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
        this.current_dir = DIR_LEFT;
    }

    /**
     * Checks if the move was valid. Also changes direction for the rogue if
     * it hits a wall.
     * @param new_pos       Coordinate to check
     * @return boolean of whether the move is valid.
     */
    @Override
    public boolean checkValid(Coordinate new_pos) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) &&
                GameManager.tryPush(new_pos, current_dir)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else {
            if (current_dir == DIR_LEFT) {
                current_dir = DIR_RIGHT;
            } else {
                current_dir = DIR_LEFT;
            }
        }
        return false;
    }
}
