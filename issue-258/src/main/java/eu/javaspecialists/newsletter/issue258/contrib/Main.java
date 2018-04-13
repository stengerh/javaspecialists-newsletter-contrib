package eu.javaspecialists.newsletter.issue258.contrib;

import eu.javaspecialists.newsletter.issue258.Newsletter;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) {
        new Main().getRandomRelatedNewsletters("258", "Tips and Tricks", 5).forEach(System.out::println);
    }

    public Collection<Newsletter> getRandomRelatedNewsletters(
            String issue, String category, int numberOfNewsletters) {
        return getNewsletters(category).stream()
                .filter(newsletter -> !newsletter.getIssue().equals(issue))
                .collect(ChoosingShuffleCollector.chooseAtMost(numberOfNewsletters));
    }

    private Collection<Newsletter> getNewsletters(String category) {
        Random random = ThreadLocalRandom.current();
        int count = random.nextInt(19) + 1;
        return random.ints(count, 1, 1000)
                .mapToObj(String::valueOf)
                .map(Newsletter::new)
                .collect(toList());
    }
}
