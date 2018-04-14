package eu.javaspecialists.newsletter.issue258.contrib;

import org.apache.commons.math3.fraction.BigFraction;

import java.util.*;
import java.util.function.Supplier;

import static org.apache.commons.math3.util.CombinatoricsUtils.binomialCoefficient;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExhaustiveUniformDistributionCheck {
    private final int n;
    private final int k;


    public ExhaustiveUniformDistributionCheck(int n, int k) {
        this.n = n;
        this.k = k;
    }

    public <T> void combinations(CombinationSampler<T> sampler) {
        TracedRandom random = new TracedRandom();

        int expectedUniqueObservations = Math.toIntExact(binomialCoefficient(n, k));

        Map<Set<T>, BigFraction> probabilityByObservation = new HashMap<>();

        while (random.hasNextTrace()) {
            random.nextTrace();

            Set<T> observation = sampler.sample(n, k, () -> random);

            BigFraction probability = random.probability();

            probabilityByObservation.merge(observation, probability, BigFraction::add);
        }

        assertEquals(expectedUniqueObservations, probabilityByObservation.size());

        BigFraction expectedProbability = new BigFraction(1, expectedUniqueObservations);
        assertAll(probabilityByObservation.entrySet().stream().map(entry ->
                () -> assertEquals(expectedProbability, entry.getValue(), "probabiliby of " + entry.getKey())));
    }

    public <T> void permutations(PermutationSampler<T> sampler) {
        TracedRandom random = new TracedRandom();

        int expectedUniqueObservations = Math.toIntExact(binomialCoefficient(n, k) * factorial(k));

        Map<List<T>, BigFraction> probabilityByObservation = new HashMap<>();

        while (random.hasNextTrace()) {
            random.nextTrace();

            List<T> observation = sampler.sample(n, k, () -> random);

            BigFraction probability = random.probability();

            probabilityByObservation.merge(observation, probability, BigFraction::add);
        }

        assertEquals(expectedUniqueObservations, probabilityByObservation.size());

        BigFraction expectedProbability = new BigFraction(1, expectedUniqueObservations);
        assertAll(probabilityByObservation.entrySet().stream().map(entry ->
                () -> assertEquals(expectedProbability, entry.getValue(), "probabiliby of " + entry.getKey())));
    }

    @FunctionalInterface
    public interface CombinationSampler<T> {
        Set<T> sample(int n, int k, Supplier<? extends Random> randomSupplier);
    }

    @FunctionalInterface
    public interface PermutationSampler<T> {
        List<T> sample(int n, int k, Supplier<? extends Random> randomSupplier);
    }
}
