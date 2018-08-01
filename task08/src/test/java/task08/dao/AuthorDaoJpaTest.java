package task08.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task08.domain.Author;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


public class AuthorDaoJpaTest extends AbstractTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    public void createAuthor_ShouldSuccess_CreateNewAuthorWithoutBooks() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorDao.create(expected);
        Author actual = authorDao.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateAuthor_ShouldSuccess_UpdateExistingAuthor() {
        final int updateId = 3;
        Author expected = authorDao.getById(updateId);
        expected.setName("Updated");
        authorDao.update(expected);
        Author actual = authorDao.getById(updateId);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteAuthor_ShouldSuccess_DeleteExistingAuthor() {
        final int deleteId = 5;
        int size = authorDao.getAll().size();
        authorDao.delete(deleteId);
        assertEquals(size - 1, authorDao.getAll().size());
        Author expected = authorDao.getById(deleteId);
        assertNull(expected);
    }

    @Test
    public void getAuthorById_ShouldSuccess_ReturnAuthorById() {
        final int getId = 2;
        Author actual = authorDao.getById(getId);
        assertEquals("Rudyard", actual.getName());
    }

    @Test
    public void getAllAuthors_ShouldSuccess_ReturnListOfAuthors() {
        Author expected = new Author(null, "Test", "Sername", LocalDate.of(1990, Month.MARCH, 24), "biography");
        authorDao.create(expected);
        List<Author> all = authorDao.getAll();
        assertThat(all).isNotEmpty().contains(expected);
    }
}