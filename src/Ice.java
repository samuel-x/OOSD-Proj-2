import org.newdawn.slick.SlickException;

/**
 *  This class represents a block of ice in the game world.
 *  A block of ice acts like a normal block, but moves at a rate of 0.25
 *  tiles per second when pushed.
 */
public class Ice extends Block implements Timed {
    /** The delay the ice block moves moving between tiles after being pushed */
    private final int DELAY = 250;
    private static long frameHold;
    private int current_dir;

    /**
     * This is the constructor for ice.
     * @param image_src     Source of the Image
     * @param pos           Position of the ice
     * @throws SlickException if there is no image specified
     */
    public Ice(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
        this.current_dir = DIR_NONE;
    }

    /**
     * Updates the ice block for this frame.
     */
    public void update() {
        // If the ice is currently moving
        if (current_dir != DIR_NONE) {
            // Check if enough time has passed before sliding again
            if (System.currentTimeMillis() >= frameHold) {
                // slide the block
               move(current_dir);
               frameHold = System.currentTimeMillis() + DELAY;
            }
        }
    }

    /**
     * Moves the ice block in a direction
     * @param dir       The ice block will move in this direction
     * @return boolean  True if this ice block moved, false otherwise
     */
    @Override
    public boolean move(int dir)
    {
        // Translate the direction to an x and y displacement
        int delta_x = 0, delta_y = 0;
        switch (dir) {
            case DIR_LEFT:
                delta_x = -SPEED;
                break;
            case DIR_RIGHT:
                delta_x = SPEED;
                break;
            case DIR_UP:
                delta_y = -SPEED;
                break;
            case DIR_DOWN:
                delta_y = SPEED;
                break;
        }

        Coordinate new_pos = new Coordinate(getPosX() + delta_x,
                                            getPosY() + delta_y);

        return checkValid(new_pos, dir);
    }

    /**
     * Checks if the next position it is moving to is valid
     * @param new_pos           Position to check
     * @param dir               Direction it just moved
     * @return boolean          True if the move is valid
     */
    public boolean checkValid(Coordinate new_pos, int dir) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)
            && (this.pos != GameManager.getPlayer().getPos())) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            this.current_dir = dir;
            return true;
        }
        // Otherwise, stop moving
        else {
            this.current_dir = DIR_NONE;
        }
        return false;
    }
}
