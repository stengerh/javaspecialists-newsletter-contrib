package eu.javaspecialists.newsletter.issue258.contrib;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class ChoosingAccumulatorTest {

    @Test
    void accumulate() {
        new UniformDistributionCheck(6, 2).using((n, k) -> {
            ChoosingAccumulator<Integer> accumulator = new ChoosingAccumulator<>();
            IntStream.rangeClosed(1, n).forEach(i -> accumulator.accumulate(i, k, ThreadLocalRandom.current()));
            return accumulator.toShuffledList(ThreadLocalRandom.current());
        });
    }

    @Test
    void combine() {
        new UniformDistributionCheck(10, 2).using((n, k) -> {
            ChoosingAccumulator<Integer> evenAccumulator = new ChoosingAccumulator<>();
            ChoosingAccumulator<Integer> oddAccumulator = new ChoosingAccumulator<>();
            IntStream.rangeClosed(1, n).forEach(i -> (i % 2 == 0 ? evenAccumulator : oddAccumulator).accumulate(i, k, ThreadLocalRandom.current()));
            return evenAccumulator
                    .combine(oddAccumulator, k, ThreadLocalRandom.current())
                    .toShuffledList(ThreadLocalRandom.current());
        });
    }

    @Test
    void badCombine() {
        new UniformDistributionCheck(6, 2).using((n, k) -> {
            ChoosingAccumulator<Integer> evenAccumulator = new ChoosingAccumulator<>();
            ChoosingAccumulator<Integer> oddAccumulator = new ChoosingAccumulator<>();
            IntStream.rangeClosed(1, n).forEach(i -> (i % 2 == 0 ? evenAccumulator : oddAccumulator).accumulate(i, k, ThreadLocalRandom.current()));
            return evenAccumulator
                    .badCombine(oddAccumulator, k, ThreadLocalRandom.current())
                    .toShuffledList(ThreadLocalRandom.current());
        });
    }
}