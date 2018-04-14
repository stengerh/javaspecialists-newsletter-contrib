package eu.javaspecialists.newsletter.issue258.contrib;

import org.apache.commons.math3.fraction.BigFraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TracedRandomTest {
    @Test
    void oneLevel() {
        TracedRandom random = new TracedRandom();
        assertEquals(0, random.nextInt(2));
        assertTrue(random.hasNextTrace());
        random.nextTrace();
        assertEquals(1, random.nextInt(2));
        assertFalse(random.hasNextTrace());
    }

    @Test
    void twoLevels() {
        TracedRandom random = new TracedRandom();
        assertEquals(0, random.nextInt(2));
        assertEquals(0, random.nextInt(2));
        assertTrue(random.hasNextTrace());
        random.nextTrace();
        assertEquals(0, random.nextInt(2));
        assertEquals(1, random.nextInt(2));
        assertTrue(random.hasNextTrace());
        random.nextTrace();
        assertEquals(1, random.nextInt(2));
        assertEquals(0, random.nextInt(2));
        assertTrue(random.hasNextTrace());
        random.nextTrace();
        assertEquals(1, random.nextInt(2));
        assertEquals(1, random.nextInt(2));
        assertFalse(random.hasNextTrace());
    }

    @Test
    void probability() {
        TracedRandom random = new TracedRandom();
        random.nextInt(4);
        random.nextInt(5);
        assertEquals(new BigFraction(1, 20), random.probability());
    }
}