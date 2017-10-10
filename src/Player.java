
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * This class represents one player on the screen
 */
public class Player extends Sprite{

	// Currently just inherits everything from Sprite class
	public Player(String image_src, int x, int y, boolean access) throws SlickException {
		super(image_src, x, y, access);
	}
	
    /**
     * Checks for input and moves the player accordingly.
     * @param input of keyboard
     * @return new coordinates
     */
    public int[] movePlayer(Input input) {
        int moveX = 0;
        int moveY = 0;
        if (input.isKeyPressed(Input.KEY_UP)) {
            moveY--;
        }
        if (input.isKeyPressed(Input.KEY_DOWN)) {
            moveY++;
        }
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            moveX--;
        }
        if (input.isKeyPressed(Input.KEY_RIGHT)) {
            moveX++;
        }
        int[] newPos = {moveX, moveY};
        return newPos;
    }


}
