import org.newdawn.slick.Input;

public class Mage extends Unit {

    public Mage(String image_src, Coordinate pos) {
        super(image_src, pos);
    }

    @Override
    public void update() {
        int distanceX = getPosX() - GameManager.getPlayer().getPosX();
        int distanceY = getPosY() - GameManager.getPlayer().getPosY();
        System.out.println(distanceX);
        System.out.println(distanceY);
        int new_direction = DIR_NONE;
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            if (distanceX > 0) {
                new_direction = DIR_LEFT;
            } else if (distanceX < 0) {
                new_direction = DIR_RIGHT;
            }
        }
        else {
            if (distanceY > 0) {
                new_direction = DIR_UP;
            }
            else if (distanceY < 0) {
                new_direction = DIR_DOWN;
            }
        }
        move(new_direction);
    }
}
