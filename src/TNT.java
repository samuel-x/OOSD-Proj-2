import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.Iterator;

public class TNT extends Block {

    public TNT(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
    }

    public void explode(CrackedWall wall) {
        GameManager.deleteSprite(wall, wall.getPos());
        GameManager.addTile(pos, new Explosion(Explosion.SOURCE, pos));
    }

    private boolean checkCollisionWithCrackedWall(Coordinate pos) {
        CrackedWall wall = null;
        boolean didCollide = false;
        Iterator<Sprite> itr = GameManager.getTilesAtPos(pos).iterator();
        while (itr.hasNext()) {
            Sprite sprite = itr.next();
            if (sprite instanceof CrackedWall) {
                wall = (CrackedWall) sprite;
                didCollide = true;
                explode(wall);
                break;
            }
        }
        return didCollide;
    }

    @Override
    public boolean checkValid(Coordinate new_pos) {
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else if (checkCollisionWithCrackedWall(new_pos)) {
            GameManager.deleteSprite(this, pos);
        }
        return false;
    }
}


