package eu.javaspecialists.newsletter.issue258.contrib;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class ChoosingCollectorTest {
    @Test
    void uniformDistribution() {
        new ExhaustiveUniformDistributionCheck(5, 3)
                .combinations((n, k, randomSupplier) ->
                        IntStream.rangeClosed(1, n)
                                .boxed()
                                .collect(ChoosingCollector.chooseAtMost(k, randomSupplier)));
    }
}