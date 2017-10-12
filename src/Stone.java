import org.newdawn.slick.SlickException;

/**
 * This class represents one stone on the screen
 */
public class Stone extends Block {

    /**
     * This is the constructor for stone.
     * @param image_src     Source of the Image
     * @param pos           Position of the stone
     * @throws SlickException if there is no image specified
     */
	public Stone(String image_src, Coordinate pos) throws SlickException {
		super(image_src, pos);
	}

}
