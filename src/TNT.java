import org.newdawn.slick.SlickException;

import java.util.Iterator;
/**
 * This class represents a TNT block on the screen. A TNT can be pushed into
 * a cracked wall to blow it up. (Yay!)
 */
public class TNT extends Block {
    /**
     * This is the constructor for a TNT.
     * @param image_src     Source of the Image
     * @param pos           Position of the TNT
     * @throws SlickException if there is no image specified
     */
    public TNT(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }

    /**
     * Explodes the cracked wall that the TNT is pushed into
     * @param wall          Cracked Wall to blow up
     * @param explodeAt     Position to make the explosion
     * @throws SlickException compiler pls
     */
    private void explode(CrackedWall wall, Coordinate explodeAt)
            throws SlickException {
        // Delete the cracked wall, and make a new Explosion sprite
        GameManager.deleteSprite(wall, wall.getPos());
        GameManager.addTile(explodeAt, new Explosion(explodeAt));
    }

    /**
     * Checks if the TNT has collided with a cracked wall, in which case,
     * fun times are to be had. (i.e. The cracked wall explodes).
     * @param pos           Position to check.
     * @return boolean of whether the TNT did explode a wall
     * @throws SlickException if there is no image specified
     */
    private boolean checkCollisionWithCrackedWall(Coordinate pos)
            throws SlickException {
        CrackedWall wall;
        boolean didCollide = false;

        // Iterate through the specified position and check if there's a cracked
        // wall in it.
        for (Sprite sprite : GameManager.getTilesAtPos(pos)) {
            if (sprite instanceof CrackedWall) {
                // If so, explode it.
                wall = (CrackedWall) sprite;
                didCollide = true;
                explode(wall, pos);
                break;
            }
        }
        return didCollide;
    }

    /**
     * Checks if the move was valid
     * @param new_pos       Coordinate to check
     * @return boolean of whether the move was successful.
     */
    @Override
    public boolean checkValid(Coordinate new_pos) {
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)){
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else
        try {
            if (checkCollisionWithCrackedWall(new_pos)) {
                // Delete the TNT after the explosion.
                GameManager.deleteSprite(this, pos);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return false;
    }
}


