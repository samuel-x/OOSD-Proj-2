
public class Door extends Sprite {

    public boolean isOpen = false;

    public Door(String image_src, Coordinate pos) {
        super(image_src, pos);
    }

    @Override
    public void render() {
        if (isOpen) {
            img.drawCentered(getPosX(), getPosY());
        }
    }

    public boolean isOpen() {
        return isOpen;
    }
}
