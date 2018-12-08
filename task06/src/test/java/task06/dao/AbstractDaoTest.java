package task06.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task06.domain.Author;
import task06.domain.Book;
import task06.domain.Genre;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class AbstractDaoTest {
    @Autowired
    GenreDao genreDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    BookDao bookDao;

    @Test
    public void test() {
        List<Genre> genres = genreDao.getAll();
        assertEquals(5, genres.size());
        List<Author> authors = authorDao.getAll();
        assertEquals(5, authors.size());
        List<Book> books = bookDao.getAll();
        assertEquals(5, books.size());
    }
}