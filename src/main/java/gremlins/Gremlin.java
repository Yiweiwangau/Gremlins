package gremlins;

import java.util.ArrayList;

import static gremlins.App.*;


/**
 * This class represents gremlin and it extends MoveObjectBase
 */
public class Gremlin extends MoveObjectBase {

    /**
     * constructor of Gremlin
     *
     * @param GremlinX the x coordinate of the gremlin
     * @param GremlinY the y coordinate of the gremlin
     */
    public Gremlin(int GremlinX, int GremlinY) {
        super(GremlinX, GremlinY, 1, 1);
        moveDirection = App.Direction.values()[abs(App.randomGenerator.nextInt()) % App.Direction.values().length];
    }

    /**
     * @return the x coordinate of the gremlin
     */
    public int getXi() {
        return x / SPRITESIZE;
    }

    /**
     * @return the y coordinate of the gremlin
     */
    public int getYi() {
        return y / SPRITESIZE;
    }

    /**
     * this method check the possible directions of the gremlin and add them to an arraylist. Then it randomly chooses one of the directions
     *
     * @return true if the gremlin can move, false otherwise
     */
    public boolean move() {
        if (super.move()) {
            return true;
        } else {
            int x = getXi();
            int y = getYi();
            ArrayList<App.Direction> possibleDirections = new ArrayList<>();
            if (map[y - 1][x] != 'B' && map[y - 1][x] != 'X') {
                possibleDirections.add(App.Direction.Up);
            }
            if (map[y + 1][x] != 'B' && map[y + 1][x] != 'X') {
                possibleDirections.add(App.Direction.Down);
            }
            if (map[y][x - 1] != 'B' && map[y][x - 1] != 'X') {
                possibleDirections.add(App.Direction.Left);
            }
            if (map[y][x + 1] != 'B' && map[y][x + 1] != 'X') {
                possibleDirections.add(App.Direction.Right);
            }
            if (possibleDirections.size() > 0) {
                if (possibleDirections.size() > 1) {
                    switch (moveDirection) {
                        case Right:
                            possibleDirections.remove(App.Direction.Left);
                            break;
                        case Left:
                            possibleDirections.remove(App.Direction.Right);
                            break;
                        case Down:
                            possibleDirections.remove(App.Direction.Up);
                            break;
                        case Up:
                            possibleDirections.remove(App.Direction.Down);
                            break;
                    }
                }
                moveDirection = possibleDirections.get(abs(App.randomGenerator.nextInt()) % possibleDirections.size());
                return true;
            }
            return false;
        }
    }

    /**
     * The randMove function spawn the killed gremlin in a random place 10 tiles away from the player
     */
    public void respawnGremlin() {
        int a, b;
        int x = wizard.x / SPRITESIZE;
        int y = wizard.y / SPRITESIZE;
        while (true) {
            a = abs(randomGenerator.nextInt() % (WIDTH / SPRITESIZE));
            b = abs(randomGenerator.nextInt() % (HEIGHT / SPRITESIZE));
            if (map[b][a] != ' ')
                continue;
            if (abs(a - x) + abs(b - y) < 10)
                continue;
            break;
        }
        this.x = a * SPRITESIZE;
        this.y = b * SPRITESIZE;
        this.numberOfLives = 1;
    }

    /**
     * if cd counter is 0 or below, the gremlin will fire a slime and reset cooldown counter
     */
    public void fireSlime() {
        if (cdCount <= 0) {
            Slime slime = new Slime(x, y, 4, moveDirection);
            slimes.add(slime);
            cdCount = coolDown;
        }
    }
}
