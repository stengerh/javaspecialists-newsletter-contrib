package eu.javaspecialists.newsletter.issue258.contrib;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Collects a shuffled list of limited size from a finite stream.
 */
public class ChoosingShuffleCollector {
    public static <T> Collector<T, ?, List<T>> chooseAtMost(int limit) {
        return chooseAtMost(limit, ThreadLocalRandom::current);
    }

    public static <T> Collector<T, ?, List<T>> chooseAtMost(int limit, Supplier<? extends Random> randomSupplier) {
        return Collector.of(
                ChoosingAccumulator::new,
                (ChoosingAccumulator<T> acc, T obj) -> acc.accumulate(obj, limit, randomSupplier.get()),
                (lhs, rhs) -> lhs.combine(rhs, limit, randomSupplier.get()),
                acc -> acc.toShuffledList(randomSupplier.get()),
                Collector.Characteristics.UNORDERED
        );
    }

}
