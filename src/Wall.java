import org.newdawn.slick.SlickException;

/**
 * This class represents one wall on the screen
 */
public class Wall extends Sprite{

    // Acts as a normal sprite for now (except it blocks things!)
    public Wall(String image_src,  Coordinate pos, boolean access) throws SlickException {
        super(image_src, pos, access);

    }



}
