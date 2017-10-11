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
        System.out.println("Depth" + depth);
        System.out.println("Size" + history.size());
    }
    public World undo() {
        World undo = history.pop();
        depth--;
        System.out.println("Depth" + depth);
        System.out.println("Size" + history.size());
        return undo;
    }
    public boolean check() {
        return history.empty();
    }
}
