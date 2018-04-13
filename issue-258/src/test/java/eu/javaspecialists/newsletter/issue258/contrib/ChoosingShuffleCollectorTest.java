package eu.javaspecialists.newsletter.issue258.contrib;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChoosingShuffleCollectorTest {

    @Test
    void uniformDistribution() {
        new UniformDistributionCheck(4, 3).using(this::choose);
    }

    @Test
    void limitLowerThanStreamLength() {
        List<Integer> chosen = choose(3, 4);
        assertEquals(3, chosen.size());
    }

    @Test
    void limitHigherThanStreamLength() {
        List<Integer> chosen = choose(4, 3);
        assertEquals(3, chosen.size());
    }

    private List<Integer> choose(int n, int k) {
        return IntStream.rangeClosed(1, n).boxed().collect(ChoosingShuffleCollector.chooseAtMost(k));
    }

}
