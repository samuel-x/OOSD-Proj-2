import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {
    private static int currentLevelX;
    private static int currentLevelY;
    private static int currentLevel = 0;
    private static String level_name = "res/levels/" + currentLevel + ".lvl";
    private static World current_world;

    public static void newGame() {
        loadLevel(level_name);
    }

    public static void nextLevel() {
        currentLevel++;
        level_name = "res/levels/" + currentLevel + ".lvl";
        loadLevel(level_name);
    }

    public static void loadLevel(String level_name) {
        current_world = new World(level_name);
    }

    public static void restartLevel() {
        current_world = new World(level_name);
    }

    public static void update(Input input, int delta) {

        // Get the player, and then move it around according to input.
        if (input.isKeyPressed(Input.KEY_R)) {
            nextLevel();
        }
        if (input.isKeyPressed(Input.KEY_Z)) {
            System.out.println("yay");
            current_world = GameStates.undo();
        }
        if (hasInput(input)) {
            movePlayer(input, delta);
        }

    }

    public static void movePlayer(Input input, int delta) {

        Player player = getPlayer(current_world.getMap());
        player.update(input, delta);

    }

    /**
     * Checks if the next move is valid
     * @return bool of whether it's a valid move or not
     */
    public static boolean isValidMove(Coordinate pos) {
        // Iterate over the map, and if the sprite is moving to the specified coordinate, check if the tile is
        // blocked (as in it is a wall). If so, return false, otherwise return true.
        boolean isValid = true;
        for (Sprite sprite : current_world.getMapPos(pos)) {
            if ((sprite instanceof Wall) || (sprite instanceof CrackedWall)) {
                isValid = false;
            } else if (sprite instanceof Door) {
                if (!((Door) sprite).isOpen()) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    public static void rehashTile(Coordinate old_pos, Coordinate new_pos, Sprite sprite) {
        current_world.rehashTile(old_pos, new_pos, sprite);
    }

    public static boolean checkPush(Coordinate pos, int dir) {
        boolean isValid = true;
        Iterator<Sprite> itr = current_world.getMapPos(pos).iterator();
        while (itr.hasNext()) {
            Sprite sprite = itr.next();
            if (sprite instanceof Block) {
                isValid = ((Block) sprite).push(dir);
                break;
            }
        }
        return isValid;
    }



    private static Player getPlayer(HashMap<Coordinate, ArrayList<Sprite>> map) {
        Player player = null;
        // Iterate over the list of sprites, and render them
        for (ArrayList<Sprite> height : map.values()) {
            for (Sprite sprite : height) {
                if (sprite instanceof Player) {
                    player = (Player) sprite;
                }
            }
        }
        return player;
    }

    public static void render(Graphics g) {
        current_world.render(g);
    }

    public static void deleteSprite(Sprite sprite, Coordinate pos) {
        Iterator<Sprite> itr = current_world.getMapPos(pos).iterator();
        while(itr.hasNext()) {
            Sprite check = itr.next();
            if (check.equals(sprite)) {
                itr.remove();
            }
        }
    }

    public static ArrayList<Sprite> getTilesAtPos(Coordinate pos) {
        return current_world.getMapPos(pos);
    }

    private static boolean hasInput(Input input) {
        if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_UP) ||
                input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT)) {
            return true;
        }
        return false;
    }

    public static void recordWorld() {
        World prev_world = cloneWorld();
        GameStates.recordMove(prev_world);
    }

    private static World cloneWorld() {
        World copy = new World(level_name);
        copy.setMap(copy.copyMap(current_world.getMap()));
        return copy;
    }
}

