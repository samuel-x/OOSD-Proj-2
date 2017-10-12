
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * This class represents one player on the screen.
 */
public class Player extends Moveable {

    private int current_dir;

    /**
     * This is the constructor for a switch.
     * @param image_src     Source of the Image
     * @param pos           Position of the switch
     * @throws SlickException if there is no image specified
     */
	public Player(String image_src, Coordinate pos) throws SlickException {
		super(image_src, pos);
		this.current_dir = DIR_NONE;
	}
	
    /**
     * Checks for input and moves the player accordingly.
     * @param input of keyboard
     */
    public void update(Input input) {
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

    /**
     * Checks if the move was valid
     * @param new_pos       Coordinate to check
     * @return boolean of whether the move is valid.
     */
    @Override
    protected boolean checkValid(Coordinate new_pos) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) &&
                GameManager.tryPush(new_pos, current_dir)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        return false;
    }
}
