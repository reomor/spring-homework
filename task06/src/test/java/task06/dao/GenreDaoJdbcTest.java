package task06.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import task06.domain.Genre;

import java.util.List;

import static org.junit.Assert.*;

public class GenreDaoJdbcTest extends AbstractDaoTest {

    @Autowired
    GenreDao genreDao;

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void getAll() {
        List<Genre> all = genreDao.getAll();
        assertEquals(4, all.size());
    }
}