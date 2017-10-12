import org.newdawn.slick.Input;

public class Rogue extends Unit {

    public Rogue(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.current_dir = DIR_LEFT;
    }

    @Override
    public void update() {
        move(current_dir);
    }

    @Override
    public boolean checkValid(Coordinate new_pos) {
        // Make sure the position isn't occupied!
        if (GameManager.isValidMove(new_pos) && GameManager.tryPush(new_pos, current_dir)) {
            GameManager.rehashTile(getPos(), new_pos, this);
            setPos(new_pos);
            return true;
        }
        else {
            if (current_dir == DIR_LEFT) {
                current_dir = DIR_RIGHT;
            } else {
                current_dir = DIR_LEFT;
            }
        }
        return false;
    }
}
