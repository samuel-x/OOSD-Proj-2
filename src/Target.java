import org.newdawn.slick.SlickException;

/**
 * This class represents one target on the screen. A target must be covered
 * by a block in order to progress to the next level.
 */
public class Target extends Sprite {

	/**
	 * This is the constructor for a target.
	 * @param image_src     Source of the Image
	 * @param pos           Position of the target
     * @throws SlickException if there is no image specified
	 */
	public Target(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
	}
}
