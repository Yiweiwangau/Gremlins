package gremlins;

import org.junit.jupiter.api.*;
import processing.core.PApplet;

import static gremlins.App.*;
import static gremlins.App.Direction.*;
import static gremlins.App.GameStatus.*;
import static gremlins.App.GameStatus.Null;
import static org.junit.jupiter.api.Assertions.*;

//generate tests for App.java
class AppTest {

    @Test
    void testGremlinRespawn() {
        //test if gremlin respawn when number of gremlins is  0
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        for (Gremlin g : App.gremlins) {
            g.numberOfLives = 0;
            assertEquals(0, g.numberOfLives);
        }
        app.setUpGremlin();
    }


    @Test
    void testDrawInit() {
        //test if the draw function works when current game status is init
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.curStatus = Init;
        app.draw();
    }
    @Test
    void testDrawWin() {
        //test if the draw function works when current game status is win
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.curStatus = Win;
        app.draw();
    }
    @Test
    void testDrawLose() {
        //test if the draw function works when current game status is lose
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.curStatus = Lose;
        app.draw();
    }
    @Test
    void testDrawNull() {
        //test if the draw function works when current game status is null(set up for testing)
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.curStatus = Null;
        app.draw();
    }
    @Test
    void testDrawGaming() {
        //test if the draw function works when current game status is gaming
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.curStatus = Gaming;
        app.draw();
    }

    // write a test function for the drawPlayer() function
    @Test
    void testDrawPlayer(){
        // test the drawPlayer() function in different directions
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        App.wizard.moveDirection = Up;
        app.drawPlayer();
        app.delay(1000);
        App.wizard.moveDirection = App.Direction.Right;
        app.drawPlayer();
        app.delay(1000);
        App.wizard.moveDirection = App.Direction.Down;
        app.drawPlayer();
        app.delay(1000);
        App.wizard.moveDirection = App.Direction.Left;
    }


    @Test
    void testDrawBrokenWall(){
        // test the if broken wall is drawn by adding some dummy broken wall
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        brokenWalls.add(new BrokenWall(3, 3, 16));
        assertEquals(1, brokenWalls.size());
        app.drawBrokenWall();
        app.delay(1000);
        brokenWalls.add(new BrokenWall(3, 3, 16));
        brokenWalls.remove(0);
        app.drawBrokenWall();
    }

    @Test
    void testKeyPressed(){
        // test the keyPressed() function by pressing different keys
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.key = CODED;
        app.curStatus = App.GameStatus.Lose;
        app.keyPressed();
        assertEquals(App.GameStatus.Init, app.curStatus);
        app.curStatus = Win;
        app.keyPressed();
        assertEquals(App.GameStatus.Init, app.curStatus);
        app.keyCode = UP;
        app.keyPressed();
        assertEquals(Up, App.wizard.moveDirection);
        app.keyCode = RIGHT;
        app.keyPressed();
        assertEquals(App.Direction.Right, App.wizard.moveDirection);
        app.keyCode = DOWN;
        app.keyPressed();
        assertEquals(App.Direction.Down, App.wizard.moveDirection);
        app.keyCode = LEFT;
        app.keyPressed();
        assertEquals(App.Direction.Left, App.wizard.moveDirection);

    }

    @Test
    void testFireball(){
        // test the fireball() function by pressing space button and different cooldowns
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.key = 32;
        App.wizard.cdCount = 10;
        app.keyPressed();
        assertEquals(0, App.fireballs.size());
        // check keyCode pressed is space
        app.key = 32;
        App.wizard.cdCount = 0;
        app.keyPressed();
        assertEquals(1, App.fireballs.size());
        app.key = 31;
        App.wizard.cdCount = 0;
        app.keyPressed();

    }

    @Test
    void testCheckIfWin(){
        // test if game is won when currentlevel is 2 (startlevel is 0)
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.currentLevel = 2;
        app.generateLayout();
        assertEquals(app.curStatus, Win);
    }
    @Test
    void testCheckIfLose(){
        // test if game is lost when number of lives of wizard is 0
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        wizard.numberOfLives = 0;
        app.generateLayout();
        assertEquals(app.curStatus, Lose);
        wizard.numberOfLives = 1;
        app.generateLayout();
    }
    @Test
    void testPowerBarCondition(){
        // check if power bar is drawn when there is power up timer.
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        wizard.powerUpTime = 10;
        wizard.cdCount = 10;
        app.drawBottombar();
    }

    @Test
    void testFireBall(){
        // test if fireball is able to break wall by creating a dummy fireball and wall
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        fireballs.add(new Fireball(3, 3, 1, Up));
        slimes.add(new Slime(3, 3, 1, Up));
        for (Fireball f :
                fireballs) {
            f.breakBrickWall(f.x, f.y);
            f.crashOther();
        }
    }
    @Test
    void testBeAttacked(){
        // check if checkStatus() function changes the beAttacked status of wizard
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        beAttacked = true;
        app.checkStatus();
        assertFalse(beAttacked);
    }
    @Test
    void testPowerUp() {
        // test powerup behaviour by checking existing powerup picked up by wizard;also test if function picks up when powerup is null
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        beAttacked = false;
        app.setup();
        app.delay(1000);
        app.checkStatus();
        wizard.x = app.powerUpObj.powerUpX * SPRITESIZE;
        wizard.y = app.powerUpObj.powerUpY * SPRITESIZE;
        app.checkStatus();
        assertEquals(wizard.powerUpTime, app.powerUpObj.duration);
        app.powerUpObj = null;
        app.checkStatus();
    }
    @Test
    void testDoorContact() {
        // test when player touches door
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.currentLevel = 1;
        beAttacked = false;
        wizard.x = app.doorObj.doorX * SPRITESIZE;
        wizard.y = app.doorObj.doorX * SPRITESIZE;
        app.draw();
    }

    @Test
    void testDrawPowerUpNull() {
        // test what if drawPowerUp() function works when powerup is null
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.powerUpObj = null;
        app.drawPowerup();
    }
    @Test
    void testDrawDoorNotNull() {
        // test if drawStationaryObjects function works when door is null
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[]{"App"}, app);
        app.setup();
        app.doorObj = null;
        app.drawStationaryObjects();
    }
    @Test
    void testDrawPlayerWhenDefault() {
        // test if drawPlayer() function works when wizard's moveDirection is default(set up just for testing)
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        wizard.moveDirection = null;
        assertThrows(NullPointerException.class, () -> app.drawPlayer());
    }
    @Test
    void testGremlinContact() {
        // test if gremlin is able to contact with wizard
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        for (Gremlin g :
                gremlins) {
            wizard.x = g.getXi() * SPRITESIZE;
            wizard.y = g.getYi() * SPRITESIZE;
        }
        app.checkStatus();
    }


}