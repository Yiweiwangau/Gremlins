package gremlins;

import static gremlins.App.beAttacked;
import static gremlins.App.wizard;

/**
 * This class represents slime Gremlin fires. It extends BulletBase.
 */
public class Slime extends BulletBase {


    /**
     * @param x             the x coordinate of the slime
     * @param y             the y coordinate of the slime
     * @param speed         the speed of the slime
     * @param moveDirection the direction of the slime is moving
     */
    public Slime(int x, int y, int speed, App.Direction moveDirection) {
        super(x, y, speed, moveDirection);
    }

    /**
     * @return return crashOther() if the parent class's contact() is false, otherwise, return true
     */
    @Override
    public boolean contact() {
        if (!super.contact()) {
            return crashOther();
        }
        return true;
    }

    /**
     * override breakBrickWall() in BulletBase to not break any brick wall since slime is not strong enough to break brick wall
     *
     * @param x x coordinate of the brick wall
     * @param y y coordinate of the brick wall
     */
    @Override
    public void breakBrickWall(int x, int y) {
// slime can not break wall
    }

    /**
     * this method is used to identify if the slime is going to crash into a player
     *
     * @return true if the slime crashes into a wizard/player and mark beAttacked as true, otherwise false
     */
    @Override
    public boolean crashOther() {
        if (attack(wizard)) {
            beAttacked = true;
            return true;
        }
        return false;
    }

}
