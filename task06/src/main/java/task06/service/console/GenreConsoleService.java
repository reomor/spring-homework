package task06.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task06.dao.GenreDao;
import task06.domain.Genre;
import task06.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;

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
        boolean valid = false;
        Integer updateId = null;
        Genre updatedGenre;
        while (!valid) {
            try {
                getAll();
                valid = true;
                System.out.println("Enter genre id to update:");
                updateId = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                valid = false;
            }
        }
        try {
            updatedGenre = updateGenre(reader, genreDao.getById(updateId));
            genreDao.update(updatedGenre);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Genre.class.getName());
        }
        return updatedGenre;
    }

    @Override
    public void delete(int objectId) {
        genreDao.delete(objectId);
    }

    @Override
    public void getById(int objectId) {
        printObject(genreDao.getById(objectId));
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

    private Genre updateGenre(BufferedReader reader, Genre genre) throws IOException {
        if (genre == null) {
            throw new ConsoleReadException(Genre.class.getName() + " object is null");
        }
        System.out.println("Reading Genre object.\nEnter name:");
        String name = reader.readLine();
        if (!name.isEmpty()) {
            genre.setName(name);
        }
        System.out.println("Enter description:");
        String description = reader.readLine();
        if (!description.isEmpty()) {
            genre.setDescription(description);
        }
        return genre;
    }
}
