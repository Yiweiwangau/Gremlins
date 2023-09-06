package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;



/**
 * This class represents the main application of the game. It extends PApplet for processing functionality.
 */
public class App extends PApplet {
    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();
    /**
     * initiate beAttacked by declaring a boolean as false
     */
    public static boolean beAttacked = false;
    public static ArrayList<Portal> portals = new ArrayList<>();
    public static Player wizard;
    public static ArrayList<Gremlin> gremlins;
    public static ArrayList<Fireball> fireballs;
    public static ArrayList<Slime> slimes;
    public static ArrayList<BrokenWall> brokenWalls;
    public static float wizardCoolDown;
    public static float enemyCoolDown;
    /**
     * initiate the map by declaring a 2D array of char
     */
    static char[][] map = new char[HEIGHT / SPRITESIZE][WIDTH / SPRITESIZE];
    private final int STARTLEVEL = 0;
    public String configPath;
    public PImage brickWall;
    public PImage brickWall_Destroyed0;
    public PImage brickWall_Destroyed1;
    public PImage brickWall_Destroyed2;
    public PImage brickWall_Destroyed3;
    public PImage stoneWall;
    public PImage door;
    public PImage portal;
    public PImage powerUpImg;
    public PImage wizardLeft;
    public PImage wizardRight;
    public PImage wizardUp;
    public PImage wizardDown;
    public PImage gremlin;
    public PImage fireBallImg;
    public PImage slimeImg;
    public int currentLevel;
    public JSONObject conf;
    public PowerUp powerUpObj;
    public Door doorObj;
    public ArrayList<PImage> breakWallAnimation;
    /**
     * initiate current gamestatus on "Init"
     */
    GameStatus curStatus = GameStatus.Init;

    public App() {
        this.configPath = "config.json";
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }

    @Override
    public void settings() {
        conf = loadJSONObject(new File(this.configPath));
        size(WIDTH, HEIGHT);
    }

