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

            int i = 0;
            int j = 0;
            // While there are still tiles to be added, separate the text into strings and
            // parse the sprites into the list
            while ((text = reader.readLine()) != null) {
                line = text.split(",");
                Coordinate pos = new Coordinate(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
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
     * Parses a string to a sprite object
     * @param name of object
     * @param x of sprite
     * @return Sprite object
     * @throws SlickException
     */
	private static Sprite parseSprite(String name, Coordinate pos) throws SlickException {
        // First make name lower case (all resource tile files should have lower case names!)
        name = name.toLowerCase();
        String source;
        boolean blocked = false;

        // If it's the player, it's player_left.png, otherwise make the path as specified
        if (name.equals("player")) {
            source = "res/player_left.png";
        } else {
            source = "res/" + name + ".png";
        }

        // parse name of sprite to actual sprite object, if it's a wall, it has the blocked property set to true
        // (as you shouldn't be able to walk through walls)
        Sprite tile = new Sprite(source, pos, blocked);
        switch (name) {
            case "wall":
                blocked = true;
                tile = new Wall(source, pos, blocked);
                break;
            case "floor":
                tile = new Floor(source, pos, blocked);
                break;
            case "stone":
                tile = new Stone(source, pos, blocked);
                break;
            case "target":
                tile = new Target(source, pos, blocked);
                break;
            case "player":
                tile = new Player(source, pos, blocked);
                break;
        }


        // return the new sprite
        return tile;
    }


}


