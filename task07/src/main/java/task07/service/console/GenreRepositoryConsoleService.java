package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.domain.Genre;
import task07.exception.ConsoleReadException;
import task07.repository.GenreRepository;

import java.io.BufferedReader;
import java.io.IOException;

@Service
public class GenreRepositoryConsoleService implements RepositoryConsoleService<Genre> {

    private final GenreRepository repository;

    @Autowired
    public GenreRepositoryConsoleService(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(BufferedReader reader) {
        Genre genre;
        try {
            genre = readGenre(reader);
            repository.save(genre);
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
            updatedGenre = updateGenre(reader, repository.findById(updateId).orElseThrow(() -> new RuntimeException("No genre in repo")));
            repository.save(updatedGenre);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Genre.class.getName());
        }
        return updatedGenre;
    }

    @Override
    public void delete(int objectId) {
        repository.deleteById(objectId);
    }

    @Override
    public void getById(int objectId) {
        printObject(repository.findById(objectId));
    }

    @Override
    public void getAll() {
        printList(repository.findAll());
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
