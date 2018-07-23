package task07.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import task07.domain.Genre;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class GenreDaoJpaTest {

    @Autowired
    GenreDao genreDao;

    @Test
    public void createGenre_ShouldSuccess_CreateNewGenre() {
        Genre expected = new Genre(null, "new", "description");
        genreDao.create(expected);
        Genre actual = genreDao.getById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void updateGenre_ShouldSuccess_UpdateExistingGenre() {
        final int updateId = 2;
        Genre expected = genreDao.getById(updateId);
        expected.setName("Updated name");
        genreDao.update(expected);
        Genre actual = genreDao.getById(updateId);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteGenre_ShouldSuccess_DeleteExistingGenre() {
        final int deleteId = 5;
        int size = genreDao.getAll().size();
        genreDao.delete(deleteId);
        assertEquals(size - 1, genreDao.getAll().size());
        Genre expected = genreDao.getById(deleteId);
        assertNull(expected);
    }

    @Test
    public void getGenreById_ShouldSuccess_ReturnGenreById() {
        final int getId = 4;
        Genre actual = genreDao.getById(getId);
        assertEquals("tale", actual.getName());
    }

    @Test
    public void getAllGenres_ShouldSuccess_ReturnListOfAllGenres() {
        List<Genre> all = genreDao.getAll();
        assertEquals(5, all.size());
    }
}