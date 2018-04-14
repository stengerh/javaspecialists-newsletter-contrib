package eu.javaspecialists.newsletter.issue258.contrib;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class ChoosingAccumulatorTest {

    @Test
    void accumulate() {
        new ExhaustiveUniformDistributionCheck(6, 2).permutations((n, k, randomSupplier) -> {
            ChoosingAccumulator<Integer> accumulator = new ChoosingAccumulator<>();
            IntStream.rangeClosed(1, n).forEach(i -> accumulator.accumulate(i, k, randomSupplier.get()));
            return accumulator.toShuffledList(randomSupplier.get());
        });
    }

    @Test
    void combine() {
        new ExhaustiveUniformDistributionCheck(10, 2).permutations((n, k, randomSupplier) -> {
            ChoosingAccumulator<Integer> evenAccumulator = new ChoosingAccumulator<>();
            ChoosingAccumulator<Integer> oddAccumulator = new ChoosingAccumulator<>();
            IntStream.rangeClosed(1, n).forEach(i -> (i % 2 == 0 ? evenAccumulator : oddAccumulator).accumulate(i, k, randomSupplier.get()));
            return evenAccumulator
                    .combine(oddAccumulator, k, randomSupplier.get())
                    .toShuffledList(randomSupplier.get());
        });
    }

    @Test
    void badCombine() {
        new ExhaustiveUniformDistributionCheck(6, 2).permutations((n, k, randomSupplier) -> {
            ChoosingAccumulator<Integer> evenAccumulator = new ChoosingAccumulator<>();
            ChoosingAccumulator<Integer> oddAccumulator = new ChoosingAccumulator<>();
            IntStream.rangeClosed(1, n).forEach(i -> (i % 2 == 0 ? evenAccumulator : oddAccumulator).accumulate(i, k, randomSupplier.get()));
            return evenAccumulator
                    .badCombine(oddAccumulator, k, randomSupplier.get())
                    .toShuffledList(randomSupplier.get());
        });
    }
}