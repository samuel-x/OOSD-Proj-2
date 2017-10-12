import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * This class represents one sprite on the screen
 */
public class Sprite {

	protected Image img;
	protected Coordinate pos;
	protected String image_src;

    /**
     * This is the constructor for Sprite.
     * @param image_src     Source of the Image
     * @param pos           Position of the sprite
     * @throws SlickException IF there is no image, throw an exception.
     */
    public Sprite(String image_src, Coordinate pos) throws SlickException {
        try {
            img = new Image(image_src);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        this.image_src = image_src;
        this.pos = pos;
    }

    /**
     * This updates the sprite for one frame.
     */
    public void update() {

    }

    /**
     * This renders the image of the sprite at the sprite's position
     */
    public void render() {
        img.drawCentered(pos.getX(), pos.getY());
    }

    /**
     * This function allows you to compare two sprites.
     * @param other         Other sprite to compare
     * @return boolean of whether the other sprite is equal to this sprite.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Sprite))
            return false;

        Sprite otherSprite = (Sprite) other;

        return ((this.img.equals(otherSprite.img)) &&
                (this.pos.equals(otherSprite.pos)));
    }

    /**
     * Returns the x position of this sprite.
     * @return y position of this sprite as an int
     */
    public int getPosX() { return this.pos.getX(); }

    /**
     * Returns the x position of this sprite.
     * @return y position of this sprite as an int
     */
    public int getPosY() {
        return this.pos.getY();
    }

    /**
     * Returns the Coordinate position of this sprite.
     * @return position of this sprite as a Coordinate object
     */
    public Coordinate getPos() { return this.pos; }

    /**
     * Set the position of this sprite as a Coordinate
     * @param pos Coordinate of sprite
     */
    public void setPos(Coordinate pos) {
        this.pos = pos;
    }

    /**
     * Makes a copy of the sprite
     * @return A copy of this sprite
     * @throws SlickException to stop the compiler from being upset
     */
    public Sprite cloneSprite() throws SlickException {
        // string used for splitting source (punctuation which includes the "/"
        // string used for splitting source (punctuation which includes the "/"
        // and "." in the file path
        final String REGEX = "\\p{Punct}";
        String name = this.image_src.split(REGEX)[1];

        // Load in a sprite of the same class
        return Loader.parseSprite(name, this.pos);
    }
}

