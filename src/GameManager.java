import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {
    private static int currentLevelX;
    private static int currentLevelY;
    private static int currentLevel = 0;
    private static String level_name = "res/levels/" + currentLevel + ".lvl";
    private static World current_world;
    private static GameStates game;

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
        game = new GameStates();
    }

    public static void restartLevel() {
        loadLevel(level_name);
    }

    public static void print_level() {
        for (ArrayList<Sprite> tile : current_world.getMap().values()) {
            for (Sprite sprite : tile) {
                System.out.print(sprite);
            }
        }
    }

    public static void update(Input input, int delta) throws SlickException {

        // Get the player, and then move it around according to input.
        if (input.isKeyPressed(Input.KEY_R)) {
            restartLevel();
        }
        else if (input.isKeyPressed(Input.KEY_L)) {
            nextLevel();
        }
        else if (input.isKeyPressed(Input.KEY_Z)) {
            if (!game.check()) {
                World prev_world = game.undo();
                current_world.setMap(prev_world.getMap());
                System.out.println(current_world);
                System.out.println("player is " + getPlayer());
            }
        }
        else if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_RIGHT)) {
            Player player = getPlayer();
            player.update(input, delta);
            checkGameOver();
            current_world.update();
            checkGameWin();
            recordWorld();
        }
        Ice ice = getIce();
        if (ice != null) {
            ice.update();
        }

    }

    public static void checkGameOver() {
        for (ArrayList<Sprite> tile : current_world.getMap().values()) {
            boolean hasEnemy = false;
            boolean hasPlayer = false;
            for (Sprite sprite : tile) {
                if (sprite instanceof Unit) {
                    hasEnemy = true;
                }
                if (sprite instanceof Player) {
                    hasPlayer = true;
                }
                if (hasEnemy && hasPlayer) {
                    restartLevel();
                }
            }
        }
    }

    public static void checkGameWin() {
        int targetsCovered = 0;
        for (ArrayList<Sprite> tile : current_world.getMap().values()) {
            boolean hasTarget = false;
            boolean hasBlock = false;
            for (Sprite sprite : tile) {
                if (sprite instanceof Target) {
                    hasTarget = true;
                }
                if (sprite instanceof Block) {
                    hasBlock = true;
                }
                if (hasTarget && hasBlock) {
                    targetsCovered++;
                }
            }
        }
        if (targetsCovered == countTargets()) {
            nextLevel();
        }
    }

    private static int countTargets() {
        int targets = 0;
        for (ArrayList<Sprite> tile : current_world.getMap().values()) {
            for (Sprite sprite : tile) {
                if (sprite instanceof Target) {
                    targets++;
                }
            }
        }
        return targets;
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
        current_world.rehashTile(old_pos, new_pos, sprite, current_world.getMap());
    }

    public static boolean checkPush(Coordinate pos, int dir) {
        boolean isValid = true;
        Iterator<Sprite> itr = current_world.getMapPos(pos).iterator();
        while (itr.hasNext()) {
            Sprite sprite = itr.next();
            if (sprite instanceof Block) {
                if (sprite instanceof Ice) {
                    isValid = ((Ice) sprite).update(dir);
                    break;
                }
                else {
                    isValid = ((Block) sprite).push(dir);
                    break;
                }
            }
        }
        return isValid;
    }

    public static boolean checkPush(Coordinate pos) {
        boolean isValid = true;
        Iterator<Sprite> itr = current_world.getMapPos(pos).iterator();
        while (itr.hasNext()) {
            Sprite sprite = itr.next();
            if (sprite instanceof Block) {
                isValid = false;
            }
        }
        return isValid;
    }

    public static void switchDoor(boolean isOpen) {
        for (ArrayList<Sprite> tile : current_world.getMap().values()) {
            for (Sprite sprite : tile) {
                if (sprite instanceof Door) {
                    Door door = (Door) sprite;
                    door.setOpen(isOpen);
                }
            }
        }
    }


    private static Player getPlayer() {
        Player player = null;
        // Iterate over the list of sprites, and render them
        for (ArrayList<Sprite> height : current_world.getMap().values()) {
            for (Sprite sprite : height) {
                if (sprite instanceof Player) {
                    player = (Player) sprite;
                }
            }
        }
        return player;
    }

    private static Ice getIce() {
        Ice ice = null;
        // Iterate over the list of sprites, and render them
        for (ArrayList<Sprite> height : current_world.getMap().values()) {
            for (Sprite sprite : height) {
                if (sprite instanceof Ice) {
                    ice = (Ice) sprite;
                }
            }
        }
        return ice;
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

    public static void recordWorld() throws SlickException{
        World prev_world = new World(level_name);
        prev_world.setMap(current_world.copyMap(current_world.getMap()));
        game.recordMove(prev_world);
    }

}

