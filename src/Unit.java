public abstract class Unit extends Moveable {

    public int current_dir;

    public Unit(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.current_dir = DIR_NONE;
    }

    @Override
    public void update() {
        move(current_dir);
    }
}
