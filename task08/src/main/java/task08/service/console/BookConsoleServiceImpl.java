package task08.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task08.domain.Author;
import task08.domain.Book;
import task08.domain.Comment;
import task08.domain.Genre;
import task08.exception.ConsoleReadException;
import task08.repository.AuthorRepository;
import task08.repository.BookRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookConsoleServiceImpl implements BookConsoleService {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;

    private final GenreConsoleService genreConsoleService;
    private final CommentConsoleService commentConsoleService;

    @Autowired
    public BookConsoleServiceImpl(BookRepository repository,
                                  AuthorRepository authorRepository,
                                  GenreConsoleService genreConsoleService,
                                  CommentConsoleService commentConsoleService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.genreConsoleService = genreConsoleService;
        this.commentConsoleService = commentConsoleService;
    }

    @Override
    public void create(BufferedReader reader) {
        Book book;
        try {
            book = readBook(reader);
            Genre genre = genreConsoleService.create(reader);
            book.setGenre(genre);
            List<Author> authors = readAuthors(reader);
            book.setAuthors(authors);
            repository.save(book);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while reading " + Book.class.getName());
        }
    }

    @Override
    public void update(BufferedReader reader) {
        boolean valid = false;
        String updateId = null;
        Book updatedBook;
        while (!valid) {
            try {
                getAll();
                valid = true;
                System.out.println("Enter book id to update:");
                updateId = reader.readLine();
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
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void getById(String id) {
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
        final String YES_LETTER = "y";
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
        if (YES_LETTER.equals(updateGenreChoice)) {
            Genre updatedGenre = genreConsoleService.create(reader);
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

    private List<Author> readAuthors(BufferedReader reader) {
        final String EXIT = "exit";
        final String SAVE_EXIT = "save";
        Set<Author> authors = new HashSet<>();
        while (true) {
            printList(authorRepository.findAll());
            System.out.println("Choose an author by id (\"exit\" to exit without save, \"save\" - exit with save):");
            try {
                String authorId = reader.readLine();
                if (EXIT.equals(authorId)) {
                    return new ArrayList<>();
                } else if (SAVE_EXIT.equals(authorId)) {
                    return new ArrayList<>(authors);
                }
                authors.add(authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("No author in repo")));
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void setComment(BufferedReader reader, String id) {
        Comment comment = commentConsoleService.create(reader);
        repository.setComment(id, comment);
    }

    @Override
    public void getComments(String id) {
        printList(repository.getComments(id));
    }
}
