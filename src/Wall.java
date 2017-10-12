import org.newdawn.slick.SlickException;

/**
 * This class represents one wall on the screen. You can't walk through a wall.
 * Believe me, I've tried.
 */
public class Wall extends Sprite{

    /**
     * This is the constructor for a wall.
     * @param image_src     Source of the Image
     * @param pos           Position of the wall
     * @throws SlickException if there is no image specified
     */
    public Wall(String image_src,  Coordinate pos) throws SlickException {
        super(image_src, pos);
    }
}
