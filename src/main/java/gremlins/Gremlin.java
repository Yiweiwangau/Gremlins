package gremlins;

import java.util.Objects;

import static gremlins.App.SPRITESIZE;
import static gremlins.App.randomGenerator;

public class Gremlin {
    public int GremlinX;
    public int GremlinY;
    public String GremlinDirection="up";
    public int GremlinSpeed =1;

    public Gremlin(int GremlinX, int GremlinY) {
        this.GremlinX = GremlinX;
        this.GremlinY = GremlinY;
    }

    public void setX(int GremlinX){
        this.GremlinX = GremlinX;
    }

    public void setY(int GremlinY){
        this.GremlinY = GremlinY;
    }

    public void move(char[][] layoutArray) {
        int y = GremlinY / SPRITESIZE;
        int x = GremlinX / SPRITESIZE;
        if (GremlinDirection.equals("right")) {
            if (layoutArray[y][x + 1] == 'X' || layoutArray[y][x + 1] == 'B') {
                GremlinDirection = "left";
            } else {
                GremlinX += GremlinSpeed;
            }
        } else if (GremlinDirection.equals("left")) {
            if (layoutArray[y][x] == 'X' || layoutArray[y][x] == 'B') {
                GremlinDirection = "right";
            } else {
                GremlinX -= GremlinSpeed;
            }
        } else if (GremlinDirection.equals("up")) {
            if (layoutArray[y][x] == 'X' || layoutArray[y][x] == 'B') {
                GremlinDirection = "down";
            } else {
                GremlinY -= GremlinSpeed;
            }
        } else if (GremlinDirection.equals("down")) {
            if (layoutArray[y + 1][x] == 'X' || layoutArray[y + 1][x] == 'B') {
                GremlinDirection = "up";
            } else {
                GremlinY += GremlinSpeed;
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
        move(app.layoutArray);
    }

}
