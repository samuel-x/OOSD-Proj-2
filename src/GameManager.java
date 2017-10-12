import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This is the GameManager class, which manages the game. It decides what to do
 * with input and overall game logic.
 */
public class GameManager {
    // Constants for number of levels.
    private static final int FINAL_LEVEL = 5;
    // Constant string for printing number of moves
    private static final String MOVES = "Moves: ";
    // Constants for getting the source of the level
    private static final String DIRECTORY = "res/levels/";
    private static final String FILE_EXT = ".lvl";
    /** Render x coordinate for "Moves" string */
    private static final int RENDER_COORD_X = 20;
    /** Render y coordinate for "Moves" string */
    private static final int RENDER_COORD_Y = 20;

    /** Current level the manager is controlling */
    private static int current_level;
    /** Number of targets in the current world */
    private static int target_count;
    /** Name of the current world */
    private static String level_name;
    /** World object of the current world */
    private static World current_world;
    /** GameStates object which manages the history of the current world */
    private static GameStates game;
    /** Player that is being managed by the GameManager */
    private static Player managed_player;
    /** Door that is being managed by the GameManager */
    private static Door door;

    /**
     * This function loads the game from the first level.
     */
    public static void newGame() {
        current_level = 0;
        level_name = DIRECTORY + current_level + FILE_EXT;
        loadLevel(level_name);
    }

    /**
     * Increments current_level and loads the next level.
     */
    private static void nextLevel() {
        current_level++;
        level_name = DIRECTORY + current_level + FILE_EXT;
        loadLevel(level_name);
    }

    /**
     * Loads the level according to the provided string.
     * @param level_name        The directory source of the level
     */
    private static void loadLevel(String level_name) {
        // Make a new world and a new history for this world.
        current_world = new World(level_name);
        game = new GameStates();

        // Grab the player and the door for later use.
        managed_player = grabPlayer();
        door = grabDoor();

        // Count the number of targets for checking if the level has won
        target_count = countTargets();
    }

    /**
     * Restarts the current level.
     */
    private static void restartLevel() {
        loadLevel(level_name);
    }

    /**
     * This function updates the game.
     * First it checks for input on whether the player undoes or restarts the
     * level.
     * It then takes the world if the player moves, except for timed entities
     * such as the ice block or the skeleton, which are updated every frame.
     *
     * @param input             input of the keyboard
     * @throws SlickException stop the compiler from complaining pls
     */
    public static void update(Input input) throws SlickException {

        if (input.isKeyPressed(Input.KEY_R)) {
            // Restarts the level if "R" is pressed.
            restartLevel();
        }
        else if (input.isKeyPressed(Input.KEY_L)) {
            // Restarts the level if "R" is pressed.
            nextLevel();
        }
        else if (input.isKeyPressed(Input.KEY_Z)) {
            // Undoes the last move if "Z" is pressed.
            // Check if the stack is empty.
            if (!game.check()) {
                // If not, then reassign the last move to the current world.
                World prev_world = game.undo();
                current_world.setMap(prev_world.getMap());
                // re-grab the player and door
                managed_player = grabPlayer();
                door = grabDoor();
            }
            else {
                restartLevel();
            }
        }
        else if (input.isKeyPressed(Input.KEY_DOWN) ||
                 input.isKeyPressed(Input.KEY_UP) ||
                 input.isKeyPressed(Input.KEY_LEFT) ||
                 input.isKeyPressed(Input.KEY_RIGHT)) {
            // Move around the player if the respective keys are pressed
            if (managed_player != null) {
                managed_player.update(input);
            }

            // Check if the player has just moved into a game over
            checkGameOver();

            // Otherwise update the world
            current_world.update();

            // Check if any enemies have touched the player
            checkGameOver();

            // Record the current world as a snapshot of this move
            recordWorld();
        }

        // Update the real time sprites (ice and skeleton)
        // regardless of player input
        updateRealTimeSprites();

        // If it's not the last "You Win" screen (where nothing happens)
        // then check if the player has won this level, or if a spooky skeleton
        // has just walked into them
        if (!(current_level == FINAL_LEVEL)) {
            checkGameWin();
            checkGameOver();
        }

    }

    /** This function updates sprites in the world which keep moving regardless
     * of player input.
     */
    private static void updateRealTimeSprites() {
        // make an arraylist containing the sprites to be moved
        ArrayList<Sprite> sprites_to_update = new ArrayList<Sprite>();
        for (ArrayList<Sprite> tile : current_world.getMap().values()) {
            for (Sprite sprite : tile) {
                if (sprite instanceof Timed) {
                    sprites_to_update.add(sprite);
                }
            }
        }
        // Iterate through list and update sprites
        Iterator<Sprite> itr = sprites_to_update.iterator();
        while(itr.hasNext()) {
            itr.next().update();
        }
    }

    /**
     * Checks the world if the player has walked into an enemy.
     */
    private static void checkGameOver() {
        for (Sprite sprite :
                current_world.getMapPos(managed_player.getPos())) {
            if (sprite instanceof Enemy) {
                restartLevel();
            }
        }
    }

