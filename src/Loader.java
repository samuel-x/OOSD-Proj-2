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
	public static List<List<Sprite>> loadSprites(String filename, String mapType) {
	    // Define variables
        List<List<Sprite>> sprites = new ArrayList<List<Sprite>>();
        String[] line;
        String text;

        // Try reading in text from rile
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            // read in dimensions of map for 2d array
            text = reader.readLine();
            line = text.split(",");
            int dimensionX = Integer.parseInt(line[0]);
            int dimensionY = Integer.parseInt(line[1]);

            Sprite[][] map = new Sprite[dimensionX][dimensionY];
            for (int i = 0; i < dimensionX; i++) {
                for (int j = 0; j < dimensionY; j++) {
                    map[i][j] = new Blank();
                }
            }

            int i = 0;
            int j = 0;
            // While there are still tiles to be added, separate the text into strings and
            // parse the sprites into the list
            while ((text = reader.readLine()) != null) {
                line = text.split(",");
                if (i < dimensionX) {
                    map[i][j] = (parseSprite(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]), mapType));
                    i++;
                }
                else {
                    i = 0;
                    j++;
                }
            }
            sprites = convertMap(map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sprites;

	}

	private static List<List<Sprite>> convertMap(Sprite[][] map) {
	    ArrayList<List<Sprite>> listMap = new ArrayList<List<Sprite>>();
	    for (int i = 0; i < map.length; i++) {
            List<Sprite> row = Arrays.asList(map[i]);
	        listMap.add(row);
        }
        return listMap;
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
	private static Sprite parseSprite(String name, int x, int y, String mapType) throws SlickException {
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
        Sprite tile = new Blank();
        if (mapType.equals("world")) {
            switch (name) {
                case "wall":
                    blocked = true;
                    tile = new Wall(source, new Coordinate(x, y), blocked);
                    break;
                case "floor":
                    tile = new Floor(source, new Coordinate(x, y), blocked);
                    break;
            }
        }
        else if (mapType.equals("block")) {
            switch (name) {
                case "stone":
                    tile = new Stone(source, new Coordinate(x, y), blocked);
                    break;
                case "target":
                    tile = new Target(source, new Coordinate(x, y), blocked);
                    break;
            }
        }
        else if (mapType.equals("character")){
            switch (name) {
                case "player":
                    tile = new Player(source, new Coordinate(x, y), blocked);
                    break;
            }
        }

        // return the new sprite
        return tile;
    }


}


