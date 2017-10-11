
public class Door extends Sprite {

    public boolean isOpen;

    public Door(String image_src, Coordinate pos) {
        super(image_src, pos);
        this.isOpen = false;
    }

    public void update() {
    }

    @Override
    public void render() {
        if (!isOpen) {
            img.drawCentered(getPosX(), getPosY());
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
