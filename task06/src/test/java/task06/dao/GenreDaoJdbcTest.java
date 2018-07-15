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
        final int idToInsert = 6;
        Genre expected = new Genre(idToInsert, "new", "empty");
        int row = genreDao.create(expected);
        assertEquals(1, row);
        Genre actual = genreDao.getById(idToInsert);
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
        int idToDelete = 5;
        int size = genreDao.getAll().size();
        boolean deleted = genreDao.delete(idToDelete);
        assertTrue(deleted);
        List<Genre> all = genreDao.getAll();
        assertEquals(size - 1, all.size());
    }

    @Test
    public void getById() {
        Genre expected = new Genre(2, "romance", "romance descr");
        Genre actual = genreDao.getById(2);
        assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        List<Genre> all = genreDao.getAll();
        assertEquals(5, all.size());
    }
}