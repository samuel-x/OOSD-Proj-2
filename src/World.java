
import org.newdawn.slick.SlickException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents the entire game world
 */
public class World {
    private int worldX;
    private int worldY;
    private HashMap<Coordinate, ArrayList<Sprite>> map = new HashMap<>();

    public World(String level) {
        // load in map
        this.map = Loader.loadSprites(level);

        // Get the dimensions for this map
        int[] dimensions = Loader.getDimensions(level);
        this.worldX = dimensions[0];
        this.worldY = dimensions[1];
	}

    /**
     * Updates the world for the next frame
     */
	public void update() {
	    // Make a list of sprites to update (as you can't modify a list you
        // are iterating through)
        ArrayList<Sprite> sprites_to_update = new ArrayList<Sprite>();

        // Iterate through tiles and add them to this list (don't update player)
        for (ArrayList<Sprite> tile : map.values()) {
            for (Sprite sprite : tile) {
                if (sprite instanceof Player) {
                    continue;
                }
                sprites_to_update.add(sprite);
            }
        }

        // Iterate through new list and update everything
        Iterator<Sprite> itr = sprites_to_update.iterator();
        while(itr.hasNext()) {
            itr.next().update();
        }
	}


    /**
     * Renders the frame
     */
	public void render() {
        for (ArrayList<Sprite> tile : map.values()) {
            for (Sprite sprite : tile) {
                // Get centered coordinates for the map (so it renders in the
                // middle of the screen)
                sprite.render();
            }
        }
    }

    /**
     * Gets the map of the world
     * @return The map
     */
    public HashMap<Coordinate, ArrayList<Sprite>> getMap() {
        return map;
    }

    /**
     * Gets the arraylist of sprite
     * @param pos               The position of tile to get.
     * @return The list of sprites on the tile.
     */
    public ArrayList<Sprite> getMapPos(Coordinate pos) {
        return map.get(pos);
    }

    /**
     * Add a sprite to the specified tile in the world
     * @param pos               The position to add to
     * @param sprite            The sprite to add
     * @param map               The map to add to
     */
    public void addToTile(Coordinate pos, Sprite sprite, HashMap<Coordinate,
            ArrayList<Sprite>> map) {
        map.get(pos).add(sprite);
    }

    /**
     * Rehashes a tile in the HashMap
     * @param old_pos           The previous (current) position of the Sprite
     * @param new_pos           The new position the Sprite will be hashed to
     * @param sprite            The Sprite to hash
     * @param map               The map
     */
    public void rehashTile(Coordinate old_pos, Coordinate new_pos,
                           Sprite sprite, HashMap<Coordinate,
                            ArrayList<Sprite>> map) {
        // Iterate through the tile
	    Iterator<Sprite> itr = map.get(old_pos).iterator();
	    while(itr.hasNext()) {
	        Sprite check = itr.next();
	        // If the sprite is the (exact) same as the one specified, remove it
	        if (check.equals(sprite)) {
	            itr.remove();
	            break;
            }
        }
        // Add the new sprite
        map.get(new_pos).add(sprite);
    }

    /**
     * This returns a copy of the current map
     * @param map               The map to copy
     * @return                  The map as a copy
     * @throws SlickException to stop the compiler from being mad
     */
    public HashMap<Coordinate, ArrayList<Sprite>>
    copyMap(HashMap<Coordinate, ArrayList<Sprite>> map) throws SlickException {
        // Iterate through all the keys and values of the current hash map
        HashMap<Coordinate, ArrayList<Sprite>> new_map = new
                HashMap<Coordinate, ArrayList<Sprite>>();
        for (HashMap.Entry<Coordinate, ArrayList<Sprite>> tile : map.entrySet()) {

            // Make new values for Coordinate and the Sprites in ArrayList
            Coordinate new_coord = tile.getKey().clone();
            ArrayList<Sprite> new_tile = new ArrayList<>();
            for (Sprite sprite : tile.getValue()) {
                Sprite new_sprite = sprite.cloneSprite();
                new_tile.add(new_sprite);
            }

            // Rehash the ArrayList into the new map
            new_map.put(new_coord, new_tile);
        }

        return new_map;
    }

    /**
     * Sets the current map to the specified map
     * @param map               The map to set the current world map to
     */
    public void setMap(HashMap<Coordinate, ArrayList<Sprite>> map) {
        this.map = map;
    }

    /**
     * Get the width of the world
     * @return Returns the width of the world in tiles as an int
     */
    public int getWorldX() {
        return worldX;
    }


    /**
     * Get the height of the world
     * @return Returns the height of the world in tiles as an int
     */
    public int getWorldY() {
        return worldY;
    }
}
