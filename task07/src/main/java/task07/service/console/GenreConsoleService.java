package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.GenreDao;
import task07.domain.Genre;

import java.util.List;

@Service
public class GenreConsoleService implements DaoConsoleService<Genre> {

    private final GenreDao genreDao;

    @Autowired
    public GenreConsoleService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void create() {
    }

    @Override
    public Genre update(Genre object) {
        return genreDao.update(object);
    }

    @Override
    public void delete(int objectId) {
        genreDao.delete(objectId);
    }

    @Override
    public Genre getById(int objectId) {
        return genreDao.getById(objectId);
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
