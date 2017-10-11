public class Ice extends Block {

    final int DELAY = 250;
    static long frameHold;
    int previous_dir;
    public Ice(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.previous_dir = DIR_NONE;
    }

    public void update() {
        if (previous_dir != DIR_NONE) {
            update(previous_dir);
        }
    }

    public boolean update(int dir) {
        boolean didMove = true;
        if (dir != DIR_NONE) {
            if (System.currentTimeMillis() >= frameHold) {
                didMove = push(dir);
            }
        }
        return didMove;
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
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            did_move = true;
            frameHold = System.currentTimeMillis() + DELAY;
            this.previous_dir = dir;
        }
        else if (!GameManager.checkPush(new_pos)){
            this.previous_dir = DIR_NONE;
        }
        else {
            this.previous_dir = DIR_NONE;
        }

        return did_move;
    }
}
