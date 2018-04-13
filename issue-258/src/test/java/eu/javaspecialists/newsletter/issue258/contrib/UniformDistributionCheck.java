package eu.javaspecialists.newsletter.issue258.contrib;

import org.apache.commons.math3.fraction.BigFraction;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UniformDistributionCheck {
    private final int n;
    private final int k;

    public UniformDistributionCheck(int n, int k) {
        this.n = n;
        this.k = k;
    }

    public void using(BiFunction<Integer, Integer, List<Integer>> sampler) {
        int expectedUniquePermutations = Math.toIntExact(binomialCoefficient(n, k) * factorial(k));
        long samples = 1000L * expectedUniquePermutations;

        Map<List<Integer>, Long> countByPermutation = LongStream.range(0, samples)
                .boxed()
                .collect(groupingBy(sample -> sampler.apply(n, k), counting()));

        System.out.println(getSummaryStatistics(countByPermutation));

        countByPermutation.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey, ListComparator.naturalElementOrder()))
                .forEach(System.out::println);

        assertEquals(expectedUniquePermutations, countByPermutation.size(), "number of unique permutations");

        double alpha = 0.05;
        assertFalse(TestUtils.chiSquareTest(
                expectedFrequency(expectedUniquePermutations, samples),
                observedFrequency(expectedUniquePermutations, countByPermutation.values()),
                alpha),
                "rejected null hypothesis 'permutations are uniformly distributed' with " + (100 * (1 - alpha)) + "% confidence");

    }

    private SummaryStatistics getSummaryStatistics(Map<List<Integer>, Long> countByPermutation) {
        SummaryStatistics summaryStatistics = new SummaryStatistics();
        countByPermutation.values().stream()
                .mapToDouble(Long::doubleValue)
                .forEach(summaryStatistics::addValue);
        return summaryStatistics;
    }

    private double[] expectedFrequency(int categories, long totalObservations) {
        double f = new BigFraction(categories, totalObservations).doubleValue();
        double[] fs = new double[categories];
        Arrays.fill(fs, f);
        return fs;
    }

    private <T> long[] observedFrequency(int categories, Collection<Long> observations) {
        long[] fs = new long[categories];
        int index = 0;
        for (Long observation : observations) {
            fs[index] = observation;
            index += 1;
        }
        return fs;
    }
}
