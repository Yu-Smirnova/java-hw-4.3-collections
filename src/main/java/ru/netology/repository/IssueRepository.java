package ru.netology.repository;

import ru.netology.domain.Issue;

import java.util.ArrayList;
import java.util.Collection;

public class IssueRepository {
    private Collection<Issue> issues = new ArrayList<>();

    public boolean add(Issue issue) {
        return issues.add(issue);
    }

    public boolean remove(Issue issue) {
        return issues.remove(issue);
    }

    public Collection<Issue> getAll() {
        return issues;
    }

    public Issue findById(int id) {
        for (Issue issue : issues) {
            if (issue.getId() == id) {
                return issue;
            }
        }
        return null;
    }
}
