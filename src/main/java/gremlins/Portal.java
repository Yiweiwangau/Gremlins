package gremlins;

/**
 * This class represents the portal that the player can teleport to.
 */
public class Portal {
    /**
     * the x coordinate of the portal
     */
    public int portalX;
    /**
     * the y coordinate of the portal
     */
    public int portalY;

    /**
     * constructor of Portal
     *
     * @param x the x coordinate of the portal
     * @param y the y coordinate of the portal
     */
    public Portal(int x, int y) {
        portalX = x;
        portalY = y;
    }
}
