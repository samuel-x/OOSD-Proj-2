
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * This class represents one player on the screen
 */
public class Player extends Sprite{

	// Currently just inherits everything from Sprite class
	public Player(String image_src, Coordinate pos) throws SlickException {
		super(image_src, pos);
	}
	
    /**
     * Checks for input and moves the player accordingly.
     * @param input of keyboard
     * @return new coordinates
     */
    @Override
    public void update(Input input, int delta) {
        int dir = DIR_NONE;

        if (input.isKeyPressed(Input.KEY_LEFT)) {
            dir = DIR_LEFT;
        }
        else if (input.isKeyPressed(Input.KEY_RIGHT)) {
            dir = DIR_RIGHT;
        }
        else if (input.isKeyPressed(Input.KEY_UP)) {
            dir = DIR_UP;
        }
        else if (input.isKeyPressed(Input.KEY_DOWN)) {
            dir = DIR_DOWN;
        }

        // Move to our destination
        // if it moved, rehash position in hash map
        if (moveToDest(dir)) {
            GameManager.rehashPlayer();
        };

    }




}
