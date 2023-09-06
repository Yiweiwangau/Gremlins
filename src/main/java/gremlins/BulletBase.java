package gremlins;

import static gremlins.App.*;

/**
 * This class is the  class for all bullets in the game
 */
public abstract class BulletBase {
    /**
     * the x coordinate of the bullet
     */
    public int x;
    /**
     * the y coordinate of the bullet
     */
    public int y;
    /**
     * the direction of the bullet
     */
    public int speed;
    /**
     * the direction of the bullet
     */
    public App.Direction moveDirection;

    /**
     * constructor for the bullet
     *
     * @param x             x coordinate of the bullet
     * @param y             y coordinate of the bullet
     * @param speed         speed of the bullet
     * @param moveDirection direction of the bullet
     */
    public BulletBase(int x, int y, int speed, App.Direction moveDirection) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.moveDirection = moveDirection;
    }

    /**
     * this method is used to identify if the bullet is going to crash into a wall
     *
     * @return false if the bullet is at the same tile as a brick wall or the next tile is, and call breakBrickWall()
     * false if the bullet is at the same tile as a stone wall or the next tile is.
     */
    public boolean contact() {
        int y = this.y / SPRITESIZE;
        int x = this.x / SPRITESIZE;

        if (map[y][x] == 'X') return true;
        if (map[y][x] == 'B') {
            breakBrickWall(x, y);
            return true;
        }
        switch (moveDirection) {
            case Up:
                if (((y - 1 < 0) || (map[y - 1][x] == 'X')) && this.y <= y * SPRITESIZE) return true;
                if (((y - 1 < 0) || (map[y - 1][x] == 'B')) && this.y <= y * SPRITESIZE) {
                    breakBrickWall(x, y - 1);
                    return true;
                }
                this.y -= speed;
                break;
            case Down:
                if (((y + 1 >= HEIGHT / SPRITESIZE) || (map[y + 1][x] == 'X')) && this.y >= y * SPRITESIZE) return true;
                if (((y + 1 >= HEIGHT / SPRITESIZE) || (map[y + 1][x] == 'B')) && this.y >= y * SPRITESIZE) {
                    breakBrickWall(x, y + 1);
                    return true;
                }
                this.y += speed;
                break;
            case Left:
                if (((x - 1 < 0) || (map[y][x - 1] == 'X')) && this.x <= x * SPRITESIZE) return true;
                if (((x - 1 < 0) || (map[y][x - 1] == 'B')) && this.x <= x * SPRITESIZE) {
                    breakBrickWall(x - 1, y);
                    return true;
                }
                this.x -= speed;
                break;
            case Right:
                if (((x + 1 >= WIDTH / SPRITESIZE) || (map[y][x + 1] == 'X')) && this.x >= x * SPRITESIZE) return true;
                if (((x + 1 >= WIDTH / SPRITESIZE) || (map[y][x + 1] == 'B')) && this.x >= x * SPRITESIZE) {
                    breakBrickWall(x + 1, y);
                    return true;
                }
                this.x += speed;
                break;
        }
        return false;
    }

    /**
     * this method is an abstract method that is used to define how the wall is broken
     *
     * @param x x coordinate of the brick wall
     * @param y y coordinate of the brick wall
     */
    public abstract void breakBrickWall(int x, int y);

    /**
     * this method is an abstract method that is used to define how the bullet crashes into other objects
     *
     * @return true if the bullet crashes into other objects, false otherwise
     */
    public abstract boolean crashOther();

    /**
     * this method is used to identify if the bullet has hit a gremlin or player
     *
     * @param someone the gremlin or player that the bullet is going to crash into
     * @return true if the bullet has hit the gremlin or player, false otherwise
     */
    public boolean attack(MoveObjectBase someone) {
        if ((abs(someone.x - this.x) < SPRITESIZE) && (abs(someone.y - this.y) < SPRITESIZE)) {
            someone.numberOfLives--;
            return true;
        }
        return false;
    }

}
