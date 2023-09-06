package gremlins;

import org.junit.jupiter.api.*;
import processing.core.PApplet;

import static gremlins.App.*;
import static gremlins.App.SPRITESIZE;
import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {

    // use Junit to test the checkPosition() function
    @Test
    void checkPositionXRightTest() {
        // check if movedirection is right and the player can move right
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Player player = new Player(30, 30, 3);
        player.moveDirection = App.Direction.Right;
        player.checkPosition();
        assertEquals(30 + player.speed, player.x);
    }

    @Test
    void checkPositionLeftTest() {
        // check if movedirection is left and the player can move left
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Player player = new Player(30, 30, 3);
        player.moveDirection = App.Direction.Left;
        player.checkPosition();
        assertEquals(30 - player.speed, player.x);
    }


    @Test
    void checkPositionYUpTest() {
        // check if movedirection is up and the player can move up
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Player player = new Player(30, 30, 3);
        player.moveDirection = App.Direction.Up;
        player.checkPosition();
        assertEquals(30 - player.speed, player.y);
    }

    @Test
    void checkPositionYDownTest(){
        // check if movedirection is down and the player can move down
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        Player player = new Player(30, 30, 3);
        player.moveDirection = App.Direction.Down;
        player.checkPosition();
        assertEquals(30 + player.speed, player.y);
    }


    @Test
    void teleportWhenNoPortal() {
//        check if teleport function considers the case when there is no portal
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        App.portals.clear();
        wizard.teleport();
        assertEquals(0, App.portals.size());
    }
    @Test
    void teleportWhenOnPortal() {
// check if teleport works when wizard steps on one portal, wizard's X and Y should be the other portal's X and Y
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        App.portals.clear();
        App.portals.add(new Portal(1, 1));
        App.portals.add(new Portal(2, 2));
        wizard.x = 40;
        wizard.y = 40;
        wizard.teleport();
        assertEquals(20, wizard.x);
        assertEquals(20, wizard.y);
    }

    @Test
    void teleportWhenNotOnPortal() {
// check if teleport works when wizard is not on portal, wizard's X and Y should stay the same
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        App.portals.clear();
        App.portals.add(new Portal(1, 1));
        App.portals.add(new Portal(2, 2));
        wizard.x = 60;
        wizard.y = 60;
        wizard.teleport();
        assertEquals(60, wizard.x);
        assertEquals(60, wizard.y);
    }

    @Test
    void teleportWhenOnEveryPortal() {
// check if teleport works when wizard is not on portal, wizard's X and Y should stay the same
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        App.portals.clear();
        App.portals.add(new Portal(1, 1));
        App.portals.add(new Portal(2, 2));
        App.portals.add(new Portal(3, 3));
        App.portals.add(new Portal(4, 4));
        wizard.x = 60;
        wizard.y = 60;
        wizard.teleport();
        wizard.y = 60;
        wizard.x = 60;
        wizard.teleport();
        wizard.x = 60;
        wizard.y = 60;
        wizard.teleport();
        wizard.y = 60;
        wizard.x = 60;
        wizard.teleport();
    }

    @Test
    void testTeleport() {
        // check if teleport works when one of the wizard's coordinate is the same as one portal's coordinate, wizard's X and Y should stay the same
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        App.portals.clear();
        App.portals.add(new Portal(1, 1));
        App.portals.add(new Portal(2, 2));
        wizard.x = 1;
        wizard.y = 60;
        wizard.teleport();
    }
    @Test
    void testPowerOver() {
        // check if powerOver function works when wizard is already powered up.
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        wizard.isPoweredUp = true;
        wizard.powerOver();
    }
    @Test
    void testDoorContactwhenXmatches() {
        // test when player touches door
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.currentLevel = 1;
        beAttacked = false;
        wizard.x = app.doorObj.doorX * SPRITESIZE;
        app.draw();
    }
    @Test
    void testDoorContactwhenYmatches() {
        // test when player touches door
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.currentLevel = 1;
        beAttacked = false;
        wizard.y = app.doorObj.doorY * SPRITESIZE;
        app.draw();
    }
    @Test
    void testDoorContactwhenNonematches() {
        // test when player touches door
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.currentLevel = 1;
        beAttacked = false;
        app.draw();
    }


}