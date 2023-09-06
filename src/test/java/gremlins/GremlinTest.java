package gremlins;

import org.junit.jupiter.api.*;
import processing.core.PApplet;

import static gremlins.App.*;

class GremlinTest {

    @Test
    void testFireSlime() {
        // test if fireslime function works when cd counter is the edge case
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        for (Gremlin g : gremlins) {
            g.cdCount = 10;
            g.fireSlime();
        }

    }

    @Test
    void testGremlinMove(){
        App app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        for (Gremlin g : gremlins) {
            int x = g.getXi();
            int y = g.getYi();
            map[y - 1][x] = 'B';
            map[y + 1][x] = 'B';
            map[y][x - 1] = 'B';
            map[y][x + 1] = 'B';
            g.move();
        }
    }

}