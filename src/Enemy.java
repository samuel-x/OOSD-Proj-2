import org.newdawn.slick.SlickException;

/**
 * This class represents an enemy. If the player comes into contact with an
 * enemy, the level restarts. Enemies can also move.
 */
public abstract class Enemy extends Moveable {

    /** Current direction of the enemy */
    protected int current_dir;

    /**
     * This is the constructor for enemy.
     * @param image_src     Source of the Image
     * @param pos           Position of the enemy
     * @throws SlickException in case there is no image specified
     */
    public Enemy(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
        this.current_dir = DIR_NONE;
    }

    /**
     * This updates the enemy for one frame.
     */
    @Override
    public void update() {
        move(current_dir);
    }
}
