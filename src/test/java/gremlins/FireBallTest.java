package gremlins;

import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import static gremlins.App.*;

//generate tests for App.java
class FireBallTest {

    @Test
    void testFireBall() {
        //test if fireball function works when Y is 0
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        for (Fireball f : fireballs) {
            f.y = 0;
        }
    }

    @Test
    void testAttack() {
        // test if fireball can contact with slime
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        for (Slime s : slimes) {
            s.x = 10;
            s.y = 10;
        }

        for (Fireball f : fireballs) {
            f.y = 10;
            f.x = 10;
            f.contact();
        }

    }


}

