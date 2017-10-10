import org.newdawn.slick.SlickException;

/**
 * This class represents one target on the screen
 */
public class Target extends Sprite {

	// Just acts as a normal sprite for now.
	public Target(String image_src, int x, int y, boolean access) throws SlickException {
        super(image_src, x, y, access);
	}
}
