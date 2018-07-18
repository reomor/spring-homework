package task06.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import task06.domain.Book;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
public class BookDaoJdbcTest extends AbstractDaoTest {
    
    @Autowired
    private BookDao bookDao;

    @Test
    public void testCreate_ShouldSuccess_CreateBook() {
        int bookId = 6;
        Book expected = new Book(null, "Test", 3, "978-0-34-526079-9", "empty");
        bookDao.create(expected);
        Book actual = bookDao.getById(bookId);
        expected.setId(actual.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdate_ShouldSuccess_UpdateBook() {
        Book expected = bookDao.getById(1);
        expected.setTitle("new title");
        bookDao.update(expected);
        Book actual = bookDao.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete_ShouldSuccess_DeleteBook() {
        int size = bookDao.getAll().size();
        boolean isDeleted = bookDao.delete(5);
        assertTrue(isDeleted);
        List<Book> all = bookDao.getAll();
        assertEquals(size - 1, all.size());
    }

    @Test
    public void testGetById_ShouldSuccess_GetBook() {
        Book expected = new Book(2, "The Jungle Book", 2, "978-1-50-333254-6", "some");
        Book actual = bookDao.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll_ShouldSuccess_GetAllBooks() {
        List<Book> all = bookDao.getAll();
        assertEquals(5, all.size());
    }

    @Test
    public void testGetByAuthor_ShouldSuccess_ReturnListOfBooks() {
        int authorId = 1;
        List<Book> booksByAuthorId = bookDao.getByAuthorId(authorId);
        assertEquals(2, booksByAuthorId.size());
    }
}