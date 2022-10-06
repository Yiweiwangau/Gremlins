package gremlins;

import static gremlins.App.SPRITESIZE;

public class Player {
//    extract the methods to a seperate Player class

    public int playerX;
    public int playerY;

    public int playerSpeed = 2;

    public String playerDirection ="right";


    public Player(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
        playerDirection = "right";
    }

    public void findPlayerPosition(char[][] layoutArray) {
        for (int i = 0; i < layoutArray.length; i++) {
            for (int j = 0; j < layoutArray[i].length; j++) {
                if (layoutArray[i][j] == 'W') {
                    playerX = j * SPRITESIZE;
                    playerY = i * SPRITESIZE;
                }
            }
        }
    }

    public void checkPlayerPosition() {
        if (playerX % SPRITESIZE != 0) {
            if (playerDirection.equals("right")) {
                playerX += playerSpeed;
            } else if (playerDirection.equals("left")) {
                playerX -= playerSpeed;
            }
        }
        if (playerY % SPRITESIZE != 0) {
            if (playerDirection.equals("up")) {
                playerY -= playerSpeed;
            } else if (playerDirection.equals("down")) {
                playerY += playerSpeed;
            }
        }
    }


    // the wizard must not move through X and B walls.
    public void checkPlayerCollision(char[][] layoutArray) {
        int x = playerX / SPRITESIZE;
        int y = playerY / SPRITESIZE;

        switch (playerDirection) {
            case "right":
                if (layoutArray[y][x + 1] == 'X' || layoutArray[y][x + 1] == 'B') {
                    playerX -= playerSpeed;
                }

                break;
            case "left":
                if (layoutArray[y][x] == 'X' || layoutArray[y][x] == 'B') {
                    playerX += playerSpeed;
                }

                break;
            case "up":
                if (layoutArray[y][x] == 'X' || layoutArray[y][x] == 'B') {
                    playerY += playerSpeed;
                }

                break;
            case "down":
                if (layoutArray[y + 1][x] == 'X' || layoutArray[y + 1][x] == 'B') {
                    playerY -= playerSpeed;
                }

                break;
        }

    }

    public void left() {
        playerDirection = "left";
        playerX -= playerSpeed;
    }
    public void right() {
        playerDirection = "right";
        playerX += playerSpeed;
    }
    public void up() {
        playerDirection = "up";
        playerY -= playerSpeed;
    }
    public void down() {
        playerDirection = "down";
        playerY += playerSpeed;
    }

    public void draw(App app) {
        switch (playerDirection) {
            case "right":
                app.image(app.wizardright, playerX, playerY);
                break;
            case "left":
                app.image(app.wizardleft, playerX, playerY);
                break;
            case "up":
                app.image(app.wizardup, playerX, playerY);
                break;
            case "down":
                app.image(app.wizarddown, playerX, playerY);
                break;
        }
        checkPlayerCollision(app.layoutArray);
        checkPlayerPosition();
    }


}
