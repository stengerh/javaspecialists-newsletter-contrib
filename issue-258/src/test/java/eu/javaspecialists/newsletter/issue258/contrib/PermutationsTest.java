package eu.javaspecialists.newsletter.issue258.contrib;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

class PermutationsTest {

    @Test
    void pickFromMutableRandomAccessList() {
        new UniformDistributionCheck(4, 3).using((n, k) -> {
            List<Integer> list = IntStream.rangeClosed(1, n)
                    .boxed()
                    .collect(toCollection(ArrayList::new));
            return Permutations.chooseFromMutableRandomAccessList(list, k, ThreadLocalRandom.current());
        });
    }

    @Test
    void pickFromList() {
        new UniformDistributionCheck(4, 3).using((n, k) -> {
            List<Integer> list = IntStream.rangeClosed(1, k)
                    .boxed()
                    .collect(collectingAndThen(
                            toCollection(LinkedList::new),
                            Collections::unmodifiableList));
            return Permutations.chooseFromList(list, k, ThreadLocalRandom.current());
        });
    }

    @Test
    void pickFromIterable() {
        new UniformDistributionCheck(4, 3).using((n, k) -> {
            Set<Integer> list = IntStream.rangeClosed(1, k)
                    .boxed()
                    .collect(toSet());
            return Permutations.chooseFromIterable(list, k, ThreadLocalRandom.current());
        });
    }
}