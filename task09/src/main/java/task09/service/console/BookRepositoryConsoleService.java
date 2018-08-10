package task09.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task09.domain.Author;
import task09.domain.Book;
import task09.domain.Genre;
import task09.exception.ConsoleReadException;
import task09.repository.AuthorRepository;
import task09.repository.BookRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookRepositoryConsoleService implements RepositoryConsoleService<Book> {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryConsoleService(BookRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void create(BufferedReader reader) {
        Book book;
        try {
            book = readBook(reader);
            Genre genre;// = readGenre(reader);
            // book.setGenre(genre);
            List<Author> authors = readAuthors(reader);
            book.setAuthors(authors);
            repository.save(book);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while reading " + Book.class.getName());
        }
    }

    @Override
    public Book update(BufferedReader reader) {
        boolean valid = false;
        Integer updateId = null;
        Book updatedBook;
        while (!valid) {
            try {
                getAll();
                valid = true;
                System.out.println("Enter book id to update:");
                updateId = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                valid = false;
            }
        }
        try {
            updatedBook = updateBook(reader, repository.findById(updateId).orElseThrow(() -> new RuntimeException("No book in repo")));
            repository.save(updatedBook);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Genre.class.getName());
        }
        return updatedBook;
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

    private Book readBook(BufferedReader reader) throws IOException {
        System.out.println("Reading Book object.\nEnter title:");
        String title = reader.readLine();
        System.out.println("Enter isbn:");
        String isbn = reader.readLine();
        System.out.println("Enter description:");
        String description = reader.readLine();
        return new Book(null, title, null, isbn, description, null, null);
    }

    private Book updateBook(BufferedReader reader, Book book) throws IOException {
        if (book == null) {
            throw new ConsoleReadException(Book.class.getName() + " object is null");
        }
        System.out.println("Reading Book object.\nEnter title:");
        String title = reader.readLine();
        if(!title.isEmpty()) {
            book.setTitle(title);
        }
        System.out.println("Enter isbn:");
        String isbn = reader.readLine();
        if (!isbn.isEmpty()) {
            book.setIsbn(isbn);
        }
        System.out.println("Enter description:");
        String description = reader.readLine();
        if (!description.isEmpty()) {
            book.setDescription(description);
        }
        // ask genre
        System.out.println("Update book genre? (y/n, enter to skip");
        String updateGenreChoice = reader.readLine();
        Genre updatedGenre = null;
        final String YES_LETTER = "y";
        if (YES_LETTER.equals(updateGenreChoice)) {
            // updatedGenre = readGenre(reader);
            book.setGenre(updatedGenre);
        }
        // ask authors
        System.out.println("Update book authors? (y/n, enter to skip");
        String updateAuthorChoice = reader.readLine();
        List<Author> authors = null;
        if (YES_LETTER.equals(updateAuthorChoice)) {
            authors = readAuthors(reader);
            book.setAuthors(authors);
        }
        return book;
    }
/*
    private Genre readGenre(BufferedReader reader) throws IOException {
        System.out.println("Choose a genre by id:");
        printList(genreRepository.findAll());
        final int genreId = Integer.parseInt(reader.readLine());
        return genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("No genre in repo"));
    }
//*/
    private List<Author> readAuthors(BufferedReader reader) {
        Set<Author> authors = new HashSet<>();
        while (true) {
            System.out.println("Choose an author by id (-1 - exit without save, 0 - exit):");
            printList(authorRepository.findAll());
            try {
                int authorId = Integer.parseInt(reader.readLine());
                if (authorId == -1) {
                    return new ArrayList<>();
                } else if (authorId == 0) {
                    return new ArrayList<>(authors);
                }
                //authors.add(authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("No author in repo")));
            } catch (IOException ignored) {
            }
        }
    }
}