    /**
     * This function checks if the player has completed the level.
     * If the player has, then restart the level.
     */
    private static void checkGameWin() {
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
        if (targetsCovered == target_count) {
            nextLevel();
        }
    }

    /**
     * Counts the number of targets in the world.
     * @return int              number of targets.
     */
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
     * Checks if the next move is valid.
     * @param pos               position to check
     * @return                  bool of whether it's a valid move or not
     */
    protected static boolean isValidMove(Coordinate pos) {
        // Iterate over the map, and if the sprite is moving to the specified
        // coordinate, check if the tile is blocked (as in it is a wall). If so,
        // return false, otherwise return true.
        boolean isValid = true;

        // First check for in bounds
        int checkX = App.SCREEN_WIDTH/2 -
                (current_world.getWorldX()*App.TILE_SIZE)/2 +
                (pos.getX()*App.TILE_SIZE);
        int checkY = App.SCREEN_HEIGHT/2 -
                (current_world.getWorldY()*App.TILE_SIZE)/2 +
                (pos.getY()*App.TILE_SIZE);
        int x = pos.getX();
        int y = pos.getY();

        // Check if the sprite is out of bounds
        if (!((x < checkX && x >= 0 ) && (y < checkY && y >= 0))){
            return false;
        }

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

    /**
     * This rehashes a sprite into the world (into the hashmap used in world)
     * @param old_pos           Old position that the sprite is currently in
     * @param new_pos           New position to more the sprite
     * @param sprite            Sprite to hash
     */
    protected static void rehashTile(Coordinate old_pos,
                                     Coordinate new_pos, Sprite sprite) {
        current_world.rehashTile(old_pos, new_pos, sprite,
                                    current_world.getMap());
    }

    /**
     * Adds the sprite to the current world at the specified position.
     * @param new_pos           the position to add the sprite
     * @param sprite            the sprite to add
     */
    protected static void addTile(Coordinate new_pos, Sprite sprite) {
        current_world.addToTile(new_pos, sprite, current_world.getMap());
    }

    /**
     * This function checks the next position if there is a block, attempts to
     * move it in the direction.
     * @param pos               Position to check
     * @param dir               Direction to move block in position
     * @return boolean of whether push was successful
     */
    protected static boolean tryPush(Coordinate pos, int dir) {
        boolean isValid = true;
        Iterator<Sprite> itr = current_world.getMapPos(pos).iterator();
        while (itr.hasNext()) {
            Sprite sprite = itr.next();
            if (sprite instanceof Block) {
                isValid = ((Block) sprite).move(dir);
                break;
            }
        }
        return isValid;
    }

    /**
     * This function checks the position for a block. If there is a block,
     * return true.
     * @param pos               Position to check.
     * @return boolean of whether there is a block in the specified position
     */
    protected static boolean checkPush(Coordinate pos) {
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

    /**
     * This sets the door in the current level to the specified state.
     * @param isOpen            The state to set the door to
     */
    protected static void switchDoor(boolean isOpen) {
        if (door != null) {
            door.setOpen(isOpen);
        }
    }

    /**
     * This looks through the current world and returns the player.
     * @return Returns the player of the world.
     */
    private static Player grabPlayer() {
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

    /**
     * This looks through the current world and returns the door.
     * @return Returns the door of the world.
     */
    private static Door grabDoor() {
        Door door = null;
        // Iterate over the list of sprites, and render them
        for (ArrayList<Sprite> height : current_world.getMap().values()) {
            for (Sprite sprite : height) {
                if (sprite instanceof Door) {
                    door = (Door) sprite;
                }
            }
        }
        return door;
    }

    /**
     * Returns the managed player.
     * @return player
     */
    public static Player getPlayer() {
        return managed_player;
    }

    /**
     * Renders the frame
     * @param g             Graphics object to render.
     */
    public static void render(Graphics g) {
        current_world.render();
        renderMoves(game.getDepth(), g);
    }

    /**
     * Deletes the specified sprite from the world
     * @param sprite        Sprite to delete
     * @param pos           Position of sprite
     */
    protected static void deleteSprite(Sprite sprite, Coordinate pos) {
        Iterator<Sprite> itr = current_world.getMapPos(pos).iterator();
        while(itr.hasNext()) {
            Sprite check = itr.next();
            if (check.equals(sprite)) {
                itr.remove();
            }
        }
    }

    /**
     * Gets the ArrayList of tiles at the specified position in the map
     * @param pos           Position to get
     * @return              ArrayList of sprites at this position
     */
    protected static ArrayList<Sprite> getTilesAtPos(Coordinate pos) {
        return current_world.getMapPos(pos);
    }

    /**
     * Records the state of the world in GameStates.
     * @throws SlickException to stop the compiler from being mad pls
     */
    private static void recordWorld() throws SlickException{
        World prev_world = new World(level_name);
        prev_world.setMap(current_world.copyMap(current_world.getMap()));
        game.recordMove(prev_world);
    }

    /**
     * This renders the number of moves on the screen.
     * @param depth         Number of moves
     * @param g             Graphics object
     */
    private static void renderMoves(int depth, Graphics g) {
        g.drawString(MOVES + Integer.toString(depth),
                    RENDER_COORD_X, RENDER_COORD_Y);
    }

}

