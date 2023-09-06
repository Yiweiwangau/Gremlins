package gremlins;

import static gremlins.App.*;

/**
 * This class represents a fireball. It extends BulletBase.
 */
public class Fireball extends BulletBase {


    /**
     * constructor for the fireball
     *
     * @param x             x coordinate of the fireball
     * @param y             y coordinate of the fireball
     * @param speed         speed of the fireball
     * @param moveDirection moving direction of the fireball
     */
    public Fireball(int x, int y, int speed, Direction moveDirection) {
        super(x, y, speed, moveDirection);
    }

    /**
     * this method defines what happens when the fireball hits a wall. The location of the wall is updated to ' ' in the map.
     * A brokenwall object is created and added to the brokenWalls arraylist with the animation time of 16.
     *
     * @param x x coordinate of the wall
     * @param y y coordinate of the wall
     */
    @Override
    public void breakBrickWall(int x, int y) {
        map[y][x] = ' ';
        brokenWalls.add(new BrokenWall(x, y, 16));
    }

    /**
     * @return crashOther() if the parent class's contact() is false, otherwise, return true
     */
    @Override
    public boolean contact() {
        if (!super.contact()) {
            return crashOther();
        }
        return true;
    }


    /**
     * this method defines what happens when the fireball hits a gremlin or the slime.
     * slime is removed from the map if fireball hits it.
     *
     * @return true if the fireball hits a gremlin or the slime, false otherwise
     */
    @Override
    public boolean crashOther() {
        for (Gremlin someone :
                gremlins) {
            if (attack(someone))
                return true;
        }
        Slime s;
        for (int i = slimes.size() - 1; i >= 0; i--) {
            s = slimes.get(i);
            if ((abs(s.x - x) < SPRITESIZE) && (abs(s.y - y) < SPRITESIZE)) {
                slimes.remove(s);
                return true;
            }
        }
        return false;
    }
}
