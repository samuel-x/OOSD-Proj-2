import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.Iterator;

public class TNT extends Block {

    private static Image explosion = null;

    public TNT(String image_src, Coordinate pos) throws SlickException {
        super(image_src, pos);
        this.explosion = new Image("res/explosion.png");
    }

    @Override
    public boolean push(int dir)
    {
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
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos, dir)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            did_move = true;
        }
        else if(checkCollisionWithCrackedWall(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            GameManager.deleteSprite(this, new_pos);

        }

        return did_move;
    }

    public void explode(CrackedWall wall) {
        GameManager.deleteSprite(wall, wall.getPos());
        explosion.drawCentered(getPosX(), getPosY());
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
                break;
            }
        }
        explode(wall);
        return didCollide;
    }
}
