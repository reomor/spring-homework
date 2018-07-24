package task07.service.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task07.dao.AuthorDao;
import task07.dao.BookDao;
import task07.dao.GenreDao;
import task07.domain.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

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
            System.out.println("Choose a genre by id:");
            printList(genreDao.getAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book update(BufferedReader reader) {
        return null;
    }

    @Override
    public void delete(int objectId) {

    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public List<Book> getAll() {
        return null;
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
}
