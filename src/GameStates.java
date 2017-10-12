import java.util.Stack;

/**
 * This class holds the previous instances of the game world, which will be used
 * when the player undoes his move.
 */
public class GameStates {
    /** The stack in which the instances of the game world are held */
    private static Stack<World> history;
    /** Number of turns made */
    private static int depth;

    /**
     * This is the data constructor for GameStates
     */
    public GameStates() {
        this.history = new Stack<World>();
        this.depth = 0;
    }

    /**
     * This function records a move into the history stack.
     * @param world         The world instance to record
     */
    public void recordMove(World world) {
        history.push(world);
        depth++;
    }

    /**
     * This function returns the last instance of the game world
     * to be used when the player undoes his move.
     * @return World        The previous instance of world
     */
    public World undo() {
        World undo = history.pop();
        depth--;
        return undo;
    }

    /**
     * Checks if there are no previous turns left
     * @return boolean      True if there are turns left, false otherwise
     */
    public boolean check() {
        return history.empty();
    }

    /**
     * Gets the number of turns recorded
     * @return int          Number of turns recorded.
     */
    public int getDepth() {
        return depth;
    }
}
