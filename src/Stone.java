import org.newdawn.slick.SlickException;

/**
 * This class represents one stone on the screen
 */
public class Stone extends Block {

	// Acts as a normal sprite for now.
	public Stone(String image_src, Coordinate pos) throws SlickException {
		super(image_src, pos);
	}


	@Override
	public void push(int dir) {

	}
}
