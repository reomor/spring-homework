package task09.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task09.domain.Author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    public void saveAuthor_ShouldSuccess_SaveNewAuthor() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        Author actual = repository.save(expected);
        assertNotNull(actual.getId());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void updateAuthor_ShouldSuccess_UpdateExistingAuthor() {
        final String updatedName = "Updated";
        Author expected = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        repository.save(expected);
        Author actual = repository.findById(expected.getId()).orElseThrow(() -> new RuntimeException("No author in repo"));
        actual.setName(updatedName);
        repository.save(actual);
        assertEquals(updatedName, actual.getName());
    }

    @Test
    public void deleteAuthor_ShouldSuccess_DeleteExistingAuthor() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        expected = repository.save(expected);
        repository.deleteById(expected.getId());
        Optional<Author> authorOptional = repository.findById(expected.getId());
        assertFalse(authorOptional.isPresent());
    }

    @Test
    public void findById_ShouldSuccess_FindAuthorById() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        repository.save(expected);
        Optional<Author> authorOptional = repository.findById(expected.getId());
        assertTrue(authorOptional.isPresent());
        assertEquals(expected, authorOptional.get());
    }

    @Test
    public void findAll_ShouldSuccess_FindAllAuthors() {
        Author author = new Author(null, "Test", "Sername", LocalDate.now(), "biography");
        repository.save(author);
        List<Author> all = repository.findAll();
        assertThat(all).isNotEmpty().contains(author);
    }
}