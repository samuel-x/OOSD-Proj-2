import org.newdawn.slick.SlickException;

/**
 * This class represents a (spooky) skeleton in the world. The skeleton moves
 * independently of the player, every second. It will initially move up, until
 * it hits a wall or a block, in which case it will turn around.
 */
public class Skeleton extends Enemy implements Timed {

    final int DELAY = 1000;
    static long frameHold;

    /**
     * This is the constructor for skeleton.
     * @param image_src     Source of the Image
     * @param pos           Position of the skeleton
     * @throws SlickException if there is no image specified
     */
    public Skeleton(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
        this.current_dir = DIR_UP;
    }

    /**
     * Updates the skeleton for one frame.
     */
    @Override
    public void update() {
        // if the skeleton is moving, and it has been longer than one second,
        // then move it.
        if (current_dir != DIR_NONE &&
                (System.currentTimeMillis() >= frameHold)) {
            move(current_dir);
            frameHold = System.currentTimeMillis() + DELAY;
        }
    }

    /**
     * Checks if the next move is valid, if not, turns the skeleton around.
     * @param new_pos       Coordinate to check
     * @return boolean of whether the move was valid
     */
    @Override
    public boolean checkValid(Coordinate new_pos) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)){
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else {
            if (this.current_dir == DIR_UP) {
                this.current_dir = DIR_DOWN;
            } else {
                this.current_dir = DIR_UP;
            }
        }
        return false;
    }
}
