import org.newdawn.slick.SlickException;

/**
 * This class represents an explosion.
 * Explosions happen when a TNT is pushed into a cracked wall.
 *
 * (TO DO: make a button to draw an explosion on screen because who doesn't
 * love explosions???)
 */
public class Explosion extends Sprite implements Timed {

    // define constants
    // this holds the duration of the explosion
    private final int DELAY = 400;
    // the source image
    private static final String SOURCE = "res/explosion.png";
    private long frameHold;

    /** Constructor for the explosion class
     * @param pos       coordinate of the position of this explosion
     * @throws SlickException in case there is no image specified
     */
    public Explosion(Coordinate pos) throws SlickException {
        super(SOURCE, pos);
        frameHold = System.currentTimeMillis() + DELAY;
    }

    /**
     * This is the update() function for the explosion.
     * After 0.4 seconds, the explosion will be deleted.
     */
    @Override
    public void update() {
        if (frameHold <= System.currentTimeMillis()) {
            GameManager.deleteSprite(this, pos);
        }
    }
}
