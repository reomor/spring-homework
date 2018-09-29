package task10.nosql.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task10.nosql.domain.Book;
import task10.nosql.domain.Comment;
import task10.nosql.domain.Genre;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
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
        return new Book("Title", genre, "978-3-16-148410-0", "Description", Collections.emptyList(), Arrays.asList(comment, comment2));
    }
}