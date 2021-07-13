package ru.netology.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.domain.Issue;
import ru.netology.domain.NotFoundException;
import ru.netology.repository.IssueRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class CRUDIssueManagerTest {
    //@Mock
    private IssueRepository repository = new IssueRepository();

    // @InjectMocks
    private IssueManager manager = new IssueManager(repository);
    private Issue issue1 = new Issue(1, "Issue1", true, "Author1", Set.of("bug", "new"), "Project1", Set.of("Milestone"), "Assignee1", 2);
    private Issue issue2 = new Issue(2, "Issue2", false, "Author1", Set.of("bug", "test"), "Project4", Set.of("Milestone1"), "Assignee1", 2);
    private Issue issue3 = new Issue(3, "Issue3", true, "Author3", Set.of("bug", "new", "test"), "Project1", Set.of("Milestone2"), "Assignee1", 3);
    private Issue issue4 = new Issue(4, "Issue4", false, "Author2", Set.of("bug"), "Project2", Set.of("Milestone3"), "Assignee1", 2);
    private Issue issue5 = new Issue(5, "Issue5", true, "Author1", Set.of("bug", "new"), "Project1", Set.of("Milestone4"), "Assignee1", 8);
    private Issue issue6 = new Issue(6, "Issue6", false, "Author3", Set.of("bug", "open"), "Project3", Set.of("Milestone1"), "Assignee1", 2);
    private Issue issue7 = new Issue(7, "Issue7", true, "Author1", Set.of("new"), "Project1", Set.of("Milestone"), "Assignee1", 10);

    @Nested
    class WhenRepoIsEmpty {

        @Test
        void add() {
            manager.add(issue1);

            Collection<Issue> expected = List.of(issue1);
            Collection<Issue> actual = repository.getAll();

            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void getAllOpen() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.getAllOpen();

            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void getAllClosed() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.getAllClosed();

            Assertions.assertIterableEquals(expected, actual);
        }
    }

    @Nested
    class WhenOneInside {
        @BeforeEach
        void setUp() {
            manager.add(issue1);
        }

        @Test
        void add() {
            manager.add(issue2);

            Collection<Issue> expected = List.of(issue1, issue2);
            Collection<Issue> actual = manager.getAll();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void getAllOpen() {
            Collection<Issue> expected = List.of(issue1);
            Collection<Issue> actual = manager.getAllOpen();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void getAllClosed() {
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.getAllClosed();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void removeWhenExist() {
            manager.remove(issue1);

            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.getAll();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void removeWhenNotExist() {
            manager.remove(issue2);

            Collection<Issue> expected = List.of(issue1);
            Collection<Issue> actual = manager.getAll();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void updateWhenExist() {
            manager.updateIssue(1);

            boolean actual = issue1.isOpen();
            Assertions.assertFalse(actual);
        }

        @Test
        void updateWhenNotExist() {

            Assertions.assertThrows(NotFoundException.class, () -> manager.updateIssue(2));
        }

    }

    @Nested
    public class WhenMoreThenOneInside{

        @BeforeEach
        void setUp(){
            manager.add(issue1);
            manager.add(issue2);
            manager.add(issue3);
            manager.add(issue4);
            manager.add(issue5);
            manager.add(issue6);
        }

        @Test
        void shouldAdd(){
            manager.add(issue7);

            Collection<Issue> expected = List.of(issue1, issue2, issue3, issue4, issue5,issue6, issue7);
            Collection<Issue> actual = manager.getAll();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldGetAll(){
            Collection<Issue> expexted = List.of(issue1, issue2, issue3, issue4, issue5, issue6);
            Collection<Issue> actual = manager.getAll();
            Assertions.assertIterableEquals(expexted, actual);
        }

        @Test
        void shouldRemoveWhenExist(){
            manager.remove(issue3);

            Collection<Issue> expexted = List.of(issue1, issue2, issue4, issue5, issue6);
            Collection<Issue> actual = manager.getAll();
            Assertions.assertIterableEquals(expexted, actual);
        }

        @Test
        void shouldGetAllOpen(){
            Collection<Issue> expected = List.of(issue1, issue3, issue5);
            Collection<Issue> actual = manager.getAllOpen();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldGetAllClosed(){
            Collection<Issue> expected  =List.of(issue2, issue4, issue6);
            Collection<Issue> actual = manager.getAllClosed();
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldFilterByAuthorWhenExist(){
            Collection<Issue> expected = List.of(issue1, issue2, issue5);
            Collection<Issue> actual = manager.filterByAuthor("Author1");
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldFilterByAuthorWhenNotExist(){
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAuthor("Author");
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldFilterByALabelWhenExist(){
            Collection<Issue> expected = List.of(issue1, issue3, issue5);
            Collection<Issue> actual = manager.filterByLabel(Set.of("bug", "new"));
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldFilterByLabelWhenNotExist(){
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByLabel(Set.of("Author"));
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldFilterByAssigneeWhenExist(){
            Collection<Issue> expected = List.of(issue1, issue2, issue3, issue4, issue5, issue6);
            Collection<Issue> actual = manager.filterByAssignee("Assignee1");
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldFilterByAssigneeWhenNotExist(){
            Collection<Issue> expected = List.of();
            Collection<Issue> actual = manager.filterByAssignee("Assignee2");
            Assertions.assertIterableEquals(expected, actual);
        }

        @Test
        void shouldUpdateWhenOpen(){
            manager.updateIssue(3);

            boolean actual = issue3.isOpen();
            Assertions.assertFalse(actual);
        }

        @Test
        void shouldUpdateWhenClosed(){
            manager.updateIssue(4);

            boolean actual = issue4.isOpen();
            Assertions.assertTrue(actual);
        }

        @Test
        void updateWhenNotExist() {

            Assertions.assertThrows(NotFoundException.class, () -> manager.updateIssue(7));
        }
    }
    }