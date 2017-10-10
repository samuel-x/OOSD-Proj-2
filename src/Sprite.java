import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * This class represents one sprite on the screen
 */
public class Sprite {
    public static final int DIR_NONE = 0;
    public static final int DIR_LEFT = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_UP = 3;
    public static final int DIR_DOWN = 4;
	private Image img;
	private Coordinate pos;

	// Make a data constructor
    public Sprite(String image_src, Coordinate pos) {
        try {
            img = new Image(image_src);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        this.pos = pos;
    }

    public void render() {
        img.drawCentered(pos.getX(), pos.getY());
    }

    public void update(Input input, int delta) {

    }

    public boolean moveToDest(int dir) {
        boolean did_move = false;

        int speed = 32;
        // Translate the direction to an x and y displacement
        int delta_x = 0,
                delta_y = 0;
        switch (dir) {
            case DIR_LEFT:
                delta_x = -speed;
                break;
            case DIR_RIGHT:
                delta_x = speed;
                break;
            case DIR_UP:
                delta_y = -speed;
                break;
            case DIR_DOWN:
                delta_y = speed;
                break;
        }

        Coordinate new_pos = new Coordinate(getPosX() + delta_x, getPosY() + delta_y);

        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos)) {
            setPos(new_pos);
            did_move = true;
        }
        return did_move;
    }

    // Getters and setters
    public int getPosX() { return this.pos.getX(); }

    public int getPosY() {
        return this.pos.getY();
    }

    public Coordinate getPos() { return this.pos; }

    public void setPosX(int posX) {
        this.pos.setX(posX);
    }

    public void setPosY(int posY) { this.pos.setY(posY); }

    public void setPos(Coordinate pos) {
        this.pos = pos;
    }
}

