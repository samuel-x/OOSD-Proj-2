
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

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
     */
	public void update() {
        ArrayList<Sprite> sprites_to_update = new ArrayList<Sprite>();
        for (ArrayList<Sprite> tile : map.values()) {
            for (Sprite sprite : tile) {
                if (sprite instanceof Player) {
                    continue;
                }
                sprites_to_update.add(sprite);
            }
        }

        Iterator<Sprite> itr = sprites_to_update.iterator();
        while(itr.hasNext()) {
            itr.next().update();
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

    public void addTile(Coordinate pos, Sprite sprite, HashMap<Coordinate, ArrayList<Sprite>> map) {
        map.get(pos).add(sprite);
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

    public HashMap<Coordinate, ArrayList<Sprite>> copyMap(HashMap<Coordinate, ArrayList<Sprite>> map) throws SlickException {
        HashMap<Coordinate, ArrayList<Sprite>> new_map = new HashMap<Coordinate, ArrayList<Sprite>>();
        for (HashMap.Entry<Coordinate, ArrayList<Sprite>> tile : map.entrySet()) {
            Coordinate new_coord = tile.getKey().clone();
            ArrayList<Sprite> new_tile = new ArrayList<>();
            for (Sprite sprite : tile.getValue()) {
                Sprite new_sprite = sprite.cloneSprite();
                new_tile.add(new_sprite);
            }
            new_map.put(new_coord, new_tile);
        }

        return new_map;
    }

    public void setMap(HashMap<Coordinate, ArrayList<Sprite>> map) {
        this.map = map;
    }
}
