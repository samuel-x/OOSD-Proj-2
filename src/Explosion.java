public class Explosion extends Sprite implements Timed {

    final int DELAY = 400;
    static final String SOURCE = "res/explosion.png";
    private long frameHold;

    public Explosion(String image_src, Coordinate pos) {
        super(image_src, pos);
        frameHold = System.currentTimeMillis() + DELAY;
    }

    @Override
    public void update() {
        if (frameHold <= System.currentTimeMillis()) {
            GameManager.deleteSprite(this, pos);
        }
    }
}
