package gremlins;

import static gremlins.App.SPRITESIZE;
import static gremlins.App.map;


/**
 * This class is the parent class for free moving objects in the game.(Gremlin and Player)
 */
public class MoveObjectBase {
    /**
     * x coordinate of the object
     */
    public int x;
    /**
     * y coordinate of the object
     */
    public int y;

    public int numberOfLives;
    public int speed;
    public App.Direction moveDirection;
    public float coolDown;
    /**
     * counter of cooldown
     */
    public float cdCount;

    /**
     * constructor for the MoveObjectBase
     *
     * @param x             x coordinate of the object
     * @param y             y coordinate of the object
     * @param speed         speed of the object
     * @param numberOfLives number of lives of the object
     */
    public MoveObjectBase(int x, int y, int speed, int numberOfLives) {
        this.x = x;
        this.y = y;
        this.numberOfLives = numberOfLives;
        this.speed = speed;
        moveDirection = App.Direction.Right;
        cdCount = 0;
    }

    /**
     * set cooldown respectively
     * @param cd coolDown
     */
    public void setCoolDown(float cd) {
        coolDown = cd;
    }


    /**
     * this method is used to check if the object will crash into a wall, if so, it will stop moving
     *
     * @return true if the object is at the same tile as a wall or the next tile is.
     */
    public boolean move() {
        int y = this.y / SPRITESIZE;
        int x = this.x / SPRITESIZE;

        switch (moveDirection) {
            case Up:
                moveDirection = App.Direction.Up;
                if ((map[y - 1][x] == 'X' || map[y - 1][x] == 'B') && this.y <= y * SPRITESIZE) return false;
                this.y -= speed;
                break;
            case Right:
                moveDirection = App.Direction.Right;
                if ((map[y][x + 1] == 'X' || map[y][x + 1] == 'B') && this.x >= x * SPRITESIZE) return false;
                this.x += speed;
                break;
            case Down:
                moveDirection = App.Direction.Down;
                if ((map[y + 1][x] == 'X' || map[y + 1][x] == 'B') && this.y >= y * SPRITESIZE) return false;
                this.y += speed;
                break;
            case Left:
                moveDirection = App.Direction.Left;
                if ((map[y][x - 1] == 'X' || map[y][x - 1] == 'B') && this.x <= x * SPRITESIZE) return false;
                this.x -= speed;
                break;
            default:
                break;
        }
        return true;
    }
}
