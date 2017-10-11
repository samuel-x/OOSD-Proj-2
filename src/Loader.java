import org.newdawn.slick.SlickException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.*;


/**
 * This is a static class with functions to load the map from the CSV file
 */
public class Loader {

	/**
	 * Loads the sprites from a given file.
	 * @param filename of level
	 * @return list of sprites
	 */
	public static HashMap<Coordinate, ArrayList<Sprite>> loadSprites(String filename) {
	    // Define variables
        HashMap<Coordinate, ArrayList<Sprite>> map = new HashMap<>();
        String[] line;
        String text;

        // Try reading in text from rile
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            // read in dimensions of map for 2d array
            text = reader.readLine();
            line = text.split(",");
            int worldX = Integer.parseInt(line[0]);
            int worldY = Integer.parseInt(line[1]);

            int i = 0;
            int j = 0;
            // While there are still tiles to be added, separate the text into strings and
            // parse the sprites into the list
            while ((text = reader.readLine()) != null) {
                line = text.split(",");
                Coordinate pos = snapToGrid(Integer.parseInt(line[1]), Integer.parseInt(line[2]), worldX, worldY);
                if (map.get(pos) != null) {
                    map.get(pos).add(parseSprite(line[0], pos));
                }
                else {
                    ArrayList<Sprite> sprite = new ArrayList<>();
                    sprite.add(parseSprite(line[0], pos));
                    map.put(pos, sprite);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;

	}

    /**
     * Function gets dimensions of the map.
     * @param filename of map
     * @return dimensions of map in an array
     */
	public static int[] getDimensions(String filename) {
        int[] dimensions = new int[2];
        String text;

        // Try reading in file
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // get the dimensions of map
            if ((text = reader.readLine()) != null) {
                dimensions[0] = Integer.parseInt(text.split(",")[0]);
                dimensions[1] = Integer.parseInt(text.split(",")[1]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // return an array holding x and y dimensions
        return dimensions;

    }

    /**
     * Function gets centered coordinates to draw frames.
     * @param x coordinate of tile on x axis
     * @param y coordinate of tile on y axis
     * @return modified coordinates in an array
     */
    public static Coordinate snapToGrid(int x, int y, int worldX, int worldY) {

        // Works by getting the middle point of the screen (origin), and then finding the top left corner of the map
        // relative to the origin, and applying the TILE_SIZE factor.
        Coordinate coords = new Coordinate(App.SCREEN_WIDTH/2 - (worldX*App.TILE_SIZE)/2 + (x*App.TILE_SIZE)
                                         , App.SCREEN_HEIGHT/2 - (worldY*App.TILE_SIZE)/2 + (y*App.TILE_SIZE));
        return coords;
    }

    /**
     * Parses a string to a sprite object
     * @param name of object
     * @return Sprite object
     * @throws SlickException
     */
	public static Sprite parseSprite(String name, Coordinate pos) throws SlickException {
        // First make name lower case (all resource tile files should have lower case names!)
        name = name.toLowerCase();
        String source;

        // If it's the player, it's player_left.png, otherwise make the path as specified
        if (name.equals("player")) {
            source = "res/player_left.png";
        } else if (name.equals("skeleton")) {
            source = "res/skull.png";
        } else if (name.equals("cracked")) {
            source = "res/cracked_wall.png";
        }
        else {
            source = "res/" + name + ".png";
        }

        // parse name of sprite to actual sprite object, if it's a wall, it has the blocked property set to true
        // (as you shouldn't be able to walk through walls)
        Sprite tile = new Sprite(source, pos);
        switch (name) {
            case "wall":
                tile = new Wall(source, pos);
                break;
            case "floor":
                tile = new Floor(source, pos);
                break;
            case "stone":
                tile = new Stone(source, pos);
                break;
            case "target":
                tile = new Target(source, pos);
                break;
            case "player":
                tile = new Player(source, pos);
                break;
            case "mage":
                tile = new Mage(source, pos);
                break;
            case "rogue":
                tile = new Rogue(source, pos);
                break;
            case "skeleton":
                tile = new Skeleton(source, pos);
                break;
            case "switch":
                tile = new Switch(source, pos);
                break;
            case "tnt":
                tile = new TNT(source, pos);
                break;
            case "ice":
                tile = new Ice(source, pos);
                break;
            case "door":
                tile = new Door(source, pos);
                break;
            case "cracked":
                tile = new CrackedWall(source, pos);
                break;
        }


        // return the new sprite
        return tile;
    }

}


