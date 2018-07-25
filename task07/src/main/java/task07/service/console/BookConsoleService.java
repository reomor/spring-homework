package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.AuthorDao;
import task07.dao.BookDao;
import task07.dao.GenreDao;
import task07.domain.Author;
import task07.domain.Book;
import task07.domain.Genre;
import task07.exception.ConsoleReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookConsoleService implements DaoConsoleService<Book> {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Autowired
    public BookConsoleService(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public void create(BufferedReader reader) {
        Book book;
        try {
            book = readBook(reader);
            Genre genre = readGenre(reader);
            book.setGenre(genre);
            List<Author> authors = readAuthors(reader);
            book.setAuthors(authors);
            bookDao.create(book);
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
            updatedBook = updateBook(reader, bookDao.getById(updateId));
            bookDao.update(updatedBook);
        } catch (IOException e) {
            throw new ConsoleReadException("Error while updating " + Genre.class.getName());
        }
        return updatedBook;
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }

    @Override
    public Book getById(int id) {
        return bookDao.getById(id);
    }

    @Override
    public void getAll() {
        printList(bookDao.getAll());
    }

    private Book readBook(BufferedReader reader) throws IOException {
        System.out.println("Reading Book object.\nEnter title:");
        String title = reader.readLine();
        System.out.println("Enter isbn:");
        String isbn = reader.readLine();
        System.out.println("Enter description:");
        String description = reader.readLine();
        return new Book(null, title, null, isbn, description);
    }

    private Book updateBook(BufferedReader reader, Book book) throws IOException {
        if (book == null) {
            throw new ConsoleReadException(Book.class.getName() + " object is null");
        }
        System.out.println("Reading Book object.\nEnter title:");
        String title = reader.readLine();
        System.out.println("Enter isbn:");
        String isbn = reader.readLine();
        System.out.println("Enter description:");
        String description = reader.readLine();
        return null;
    }

    private Genre readGenre(BufferedReader reader) throws IOException {
        System.out.println("Choose a genre by id:");
        printList(genreDao.getAll());
        final int genreId = Integer.parseInt(reader.readLine());
        return genreDao.getById(genreId);
    }

    private List<Author> readAuthors(BufferedReader reader) {
        Set<Author> authors = new HashSet<>();
        while (true) {
            System.out.println("Choose an author by id (-1 - exit without save, 0 - exit):");
            printList(authorDao.getAll());
            try {
                int authorId = Integer.parseInt(reader.readLine());
                if (authorId == -1) {
                    return new ArrayList<>();
                } else if (authorId == 0) {
                    return new ArrayList<>(authors);
                }
                authors.add(authorDao.getById(authorId));
            } catch (IOException ignored) {
            }
        }
    }
}
