package ru.netology.manager;

import ru.netology.domain.Issue;
import ru.netology.domain.NotFoundException;
import ru.netology.repository.IssueRepository;

import java.util.*;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    public void add(Issue issue) {
        repository.add(issue);
    }

    public Collection<Issue> getAll() {
        return repository.getAll();
    }

    public void remove(Issue issue) {
        repository.remove(issue);
    }

    public Collection<Issue> getAllOpen() {
        Collection<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.isOpen())
                result.add(issue);
        }
        return result;
    }

    public Collection<Issue> getAllClosed() {
        Collection<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (!issue.isOpen())
                result.add(issue);
        }
        return result;
    }

    public Collection<Issue> filterByAuthor(String text) {
        Collection<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getAuthor().equalsIgnoreCase(text)) {
                result.add(issue);
            }
        }
        return result;
    }

    public Collection<Issue> filterByLabel(Set<String> labels) {
        Collection<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getLabel().containsAll(labels)) {
                result.add(issue);
            }
        }
        return result;
    }

    public Collection<Issue> filterByAssignee(String text) {
        Collection<Issue> result = new ArrayList<>();
        for (Issue issue : repository.getAll()) {
            if (issue.getAssignee().equalsIgnoreCase(text)) {
                result.add(issue);
            }
        }
        return result;
    }

    public void updateIssue(int id) {
        if (repository.findById(id) == null) {
            throw new NotFoundException(String.format("Issue with id %s not found", id));
        }
        Issue foundIssue = repository.findById(id);
        if (foundIssue.isOpen()) {
            foundIssue.setOpen(false);
        } else {
            foundIssue.setOpen(true);
        }
    }

    public Collection<Issue> sortByCreateDaysAgo(){
        List<Issue> result = new ArrayList<>(repository.getAll());
        Collections.sort(result);
        return result;
    }

}
