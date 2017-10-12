import java.util.Stack;

public class GameStates {
    public static Stack<World> history;
    public static int depth;

    public GameStates() {
        this.history = new Stack<World>();
        this.depth = 0;
    }

    public void recordMove(World world) {
        history.push(world);
        depth++;
    }
    public World undo() {
        World undo = history.pop();
        depth--;
        return undo;
    }
    public boolean check() {
        return history.empty();
    }

    public static int getDepth() {
        return depth;
    }
}
