import org.newdawn.slick.SlickException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;


/**
 * This is a static class with functions to load the map from the CSV file
 */
public class Loader {

	/**
	 * Loads the sprites from a given file.
	 * @param filename of level
	 * @return list of sprites
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
	    // Define variables
        ArrayList<Sprite> sprites = new ArrayList<>();
        String[] line;
        String text;

        // Try reading in text from rile
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            // Skip the line of map dimensions
            reader.readLine();

            // While there are still tiles to be added, separate the text into strings and
            // parse the sprites into the list
            while ((text = reader.readLine()) != null) {
                line = text.split(",");
                sprites.add(parseSprite(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sprites;

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
     * @param x coordinate of sprite
     * @param y coordinate of sprite
     * @return Sprite object
     * @throws SlickException
     */
	private static Sprite parseSprite(String name, int x, int y) throws SlickException {
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
        Sprite tile = new Sprite(source, x, y, blocked);
        switch (name) {
            case "wall":
                blocked = true;
                tile = new Wall(source, x, y, blocked);
                break;
            case "stone":
                tile = new Stone(source, x, y, blocked);
                break;
            case "floor":
                tile = new Floor(source, x, y, blocked);
                break;
            case "player":
                tile = new Player(source, x, y, blocked);
                break;
            case "target":
                tile = new Target(source, x, y, blocked);
                break;
        }

        // return the new sprite
        return tile;
    }


}


