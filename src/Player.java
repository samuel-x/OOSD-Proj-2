
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * This class represents one player on the screen
 */
public class Player extends Moveable {

    public int current_dir;


	// Currently just inherits everything from Sprite class
	public Player(String image_src, Coordinate pos) throws SlickException {
		super(image_src, pos);
		this.current_dir = DIR_NONE;
	}
	
    /**
     * Checks for input and moves the player accordingly.
     * @param input of keyboard
     * @return new coordinates
     */
    public void update(Input input, int delta) {
        int dir = DIR_NONE;

        if (input.isKeyDown(Input.KEY_LEFT)) {
            dir = DIR_LEFT;
        }
        else if (input.isKeyDown(Input.KEY_RIGHT)) {
            dir = DIR_RIGHT;
        }
        else if (input.isKeyDown(Input.KEY_UP)) {
            dir = DIR_UP;
        }
        else if (input.isKeyDown(Input.KEY_DOWN)) {
            dir = DIR_DOWN;
        }

        // Move to our destination
        // if it moved, rehash position in hash map
        current_dir = dir;
        move(current_dir);
    }


    @Override
    public boolean checkValid(Coordinate new_pos) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) && GameManager.tryPush(new_pos, current_dir)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        return false;
    }
}
