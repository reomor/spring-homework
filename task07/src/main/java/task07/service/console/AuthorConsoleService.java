package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.AuthorDao;
import task07.domain.Author;
import task07.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AuthorConsoleService implements DaoConsoleService<Author> {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorConsoleService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }


    @Override
    public void create(BufferedReader reader) {
        Author author = null;
        try {
            author = readAuthor(reader);
            authorDao.create(author);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while reading " + Author.class.getName());
        }
    }

    @Override
    public Author update(BufferedReader reader) {
        return null;
    }

    @Override
    public void delete(int objectId) {

    }

    @Override
    public Author getById(int id) {
        return null;
    }

    @Override
    public void getAll() {
        printList(authorDao.getAll());
    }

    private Author readAuthor(BufferedReader reader) throws IOException {
        System.out.println("Reading Author object.\nEnter name:");
        String name = reader.readLine();
        System.out.println("Enter sername:");
        String sername = reader.readLine();
        System.out.println("Enter date of birth (yyyy-mm-dd):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfBirth = LocalDate.parse(reader.readLine(), formatter);
        System.out.println("Enter biography:");
        String biography = reader.readLine();
        return new Author(null, name, sername, dateOfBirth, biography);
    }
}
