package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.AuthorDao;
import task07.domain.Author;
import task07.domain.Genre;
import task07.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        boolean valid = false;
        Integer updateId = null;
        Author updatedAuthor;
        while (!valid) {
            try {
                getAll();
                valid = true;
                System.out.println("Enter author id to update:");
                updateId = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                valid = false;
            }
        }
        try {
            updatedAuthor = updateAuthor(reader, authorDao.getById(updateId));
            authorDao.update(updatedAuthor);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Author.class.getName());
        }
        return updatedAuthor;
    }

    @Override
    public void delete(int id) {
        authorDao.delete(id);
    }

    @Override
    public void getById(int id) {
        printObject(authorDao.getById(id));
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

    private Author updateAuthor(BufferedReader reader, Author author) throws IOException {
        if (author == null) {
            throw new ConsoleReadException(Author.class.getName() + " object is null");
        }

        System.out.println("Reading Author object.\nEnter name:");
        String name = reader.readLine();
        if (!name.isEmpty()) {
            author.setName(name);
        }
        System.out.println("Enter sername:");
        String sername = reader.readLine();
        if (!sername.isEmpty()) {
            author.setSername(sername);
        }
        System.out.println("Enter date of birth (yyyy-mm-dd):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateOfBirthString = reader.readLine();
        if (!dateOfBirthString.isEmpty()) {
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
            author.setDateOfBirth(dateOfBirth);
        }
        System.out.println("Enter biography:");
        String biography = reader.readLine();
        if (!biography.isEmpty()) {
            author.setBiography(biography);
        }
        return author;
    }
}
