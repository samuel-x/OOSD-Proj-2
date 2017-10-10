import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * This class represents one sprite on the screen
 */
public class Sprite {

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

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Sprite))
            return false;

        Sprite otherSprite = (Sprite) other;

        return ((this.img == otherSprite.img) && (this.pos.equals(otherSprite.pos)));
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

