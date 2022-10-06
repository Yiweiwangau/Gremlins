package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;

import java.util.Random;
import java.io.*;

    // The player wizard character is controlled using the arrow keys (up, down, left, right). Movement should be smoothly transitioning from one tile space to another. The player begins in the tile W on the map layout. The user must be actively holding the movement key for movement to occur, otherwise movement stops (when it reaches the next whole tile). The wizard may only stop movement on a whole tile space, not part-way between tiles.
    // The player’s movement speed is 2 pixels per frame. Fireball speed is 4 pixels per frame. The player may only move in one direction at a time. The player may not move diagonally. The player may not move through walls or other objects. The player may not move through fireballs.
public class App extends PApplet {

    public static final int WIDTH = 720;
    public static final int HEIGHT = 720;
    public static final int SPRITESIZE = 20;
    public static final int BOTTOMBAR = 60;

    public static final int FPS = 60;

    public static final Random randomGenerator = new Random();

    public String configPath;

    public char[][] layoutArray = readLayout("level1.txt");;

    
    public PImage brickwall;
    public PImage stonewall;
    public PImage wizardleft;
    public PImage wizardright;
    public PImage wizardup;
    public PImage wizarddown;
    public PImage gremlin;

    public Player wizard = new Player(0, 0);

    public Gremlin gremlin1 = new Gremlin(0, 0);
    public Gremlin gremlin2 = new Gremlin(0, 0);
    public Gremlin gremlin3 = new Gremlin(0, 0);
    public Gremlin gremlin4 = new Gremlin(0, 0);
    public Gremlin[] gremlins = {gremlin1, gremlin2, gremlin3, gremlin4};
//    public Gremlin[] gremlins = {gremlin1};

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        // Load images during setup
        this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
        this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));

        wizardleft = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        wizardright = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        wizardup = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        wizarddown = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.gremlin = loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        //this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        //this.fireball = loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        char[][] layoutArray = readLayout("level1.txt");
        wizard.findPlayerPosition(layoutArray);
        findGremlinPosition(layoutArray);

        JSONObject conf = loadJSONObject(new File(this.configPath));

    }

        /**
         * read layoutArray and find all i and j index of 4 occurrence of G and store in a 2D array. Then assign the i and j in the 2d array to the gremlin object.
         */
    public void findGremlinPosition(char[][] layoutArray) {
        int[][] gremlinPosition = new int[4][2];
        int count = 0;
        for (int i = 0; i<layoutArray.length; i++) {
            for (int j = 0; j<layoutArray[i].length; j++) {
                if (layoutArray[i][j] == 'G') {
                    gremlinPosition[count][0] = j;
                    gremlinPosition[count][1] = i;
                    count++;
                }
            }
        }
        for (int i = 0; i<gremlins.length; i++) {
            gremlins[i].setX(gremlinPosition[i][0]*SPRITESIZE);
            gremlins[i].setY(gremlinPosition[i][1]*SPRITESIZE);
        }
    }


         
    

    /**
     * Receive key pressed signal from the keyboard
     * The user must be actively holding the movement key for movement to occur, otherwise movement stops
    */
    @Override
    public void keyPressed(){
        if (key == CODED) {
            if (keyCode == UP) {
                wizard.up();
            } else if (keyCode == DOWN) {
                wizard.down();
            } else if (keyCode == LEFT) {
                wizard.left();
            } else if (keyCode == RIGHT) {
                wizard.right();
            }
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
     * Stop moving when key is released.
    */
    @Override
    public void keyReleased(){
        if (key == CODED) {
            if (keyCode == UP) {
                wizard.playerDirection = "up";
            } else if (keyCode == DOWN) {
                wizard.playerDirection = "down";
            } else if (keyCode == LEFT) {
                wizard.playerDirection = "left";
            } else if (keyCode == RIGHT) {
                wizard.playerDirection = "right";
            }
        }
    }




    public char[][] readLayout(String layout) {
        char[][] map = new char[WIDTH / SPRITESIZE][HEIGHT / SPRITESIZE];
        try {
            File file = new File(layout);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int i = 0;
            while ((st = br.readLine()) != null) {
                for (int j = 0; j < st.length(); j++) {
                    map[i][j] = st.charAt(j);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(191, 153, 114);
        char[][] layoutArray = readLayout("level1.txt");
        drawWall(layoutArray);

        wizard.draw(this);
        gremlin1.draw(this);
        gremlin2.draw(this);
        gremlin3.draw(this);
        gremlin4.draw(this);



    }

        private void drawWall(char[][] layoutArray) {
            for (int i = 0; i < layoutArray.length; i++) {
                for (int j = 0; j < layoutArray[i].length; j++) {
                    if (layoutArray[i][j] == 'X') {
                        image(stonewall, j * SPRITESIZE, i * SPRITESIZE);
                    } else if (layoutArray[i][j] == 'B') {
                        image(brickwall, j* SPRITESIZE, i * SPRITESIZE);
                    }
                }
            }
        }

        public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
