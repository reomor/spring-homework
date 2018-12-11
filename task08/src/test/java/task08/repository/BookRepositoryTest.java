package task08.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task08.domain.Book;
import task08.domain.Comment;
import task08.domain.Genre;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    public void saveBook_ShouldSuccess_SaveNewBook() {
        Book expected = getBook();
        Book actual = repository.save(expected);
        assertNotNull(actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    public void updateBook_ShouldSuccess_UpdateExistingBook() {
        final Genre genre = new Genre("updated", "updated book genre");
        Book book = getBook();
        Book expected = repository.save(book);
        expected.setGenre(genre);
        repository.save(expected);
        Book actual = repository.findById(expected.getId()).orElseThrow(() -> new RuntimeException("No book in repo"));
        assertEquals(genre, actual.getGenre());
        assertEquals(expected, actual);
    }

    @Test
    public void deleteBook_ShouldSuccess_DeleteBook() {
        Book book = getBook();
        book = repository.save(book);
        repository.deleteById(book.getId());
        Optional<Book> bookOptional = repository.findById(book.getId());
        assertFalse(bookOptional.isPresent());
    }

    @Test
    public void findById_ShouldSuccess_FindBookById() {
        Book book = getBook();
        Book expected = repository.save(book);
        Optional<Book> bookOptional = repository.findById(expected.getId());
        assertTrue(bookOptional.isPresent());
        assertEquals(expected, bookOptional.get());
    }

    @Test
    public void findAll_ShouldSuccess_FindAllBooks() {
        Book book = getBook();
        repository.save(book);
        List<Book> all = repository.findAll();
        assertThat(all).isNotEmpty().contains(book);
    }

    @Test
    public void commentBook_ShouldSuccess_CommentABook() {
        Book book = getBook();
        Book actual = repository.save(book);
        List<Comment> repositoryComments = repository.getComments(actual.getId());
        Comment comment = new Comment("new comment", LocalDateTime.now());
        repository.setComment(book.getId(), comment);
        List<Comment> actualComments = repository.getComments(book.getId());
        assertThat(actualComments).contains(comment);
        assertEquals(repositoryComments.size(), actualComments.size() - 1);
    }

    @Test
    public void getComments_ShouldSuccess_GetAllBookComments() {
        Book book = getBook();
        Book expected = repository.save(book);
        List<Comment> comments = repository.getComments(expected.getId());
        assertThat(comments).isNotEmpty().contains(book.getComments().get(0));
    }

    private Book getBook() {
        Genre genre = new Genre("Genre", "Genre");
        Comment comment = new Comment("Like a shit", LocalDateTime.now());
        Comment comment2 = new Comment("Like a shit2", LocalDateTime.now());
        return new Book("Title", genre, "ISBN-01", "Description", Collections.emptyList(), Arrays.asList(comment, comment2));
    }
}