public class Ice extends Block {

    public Ice(String image_src, Coordinate pos) {
        super(image_src, pos);
    }

    @Override
    public boolean push(int dir)
    {
        boolean did_move = false;

        int speed = 8;
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
            push(dir);
        }

        return did_move;
    }
}
