import java.util.Stack;

public class GameStates {
    static Stack<World> history = new Stack();
    public static void recordMove(World world) {
        history.push(world);
    }
    public static World undo() {
        World undo = history.pop();
        System.out.println(undo);
        return undo;
    }
}
