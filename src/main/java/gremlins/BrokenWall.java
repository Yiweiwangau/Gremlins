package gremlins;

/**
 * This class represents a broken wall.
 */
public class BrokenWall {

    /**
     * x coordinate of the wall that got broken
     */
    public int wallX;
    /**
     * y coordinate of the wall that got broken
     */
    public int wallY;
    /**
     * time of wall breaking animation
     */
    public int animationTime;
    /**
     * number of wall broken
     */
    public int n;

    /**
     * constructor for the bullet
     *
     * @param x             x coordinate of the wall that got broken
     * @param y             y coordinate of the wall that got broken
     * @param animationTime time of wall breaking animation
     */
    public BrokenWall(int x, int y, int animationTime) {
        wallX = x;
        wallY = y;
        this.animationTime = animationTime;
        n = 0;
    }
}
