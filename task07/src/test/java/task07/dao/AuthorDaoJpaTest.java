package task07.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task07.domain.Author;
import task07.domain.Book;
import task07.domain.Genre;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class AuthorDaoJpaTest {

    @Autowired
    AuthorDao authorDao;

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
        List<Author> all = authorDao.getAll();
        assertEquals(5, all.size());
    }
}