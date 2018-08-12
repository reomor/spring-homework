package task09.service.console;

import org.springframework.stereotype.Service;
import task09.domain.Genre;
import task09.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;

@Service
public class GenreConsoleServiceImpl implements GenreConsoleService {

    public Genre create(BufferedReader reader) {
        Genre genre;
        try {
            genre = readGenre(reader);
        } catch (IOException e) {
            throw new ConsoleReadException("Error read " + Genre.class);
        }
        return genre;
    }

    private Genre readGenre(BufferedReader reader) throws IOException {
        System.out.println("Reading Genre object.\nEnter name:");
        String name = reader.readLine();
        System.out.println("Enter description:");
        String description = reader.readLine();
        return new Genre(name, description);
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
