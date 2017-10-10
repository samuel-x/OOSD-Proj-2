import java.util.Stack;

public class GameStates {
    Stack<World> history = new Stack();
    public void recordMove(World world) {
        history.push(world);
    }
    public World undo() {
        return history.pop();
    }
}
