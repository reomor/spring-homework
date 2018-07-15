package task06.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import task06.domain.Genre;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// this is auto rollback every test by default
@Transactional
public class GenreDaoJdbcTest extends AbstractDaoTest{

    @Autowired
    GenreDao genreDao;

    @Test
    public void create() {
        Genre expected = new Genre(5, "new", "empty");
        int row = genreDao.create(expected);
        assertEquals(1, row);
        Genre actual = genreDao.getById(5);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Genre expected = new Genre(4, "taleee", "tale descr");
        int row = genreDao.update(expected);
        assertEquals(1, row);
        Genre actual = genreDao.getById(4);
        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        List<Genre> all = genreDao.getAll();
        assertEquals(4, all.size());
        boolean deleted = genreDao.delete(3);
        assertTrue(deleted);
        all = genreDao.getAll();
        assertEquals(3, all.size());
    }

    @Test
    public void getById() {
        Genre genre = genreDao.getById(2);
        assertEquals(new Genre(2, "romance", "romance descr"), genre);
    }

    @Test
    public void getAll() {
        List<Genre> all = genreDao.getAll();
        assertEquals(4, all.size());
    }
}