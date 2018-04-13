package eu.javaspecialists.newsletter.issue258.contrib;

import java.util.*;

public class Permutations {
    public static <T> List<T> chooseFromMutableRandomAccessList(List<T> list, int limit, Random random) {
        int size = list.size();
        int chosen = 0;
        while (chosen < limit && chosen < size) {
            int index = random.nextInt(size - chosen);
            Collections.swap(list, chosen, chosen + index);
            chosen += 1;
        }
        return new ArrayList<>(list.subList(0, chosen));
    }

    public static <T> List<T> chooseFromList(List<T> list, int limit, Random random) {
        int remaining = list.size();
        List<T> chosen = new ArrayList<>();

        Iterator<T> it = list.iterator();
        while (chosen.size() < limit && it.hasNext()) {
            T obj = it.next();
            int x = random.nextInt(remaining);
            if (x < limit - chosen.size()) {
                chosen.add(obj);
            }
            remaining -= 1;
        }
        Collections.shuffle(chosen, random);
        return chosen;
    }

    public static <T> List<T> chooseFromIterable(Iterable<T> list, int limit, Random random) {
        List<T> chosen = new ArrayList<>();
        int seen = 0;

        for (T obj : list) {
            seen += 1;
            if (chosen.size() < limit) {
                chosen.add(obj);
            } else {
                int index = random.nextInt(seen);
                if (index < limit) {
                    chosen.set(index, obj);
                }
            }
        }
        Collections.shuffle(chosen, random);
        return chosen;
    }
}
