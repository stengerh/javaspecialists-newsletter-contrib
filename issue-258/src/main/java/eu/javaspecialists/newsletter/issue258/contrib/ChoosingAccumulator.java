package eu.javaspecialists.newsletter.issue258.contrib;

import java.util.*;

public class ChoosingAccumulator<T> {
    private List<T> taken;
    private int seen;

    public ChoosingAccumulator() {
        taken = new ArrayList<>();
        seen = 0;
    }

    public void accumulate(T obj, int limit, Random random) {
        seen += 1;
        if (taken.size() < limit) {
            taken.add(obj);
        } else {
            int index = random.nextInt(seen);
            if (index < limit) {
                taken.set(index, obj);
            }
        }
    }

    public ChoosingAccumulator<T> combine(ChoosingAccumulator<T> other, int limit, Random random) {
        ChoosingAccumulator<T> combined = new ChoosingAccumulator<>();
        combined.seen = this.seen + other.seen;

        while (combined.taken.size() < limit && !(this.taken.isEmpty() && other.taken.isEmpty())) {
            combined.drain(this.or(other, random), random);
        }

        return combined;
    }

    private void drain(ChoosingAccumulator<T> other, Random random) {
        int index = random.nextInt(other.taken.size());
        taken.add(other.taken.remove(index));
        other.seen -= 1;
    }

    private ChoosingAccumulator<T> or(ChoosingAccumulator<T> other, Random random) {
        return (random.nextInt(this.seen + other.seen) < this.seen) ? this : other;
    }

    public Set<T> toSet() {
        return new TreeSet<>(taken);
    }

    public List<T> toShuffledList(Random random) {
        Collections.shuffle(taken, random);
        return taken;
    }
}
