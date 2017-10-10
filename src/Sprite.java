import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This class represents one sprite on the screen
 */
public class Sprite {
	private Image img;
	private Coordinate pos;
	private boolean isBlocked;

	// Make a data constructor
    public Sprite(String image_src, Coordinate pos, boolean access) throws SlickException {
        if (image_src != null) {
            this.img = new Image(image_src);
        }
        this.pos = pos;
        this.isBlocked = access;
    }

    // Getters and setters
    public int getPosX() { return this.pos.getX(); }

    public int getPosY() {
        return this.pos.getY();
    }

    public Image getImg() {
        return img;
    }

    public boolean getBlocked() { return isBlocked; }

    public void setPosX(int posX) {
        this.pos.setX(posX);
    }

    public void setPosY(int posY) { this.pos.setY(posY); }
}

