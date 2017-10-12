import org.newdawn.slick.SlickException;

/**
 * This class represents one floor sprite on the screen
 */
public class Floor extends Sprite {

    /**
     * This is the constructor for a floor tile.
     * @param image_src     Source of the Image
     * @param pos           Position of the floor
     * @throws SlickException if there is no image specified
     */
	public Floor(String image_src, Coordinate pos) throws SlickException {
		super(image_src, pos);
	}

}
