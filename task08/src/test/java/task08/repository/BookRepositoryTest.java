package task08.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task08.domain.Author;
import task08.domain.Book;
import task08.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void saveBook_ShouldSuccess_SaveNewBook() {
        Genre genre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("No genre in repo"));
        Author author = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("No author in repo"));
        Book book = new Book(
                null,
                "New",
                genre,
                "34583405354",
                "description",
                Collections.singletonList(author),
                Collections.emptyList()
        );
        bookRepository.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void updateBook_ShouldSuccess_UpdateBook() {
        final String updateIsbn = "978-0-34-526079-0";
        Genre genre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("No genre no repo"));
        Author author = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("No author no repo"));
        Book book = new Book(
                null,
                "New",
                genre,
                "34583405354",
                "description",
                Collections.singletonList(author),
                Collections.emptyList()
        );
        bookRepository.save(book);
        book.setIsbn(updateIsbn);
        Book actual = bookRepository.findById(book.getId()).orElseThrow(() -> new RuntimeException("No book in repo"));
        assertNotNull(actual);
        assertEquals(updateIsbn, actual.getIsbn());
    }

    @Test
    public void deleteBookById_ShouldSuccess_DeleteBook() {
        Genre genre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("No genre no repo"));
        Author author = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("No author no repo"));
        Book book = new Book(
                null,
                "New",
                genre,
                "34583405354",
                "description",
                Collections.singletonList(author),
                Collections.emptyList()
        );
        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertFalse(bookOptional.isPresent());
    }

    @Test
    public void getBookById_ShouldSuccess_GetBookById() {
        Genre genre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("No genre no repo"));
        Author author = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("No author no repo"));
        Book book = new Book(
                null,
                "New",
                genre,
                "34583405354",
                "description",
                Collections.singletonList(author),
                Collections.emptyList()
        );
        bookRepository.save(book);
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        assertTrue(bookOptional.isPresent());
        assertNotNull(bookOptional.get());
    }

    @Test
    public void findAll() {
        Genre genre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("No genre no repo"));
        Author author = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("No author no repo"));
        Book book = new Book(
                null,
                "New",
                genre,
                "34583405354",
                "description",
                Collections.singletonList(author),
                Collections.emptyList()
        );
        bookRepository.save(book);
        List<Book> all = bookRepository.findAll();
        assertThat(all).isNotEmpty().contains(book);
    }
}