package gremlins;

/**
 * This class represents the powerup that the player can pick up.
 */
public class PowerUp {
    /**
     * the x coordinate of the powerup
     */
    public int powerUpX;
    /**
     * the y coordinate of the powerup
     */
    public int powerUpY;
    public float duration;
    public float coolDown;
    public float cdTimer;
    /**
     * the status of whether the powerUp is displayed. If it is 0, it is displayed. If it is 1, it is not displayed.
     */
    public int displayStatus;

    /**
     * @param x        the x coordinate of the powerup
     * @param y        the y coordinate of the powerup
     * @param start    the start on the timer of the powerup
     * @param duration the duration of the powerup
     * @param coolDown the cooldown of the powerup
     */
    public PowerUp(int x, int y, float start, float duration, float coolDown) {
        displayStatus = -1;
        cdTimer = start;
        powerUpX = x;
        powerUpY = y;
        this.duration = duration;
        this.coolDown = coolDown;
    }
}
