public class Switch extends Sprite {

    public Switch(String image_src, Coordinate pos) {
        super(image_src, pos);
    }

    @Override
    public void update() {
        GameManager.switchDoor(checkCovered());

    }

    public boolean checkCovered() {
        for (Sprite sprite : GameManager.getTilesAtPos(pos)) {
            if (sprite instanceof Block) {
                return true;
            }
        }
        return false;
    }

}
