package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.domain.Author;
import task07.exception.ConsoleReadException;
import task07.repository.AuthorRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthorRepositoryConsoleService implements RepositoryConsoleService<Author> {

    private final AuthorRepository repository;

    @Autowired
    public AuthorRepositoryConsoleService(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void create(BufferedReader reader) {
        Author author = null;
        try {
            author = readAuthor(reader);
            repository.save(author);
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
            updatedAuthor = updateAuthor(reader, repository.findById(updateId).orElseThrow(() -> new RuntimeException("No author in repo")));
            repository.save(updatedAuthor);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Author.class.getName());
        }
        return updatedAuthor;
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public void getById(int id) {
        printObject(repository.findById(id));
    }

    @Override
    public void getAll() {
        printList(repository.findAll());
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
