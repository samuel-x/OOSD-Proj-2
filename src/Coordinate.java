/**
 *  This class represents one X Y coordinate. This is used by sprites to
 *  describe position in the game world.
 */
public class Coordinate {
    public int x;
    public int y;

    // Arbitrarily picked non-zero value for hashcode() according to
    // "Effective Java by Joshua Bloch"
    // http://www.oracle.com/technetwork/java/effectivejava-136174.html
    private final int INITIAL_HASH_CODE = 17;

    /**
     * This is the data constructor for a coordinate.
     * @param x         horizontal position of this coordinate
     * @param y         vertical position of this coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x position of this coordinate.
     * @return          x position of this coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of this coordinate.
     * @return          y position of this coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x position of this coordinate.
     * @param x         x position to set this coordinate to
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y position of this coordinate.
     * @param y         y position to set this coordinate to
     */
    public void setY(int y) { this.y = y; }

    /**
     * This is used to compare equality between two coordinates.
     * @param other     The other coordinate to compare this coordinate against.
     * @return boolean  True if the coordinates are equal, false if not.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Coordinate))
            return false;

        Coordinate otherCoord = (Coordinate) other;

        return ((this.x == otherCoord.x) && (this.y == otherCoord.y));
    }

    /**
     * This creates a hash code for this coordinate as per described in
     * "Effecive Java by Joshua Bloch"
     * http://www.oracle.com/technetwork/java/effectivejava-136174.html
     *
     * @return          the hashcode of this coordinate as an integer.
     */
    @Override
    public int hashCode() {
        final int HASH_PRIME = 31;
        int result = INITIAL_HASH_CODE;
        result = HASH_PRIME * result + x;
        result = HASH_PRIME * result + y;
        return result;
    }

    /**
     * This makes a copy of the current coordinate.
     * @return          a new copy of this coordinate
     */
    public Coordinate clone() {
        return new Coordinate(x, y);
    }
}
