
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import java.util.*;

/**
 * This class represents the entire game world
 */
public class World {
    private final String FILENAME = "res/levels/0.lvl";
    private int worldX;
    private int worldY;
    private ArrayList<Sprite> map = new ArrayList<>();

    public World() {
        // load in map
        this.map = Loader.loadSprites(FILENAME);
        this.worldX = Loader.getDimensions(FILENAME)[0];
        this.worldY = Loader.getDimensions(FILENAME)[1];
	}

    /**
     * Updates the world for the next frame
     * @param input of the keyboard
     * @param delta (for modifying the rate at which the game is updated)
     */
	public void update(Input input, int delta) {

        // Get the player, and then move it around according to input.
        Player player = (Player) map.get(map.size()-1);

        // Get the next move (possible turn based application later?)
        int[] move = player.movePlayer(input);

        // Apply move, and check if it's valid (as in it doesn't go over a wall).
        // If it's valid, set new coordinates for player.
        move[0] += player.getPosX();
        move[1] += player.getPosY();
        if (isValidMove(move[0], move[1])) {
            player.setPosX(move[0]);
            player.setPosY(move[1]);
        }
	}

    /**
     * Renders the frame
     * @param g - graphics object passed from App.java for later use
     */
	public void render(Graphics g) {

        // Iterate over the list of sprites, and render them
        Iterator<Sprite> iterate = this.map.iterator();
        while (iterate.hasNext()) {
            Sprite sprite = iterate.next();

            // Get centered coordinates for the map (so it renders in the middle of the screen)
            int[] coordinates = getCoords(sprite.getPosX(), sprite.getPosY());

            // Draw it!
            sprite.getImg().drawCentered(coordinates[0], coordinates[1]);
        }
	}

    /**
     * Function gets centered coordinates to draw frames.
     * @param x coordinate of tile on x axis
     * @param y coordinate of tile on y axis
     * @return modified coordinates in an array
     */
	public int[] getCoords(int x, int y) {

        // Works by getting the middle point of the screen (origin), and then finding the top left corner of the map
        // relative to the origin, and applying the TILE_SIZE factor.
        int[] coords = {App.SCREEN_WIDTH/2 - (this.worldX*App.TILE_SIZE)/2 + (x*App.TILE_SIZE)
                     , App.SCREEN_HEIGHT/2 - (this.worldY*App.TILE_SIZE)/2 + (y*App.TILE_SIZE)};
        return coords;
    }

    /**
     * Checks if the next move is valid
     * @param x coordinate of tile on x axis
     * @param y coordinate of tile on y axis
     * @return bool of whether it's a valid move or not
     */
    public boolean isValidMove(int x, int y) {
        // Iterate over the map, and if the sprite is moving to the specified coordinate, check if the tile is
        // blocked (as in it is a wall). If so, return false, otherwise return true.
        Iterator<Sprite> iterate = this.map.iterator();
        while (iterate.hasNext()) {
            Sprite sprite = iterate.next();
            if ((sprite.getPosX() == x) && (sprite.getPosY() == y) && (sprite.getBlocked())) {
                return false;
            }
        }
        return true;
    }



}
