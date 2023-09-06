package gremlins;

import static gremlins.App.*;

/**
 * This class represents the wizard played by the user. It extends MoveObjectBase.
 */
public class Player extends MoveObjectBase {

    /**
     * speed of fireball
     */
    private final int FIREBALLSPEEED = 4;
    /**
     * Time remainder for powerUp on the player
     */
    public float powerUpTime;
    /**
     * whether the player is currently powered up
     */
    public boolean isPoweredUp;

    /**
     * constructor for Player
     *
     * @param x             the x coordinate of the player
     * @param y             the y coordinate of the player
     * @param numberOfLives number of the lives of the player
     */
    public Player(int x, int y, int numberOfLives) {
        super(x, y, 2, numberOfLives);
        isPoweredUp = false;
        powerUpTime = 0;
    }

    /**
     * check the position of the player to see if it is between two tiles. If yes, it will move to the next tile.
     */
    public void checkPosition() {
        if (x % SPRITESIZE != 0) {
            if (moveDirection == App.Direction.Right) {
                x += speed;
            } else if (moveDirection == App.Direction.Left) {
                x -= speed;
            }
        }
        if (y % SPRITESIZE != 0) {
            if (moveDirection == App.Direction.Up) {
                y -= speed;
            } else if (moveDirection == App.Direction.Down) {
                y += speed;
            }
        }
    }

    /**
     * if the cooldown is equal or below 0, fire a fireball and add to the list of fireball. Then reset the cooldown.
     */
    public void fireFireBall() {
        if (cdCount <= 0) {
            Fireball fireball = new Fireball(x, y, FIREBALLSPEEED, moveDirection);
            fireballs.add(fireball);
            cdCount = coolDown;
        }
    }

    /**
     * The player can teleport if lands on a portal. The player will be teleported another portal randomly.
     * The location the player is teleported to is a random empty tile next to the portal.
     */
    public void teleport() {
        if (portals.size() == 0)
            return;
        int player_y = wizard.y / SPRITESIZE;
        int player_x = wizard.x / SPRITESIZE;
//        choose a random portal except the one the player is on
        for (Portal p :
                portals) {
            if (player_x == p.portalX && player_y == p.portalY) {
                Portal p2 = portals.get((int) (Math.random() * portals.size()));
                while (p2 == p) {
                    p2 = portals.get((int) (Math.random() * portals.size()));
                }
                int x = p2.portalX;
                int y = p2.portalY;
//                   check nearby tiles randomly and move to the one selected
                while (map[y][x] != ' ') {
                    int r = (int) (Math.random() * 4);
                    switch (r) {
                        case 0:
                            x = p2.portalX + 1;
                            y = p2.portalY;
                            break;
                        case 1:
                            x = p2.portalX - 1;
                            y = p2.portalY;
                            break;
                        case 2:
                            x = p2.portalX;
                            y = p2.portalY + 1;
                            break;
                        case 3:
                            x = p2.portalX;
                            y = p2.portalY - 1;
                            break;
                        default:
                            break;
                    }
                }
                wizard.x = x * SPRITESIZE;
                wizard.y = y * SPRITESIZE;
            } else {
                continue;
            }
        }

    }


    /**
     * if the player is not currently powered up, power up the player by two-fold the playerspeed  and set isPoweredUp to true.
     */
    public void bePowerUp() {
        if (!isPoweredUp) {
            speed *= 2;
            isPoweredUp = true;
        }
    }

    /**
     * if the player is currently powered up, set the player speed back to normal, reset powerUp Timer and set isPoweredUp to false.
     */
    public void powerOver() {
        if (isPoweredUp) {
            speed /= 2;
            powerUpTime = 0;
            isPoweredUp = false;
        }
    }


}
