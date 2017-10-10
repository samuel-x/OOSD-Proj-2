import org.newdawn.slick.SlickException;

/**
 * This class represents one floor sprite on the screen
 */
public class Floor extends Sprite {

    // Acts as a normal sprite (because it's a floor and floors are made for walking on)
	public Floor(String image_src, Coordinate pos, boolean access) throws SlickException {
		super(image_src, pos, access);
	}

}
