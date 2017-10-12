import org.newdawn.slick.SlickException;

/**
 *  This class represents a cracked wall in the game world.
 *  A cracked wall is an sprite that can be destroyed by pushing a TNT block
 *  into it.
 */
public class CrackedWall extends Sprite {

    /** Constructor for the cracked wall class
     * @param image_src directory source for the image
     * @param pos       coordinate of the position of this cracked wall
     * @throws SlickException if there is no image specified
     */
    public CrackedWall(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }
}
