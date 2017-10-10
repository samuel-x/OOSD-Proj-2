import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This class represents one sprite on the screen
 */
public class Sprite {
	private Image img;
	private int posX;
	private int posY;
	private boolean isBlocked;

	// Make a data constructor
    public Sprite(String image_src, int x, int y, boolean access) throws SlickException {
        this.img = new Image(image_src);
        this.posX = x;
        this.posY = y;
        this.isBlocked = access;
    }

    // Getters and setters
    public int getPosX() { return posX; }

    public int getPosY() {
        return posY;
    }

    public Image getImg() {
        return img;
    }

    public boolean getBlocked() { return isBlocked; }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}

