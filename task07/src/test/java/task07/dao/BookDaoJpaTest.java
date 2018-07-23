package task07.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import task07.domain.Author;
import task07.domain.Book;
import task07.domain.Comment;
import task07.domain.Genre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookDaoJpaTest {

    @Autowired
    BookDao bookDao;

    @Test
    public void createBook_ShouldSuccess_CreateNewBook() {
        Book expected = new Book(
                null,
                "New",
                new Genre(1, "detective", "detective descr"),
                "34583405354",
                "description",
                Collections.singletonList(new Author(3, "George", "Lucas", LocalDate.of(1970, Month.MAY, 7), "Creator of a universe.")),
                Collections.singletonList(new Comment(null, "new comment 1", LocalDateTime.now()))
        );
        bookDao.create(expected);
        Book actual = bookDao.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateBook_ShouldSuccess_UpdateExistingBook() {
        final int updateId = 4;
        Book expected = bookDao.getById(updateId);
        expected.setAuthors(Arrays.asList(
                new Author(1, "Mark", "Twain", LocalDate.of(1830, Month.NOVEMBER, 30), "Tom Sawyers creator."),
                new Author(3, "George", "Lucas", LocalDate.of(1970, Month.MAY, 7), "Creator of a universe.")
        ));
        Book actual = bookDao.update(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteBook_ShouldSuccess_DeleteExistingBook() {
        final int deleteId = 5;
        int size = bookDao.getAll().size();
        bookDao.delete(deleteId);
        List<Book> all = bookDao.getAll();
        assertEquals(size - 1, all.size());
        List<Book> actual = bookDao.getByAuthorId(deleteId);
        assertNull(actual);
    }

    @Test
    @Transactional // to load lazy comments
    public void getBookById_ShouldSuccess_ReturnBookWithCommentsById() {
        final int getId = 1;
        Book book = bookDao.getById(getId);
        assertEquals("978-1-49-523895-6", book.getIsbn());
        List<Comment> comments = book.getComments();
        assertEquals(3, comments.size());
    }

    @Test
    public void getAllBooks_ShouldSuccess_ReturnListOfAllBooks() {
        List<Book> all = bookDao.getAll();
        assertEquals(5, all.size());
    }
}