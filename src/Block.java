public abstract class Block extends Sprite implements Pushable {
    private boolean onTarget;

    Block(String image_src, Coordinate pos) {
        super(image_src, pos);
    }

}
