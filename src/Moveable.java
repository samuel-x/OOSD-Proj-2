import org.newdawn.slick.SlickException;

/**
 * This class represents moveable sprite in the world.
 */
public abstract class Moveable extends Sprite implements Direction {

    /**
     * The speed at which sprites move (one tile)
     */
    protected static int SPEED = 32;

    /**
     * This is the constructor for a moveable sprite.
     * @param image_src     Source of the Image
     * @param pos           Position of the sprite
     * @throws SlickException throws this
     */
    public Moveable(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }

    /**
     * This moves the sprite in the world
     * @param dir       Direction to move
     * @return boolean of whether the move was successful
     */
    public boolean move(int dir) {
        // Translate the direction to an x and y displacement
        int delta_x = 0,
                delta_y = 0;
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
        return checkValid(new_pos);
    }

    /**
     * Checks if the move was valid
     * @param new_pos       Coordinate to check
     * @return boolean of whether the move is valid.
     */
    protected boolean checkValid(Coordinate new_pos) {
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else {
            return false;
        }
    }

}
