import org.newdawn.slick.Input;

public class Skeleton extends Unit implements Timed {

    final int DELAY = 1000;
    static long frameHold;

    public Skeleton(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.current_dir = DIR_UP;
    }

    @Override
    public void update() {
        if (current_dir != DIR_NONE && (System.currentTimeMillis() >= frameHold)) {
            move(current_dir);
        }
    }

    @Override
    public boolean checkValid(Coordinate new_pos) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) && GameManager.checkPush(new_pos)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            frameHold = System.currentTimeMillis() + DELAY;
            return true;
        }
        else {
            if (this.current_dir == DIR_UP) {
                this.current_dir = DIR_DOWN;
            } else {
                this.current_dir = DIR_UP;
            }
        }
        return false;
    }
}
