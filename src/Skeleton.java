import org.newdawn.slick.Input;

public class Skeleton extends Unit {
    public Skeleton(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.current_dir = DIR_UP;
    }

    @Override
    public void update(Input input, int delta) {
        if(!move(current_dir)) {
            if (current_dir == DIR_UP) {
                current_dir = DIR_DOWN;
            } else {
                current_dir = DIR_UP;
            }
        }
    }
}
