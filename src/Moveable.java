public abstract class Moveable extends Sprite implements Direction {

    public Moveable(String image_src, Coordinate pos) {
        super(image_src, pos);
    }

    public boolean move(int dir) {
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
        return checkValid(new_pos);
    }

    public boolean checkValid(Coordinate new_pos) {
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else {
            return false;
        }
    }

}
