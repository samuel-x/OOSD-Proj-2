
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.lang.reflect.Array;
import java.util.*;

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
	}

    /**
     * Updates the world for the next frame
     * @param input of the keyboard
     * @param delta (for modifying the rate at which the game is updated)
     */
	public void update() {
        HashMap<Coordinate, ArrayList<Sprite>> updated_map = copyMap(map);
        for (ArrayList<Sprite> tile : map.values()) {
            for (Sprite sprite : tile) {
                sprite.update();
            }
        }
	}


    /**
     * Renders the frame
     * @param g - graphics object passed from App.java for later use
     */
	public void render(Graphics g) {

        for (ArrayList<Sprite> tile : map.values()) {
            for (Sprite sprite : tile) {
                // Get centered coordinates for the map (so it renders in the middle of the screen)
                sprite.render();

            }
        }
    }

    public HashMap<Coordinate, ArrayList<Sprite>> getMap() {
        return map;
    }

    public ArrayList<Sprite> getMapPos(Coordinate pos) {
        return map.get(pos);
    }

    public void rehashTile(Coordinate old_pos, Coordinate new_pos, Sprite sprite, HashMap<Coordinate, ArrayList<Sprite>> map) {
	    Iterator<Sprite> itr = map.get(old_pos).iterator();
	    while(itr.hasNext()) {
	        Sprite check = itr.next();
	        if (check.equals(sprite)) {
	            itr.remove();
	            break;
            }
        }
        map.get(new_pos).add(sprite);
    }

    public HashMap<Coordinate, ArrayList<Sprite>> copyMap(HashMap<Coordinate, ArrayList<Sprite>> map) {
        HashMap<Coordinate, ArrayList<Sprite>> new_map = new HashMap<Coordinate, ArrayList<Sprite>>();
        for (Map.Entry<Coordinate, ArrayList<Sprite>> tile : map.entrySet()) {
            new_map.put(tile.getKey(), tile.getValue());
        }
        return new_map;
    }

    public void setMap(HashMap<Coordinate, ArrayList<Sprite>> map) {
        this.map = map;
    }
}
