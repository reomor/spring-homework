package task06.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import task06.domain.Author;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
public class AuthorDaoJdbcTest extends AbstractDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    public void testCreate_shouldCreateAuthor() {
        int authorIdToCreate = 5;
        Author expected = new Author(
                authorIdToCreate, "New", "Author",
                LocalDate.of(1999, Month.JANUARY, 25), "empty"
        );
        int row = authorDao.create(expected);
        assertEquals(1, row);
        Author actual = authorDao.getById(authorIdToCreate);
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_shouldUpdateAuthor() {
        Author expected = new Author(
                1, "Mark", "Twain-Twain",
                LocalDate.of(1999, Month.JANUARY, 25), "empty"
        );
        int row = authorDao.update(expected);
        assertEquals(1, row);
        Author actual = authorDao.getById(1);
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete_shouldDeleteAuthorById() {
        int size = authorDao.getAll().size();
        boolean deleted = authorDao.delete(4);
        assertTrue(deleted);
        List<Author> all = authorDao.getAll();
        assertEquals(size - 1, all.size());
    }

    @Test
    public void testGet_shouldReturnAuthorById() {
        int authorIdToGet = 2;
        Author expected = new Author(
                authorIdToGet, "Rudyard", "Kipling",
                LocalDate.of(1860, Month.DECEMBER, 30), "Maugly creator."
        );
        Author actual = authorDao.getById(authorIdToGet);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll_shouldReturnNonEmptyList() {
        List<Author> all = authorDao.getAll();
        assertEquals(4, all.size());
    }
}