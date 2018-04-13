package eu.javaspecialists.newsletter.issue258;

import java.util.Objects;

public class Newsletter {
    private final String issue;

    public Newsletter(String issue) {
        this.issue = issue;
    }

    public String getIssue() {
        return issue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newsletter that = (Newsletter) o;
        return Objects.equals(issue, that.issue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issue);
    }

    @Override
    public String toString() {
        return "Newsletter{" +
                "issue='" + issue + '\'' +
                '}';
    }
}
