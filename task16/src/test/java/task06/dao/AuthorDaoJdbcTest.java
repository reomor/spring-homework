package task16.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import task16.domain.Author;
import task16.domain.Book;

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
        int authorIdToCreate = 6;
        Author expected = new Author(
                null, "New", "Author",
                LocalDate.of(1999, Month.JANUARY, 25), "empty"
        );
        int row = authorDao.create(expected);
        assertEquals(1, row);
        Author actual = authorDao.getById(authorIdToCreate);
        expected.setId(actual.getId());
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
        int deleteId = 5;
        int size = authorDao.getAll().size();
        boolean deleted = authorDao.delete(deleteId);
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
        assertEquals(5, all.size());
    }

    @Test
    public void testGetByBookId_ShouldSuccess_ReturnBookAuthors() {
        int bookId = 2;
        List<Author> authorsByBookId = authorDao.getByBookId(bookId);
        assertEquals(2, authorsByBookId.size());
    }
}