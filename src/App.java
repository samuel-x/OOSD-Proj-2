
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Main class for the game.
 * Handles initialisation, input and rendering.
 *
 *  Made for Project 2, SWEN20003: Object Oriented Software Development 2017
 *  @author Samuel Xu
 *  using Eleanor McMurty's skeleton code.
 *  samuelx@student.unimelb.edu.au
 *  Student Number: 835273
 */
public class App extends BasicGame
{
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 800;
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 600;
    /** size of the tiles, in pixels */
    public static final int TILE_SIZE = 32;

    public App()
    {
        super("Shadow Blocks");
    }

    @Override
    public void init(GameContainer gc)
    throws SlickException
    {
    	GameManager.newGame();
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException to stop the compiler from being mad
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        GameManager.update(input);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     * @throws SlickException to stop the compiler from being mad
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
    	GameManager.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     * @throws SlickException to stop the compiler from being mad
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new App());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }

}