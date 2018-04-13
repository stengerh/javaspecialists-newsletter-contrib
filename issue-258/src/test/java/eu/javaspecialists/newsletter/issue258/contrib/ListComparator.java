package eu.javaspecialists.newsletter.issue258.contrib;

import java.util.Comparator;
import java.util.List;

public class ListComparator<T> implements Comparator<List<T>> {

    private final Comparator<T> elementComparator;

    public static <T extends Comparable<? super T>> ListComparator<T> naturalElementOrder() {
        return new ListComparator<>(Comparator.<T>naturalOrder());
    }

    public ListComparator(Comparator<T> elementComparator) {
        this.elementComparator = elementComparator;
    }

    @Override
    public int compare(List<T> o1, List<T> o2) {
        int index = 0;
        while (index < o1.size() && index < o2.size()) {
            int elementComparison = elementComparator.compare(o1.get(index), o2.get(index));
            if (elementComparison != 0) {
                return elementComparison;
            }
            index++;
        }
        return Integer.compare(o1.size(), o2.size());
    }
}
