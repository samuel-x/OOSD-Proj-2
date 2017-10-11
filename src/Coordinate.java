public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof Coordinate))
            return false;

        Coordinate otherCoord = (Coordinate) other;

        return ((this.x == otherCoord.x) && (this.y == otherCoord.y));
    }

    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        return hash;
    }

    public Coordinate clone() {
        return new Coordinate(x, y);
    }
}