    /**
     * The setup function is called once when the program starts. It's used to perform any
     * necessary initialization. In this case, it loads the images and creates a list of broken walls
     */
    @Override
    public void setup() {
        frameRate(FPS);
        InitData();
        //load all images and adjust size accordingly
        this.stoneWall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickWall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        brickWall_Destroyed0 = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        brickWall_Destroyed1 = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        brickWall_Destroyed2 = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        brickWall_Destroyed3 = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
        this.door = loadImage(this.getClass().getResource("door.png").getPath().replace("%20", " "));
        door.resize(SPRITESIZE, SPRITESIZE);
        this.portal = loadImage(this.getClass().getResource("portal.png").getPath().replace("%20", " "));
        portal.resize(SPRITESIZE, SPRITESIZE);
        powerUpImg = loadImage(this.getClass().getResource("powerup.png").getPath().replace("%20", " "));
        powerUpImg.resize(SPRITESIZE, SPRITESIZE);
        wizardLeft = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        wizardRight = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        wizardUp = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        wizardDown = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));

        fireBallImg = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        slimeImg = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        //set game status to gaming
        curStatus = GameStatus.Gaming;
        // generate brokenwall list and animation list
        brokenWalls = new ArrayList<>();
        breakWallAnimation = new ArrayList<>();
        breakWallAnimation.add(brickWall_Destroyed0);
        breakWallAnimation.add(brickWall_Destroyed1);
        breakWallAnimation.add(brickWall_Destroyed2);
        breakWallAnimation.add(brickWall_Destroyed3);
    }

    /**
     * The keyPressed function is called whenever a key is pressed.
     * It's used to change the direction of the wizard and fire Fireballs.
     * When the game is over(either win or lose), any key can restart the game.
     */
    @Override
    public void keyPressed() {
        if (curStatus == GameStatus.Lose || curStatus == GameStatus.Win){
            delay(1000);
            curStatus = GameStatus.Init;
        }
        if (key == CODED) {
            if (keyCode == UP) {
                wizard.moveDirection = Direction.Up;
                wizard.move();
            } else if (keyCode == DOWN) {
                wizard.moveDirection = Direction.Down;
                wizard.move();
            } else if (keyCode == LEFT) {
                wizard.moveDirection = Direction.Left;
                wizard.move();
            } else if (keyCode == RIGHT) {
                wizard.moveDirection = Direction.Right;
                wizard.move();
            }
        } else if (key == 32) {
            wizard.fireFireBall();
        }
    }

    /**
     * do nothing when key is released
     */
    @Override
    public void keyReleased() {
    }

    /**
     * main method in processing to draw the game. It calls setup during game status of Init.
     * It prints win or lose message when game is over.
     * It calls all draw methods when gaming.
     */
    @Override
    public void draw() {
        background(191, 153, 114);
//      checking the game status and draw the corresponding screen
        switch (curStatus) {
            case Init:
                setup();
                break;
            case Win:
                textSize(36);
                text("You win", 300, 320);
                break;
            case Lose:
                textSize(26);
                text("Game over", 300, 320);
                break;
            case Gaming:
                drawStationaryObjects();
                drawBrokenWall();
                drawPowerup();
                drawPlayer();
                setUpGremlin();

                setUpBullets();
                drawBottombar();
                wizard.checkPosition();
                checkStatus();
                wizard.teleport();

                break;
            default:
                break;
        }
    }

    /**
     * The checkStatus function checks if the player is attacked, if the player has obtained a powerup, if the player has reached the door and if the player has got to the same location as the gremlin.
     * If so, it will read in a new layout and reset all the variables.
     */
    public void checkStatus() {
        if (beAttacked) {
            generateLayout();
            beAttacked = false;
            return;
        }
        // check if the player has obtained a powerup, if so, set the poweruptime as the powerup duration and set the player's powerup to true
        if (powerUpObj != null && powerUpObj.powerUpX == wizard.x / SPRITESIZE && powerUpObj.powerUpY == wizard.y / SPRITESIZE) {
            powerUpObj.displayStatus = 1;
            wizard.powerUpTime = powerUpObj.duration;
            wizard.bePowerUp();
        }
        int player_y = wizard.y / SPRITESIZE;
        int player_x = wizard.x / SPRITESIZE;
        // if player reaches gremilin, reduce number of lives and reset the game
        for (Gremlin g :
                gremlins) {
            if (player_x == g.getXi() && player_y == g.getYi()) {
                wizard.numberOfLives--;
                generateLayout();
                return;
            }
        }
        // if the player has reached the door, increase the level number and read in a new layout
        if (player_x == doorObj.doorX && player_y == doorObj.doorY) {
            currentLevel++;
            generateLayout();
        }
    }

    /**
     * initialise the arraylists and generate a new layout
     */
    private void InitData() {
        gremlins = new ArrayList<>();
        fireballs = new ArrayList<>();
        slimes = new ArrayList<>();

        generateLayout();
    }

    /**
     * The first part of the mehod is to clear everything in the game and sets the current game status to the correct status.
     * The generateLayout function reads in a new layout from the text file and sets the player's position, the door's position and the powerup's position.
     */
    public void generateLayout() {
        //clear everything in the game
        gremlins.clear();
        fireballs.clear();
        slimes.clear();
        portals.clear();
        powerUpObj = null;
        if (curStatus == GameStatus.Init) {
            wizard = null;
            currentLevel = STARTLEVEL;
        }
        // if currentlevel is larger than the number of levels, the player wins
        if (this.currentLevel >= conf.getJSONArray("levels").size()) {
            curStatus = GameStatus.Win;
            return;
        }
        // if the player has no lives left, the player loses
        if (wizard != null && wizard.numberOfLives == 0) {
            curStatus = GameStatus.Lose;
            return;
        }
        try {
            //read cooldown from json file
            wizardCoolDown = conf.getJSONArray("levels").getJSONObject(this.currentLevel).getFloat("wizard_cooldown");
            enemyCoolDown = conf.getJSONArray("levels").getJSONObject(this.currentLevel).getFloat("enemy_cooldown");
            File file = new File(conf.getJSONArray("levels").getJSONObject(this.currentLevel).getString("layout"));
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int i = 0;
            while ((st = br.readLine()) != null) {
                for (int j = 0; j < st.length(); j++) {
                    map[i][j] = st.charAt(j);
                    //initialize wizard, gremlin, door, powerup and portal. Add them to the corresponding arraylist
                    if (map[i][j] == 'W') {
                        if (wizard == null)
                            wizard = new Player(j * SPRITESIZE, i * SPRITESIZE, conf.getInt("lives"));
                        else {
                            wizard.x = j * SPRITESIZE;
                            wizard.y = i * SPRITESIZE;
                            wizard.powerOver();
                        }
                        wizard.setCoolDown(wizardCoolDown);
                    } else if (map[i][j] == 'G') {
                        Gremlin gg = new Gremlin(j * SPRITESIZE, i * SPRITESIZE);
                        gg.setCoolDown(enemyCoolDown);
                        gremlins.add(gg);
                        gg.move();
                    } else if (map[i][j] == 'E') {
                        doorObj = new Door(j, i);
                    } else if (map[i][j] == 'T') {
                        Portal p = new Portal(j, i);
                        portals.add(p);
                    }
                    // initialize the powerup
                    else if (map[i][j] == 'P') {
                        powerUpObj = new PowerUp(j, i, 5, 10, 10);
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * draw powerup and set up the powerup timer
     */
    public void drawPowerup() {
        if (powerUpObj != null) {
            if (powerUpObj.displayStatus == 0) {
                image(powerUpImg, powerUpObj.powerUpX * SPRITESIZE, powerUpObj.powerUpY * SPRITESIZE);
            } else if (wizard.powerUpTime <= 0) {
                if (powerUpObj.cdTimer <= 0) {
                    powerUpObj.displayStatus = 0;
                    powerUpObj.cdTimer = powerUpObj.coolDown;
                }
                powerUpObj.cdTimer -= (1.0 / FPS);
            }
        }
    }

    /**
     * draw all stationary objects like door, portal, walls
     */
    public void drawStationaryObjects() {
        //draw the door
        if (doorObj != null)
            image(door, doorObj.doorX * SPRITESIZE, doorObj.doorY * SPRITESIZE);
        //draw the portal
        for (Portal p :
                portals) {
            image(portal, p.portalX * SPRITESIZE, p.portalY * SPRITESIZE);
        }
        //draw the stonewall and brickwall
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 'X') {
                    image(stoneWall, j * SPRITESIZE, i * SPRITESIZE);
                } else if (map[i][j] == 'B') {
                    image(brickWall, j * SPRITESIZE, i * SPRITESIZE);
                }
            }
        }
    }

    /**
     * draw the wizard and use different images for different directions
     */
    public void drawPlayer() {
        switch (wizard.moveDirection) {
            case Up:
                image(wizardUp, wizard.x, wizard.y);
                break;
            case Right:
                image(wizardRight, wizard.x, wizard.y);
                break;
            case Down:
                image(wizardDown, wizard.x, wizard.y);
                break;
            case Left:
                image(wizardLeft, wizard.x, wizard.y);
                break;
        }
    }

    /**
     * draw gremlins.
     * if the gremlin is dead, call respwanGremlin to respawn it.
     * Enable the gremlin's move and fireslime ability and set cooldown
     */
    public void setUpGremlin() {

        for (Gremlin g :
                gremlins) {
            if (g.numberOfLives <= 0) {
                g.respawnGremlin();
            }
            image(gremlin, g.x, g.y);
            g.move();
            if (g.cdCount > 0) {
                g.cdCount -= (1.0 / FPS);
            } else {
                g.fireSlime();
            }
        }
    }

    /**
     * draw two kinds of bullets, fireball and slime
     * if slime and fireball get in contact of something, they will disappear
     */
    public void setUpBullets() {
        for (Fireball f :
                fireballs) {
            image(fireBallImg, f.x, f.y);
        }
        for (Slime s :
                slimes) {
            image(slimeImg, s.x, s.y);
        }
        Fireball f;
        for (int i = fireballs.size() - 1; i >= 0; i--) {
            f = fireballs.get(i);
            if (f.contact()) {
                fireballs.remove(f);
            }
        }
        Slime s;
        for (int i = slimes.size() - 1; i >= 0; i--) {
            s = slimes.get(i);
            if (s.contact()) {
                slimes.remove(s);
            }
        }
    }

    /**
     * draw contents in the bottom bar, for example, the number of lives, the current level, the cooldown bar
     */
    public void drawBottombar() {
        textSize(26);
        // draw level number
        text(String.format("Level %d/%d", currentLevel + 1, conf.getJSONArray("levels").size()), 300, 700);
        //draw lives
        text("Lives:", 20, 700);
        for (int i = 1; i <= wizard.numberOfLives; i++) {
            image(wizardRight, 100 + i * SPRITESIZE, 680);
        }
        //draw cooldown bar
        if (wizard.cdCount > 0) {
            rect(560, 685, 120, 10);
            fill(0);
            rect(560, 685, (120 * (wizard.cdCount / wizard.coolDown)), 10);
            fill(255);
            wizard.cdCount -= (1.0 / FPS);
        }
        // draw powerup bar
        if (wizard.powerUpTime > 0) {
            rect(560, 700, 120, 10);
            fill(180);
            rect(560, 700, (120 * (wizard.powerUpTime / powerUpObj.coolDown)), 10);
            fill(255);
            wizard.powerUpTime -= (1.0 / FPS);
        } else {
            wizard.powerOver();
        }
    }

    /**
     * draw broken wall and animation of wall breaking
     */
    public void drawBrokenWall() {
        if (brokenWalls.size() == 0) return;
        int frame = brokenWalls.get(0).animationTime / breakWallAnimation.size();
        for (int i = brokenWalls.size() - 1; i >= 0; i--) {
            if (brokenWalls.get(i).n == brokenWalls.get(i).animationTime) {
                brokenWalls.remove(i);
                continue;
            }
            image(breakWallAnimation.get(brokenWalls.get(i).n / frame), brokenWalls.get(i).wallX * SPRITESIZE, brokenWalls.get(i).wallY * SPRITESIZE);
            brokenWalls.get(i).n++;
        }
    }

    /**
     * list all possible game status
     */
    public enum GameStatus {
        Init,
        Gaming,
        Win,
        Lose,
        Null
    }

    /**
     * list all possible directions
     */
    public enum Direction {
        Right,
        Down,
        Left,
        Up
    }
}
