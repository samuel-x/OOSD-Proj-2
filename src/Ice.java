public class Ice extends Block implements Timed {

    final int DELAY = 250;
    static long frameHold;
    int current_dir;
    public Ice(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.current_dir = DIR_NONE;
    }

    public void update() {
        if (current_dir != DIR_NONE) {
            if (System.currentTimeMillis() >= frameHold) {
               move(current_dir);
            }
        }
    }

    @Override
    public boolean move(int dir)
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

        return checkValid(new_pos, dir);
    }

    public boolean checkValid(Coordinate new_pos, int dir) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            frameHold = System.currentTimeMillis() + DELAY;
            this.current_dir = dir;
            return true;
        }
        else {
            this.current_dir = DIR_NONE;
        }
        return false;
    }
}
