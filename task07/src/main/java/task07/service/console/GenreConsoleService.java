package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.GenreDao;
import task07.domain.Genre;
import task07.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Service
public class GenreConsoleService implements DaoConsoleService<Genre> {

    private final GenreDao genreDao;

    @Autowired
    public GenreConsoleService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public void create(BufferedReader reader) {
        Genre genre;
        try {
            genre = readGenre(reader);
            genreDao.create(genre);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while reading " + Genre.class.getName());
        }
    }

    @Override
    public Genre update(BufferedReader reader) {
        return null;
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
    public void getAll() {
        printList(genreDao.getAll());
    }

    private Genre readGenre(BufferedReader reader) throws IOException {
        System.out.println("Reading Genre object.\nEnter name:");
        String name = reader.readLine();
        System.out.println("Enter description:");
        String description = reader.readLine();
        return new Genre(null, name, description);
    }
}
