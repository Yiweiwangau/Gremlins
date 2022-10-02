package gremlins;
//Gremlin enemies are green mischievous figures. It has the character G in the map layout. When hit by a wizard’s fireball, it will disappear and respawn in another empty area of the map, at least 10 tiles radius away from the player (in the process, absorbing the fireball). Each gremlin throws slime projectiles in the direction of their current movement, with a frequency in seconds specified in the configuration JSON for that level. If the gremlin hits a wall with more than one possible new direction to go in, it will randomly choose a new direction but won’t go back the way it just came.
//        If the wizard comes into contact with a gremlin or its slime, they lose a life and the level is reset to its original state. If the wizard’s fireball hits a gremlin’s slime, the slime absorbs the fireball, and in the process is itself vapourised.
//        Gremlin movement speed is 1 pixel per frame and slime projectile speed is 4 pixels per frame.

import static gremlins.App.SPRITESIZE;
import static gremlins.App.randomGenerator;

public class Gremlin {
    public int GremlinX;
    public int GremlinY;
    public String GremlinDirection="right";
    public int GremlinSpeed =1;

    public Gremlin(int GremlinX, int GremlinY) {
        this.GremlinX = GremlinX;
        this.GremlinY = GremlinY;
    }

    public void findNewDirection(char[][] layoutArray) {
        int randomDirection = randomGenerator.nextInt(4);
        if (randomDirection == 0) {
            if (layoutArray[GremlinY][GremlinX + 1] == ' ') {
                GremlinDirection = "right";
            } else {
                findNewDirection(layoutArray);
            }
        } else if (randomDirection == 1) {
            if (layoutArray[GremlinY][GremlinX - 1] == ' ') {
                GremlinDirection = "left";
            } else {
                findNewDirection(layoutArray);
            }
        } else if (randomDirection == 2) {
            if (layoutArray[GremlinY + 1][GremlinX] == ' ') {
                GremlinDirection = "down";
            } else {
                findNewDirection(layoutArray);
            }
        } else {
            if (layoutArray[GremlinY - 1][GremlinX] == ' ') {
                GremlinDirection = "up";
            } else {
                findNewDirection(layoutArray);
            }
        }
    }
//    find the four gremlin positions. They are all called G in the layout file. All four gremlins must be placed in different positions.
    public void findGremlinPosition(char[][] layoutArray) {
        for (int i = 0; i < layoutArray.length; i++) {
            for (int j = 0; j < layoutArray[i].length; j++) {
                if (layoutArray[i][j] == 'G') {
                    GremlinX = j*SPRITESIZE;
                    GremlinY = i*SPRITESIZE;
                    layoutArray[i][j] = ' ';
                }
            }
        }
    }

    public void left() {
        GremlinX -= GremlinSpeed;
        GremlinDirection = "left";
    }
    public void right() {
        GremlinX += GremlinSpeed;
        GremlinDirection = "right";
    }
    public void up() {
        GremlinY -= GremlinSpeed;
        GremlinDirection = "up";
    }
    public void down() {
        GremlinY += GremlinSpeed;
        GremlinDirection = "down";
    }

    public void draw(App app) {
        app.image(app.gremlin, GremlinX, GremlinY);
        right();
    }

}
