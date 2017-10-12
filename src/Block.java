import org.newdawn.slick.SlickException;

/**
 *  This class represents a block in the game world.
 *  A block is an object that can be pushed by the to cover targets.
 */
public abstract class Block extends Moveable {

    /**
     * This is the constructor for a block.
     * @param image_src     Source of the Image
     * @param pos           Position of the block
     * @throws SlickException if there is no image specified
     */
    Block(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }

}

