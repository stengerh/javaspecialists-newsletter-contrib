package eu.javaspecialists.newsletter.issue258.contrib;

import org.apache.commons.math3.fraction.BigFraction;

import java.util.ArrayList;
import java.util.Random;

public class TracedRandom extends Random {

    private ArrayList<NextInt> choices;
    private int nextChoice;

    public TracedRandom() {
        choices = new ArrayList<>();
        nextChoice = 0;
    }

    public long inverseProbability() {
        long denominator = 1;
        for (int index = 0; index < nextChoice; ++index) {
            NextInt choice = choices.get(index);
            denominator *= choice.bound;
        }
        return denominator;
    }

    public BigFraction probability() {
        BigFraction probability = BigFraction.ONE;
        for (int index = 0; index < nextChoice; ++index) {
            NextInt choice = choices.get(index);
            probability = probability.divide(choice.bound);
        }

        return probability;
    }

    public boolean hasNextTrace() {
        if (choices.isEmpty()) {
            return true;
        }
        int index = nextChoice - 1;
        while (index >= 0) {
            NextInt choice = choices.get(index);
            if (choice.hasNext()) {
                return true;
            }
            index -= 1;
        }
        return false;
    }

    public void nextTrace() {
        if (nextChoice != choices.size()) {
            throw new NondeterministicExecutionException();
        }
        int index = nextChoice - 1;
        while (index >= 0) {
            NextInt choice = choices.get(index);
            if (choice.hasNext()) {
                choice.next();
                break;
            }
            index -= 1;
        }
        truncate(index + 1);
        nextChoice = 0;
    }

    @Override
    protected int next(int bits) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int nextInt(int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }

        NextInt choice;
        if (nextChoice == choices.size()) {
            choice = new NextInt(bound);
            choices.add(choice);
        } else {
            choice = choices.get(nextChoice);
            if (choice.bound != bound) {
                throw new NondeterministicExecutionException();
            }
        }
        nextChoice += 1;
        return choice.current();
    }

    private void truncate(int size) {
        if (size < choices.size()) {
            choices = new ArrayList<>(choices.subList(0, size));
        }
    }

    private static class NextInt {
        private final int bound;
        private int value;

        private NextInt(int bound) {
            this.bound = bound;
            this.value = 0;
        }

        private boolean hasNext() {
            return value < bound - 1;
        }

        private int next() {
            value += 1;
            return value;
        }

        private int current() {
            return value;
        }
    }
}
