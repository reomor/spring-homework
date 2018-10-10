package task17.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task17.domain.Author;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void saveAuthor_ShouldSuccess_SaveNewAuthor() {
        Author author = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorRepository.save(author);
        assertThat(author.getId()).isNotNull();
    }

    @Test
    public void updateAuthor_ShouldSuccess_UpdateAuthor() {
        final String updateSername = "Updated";
        Author expected = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorRepository.save(expected);
        expected.setSername(updateSername);
        Optional<Author> authorOptional = authorRepository.findById(expected.getId());
        assertTrue(authorOptional.isPresent());
        assertEquals(updateSername, authorOptional.get().getSername());
    }

    @Test
    public void deleteAuthor() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorRepository.save(expected);
        authorRepository.deleteById(expected.getId());
        Optional<Author> authorOptional = authorRepository.findById(expected.getId());
        assertFalse(authorOptional.isPresent());
    }

    @Test
    public void getAuthorById() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorRepository.save(expected);
        Optional<Author> authorOptional = authorRepository.findById(expected.getId());
        assertTrue(authorOptional.isPresent());
        assertNotNull(authorOptional.get());
    }

    @Test
    public void getAllAuthors() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorRepository.save(expected);
        List<Author> all = authorRepository.findAll();
        assertThat(all).isNotEmpty().contains(expected);
    }
}