import org.newdawn.slick.SlickException;

/**
 * This class represents a mage in the world. A mage follows the player around
 * the map. If the player comes into contact with the mage, the level restarts.
 * Scary.
 */
public class Mage extends Enemy {

    /**
     * This is the constructor for mage.
     * @param image_src     Source of the Image
     * @param pos           Position of the mage
     * @throws SlickException if there is no image specified
     */
    public Mage(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }

    /**
     * This updates the mage for one frame.
     */
    @Override
    public void update() {
        // The mage moves according to the algorithm specified in the spec

        // First get the distance to the player
        int distanceX = getPosX() - GameManager.getPlayer().getPosX();
        int distanceY = getPosY() - GameManager.getPlayer().getPosY();

        // Define a new direction to move
        int new_direction = DIR_NONE;

        // If the distance to the player in the horizontal plane is further,
        // then move closer to the player, otherwise move vertical towards the
        // player
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            if (distanceX > 0) {
                new_direction = DIR_LEFT;
            } else if (distanceX < 0) {
                new_direction = DIR_RIGHT;
            }
        }
        else {
            if (distanceY > 0) {
                new_direction = DIR_UP;
            }
            else if (distanceY < 0) {
                new_direction = DIR_DOWN;
            }
        }
        move(new_direction);
    }
}
